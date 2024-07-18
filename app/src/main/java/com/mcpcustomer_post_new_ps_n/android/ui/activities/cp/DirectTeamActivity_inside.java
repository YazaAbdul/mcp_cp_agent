package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DirectTeamAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamCountResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamMainResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectTeamActivity_inside extends AppCompatActivity implements DirectTeamAdapter.DirectTeamClickListener {


    AppCompatTextView noDataTVID;
    ProgressBar pb;
    RecyclerView directTeamRVID;
    RelativeLayout backRLID;
    TextView headerTittleTVID;
    ArrayList<DirectTeamResponse> directTeamResponses = new ArrayList<>();
    DirectTeamMainResponse directTeamMainResponse;
    DirectTeamCountResponse directTeamCountResponse;
    DirectTeamAdapter adapter;
    String agentID,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_team_inside);




        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                agentID = bundle.getString("ID");
            }
        }

        Log.e("api==>", "type: "+type);

        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        backRLID = findViewById(R.id.backRLID);
        directTeamRVID = findViewById(R.id.directTeamRVID);
        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);



        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(DirectTeamActivity_inside.this, "" + AppConstants.CP_VIEW, "YES");

        });
        if (Utilities.isNetworkAvailable(this)){
            loadDirectTeam();
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }




    }
    private void loadDirectTeam() {

        pb.setVisibility(View.VISIBLE);
        directTeamRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);

        Call<DirectTeamMainResponse> call = null;

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        call = apiInterface.getTotalTeam(agentID);

        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<DirectTeamMainResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DirectTeamMainResponse> call, Response<DirectTeamMainResponse> response) {

                pb.setVisibility(View.GONE);
                directTeamRVID.setVisibility(View.VISIBLE);
                noDataTVID.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200){

                    directTeamMainResponse = response.body();

                    directTeamResponses = directTeamMainResponse.getAssociates();
                    directTeamCountResponse = directTeamMainResponse.getCount();


                        headerTittleTVID.setText("Direct Team" + " ("+directTeamCountResponse.getAssociates_count() +")");


                    if (directTeamResponses.size() > 0){

                        LinearLayoutManager layoutManager = new LinearLayoutManager(DirectTeamActivity_inside.this,RecyclerView.VERTICAL,false);
                        directTeamRVID.setLayoutManager(layoutManager);

                        adapter = new DirectTeamAdapter(directTeamResponses,DirectTeamActivity_inside.this,DirectTeamActivity_inside.this,"1");
                        directTeamRVID.setAdapter(adapter);
                    }else {
                        pb.setVisibility(View.GONE);
                        directTeamRVID.setVisibility(View.GONE);
                        noDataTVID.setVisibility(View.VISIBLE);
                    }
                }else {
                    pb.setVisibility(View.GONE);
                    directTeamRVID.setVisibility(View.GONE);
                    noDataTVID.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DirectTeamMainResponse> call, Throwable t) {

                pb.setVisibility(View.GONE);
                directTeamRVID.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onDirectTeamItemClick(DirectTeamResponse response, View v, int pos, DirectTeamAdapter.DirectTeamVH holder) {

        switch (v.getId()) {
            case R.id.newMainLeadView:
                Intent intent = new Intent(DirectTeamActivity_inside.this, DirectTeamActivity_inside.class);
                intent.putExtra("ID",response.getAgent_id());
                startActivity(intent);

        }


    }
}