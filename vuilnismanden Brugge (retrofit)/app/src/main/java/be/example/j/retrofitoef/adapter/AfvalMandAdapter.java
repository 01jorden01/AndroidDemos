package be.example.j.retrofitoef.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import be.example.j.retrofitoef.R;
import be.example.j.retrofitoef.model.AfvalMand;

public class AfvalMandAdapter extends RecyclerView.Adapter<AfvalMandAdapter.AfvalMandViewHolder> {

    private List<AfvalMand> dataList;
    private Context context;

    public AfvalMandAdapter(Context context, List<AfvalMand> afvalMandListList) {
        this.context = context;
        this.dataList = afvalMandListList;
    }

    //region verplichte methods voor recycleview
    @NonNull
    @Override
    public AfvalMandViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.afvalbak_item, viewGroup, false);

//        if (viewGroup.getContext() instanceof CurrentEventClickListener) {
//            mListener = (CurrentEventClickListener) viewGroup.getContext();
//        }


        return new AfvalMandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AfvalMandViewHolder holder, int position) {
        // holder.txtTitle.setText(dataList.get(position).getTitle());

        if (!TextUtils.isEmpty(dataList.get(position).getNUMMER()) && !TextUtils.isEmpty(dataList.get(position).getDEELGEMEENTE()) && !TextUtils.isEmpty(dataList.get(position).getSTRAAT())) {
            holder.afvalmandNummer.setText(dataList.get(position).getNUMMER());
            holder.afvalmandDeelgemeente.setText(dataList.get(position).getDEELGEMEENTE());
            holder.afvalmandStraat.setText(dataList.get(position).getSTRAAT());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
//endregion


    class AfvalMandViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public TextView afvalmandNummer;
        public TextView afvalmandDeelgemeente;
        public TextView afvalmandStraat;

        CardView cv;


        AfvalMandViewHolder(View view) {
            super(view);
            mView = view;

            cv = view.findViewById(R.id.cv);
            afvalmandNummer = view.findViewById(R.id.txtNummer);
            afvalmandDeelgemeente = view.findViewById(R.id.txtDeelGemeente);
            afvalmandStraat = view.findViewById(R.id.txtStraat);

        }
    }

}
