package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.PaginationScrollListener;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.SiteVisitsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SiteVisitResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiteVisitActivity extends AppCompatActivity {

    RelativeLayout backRLID,refreshRLID;
    ProgressBar pb;
    AppCompatTextView noDataTVID;
    TextView headerTittleTVID;
    String agentID;
    

    int start = 0;
    boolean isLoading;
    boolean isLastPage;
    public static boolean isClickable = true;
    LinearLayoutManager linearLayoutManager;
    SiteVisitsAdapter adapter;
    ArrayList<SiteVisitResponse> siteVisitResponses = new ArrayList<>();
    RecyclerView siteVisitRVID;
    FloatingActionButton fabAddBtn;


    @SuppressLint({"SetTextI18n", "WrongThread"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_visit);
        MySharedPreferences.setPreference(this, "" + AppConstants.PAGE_REFRESH, "" + AppConstants.NO);
        

        backRLID = findViewById(R.id.backRLID);
        refreshRLID = findViewById(R.id.refreshRLID);

        refreshRLID.setVisibility(View.VISIBLE);

        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);
        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        fabAddBtn = findViewById(R.id.fabAddBtn);
        agentID = MySharedPreferences.getPreferences(this, "" + AppConstants.CP_USER_ID);



        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(SiteVisitActivity.this, "" + AppConstants.CP_LOGIN_STATUS, "10");
            MySharedPreferences.setPreference(SiteVisitActivity.this, "" + AppConstants.CP_VIEW, "YES");
        });

        headerTittleTVID.setText("Site Visits");

        fabAddBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this,CreateSiteVisitActivity.class);
            startActivity(intent);
        });

        if (Utilities.isNetworkAvailable(this)){
            loadCreatives();
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }

        refreshRLID.setOnClickListener(v -> {
            loadCreatives();
        });


    }



    @SuppressLint("NotifyDataSetChanged")
    private void loadCreatives() {

        start = 0;
        isLoading=false;
        isLastPage=false;
        siteVisitRVID = findViewById(R.id.siteVisitRVID);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new SiteVisitsAdapter(this,siteVisitResponses);
        siteVisitRVID.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        siteVisitRVID.setLayoutManager(linearLayoutManager);
        siteVisitRVID.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.d("pagination","api called");
                isLastPage =false;
                isLoading = true;
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                Log.d("pagination","is last page");
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                Log.d("pagination","is loading");
                return isLoading;
            }
        });

        loadFirstPage();
    }

    private void loadFirstPage() {

        pb.setVisibility(View.VISIBLE);
        siteVisitRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);
        isClickable = false;

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<SiteVisitResponse>> call = apiInterface.getSiteVisits(agentID, start);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<SiteVisitResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<SiteVisitResponse>> call, Response<ArrayList<SiteVisitResponse>> response) {

                pb.setVisibility(View.GONE);
                siteVisitRVID.setVisibility(View.VISIBLE);
                noDataTVID.setVisibility(View.GONE);
                //refreshFab.setVisibility(View.VISIBLE);
                isClickable = false;

                if (response.body() != null && response.code() == 200) {
                    siteVisitResponses.clear();
                    siteVisitResponses = response.body();
                    if (siteVisitResponses.size() > 0) {
                        isClickable = true;
                        startedValues();
                        adapter.addAll(siteVisitResponses);

                        if (siteVisitResponses.size() == 20) {
                            adapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    } else {
                        setErrorViews();
                    }
                } else {
                    setErrorViews();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SiteVisitResponse>> call, Throwable t) {
                setErrorViews();
            }
        });

    }

    private void loadNextPage() {

        Log.d("START_VALUE",""+start);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<SiteVisitResponse>> call = apiInterface.getSiteVisits(agentID, start);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<SiteVisitResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<SiteVisitResponse>> call, Response<ArrayList<SiteVisitResponse>> response) {
                isClickable = true;
                if (response.body() != null && response.code() == 200) {
                    siteVisitResponses = response.body();
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    if (siteVisitResponses.size() > 0) {
                        adapter.addAll(siteVisitResponses);

                        if (siteVisitResponses.size() == 20) {
                            startedValues();
                            adapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                            adapter.removeLoadingFooter();
                        }
                    } else {
                        adapter.removeLoadingFooter();
                    }
                } else {
                    adapter.removeLoadingFooter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SiteVisitResponse>> call, Throwable t) {
                Log.d("ERROR : ", "" + t.getMessage());
                adapter.removeLoadingFooter();
            }
        });
    }


    private void setErrorViews() {
        isClickable = true;
        pb.setVisibility(View.GONE);
        siteVisitRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.VISIBLE);
    }

    private void startedValues() {
        start = start + 20;
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        loadCreatives();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        String pageRefresh = MySharedPreferences.getPreferences(this, "" + AppConstants.PAGE_REFRESH);
        if (pageRefresh.equalsIgnoreCase(AppConstants.YES)) {
           loadCreatives();
        }
    }

}