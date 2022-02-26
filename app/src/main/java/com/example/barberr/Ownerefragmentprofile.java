package com.example.barberr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
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
import android.widget.Toast;

import com.example.barberr.userdetails.Owner;
import com.example.barberr.userdetails.user;
import com.google.android.gms.tasks.OnSuccessListener;
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

    ImageButton logoutbtn;
    ImageView profileimg;
    private Activity activity;
    EditText editshopname,editownername,editpassword,editaddress,editmobile,editmail;
    ImageButton browsebtn,editbutton,savebutton;
    ActivityResultLauncher<String> launcher;
    ProgressDialog progressDialog;


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

        Log.d("aayege","1111112");
      mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Log.d("aayege","1111113");
//
//        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//                profileimg.setImageURI(result) ;
//            }
//        });

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
                            database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("shop_profile_pic").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // progressDialog2.dismiss();
                                    Toast.makeText(getContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            });
        });

        Log.d("aayege","1111114"+mAuth.getCurrentUser().getUid());
        database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Owner ownerdetail = snapshot.getValue(Owner.class);
                Log.d("aayege","111111");
                editshopname.setText(ownerdetail.getShop_name());
               editownername.setText(ownerdetail.getOwner_name());
               editaddress.setText(ownerdetail.getShop_address());
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
                }, 2500);   //5 seconds





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ownerefragmentprofile, container, false);
        logoutbtn= (ImageButton) view.findViewById(R.id.logoutbtnn);

        editbutton= (ImageButton) view.findViewById(R.id.editbutton);
        savebutton=(ImageButton)view.findViewById(R.id.savebtn);

        editshopname=(EditText) view.findViewById(R.id.editname);
        editownername=(EditText) view.findViewById(R.id.editownername);
        editmail=(EditText) view.findViewById(R.id.editmail);
        editaddress=(EditText) view.findViewById(R.id.editaddress);
        editmobile=(EditText) view.findViewById(R.id.editmobile);
        editpassword=(EditText) view.findViewById(R.id.editpassword);
        browsebtn=(ImageButton)view.findViewById(R.id.browsebtn);
        profileimg=(ImageView)view.findViewById(R.id.profile_image);


       // FirebaseUser user = mAuth.getCurrentUser();



//        browsebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launcher.launch("image/*");
//            }
//        });

//
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Logging Out",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
//                Intent intent = new Intent(this, LoginActivity.class);
//                intent.putExtra("finish", true); // if you are checking for this in your other Activities
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                        Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();

                startActivity(new Intent(getActivity(),Ownerlogin.class));
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("shop_name").setValue(editshopname.getText().toString());
                database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("shop_mail").setValue(editmail.getText().toString());
                database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("shop_mobile_no").setValue(editmobile.getText().toString());
                database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("shop_password").setValue(editpassword.getText().toString());
                database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("owner_name").setValue(editownername.getText().toString());
                database.getReference("Owners").child(mAuth.getCurrentUser().getUid()).child("shop_address").setValue(editaddress.getText().toString());

                Toast.makeText(getContext(),"Data Updated Successfully :)",Toast.LENGTH_SHORT).show();



                editshopname.setEnabled(false);
                editmail.setEnabled(false);
                editpassword.setEnabled(false);
                editmobile.setEnabled(false);
                editownername.setEnabled(false);
                editaddress.setEnabled(false);



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

                Toast.makeText(getContext(),"Data is in Edit Mode !!",Toast.LENGTH_SHORT).show();


            }
        });

        browsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Logging Out",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(getActivity(),Ownerlogin.class));
            }
        });

        return view;
    }
}