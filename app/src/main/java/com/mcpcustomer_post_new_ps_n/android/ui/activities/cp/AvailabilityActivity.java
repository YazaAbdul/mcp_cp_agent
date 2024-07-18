package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.AvailabilityDetailsActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.AvailableNewAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AvailabilityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilityActivity extends AppCompatActivity {

    Spinner projectSPID;
    String projectID,projectName;
    ArrayList<AvailabilityResponse>performanceResponses=new ArrayList<>();
    Button submitBtn;

    ListView availableLVID;
    ProgressBar pb;
    TextView noData;
    AvailableNewAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        overridePendingTransition(R.anim.act_pull_in_right,R.anim.act_push_out_left);

        RelativeLayout backRLID=findViewById(R.id.backRLID);
        backRLID.setOnClickListener(v-> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(AvailabilityActivity.this, "" + AppConstants.CP_VIEW, "YES");
        });

        //AppCompatTextView headerTittleTVID=findViewById(R.id.headerTittleTVID);
        TextView headerTittleTVID = findViewById(R.id.headerTittleTVID);
        headerTittleTVID.setText("Availability");

        projectSPID=findViewById(R.id.projectSPID);
        projectSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                projectID=performanceResponses.get(i).getProject_id();
                projectName=performanceResponses.get(i).getProject_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submitBtn=findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AvailabilityActivity.this, AvailabilityDetailsActivity.class);
                intent.putExtra("PROJECT_ID",""+projectID);
                intent.putExtra("PROJECT_NAME",""+projectName);
                startActivity(intent);
            }
        });



        availableLVID=findViewById(R.id.availableLVID);
        pb=findViewById(R.id.pb);
        noData=findViewById(R.id.noData);


        if (Utilities.isNetworkAvailable(AvailabilityActivity.this)){
            loaProjects();
        }else{
            Utilities.AlertDaiolog(AvailabilityActivity.this,"No Internet","Please check your internet connection...",1,null,"OK");
        }
    }

    private void loaProjects() {
        pb.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AvailabilityResponse>> call=apiInterface.getProjectNames();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<AvailabilityResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AvailabilityResponse>> call, Response<ArrayList<AvailabilityResponse>> response) {
                pb.setVisibility(View.GONE);
                if (response.body()!=null && response.code()==200){
                   performanceResponses=response.body();
                    if (performanceResponses.size()>0){
                        for (int i = 0; i < performanceResponses.size(); i++) {
                            adapter=new AvailableNewAdapter(AvailabilityActivity.this,performanceResponses,"1");
                            availableLVID.setAdapter(adapter);
                        }
                    }else{
                        noData.setVisibility(View.VISIBLE);
                    }
                }else{
                    noData.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AvailabilityResponse>> call, Throwable t) {
                noData.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                backPressedAnimation();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        backPressedAnimation();
    }

    private void backPressedAnimation() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left,R.anim.act_push_out_right);

    }
}
