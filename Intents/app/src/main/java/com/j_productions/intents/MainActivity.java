package com.j_productions.intents;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.j_productions.intents.Contract.PICK_CONTACT;
import static com.j_productions.intents.Contract.PICK_LOCATION;
import static com.j_productions.intents.Contract.PICK_LOCATION_KEY;


public class MainActivity extends AppCompatActivity {

    CardView cardGeo;
    CardView cardInternet;
    CardView cardContact;
    CardView cardDail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardInternet = (CardView) findViewById(R.id.cardInternet);
        cardGeo = (CardView) findViewById(R.id.cardGeo);
        cardContact = (CardView) findViewById(R.id.cardContacts);
        cardDail = (CardView) findViewById(R.id.cardDial);


        cardInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowserToXDADeveopers();
            }
        });
        cardGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickPlace();
            }
        });
        cardContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseContact();
            }
        });
        cardDail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callANumber();
            }
        });

        //kleur navigatiebar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#e0f1f1"));
        }
    }


    private void PickPlace() {
        Intent intent = new Intent(this, GeoActivity.class);
        startActivityForResult(intent, PICK_LOCATION);
    }

    private void chooseContact() {
        //Controle voor permissie systeem
        if ((int) Build.VERSION.SDK_INT < 23) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
        //Marshmellow en nieuwer
        else {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);


            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                //GEEN permissie :-(
                Toast.makeText(this, "Er zijn geen permissies toegekend om Contacten te raadplegen ", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);

            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        }
    }

    private void openBrowserToXDADeveopers() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nmct.be"));
        intent.setData(Uri.parse("https://www.xda-developers.com/"));
        startActivity(intent);
    }

    private void callANumber() {

        if ((int) Build.VERSION.SDK_INT < 23) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:0479444053"));
            startActivity(intent);
        } else {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                //GEEN permissie :-(

                Toast.makeText(this, "Er zijn geen permissies toegekend om te bellen ", Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);


            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0479444053"));
                startActivity(intent);
            }

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri gekozenContact = data.getData();
                Cursor c = getContentResolver().query(gekozenContact, null, null, null, null);

                if (c.moveToFirst()) {
                    String Naam = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    Toast.makeText(this, "U hebt " + Naam + " gekozen", Toast.LENGTH_LONG).show();
                }
            }

            if (requestCode == 2) {
//                if (resultCode == RESULT_OK) {
//                    String contents1 = intent.getStringExtra("SCAN_RESULT");
//                    String format1 = intent.getStringExtra("SCAN_RESULT_FORMAT");
//                    EditText assetMon2 = (EditText) findViewById(R.id.assetMon2);
//                    assetMon2.setText(contents1);
//                } else if (resultCode == RESULT_CANCELED) {
//                    // Handle cancel
//                }
            }
        }
        if (requestCode == PICK_LOCATION) {
            String result = data.getStringExtra(PICK_LOCATION_KEY);
            Toast.makeText(this, "Gekozen locatie: " + result + "!", Toast.LENGTH_LONG).show();
        }
    }

}
