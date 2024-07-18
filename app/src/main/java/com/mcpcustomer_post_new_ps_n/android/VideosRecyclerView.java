package com.mcpcustomer_post_new_ps_n.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectDetailsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.VideosAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.VideosResponce;

import java.util.ArrayList;

public class VideosRecyclerView extends AppCompatActivity {

    RecyclerView videosRCID;
    //ArrayList<FloorPlansPOJO> data = new ArrayList<>();
    VideosAdapter adapter;
    ProgressBar progressBar;
    ArrayList<VideosResponce> project_videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_recycler_view);

        videosRCID = findViewById(R.id.videosRCVID);
        progressBar = findViewById(R.id.pb);

        AppCompatImageView imageView = findViewById(R.id.backBtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosRecyclerView.this, ProjectDetailsAdapter.class);
                finish();
            }
        });

        if (getIntent()!=null){
            Bundle bundle = getIntent().getExtras();
            if (bundle!=null){
                project_videos = (ArrayList<VideosResponce>)bundle.getSerializable("data");
            }
        }

        loadFiles();
    }

    private void loadFiles() {
        /*progressBar.setVisibility(View.VISIBLE);
        videosRCID.setVisibility(View.GONE);*/

        videosRCID.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new VideosAdapter(project_videos);
        videosRCID.setAdapter(adapter);

        /*ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<FloorPlansPOJO>> call = apiInterface.getFloorAPI("23","125","2");

        call.enqueue(new Callback<ArrayList<FloorPlansPOJO>>() {
            @Override
            public void onResponse(Call<ArrayList<FloorPlansPOJO>> call, Response<ArrayList<FloorPlansPOJO>> response) {
                if (response.body() != null && response.code() == 200){
                    data = response.body();

                    progressBar.setVisibility(View.GONE);
                    videosRCID.setVisibility(View.VISIBLE);

                    if (data.size()>0){
                        adapter = new VideosAdapter(data,VideosRecyclerView.this);
                        videosRCID.setAdapter(adapter);

                    }else{noDataViews();}

                }else {noDataViews();}
            }

            @Override
            public void onFailure(Call<ArrayList<FloorPlansPOJO>> call, Throwable t) {
                noDataViews();

            }
        });*/
    }


}