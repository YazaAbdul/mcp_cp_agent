package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AmenitiesResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectDashBoardResponce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.AmenitiesVH> {

    private ArrayList<AmenitiesResponce> amenitiesList;
    private Activity activity;

    public AmenitiesAdapter(ArrayList<AmenitiesResponce> amenitiesList, Activity activity) {
        this.amenitiesList = amenitiesList;
        this.activity = activity;
    }

    public AmenitiesAdapter(ArrayList<ProjectDashBoardResponce> amenities_responce) {
        this.amenitiesList =amenitiesList;
    }


    @NonNull
    @Override
    public AmenitiesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AmenitiesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_amenities,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesVH holder, int position) {
        Picasso.get().load(amenitiesList.get(position).getAmmenities_img()).into(holder.amenitiesIVID);
        holder.amenitiesTypeTVID.setText(amenitiesList.get(position).getAmmenities_nm());

    }

    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }

    public static class AmenitiesVH extends RecyclerView.ViewHolder{

        AppCompatImageView amenitiesIVID;
        AppCompatTextView amenitiesTypeTVID;

        public AmenitiesVH(@NonNull View itemView) {
            super(itemView);
            amenitiesIVID = itemView.findViewById(R.id.amenitiesIVID);
            amenitiesTypeTVID = itemView.findViewById(R.id.amenitiesTypeTVID);
        }
    }
}
