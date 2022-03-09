package com.example.barberr;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barberr.custom_adapters.shop_list_adapter;
import com.example.barberr.userdetails.Shop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Apphomescreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Apphomescreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    FirebaseDatabase database;
    FirebaseAuth mAuth;
    RecyclerView shop_list_recyclerview;
    public static ProgressDialog Loading_box;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Apphomescreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Apphomescreen.
     */
    // TODO: Rename and change types and number of parameters
    public static Apphomescreen newInstance(String param1, String param2) {
        Apphomescreen fragment = new Apphomescreen();
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

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_apphomescreen, container, false);

        shop_list_recyclerview=view.findViewById(R.id.shop_list);

        Loading_box=new ProgressDialog(getContext());
        Loading_box.setTitle("Loading Shops");
        Loading_box.setMessage("Wait A Moment");
        Loading_box.show();

        ArrayList<Shop> list=new ArrayList<>();

        database.getReference("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Shop shop = postSnapshot.child("shop_details").getValue(Shop.class);
                    Log.d("Fuckshubham",shop.getShop_name());
                    list.add(shop);
//
                }
                shop_list_adapter adapter=new shop_list_adapter(list,getContext());
                shop_list_recyclerview.setAdapter(adapter);
                shop_list_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));





//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // yourMethod();
//
//                    }
//                }, 0);   //5 seconds


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  view;
    }
}