package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MelaResponse;

import java.util.ArrayList;

public class MelaUpdateAdapter extends RecyclerView.Adapter<MelaUpdateAdapter.MelaUpdateVH> {

    Activity activity;
    ArrayList<MelaResponse> melaResponses = new ArrayList<>();
    MelaClickListener melaClickListener;
    String userID,melaID;




    public MelaUpdateAdapter(Activity activity, ArrayList<MelaResponse> melaResponses, MelaClickListener melaClickListener) {
        this.activity = activity;
        this.melaResponses = melaResponses;
        this.melaClickListener = melaClickListener;
    }

    @NonNull
    @Override
    public MelaUpdateVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MelaUpdateVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.mela_update_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MelaUpdateVH holder, int position) {

        userID = MySharedPreferences.getPreferences(activity, "" + AppConstants.CP_USER_ID);

        MelaResponse item = melaResponses.get(position);

        holder.titleTVID.setText(melaResponses.get(position).getMela_title());
        holder.descriptionTVID.setText(melaResponses.get(position).getMela_description());
        holder.dateTVID.setText(melaResponses.get(position).getMela_date());

        holder.associateCountTVID.setText(melaResponses.get(position).getAssociate_count());
        holder.customersCountTVID.setText(melaResponses.get(position).getCustomer_count());
        holder.melaDateTVID.setText(melaResponses.get(position).getCreated_date());

        holder.deleteRLID.setOnClickListener(v -> melaClickListener.onMelaItemClick(item, v, position, holder));

        holder.melaPlanDeleteRLID.setOnClickListener(v -> melaClickListener.onMelaItemClick(item, v, position, holder));
        holder.melaPlanUpdateLLID.setOnClickListener(v -> melaClickListener.onMelaItemClick(item, v, position, holder));
        holder.melaUpdateLLID.setOnClickListener(v -> melaClickListener.onMelaItemClick(item, v, position, holder));

        melaID = melaResponses.get(position).getS_no();

        if (melaResponses.get(position).getMobile_logic().equalsIgnoreCase("0")){
            holder.melaPlansLLID.setVisibility(View.GONE);
        }else {
            holder.melaPlansLLID.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public int getItemCount() {
        return melaResponses.size();
    }

    public class MelaUpdateVH extends RecyclerView.ViewHolder{

        LinearLayout melaUpdateLLID;
        AppCompatTextView titleTVID,descriptionTVID,dateTVID,
                associateCountTVID,customersCountTVID,melaDateTVID;
        RelativeLayout deleteRLID,melaPlanDeleteRLID;
        public ProgressBar pb;
        RecyclerView melaPlansRVID;
        public LinearLayout melaPlansLLID,melaPlanUpdateLLID;
        public View melaPlanVID;

        public MelaUpdateVH(@NonNull View itemView) {
            super(itemView);

            melaUpdateLLID = itemView.findViewById(R.id.melaUpdateLLID);
            titleTVID = itemView.findViewById(R.id.titleTVID);
            descriptionTVID = itemView.findViewById(R.id.descriptionTVID);
            dateTVID = itemView.findViewById(R.id.dateTVID);
            deleteRLID = itemView.findViewById(R.id.deleteRLID);
            pb = itemView.findViewById(R.id.pb);

            melaPlansLLID = itemView.findViewById(R.id.melaPlansLLID);
            melaPlanVID = itemView.findViewById(R.id.melaPlanVID);

            associateCountTVID = itemView.findViewById(R.id.associateCountTVID);
            customersCountTVID = itemView.findViewById(R.id.customersCountTVID);
            melaDateTVID = itemView.findViewById(R.id.melaDateTVID);
            melaPlanDeleteRLID = itemView.findViewById(R.id.melaPlanDeleteRLID);
            melaPlanUpdateLLID = itemView.findViewById(R.id.melaPlanUpdateLLID);
        }
    }

    public interface MelaClickListener {
        void onMelaItemClick(MelaResponse response, View v, int pos, MelaUpdateVH holder);
    }
}
