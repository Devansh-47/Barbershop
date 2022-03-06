package com.example.barberr;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barberr.databinding.FragmentAddServiceBinding;
import com.example.barberr.userdetails.services;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_service#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_service extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

    FragmentAddServiceBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    public add_service() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_service.
     */
    // TODO: Rename and change types and number of parameters
    public static add_service newInstance(String param1, String param2) {
        add_service fragment = new add_service();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddServiceBinding.inflate(getLayoutInflater(),container,false);
       // view=inflater.inflate(R.layout.fragment_add_service, container, false);

        view=binding.getRoot();



        binding.cancelAddservicelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ownerfragmentservices.blurr_background.setVisibility(View.GONE);
                getActivity().onBackPressed();
            }
        });

        binding.addServicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                services service=new services(binding.serviceName.getEditText().getText().toString(),binding.servicePrice.getEditText().getText().toString(),binding.serviceDescription.getEditText().getText().toString(),binding.serviceDuration.getEditText().getText().toString());
                Log.d("TAGGG",binding.serviceName.getEditText().getText().toString());

                if(!binding.serviceName.getEditText().getText().toString().equals("") && !binding.servicePrice.getEditText().getText().toString().equals("") && !binding.serviceDescription.getEditText().getText().toString().equals("") && !binding.serviceDuration.getEditText().getText().toString().equals("")) {
                    database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("services").child(binding.serviceName.getEditText().getText().toString()).setValue(service);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    if (Build.VERSION.SDK_INT >= 26) {
//                        ft.setReorderingAllowed(false);
//                    }
//                    ft.detach(new Ownerfragmentservices()).attach(new Ownerfragmentservices()).commit();
                    Toast.makeText(getContext(), "Service has been added To your Shop :)", Toast.LENGTH_LONG).show();
                    Ownerfragmentservices.blurr_background.setVisibility(View.GONE);
                    getActivity().onBackPressed();
                }else{
                    Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}