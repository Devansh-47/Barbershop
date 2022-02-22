package com.example.barberr;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;

import android.content.Intent;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Ownerlogin extends AppCompatActivity {
    TextView textView,t;
    EditText password,shopname;
    Button button2,loginbtn;
    Boolean flag=true;
   // dbhelperforowner db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerlogin);

      //  db = new dbhelperforowner(this);


        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView textView = (TextView) findViewById(R.id.linkforregister);
        SpannableString content = new SpannableString(textView.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        t = findViewById(R.id.textView2);
        t.setPaintFlags(t.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Ownerlogin.this, OwenerRegistration.class));
            }
        });


        password = findViewById(R.id.user_password);
        shopname = findViewById(R.id.user_email);
        button2 = findViewById(R.id.button2);
        loginbtn = findViewById(R.id.loginbtn);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag = !flag;
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag = !flag;
                }
            }
        });



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shopname.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(Ownerlogin.this, "enterrrr all fields", Toast.LENGTH_SHORT).show();
                }
                    startActivity(new Intent(Ownerlogin.this,OwnerHomeActivity.class));
                    //                 else {
//                    Boolean checkuser = db.checkshopnamepassword(shopname.getText().toString(), password.getText().toString());
//
//                    if (checkuser) {
//                        startActivity(new Intent(Ownerlogin.this, OwnerHomeActivity.class));
//                    } else {
//                        Toast.makeText(Ownerlogin.this, "User does not Exists!!!", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
            }
        });





    }
}