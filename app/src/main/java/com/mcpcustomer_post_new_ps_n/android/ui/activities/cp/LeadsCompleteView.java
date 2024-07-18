package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.AppSingleton;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.LeadViewAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.LeadsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeadsCompleteView extends AppCompatActivity {
    public static String tittleStr, urlStr;

    RelativeLayout backRLID;
    TextView headerTittleTVID;
    String userID, userType;
    TextView noDataTVID;
    ListView newCrmMainLVID;

    ProgressBar mainProgressBarCRM;
    ArrayList<LeadsResponse> crmAllResponses = new ArrayList<>();
    LeadViewAdapter listAdapter;
    SwipeRefreshLayout swipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads_complete_view);
        Utilities.startAnimation(this);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                urlStr = bundle.getString("URL");
                tittleStr = bundle.getString("Tittle");
            }
        }

        backRLID=findViewById(R.id.backRLID);
        headerTittleTVID=findViewById(R.id.headerTittleTVID);
        MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "YES");
        headerTittleTVID.setText(tittleStr);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.finishAnimation(LeadsCompleteView.this);
            }
        });

        RelativeLayout searchRLID=findViewById(R.id.searchRLID);
        searchRLID.setVisibility(View.VISIBLE);
        searchRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeadsCompleteView.this,SearchActivity.class));
            }
        });

        userID= MySharedPreferences.getPreferences(this,""+ AppConstants.CP_USER_ID);
        userType=MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_TYPE);
        noDataTVID=findViewById(R.id.noDataTVID);
        mainProgressBarCRM=findViewById(R.id.mainProgressBarCRM);
        newCrmMainLVID=findViewById(R.id.newCrmMainLVID);

        swipeID=findViewById(R.id.swipeID);
        swipeID.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                crmAllActivitiesList(AppConstants.BASE_URL+"mobileapp/activitiescompleteddata" +"?cp_id=" + userID +"&act_id="+LeadsCompleteView.urlStr  +"&start=0");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeID.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        crmAllActivitiesList(AppConstants.BASE_URL +"mobileapp/activitiescompleteddata"+"?cp_id=" + userID+"&act_id="+ LeadsCompleteView.urlStr  +"&start=0");
    }

    public void crmAllActivitiesList(String url) {
        mainProgressBarCRM.setVisibility(View.VISIBLE);
        noDataTVID.setVisibility(View.GONE);
        newCrmMainLVID.setVisibility(View.VISIBLE);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mainProgressBarCRM.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.GONE);
                crmAllResponses.clear();
                if (response == null) {
                    noDataTVID.setVisibility(View.VISIBLE);
                } else if (response.equals("null")) {
                    noDataTVID.setVisibility(View.VISIBLE);
                } else {
                    newCrmMainLVID.setVisibility(View.VISIBLE);
                    try {
                        int count = 0;
                        String custname, actvty_dt, custid, custmobile, custemail, custproj;

                        while (count < response.length()) {

                            JSONObject jo = response.getJSONObject(count);
                            custname = jo.getString("customer_name");
                            actvty_dt = jo.getString("actvty_dt");
                            custid = jo.getString("customer_id");
                            custmobile = jo.getString("mobile");
                            custemail = jo.getString("email");
                            //remarks = jo.getString("remarks");
                            custproj = jo.getString("project_name");

                            crmAllResponses.add(new LeadsResponse(custname, actvty_dt, custid, custmobile, custemail, custproj));
                            listAdapter=new LeadViewAdapter(LeadsCompleteView.this,crmAllResponses);
                            newCrmMainLVID.setAdapter(listAdapter);

                            count++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainProgressBarCRM.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.VISIBLE);
                newCrmMainLVID.setVisibility(View.GONE);
            }
        });

        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppSingleton.getInstance(LeadsCompleteView.this).addToRequestQueue(arrayRequest, "");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

}