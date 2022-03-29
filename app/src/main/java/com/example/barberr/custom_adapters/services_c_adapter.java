package com.example.barberr.custom_adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberr.R;
import com.example.barberr.userdetails.services;

import java.util.ArrayList;

public class services_c_adapter extends RecyclerView.Adapter<services_c_adapter.ViewHolder> {

    private ArrayList<services> list;
    private Context context;

    public services_c_adapter(ArrayList<services> listt, Context context){
        list=listt;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(true){
     view= LayoutInflater.from(context)
            .inflate(R.layout.serviceview_in_shop_cardview, parent, false);
}else {
     view = LayoutInflater.from(context)
            .inflate(R.layout.cardview_service, parent, false);
}

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (true) {
            Log.d("ASDAA", "sizeinserv_adap ="+list.size());
          holder.getServiceinfo_for_shopcardview().setText(list.get(position).getService_name()+" . "+list.get(position).getService_duration()+"mins . "+list.get(position).getService_price()+"$ ");

        } else {
            holder.getName().setText("    " + list.get(position).getService_name() + "    ");
//        holder.getName().getPaint().setUnderlineText(true);


            Log.d("Fuck onbindname", list.get(position).getService_name());
            holder.getPrice().setText(list.get(position).getService_price() + " Rs");
            holder.getDuration().setText(list.get(position).getService_duration() + " Minutes");
            holder.getDescription().setText(list.get(position).getService_description());
        }
    }

    @Override
    public int getItemCount() {
        Log.d("Fuck size",list.size()+"");
        return list.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,price,duration,description;
        private TextView serviceinfo_for_shopcardview;

        public ViewHolder(View view) {
            super(view);

            if(true){
              serviceinfo_for_shopcardview=view.findViewById(R.id.service_info_inshopcard);

                }else {
                name = view.findViewById(R.id.cardview_name);
                price = view.findViewById(R.id.cardview_price);
                duration = view.findViewById(R.id.cardview_duration);
                description = view.findViewById(R.id.cardview_description);
            }
            // Define click listener for the ViewHolder's View

            // textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getServiceinfo_for_shopcardview() {
            return serviceinfo_for_shopcardview;
        }

        public TextView getName(){return name;}
        public TextView getPrice(){return price;}
        public TextView getDuration(){return duration;}
        public TextView getDescription(){return description;}

    }
}