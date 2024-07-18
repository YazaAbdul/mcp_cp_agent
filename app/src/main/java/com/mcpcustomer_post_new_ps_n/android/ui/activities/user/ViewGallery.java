package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ImagesAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.VideoAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ImageResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.YoutubeVideoResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewGallery extends AppCompatActivity {
    TextView headerTittleTVID,videosTV,imagesTV;
    String status="";
    RelativeLayout backRLID;
    RecyclerView imagesRCID,videosRCID;
    ProgressBar pb;
    ArrayList<ImageResponse> imageResponselist=new ArrayList<>();
    ArrayList<YoutubeVideoResponse> youtubeVideos = new ArrayList<>();
    String customerId,paymentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gallery);

        videosTV=findViewById(R.id.videosTV);
        imagesTV=findViewById(R.id.imagesTV);

        pb=findViewById(R.id.pb);
        imagesRCID=findViewById(R.id.imagesRCID);
        videosRCID=findViewById(R.id.videosRCID);

        headerTittleTVID=findViewById(R.id.headerTittleTVID);
        headerTittleTVID.setText(getIntent().getStringExtra("Title"));

        backRLID=findViewById(R.id.backRLID);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        customerId= MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_ID);;
        paymentId=getIntent().getStringExtra("Payment_ID");

        LinearLayoutManager pa = new GridLayoutManager(this,2);
        imagesRCID.setLayoutManager(pa);
        callImagesApi(customerId,paymentId,"1");

        LinearLayoutManager pa1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        videosRCID.setLayoutManager(pa1);
        callVideosApi(customerId,paymentId,"0");



    }

    private void callVideosApi(String customerId,String paymentId,String type) {
        pb.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<YoutubeVideoResponse>> call = apiInterface.videosApi(customerId,paymentId,type);
        call.enqueue(new Callback<ArrayList<YoutubeVideoResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<YoutubeVideoResponse>> call, Response<ArrayList<YoutubeVideoResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    pb.setVisibility(View.GONE);
                    youtubeVideos.addAll(response.body());
                    if (youtubeVideos.size() > 0) {
                        if(response.body().get(0).getStatus().equalsIgnoreCase("1")) {
                            VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos, ViewGallery.this);
                            videosRCID.setAdapter(videoAdapter);
                            videosRCID.setVisibility(View.VISIBLE);
                            videosTV.setVisibility(View.VISIBLE);
                        }else{
                            videosRCID.setVisibility(View.GONE);
                            videosTV.setVisibility(View.GONE);
                            Toast.makeText(ViewGallery.this, "No Videos Available", Toast.LENGTH_SHORT).show();
                        }

                    } else {
//                        Toast.makeText(ViewGallery.this, "No Images", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(ViewGallery.this, "No Images", Toast.LENGTH_SHORT).show();
                }
//                status=response.body().get(0).getStatus();
//                if(status.equalsIgnoreCase("0")){
//                    videosRCID.setVisibility(View.GONE);
//                    videosTV.setVisibility(View.GONE);
//                    Toast.makeText(ViewGallery.this, "No Videos Availiable", Toast.LENGTH_SHORT).show();
//                }
//

            }

            @Override
            public void onFailure(Call<ArrayList<YoutubeVideoResponse>> call, Throwable t) {

                pb.setVisibility(View.GONE);
//                paidRCID.setVisibility(View.GONE);
                Toast.makeText(ViewGallery.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void callImagesApi(String customerId,String paymentId,String type) {
        pb.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ImageResponse>> call = apiInterface.imagesApi(customerId,paymentId,type);
        call.enqueue(new Callback<ArrayList<ImageResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ImageResponse>> call, Response<ArrayList<ImageResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    pb.setVisibility(View.GONE);
                    imageResponselist.addAll(response.body());
                    if (imageResponselist.size() > 0) {
                        if(response.body().get(0).getStatus().equalsIgnoreCase("1")) {
                            ImagesAdapter imagesAdapter = new ImagesAdapter(ViewGallery.this, imageResponselist);
                            imagesRCID.setAdapter(imagesAdapter);
                            imagesRCID.setVisibility(View.VISIBLE);
                            imagesTV.setVisibility(View.VISIBLE);
                        }else {
                            imagesRCID.setVisibility(View.GONE);
                            imagesTV.setVisibility(View.GONE);
                            Toast.makeText(ViewGallery.this,"No Images Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                        Toast.makeText(ViewGallery.this, "No Images", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(ViewGallery.this, "No Images", Toast.LENGTH_SHORT).show();
                }
                if(response.toString().equalsIgnoreCase("0")){
                    Toast.makeText(ViewGallery.this, "No Images found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ImageResponse>> call, Throwable t) {
//                paidRCID.setVisibility(View.GONE);
                pb.setVisibility(View.GONE);
                Toast.makeText(ViewGallery.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}