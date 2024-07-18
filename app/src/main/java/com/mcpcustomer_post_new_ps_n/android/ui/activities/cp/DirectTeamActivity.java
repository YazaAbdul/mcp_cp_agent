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
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectteamcountRespon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectTeamActivity extends AppCompatActivity implements DirectTeamAdapter.DirectTeamClickListener {

    AppCompatTextView noDataTVID;
    ProgressBar pb;
    RecyclerView directTeamRVID;
    RelativeLayout backRLID;
    TextView headerTittleTVID;
    ArrayList<DirectTeamResponse> directTeamResponses = new ArrayList<>();
    ArrayList<DirectteamcountRespon> directteamcountRespons_ = new ArrayList<>();
    DirectTeamMainResponse directTeamMainResponse;
    DirectTeamCountResponse directTeamCountResponse;
    DirectTeamAdapter adapter;
    String agentID="",type;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_team);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                type = bundle.getString("TYPE");
                agentID = bundle.getString("ID");

            }
        }

        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        backRLID = findViewById(R.id.backRLID);
        directTeamRVID = findViewById(R.id.directTeamRVID);
        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);
       // agentID = MySharedPreferences.getPreferences(getApplicationContext(), "" + AppConstants.CP_USER_ID);

        if( agentID==null){
            agentID = MySharedPreferences.getPreferences(getApplicationContext(), "" + AppConstants.CP_USER_ID);
        }




        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(DirectTeamActivity.this, "" + AppConstants.CP_VIEW, "YES");

        });

        if (Utilities.isNetworkAvailable(this)){
            loadDirectTeam();

        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }


    }

    private void showCount() {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DirectteamcountRespon>> call = apiInterface.Getdirectteamcount(agentID);
       Log.e("api==>",call.request().toString());
      call.enqueue(new Callback<ArrayList<DirectteamcountRespon>>() {
          @Override
          public void onResponse(Call<ArrayList<DirectteamcountRespon>> call, Response<ArrayList<DirectteamcountRespon>> response) {
              if (response.body() != null && response.code() == 200){
              directteamcountRespons_=response.body();
              if(directteamcountRespons_.size()>0){
                  headerTittleTVID.setText("Direct Team ("+directteamcountRespons_.get(0).getCount()+")");
              }
          }
          }

          @Override
          public void onFailure(Call<ArrayList<DirectteamcountRespon>> call, Throwable t) {

          }
      });


    }

    private void loadDirectTeam() {

        pb.setVisibility(View.VISIBLE);
        directTeamRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);

        Call<DirectTeamMainResponse> call = null;
        if (type.equalsIgnoreCase("1")) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            call = apiInterface.getDirectTeam(agentID);
        } else if(type.equalsIgnoreCase("2")) {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            call = apiInterface.getTotalTeam(agentID);
        }else if(type.equalsIgnoreCase("3")){
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            call = apiInterface.getdirectagentteam(agentID);
        }
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

                    if (type.equalsIgnoreCase("1")) {
                       headerTittleTVID.setText("Direct Team" + " ("+directTeamCountResponse.getAssociates_count() +")");
                    } else if(type.equalsIgnoreCase("2")) {
                        headerTittleTVID.setText("Total Team" + " ("+directTeamCountResponse.getAssociates_count() +")");
                    }else if(type.equalsIgnoreCase("3")){
                        showCount();
                     //   headerTittleTVID.setText("Direct Team " );
                    }

                    if (directTeamResponses.size() > 0){

                        LinearLayoutManager layoutManager = new LinearLayoutManager(DirectTeamActivity.this,RecyclerView.VERTICAL,false);
                        directTeamRVID.setLayoutManager(layoutManager);

                        adapter = new DirectTeamAdapter(directTeamResponses,DirectTeamActivity.this,DirectTeamActivity.this,type);
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

                if(type.equalsIgnoreCase("3")){
                    Intent intent = new Intent(DirectTeamActivity.this, DirectTeamActivity.class);
                    intent.putExtra("ID",response.getAgent_id());
                    intent.putExtra("TYPE","3");
                    startActivity(intent);
                }else if(type.equalsIgnoreCase("2")){
                    Intent intent = new Intent(DirectTeamActivity.this, DirectTeamActivity.class);
                    intent.putExtra("ID",response.getAgent_id());
                    intent.putExtra("TYPE","2");
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(DirectTeamActivity.this, DirectTeamActivity_inside.class);
                    intent.putExtra("ID",response.getAgent_id());
                    startActivity(intent);
                }



        }




    }

}