package com.example.barberr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.barberr.userdetails.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {
    Button registerbtn,eyebutton;
    Button verify;
    EditText otp1,otp6,otp5,otp4,otp3,otp2;
    TextView t;
    EditText user_name,user_mail,user_mobile_no,user_password;
    Boolean flag=true;
    ProgressDialog progressDialog;
    ProgressBar Loading;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    PhoneAuthProvider mCallbacks;
    String mVerificationId;
    ImageView check;
    String otp;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    View otpinputView;
String sentotp;
String authwithmail_uid;
    String verificationId;



    // dbhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);






        Objects.requireNonNull(getSupportActionBar()).hide();
        t=findViewById(R.id.textView2);
        t.setPaintFlags(t.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        user_password=findViewById(R.id.shop_password);
        user_name=findViewById(R.id.shopmaill);
        user_mail=findViewById(R.id.shop_mail);
        user_mobile_no=findViewById(R.id.user_mobile_no);
        check=findViewById(R.id.check);

        Loading=findViewById(R.id.progress_loader);

        registerbtn=findViewById(R.id.loginbtn);
        eyebutton=findViewById(R.id.button2);

        //progress symbol
        progressDialog=new ProgressDialog(Registration.this);
        progressDialog.setTitle("Thank You For Sign-up");
        progressDialog.setMessage("We're Creating Your Account");
       // db=new dbhelper(this);
        mAuth=FirebaseAuth.getInstance();
       // mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        database=FirebaseDatabase.getInstance();

        LayoutInflater li = LayoutInflater.from(Registration.this);
        otpinputView = li.inflate(R.layout.otp_input, null);

        alertDialogBuilder = new AlertDialog.Builder(
                Registration.this);

        alertDialogBuilder
                .setCancelable(false);

        // create alert dialog


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(otpinputView);

        alertDialog = alertDialogBuilder.create();

        otp1 = (EditText) otpinputView
                .findViewById(R.id.otpinput1);
         otp6 = (EditText) otpinputView
                .findViewById(R.id.otpinput6);
        otp5 = (EditText) otpinputView
                .findViewById(R.id.otpinput5);
        otp4 = (EditText) otpinputView
                .findViewById(R.id.otpinput4);
         otp3 = (EditText) otpinputView
                .findViewById(R.id.otpinput3);
         otp2 = (EditText) otpinputView
                .findViewById(R.id.otpinput2);
         verify = (Button) otpinputView.findViewById(R.id.verify);




        eyebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // alertDialog.show();
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
                Loading.setVisibility(View.VISIBLE);

                if(user_name.getText().toString().equals("") || user_mail.getText().toString().equals("") || user_mobile_no.getText().toString().equals("") || user_password.getText().toString().equals("")) {
                 Toast.makeText(Registration.this,"enterrrr all fields",Toast.LENGTH_SHORT).show();
//
                }
                else {
                    if (user_mobile_no.getText().toString().length() != 10) {
                        Toast.makeText(Registration.this, "Invalid mobile no length", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(user_mail.getText().toString(), user_password.getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {

                                            authwithmail_uid=mAuth.getCurrentUser().getUid();


                                           // Toast.makeText(Registration.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                          //  Intent i = new Intent(Registration.this, custHomeActivity.class);
                                         //   startActivity(i);
                                          sendVerificationCode("+91"+user_mobile_no.getText().toString());

                                        } else {
                                            Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                }

                }



        });



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otp1.getText().toString().equals("")&&
                        !otp2.getText().toString().equals("")&&
                        !otp3.getText().toString().equals("")&&
                        !otp4.getText().toString().equals("")&&!otp5.getText().toString().equals("")
                &&!otp6.getText().toString().equals(""))
                {
                    otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString()
                            + otp5.getText().toString() + otp6.getText().toString();

                    //  Loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.ic_baseline_check_24));
                    alertDialog.dismiss();
                    verifyCode(otp);
                }
                else {
                    Toast.makeText(Registration.this,"Enter Full OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void signInWithCredential(PhoneAuthCredential credential) {

        Toast.makeText(Registration.this,"Ready To Go",Toast.LENGTH_SHORT).show();
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Loading.setVisibility(View.GONE);
                            check.setVisibility(View.VISIBLE);


                            user user = new user("default", user_name.getText().toString(), user_mail.getText().toString(),
                                    user_password.getText().toString(), user_mobile_no.getText().toString());

                            database.getReference().child("Users").child(authwithmail_uid).setValue(user);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // yourMethod();


                                    // Loading.setIndeterminateDrawable(AppCompatResources.getDrawable(Registration.this, R.drawable.ic_baseline_check_24));
                                    Intent i = new Intent(Registration.this, custHomeActivity.class);
                                    Log.d("piooo reg uidd mailauth",authwithmail_uid);
                                    i.putExtra("userid",authwithmail_uid);
                                    startActivity(i);
                                    finish();


                                }
                            }, 3000);


                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Log.d("piooo","failll");
                            Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        Log.d("piooo",number);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            Log.d("piooo  s",s);
             verificationId = s;
            progressDialog.dismiss();
            alertDialog.show();

            otp1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    otp2.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            otp2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    otp3.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            otp3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    otp4.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            otp4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    otp5.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            otp5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    otp6.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            Log.d("piooo  vercom","");
            progressDialog.dismiss();

            // checking if the code
            // is null or not.
            if (code != null) {

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Log.d("piooo  fail","");
            Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {


        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Log.d("piooo  cred","");
        // after getting credential we are
        // calling sign in method.
       // progressDialog.show();
        signInWithCredential(credential);
    }


}


//
//    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//            .setPhoneNumber("+91" + user_mobile_no.getText().toString())
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(Registration.this)
//            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onCodeSent(String verificationId,
//                                       PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                    super.onCodeSent(verificationId,forceResendingToken);
//                    // Save the verification id somewhere
//                    // ...
//
//                    // The corresponding whitelisted code above should be used to complete sign-in.
//
//                    // show it
//                    sentotp=verificationId;
//                    alertDialog.show();
//

//
//
//
//                    // set dialog message
//
//
//                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(sentotp, otp);
//
//                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Loading.setIndeterminateDrawable(AppCompatResources.getDrawable(Registration.this, R.drawable.ic_baseline_check_24));
//
//
//                                Toast.makeText(Registration.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Registration.this, custHomeActivity.class));
//                            }
//                            else {
//                                Toast.makeText(Registration.this, "USomething went wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//
//
//
//                }
//
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                    // Sign in with the credential
//                    //  ...
//
//                }
//
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    // ...
//                    Log.d("Taggg",""+e.getMessage());
//                    Toast.makeText(Registration.this, "wronggggggggg", Toast.LENGTH_SHORT).show();
//                }
//            })
//            .build();
//                                            PhoneAuthProvider.verifyPhoneNumber(options);