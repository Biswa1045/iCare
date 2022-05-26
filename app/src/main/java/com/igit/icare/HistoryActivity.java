package com.igit.icare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    LineChart temp_chart_history,bpm_chart_history,spo2_chart_history;
    CardView yesterday, last7days, lastmonth, lastyear;
    DatabaseReference myRef_constant_temp,myRef_constant_bpm,myRef_constant_spo2;
    LineDataSet lineDataSet_temp = new LineDataSet(null,null);
    LineDataSet lineDataSet_bpm = new LineDataSet(null,null);
    LineDataSet lineDataSet_spo2 = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets_temp = new ArrayList<>();
    ArrayList<ILineDataSet> iLineDataSets_bpm = new ArrayList<>();
    ArrayList<ILineDataSet> iLineDataSets_spo2 = new ArrayList<>();
    LineData lineData_temp;
    LineData lineData_bpm;
    LineData lineData_spo2;
    String name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        yesterday = findViewById(R.id.yesterday);
        last7days = findViewById(R.id.last7days);
        lastmonth = findViewById(R.id.lastmonth);
        lastyear = findViewById(R.id.lastyear);
        temp_chart_history = findViewById(R.id.temp_chart_history);
        bpm_chart_history = findViewById(R.id.bpm_chart_history);
        spo2_chart_history = findViewById(R.id.spo2_chart_history);
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        findViewById(R.id.back_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                in.putExtra("name",name);
                in.putExtra("email",email);
                startActivity(in);
                finish();
            }
        });
        temp();
        bpm();
        spo2();
        yesterday.setCardBackgroundColor(Color.parseColor("#353666"));
        last7days.setCardBackgroundColor(Color.parseColor("#353666"));
        lastmonth.setCardBackgroundColor(Color.parseColor("#353666"));
        lastyear.setCardBackgroundColor(Color.parseColor("#353666"));
     //   temp();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(getApplicationContext(), HomeActivity.class);
        in.putExtra("name",name);
        in.putExtra("email",email);
        startActivity(in);
        finish();
    }

    public void yesterday_click(View view) {
        yesterday.setCardBackgroundColor(Color.parseColor("#FFBD26"));
        last7days.setCardBackgroundColor(Color.parseColor("#353666"));
        lastmonth.setCardBackgroundColor(Color.parseColor("#353666"));
        lastyear.setCardBackgroundColor(Color.parseColor("#353666"));


    }

    public void lastyear_click(View view) {
        yesterday.setCardBackgroundColor(Color.parseColor("#353666"));
        last7days.setCardBackgroundColor(Color.parseColor("#353666"));
        lastmonth.setCardBackgroundColor(Color.parseColor("#353666"));
        lastyear.setCardBackgroundColor(Color.parseColor("#FFBD26"));
    }

    public void lastmonth_click(View view) {
        yesterday.setCardBackgroundColor(Color.parseColor("#353666"));
        last7days.setCardBackgroundColor(Color.parseColor("#353666"));
        lastmonth.setCardBackgroundColor(Color.parseColor("#FFBD26"));
        lastyear.setCardBackgroundColor(Color.parseColor("#353666"));
    }

    public void last7days_click(View view) {
        yesterday.setCardBackgroundColor(Color.parseColor("#353666"));
        last7days.setCardBackgroundColor(Color.parseColor("#FFBD26"));
        lastmonth.setCardBackgroundColor(Color.parseColor("#353666"));
        lastyear.setCardBackgroundColor(Color.parseColor("#353666"));


    }

    private void temp() {
        myRef_constant_temp = FirebaseDatabase.getInstance().getReference().child("temp").child("chart_data");
        myRef_constant_temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Entry> data = new ArrayList<Entry>();

                if(snapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : snapshot.getChildren()){
                        try{
                            value datavalue = myDataSnapshot.getValue(value.class);
                            data.add(new Entry(Integer.parseInt(datavalue.getTime()),datavalue.getValue()));

                        }catch (Exception e){
                            //   Toast.makeText(HistoryActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        }

                    }
                    showchart_temp(data);


                }else{
                    temp_chart_history.clear();
                    temp_chart_history.invalidate();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void bpm() {
        myRef_constant_bpm = FirebaseDatabase.getInstance().getReference().child("bpm").child("chart_data");
        myRef_constant_bpm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Entry> data = new ArrayList<Entry>();

                if(snapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : snapshot.getChildren()){
                        try{
                            value datavalue = myDataSnapshot.getValue(value.class);
                            data.add(new Entry(Integer.parseInt(datavalue.getTime()),datavalue.getValue()));

                        }catch (Exception e){
                            //   Toast.makeText(HistoryActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        }

                    }
                   showchart_bpm(data);


                }else{
                    bpm_chart_history.clear();
                    bpm_chart_history.invalidate();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void spo2() {
        myRef_constant_spo2 = FirebaseDatabase.getInstance().getReference().child("oxymeter").child("chart_data");
        myRef_constant_spo2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Entry> data = new ArrayList<Entry>();

                if(snapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : snapshot.getChildren()){
                        try{
                            value datavalue = myDataSnapshot.getValue(value.class);
                            data.add(new Entry(Integer.parseInt(datavalue.getTime()),datavalue.getValue()));

                        }catch (Exception e){
                            //   Toast.makeText(HistoryActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        }

                    }
                    showchart_spo2(data);


                }else{
                    spo2_chart_history.clear();
                    spo2_chart_history.invalidate();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showchart_temp(ArrayList<Entry> datavals){
        lineDataSet_temp.setValues(datavals);
        lineDataSet_temp.setLabel("Temperature");
        lineDataSet_temp.setDrawCircleHole(false);
        lineDataSet_temp.setDrawCircles(false);
        XAxis xAxis_temp = temp_chart_history.getXAxis();
        xAxis_temp.setDrawLabels(false);
        iLineDataSets_temp.clear();
        iLineDataSets_temp.add(lineDataSet_temp);
        lineData_temp = new LineData(iLineDataSets_temp);
        temp_chart_history.clear();
        temp_chart_history.setData(lineData_temp);
        temp_chart_history.setVisibleXRangeMaximum(100);
        temp_chart_history.invalidate();
    }
    private void showchart_bpm(ArrayList<Entry> datavals){
        lineDataSet_bpm.setValues(datavals);
        lineDataSet_bpm.setLabel("Heart Beat");
        lineDataSet_bpm.setDrawCircleHole(false);
        lineDataSet_bpm.setDrawCircles(false);
        XAxis xAxis_bpm = bpm_chart_history.getXAxis();
        xAxis_bpm.setDrawLabels(false);
        iLineDataSets_bpm.clear();
        iLineDataSets_bpm.add(lineDataSet_bpm);
        lineData_bpm = new LineData(iLineDataSets_bpm);
        bpm_chart_history.clear();
        bpm_chart_history.setData(lineData_bpm);
        bpm_chart_history.setVisibleXRangeMaximum(100);
        bpm_chart_history.invalidate();
    }
    private void showchart_spo2(ArrayList<Entry> datavals){
        lineDataSet_spo2.setValues(datavals);
        lineDataSet_spo2.setLabel("Spo2");
        lineDataSet_spo2.setDrawCircleHole(false);
        lineDataSet_spo2.setDrawCircles(false);
        XAxis xAxis_spo2 = spo2_chart_history.getXAxis();
        xAxis_spo2.setDrawLabels(false);
        iLineDataSets_spo2.clear();
        iLineDataSets_spo2.add(lineDataSet_spo2);
        lineData_spo2 = new LineData(iLineDataSets_spo2);
        spo2_chart_history.clear();
        spo2_chart_history.setData(lineData_spo2);
        spo2_chart_history.setVisibleXRangeMaximum(100);
        spo2_chart_history.invalidate();
    }
}