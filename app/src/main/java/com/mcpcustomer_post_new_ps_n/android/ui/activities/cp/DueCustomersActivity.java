package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DueCustomersAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueCustomersResponse;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DueCustomersActivity extends AppCompatActivity implements DueCustomersAdapter.DueCustomerClickListener {

    RelativeLayout backRLID;
    TextView headerTittleTVID;
    RecyclerView dueCustomersRVID;
    ProgressBar pb;
    AppCompatTextView noDataTVID;
    ArrayList<DueCustomersResponse> dueCustomersResponses = new ArrayList<>();
    DueCustomersAdapter adapter;
    String projectName,projectID,agentid,type;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_customers);

        if (getIntent()!=null){
            Bundle bundle = getIntent().getExtras();
            if (bundle!=null){
                dueCustomersResponses = (ArrayList<DueCustomersResponse>)bundle.getSerializable("DATA");
                projectName = bundle.getString("PROJECT_NAME");
                projectID = bundle.getString("PROJECT_ID");
                type = bundle.getString("TYPE");
            }
        }

        agentid = MySharedPreferences.getPreferences(getApplicationContext(), "" + AppConstants.CP_USER_ID);

        backRLID = findViewById(R.id.backRLID);
        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        dueCustomersRVID = findViewById(R.id.dueCustomersRVID);
        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);

        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
        });

        headerTittleTVID.setText("Due Customers");

        if (Utilities.isNetworkAvailable(this)){
            loadDueCustomers();
        }
    }

    private void loadDueCustomers() {

        pb.setVisibility(View.VISIBLE);
        dueCustomersRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DueCustomersResponse>> call = apiInterface.getDueCustomers(agentid,type);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DueCustomersResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DueCustomersResponse>> call, Response<ArrayList<DueCustomersResponse>> response) {

                pb.setVisibility(View.GONE);
                dueCustomersRVID.setVisibility(View.VISIBLE);
                noDataTVID.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200){

                    dueCustomersResponses = response.body();

                    if (dueCustomersResponses.size() > 0 ){

                        LinearLayoutManager layoutManager = new LinearLayoutManager(DueCustomersActivity.this,LinearLayoutManager.VERTICAL,false);
                        dueCustomersRVID.setLayoutManager(layoutManager);
                        adapter = new DueCustomersAdapter(dueCustomersResponses,DueCustomersActivity.this,DueCustomersActivity.this,type);
                        dueCustomersRVID.setAdapter(adapter);
                    }else {
                        pb.setVisibility(View.GONE);
                        dueCustomersRVID.setVisibility(View.GONE);
                        noDataTVID.setVisibility(View.VISIBLE);
                    }
                }else {
                    pb.setVisibility(View.GONE);
                    dueCustomersRVID.setVisibility(View.GONE);
                    noDataTVID.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DueCustomersResponse>> call, Throwable t) {

                pb.setVisibility(View.GONE);
                dueCustomersRVID.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onDueCustomerItemClick(DueCustomersResponse response, View v, int pos, DueCustomersAdapter.DueCustomerVH holder) {

        switch (v.getId()) {
            case R.id.whatsAppCallRLID:

                loadWhatsApp(response);

                break;

            case R.id.callRLID:

                callToCustomer(response.getMobile_no());

                break;
        }
    }

    private void callToCustomer(String mobile_no) {

        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mobile_no));
                startActivity(callIntent);
                // Log.d("CONSTRUCTOR_ID_EXE_1->", "" + callID);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mobile_no));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadWhatsApp(DueCustomersResponse response) {

        final BottomSheetDialog dialog1 = new BottomSheetDialog(DueCustomersActivity.this);
        dialog1.setContentView(R.layout.alert_business_whatsapp);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        int width1 = ViewGroup.LayoutParams.MATCH_PARENT;
        int height1 = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog1.getWindow().setLayout(width1, height1);
        dialog1.show();

        AppCompatImageView whatsappIVID = dialog1.findViewById(R.id.whatsappIVID);
        AppCompatImageView businessWhatsappIVID = dialog1.findViewById(R.id.businessWhatsappIVID);

        whatsappIVID.setOnClickListener(view1 -> {

            try {
                Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + "+91" + response.getMobile_no() + "&text=" + URLEncoder.encode("", "UTF-8");
                sendMsg.setPackage("com.whatsapp");
                sendMsg.setData(Uri.parse(url));
                startActivity(sendMsg);
                dialog1.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                /*Utilities.showToast(this,e.getMessage());*/
                Toast.makeText(DueCustomersActivity.this, "You don't have WhatsApp in your device", Toast.LENGTH_SHORT).show();
            }

        });

        businessWhatsappIVID.setOnClickListener(view2 -> {

            try {

                Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + "+91" + response.getMobile_no() + "&text=" + URLEncoder.encode("", "UTF-8");
                sendMsg.setPackage("com.whatsapp.w4b");
                sendMsg.setData(Uri.parse(url));

                startActivity(sendMsg);
                dialog1.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                /*Utilities.showToast(this,e.getMessage());*/
                Toast.makeText(DueCustomersActivity.this, "You don't have business WhatsApp in your device", Toast.LENGTH_SHORT).show();
            }

            dialog1.dismiss();

        });
    }
}