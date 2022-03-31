package com.example.barberr;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link showing_listofservices_for_shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class showing_listofservices_for_shop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public showing_listofservices_for_shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment showing_listofservices_for_shop.
     */
    // TODO: Rename and change types and number of parameters
    public static showing_listofservices_for_shop newInstance(String param1, String param2) {
        showing_listofservices_for_shop fragment = new showing_listofservices_for_shop();
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

        String sa=getArguments().getString("ID");
        Log.d("ASAAa","sasasas==="+sa);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView service,reviews,about,filltab;
        ImageButton empty_heart,fill_heart;

        View view;
        view=inflater.inflate(R.layout.fragment_showing_listofservices_for_shop, container, false);
        service=view.findViewById(R.id.service_tab);
        reviews=view.findViewById(R.id.reviews_tab);
        about=view.findViewById(R.id.about_tab);
        filltab=view.findViewById(R.id.filltab);
        empty_heart=view.findViewById(R.id.empty_heart);
        fill_heart=view.findViewById(R.id.fill_heart);

        empty_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_heart.setVisibility(View.GONE);
                fill_heart.setVisibility(View.VISIBLE);
            }
        });
        fill_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_heart.setVisibility(View.VISIBLE);
                fill_heart.setVisibility(View.GONE);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filltab.setWidth(view.getWidth());
                filltab.setHeight(view.getHeight());
                filltab.animate().x(0).setDuration(100);
                filltab.setVisibility(View.VISIBLE);
                filltab.setText("Services");
                filltab.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filltab.setWidth(view.getWidth());
                filltab.setHeight(view.getHeight());
                filltab.animate().x(view.getWidth()).setDuration(100);
                filltab.setVisibility(View.VISIBLE);
                filltab.setText("Reviews");
                filltab.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filltab.setWidth(view.getWidth());
                filltab.setHeight(view.getHeight());
                filltab.animate().x(view.getWidth()*2).setDuration(100);
                filltab.setVisibility(View.VISIBLE);
                filltab.setText("About");
                filltab.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}