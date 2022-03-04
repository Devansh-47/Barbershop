package com.example.barberr;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.barberr.userdetails.Shop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage Storage;
    StorageReference reference;

    Button logoutbtn,deletebtn;
    ImageView profileimg;
    private Activity activity;
    EditText editshopname,editownername,editpassword,editaddress,editmobile,editmail;
    ImageButton browsebtn,editbutton,savebutton;
    ActivityResultLauncher<String> launcher;
    ProgressDialog progressDialog;
    ProgressBar Loadimg;

    //this is for changing shops email and password

    String mail,pass;

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
        logoutbtn= (Button) view.findViewById(R.id.logoutbtn);
       deletebtn= (Button) view.findViewById(R.id.deletebtnn);

        editbutton= (ImageButton) view.findViewById(R.id.editbutton);
        savebutton=(ImageButton)view.findViewById(R.id.savebtn);

        editshopname=(EditText) view.findViewById(R.id.editname);
        editownername=(EditText) view.findViewById(R.id.editownername);
        editmail=(EditText) view.findViewById(R.id.editmail);
        editaddress=(EditText) view.findViewById(R.id.editaddress);
        editmobile=(EditText) view.findViewById(R.id.editmobile);
        editpassword=(EditText) view.findViewById(R.id.editpassword);
        browsebtn=(ImageButton)view.findViewById(R.id.browseimg);
        profileimg=(ImageView)view.findViewById(R.id.profile_image);
        profileimg.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


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
                editshopname.setText(ownerdetail.getShop_name());
                editownername.setText(ownerdetail.getOwner_name());
                editaddress.setText(ownerdetail.getShop_address());
                editaddress.setMaxLines(5);
//                editaddress.setVerticalScrollBarEnabled(true);
//                editaddress.setMovementMethod(new ScrollingMovementMethod());
//                editaddress.setScroller(new Scroller(getContext()));

                editpassword.setText(ownerdetail.getShop_password());
                editmail.setText(ownerdetail.getShop_mail());
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
                        editpassword.setEnabled(false);
                        editmobile.setEnabled(false);
                    }
                }, 500);   //5 seconds


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




        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_name").setValue(editshopname.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_mail").setValue(editmail.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_mobile_no").setValue(editmobile.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_password").setValue(editpassword.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("owner_name").setValue(editownername.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_address").setValue(editaddress.getText().toString());


                editshopname.setEnabled(false);
                editmail.setEnabled(false);
                editpassword.setEnabled(false);
                editmobile.setEnabled(false);
                editownername.setEnabled(false);
                editaddress.setEnabled(false);

                if(mail!=editmail.getText().toString()){

                    Log.d("change edittextmail", editmail.getText().toString());
                    Log.d("change beforeeditmail",mail);

                    user.updateEmail(editmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //  Log.d(TAG, "User email address updated.");
//                                            user.sendEmailVerification()
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            if (task.isSuccessful()) {
//                                                              //  Log.d(TAG, "Email sent.");
//                                                            }
//                                                        }
//                                                    });

                                    }else{
                                        Toast.makeText(getContext()," Email Updadtion Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                if(pass!=editpassword.getText().toString()){

                    Log.d("change edittextpass", editpassword.getText().toString());
                    Log.d("change beforeeditpas",pass);
                    user.updatePassword(editpassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                    }else{
                                        Toast.makeText(getContext()," password Updadtion Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                Toast.makeText(getContext(),"Profile Updated Successfully :)",Toast.LENGTH_SHORT).show();



            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editshopname.setEnabled(true);
                editmail.setEnabled(true);
                editpassword.setEnabled(true);
                editmobile.setEnabled(true);
                editownername.setEnabled(true);
                editaddress.setEnabled(true);

                mail=editmail.getText().toString();
                pass=editpassword.getText().toString();

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