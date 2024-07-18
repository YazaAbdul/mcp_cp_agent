package com.mcpcustomer_post_new_ps_n.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.SubCategoriesActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.FloorAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.FloorPlansResponce;

import java.util.ArrayList;

public class FloorPlansActivityRcv extends AppCompatActivity {

    RecyclerView recyclerView;
    //LinearLayoutManager layoutManager;
    //GridLayoutManager gridLayoutManager;
   // ArrayList<FloorPlansPOJO> data = new ArrayList<>();
    FloorAdapter adapter;
    ProgressBar progressBar;
    ArrayList<FloorPlansResponce> floor_plans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plans_rcv);
        recyclerView = findViewById(R.id.floorPlansRCV);
        progressBar = findViewById(R.id.pb);



        AppCompatImageView imageView = findViewById(R.id.backBtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloorPlansActivityRcv.this, SubCategoriesActivity.class);
                finish();
            }
        });

        if (getIntent()!= null){
            Bundle bundle = getIntent().getExtras();
            if (bundle!=null){
                floor_plans = (ArrayList<FloorPlansResponce>) bundle.getSerializable("data");
            }
        }

        loadFiles();
    }

    private void loadFiles() {
        /*progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new FloorAdapter(floor_plans);
        recyclerView.setAdapter(adapter);


    }

    /*private void noDataViews() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onBackPressed() {
        Utilities.finishAnimation(this);

    }
}