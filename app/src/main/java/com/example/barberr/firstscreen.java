package com.example.barberr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class firstscreen extends AppCompatActivity {
    ImageButton customerbtn,ownerbtn;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);

        Objects.requireNonNull(getSupportActionBar()).hide();
        customerbtn=findViewById(R.id.customerbtn);
        ownerbtn=findViewById(R.id.ownerbtn);
        t=findViewById(R.id.textView2);
        t.setPaintFlags(t.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        customerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(firstscreen.this, Login.class));
            }
        });

        ownerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(firstscreen.this,Ownerlogin.class));
            }
        });
    }
}