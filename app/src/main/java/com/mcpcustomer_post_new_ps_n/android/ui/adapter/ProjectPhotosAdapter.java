package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectImagesResponce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectPhotosAdapter extends RecyclerView.Adapter<ProjectPhotosAdapter.PhotosVH> {

    private ArrayList<ProjectImagesResponce> projectPhotosList = new ArrayList<>();

    public ProjectPhotosAdapter(ArrayList<ProjectImagesResponce> projectPhotosList) {
        this.projectPhotosList = projectPhotosList;
    }

    @NonNull
    @Override
    public PhotosVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_photos,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosVH holder, int position) {
        Picasso.get().load(projectPhotosList.get(position).getImage()).into(holder.projectImagesIVID);

    }

    @Override
    public int getItemCount() {
        return projectPhotosList.size();
    }

    public static class PhotosVH extends RecyclerView.ViewHolder{

        AppCompatImageView projectImagesIVID;

        public PhotosVH(@NonNull View itemView) {

            super(itemView);

            projectImagesIVID = itemView.findViewById(R.id.projectImagesIVID);
        }

    }
}
