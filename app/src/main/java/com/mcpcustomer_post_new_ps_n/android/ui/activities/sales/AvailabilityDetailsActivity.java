package com.mcpcustomer_post_new_ps_n.android.ui.activities.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.AvailableDetailsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.BhkAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AvailableDetailsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BhkResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilityDetailsActivity extends AppCompatActivity {

    public static String projectId, projectName;

    ProgressBar pb;
    TextView noData;
    RecyclerView bhkGVID,plotsLVID;
    ArrayList<BhkResponse> bhkResponses = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    BhkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_details);

        overridePendingTransition(R.anim.act_pull_in_right, R.anim.act_push_out_left);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                projectId = bundle.getString("PROJECT_ID");
                projectName = bundle.getString("PROJECT_NAME");
            }
        }

        RelativeLayout backRLID = findViewById(R.id.headerBackRLID);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressedAnimation();
            }
        });

        noData = findViewById(R.id.noData);
        TextView headerTittleTVID = findViewById(R.id.headerTitleTVID);
        headerTittleTVID.setText(projectName);
        pb = findViewById(R.id.pb1);
        plotsLVID = findViewById(R.id.plotsLVID);


        FloatingActionButton refresh_fab = findViewById(R.id.refresh_fab);
        refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getPlotsData(projectId);
            }
        });


        bhkGVID = findViewById(R.id.bhkGVID);
        getBhkData(projectId);

        linearLayoutManager = new GridLayoutManager(this, 4);
        bhkGVID.setLayoutManager(linearLayoutManager);
        bhkGVID.setItemAnimator(new DefaultItemAnimator());
        adapter = new BhkAdapter(AvailabilityDetailsActivity.this, bhkResponses);
        bhkGVID.setAdapter(adapter);

        //getPlotsData(projectId, "EAST");

        adapter.setChildClickListener(new BhkAdapter.ChildItemCLicListener() {
            @Override
            public void childItemClickedPosition(String name) {
                //Toast.makeText(AvailabilityDetailsActivity.this, name, Toast.LENGTH_SHORT).show();
                getPlotsData(projectId, name);
                noData.setVisibility(View.GONE);
            }
        });
    }

    private void getBhkData(final String projectId) {
        bhkGVID.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<BhkResponse>> call = apiInterface.getBhkNames(projectId);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<BhkResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<BhkResponse>> call, Response<ArrayList<BhkResponse>> response) {
                pb.setVisibility(View.GONE);
                bhkGVID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    bhkResponses = response.body();
                    if (bhkResponses.size() > 0) {
                        //adapter = new BhkAdapter(AvailabilityDetailsActivity.this, bhkResponses);
                        //bhkGVID.setAdapter(adapter);
                        adapter.setSubCategoriesHolderslist(bhkResponses);
                        for (int i = 0; i < bhkResponses.size(); i++) {
                            getPlotsData(projectId, bhkResponses.get(0).getBhk_type());
                        }
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        bhkGVID.setVisibility(View.GONE);
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                    bhkGVID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BhkResponse>> call, Throwable t) {
                pb.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                plotsLVID.setVisibility(View.GONE);
            }
        });
    }

    private void getPlotsData(String projectId, String type) {
        plotsLVID.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AvailableDetailsResponse>> call = apiInterface.getPlotNames(projectId, type);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<AvailableDetailsResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AvailableDetailsResponse>> call, Response<ArrayList<AvailableDetailsResponse>> response) {
                pb.setVisibility(View.GONE);
                plotsLVID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<AvailableDetailsResponse> detailsResponses = response.body();
                    if (detailsResponses.size() > 0) {
                        GridLayoutManager layoutManager = new GridLayoutManager(AvailabilityDetailsActivity.this,5);
                        plotsLVID.setLayoutManager(layoutManager);
                        AvailableDetailsAdapter detailsAdapter = new AvailableDetailsAdapter(AvailabilityDetailsActivity.this, detailsResponses);
                        plotsLVID.setAdapter(detailsAdapter);
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        plotsLVID.setVisibility(View.GONE);
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                    plotsLVID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AvailableDetailsResponse>> call, Throwable t) {
                pb.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                plotsLVID.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressedAnimation();
    }

    private void backPressedAnimation() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);

    }

}
