package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DueCustomersProjectsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AddSaleHistoryResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueCustomersMainResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueCustomersResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CPSaleHistory extends AppCompatActivity implements DueCustomersProjectsAdapter.DueCustomerProjectClickListener {

    String agentid;

    TextView agentTVID,recievedTVID,totalsaleTVID,dueamountTVID,commissionTVID,eligibleTVID,commissionpaidTVID,presentdueTVID,commissiondueTVID;
    String agentTVIDstr,recievedTVIDstr,totalsaleTVIDstr,dueamountTVIDstr,commissionTVIDstr,eligibleTVIDstr,commissionpaidTVIDstr,presentdueTVIDstr,commissiondueTVIDstr;
    TextView tittle,preNoDataPB;
    CardView salehistoryCVID;

    RecyclerView projectsRVID;
    ProgressBar pb;

    ArrayList<DueCustomersMainResponse> dueCustomersMainResponses = new ArrayList<>();
    ArrayList<DueCustomersResponse> cust = new ArrayList<>();
    DueCustomersProjectsAdapter adapter;
    LinearLayout directSaleLLID,teamSalesLLID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p_sale_history);

      /*  FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount() == 1) finish();
            }
        });*/
        RelativeLayout back = findViewById(R.id.headerBackRLID);
        // backRLID.setOnClickListener(v -> Utilities.finishAnimation(CPTravel_Request.this));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* Intent intent = new Intent(getApplicationContext(), FragmentCP.class);
                startActivity(intent);*/
               Utilities.finishAnimation(CPSaleHistory.this);
               MySharedPreferences.setPreference(CPSaleHistory.this, "" + AppConstants.CP_VIEW, "YES");

            }
        });
        tittle = findViewById(R.id.headerTitleTVID);
        tittle.setText("Sale History");
        preNoDataPB = findViewById(R.id.preNoDataPB);
        agentid = MySharedPreferences.getPreferences(getApplicationContext(), "" + AppConstants.CP_USER_ID);

        agentTVID = findViewById(R.id.agentTVID);
        recievedTVID = findViewById(R.id.recievedTVID);
        totalsaleTVID = findViewById(R.id.totalsaleTVID);
        dueamountTVID = findViewById(R.id.dueamountTVID);
        commissionTVID = findViewById(R.id.commissionTVID);
        eligibleTVID = findViewById(R.id.eligibleTVID);
        commissionpaidTVID = findViewById(R.id.commissionpaidTVID);
        presentdueTVID = findViewById(R.id.presentdueTVID);
        commissiondueTVID = findViewById(R.id.commissiondueTVID);
        salehistoryCVID = findViewById(R.id.salehistoryCVID);
        directSaleLLID = findViewById(R.id.directSaleLLID);
        teamSalesLLID = findViewById(R.id.teamSalesLLID);


        pb = findViewById(R.id.pb);

       /* salehistoryCVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DueCustomersActivity.class);
                startActivity(intent);
            }
        });*/

        directSaleLLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DueCustomersActivity.class);
                intent.putExtra("TYPE", "1");
                startActivity(intent);
            }
        });

        teamSalesLLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DueCustomersActivity.class);
                intent.putExtra("TYPE", "2");
                startActivity(intent);
            }
        });

       // RelativeLayout backRLID = findViewById(R.id.backRLID);

      /*  backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.finishAnimation(CPSaleHistory.this);
            }
        });*/
        SaleHistory();

        if (Utilities.isNetworkAvailable(this)){
            //loadProjects();
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }

    }

   /* private void loadProjects() {

        pb.setVisibility(View.VISIBLE);
        projectsRVID.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DueCustomersMainResponse>> call = apiInterface.getDueCustomers();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DueCustomersMainResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DueCustomersMainResponse>> call, Response<ArrayList<DueCustomersMainResponse>> response) {

                pb.setVisibility(View.GONE);
                projectsRVID.setVisibility(View.VISIBLE);

                if (response.body() != null && response.code() == 200){

                    dueCustomersMainResponses = response.body();

                    for (int i = 0; i < dueCustomersMainResponses.size(); i++) {

                       cust = dueCustomersMainResponses.get(i).getCust();
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(CPSaleHistory.this,LinearLayoutManager.VERTICAL,false);
                    projectsRVID.setLayoutManager(layoutManager);

                    adapter = new DueCustomersProjectsAdapter(dueCustomersMainResponses,CPSaleHistory.this,CPSaleHistory.this);
                    projectsRVID.setAdapter(adapter);



                }
            }

            @Override
            public void onFailure(Call<ArrayList<DueCustomersMainResponse>> call, Throwable t) {

            }
        });
    }*/

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }*/

    private void SaleHistory() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AddSaleHistoryResponse>> call = apiInterface.getsalehistory(agentid);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<AddSaleHistoryResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<AddSaleHistoryResponse>> call, Response<ArrayList<AddSaleHistoryResponse> > response) {
                salehistoryCVID.setVisibility(View.VISIBLE);
                preNoDataPB.setVisibility(View.INVISIBLE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<AddSaleHistoryResponse> addCustomerResponse = response.body();
                    if (addCustomerResponse.size() > 0) {
                        for (int i = 0; i < addCustomerResponse.size(); i++) {

                           /* lists.clear();
                            lists.add(new PerformanceList("Month Performance", "", "" + countResponses.get(i).getMnth_enq(), "" + countResponses.get(i).getMnth_sitevisit(), "" + countResponses.get(i).getMnth_sale(), "0.00%", "", "", ""));
                            lists.add(new PerformanceList("Overall Performance", "", "" + countResponses.get(i).getOverall_enq(), "" + countResponses.get(i).getOverall_sitevisit(), "" + countResponses.get(i).getOverall_sale(), "0.00%", "", "", ""));
                            myViewPagerAdapter = new PerformanceAdapter(getActivity(), lists);
                            viewPager.setAdapter(myViewPagerAdapter);
                            viewPager.setPageMargin(64);*/
                            // addBottomDots(0);
                            // viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


                            try {
                                agentTVID.setText(addCustomerResponse.get(i).getAgent_count());
                                recievedTVID.setText(addCustomerResponse.get(i).getTotal_sale());
                                totalsaleTVID.setText(addCustomerResponse.get(i).getTeam_sale());
                                dueamountTVID.setText(addCustomerResponse.get(i).getDue_amt() +" Points");
                                commissionTVID.setText(addCustomerResponse.get(i).getComm()+" Points");
                                eligibleTVID.setText(addCustomerResponse.get(i).getEligible()+" Points");
                                commissionpaidTVID.setText(addCustomerResponse.get(i).getComm_paid()+" Points");
                                presentdueTVID.setText(addCustomerResponse.get(i).getP_due()+" Points");
                                commissiondueTVID.setText(addCustomerResponse.get(i).getComm_due()+" Points");





                                // Picasso.with(DashBoardActivity.this).load(countResponses.get(i).getPic()).into(crmProfilePic);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        salehistoryCVID.setVisibility(View.INVISIBLE);
                        preNoDataPB.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    salehistoryCVID.setVisibility(View.INVISIBLE);
                    preNoDataPB.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }

             /*   if (response.body() != null && response.code() == 200) {
                    ArrayList<AddSaleHistoryResponse> addCustomerResponse = response.body();
                    if (addCustomerResponse != null) {
                        for (int i = 0; i < addCustomerResponse.size(); i++) {
                            agentTVID.setText(addCustomerResponse.get(i).getAgent_count());

                          //  agentTVIDstr = addCustomerResponse.get(i).getAgent_count();
                            //agentTVID.setText(agentTVIDstr);
                            recievedTVIDstr= addCustomerResponse.get(i).getReceived_amt();
                            recievedTVID.setText(recievedTVIDstr);

                            totalsaleTVIDstr = addCustomerResponse.get(i).getTotal_sale();
                            totalsaleTVID.setText(totalsaleTVIDstr);

                            dueamountTVIDstr = addCustomerResponse.get(i).getAgent_count();
                            dueamountTVID.setText(dueamountTVIDstr);

                            commissionTVIDstr = addCustomerResponse.get(i).getComm();
                            commissionTVID.setText(commissionTVIDstr);

                            eligibleTVIDstr = addCustomerResponse.get(i).getEligible();
                            eligibleTVID.setText(eligibleTVIDstr);

                            commissionpaidTVIDstr = addCustomerResponse.get(i).getComm_paid();
                            commissionpaidTVID.setText(commissionpaidTVIDstr);

                            presentdueTVIDstr = addCustomerResponse.get(i).getP_due();
                            presentdueTVID.setText(presentdueTVIDstr);

                            commissiondueTVIDstr = addCustomerResponse.get(i).getComm_due();
                            commissiondueTVID.setText(commissiondueTVIDstr);




                            //  Utilities.showToast(CreateIndent.this, "Added Customer");

                            //  SuccessAlertDialog("Lead Created Successfully");
                        }


                    }else {
                        Toast.makeText(getActivity(), "Error at server side", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }*/
            }

            @Override
            public void onFailure(Call<ArrayList<AddSaleHistoryResponse> >call, Throwable t) {
                //  Utilities.showToast(CreateLeadActivity.this, "Error : " + t.getMessage());
            }
        });

    }

    @Override
    public void onDueCustomerProjectItemClick(DueCustomersMainResponse response, View v, int pos, DueCustomersProjectsAdapter.DueCustomerProjectVH holder) {

        switch (v.getId()) {
            case R.id.projectRLID:


                Intent intent = new Intent(CPSaleHistory.this,DueCustomersActivity.class);
                intent.putExtra("DATA",response.getCust());
                intent.putExtra("PROJECT_ID",response.getProject_id());
                intent.putExtra("PROJECT_NAME",response.getProject_name());
                startActivity(intent);

                break;
        }
    }
}