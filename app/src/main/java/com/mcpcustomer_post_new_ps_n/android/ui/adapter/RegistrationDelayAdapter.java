package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewRegistrationDelayChargesResponse;

import java.util.ArrayList;

public class RegistrationDelayAdapter extends RecyclerView.Adapter<RegistrationDelayAdapter.DueTransactionVH> {
    private ArrayList<_NewRegistrationDelayChargesResponse> dueResponses = new ArrayList<>();
    private Activity activity;


    private String[] colors = {"#F5F5F5", "#ffffff"};


    public RegistrationDelayAdapter(Activity activity, ArrayList<_NewRegistrationDelayChargesResponse> dueResponses) {
        this.activity = activity;
        this.dueResponses = dueResponses;

    }

    @NonNull
    @Override
    public DueTransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DueTransactionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.due_main_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DueTransactionVH holder, int position) {

        holder.scheduleDateTVID.setVisibility(View.VISIBLE);
        holder.scheduleAmountTVID.setVisibility(View.VISIBLE);
        holder.paidAmountTVID.setVisibility(View.GONE);


        holder.particularTVID.setText(dueResponses.get(position).getRemarks());
        holder.scheduleAmountTVID.setText("\u20B9" + dueResponses.get(position).getDelay_amount());
        holder.scheduleDateTVID.setText(dueResponses.get(position).getDelay_date());


        holder.mainLLID.setBackgroundColor(Color.parseColor(colors[position % 2]));
    }

    @Override
    public int getItemCount() {
        return dueResponses.size();
    }

    public class DueTransactionVH extends RecyclerView.ViewHolder {

        AppCompatTextView particularTVID;
        AppCompatTextView scheduleDateTVID;
        AppCompatTextView scheduleAmountTVID;
        AppCompatTextView paidAmountTVID;
        LinearLayout mainLLID;

        public DueTransactionVH(@NonNull View itemView) {
            super(itemView);
            particularTVID = itemView.findViewById(R.id.particularTVID);
            scheduleDateTVID = itemView.findViewById(R.id.scheduleDateTVID);
            scheduleAmountTVID = itemView.findViewById(R.id.scheduleAmountTVID);
            paidAmountTVID = itemView.findViewById(R.id.paidAmountTVID);
            mainLLID = itemView.findViewById(R.id.mainLLID);
        }
    }
}
