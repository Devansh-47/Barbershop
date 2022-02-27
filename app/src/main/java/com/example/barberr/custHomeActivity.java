package com.example.barberr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class custHomeActivity extends AppCompatActivity {
        BottomNavigationView bottomNavigationView;
        FirebaseAuth mAuth;
        String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).hide();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fm,new Apphomescreen()).commit();
        userid=getIntent().getStringExtra("userid");


       // Log.d("piooo cust",userid);
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override



            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction;

                if (item.getItemId() == R.id.home) {
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Apphomescreen apphomescreen=new Apphomescreen();
//                    apphomescreen.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fm,apphomescreen).commit();
                }
                if (item.getItemId() == R.id.appointments) {
                   fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fm,new Appointmentscreen()).commit();
                }
                if (item.getItemId() == R.id.profile) {
                   fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Profilescreen profilescreen=new Profilescreen();
                    profilescreen.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fm,profilescreen).commit();
                }

                return true;
            }
        });
    }


}