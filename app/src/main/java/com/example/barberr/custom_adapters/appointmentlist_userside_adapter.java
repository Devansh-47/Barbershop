package com.example.barberr.custom_adapters;

        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.barberr.R;
        import com.example.barberr.userdetails.appointment_in_userside;
        import com.example.barberr.userdetails.services;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

public class appointmentlist_userside_adapter extends RecyclerView.Adapter<appointmentlist_userside_adapter.ViewHolder> {

    private static ArrayList<appointment_in_userside> list;
    private Context context;


    public appointmentlist_userside_adapter(ArrayList<appointment_in_userside> listt, Context context){

        list=listt;
        Log.d("ASDAA", "sizeinserv_adap ="+list.size());
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context)
                .inflate(R.layout.appointment_list_of_cust, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getShopname().setText(list.get(position).getShopname());
        holder.getApmt_date().setText(list.get(position).getAppointment_date());
        Log.d("QWWQ","size="+list.get(position).getServices());
        holder.getServices().setText(list.get(position).getServices());
        holder.getSlot().setText(list.get(position).getSlot());
        holder.getAmount().setText(list.get(position).getAmount());

        holder.getcancelBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("appointments").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot datasnapshot: snapshot.getChildren()
                             ) {
                            if(datasnapshot.child("shop_name").getValue(String.class).equals(list.get(holder.getAdapterPosition()).getShopname())){
                                Log.d("CHHH","Users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+datasnapshot.getKey()+"/appointment_dates/"+list.get(holder.getAdapterPosition()).getAppointment_date());
                                final String apmt_date=list.get(holder.getAdapterPosition()).getAppointment_date();
                                final String shopname=list.get(holder.getAdapterPosition()).getShopname();
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("appointments").child(datasnapshot.getKey()).child("appointment_dates").child(list.get(holder.getAdapterPosition()).getAppointment_date()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if(error!=null){
                                            Log.d("Firebase", "Error deleting node"+ error.getMessage());
                                        }else {

                                            FirebaseDatabase.getInstance().getReference("Shops").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot datasnapshot: snapshot.getChildren()
                                                    ) {

                                                        if(datasnapshot.child("shop_details").child("shop_name").getValue(String.class).equals(shopname)){

                                                            FirebaseDatabase.getInstance().getReference("Shops").child(datasnapshot.getKey()).child("appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(apmt_date).removeValue(new DatabaseReference.CompletionListener() {
                                                                @Override
                                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                                    if(error!=null){
                                                                        Log.d("Firebase", "Error deleting node"+ error.getMessage());
                                                                    }else {
                                                                        Toast.makeText(view.getContext(), "Your Appointment with "+shopname+" ha been Cancelled",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    //  Toast.makeText(view.getContext(), "Your Appointment With "+list.get(holder.getAdapterPosition()).getShopname()+" has been cancelled!!",Toast.LENGTH_SHORT).show();


                                                                }
                                                            });
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    throw error.toException();
                                                }
                                            });

                                        }
                                      //  Toast.makeText(view.getContext(), "Your Appointment With "+list.get(holder.getAdapterPosition()).getShopname()+" has been cancelled!!",Toast.LENGTH_SHORT).show();


                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });

            }
        });
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
        private TextView shopname,apmt_date,services,slot,amount;
        private Button cancel_appointment_btn;

        public ViewHolder(View view) {
            super(view);
           shopname=view.findViewById(R.id.shopname_apmt_userside);
           apmt_date=view.findViewById(R.id.apmt_date_userside);
           services=view.findViewById(R.id.services_apmt_userside);
           slot=view.findViewById(R.id.slot_apmt_userside);
           amount=view.findViewById(R.id.amount_apmt_userside);
           cancel_appointment_btn=view.findViewById(R.id.cancel_appointment_btn);
          // cancel_appointment_btn.setOnClickListener(this);

        }
        public Button getcancelBtn(){
            return cancel_appointment_btn;
        }
        public TextView getShopname() {
            return shopname;
        }
        public TextView getApmt_date() {
            return apmt_date;
        }
        public TextView getServices() {
            return services;
        }
        public TextView getSlot() {
            return slot;
        }
        public TextView getAmount() {
            return amount;
        }

//        @Override
//        public void onClick(View view) {
//            Toast.makeText(view.getContext(), "clickedddd "+list.get(getAdapterPosition()).getShopname(),Toast.LENGTH_SHORT).show();
//            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("appointments").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot datasnapshot: snapshot.getChildren()
//                    ) {
//                        if(datasnapshot.child("shop_name").getValue(String.class).equals(list.get(getAdapterPosition()).getShopname())){
//                            Log.d("CHHH","Users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+datasnapshot.getKey()+"/appointment_dates/"+list.get(getAdapterPosition()).getAppointment_date());
//                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(datasnapshot.getKey()).child("appointment_dates").child(list.get(getAdapterPosition()).getAppointment_date()).removeValue(new DatabaseReference.CompletionListener() {
//                                @Override
//                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                    FirebaseDatabase.getInstance().getReference("Shops").child("appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(list.get(getAdapterPosition()).getAppointment_date()).removeValue(new DatabaseReference.CompletionListener() {
//                                        @Override
//                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                            Toast.makeText(view.getContext(), "Your Appointment With "+list.get(getAdapterPosition()).getShopname()+" has been cancelled!!",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
    }
}