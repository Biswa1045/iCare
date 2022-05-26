package com.igit.icare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class LoginActivity extends AppCompatActivity {
    EditText name,email,password;
    String name_s,email_s,password_s;

   // public static final String KEY_STORE = "opened";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

                findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name_s =  name.getText().toString();
                email_s =  email.getText().toString();
                password_s =  password.getText().toString();

                Intent in = new Intent(getApplicationContext(),HomeActivity.class);
                in.putExtra("name",name_s);
                in.putExtra("email",email_s);
                startActivity(in);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}