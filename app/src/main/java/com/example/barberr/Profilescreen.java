package com.example.barberr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.barberr.userdetails.user;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilescreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilescreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage Storage;
    StorageReference reference;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button logoutbtn;
    ImageView profileimg;
    private Activity activity;
    EditText editname, editpassword, editmobile, editmail;
    ImageButton browsebtn, editbutton, savebutton;
    ActivityResultLauncher<String> launcher;
    ProgressDialog progressDialog;
    ProgressBar Loadimg;

    String userid;

    public Profilescreen() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profilescreen.
     */
    // TODO: Rename and change types and number of parameters
    public static Profilescreen newInstance(String param1, String param2) {
        Profilescreen fragment = new Profilescreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Getting Info...");
        progressDialog.setMessage("Take a Sip..");
        progressDialog.show();



        if (getArguments() != null) {


            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//        progressDialog=new ProgressDialog(getContext());
//        progressDialog.setTitle("Getting Info...");
//        progressDialog.setMessage("Take a Sip..");


//        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
//            profileimg.setImageURI(result);
//
//
//            Storage=FirebaseStorage.getInstance();
//
//            reference=Storage.getReference().child("images").child(userid);
//            reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        database.getReference("Users").child(userid).child("user_profile_pic").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                               // progressDialog2.dismiss();
//                                Toast.makeText(getContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();
//                                Loadimg.setVisibility(View.INVISIBLE);
//                                browsebtn.setVisibility(View.VISIBLE);
//
//                            }
//                        });
//                    }
//                });
//                }
//            });
//        });
//        database.getReference("Users").child(userid).addValueEventListener(new ValueEventListener() {
//
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                Log.d("piooo",userid+"data:"+snapshot.getValue(user.class).getUser_name());
//
//                user userdetail = snapshot.getValue(user.class);
//                assert userdetail != null;
//                editname.setText(userdetail.getUser_name());
//                editpassword.setText(userdetail.getUser_password());
//                editmail.setText(userdetail.getUser_mail());
//                editmobile.setText(userdetail.getUser_mobile_no());
//                Picasso.get().load(Uri.parse(userdetail.getUser_profile_pic())).into(profileimg);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // yourMethod();
//
//                        progressDialog.dismiss();
//
//                        editname.setEnabled(false);
//                        editmail.setEnabled(false);
//                        editpassword.setEnabled(false);
//                        editmobile.setEnabled(false);
//                    }
//                }, 2500);   //5 seconds
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profilescreen, container, false);
        logoutbtn = (Button) view.findViewById(R.id.logoutbtn);

        editbutton = (ImageButton) view.findViewById(R.id.editbutton);
        savebutton = (ImageButton) view.findViewById(R.id.savebutton);
        editname = (EditText) view.findViewById(R.id.editname);
        editmail = (EditText) view.findViewById(R.id.editmail);
        editmobile = (EditText) view.findViewById(R.id.editmobile);
        editpassword = (EditText) view.findViewById(R.id.editpassword);
        browsebtn = (ImageButton) view.findViewById(R.id.browseimg);
        profileimg = (ImageView) view.findViewById(R.id.profile_image);
        Loadimg = (ProgressBar) view.findViewById(R.id.Loadimg);

        assert getArguments() != null;

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            profileimg.setImageURI(result);


            Storage = FirebaseStorage.getInstance();

                reference = Storage.getReference().child("images").child(mAuth.getCurrentUser().getUid());
                reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("user_profile_pic").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // progressDialog2.dismiss();
                                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        Loadimg.setVisibility(View.INVISIBLE);
                                        browsebtn.setVisibility(View.VISIBLE);

                                    }
                                });
                            }
                        });
                    }
                });

            });

          //  Log.d("piooo userid inprofile", getArguments().getString("userid"));
            Log.d("piooo mauthuse profile", mAuth.getCurrentUser().getUid());
            database.getReference("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                  //  Log.d("piooogettinfoinprofile", mAuth.getCurrentUser().getUid() + "data:" + snapshot.getValue(user.class).getUser_name());

                    user userdetail = snapshot.getValue(user.class);
                    if(userdetail!=null){
                        editname.setText(userdetail.getUser_name());
                        editpassword.setText(userdetail.getUser_password());
                        editmail.setText(userdetail.getUser_mail());
                        editmobile.setText(userdetail.getUser_mobile_no());
                        Picasso.get().load(Uri.parse(userdetail.getUser_profile_pic())).into(profileimg);
                        progressDialog.dismiss();
                        editname.setEnabled(false);
                        editmail.setEnabled(false);
                        editpassword.setEnabled(false);
                        editmobile.setEnabled(false);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // yourMethod();

                                progressDialog.dismiss();


                            }
                        }, 0);   //5 seconds

                    }else{
                        Toast.makeText(getContext(),"cant find any user",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        database.getReference("Users").child(Objects.requireNonNull(mAuth.getCurrentUser().getUid())).child("user_name").setValue(editname.getText().toString());
                        database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("user_mail").setValue(editmail.getText().toString());
                        database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("user_mobile_no").setValue(editmobile.getText().toString());
                        database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("user_password").setValue(editpassword.getText().toString());

                        Toast.makeText(getContext(), "Data Updated Successfully :)", Toast.LENGTH_SHORT).show();



                    editname.setEnabled(false);
                    editmail.setEnabled(false);
                    editpassword.setEnabled(false);
                    editmobile.setEnabled(false);


                }
            });
            editbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editname.setEnabled(true);
                    editmail.setEnabled(true);
                    editpassword.setEnabled(true);
                    editmobile.setEnabled(true);

                    Toast.makeText(getContext(), "Data is in Edit Mode !!", Toast.LENGTH_SHORT).show();


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

                    Toast.makeText(getActivity(), "Logging Out", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    startActivity(new Intent(getActivity(), Login.class));
                }
            });


            return view;

    }

}

































