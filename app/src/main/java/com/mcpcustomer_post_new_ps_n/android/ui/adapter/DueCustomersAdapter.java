package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueCustomersResponse;

import java.util.ArrayList;

public class DueCustomersAdapter extends RecyclerView.Adapter<DueCustomersAdapter.DueCustomerVH> {

    ArrayList<DueCustomersResponse> dueCustomersResponses = new ArrayList<>();
    Activity activity;
    DueCustomerClickListener dueCustomerClickListener;
    String type;

    public DueCustomersAdapter(ArrayList<DueCustomersResponse> dueCustomersResponses, Activity activity, DueCustomerClickListener dueCustomerClickListener,String type) {
        this.dueCustomersResponses = dueCustomersResponses;
        this.activity = activity;
        this.dueCustomerClickListener = dueCustomerClickListener;
        this.type = type;
    }

    @NonNull
    @Override
    public DueCustomerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DueCustomerVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.due_customers_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DueCustomerVH holder, int position) {

        DueCustomersResponse item = dueCustomersResponses.get(position);

        holder.customerNameTVID.setText(item.getCustomer_name());

        if (type.equalsIgnoreCase("1")){
            holder.whatsAppCallRLID.setVisibility(View.VISIBLE);
            holder.callRLID.setVisibility(View.VISIBLE);
        }else {
            holder.whatsAppCallRLID.setVisibility(View.GONE);
            holder.callRLID.setVisibility(View.GONE);
        }

        holder.whatsAppCallRLID.setOnClickListener(v -> dueCustomerClickListener.onDueCustomerItemClick(item,v,position,holder));
        holder.callRLID.setOnClickListener(v -> dueCustomerClickListener.onDueCustomerItemClick(item,v,position,holder));


    }

    @Override
    public int getItemCount() {
        return dueCustomersResponses.size();
    }

    public class DueCustomerVH extends RecyclerView.ViewHolder{

        AppCompatTextView customerNameTVID;
        RelativeLayout whatsAppCallRLID,callRLID;

        public DueCustomerVH(@NonNull View itemView) {
            super(itemView);

            customerNameTVID = itemView.findViewById(R.id.customerNameTVID);
            whatsAppCallRLID = itemView.findViewById(R.id.whatsAppCallRLID);
            callRLID = itemView.findViewById(R.id.callRLID);
        }
    }

    public interface DueCustomerClickListener {
        void onDueCustomerItemClick(DueCustomersResponse response, View v, int pos, DueCustomerVH holder);
    }
}
