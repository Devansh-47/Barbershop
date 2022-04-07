package com.example.barberr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.barberr.userdetails.services;

import java.util.ArrayList;
import java.util.Objects;

public class select_date_and_time_activity extends AppCompatActivity {
    TextView service_info_dateactivity;
    CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_and_time);
        Objects.requireNonNull(getSupportActionBar()).hide();

        calendar=findViewById(R.id.calendarView);
        service_info_dateactivity=findViewById(R.id.service_info_dateactivity);

        calendar.setMinDate(calendar.getDate());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //i=year,i1=month,i2=day
                service_info_dateactivity.setText(i+":"+i1+":"+i2);
            }
        });

        String shop_id=getIntent().getStringExtra("shop_id");
        ArrayList<String> a = getIntent().getStringArrayListExtra("selected_services");
String info="";
        for(int i=0;i<a.size();i++){
            info=info+"\n"+a.get(i);
        }

    }


}