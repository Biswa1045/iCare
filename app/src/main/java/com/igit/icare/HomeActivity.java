package com.igit.icare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class HomeActivity extends AppCompatActivity {
    private Dialog dialog;
    TextView temp_msg,spo2_msg,bpm_msg;
    Button range_1_temp,range_2_temp,range_3_temp,range_4_temp;
    Button range_1_bpm,range_2_bpm,range_3_bpm,range_4_bpm,range_0_bpm;
    Button range_1_spo2,range_2_spo2,range_3_spo2,range_4_spo2;
    Button stop_temp,stop_bpm,stop_spo2;
    TextView temp_txt,heart_txt,oxy_txt;
    DatabaseReference myRef_temp,myRef_constant_temp,reference_getdata_temp;
    DatabaseReference myRef_bpm,myRef_constant_bpm,reference_getdata_bpm;
    DatabaseReference myRef_spo2,myRef_constant_spo2,reference_getdata_spo2;
    String name,email;
    int live_value,live_value_bpm,live_value_spo2;
    String start_temp_1,start_temp_2,start_temp_3,start_temp_4="off";
    String start_bpm_0,start_bpm_1,start_bpm_2,start_bpm_3,start_bpm_4="off";
    String start_spo2_1,start_spo2_2,start_spo2_3,start_spo2_4="off";
    TextView date,greetings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        dialog = new Dialog(this);

        temp_msg = findViewById(R.id.temp_msg);
        spo2_msg = findViewById(R.id.spo2_msg);
        bpm_msg = findViewById(R.id.bpm_msg);
        range_1_temp = findViewById(R.id.range_1_temp);
        range_2_temp =findViewById(R.id.range_2_temp);
        range_3_temp = findViewById(R.id.range_3_temp);
        range_4_temp = findViewById(R.id.range_4_temp);
        range_0_bpm = findViewById(R.id.range_0_bpm);
        range_1_bpm = findViewById(R.id.range_1_bpm);
        range_2_bpm =findViewById(R.id.range_2_bpm);
        range_3_bpm = findViewById(R.id.range_3_bpm);
        range_4_bpm = findViewById(R.id.range_4_bpm);

        range_1_spo2 = findViewById(R.id.range_1_spo2);
        range_2_spo2 =findViewById(R.id.range_2_spo2);
        range_3_spo2 = findViewById(R.id.range_3_spo2);
        range_4_spo2 = findViewById(R.id.range_4_spo2);

        stop_temp = findViewById(R.id.stop_temp);
        stop_bpm = findViewById(R.id.stop_bpm);
        stop_spo2 = findViewById(R.id.stop_spo2);

        temp_txt = findViewById(R.id.temp_txt);
        heart_txt = findViewById(R.id.heart_txt);
        oxy_txt = findViewById(R.id.oxy_txt);
        date = findViewById(R.id.date);
        greetings = findViewById(R.id.greetings);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);



        Timer temp_get_timer = new Timer();
        Timer temp_set_timer = new Timer();
        temp_get_timer.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        show_data("temp");
                        show_data_spo2("oxymeter");
                        show_data_bpm("bpm");
                    }
                },
                0,
                2000);
        findViewById(R.id.report_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in  = new Intent(getApplicationContext(),HistoryActivity.class);
                in.putExtra("name",name);
                in.putExtra("email",email);
                startActivity(in);
                finish();
            }
        });
        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView cancel_dailog;
                TextView name_d,email_d;

                dialog.setContentView(R.layout.custom_dialog);

                name_d = dialog.findViewById(R.id.name);
                email_d = dialog.findViewById(R.id.email);
                name_d.setText(name);
                email_d.setText(email);
                cancel_dailog = dialog.findViewById(R.id.cancel_dailog);
                cancel_dailog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in  = new Intent(getApplicationContext(),HistoryActivity.class);
                        in.putExtra("name",name);
                        in.putExtra("email",email);
                        startActivity(in);
                        finish();
                    }
                });
                dialog.findViewById(R.id.doctor).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in  = new Intent(getApplicationContext(),DoctorActivity.class);
                        startActivity(in);
                        finish();
                    }
                });
                dialog.findViewById(R.id.logout_real).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in  = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(in);
                        finish();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        range_1_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_temp_1 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_temp_1.equals("on")){

                                    temp_range(34,36,true);// lower than body temp
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_2_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_temp_2 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_temp_2.equals("on")){

                                    temp_range(35,37,true);// normal body temp
                                }

                            }
                        },
                        0,
                        2000);
            }
        });
        range_3_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_temp_3 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_temp_3.equals("on")){
                                    temp_range(37,38,true);// normal fever
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_4_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_temp_4 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_temp_4.equals("on")){
                                    temp_range(0,0,true);
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        stop_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_temp_1 = "off";
                start_temp_2 = "off";
                start_temp_3 = "off";
                start_temp_4 = "off";
            }
        });

        range_0_bpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_bpm_0 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_bpm_0.equals("on")){
                                    bpm_range(50,60,true);

                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_1_bpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_bpm_1 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_bpm_1.equals("on")){
                                    bpm_range(60,70,true);

                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_2_bpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_bpm_2 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_bpm_2.equals("on")){
                                    bpm_range(70,80,true);
                                }

                            }

                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_3_bpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_bpm_3 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_bpm_3.equals("on")){
                                    bpm_range(100,105,true);
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_4_bpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_bpm_4 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_bpm_4.equals("on")){
                                    bpm_range(0,0,true);
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        stop_bpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_bpm_2 = "off";
                start_bpm_3 = "off";
                start_bpm_4 = "off";
                start_bpm_1 = "off";
            }
        });

        range_1_spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_spo2_1 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_spo2_1.equals("on")){
                                    oxy_range(93,95,true);

                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_2_spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_spo2_2 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_spo2_2.equals("on")){
                                    oxy_range(95,97,true);
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_3_spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_spo2_3 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_spo2_3.equals("on")){
                                    oxy_range(98,99,true);
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        range_4_spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_spo2_4 = "on";
                temp_set_timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            public void run()
                            {
                                if(start_spo2_4.equals("on")){
                                    oxy_range(0,0,true);
                                }

                            }
                        },
                        0,      // run first occurrence immediatetly
                        2000);
            }
        });
        stop_spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_spo2_1 = "off";
                start_spo2_2 = "off";
                start_spo2_3 = "off";
                start_spo2_4 = "off";
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }



    public void temp_range(int min,int max,boolean c){
        if(c){

            int number = ThreadLocalRandom.current().nextInt(min, max + 1);
            String time_id= new SimpleDateFormat("ddHHmmss", Locale.getDefault()).format(new Date());;
            myRef_constant_temp = FirebaseDatabase.getInstance().getReference().child("temp").child("constant");
            myRef_temp = FirebaseDatabase.getInstance().getReference().child("temp").child("chart_data").child(time_id);
            ValueEventListener eventListener_temp = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        myRef_temp.child("value").setValue(number);
                        //  myRef.child("userid").setValue(userid);
                        myRef_constant_temp.child("value").setValue(number);
                        // myRef.child("msg").setValue(msg_str);
                        myRef_temp.child("time").setValue(time_id);
                        //  myRef.child("date").setValue(date_str);

                    }else{
                        myRef_constant_temp.child("value").setValue(number);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            myRef_temp.addListenerForSingleValueEvent(eventListener_temp);

        }

  }
    public void bpm_range(int min,int max,boolean c){
        if(c){
            int number = ThreadLocalRandom.current().nextInt(min, max + 1);
            String time_id= new SimpleDateFormat("ddHHmmss", Locale.getDefault()).format(new Date());;
            myRef_constant_bpm = FirebaseDatabase.getInstance().getReference().child("bpm").child("constant");
            myRef_bpm = FirebaseDatabase.getInstance().getReference().child("bpm").child("chart_data").child(time_id);
            ValueEventListener eventListener_bpm = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        myRef_bpm.child("value").setValue(number);
                        //  myRef.child("userid").setValue(userid);
                        // myRef.child("msg").setValue(msg_str);
                        myRef_constant_bpm.child("value").setValue(number);
                        myRef_bpm.child("time").setValue(time_id);
                        //  myRef.child("date").setValue(date_str);
                    }else{
                        myRef_constant_bpm.child("value").setValue(number);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            myRef_bpm.addListenerForSingleValueEvent(eventListener_bpm);

        }

    }
    public void oxy_range(int min,int max,boolean c){
        if(c){
            int number = ThreadLocalRandom.current().nextInt(min, max + 1);
            String time_id= new SimpleDateFormat("ddHHmmss", Locale.getDefault()).format(new Date());
            myRef_constant_spo2 = FirebaseDatabase.getInstance().getReference().child("oxymeter").child("constant");
            myRef_spo2 = FirebaseDatabase.getInstance().getReference().child("oxymeter").child("chart_data").child(time_id);
            ValueEventListener eventListener_spo2 = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        myRef_spo2.child("value").setValue(number);
                        myRef_constant_spo2.child("value").setValue(number);

                        //  myRef.child("userid").setValue(userid);
                        // myRef.child("msg").setValue(msg_str);
                        myRef_spo2.child("time").setValue(time_id);
                        //  myRef.child("date").setValue(date_str);
                    }else{
                        myRef_constant_spo2.child("value").setValue(number);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            myRef_spo2.addListenerForSingleValueEvent(eventListener_spo2);

        }

    }
    public void show_data(String meter){
        reference_getdata_temp = FirebaseDatabase.getInstance().getReference().child(meter).child("constant");
        reference_getdata_temp.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    live_value = Integer.parseInt(snapshot.getValue().toString());
                    if(live_value<36&&live_value!=0){
                        temp_txt.setText(String.valueOf(live_value)+"°C");
                        temp_msg.setText("Temperature is abnormally low, please consult a doctor");
                        temp_msg.setTextColor(Color.parseColor("#ED4C51"));

                    }else if(live_value>=36&&live_value<=38) {
                        temp_txt.setText(String.valueOf(live_value)+"°C");
                        temp_msg.setText("You are Healthy");
                        temp_msg.setTextColor(Color.parseColor("#BDF951"));
                    }else if(live_value>38){
                        temp_txt.setText(String.valueOf(live_value)+"°C");
                        temp_msg.setText("Temperature is abnormally high, please consult a doctor");
                        temp_msg.setTextColor(Color.parseColor("#ED4C51"));
                    }else if(live_value==0){
                        temp_txt.setText("");
                        temp_msg.setText("Unable to detect");
                        temp_msg.setTextColor(Color.parseColor("#FFFFFF"));
                    }






                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void show_data_bpm(String meter){
        reference_getdata_bpm = FirebaseDatabase.getInstance().getReference().child(meter).child("constant");
        reference_getdata_bpm.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    live_value_bpm = Integer.parseInt(snapshot.getValue().toString());
                   // heart_txt.setText(String.valueOf(live_value_bpm)+"");
                    if(live_value_bpm<60&&live_value_bpm!=0){
                        heart_txt.setText(String.valueOf(live_value_bpm));
                        bpm_msg.setText("Your Heart beat is very Low");
                        bpm_msg.setTextColor(Color.parseColor("#ED4C51"));

                    }else if(live_value_bpm>=60&&live_value_bpm<=80) {
                        heart_txt.setText(String.valueOf(live_value_bpm));
                        bpm_msg.setText("You are Healthy");
                        bpm_msg.setTextColor(Color.parseColor("#BDF951"));
                    }else if(live_value_bpm>80){
                        heart_txt.setText(String.valueOf(live_value_bpm));
                        bpm_msg.setText("Your Heart beat is very High");
                        bpm_msg.setTextColor(Color.parseColor("#ED4C51"));
                    }else if(live_value_bpm==0){
                        heart_txt.setText("");
                        bpm_msg.setText("Unable to detect");
                        bpm_msg.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void show_data_spo2(String meter){
        reference_getdata_spo2 = FirebaseDatabase.getInstance().getReference().child(meter).child("constant");
        reference_getdata_spo2.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    live_value_spo2 = Integer.parseInt(snapshot.getValue().toString());
                  //  oxy_txt.setText(String.valueOf(live_value_spo2)+"%");
                    if(live_value_spo2<95&&live_value_spo2!=0){
                        oxy_txt.setText(String.valueOf(live_value_spo2));
                        spo2_msg.setText("Low Oxygen level, consult a Doctor");
                        spo2_msg.setTextColor(Color.parseColor("#ED4C51"));

                    }
                    else if(live_value_spo2>=95){
                        oxy_txt.setText(String.valueOf(live_value_spo2));
                        spo2_msg.setText("Normal Oxygen Level");
                        spo2_msg.setTextColor(Color.parseColor("#BDF951"));
                    }else if(live_value_spo2==0){
                        oxy_txt.setText("");
                        spo2_msg.setText("Unable to detect");
                        spo2_msg.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        Calendar co = Calendar.getInstance();
        int timeOfDay = co.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetings.setText("Good Morning "+name);
            findViewById(R.id.back_time).setBackgroundResource(R.drawable.day);
            //  Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            //  Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            greetings.setText("Good Afternoon "+name);
            findViewById(R.id.back_time).setBackgroundResource(R.drawable.day);
        }else if(timeOfDay >= 16 ){
            //  Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            greetings.setText("Good Evening "+name);
            findViewById(R.id.back_time).setBackgroundResource(R.drawable.night);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start_temp_1="off";
                start_temp_2="off";
        start_temp_3="off";
                start_temp_4="off";
        start_bpm_1="off";
                start_bpm_2="off";
        start_bpm_3="off";
                start_bpm_4="off";
         start_spo2_1="off";
                 start_spo2_2="off";
        start_spo2_3="off";
                start_spo2_4="off";
    }
}