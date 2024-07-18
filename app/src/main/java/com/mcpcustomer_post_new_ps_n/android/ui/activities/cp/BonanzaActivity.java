package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
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
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.BonanzaAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BonanzaResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BonanzaActivity extends AppCompatActivity {



    TextView headerTittleTVID;
    RelativeLayout backRLID;

    BonanzaAdapter bonanzaAdapter;
    RecyclerView bonanzaRVID;

    ProgressBar pb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonanza);


        headerTittleTVID = findViewById(R.id.headerTittleTVID);
        bonanzaRVID = findViewById(R.id.bonanzaRVID);
        backRLID = findViewById(R.id.backRLID);
        pb = findViewById(R.id.pb);

        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(BonanzaActivity.this, "" + AppConstants.CP_VIEW, "YES");
        });

        headerTittleTVID.setText("Bonanza");

        if (Utilities.isNetworkAvailable(this)){
            loadBonanza();
        }else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadBonanza() {

        pb.setVisibility(View.VISIBLE);
        bonanzaRVID.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<BonanzaResponse>> call = apiInterface.showBonanza();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<BonanzaResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<BonanzaResponse>> call, Response<ArrayList<BonanzaResponse>> response) {

                pb.setVisibility(View.GONE);
                bonanzaRVID.setVisibility(View.VISIBLE);

                if (response.body() != null && response.code() == 200){

                    ArrayList<BonanzaResponse> bonanzaResponses = new ArrayList<>();

                    bonanzaResponses = response.body();

                    if (bonanzaResponses.size() > 0){

                        LinearLayoutManager layoutManager = new LinearLayoutManager(BonanzaActivity.this,RecyclerView.VERTICAL,false);
                        bonanzaRVID.setLayoutManager(layoutManager);

                        bonanzaAdapter = new BonanzaAdapter(bonanzaResponses,BonanzaActivity.this);
                        bonanzaRVID.setAdapter(bonanzaAdapter);

                    }

                    /*startTVID.setText(bonanzaResponses.get(0).getBonanza_start());
                    endTVID.setText(bonanzaResponses.get(0).getBonanza_end());
                    notesTVID.setText(bonanzaResponses.get(0).getNotes());
                    Picasso.get().load(bonanzaResponses.get(0).getPic()).into(bonanzaIVID);*/


                }else {
                    Toast.makeText(BonanzaActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BonanzaResponse>> call, Throwable t) {

                Toast.makeText(BonanzaActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}