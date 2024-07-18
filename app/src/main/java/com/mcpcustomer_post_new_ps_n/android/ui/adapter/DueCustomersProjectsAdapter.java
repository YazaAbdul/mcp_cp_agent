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
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueCustomersMainResponse;

import java.util.ArrayList;

public class DueCustomersProjectsAdapter extends RecyclerView.Adapter<DueCustomersProjectsAdapter.DueCustomerProjectVH> {

    ArrayList<DueCustomersMainResponse> dueCustomersMainResponses = new ArrayList<>();
    Activity activity;
    DueCustomerProjectClickListener dueCustomerProjectClickListener;

    public DueCustomersProjectsAdapter(ArrayList<DueCustomersMainResponse> dueCustomersMainResponses, Activity activity, DueCustomerProjectClickListener dueCustomerProjectClickListener) {
        this.dueCustomersMainResponses = dueCustomersMainResponses;
        this.activity = activity;
        this.dueCustomerProjectClickListener = dueCustomerProjectClickListener;
    }

    @NonNull
    @Override
    public DueCustomerProjectVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DueCustomerProjectVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.due_customer_project_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DueCustomerProjectVH holder, int position) {

        DueCustomersMainResponse item = dueCustomersMainResponses.get(position);

        holder.projectNameTVID.setText(dueCustomersMainResponses.get(position).getProject_name());
        holder.projectRLID.setOnClickListener(v -> dueCustomerProjectClickListener.onDueCustomerProjectItemClick(item,v,position,holder));

    }

    @Override
    public int getItemCount() {
        return dueCustomersMainResponses.size();
    }

    public class DueCustomerProjectVH extends RecyclerView.ViewHolder{

        RelativeLayout projectRLID;
        AppCompatTextView projectNameTVID;

        public DueCustomerProjectVH(@NonNull View itemView) {
            super(itemView);

            projectNameTVID = itemView.findViewById(R.id.projectNameTVID);
            projectRLID = itemView.findViewById(R.id.projectRLID);
        }
    }

    public interface DueCustomerProjectClickListener {
        void onDueCustomerProjectItemClick(DueCustomersMainResponse response, View v, int pos, DueCustomerProjectVH holder);
    }
}
