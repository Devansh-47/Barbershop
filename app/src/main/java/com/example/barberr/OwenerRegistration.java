package com.example.barberr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class OwenerRegistration extends AppCompatActivity {
    Button button2;
    Button button;
    TextView t;
    //dbhelperforowner db;
    EditText password,shopname,shopmail;
    Boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owener_registration);
        Objects.requireNonNull(getSupportActionBar()).hide();
        t=findViewById(R.id.textView2);
        t.setPaintFlags(t.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        password=findViewById(R.id.user_password);
        shopname=findViewById(R.id.user_email);
        shopmail=findViewById(R.id.user_mail);

        button2=findViewById(R.id.button2);

        button=findViewById(R.id.loginbtn);

        //db=new dbhelperforowner(this);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    password.setTransformationMethod( HideReturnsTransformationMethod.getInstance());
                    flag=!flag;
                }else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=!flag;
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(shopname.getText().toString().equals("") || shopmail.getText().toString().equals("")  || password.getText().toString().equals("")){
                    Toast.makeText(OwenerRegistration.this,"enterrrr all fields",Toast.LENGTH_SHORT).show();
//                }else{
//                    if(db.checkshopname(shopname.getText().toString())){
//                        Toast.makeText(OwenerRegistration.this,"shopname already exists!!!",Toast.LENGTH_SHORT).show();
//
//                    }
//                    else{
//                        Boolean insert=db.insertData(shopname.getText().toString(), password.getText().toString(),shopmail.getText().toString());
//                        if(insert){
//
//                            Toast.makeText(OwenerRegistration.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
//                           startActivity(new Intent(OwenerRegistration.this,OwnerHomeActivity.class));
//
//                        }else{
//                            Toast.makeText(OwenerRegistration.this,"Registration Failed!!!",Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }

            }
        });
    }
}