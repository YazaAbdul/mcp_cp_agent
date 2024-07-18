package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.MelaUpdateAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MelaResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MelaUpdateActivity extends AppCompatActivity implements MelaUpdateAdapter.MelaClickListener {

    TextView headerTittleTVID;
    RelativeLayout backRLID;
    RecyclerView melaUpdateRVID;
    ProgressBar pb;

    ArrayList<MelaResponse> melaResponses = new ArrayList<>();
    MelaUpdateAdapter adapter;
    AppCompatTextView noDataTVID;
    String refresh,userID;
    String melaID,melaPlanID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mela_update);

        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        backRLID = findViewById(R.id.backRLID);
        melaUpdateRVID = findViewById(R.id.melaUpdateRVID);
        pb = findViewById(R.id.pb);
        noDataTVID = findViewById(R.id.noDataTVID);
        userID = MySharedPreferences.getPreferences(this, "" + AppConstants.CP_USER_ID);

        refresh = MySharedPreferences.getPreferences(this,"REFRESH");

        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(MelaUpdateActivity.this, "" + AppConstants.CP_VIEW, "YES");
        });

        headerTittleTVID.setText("Mela Update");

        if (refresh.equalsIgnoreCase("YES")){
            loadMelaUpdate();
        }

        if (Utilities.isNetworkAvailable(this)) {
            loadMelaUpdate();
        } else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMelaUpdate() {

        pb.setVisibility(View.VISIBLE);
        melaUpdateRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<MelaResponse>> call = apiInterface.getMela(userID);
        Log.e("getAllMela==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<MelaResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<MelaResponse>> call, Response<ArrayList<MelaResponse>> response) {

                pb.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.GONE);
                melaUpdateRVID.setVisibility(View.VISIBLE);

                if (response.body() != null && response.code() == 200){
                    melaResponses = response.body();
                    if (melaResponses.size() > 0){
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MelaUpdateActivity.this,LinearLayoutManager.VERTICAL,false);
                        melaUpdateRVID.setLayoutManager(layoutManager);
                        melaUpdateRVID.setLayoutManager(layoutManager);

                        adapter = new MelaUpdateAdapter(MelaUpdateActivity.this,melaResponses,MelaUpdateActivity.this);
                        melaUpdateRVID.setAdapter(adapter);
                    }else {
                        pb.setVisibility(View.GONE);
                        noDataTVID.setVisibility(View.VISIBLE);
                        melaUpdateRVID.setVisibility(View.GONE);                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MelaResponse>> call, Throwable t) {
                pb.setVisibility(View.GONE);
                noDataTVID.setVisibility(View.VISIBLE);
                melaUpdateRVID.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMelaItemClick(MelaResponse response, View v, int pos, MelaUpdateAdapter.MelaUpdateVH holder) {
        switch (v.getId()) {
            case R.id.melaUpdateLLID:

                try {
                    if (response.getMobile_logic().equalsIgnoreCase("0")) {
                        Dialog dialog = new Dialog(MelaUpdateActivity.this);
                        dialog.setContentView(R.layout.mela_plan_alert);
                        int height2 = ViewGroup.LayoutParams.WRAP_CONTENT;
                        int width2 = ViewGroup.LayoutParams.MATCH_PARENT;
                        dialog.getWindow().setLayout(width2, height2);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                        AppCompatEditText customerETID,associateETID;
                        AppCompatButton submitBtn;

                        melaID = response.getS_no();

                        Log.e("mela_id==>","" + melaID);

                        customerETID = dialog.findViewById(R.id.customerETID);
                        associateETID = dialog.findViewById(R.id.associateETID);
                        submitBtn = dialog.findViewById(R.id.submitBtn);

                        submitBtn.setOnClickListener(v1 -> {
                            melaValidations(customerETID,associateETID,userID,melaID,dialog, holder.melaPlansLLID,holder.pb,holder.melaPlanVID);
                        });
                    } else {

                        Toast.makeText(this, "Already Mela Plan is There", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.melaPlanDeleteRLID:

                deleteMela(response.getMela_id());

                break;

            case R.id.melaPlanUpdateLLID:

                getAllMelaAlert(response.getMela_id(),holder.melaPlansLLID,holder.pb,holder.melaPlanVID);

                Log.e("Mela_update==>","" + response.getMela_id() );

                break;
        }
    }

    private void melaValidations(AppCompatEditText customerETID, AppCompatEditText associateETID, String userID, String melaID, Dialog dialog, LinearLayout melaPlansLLID, ProgressBar pb, View melaPlanVID) {

        String customerStr,associateStr;

        customerStr = customerETID.getText().toString();
        associateStr = associateETID.getText().toString();

        if (TextUtils.isEmpty(customerStr)){
            Toast.makeText(this, "Enter Customer Count", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(associateStr)) {
            Toast.makeText(this, "Enter Associate Count", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Utilities.isNetworkAvailable(this)){

            getMelaUpdate(customerStr,associateStr,userID,melaID,dialog,melaPlansLLID,pb,melaPlanVID);
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
            
        }
    }

    private void getMelaUpdate(String customerStr, String associateStr, String userID, String melaID, Dialog dialog,  LinearLayout melaPlansLLID, ProgressBar pb, View melaPlanVID) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponseNew>> call = apiInterface.getMelaPlan(melaID,userID,customerStr,associateStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<StatusResponseNew>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseNew>> call, Response<ArrayList<StatusResponseNew>> response) {
                if (response.body() != null && response.code() == 200){
                    ArrayList<StatusResponseNew> statusResponseNew = new ArrayList<>();
                    statusResponseNew = response.body();

                    if (statusResponseNew.get(0).getStatus().equalsIgnoreCase("Inserted")){
                        Toast.makeText(MelaUpdateActivity.this, "Mela Plan Inserted", Toast.LENGTH_SHORT).show();

                        loadMelaUpdate();
                    }else {
                        Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseNew>> call, Throwable t) {

                Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.dismiss();
    }

    private void getAllMelaAlert(String s_no, LinearLayout melaPlansLLID, ProgressBar pb, View melaPlanVID) {

        Dialog dialog = new Dialog(MelaUpdateActivity.this);
        dialog.setContentView(R.layout.mela_plan_alert);
        int height2 = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width2 = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width2, height2);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        AppCompatEditText customerETID,associateETID;
        AppCompatButton submitBtn;

        //melaID = melaResponses.get(position).getS_no();

        //Log.e("mela_id==>","" + melaResponses.get(position).getS_no());



        customerETID = dialog.findViewById(R.id.customerETID);
        associateETID = dialog.findViewById(R.id.associateETID);
        submitBtn = dialog.findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(v1 -> {
            validationsAllPlan(customerETID,associateETID,userID,melaID,dialog, melaPlansLLID,pb,melaPlanVID,s_no);
        });
    }

    private void validationsAllPlan(AppCompatEditText customerETID, AppCompatEditText associateETID, String userID, String melaID, Dialog dialog, LinearLayout melaPlansLLID, ProgressBar pb, View melaPlanVID, String s_no) {

        String customerStr,associateStr;

        customerStr = customerETID.getText().toString();
        associateStr = associateETID.getText().toString();

        if (TextUtils.isEmpty(customerStr)){
            Toast.makeText(MelaUpdateActivity.this, "Enter Customer Count", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(associateStr)) {
            Toast.makeText(MelaUpdateActivity.this, "Enter Associate Count", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Utilities.isNetworkAvailable(MelaUpdateActivity.this)){

            getAllMelaUpdate(customerStr,associateStr,userID,melaID,dialog,melaPlansLLID,pb,melaPlanVID,s_no);
        }else {
            Toast.makeText(MelaUpdateActivity.this, "Please check your network", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllMelaUpdate(String customerStr, String associateStr, String userID, String melaID, Dialog dialog, LinearLayout melaPlansLLID, ProgressBar pb, View melaPlanVID, String s_no) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatusResponseNew>call = apiInterface.getAllMelaPlanUpdate(s_no,userID,customerStr,associateStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<StatusResponseNew>() {
            @Override
            public void onResponse(Call<StatusResponseNew> call, Response<StatusResponseNew> response) {
                if (response.body() != null && response.code() == 200){
                    StatusResponseNew statusResponseNew = response.body();
                    if (statusResponseNew.getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(MelaUpdateActivity.this, "Mela Updated", Toast.LENGTH_SHORT).show();
                       loadMelaUpdate();
                    }else {
                        Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponseNew> call, Throwable t) {
                Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();
    }

   

    private void deleteMela(String mela_id) {

        pb.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatusResponseNew> call = apiInterface.getMelaDelete(mela_id);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<StatusResponseNew>() {
            @Override
            public void onResponse(Call<StatusResponseNew> call, Response<StatusResponseNew> response) {
                pb.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200){

                    StatusResponseNew statusResponseNew = response.body();

                    if (statusResponseNew.getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(MelaUpdateActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        loadMelaUpdate();
                    }else {
                        Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StatusResponseNew> call, Throwable t) {
                Toast.makeText(MelaUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadMelaUpdate();
    }


}