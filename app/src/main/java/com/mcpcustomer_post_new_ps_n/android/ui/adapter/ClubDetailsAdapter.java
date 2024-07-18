package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ClubDetailsResponse;

import java.util.ArrayList;

public class ClubDetailsAdapter extends RecyclerView.Adapter<ClubDetailsAdapter.ClubDetailsVH> {

    ArrayList<ClubDetailsResponse> clubDetailsResponses = new ArrayList<>();
    Activity activity;

    public ClubDetailsAdapter(ArrayList<ClubDetailsResponse> clubDetailsResponses, Activity activity) {
        this.clubDetailsResponses = clubDetailsResponses;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ClubDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClubDetailsVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.club_details_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClubDetailsVH holder, int position) {

        holder.titleTVID.setText(clubDetailsResponses.get(position).getAchievement_target());
        holder.offerTVID.setText(clubDetailsResponses.get(position).getAchievement_rank());
        holder.createdTVID.setText(clubDetailsResponses.get(position).getClub_benefits());
        holder.dateTVID.setText(clubDetailsResponses.get(position).getAch());

    }

    @Override
    public int getItemCount() {
        return clubDetailsResponses.size();
    }

    public class ClubDetailsVH extends RecyclerView.ViewHolder{

        AppCompatTextView titleTVID,offerTVID,createdTVID,dateTVID;

        public ClubDetailsVH(@NonNull View itemView) {
            super(itemView);

            titleTVID = itemView.findViewById(R.id.titleTVID);
            offerTVID = itemView.findViewById(R.id.offerTVID);
            createdTVID = itemView.findViewById(R.id.createdTVID);
            dateTVID = itemView.findViewById(R.id.dateTVID);
        }
    }
}
