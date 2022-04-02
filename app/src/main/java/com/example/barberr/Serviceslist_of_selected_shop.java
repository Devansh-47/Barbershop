package com.example.barberr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barberr.custom_adapters.RecyclerItemClickListener;
import com.example.barberr.custom_adapters.serviceslist_of_selected_shop_adapter;
import com.example.barberr.userdetails.services;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Serviceslist_of_selected_shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Serviceslist_of_selected_shop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView services_list_selected_shop;

    public Serviceslist_of_selected_shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Serviceslist_of_selected_shop.
     */
    // TODO: Rename and change types and number of parameters
    public static Serviceslist_of_selected_shop newInstance(String param1, String param2) {
        Serviceslist_of_selected_shop fragment = new Serviceslist_of_selected_shop();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_serviceslist_of_selected_shop, container, false);
        services_list_selected_shop = view.findViewById(R.id.serviceslist_of_selected_shop);

        ArrayList<services> al = new ArrayList<>();
        String shop_id = getArguments().getString("ID");
        FirebaseDatabase.getInstance().getReference("Shops").child(shop_id).child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()
                ) {
                    services s = datasnapshot.getValue(services.class);
                    al.add(s);
                }
                serviceslist_of_selected_shop_adapter ad = new serviceslist_of_selected_shop_adapter(al, getContext());
                services_list_selected_shop.setAdapter(ad);
                services_list_selected_shop.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        services_list_selected_shop.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), services_list_selected_shop, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                            if(view.getId()==R.id.expand_description_selected_shop){
                                Toast.makeText(getContext(), "idname=wxpandeble", Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

return view;
    }
}