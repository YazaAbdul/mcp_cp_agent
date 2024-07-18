package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ClubDetailsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ClubDetailsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubDetailsActivity extends AppCompatActivity {

    RecyclerView clubDetailsRVID;
    AppCompatTextView noDataTVID;
    ProgressBar pb;
    RelativeLayout backRLID;
    TextView headerTittleTVID;
    ArrayList<ClubDetailsResponse> clubDetailsResponses = new ArrayList<>();
    ClubDetailsAdapter clubDetailsAdapter;
    String agentID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        backRLID =findViewById(R.id.backRLID);
        headerTittleTVID =findViewById(R.id.headerTittleTVID);
        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);
        clubDetailsRVID = findViewById(R.id.clubDetailsRVID);

        headerTittleTVID.setText("IMARK CLUB");


        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "YES");
        });

        agentID =MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_ID);

        if (Utilities.isNetworkAvailable(this)){
            loadClubDetails();
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadClubDetails() {

        pb.setVisibility(View.VISIBLE);
        clubDetailsRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ClubDetailsResponse>> call = apiInterface.getClubDetails(agentID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ClubDetailsResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ClubDetailsResponse>> call, Response<ArrayList<ClubDetailsResponse>> response) {

                pb.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.GONE);
                clubDetailsRVID.setVisibility(View.VISIBLE);

                if (response.body() != null && response.code() == 200){
                    

                    clubDetailsResponses = response.body();

                    if (clubDetailsResponses.size() > 0){

                        LinearLayoutManager layoutManager = new LinearLayoutManager(ClubDetailsActivity.this,RecyclerView.VERTICAL,false);
                        clubDetailsRVID.setLayoutManager(layoutManager);

                        clubDetailsAdapter = new ClubDetailsAdapter(clubDetailsResponses,ClubDetailsActivity.this);
                        clubDetailsRVID.setAdapter(clubDetailsAdapter);

                    }else {
                        noDataTVID.setVisibility(View.VISIBLE);
                        clubDetailsRVID.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                    }


                }else {
                    noDataTVID.setVisibility(View.VISIBLE);
                    clubDetailsRVID.setVisibility(View.GONE);
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ClubDetailsResponse>> call, Throwable t) {

                noDataTVID.setVisibility(View.VISIBLE);
                clubDetailsRVID.setVisibility(View.GONE);
                pb.setVisibility(View.GONE);

            }
        });
    }
}