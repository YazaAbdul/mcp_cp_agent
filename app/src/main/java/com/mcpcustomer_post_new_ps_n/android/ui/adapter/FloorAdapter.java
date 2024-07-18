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
import com.mcpcustomer_post_new_ps_n.android.ui.models.FloorPlansResponce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorVH> {

    private ArrayList<FloorPlansResponce> floorImagesList;
    private Activity activity;

        public FloorAdapter (ArrayList<FloorPlansResponce>floorImagesList){
        this.floorImagesList = floorImagesList;
    }

    @NonNull
    @Override
    public FloorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FloorVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_floor_plans,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull FloorVH holder, int position) {
        Picasso.get().load(floorImagesList.get(position).getImage()).into(holder.floorIV);
        holder.detailsTVID.setText(floorImagesList.get(0).getBhk());
        holder.facingTVID.setText(floorImagesList.get(0).getFacing());

    }

    @Override
    public int getItemCount() {
        return floorImagesList.size();
    }

    public static class FloorVH extends RecyclerView.ViewHolder{
        AppCompatImageView floorIV;
        AppCompatTextView detailsTVID;
        AppCompatTextView facingTVID;
        public FloorVH(@NonNull View itemView) {
            super(itemView);
            floorIV = itemView.findViewById(R.id.floorIV);
            detailsTVID = itemView.findViewById(R.id.detailsTVID);
            facingTVID = itemView.findViewById(R.id.facingTVID);

        }
    }

   /* @NonNull
    @Override
    public FloorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_floor_plans,parent,false);
        return new FloorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorAdapter.ViewHolder holder, int position) {

        int imageResource = floorImagesList.get(position).getFloorImage();
        String details = floorImagesList.get(position).getDetails();
        String facing = floorImagesList.get(position).getFacing();
        holder.setData(imageResource,details,facing);
    }

    @Override
    public int getItemCount() {
        return floorImagesList.size();
    }

        public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.precise_customer.android.ui.adapter.ViewHolder {

            private ImageView imageView;
            private AppCompatTextView detailsTV;
            private AppCompatTextView facingTV;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.floorIV);
                detailsTV = itemView.findViewById(R.id.detailsTVID);
                facingTV = itemView.findViewById(R.id.facingTVID);
            }

            public void setData(int imageResource, String details, String facing) {
                imageView.setImageResource(imageResource);
                detailsTV.setText(details);
                facingTV.setText(facing);

            }
        }*/


}
