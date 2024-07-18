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
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewRegistrationPaymentDetailsResponse;

import java.util.ArrayList;

public class RegistrationPaymentsAdapter extends RecyclerView.Adapter<RegistrationPaymentsAdapter.DueTransactionVH> {
    private ArrayList<_NewRegistrationPaymentDetailsResponse> dueResponses = new ArrayList<>();
    private Activity activity;


    private String[] colors = {"#F5F5F5", "#ffffff"};


    public RegistrationPaymentsAdapter(Activity activity, ArrayList<_NewRegistrationPaymentDetailsResponse> dueResponses) {
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
        holder.scheduleAmountTVID.setVisibility(View.GONE);
        holder.paidAmountTVID.setVisibility(View.VISIBLE);


        holder.particularTVID.setText(dueResponses.get(position).getPayment_made());
        holder.paidAmountTVID.setText("\u20B9" + dueResponses.get(position).getReceived_amount());
        holder.scheduleDateTVID.setText(dueResponses.get(position).getPaid_date());


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
