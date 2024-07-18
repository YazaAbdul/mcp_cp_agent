package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamResponse;

import java.net.URLEncoder;
import java.util.ArrayList;

public class DirectTeamAdapter extends RecyclerView.Adapter<DirectTeamAdapter.DirectTeamVH> {

    ArrayList<DirectTeamResponse> directTeamResponses = new ArrayList<>();
    Activity activity;
    DirectTeamClickListener directTeamClickListener;
    String type;

    public DirectTeamAdapter(ArrayList<DirectTeamResponse> directTeamResponses, Activity activity, DirectTeamClickListener directTeamClickListener, String type) {
        this.directTeamResponses = directTeamResponses;
        this.activity = activity;
        this.directTeamClickListener = directTeamClickListener;
        this.type = type;
    }

    @NonNull
    @Override
    public DirectTeamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DirectTeamVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_team_item,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DirectTeamVH holder, int position) {


        DirectTeamResponse item = directTeamResponses.get(position);
        DirectTeamVH itemVH = (DirectTeamVH) holder;

        if(type.equalsIgnoreCase("3")){
            holder.associateTVID.setText(item.getAssociate()+" ("+item.getCount()+")");
        }else{
            holder.associateTVID.setText(item.getAssociate());
        }

        holder.serialNoTVID.setText(item.getS_no()+")");
        holder.idTVID.setText("ID : "+item.getId());
        holder.salesTVID.setText("ID : "+item.getId());
        holder.saleAmountTVID.setText("Sales Amount : "+item.getSale_amount()+" ₹");
        holder.upLineTVID.setText(item.getDesignation());
        holder.upLineNameTVID.setText(item.getUpline_name());
        itemVH.newMainLeadView.setOnClickListener(v -> directTeamClickListener.onDirectTeamItemClick(item,v,position,itemVH));

        holder.whatsAppCallRLID.setOnClickListener(v -> {
            loadWhatsApp(item.getMobile_no());
        });

        if (type.equalsIgnoreCase("1")) {
            holder.newPhoneRL.setVisibility(View.VISIBLE);
            holder.whatsAppCallRLID.setVisibility(View.VISIBLE);
        } else if (type.equalsIgnoreCase("2")){
            holder.newPhoneRL.setVisibility(View.VISIBLE);
            holder.whatsAppCallRLID.setVisibility(View.VISIBLE);

           /* holder.newPhoneRL.setVisibility(View.GONE);
            holder.whatsAppCallRLID.setVisibility(View.GONE);*/
        }else if(type.equalsIgnoreCase("3")){
            holder.newPhoneRL.setVisibility(View.VISIBLE);
            holder.whatsAppCallRLID.setVisibility(View.VISIBLE);
        }

        holder.newPhoneRL.setOnClickListener(v -> {
            numberCalling(item.getMobile_no());
        });


    }

    private void loadWhatsApp (String mobile_no) {

        Log.e("mobile_number==>","" + mobile_no);

        final BottomSheetDialog dialog1 = new BottomSheetDialog(activity);
        dialog1.setContentView(R.layout.alert_business_whatsapp);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        int width1 = ViewGroup.LayoutParams.MATCH_PARENT;
        int height1 = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog1.getWindow().setLayout(width1, height1);
        dialog1.show();

        AppCompatImageView whatsappIVID = dialog1.findViewById(R.id.whatsappIVID);
        AppCompatImageView businessWhatsappIVID = dialog1.findViewById(R.id.businessWhatsappIVID);

        whatsappIVID.setOnClickListener(view1 -> {

            try {
                Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + "+91" + mobile_no + "&text=" + URLEncoder.encode("", "UTF-8");
                sendMsg.setPackage("com.whatsapp");
                sendMsg.setData(Uri.parse(url));
                activity.startActivity(sendMsg);
                dialog1.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                /*Utilities.showToast(this,e.getMessage());*/
                Toast.makeText(activity, "You don't have WhatsApp in your device", Toast.LENGTH_SHORT).show();
            }

        });

        businessWhatsappIVID.setOnClickListener(view2 -> {

            try {

                Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + "+91" + mobile_no + "&text=" + URLEncoder.encode("", "UTF-8");
                sendMsg.setPackage("com.whatsapp.w4b");
                sendMsg.setData(Uri.parse(url));
                activity.startActivity(sendMsg);



                dialog1.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                /*Utilities.showToast(this,e.getMessage());*/
                Toast.makeText(activity, "You don't have business WhatsApp in your device", Toast.LENGTH_SHORT).show();
            }

            dialog1.dismiss();

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
        return directTeamResponses.size();
    }

    public class DirectTeamVH extends RecyclerView.ViewHolder{

        AppCompatTextView associateTVID,idTVID,salesTVID,saleAmountTVID,upLineTVID,upLineNameTVID,serialNoTVID;
        RelativeLayout whatsAppCallRLID,newPhoneRL,newMainLeadView;

        public DirectTeamVH(@NonNull View itemView) {
            super(itemView);

            associateTVID = itemView.findViewById(R.id.associateTVID);
            serialNoTVID = itemView.findViewById(R.id.serialNoTVID);
            idTVID = itemView.findViewById(R.id.idTVID);
            salesTVID = itemView.findViewById(R.id.salesTVID);
            saleAmountTVID = itemView.findViewById(R.id.saleAmountTVID);
            upLineTVID = itemView.findViewById(R.id.upLineTVID);
            upLineNameTVID = itemView.findViewById(R.id.upLineNameTVID);
            whatsAppCallRLID = itemView.findViewById(R.id.whatsAppCallRLID);
            newPhoneRL = itemView.findViewById(R.id.newPhoneRL);
            newMainLeadView = itemView.findViewById(R.id.newMainLeadView);
        }
    }

    public interface DirectTeamClickListener {
        void onDirectTeamItemClick(DirectTeamResponse response, View v, int pos, DirectTeamVH holder);
    }
}
