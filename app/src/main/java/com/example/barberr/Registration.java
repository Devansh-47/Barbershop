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
import com.example.barberr.userdetails.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {
    Button registerbtn,eyebutton;
    TextView t;
    EditText user_name,user_mail,user_mobile_no,user_password;
    Boolean flag=true;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;



    // dbhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Objects.requireNonNull(getSupportActionBar()).hide();
        t=findViewById(R.id.textView2);
        t.setPaintFlags(t.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        user_password=findViewById(R.id.user_password);
        user_name=findViewById(R.id.user_email);
        user_mail=findViewById(R.id.user_mail);
        user_mobile_no=findViewById(R.id.user_mobile_no);


        registerbtn=findViewById(R.id.loginbtn);
        eyebutton=findViewById(R.id.button2);

        //progress symbol
        progressDialog=new ProgressDialog(Registration.this);
        progressDialog.setTitle("Thank You For Sign-up");
        progressDialog.setMessage("We're Creating Your Account");
       // db=new dbhelper(this);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        eyebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    user_password.setTransformationMethod( HideReturnsTransformationMethod.getInstance());
                    flag=!flag;
                }else{
                    user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=!flag;
                }
            }
        });


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_name.getText().toString().equals("") || user_mail.getText().toString().equals("") || user_mobile_no.getText().toString().equals("") || user_password.getText().toString().equals("")) {
                 Toast.makeText(Registration.this,"enterrrr all fields",Toast.LENGTH_SHORT).show();
//
                }
                else {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(user_mail.getText().toString(), user_password.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {

                                        String id = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();

                                        user user = new user("default", user_name.getText().toString(), user_mail.getText().toString(), user_password.getText().toString(), user_mobile_no.getText().toString());
                                        database.getReference().child("Users").child(id).setValue(user);

                                        Toast.makeText(Registration.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Registration.this, custHomeActivity.class));

                                    } else {
                                        Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }

//                mAuth.createUserWithEmailAndPassword(user_mail.getText().toString(), user_password.getText().toString())
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("TAG", "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
//
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(Registration.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                   // updateUI(null);
//                                }
//                            }
//                        });


//               if(username.getText().toString().equals("") || user_mail.getText().toString().equals("") || user_mobile.getText().toString().equals("") || user_password.getText().toString().equals("")){
//                   Toast.makeText(Registration.this,"enterrrr all fields",Toast.LENGTH_SHORT).show();
//               }else{
//                   if(db.checkusername(username.getText().toString())){
//                       Toast.makeText(Registration.this,"username already exists!!!",Toast.LENGTH_SHORT).show();
//
//                   }
//                   else{
//                       Boolean insert=db.insertData(username.getText().toString(), user_password.getText().toString(),user_mobile.getText().toString(),user_mail.getText().toString());
//                            if(insert){
//
//                                Toast.makeText(Registration.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Registration.this,custHomeActivity.class));
//
//                            }else{
//                                Toast.makeText(Registration.this,"Registration Failed!!!",Toast.LENGTH_SHORT).show();
//                            }
//                   }
//               }

                }

        });
    }
}