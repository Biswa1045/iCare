package com.igit.icare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
       findViewById(R.id.back_doc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(in);
                finish();
            }
        });
        findViewById(R.id.wp1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsappContact("+918249083855");
            }
        });
    }
    void openWhatsappContact(String number) {
        String phoneNumberWithCountryCode = number;
        String message = "Hello";
        startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                        )
                )
        );
    }
}