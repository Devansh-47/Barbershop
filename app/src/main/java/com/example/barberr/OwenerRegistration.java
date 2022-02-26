package com.example.barberr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barberr.userdetails.Owner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class OwenerRegistration extends AppCompatActivity {
    Button button2;
    Button button;
    TextView t;
    //dbhelperforowner db;
    EditText password,shopname,shopmail,ownername,shopnumber,shopaddress;
    Boolean flag=true;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owener_registration);
        Objects.requireNonNull(getSupportActionBar()).hide();
        t=findViewById(R.id.textView2);
        t.setPaintFlags(t.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        password=findViewById(R.id.shop_password);
        shopname=findViewById(R.id.shopmaill);
        ownername=findViewById(R.id.ownername);
        shopmail=findViewById(R.id.shop_mail);
        shopaddress=findViewById(R.id.shop_address);
        shopnumber=findViewById(R.id.shopmobile);

        button2=findViewById(R.id.button2);

        button=findViewById(R.id.loginbtn);

        //progress symbol
        progressDialog=new ProgressDialog(OwenerRegistration.this);
        progressDialog.setTitle("Thank You For Sign-up");
        progressDialog.setMessage("We're Creating Your Account");
        // db=new dbhelper(this);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

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
                else {

                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(shopmail.getText().toString(), password.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {

                                        String id = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();

                                        Owner owner = new Owner("default", shopname.getText().toString(),ownername.getText().toString(), shopmail.getText().toString(),
                                                password.getText().toString(), shopnumber.getText().toString(),shopaddress.getText().toString());
                                        database.getReference().child("Owners").child(id).setValue(owner);

                                        Toast.makeText(OwenerRegistration.this, "Shop Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(OwenerRegistration.this, OwnerHomeActivity.class));

                                    } else {
                                        Toast.makeText(OwenerRegistration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }

            }
        });
    }
}