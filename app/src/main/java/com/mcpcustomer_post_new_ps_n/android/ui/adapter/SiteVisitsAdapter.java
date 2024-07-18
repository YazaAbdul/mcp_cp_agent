package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SiteVisitResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SiteVisitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int VIEW_ITEM = 1;
    private static final int VIEW_LOADING = 0;
    private boolean isLoadingAdded = false;
    Activity activity;
    
    ArrayList<SiteVisitResponse> siteVisitResponses = new ArrayList<>();

    public SiteVisitsAdapter(Activity activity, ArrayList<SiteVisitResponse> siteVisitResponses) {
        this.activity = activity;
        this.siteVisitResponses = siteVisitResponses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (i) {
            case VIEW_ITEM:
                viewHolder = new SiteVisitVH(inflater.inflate(R.layout.site_visit_item, parent, false));
                break;

            case VIEW_LOADING:
                viewHolder = new LoadingViewHolder(inflater.inflate(R.layout.progressbar1, parent, false));
                break;
        }

        return viewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        switch (getItemViewType(i)) {
            case VIEW_ITEM:

                SiteVisitVH itemVH = (SiteVisitVH) holder;
                SiteVisitResponse item = siteVisitResponses.get(i);
                Picasso.get().load(item.getUpload_pic()).into(itemVH.agentIVID);

                itemVH.greetingsTVID.setText("Customer : " + item.getCustomer_name());
                itemVH.customerNumberTVID.setText("Mobile : " + item.getCustomer_number());
                itemVH.agentNameTVID.setText("Agent : " + item.getAgent_name());
                itemVH.agentMobileTVID.setText("Agent Number : " +item.getAgent_number());
                itemVH.agentEmailTVID.setText("Project : " + item.getProject_name());
                itemVH.siteVisitDateTVID.setText("Created Date : " + item.getDate_time());

                break;

            case VIEW_LOADING:

                LoadingViewHolder loadingVH = (LoadingViewHolder) holder;
                loadingVH.progressBar1.setVisibility(View.VISIBLE);

                break;
        }

    }



    @Override
    public int getItemCount() {

        return siteVisitResponses == null ? 0 : siteVisitResponses.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == siteVisitResponses.size() - 1 && isLoadingAdded) ? VIEW_LOADING : VIEW_ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new SiteVisitResponse());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = siteVisitResponses.size() - 1;
        SiteVisitResponse result = getItem(position);
        if (result != null) {
            siteVisitResponses.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(SiteVisitResponse movie) {
        siteVisitResponses.add(movie);
        notifyItemInserted(siteVisitResponses.size() - 1);
    }

    public void addAll(List<SiteVisitResponse> moveResults) {
        for (SiteVisitResponse result : moveResults) {
            add(result);
        }
    }


    public SiteVisitResponse getItem(int position) {
        return siteVisitResponses.get(position);
    }

    public class SiteVisitVH extends RecyclerView.ViewHolder{

        AppCompatImageView agentIVID;
        AppCompatTextView greetingsTVID,agentNameTVID,agentMobileTVID,agentEmailTVID,siteVisitDateTVID,customerNumberTVID;


        public SiteVisitVH(@NonNull View itemView) {
            super(itemView);

            
            agentIVID = itemView.findViewById(R.id.agentIVID);
            
            greetingsTVID = itemView.findViewById(R.id.greetingsTVID);
            agentNameTVID = itemView.findViewById(R.id.agentNameTVID);
            agentMobileTVID = itemView.findViewById(R.id.agentMobileTVID);
            agentEmailTVID = itemView.findViewById(R.id.agentEmailTVID);
            siteVisitDateTVID = itemView.findViewById(R.id.siteVisitDateTVID);
            customerNumberTVID = itemView.findViewById(R.id.customerNumberTVID);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar1;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar1 = itemView.findViewById(R.id.progressBar1);
        }
    }
}
