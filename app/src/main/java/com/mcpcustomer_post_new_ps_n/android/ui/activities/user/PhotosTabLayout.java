package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.PhotosFragmentAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectDetailsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectPhotosAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MenuResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectImagesResponce;

import java.util.ArrayList;

public class PhotosTabLayout extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PhotosFragmentAdapter adapter;
    RelativeLayout imageView;
    RecyclerView projectPhotosRVID;
    AppCompatTextView amountTV,bhkTVID,locationTVID,photosCountTV;
    ProjectPhotosAdapter projectPhotosAdapter;
    ArrayList<ProjectImagesResponce> project_images = new ArrayList<>();
    ArrayList<MenuResponce> menu_responce = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_tab_layout);
        Utilities.startAnimationTop(this);
        tabLayout = findViewById(R.id.photosTL);
        viewPager2 = findViewById(R.id.photosViewPager2);
         imageView = findViewById(R.id.backBtn);
        amountTV = findViewById(R.id.amountTV);
        bhkTVID = findViewById(R.id.bhkTVID);
        locationTVID = findViewById(R.id.locationTVID);
        photosCountTV = findViewById(R.id.photosCountTV);

        if (getIntent()!=null){
            Bundle bundle=getIntent().getExtras();
            if (bundle!=null){
                project_images= (ArrayList<ProjectImagesResponce>) bundle.getSerializable("data");
                menu_responce = (ArrayList<MenuResponce>) bundle.getSerializable("header_data");

            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotosTabLayout.this, ProjectDetailsAdapter.class);
                onBackPressed();
            }
        });

        loadPhotos();
    }

    private void loadPhotos() {
        projectPhotosRVID=findViewById(R.id.projectPhotosRVID);
        projectPhotosRVID.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        projectPhotosAdapter=new ProjectPhotosAdapter(project_images);
        projectPhotosRVID.setAdapter(projectPhotosAdapter);
        amountTV.setText("₹" + menu_responce.get(0).getNo_units());
        bhkTVID.setText(menu_responce.get(0).getBhk());
        locationTVID.setText(menu_responce.get(0).getAddress());
        photosCountTV.setText(menu_responce.get(0).getImage_count() + " photos");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimationTop(this);
    }
}