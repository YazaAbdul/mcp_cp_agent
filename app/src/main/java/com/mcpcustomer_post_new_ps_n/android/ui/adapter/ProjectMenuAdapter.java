package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.SubCategoriesActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.HomeMainResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectMenuResponce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectMenuAdapter extends RecyclerView.Adapter<ProjectMenuAdapter.ProjectMenuVH> {

    ArrayList<ProjectMenuResponce> projectMenuList = new ArrayList<>();
    private ArrayList<HomeMainResponse> crmactivityholders = new ArrayList<>();
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_LOADING = 0;
    private boolean isLoadingAdded = false;
    Activity activity;

    public ProjectMenuAdapter( ArrayList<ProjectMenuResponce> projectMenuList,Activity activity) {
       this.projectMenuList = projectMenuList;
       this.activity = activity;
    }



    @NonNull
    @Override
    public ProjectMenuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectMenuVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_projects_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectMenuVH holder, @SuppressLint("RecyclerView") int position) {
        //final HomeMainResponse user = crmactivityholders.get(position);
        Picasso.get().load(projectMenuList.get(position).getMenu_icon()).into(holder.projectIVID);
        if (projectMenuList.get(position).getPrice()==null) {
            holder.costTVID.setVisibility(View.GONE);
        } else {
            holder.costTVID.setText("₹ " +projectMenuList.get(position).getPrice());
        }
        holder.detailsTVID.setText(projectMenuList.get(position).getBhk());
        holder.companyTVID.setText(projectMenuList.get(position).getMenu_name());
        holder.addressTVID.setText(projectMenuList.get(position).getAddress());

        holder.projectMenuCVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectMenuList.size()>0 ){
                    Intent intent = new Intent(activity,SubCategoriesActivity.class);
                    intent.putExtra("menu_id",projectMenuList.get(position).getMenu_id());
                    activity.startActivity(intent);
                    activity.finish();}

            }
        });

    }

    @Override
    public int getItemCount() {
        return projectMenuList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == projectMenuList.size() - 1 && isLoadingAdded) ? VIEW_LOADING : VIEW_ITEM;
    }

    public void setLoaded() {
        isLoadingAdded = false;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ProjectMenuResponce());
    }

    public void addAll(ArrayList<ProjectMenuResponce> moveResults) {
        for (ProjectMenuResponce result : moveResults) {
            add(result);
        }
    }

    public void add(ProjectMenuResponce movie) {
        projectMenuList.add(movie);
        notifyItemInserted(projectMenuList.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = projectMenuList.size() - 1;
        ProjectMenuResponce result = getItem(position);
        if (result != null) {
            projectMenuList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ProjectMenuResponce getItem(int position) {
        return projectMenuList.get(position);
    }

    public static class ProjectMenuVH extends RecyclerView.ViewHolder{
        AppCompatImageView projectIVID;
        AppCompatTextView costTVID, detailsTVID,companyTVID,addressTVID;
        CardView projectMenuCVID;

        public ProjectMenuVH(@NonNull View itemView) {
            super(itemView);
            projectIVID = itemView.findViewById(R.id.projectIVID);
            costTVID = itemView.findViewById(R.id.costTVID);
            detailsTVID = itemView.findViewById(R.id.detailsTVID);
            companyTVID = itemView.findViewById(R.id.companyTVID);
            addressTVID = itemView.findViewById(R.id.addressTVID);
            projectMenuCVID = itemView.findViewById(R.id.projectMenuCVID);

        }
    }
}
