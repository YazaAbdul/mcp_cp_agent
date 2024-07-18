package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.SiteInChargesAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SiteInchargeResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiteInChargesActivity extends AppCompatActivity implements SiteInChargesAdapter.SiteInChargesClickListener {

    AppCompatTextView noDataTVID;
    ProgressBar pb;
    RecyclerView siteInChargesRVID;
    RelativeLayout backRLID;
    TextView headerTittleTVID;
    ArrayList<SiteInchargeResponse> siteInChargeResponses = new ArrayList<>();
    SiteInChargesAdapter adapter;
    String agentID,type;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_incharges);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                type = bundle.getString("TYPE");
            }
        }

        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        backRLID = findViewById(R.id.backRLID);
        siteInChargesRVID = findViewById(R.id.siteInChargesRVID);
        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);

        headerTittleTVID.setText("Site Incharge");

        agentID = MySharedPreferences.getPreferences(getApplicationContext(), "" + AppConstants.CP_USER_ID);


        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(SiteInChargesActivity.this, "" + AppConstants.CP_VIEW, "YES");

        });

        if (Utilities.isNetworkAvailable(this)){
            loadDirectTeam();
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadDirectTeam() {

        pb.setVisibility(View.VISIBLE);
        siteInChargesRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);
        
        
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<SiteInchargeResponse>>  call = apiInterface.getSiteInCharges();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<SiteInchargeResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<SiteInchargeResponse>> call, Response<ArrayList<SiteInchargeResponse>> response) {

                pb.setVisibility(View.GONE);
                siteInChargesRVID.setVisibility(View.VISIBLE);
                noDataTVID.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200){

                    siteInChargeResponses = response.body();

                    if (siteInChargeResponses.size() > 0){

                        LinearLayoutManager layoutManager = new LinearLayoutManager(SiteInChargesActivity.this,RecyclerView.VERTICAL,false);
                        siteInChargesRVID.setLayoutManager(layoutManager);

                        adapter = new SiteInChargesAdapter(siteInChargeResponses, SiteInChargesActivity.this, SiteInChargesActivity.this);
                        siteInChargesRVID.setAdapter(adapter);
                    }else {
                        pb.setVisibility(View.GONE);
                        siteInChargesRVID.setVisibility(View.GONE);
                        noDataTVID.setVisibility(View.VISIBLE);
                    }
                }else {
                    pb.setVisibility(View.GONE);
                    siteInChargesRVID.setVisibility(View.GONE);
                    noDataTVID.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SiteInchargeResponse>> call, Throwable t) {

                pb.setVisibility(View.GONE);
                siteInChargesRVID.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onSiteInChargeItemClick(SiteInchargeResponse response, View v, int pos, SiteInChargesAdapter.SiteInChargesVH holder) {


    }

}