package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.AvailabilityDetailsActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.PlotDetailsActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AvailableDetailsResponse;

import java.util.ArrayList;

/**
 * Created by venkei on 05-Apr-19.
 */
public class AvailableDetailsAdapter extends RecyclerView.Adapter<AvailableDetailsAdapter.ViewHolder> {

    private ArrayList<AvailableDetailsResponse> detailsResponses = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity activity;

    public AvailableDetailsAdapter(Activity activity, ArrayList<AvailableDetailsResponse> detailsResponses) {
        this.activity = activity;
        this.detailsResponses = detailsResponses;
        inflater = LayoutInflater.from(activity);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.available_details_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int i) {

        holder.plotNumberTVID.setText(detailsResponses.get(i).getPlot_number());

        String input = detailsResponses.get(i).getColor();
       holder.plotBGID.setBackgroundColor(Color.parseColor(input));

        Log.e("api==>", "detailsResponses "+detailsResponses.get(i).getColor());

        holder.plotBGID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PlotDetailsActivity.class);
                intent.putExtra("PROJECT_ID", "" + AvailabilityDetailsActivity.projectId);
                intent.putExtra("PROJECT_NAME", "" + detailsResponses.get(i).getPlot_number());
                intent.putExtra("PLOT_ID", "" + detailsResponses.get(i).getPlot_id());
                intent.putExtra("PLOT_COLOR", "" + detailsResponses.get(i).getColor());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return detailsResponses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout plotBGID;
        TextView plotNumberTVID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             plotBGID = itemView.findViewById(R.id.plotBGID);
             plotNumberTVID = itemView.findViewById(R.id.plotNumberTVID);
        }
    }
}
