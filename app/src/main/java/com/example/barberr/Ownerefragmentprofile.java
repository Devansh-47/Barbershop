package com.example.barberr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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

    ImageButton logoutbtn;
    ImageView profileimg;
    private Activity activity;
    EditText editname,editownername,editpassword,editaddress,editmobile,editmail;
    ImageButton browsebtn;
    ActivityResultLauncher<String> launcher;


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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

      mAuth = FirebaseAuth.getInstance();
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
        logoutbtn= (ImageButton) view.findViewById(R.id.logoutbtnn);

        editname=(EditText) view.findViewById(R.id.editname);
        editownername=(EditText) view.findViewById(R.id.editownername);
        editmail=(EditText) view.findViewById(R.id.editmail);
        editaddress=(EditText) view.findViewById(R.id.editaddress);
        editmobile=(EditText) view.findViewById(R.id.editmobile);
        editpassword=(EditText) view.findViewById(R.id.editpassword);
        browsebtn=(ImageButton)view.findViewById(R.id.browseimg);
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
                startActivity(new Intent(getActivity(),Ownerlogin.class));
            }
        });


        return view;
    }
}