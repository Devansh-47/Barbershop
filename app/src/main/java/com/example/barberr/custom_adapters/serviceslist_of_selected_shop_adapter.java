package com.example.barberr.custom_adapters;

        import android.content.Context;
        import android.graphics.Color;
        import android.util.Log;
        import android.util.TypedValue;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.barberr.R;
        import com.example.barberr.Serviceslist_of_selected_shop;
        import com.example.barberr.userdetails.services;
        import com.ms.square.android.expandabletextview.ExpandableTextView;

        import java.util.ArrayList;

public class serviceslist_of_selected_shop_adapter extends RecyclerView.Adapter<serviceslist_of_selected_shop_adapter.ViewHolder> {

    public static ArrayList<services> list;
    public static ArrayList<String> a2=new ArrayList<>();
    public static Context context;
    public static int pos;


    public serviceslist_of_selected_shop_adapter(ArrayList<services> listt, Context context){

        list=listt;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context)
                .inflate(R.layout.service_view_of_selectedshop, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    pos=holder.getAdapterPosition();
       holder.getShopname().setText(list.get(position).getService_name());
       holder.getPrice_and_duration().setText(list.get(position).getService_price()+"$ and up to "+list.get(position).getService_duration()+"mins ");
       holder.getExpandable_description().setText(list.get(position).getService_description());

        holder.getService_checkbox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.getService_checkbox().isChecked()){
                    a2.add(holder.getShopname().getText().toString());
                }
                else {
                    a2.remove(holder.getShopname().getText().toString());
                }

                Log.d("CGHE","sizeee="+a2.size()+"added="+holder.getShopname().getText().toString());
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
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView shopname,price_and_duration,expandable_description;
        private CheckBox service_checkbox;

        public ViewHolder(View view) {
            super(view);
            shopname=view.findViewById(R.id.shopname_of_selected_shop);
            expandable_description=view.findViewById(R.id.expand_description_selected_shop);
            price_and_duration=view.findViewById(R.id.price_duration_of_selectedshop);
            service_checkbox=view.findViewById(R.id.service_checkbox);
//            expandable_description.setOnClickListener(this);
        }

        public TextView getShopname() {
            return shopname;
        }

        public TextView getPrice_and_duration() {
            return price_and_duration;
        }

        public TextView getExpandable_description() {
            return expandable_description;
        }

        public CheckBox getService_checkbox() {
            return service_checkbox;
        }

        @Override
        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.expand_description_selected_shop:
//
//
//                    float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
//                    int sp= (int) ((int)expandable_description.getTextSize()/scaledDensity);
//                    Log.d("AWAWA",sp+"");
//                    if(expandable_description.getTextSize()/scaledDensity==14){
//                        expandable_description.setText(list.get(pos).getService_description());
//                        expandable_description.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24,0);
//                        expandable_description.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
//                    }else {
//                        expandable_description.setText(list.get(pos).getService_description().substring(0,list.get(pos).getService_description().length()/3)+"...");
//                        expandable_description.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.down_arrow,0);
//                        expandable_description.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//                    }
//
//
//                    break;
//
//                default:
//                    break;
//            }
        }
    }
}