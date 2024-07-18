package com.mcpcustomer_post_new_ps_n.android.ui.activities.sales;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.PlotDetailsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlotDetailsActivity extends AppCompatActivity {

    private String projectId, projectName, plotID, plotColor;
    TextView sftTVID, priceTVID, costTVID, infraTVID, mainTVID, croposTVID, totalTVID,plostsizeTVID,
    bhkTVID,floorTVID,builtupTVID,unitpriceTVID,unitcostTVID,premiumTVID,ammenitiesTVID;

    RelativeLayout plotBGID;
    View viewID, viewID1, viewID2, viewID3, viewID4, viewID5;
    CardView cvID;
    ProgressBar pb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_details);

        overridePendingTransition(R.anim.act_pull_in_right, R.anim.act_push_out_left);
        cvID = findViewById(R.id.cvID);
        pb = findViewById(R.id.pb);

        sftTVID = findViewById(R.id.sftTVID);
        plostsizeTVID = findViewById(R.id.plostsizeTVID);
        priceTVID = findViewById(R.id.priceTVID);
        costTVID = findViewById(R.id.costTVID);
        infraTVID = findViewById(R.id.infraTVID);
        mainTVID = findViewById(R.id.mainTVID);
        croposTVID = findViewById(R.id.croposTVID);
        totalTVID = findViewById(R.id.totalTVID);
        bhkTVID = findViewById(R.id.bhkTVID);
        floorTVID = findViewById(R.id.floorTVID);
        builtupTVID = findViewById(R.id.builtupTVID);
        unitpriceTVID = findViewById(R.id.unitpriceTVID);
        unitcostTVID = findViewById(R.id.unitcostTVID);
        premiumTVID = findViewById(R.id.premiumTVID);
        ammenitiesTVID = findViewById(R.id.ammenitiesTVID);


        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                projectId = bundle.getString("PROJECT_ID");
                projectName = bundle.getString("PROJECT_NAME");
                plotID = bundle.getString("PLOT_ID");
                plotColor = bundle.getString("PLOT_COLOR");
            }
        }

        RelativeLayout backRLID = findViewById(R.id.backRLID);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressedAnimation();
            }
        });
        TextView headerTittleTVID = findViewById(R.id.headerTittleTVID);
        headerTittleTVID.setText("PLOT NO " + projectName);
        headerTittleTVID.setTextColor(Color.parseColor(plotColor));
        ImageView backID = findViewById(R.id.backID);
        backID.setColorFilter(Color.parseColor(plotColor));

        getPlotsData(projectId);
        plotBGID = findViewById(R.id.plotBGID);
        plotBGID.setVisibility(View.VISIBLE);

        plotBGID.setBackgroundColor(Color.parseColor(plotColor));
        viewID = findViewById(R.id.viewID);
        viewID1 = findViewById(R.id.viewID1);
        viewID2 = findViewById(R.id.viewID2);
        viewID3 = findViewById(R.id.viewID3);
        viewID4 = findViewById(R.id.viewID4);
        viewID5 = findViewById(R.id.viewID5);

        viewID.setBackgroundColor(Color.parseColor(plotColor));
        viewID1.setBackgroundColor(Color.parseColor(plotColor));
        viewID2.setBackgroundColor(Color.parseColor(plotColor));
        viewID3.setBackgroundColor(Color.parseColor(plotColor));
        viewID4.setBackgroundColor(Color.parseColor(plotColor));
        viewID5.setBackgroundColor(Color.parseColor(plotColor));
    }

    private void getPlotsData(String projectId) {

        cvID.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<PlotDetailsResponse>> call = apiInterface.getPlotDetails(projectId, plotID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<PlotDetailsResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<PlotDetailsResponse>> call, Response<ArrayList<PlotDetailsResponse>> response) {
                cvID.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200) {
                    ArrayList<PlotDetailsResponse> detailsResponses = response.body();
                    if (detailsResponses.size() > 0) {

                        for (int i = 0; i < detailsResponses.size(); i++) {
                            sftTVID.setText(detailsResponses.get(i).getUnit() );
                            plostsizeTVID.setText(detailsResponses.get(i).getArea() );
                            priceTVID.setText("₹ " + detailsResponses.get(i).getUnit_price());
                            costTVID.setText(detailsResponses.get(i).getFacing());
                            infraTVID.setText("₹ " + detailsResponses.get(i).getPremium());
                            mainTVID.setText("₹" + detailsResponses.get(i).getUnit_price());
                            bhkTVID.setText(detailsResponses.get(i).getBhk());
                            floorTVID.setText(detailsResponses.get(i).getFloor());
                            builtupTVID.setText(detailsResponses.get(i).getBuiltup());
                            unitpriceTVID.setText("₹"+detailsResponses.get(i).getUnit_price());
                            unitcostTVID.setText("₹"+detailsResponses.get(i).getUnit_cost());
                            premiumTVID.setText("₹"+detailsResponses.get(i).getPremium());
                            ammenitiesTVID.setText("₹"+detailsResponses.get(i).getAmmenities());
                            totalTVID.setText("₹"+detailsResponses.get(i).getTotal());



                        }

                    } else {
                        Toast.makeText(PlotDetailsActivity.this, "Details not loading", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PlotDetailsActivity.this, "Details not loading", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PlotDetailsResponse>> call, Throwable t) {
                cvID.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                Toast.makeText(PlotDetailsActivity.this, "Details not loading", Toast.LENGTH_SHORT).show();

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
