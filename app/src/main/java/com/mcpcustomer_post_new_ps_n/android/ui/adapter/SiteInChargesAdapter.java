package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SiteInchargeResponse;

import java.util.ArrayList;

public class SiteInChargesAdapter extends RecyclerView.Adapter<SiteInChargesAdapter.SiteInChargesVH> {

    ArrayList<SiteInchargeResponse> siteInChargeResponses = new ArrayList<>();
    Activity activity;
    SiteInChargesClickListener siteInChargesClickListener;

    public SiteInChargesAdapter(ArrayList<SiteInchargeResponse> siteInChargeResponses, Activity activity, SiteInChargesClickListener siteInChargesClickListener) {
        this.siteInChargeResponses = siteInChargeResponses;
        this.activity = activity;
        this.siteInChargesClickListener = siteInChargesClickListener;
    }

    @NonNull
    @Override
    public SiteInChargesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SiteInChargesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.site_incharges_item,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SiteInChargesVH holder, int position) {


        SiteInchargeResponse item = siteInChargeResponses.get(position);

        holder.siteInChargeNameTVID.setText(item.getSite_incharge());
        holder.projectNameTVID.setText(item.getProject_id());

        holder.newPhoneRL.setOnClickListener(v -> {
            numberCalling(item.getMobile());
        });


    }

    private void numberCalling(String number) {

        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                activity.startActivity(callIntent);
                // Log.d("CONSTRUCTOR_ID_EXE_1->", "" + callID);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                activity.startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return siteInChargeResponses.size();
    }

    public class SiteInChargesVH extends RecyclerView.ViewHolder{

        AppCompatTextView siteInChargeNameTVID,projectNameTVID;
        RelativeLayout whatsAppCallRLID,newPhoneRL;

        public SiteInChargesVH(@NonNull View itemView) {
            super(itemView);

            siteInChargeNameTVID = itemView.findViewById(R.id.siteInChargeNameTVID);
            projectNameTVID = itemView.findViewById(R.id.projectNameTVID);
            newPhoneRL = itemView.findViewById(R.id.newPhoneRL);
        }
    }

    public interface SiteInChargesClickListener {
        void onSiteInChargeItemClick(SiteInchargeResponse response, View v, int pos, SiteInChargesVH holder);
    }
}
