package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.FloorPlansResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MenuResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectDashBoardResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectImagesResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.VideosResponce;

import java.util.ArrayList;

import retrofit2.Call;

public class ProjectDetailsActivity extends AppCompatActivity implements OnClickListener {

    ArrayList<ProjectDashBoardResponce> projectDashBoardResponce = new ArrayList<>();
    ArrayList<MenuResponce> menu_responce = new ArrayList<>();
    ArrayList<ProjectImagesResponce> project_images = new ArrayList<>();
    ArrayList<FloorPlansResponce> floor_plans = new ArrayList<>();
    ArrayList<VideosResponce> project_videos = new ArrayList<>();
    ArrayList<ProjectDashBoardResponce> amenities_responce = new ArrayList<ProjectDashBoardResponce>();
    String menu_id;
    AppCompatImageView imageView, backBtn;
    AppCompatTextView priceTVID,
            flatDetailsTVID,
            ProjectTVID,
            byCompanyTVID,
            locationNameTVID,
            converted_areaTVID,
            locationTVID,
            socityTVID,
            configTVID,
            possessionTVID,
            why_chooseTVID,
            statusTVID2;

    AppCompatButton scheduleBtn;
    LinearLayout brochureTVID,layoutTVID,applicationTVID,mapLLID,floorTVID,videosTVID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_categories_list_item);
        Utilities.startAnimation(this);

        imageView = findViewById(R.id.dashImageID);
        brochureTVID = findViewById(R.id.brochureTVID);
        layoutTVID = findViewById(R.id.layoutTVID);
        applicationTVID =findViewById(R.id.applicationTVID);
        mapLLID = findViewById(R.id.mapLLID);
        floorTVID = findViewById(R.id.floorTVID);
        videosTVID = findViewById(R.id.videosTVID);
        backBtn = findViewById(R.id.backBtn);
        scheduleBtn = findViewById(R.id.scheduleBtn);
        priceTVID = findViewById(R.id.priceTVID);
        flatDetailsTVID = findViewById(R.id.flatDetailsTVID);
        ProjectTVID = findViewById(R.id.ProjectTVID);
        byCompanyTVID = findViewById(R.id.byCompanyTVID);
        locationNameTVID = findViewById(R.id.locationNameTVID);
        converted_areaTVID = findViewById(R.id.converted_areaTVID);
        locationTVID = findViewById(R.id.locationTVID);
        socityTVID = findViewById(R.id.socityTVID);
        configTVID = findViewById(R.id.configTVID);
        possessionTVID = findViewById(R.id.possessionTVID);
        statusTVID2 = findViewById(R.id.statusTVID2);
        why_chooseTVID = findViewById(R.id.why_chooseTVID);

        loadProjectData();
    }

    private void loadProjectData() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectDashBoardResponce>> call = apiInterface.projectDashboardApi(menu_id);
/*
        call.enqueue(new Callback<ArrayList<ProjectDashBoardResponce>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectDashBoardResponce>> call, Response<ArrayList<ProjectDashBoardResponce>> response) {

                if (response.body() != null && response.code() == 200){
                    projectDashBoardResponce = response.body();

                    for (int i = 0; i <projectDashBoardResponce.size(); i++){
                        menu_responce = projectDashBoardResponce.get(i).getMenu();
                        project_images = projectDashBoardResponce.get(i).getProject_images();
                        floor_plans = projectDashBoardResponce.get(i).getFloor_plans();
                        project_videos = projectDashBoardResponce.get(i).getProject_vedios();
                        amenities_responce = projectDashBoardResponce.get(i).getAmmenities();

                        for (int j = 0; j<menu_responce.size(); j++){
                            Picasso.get().load(menu_responce.get(j).getMenu_icon()).into(imageView);
                            priceTVID.setText(menu_responce.get(j).getNo_units());

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectDashBoardResponce>> call, Throwable t) {

            }
        });
*/
    }

    @Override
    public void onClick(View v) {

    }
}
