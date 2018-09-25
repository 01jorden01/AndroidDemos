package be.example.j.retrofitoef;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.example.j.retrofitoef.adapter.AfvalMandAdapter;
import be.example.j.retrofitoef.model.AfvalMand;
import be.example.j.retrofitoef.network.RetrofitClientInstance;
import be.example.j.retrofitoef.services.IAfvalMandService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    private AfvalMandAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        IAfvalMandService service = RetrofitClientInstance.getRetrofitInstance().create(IAfvalMandService.class);
        Call<List<AfvalMand>> call = service.getAllAfvalmandenjson();
        call.enqueue(new Callback<List<AfvalMand>>() {
            @Override
            public void onResponse(Call<List<AfvalMand>> call, Response<List<AfvalMand>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<AfvalMand>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<AfvalMand> afvalMandListbody) {
        //0) Nullwaardes uit lijst halen
        List<AfvalMand> lijstZonderNullWaardes = new ArrayList<AfvalMand>();
        for (AfvalMand a : afvalMandListbody) {
            if (!TextUtils.isEmpty(a.getNUMMER()) && !TextUtils.isEmpty(a.getDEELGEMEENTE()) && !TextUtils.isEmpty(a.getSTRAAT())) {
                lijstZonderNullWaardes.add(a);
            }
        }

        //1) Referentie naar recycleviewer
        recyclerView = findViewById(R.id.rvAfvalMand);
        adapter = new AfvalMandAdapter(this, lijstZonderNullWaardes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}

//https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23