package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BonanzaResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BonanzaAdapter extends RecyclerView.Adapter<BonanzaAdapter.BonanzaVH> {

    ArrayList<BonanzaResponse> bonanzaResponses = new ArrayList<>();
    Activity activity;

    public BonanzaAdapter(ArrayList<BonanzaResponse> bonanzaResponses, Activity activity) {
        this.bonanzaResponses = bonanzaResponses;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BonanzaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BonanzaVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.bonanza_item,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BonanzaVH holder, int position) {

        Picasso.get().load(bonanzaResponses.get(position).getPic()).into(holder.bonanzaIVID);

        holder.startTVID.setText("Start Date : "+bonanzaResponses.get(position).getBonanza_start());
        holder.endTVID.setText("End Date : "+bonanzaResponses.get(position).getBonanza_end());
        holder.notesTVID.setText("Notes : "+bonanzaResponses.get(position).getNotes());
        holder.salesTVID.setText("Sales : "+bonanzaResponses.get(position).getNo_of_sales());

    }

    @Override
    public int getItemCount() {
        return bonanzaResponses.size();
    }

    public class BonanzaVH extends RecyclerView.ViewHolder{

        AppCompatTextView startTVID,endTVID,notesTVID,salesTVID;
        AppCompatImageView bonanzaIVID;

        public BonanzaVH(@NonNull View itemView) {
            super(itemView);

            startTVID = itemView.findViewById(R.id.startTVID);
            endTVID = itemView.findViewById(R.id.endTVID);
            notesTVID = itemView.findViewById(R.id.notesTVID);
            bonanzaIVID = itemView.findViewById(R.id.bonanzaIVID);
            salesTVID = itemView.findViewById(R.id.salesTVID);
        }
    }
}
