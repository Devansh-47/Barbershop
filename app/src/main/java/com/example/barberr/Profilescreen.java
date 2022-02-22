package com.example.barberr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

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
import android.widget.Toast;

import com.example.barberr.userdetails.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button logoutbtn;
    private Activity activity;
    EditText editname,editpassword,editmobile,editmail;


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
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {


            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }



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