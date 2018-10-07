package com.j_productions.intents;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static com.j_productions.intents.Contract.PICK_LOCATION;
import static com.j_productions.intents.Contract.PICK_LOCATION_KEY;

public class GeoActivity extends AppCompatActivity {

    private Button btnToonLocatie;
    private EditText txtLongitude;
    private EditText txtLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        txtLongitude = (EditText) findViewById(R.id.txtLongitude);
        txtLatitude = (EditText) findViewById(R.id.txtLatitude);

        btnToonLocatie = (Button) findViewById(R.id.btnToonLocatie);
        btnToonLocatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAwnser();
                finish();
            }
        });

        //kleur navigatiebar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#dedede"));
        }
    }


    public void sendAwnser() {

        if (!TextUtils.isEmpty(this.txtLatitude.getText().toString()) && !TextUtils.isEmpty(this.txtLongitude.getText().toString())) {
            float lat = Float.parseFloat(this.txtLatitude.getText().toString());
            float longi = Float.parseFloat(this.txtLongitude.getText().toString());

            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", longi, lat);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        startActivity(intent);

            Intent resultIntent = new Intent();

            resultIntent.putExtra(PICK_LOCATION_KEY, uri);
            setResult(PICK_LOCATION, resultIntent);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Gelieve alle gegevens correct in te vullen.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
