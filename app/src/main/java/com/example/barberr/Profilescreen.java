package com.example.barberr;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.barberr.userdetails.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button logoutbtn;
    ImageView profileimg;
    private Activity activity;
    EditText editname,editpassword,editmobile,editmail;
    ImageButton browsebtn;
    ActivityResultLauncher<String> launcher;

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
        // logoutbtn.setBackgroundColor(16711680);

        if (getArguments() != null) {


            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
        mAuth = FirebaseAuth.getInstance();
        
        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                   profileimg.setImageURI(result) ;
                    }
                });


//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(activity, Login.class);
////                activity.startActivity(intent);
////                activity.finish();
//
//            }
//        });
    }



//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_profilescreen, container, false);
       logoutbtn= (Button) view.findViewById(R.id.logoutbtn);

       editname=(EditText) view.findViewById(R.id.editname);
        editmail=(EditText) view.findViewById(R.id.editmail);
        editmobile=(EditText) view.findViewById(R.id.editmobile);
        editpassword=(EditText) view.findViewById(R.id.editpassword);
        browsebtn=(ImageButton)view.findViewById(R.id.browseimg);
        profileimg=(ImageView)view.findViewById(R.id.profile_image);


        FirebaseUser user = mAuth.getCurrentUser();

      Log.d("lllllllllllllllllllllll",""+(mAuth.getCurrentUser()).getUid());








//      String s="Users/"+mAuth.getCurrentUser().getUid();
//        Log.d("lllllllllllllllllllllll",s);
//        DatabaseReference ref = database.getReference(s);
//
//// Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                user post = dataSnapshot.getValue(user.class);
//                assert post != null;
//                editname.setText(post.getUser_name());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });


//        database.getReference("Users").child(mAuth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                user u=snapshot.getValue(user.class);
//                Log.d("lllllllllllllllllllllll",u.getUser_name());
//
//                editname.setText(u.getUser_name());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        database.getReference().child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(task.isSuccessful()){
//                    if(task.getResult().exists()){
//                        DataSnapshot dataSnapshot=task.getResult();
//
//                        String name=String.valueOf(dataSnapshot.child("user_name").getValue());
//                        editname.setText(name);
//                        Toast.makeText(getActivity(),"Read SuccessFully",Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(getActivity(),"User does not exist",Toast.LENGTH_SHORT).show();
//                    }
//
//                }else {
//                    Toast.makeText(getActivity(),"Read Failedd",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user post = dataSnapshot.getValue(user.class);
//                editname.setText(post.getUser_name());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });


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
                startActivity(new Intent(getActivity(),Login.class));
            }
        });
         return view;
    }
}
































//        logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                startActivity(new Intent(getActivity(),Login.class));
//            }
//        });



//     final FirebaseDatabase database = FirebaseDatabase.getInstance();

//        DatabaseReference ref = database.getReference();
//        ref.child("users").child(mAuth.getCurrentUser().getUid());
//        editname=getView().findViewById(R.id.editname);
//        editpassword=getView().findViewById(R.id.editpassword);

// Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user user = dataSnapshot.getValue(user.class);
//
//                editname.setText(user.getUser_name());
//                editpassword.setText(user.getUser_password());
//
//                System.out.println(user);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });




//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.upperrightmenulogout,menu);
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//int id=item.getItemId();
//
//    if(id==R.id.Help){
//        Toast.makeText(getActivity(),"help activity",Toast.LENGTH_SHORT).show();
//    }
//    if(id==R.id.logout){
//        mAuth.signOut();
//        startActivity(new Intent(getActivity(),Login.class));
//    }
//    return super.onOptionsItemSelected(item);
//    }