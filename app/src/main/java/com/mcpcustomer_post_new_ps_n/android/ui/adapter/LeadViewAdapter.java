package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.ui.models.LeadsResponse;

import java.util.ArrayList;

/**
 * Created by venkei on 11-Aug-18.
 */
public class LeadViewAdapter extends BaseAdapter {

    private static final int REQUEST_CALL = 1;
    private Activity mContext;
    private ArrayList<LeadsResponse> crmAllResponses = new ArrayList<>();
    private String userIdStr;




    LayoutInflater inflater;
    private TextView newLeadNameTVID;

    String[] mColors = {"#3F51B5", "#FF9800", "#009688", "#673AB7", "#999999", "#454545", "#00FF00",
            "#FF0000", "#0000FF", "#800000", "#808000", "#00FF00", "#008000", "#00FFFF",
            "#000080", "#800080", "#f40059", "#0080b8", "#350040", "#650040", "#750040",
            "#45ddc0", "#dea42d", "#b83800", "#dd0244", "#c90000", "#465400",
            "#ff004d", "#ff6700", "#5d6eff", "#3955ff", "#0a24ff", "#004380", "#6b2e53",
            "#a5c996", "#f94fad", "#ff85bc", "#ff906b", "#b6bc68", "#296139"};


    public LeadViewAdapter(Activity mContext, ArrayList<LeadsResponse> crmAllResponses) {
        this.mContext = mContext;
        this.crmAllResponses = crmAllResponses;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return crmAllResponses.size();
    }

    @Override
    public Object getItem(int i) {
        return crmAllResponses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.cp_lead_list_item, null);



        TextView newProjectNameTVID = view.findViewById(R.id.newProjectNameTVID);
        TextView newActivityDateTVID = view.findViewById(R.id.newActivityDateTVID);
        TextView date=view.findViewById(R.id.date);
        date.setText(crmAllResponses.get(i).getActvty_dt());
        newLeadNameTVID = view.findViewById(R.id.newLeadNameTVID);
        RelativeLayout newPhoneRL = view.findViewById(R.id.newPhoneRL);
        newLeadNameTVID.setText(crmAllResponses.get(i).getCustomer_name());

        newProjectNameTVID.setText(crmAllResponses.get(i).getProject_name());
        newActivityDateTVID.setText(crmAllResponses.get(i).getActvty_dt());


        newPhoneRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = crmAllResponses.get(i).getMobile();
                numberCalling(number);
            }
        });

        RelativeLayout imageViewIcon = view.findViewById(R.id.imageViewIcon);

        GradientDrawable bgShape = (GradientDrawable) imageViewIcon.getBackground();
        bgShape.setColor(Color.parseColor(mColors[i % 40]));

        TextView iconTitleID = view.findViewById(R.id.iconTitleID);
        String title = crmAllResponses.get(i).getCustomer_name();
        iconTitleID.setText(Utilities.CapitalText(title));

        newLeadNameTVID.setText(crmAllResponses.get(i).getCustomer_name());

        return view;
    }


    private void numberCalling(String number) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(callIntent);

        }
    }


}