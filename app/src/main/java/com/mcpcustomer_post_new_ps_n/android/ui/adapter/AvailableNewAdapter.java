package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.AvailabilityDetailsActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AvailabilityResponse;

import java.util.ArrayList;


/**
 * Created by venkei on 20-Jun-19.
 */
public class AvailableNewAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<AvailabilityResponse>availabilityList=new ArrayList<>();
    String type;

    public AvailableNewAdapter(Activity activity, ArrayList<AvailabilityResponse>availabilityList, String type){
        this.activity=activity;
        this.availabilityList=availabilityList;
        inflater=LayoutInflater.from(activity);
        this.type=type;
    }

    @Override
    public int getCount() {
        return availabilityList.size();
    }

    @Override
    public Object getItem(int i) {
        return availabilityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view=convertView;
        if (convertView==null)
            view=inflater.inflate(R.layout.new_availability_list_item,null);

        TextView projectNameTVID=view.findViewById(R.id.projectNameTVID);
        projectNameTVID.setText(availabilityList.get(i).getProject_name());

        RelativeLayout mainDisplayRLID=view.findViewById(R.id.mainDisplayRLID);

        mainDisplayRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AvailabilityDetailsActivity.class);
                intent.putExtra("PROJECT_ID",""+availabilityList.get(i).getProject_id());
                intent.putExtra("PROJECT_NAME",""+availabilityList.get(i).getProject_name());
                activity.startActivity(intent);

              /*  if (type.equals("1")){

                }else{
                    Intent intent=new Intent(activity, DueCustomerListActivity.class);
                    intent.putExtra("PROJECT_ID",""+availabilityList.get(i).getProject_id());
                    intent.putExtra("PROJECT_NAME",""+availabilityList.get(i).getProject_name());
                    activity.startActivity(intent);
                }*/
            }
        });

        return view;
    }
}
