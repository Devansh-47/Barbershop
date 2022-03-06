package com.example.barberr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barberr.userdetails.Shop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ownerefragmentprofile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ownerefragmentprofile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage Storage;
    StorageReference reference;

    Button logoutbtn,deletebtn,re_authsendmailbtn;
    ImageView profileimg;
    private Activity activity;
    EditText editshopname,editownername,editpassword,editaddress,editmobile,editmail,reath_password,reauth_mail;
    ImageButton browsebtn,editbutton,savebutton,re_authcancelbtn;
    ActivityResultLauncher<String> launcher;
    ProgressDialog progressDialog;
    ProgressBar Loadimg;
    TextView changepassword;
    View re_authbox;
    AlertDialog.Builder alertDialogbuilder;
    AlertDialog resetmailbox;

    //this is for changing shops email and password

    String mail;

    public Ownerefragmentprofile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ownerefragmentprofile.
     */
    // TODO: Rename and change types and number of parameters
    public static Ownerefragmentprofile newInstance(String param1, String param2) {
        Ownerefragmentprofile fragment = new Ownerefragmentprofile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Getting Info...");
        progressDialog.setMessage("Take a Sip..");
        progressDialog.show();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


      mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//
//        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//                profileimg.setImageURI(result) ;
//            }
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ownerefragmentprofile, container, false);

        LayoutInflater li = LayoutInflater.from(getContext());
        re_authbox = li.inflate(R.layout.re_authbox, null);
        alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        alertDialogBuilder
                .setCancelable(false);
        alertDialogBuilder.setView(re_authbox);

        alertDialog = alertDialogBuilder.create();

        re_authcancelbtn = (ImageButton) re_authbox.findViewById(R.id.reauth_cancelbtn);
        re_authsendmailbtn = (Button) re_authbox.findViewById(R.id.reauth_sendmailbtn);
        reath_password = (EditText) re_authbox.findViewById(R.id.reauth_password);
        reauth_mail = (EditText) re_authbox.findViewById(R.id.reauth_mail);


        logoutbtn= (Button) view.findViewById(R.id.logoutbtn);
       deletebtn= (Button) view.findViewById(R.id.deletebtnn);

        editbutton= (ImageButton) view.findViewById(R.id.editbutton);
        savebutton=(ImageButton)view.findViewById(R.id.savebtn);

        editshopname=(EditText) view.findViewById(R.id.editname);

        editownername=(EditText) view.findViewById(R.id.editownername);
        editmail=(EditText) view.findViewById(R.id.editmail);

        editaddress=(EditText) view.findViewById(R.id.editaddress);
        editmobile=(EditText) view.findViewById(R.id.editmobile);
       // editpassword=(EditText) view.findViewById(R.id.editpassword);
        browsebtn=(ImageButton)view.findViewById(R.id.browseimg);
        profileimg=(ImageView)view.findViewById(R.id.profile_image);
        profileimg.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        changepassword=(TextView) view.findViewById(R.id.changepassword);

        Loadimg = (ProgressBar) view.findViewById(R.id.Loadimg);
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            profileimg.setImageURI(result);


            Storage=FirebaseStorage.getInstance();

            reference=Storage.getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid());
            reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_profile_pic").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // progressDialog2.dismiss();
                                    Loadimg.setVisibility(View.GONE);
                                    browsebtn.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            });
        });

//        Log.d("aayege","1111114"+mAuth.getCurrentUser().getUid());

        database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shop ownerdetail = snapshot.getValue(Shop.class);
                Log.d("aayege","111111");
                assert ownerdetail != null;
                editshopname.setText(ownerdetail.getShop_name());
                editownername.setText(ownerdetail.getOwner_name());
                editaddress.setText(ownerdetail.getShop_address());
                editaddress.setMaxLines(5);
//                editaddress.setVerticalScrollBarEnabled(true);
//                editaddress.setMovementMethod(new ScrollingMovementMethod());
//                editaddress.setScroller(new Scroller(getContext()));

               // editpassword.setText(ownerdetail.getShop_password());
                editmail.setText(ownerdetail.getShop_mail());
                mail=editmail.getText().toString();
                Log.d("TAGGGGG",mail);
                editmobile.setText(ownerdetail.getShop_mobile_no());
                Picasso.get().load(Uri.parse(ownerdetail.getShop_profile_pic())).into(profileimg);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();



                        progressDialog.dismiss();

                        editshopname.setEnabled(false);
                        editmail.setEnabled(false);
                        editownername.setEnabled(false);
                        editaddress.setEnabled(false);
                       // editpassword.setEnabled(false);
                        editmobile.setEnabled(false);
                    }
                }, 0);   //5 seconds


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
//        database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                Shop ownerdetail = snapshot.getValue(Shop.class);
//                Log.d("aayege","111111");
//                editshopname.setText(ownerdetail.getShop_name());
//                editownername.setText(ownerdetail.getOwner_name());
//                editaddress.setText(ownerdetail.getShop_address());
//                editaddress.setMaxLines(5);
////                editaddress.setVerticalScrollBarEnabled(true);
////                editaddress.setMovementMethod(new ScrollingMovementMethod());
////                editaddress.setScroller(new Scroller(getContext()));
//
//                editpassword.setText(ownerdetail.getShop_password());
//                editmail.setText(ownerdetail.getShop_mail());
//                editmobile.setText(ownerdetail.getShop_mobile_no());
//                Picasso.get().load(Uri.parse(ownerdetail.getShop_profile_pic())).into(profileimg);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // yourMethod();
//
//
//
//                        progressDialog.dismiss();
//
//                        editshopname.setEnabled(false);
//                        editmail.setEnabled(false);
//                        editownername.setEnabled(false);
//                        editaddress.setEnabled(false);
//                        editpassword.setEnabled(false);
//                        editmobile.setEnabled(false);
//                    }
//                }, 500);   //5 seconds
//
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        re_authcancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.show();
            }
        });

        re_authsendmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reauth_mail.getText().toString().equals("") && !reath_password.getText().toString().equals("")) {
                    alertDialog.dismiss();
                    mail = reauth_mail.getText().toString();
                    String password = reath_password.getText().toString();
                    mAuth=FirebaseAuth.getInstance();


// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(mail, password);

// Prompt the user to re-provide their sign-in credentials

                    Objects.requireNonNull(mAuth.getCurrentUser()).reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {


                                        Log.d("TAGkk", "User re-authenticated.");
                                        mAuth.sendPasswordResetEmail(editmail.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("TAGkk", "Email sent.");

                                                            progressDialog.setTitle("please Sign-in again...");
                                                            progressDialog.setMessage("mail has been sent.. , checkout inbox");
                                                            progressDialog.show();

                                                            Handler handler = new Handler();
                                                            handler.postDelayed(new Runnable() {
                                                                public void run() {
                                                                    // yourMethod();

                                                                    progressDialog.dismiss();
                                                                    mAuth.signOut();
                                                                    startActivity(new Intent(getContext(), Ownerlogin.class));
                                                                    getActivity().finish();

                                                                }
                                                            }, 4000);   //5 seconds


                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getContext(), "Invalid Credentials!!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                } else {
                    Toast.makeText(getContext(), "Enter all Fields!!", Toast.LENGTH_LONG).show();

                }
            }
        });




        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_name").setValue(editshopname.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_mail").setValue(editmail.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_mobile_no").setValue(editmobile.getText().toString());
               // database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_password").setValue(editpassword.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("owner_name").setValue(editownername.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_address").setValue(editaddress.getText().toString());


                editshopname.setEnabled(false);
                editmail.setEnabled(false);
               // editpassword.setEnabled(false);
                editmobile.setEnabled(false);
                editownername.setEnabled(false);
                editaddress.setEnabled(false);

                FirebaseUser user = mAuth.getCurrentUser();

                if(!mail.equals(editmail.getText().toString())){


                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(editmail.getText().toString()).matches()) {

                        assert user != null;

                        Log.d("TAGkk editmail", editmail.getText().toString());
                        Log.d("TAGkk mail",mail);


                        alertDialogbuilder = new AlertDialog.Builder(getContext())
                                .setTitle("Reset Email")
                                .setMessage("Are you sure you want to Update your E-mail Address ? Press Ok if yes ")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(),"Profile Updated Successfully :)",Toast.LENGTH_SHORT).show();

                                        user.updateEmail(editmail.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("user_mail").setValue(editmail.getText().toString());
                                                            mAuth.signOut();
                                                            startActivity(new Intent(getContext(), Ownerlogin.class));
                                                            Toast.makeText(getContext(), "Sign-in Again", Toast.LENGTH_LONG).show();
                                                            getActivity().finish();
                                                        }
                                                    }
                                                });


                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        resetmailbox.dismiss();
                                        editmail.setText(mail);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        resetmailbox=alertDialogbuilder.create();
                        resetmailbox.show();
                    }else {
                        Toast.makeText(getContext(), " Email Format is Invalid", Toast.LENGTH_SHORT).show();
                        editmail.setText(mail);

                    }

                }
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editshopname.setEnabled(true);
                editmail.setEnabled(true);
               // editpassword.setEnabled(true);
                editmobile.setEnabled(true);
                editownername.setEnabled(true);
                editaddress.setEnabled(true);

                mail=editmail.getText().toString();
//                pass=editpassword.getText().toString();

                Toast.makeText(getContext(),"Data is in Edit Mode !!",Toast.LENGTH_SHORT).show();


            }
        });

        browsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
                browsebtn.setVisibility(View.INVISIBLE);
                Loadimg.setVisibility(View.VISIBLE);
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Logging Out",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(getActivity(),Ownerlogin.class));
                getActivity().finish();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Deleting Your Account ");
                progressDialog.setMessage("Thank You for Visit");
                progressDialog.show();

                database.getReference().child("Shops").child(mAuth.getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Account Deleted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getActivity(), Ownerlogin.class));
                                        }
                                    }
                                });

                    }
                });
            }
        });



        return view;
    }


}