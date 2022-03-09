package com.example.barberr.custom_adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barberr.Apphomescreen;
import com.example.barberr.custom_adapters.shop_list_adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberr.R;
import com.example.barberr.userdetails.Shop;
import com.example.barberr.userdetails.services;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class shop_list_adapter extends RecyclerView.Adapter<shop_list_adapter.ViewHolder> {

    private ArrayList<Shop> list;

    private Context context;

    public shop_list_adapter(ArrayList<Shop> listt, Context context){
        list=listt;

        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.shop_card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getShop_name().setText(""+list.get(position).getShop_name()+"");
        Log.d("Fuckshubham",list.get(position).getShop_profile_pic());
//        holder.getName().getPaint().setUnderlineText(true);
        Picasso.get().load(Uri.parse(list.get(position).getShop_profile_pic())).into(holder.getShop_image());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Apphomescreen.Loading_box.dismiss();
            }
        }, 3000);   //5 seconds


        // holder.getShop_image().setImageURI(Uri.parse(list.get(position).getShop_profile_pic()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView shop_name;
        private ImageView shop_image;

        public ViewHolder(View view) {
            super(view);

            shop_name=view.findViewById(R.id.cardview_shop_name);
            shop_image=view.findViewById(R.id.cardview_shop_image);

            // Define click listener for the ViewHolder's View

            // textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getShop_name(){return shop_name;}
        public ImageView getShop_image(){return shop_image;}


    }
}