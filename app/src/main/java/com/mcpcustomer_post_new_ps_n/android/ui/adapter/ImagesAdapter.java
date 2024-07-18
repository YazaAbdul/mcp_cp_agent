package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ImageResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesAdapter  extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ImageResponse> imageResponselist;


    public ImagesAdapter(Activity activity, ArrayList<ImageResponse> imageResponselist) {
        this.activity = activity;
        this.imageResponselist = imageResponselist;
    }
    @NonNull
    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.image_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ViewHolder holder, int position) {
        Picasso.get().load(imageResponselist.get(position).getImage()).into(holder.photosIVId);
    }

    @Override
    public int getItemCount() {
        return imageResponselist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photosIVId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photosIVId=itemView.findViewById(R.id.photosIVId);
        }
    }
}
