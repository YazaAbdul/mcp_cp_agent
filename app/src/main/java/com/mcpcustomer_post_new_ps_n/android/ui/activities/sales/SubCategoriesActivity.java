package com.mcpcustomer_post_new_ps_n.android.ui.activities.sales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.mcpcustomer_post_new_ps_n.android.FloorPlansActivityRcv;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.VideosRecyclerView;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.AppSingleton;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.PaginationScrollListener;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.PhotosTabLayout;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.AmenitiesAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectDetailsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectMenuAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AmenitiesResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardList;
import com.mcpcustomer_post_new_ps_n.android.ui.models.FloorPlansResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MenuResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.NumberResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectDashBoardResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectImagesResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectMenuResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ShareResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.VideosResponce;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SubCategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    String strtext;
    ArrayList<DashboardList> dashboardLists = new ArrayList<>();
    ListView dasListview;
    ProjectDetailsAdapter projectDetailsAdapter;
    TextView coming;
    ProgressBar progressBar;
    String imagelink, tittle, descrption, content_type, video, thumbnail;
    Button attendBtn, referBtn;
    String user_id, fullname, email, mobile, tittleStr, menu_id;
    RelativeLayout search, back;
    TextView tittleID;
    ImageView homeCallIMV;
    RecyclerView amenitiesRVID, menuProjectsRVID;

    private int mYear, mMonth, mHour, mMinute, mDay,finalHour;
    private String mDate;
    private Calendar mCalendar;
    private boolean isOkayClicked = false;

    ArrayList<ProjectMenuResponce> projectMenuList = new ArrayList<>();
    ProjectMenuAdapter projectMenuAdapter;

    AmenitiesAdapter amenitiesAdapter;
    private static final int REQUEST_CALL = 1;
    public static String userIdStr, locationStr;
    TextView locationID;

    public static String brochureID, layoutID, applicationID;
    LinearLayout mainLLID,subMenuLLID;


    ArrayList<ProjectDashBoardResponce> projectDashBoardResponce = new ArrayList<>();
    ArrayList<MenuResponce> menu_responce = new ArrayList<>();
    ArrayList<ProjectImagesResponce> project_images = new ArrayList<>();
    ArrayList<FloorPlansResponce> floor_plans = new ArrayList<>();
    ArrayList<VideosResponce> project_videos = new ArrayList<>();
    ArrayList<AmenitiesResponce> amenities_responce = new ArrayList<>();
    AppCompatImageView imageView,  share;
    AppCompatTextView priceTVID, priceTVID2, flatDetailsTVID, ProjectTVID, byCompanyTVID, locationNameTVID, converted_areaTVID,
            locationTVID, socityTVID, configTVID, possessionTVID, why_chooseTVID, companyTVID, imageCountTVID, statusTVID2;

    AppCompatButton scheduleBtn, offerTVID;

    LinearLayout brochureTVID, layoutTVID, applicationTVID, mapLLID, floorTVID, videosTVID,availableLLID;
    private Calendar calendar = Calendar.getInstance();

    GridLayoutManager linearLayoutManager;
    int start = 0;
    boolean isLoading;
    boolean isLastPage;
    public static boolean isClickable = true;
    boolean isCallClick=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        Utilities.startAnimation(this);
        //Utilities.startAnimationTop(this);

        overridePendingTransition(R.anim.act_pull_in_right, R.anim.act_push_out_left);

        imageView = findViewById(R.id.dashImageID);
        brochureTVID = findViewById(R.id.brochureTVID);
        layoutTVID = findViewById(R.id.layoutTVID);
        applicationTVID = findViewById(R.id.applicationTVID);
        mapLLID = findViewById(R.id.mapLLID);
        floorTVID = findViewById(R.id.floorTVID);
        availableLLID = findViewById(R.id.availableLLID);
        videosTVID = findViewById(R.id.videosTVID);
        //backBtn = findViewById(R.id.backBtn);
        scheduleBtn = findViewById(R.id.scheduleBtn);
        offerTVID = findViewById(R.id.offerTVID);
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
        priceTVID2 = findViewById(R.id.priceTVID2);
        why_chooseTVID = findViewById(R.id.why_chooseTVID);
        companyTVID = findViewById(R.id.companyTVID);
        amenitiesRVID = findViewById(R.id.amenitiesRVID);
        menuProjectsRVID = findViewById(R.id.menuProjectsRVID);
        imageCountTVID = findViewById(R.id.imageCountTVID);
        subMenuLLID = findViewById(R.id.subMenuLLID);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                strtext = bundle.getString("menu_id");
                tittleStr = bundle.getString("menu_name");
                locationStr = bundle.getString("location_name");

                brochureID = bundle.getString("brochure");
                layoutID = bundle.getString("layout");
                applicationID = bundle.getString("application");
            }
        }

        loadProjectData();
        loadMenuProjectsData();

        mainLLID = findViewById(R.id.mainLLID);

        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
        coming = findViewById(R.id.comingTVID);
        progressBar = findViewById(R.id.dashProgress);
        coming.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        dasListview = findViewById(R.id.dashListview);
        if (Utilities.isNetworkAvailable(this)) {
            ProjectDetails(AppConstants.BASE_URL + "/customerapp/" + "menucontentnew?" + "&menu_id=" + strtext);
        } else {
            coming.setVisibility(View.VISIBLE);
            coming.setText(R.string.no_internet);
        }
        homeCallIMV = findViewById(R.id.homeCallIMV);
        homeCallIMV.setOnClickListener(this);
        attendBtn = findViewById(R.id.attendBtn);
        referBtn = findViewById(R.id.referBtn);
        attendBtn.setOnClickListener(this);
        referBtn.setOnClickListener(this);

        user_id = MySharedPreferences.getPreferences(this, "" + AppConstants.USER_ID);
        menu_id = MySharedPreferences.getPreferences(this, "" + AppConstants.MENU_ID);
        String customerTypeStr = MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_TYPE);

        fullname = MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_NAME);
        email = MySharedPreferences.getPreferences(this, "" + AppConstants.EMAIL_ID);
        mobile = MySharedPreferences.getPreferences(this, "" + AppConstants.CONTACT_NUMBER);


        back = findViewById(R.id.backBtn);
        // tittleID = findViewById(R.id.ProjectTVID);
        search = findViewById(R.id.searchIMV);

        //tittleID.setText(tittleStr);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);


        floorTVID.setOnClickListener(v -> {
            Intent intent = new Intent(SubCategoriesActivity.this, FloorPlansActivityRcv.class);
            intent.putExtra("data",floor_plans);
            startActivity(intent);
        });


        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(SubCategoriesActivity.this, PhotosTabLayout.class);
            intent.putExtra("data",project_images);
            intent.putExtra("header_data",menu_responce);
            startActivity(intent);
            Utilities.startAnimationTop(this);
        });

        videosTVID.setOnClickListener(v -> {
            Intent intent = new Intent(SubCategoriesActivity.this, VideosRecyclerView.class);
            intent.putExtra("data",project_videos);
            Log.e("api==>", "video data"+project_videos);
            startActivity(intent);
        });

        mapLLID.setOnClickListener(view1 -> {
            if (Utilities.isNetworkAvailable(this)) {

                if(menu_responce.get(0).getLat().equals("") && menu_responce.get(0).getLat().isEmpty()){

                    Toast.makeText(this,"Location not found",Toast.LENGTH_SHORT).show();
                }else{
                    openMap(Float.valueOf(menu_responce.get(0).getLat()),Float.valueOf(menu_responce.get(0).getLan()));

                }

            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        });

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);
        finalHour=mHour+1;
        mDate = mDay + "/" + mMonth + "/" + mYear;


        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.e("api==>",mDate);
                attendClasses(AppConstants.BASE_URL + "/customerapp/" + "attendtrainingdate?" + "user_id=" + user_id + "menu_id" + menu_id + "&fullname=" + fullname + "&email=" + email + "&mobile=" + mobile + "&enq_type=" + strtext + "&date=" + mDate );

                monthOfYear++;
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;
                mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
                Log.e("date",mDate);

            }

        };


        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                new DatePickerDialog(SubCategoriesActivity.this,datePickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }});

        brochureTVID.setOnClickListener(v -> showAlert("Brochure"));

        layoutTVID.setOnClickListener(view13 -> showAlert("Layout"));

        applicationTVID.setOnClickListener(view14 -> showAlert("Application"));


    }

    private void openMap(Float lat,Float lan) {

        try {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lan);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            this.startActivity(mapIntent);
        } catch (ActivityNotFoundException innerEx) {
            Toast.makeText(this, "Install Google Maps application", Toast.LENGTH_LONG).show();
        }
    }


    private void loadMenuProjectsData() {

        menuProjectsRVID = findViewById(R.id.menuProjectsRVID);
        linearLayoutManager = new GridLayoutManager(this,LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSpanCount(2);
        projectMenuAdapter = new ProjectMenuAdapter(projectMenuList,SubCategoriesActivity.this);
        menuProjectsRVID.setAdapter(projectMenuAdapter);
        menuProjectsRVID.setLayoutManager(linearLayoutManager);

        menuProjectsRVID.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.e("pagination==>","api called");
                isLastPage =false;
                isLoading = true;
                loadNextMenuProjectsPage();
            }

            @Override
            public boolean isLastPage() {
                Log.e("pagination==>","is last page");
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                Log.e("pagination==>","is loading");
                return isLoading;
            }
        });

        loadFirstMenuProjectsPage();

    }

    private void loadFirstMenuProjectsPage() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectMenuResponce>> call = apiInterface.projectMenuApi(strtext,start);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ProjectMenuResponce>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectMenuResponce>> call, retrofit2.Response<ArrayList<ProjectMenuResponce>> response) {
                if (response.body() != null && response.code() == 200){

                    projectMenuList = response.body();

                    if (projectMenuList.size()>0){
                        isClickable = true;
                        startedValues();
                        projectMenuAdapter.addAll(projectMenuList);

                        if (projectMenuList.size() == 10) {
                            projectMenuAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }

                    }else{noDataViews();}

                }else {noDataViews();}
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectMenuResponce>> call, Throwable t) {
                noDataViews();
            }
        });
    }

    private void startedValues() {
        start = start +10;
    }

    private void loadNextMenuProjectsPage() {
        Log.e("scroll==>","2"+" , "+strtext);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectMenuResponce>> call = apiInterface.projectMenuApi(strtext,start);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ProjectMenuResponce>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectMenuResponce>> call, retrofit2.Response<ArrayList<ProjectMenuResponce>> response) {
                isCallClick =true;
                if (response.body() !=null && response.code() == 200){
                    projectMenuList = response.body();
                    projectMenuAdapter.removeLoadingFooter();
                    isLoading = false;

                    if (projectMenuList.size() > 0) {
                        projectMenuAdapter.addAll(projectMenuList);

                        if (projectMenuList.size() == 10) {
                            startedValues();
                            projectMenuAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                            projectMenuAdapter.removeLoadingFooter();
                        }
                    } else {
                        projectMenuAdapter.removeLoadingFooter();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectMenuResponce>> call, Throwable t) {
                projectMenuAdapter.removeLoadingFooter();

            }
        });

    }

    private void showAlert(final String type) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_option);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tittle = dialog.findViewById(R.id.tittle);
        tittle.setText(type);

        LinearLayout shareLLID = dialog.findViewById(R.id.shareLLID);
        LinearLayout downloadLLID = dialog.findViewById(R.id.downloadLLID);

        shareLLID.setOnClickListener(view -> {
            String shareBody = "";

            if (type.equalsIgnoreCase("Brochure")) {

                if (!brochureID.equals("")) {
                    shareBody = SubCategoriesActivity.brochureID;
                } else {
                    Toast.makeText(this, "Brochure not available", Toast.LENGTH_SHORT).show();
                }

            } else if (type.equalsIgnoreCase("Layout")) {
                if (!layoutID.equals("")) {
                    shareBody = layoutID;
                } else {
                    Toast.makeText(this, "Layout not available", Toast.LENGTH_SHORT).show();
                }
            } else if (type.equalsIgnoreCase("Application")) {
                if (!applicationID.equals("")) {
                    shareBody = applicationID;
                } else {
                    Toast.makeText(this, "Application not available", Toast.LENGTH_SHORT).show();
                }
            }

            if (shareBody.equalsIgnoreCase("")) {
                Toast.makeText(this, "File not available", Toast.LENGTH_SHORT).show();
            } else {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                this.startActivity(Intent.createChooser(sharingIntent, this.getResources().getString(R.string.share_using)));
            }
            dialog.dismiss();
        });

        downloadLLID.setOnClickListener(view -> {
            if (type.equalsIgnoreCase("Brochure")) {


                if (Utilities.isNetworkAvailable(this)) {
                    openPdf(SubCategoriesActivity.brochureID);
                } else {
                    Toast.makeText(this, "Please check your internet connection..!", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            } else if (type.equalsIgnoreCase("Layout")) {


                if (Utilities.isNetworkAvailable(this)) {
                    openPdf(SubCategoriesActivity.layoutID);
                } else {
                    Toast.makeText(this, "Please check your internet connection..!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            } else if (type.equalsIgnoreCase("Application")) {

                if (Utilities.isNetworkAvailable(this)) {
                    openPdf(SubCategoriesActivity.applicationID);
                } else {
                    Toast.makeText(this, "Please check your internet connection..!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    private void openPdf(String url) {

        if (url.equalsIgnoreCase("")) {
            Toast.makeText(this, "File not available", Toast.LENGTH_SHORT).show();
        } else {
            pdf(url);
        }
    }

    private void pdf(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        this.startActivity(browserIntent);
    }


    private void loadProjectData() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectDashBoardResponce>> call = apiInterface.projectDashboardApi(strtext);
        Log.e("api==>", call.request().toString());
        call.enqueue(new Callback<ArrayList<ProjectDashBoardResponce>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectDashBoardResponce>> call, retrofit2.Response<ArrayList<ProjectDashBoardResponce>> response) {
                Log.e("response==>", new Gson().toJson(response.body()));
                if (response.body() != null && response.code() == 200) {
                    projectDashBoardResponce = response.body();

                    menu_responce = projectDashBoardResponce.get(0).getMenu();
                    project_images = projectDashBoardResponce.get(0).getProject_images();
                    floor_plans = projectDashBoardResponce.get(0).getFloor_plans();
                    project_videos = projectDashBoardResponce.get(0).getProject_vedios();
                    amenities_responce = projectDashBoardResponce.get(0).getAmmenities();

                    Log.e("project_data==>", new Gson().toJson(menu_responce));
                    Picasso.get().load(menu_responce.get(0).getMenu_icon()).into(imageView);
                    priceTVID.setText("₹ " +menu_responce.get(0).getNo_units());
                    ProjectTVID.setText(menu_responce.get(0).getProject_name());
                    content_type = menu_responce.get(0).getContent_type();
                    //byCompanyTVID.setText(menu_responce.get(0).getBuilder_name());
                    converted_areaTVID.setText(menu_responce.get(0).getCovered_area());
                    locationTVID.setText(menu_responce.get(0).getAddress());
                    priceTVID2.setText("₹ " +menu_responce.get(0).getNo_units());
                    possessionTVID.setText(menu_responce.get(0).getPosession());
                    statusTVID2.setText(menu_responce.get(0).getStatus());
                    why_chooseTVID.setText(menu_responce.get(0).getWhy_choose());
                    companyTVID.setText(menu_responce.get(0).getProject_name());
                    flatDetailsTVID.setText(menu_responce.get(0).getBhk());
                    //locationNameTVID.setText(menu_responce.get(0).getAddress());
                    offerTVID.setText(menu_responce.get(0).getOffer());
                    socityTVID.setText(menu_responce.get(0).getSociety());
                    imageCountTVID.setText("+"+menu_responce.get(0).getImage_count());

                    if (menu_responce.get(0).getOffer().equals("") && menu_responce.get(0).getOffer().isEmpty()){
                        offerTVID.setVisibility(View.GONE);
                    }

                    if (menu_responce.get(0).getOffer().equals(0)){
                        offerTVID.setVisibility(View.VISIBLE);
                    }

                    availableLLID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(menu_responce.get(0).getMap_url() != null && !(menu_responce.get(0).getMap_url().isEmpty())) {

                                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse(menu_responce.get(0).getMap_url()));
                                startActivity(Getintent);

                            }else {

                                Toast.makeText(SubCategoriesActivity.this, "Availability map not avaliable", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    
                    if (amenities_responce.size()>0){
                        amenitiesAdapter = new AmenitiesAdapter(amenities_responce, SubCategoriesActivity.this);
                        amenitiesRVID.setAdapter(amenitiesAdapter);
                    }else{

                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectDashBoardResponce>> call, Throwable t) {

            }
        });
    }

    private void noDataViews() {
        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
    }


    private void callShare() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ShareResponse> call = apiInterface.getCustomerappshare();
        call.enqueue(new Callback<ShareResponse>() {
            @Override
            public void onResponse(Call<ShareResponse> call, retrofit2.Response<ShareResponse> response) {

                if (response.body() != null && response.code() == 200) {
                    ShareResponse shareResponse = response.body();
                    if (shareResponse != null) {

                        String share = shareResponse.getShare();

                        String shareBody = "Download: " + share;
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "(Open it in Google Play Store to Download the Application)");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));

                    } else {

                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<ShareResponse> call, Throwable t) {

            }
        });
    }

    private void ProjectDetails(String url) {
        progressBar.setVisibility(View.VISIBLE);
        subMenuLLID.setVisibility(View.GONE);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                subMenuLLID.setVisibility(View.VISIBLE);
                if (response == null) {
                    coming.setVisibility(View.VISIBLE);
                    subMenuLLID.setVisibility(View.GONE);
                    coming.setText(R.string.nodata);
                } else {
                    try {
                        int count = 0;
                        while (count < response.length()) {

                            JSONObject jo = response.getJSONObject(count);
                            tittle = jo.optString("project_name");
                            descrption = jo.optString("description");
                            imagelink = jo.optString("banner_pic");
                            content_type = jo.optString("content_type");
                            video = jo.optString("video");
                            thumbnail = jo.optString("thumbnail");
                          String  strtext = jo.optString("menu_id");


                            String lat = jo.optString("lat");
                            String lan = jo.optString("lan");

                            dashboardLists.add(new DashboardList(strtext, tittle, descrption, imagelink, video, thumbnail, lat, lan));
                            projectDetailsAdapter = new ProjectDetailsAdapter(SubCategoriesActivity.this, dashboardLists, strtext);
                            dasListview.setAdapter(projectDetailsAdapter);
                            count++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                coming.setVisibility(View.VISIBLE);
                coming.setText(R.string.nodata);

            }
        });

        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppSingleton.getInstance(SubCategoriesActivity.this).addToRequestQueue(arrayRequest, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.referBtn:
                Intent intent = new Intent(SubCategoriesActivity.this, AvailabilityDetailsActivity.class);
                intent.putExtra("PROJECT_ID", "" + strtext);
                intent.putExtra("PROJECT_NAME", "" + tittleStr);
                startActivity(intent);
                //shareapp();
                break;
            case R.id.attendBtn:
                attendClasses(AppConstants.BASE_URL + "/customerapp/" + "attendtraining?" + "user_id=" + user_id + "menu_id" + menu_id + "&fullname=" + fullname + "&email=" + email + "&mobile=" + mobile + "&enq_type=" + strtext + "&date=" + mDate );
                break;

            case R.id.backBtn:
                onBackPressedAnimation();
                break;

            case R.id.searchIMV:
                startActivity(new Intent(SubCategoriesActivity.this, SearchActivity.class));
                break;

            case R.id.homeCallIMV:
                phoneCallRecordResponse();

                break;

            case R.id.share:
                callShare();
                break;

        }
    }

    private void call() {

    }

    private void phoneCallRecordResponse() {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(SubCategoriesActivity.this, null, "Getting call please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponse>> call = apiInterface.phoneCallRecord(fullname, email, mobile, user_id);
        call.enqueue(new Callback<ArrayList<StatusResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponse>> call, retrofit2.Response<ArrayList<StatusResponse>> response) {
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    ArrayList<StatusResponse> status = response.body();
                    if (status != null && status.size() > 0) {
                        for (int i = 0; i < status.size(); i++) {
                            if (status.get(i).getStatus() == 1) {
                                Log.d("Debug-->", "Successfully Submitted");
                                phoneCall();
                            } else {
                                Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Something went wrong at server side");

                            }
                        }
                    } else {
                        Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Something went wrong at server side");
                    }
                } else {
                    Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Something went wrong at server side");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Something went wrong at server side");
            }
        });
    }

    private void phoneCall() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NumberResponse> call = apiInterface.getCustomerappcall();
        call.enqueue(new Callback<NumberResponse>() {
            @Override
            public void onResponse(Call<NumberResponse> call, retrofit2.Response<NumberResponse> response) {

                if (response.body() != null && response.code() == 200) {
                    NumberResponse numberResponse = response.body();
                    if (numberResponse != null) {

                        String customer_number = numberResponse.getCustomer_number();
                        String number = "+91" + customer_number;

                        if (ActivityCompat.checkSelfPermission(SubCategoriesActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(SubCategoriesActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(SubCategoriesActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        } else {
                            // String prodcatnumber = "+91 8143300555";
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + number));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        }

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<NumberResponse> call, Throwable t) {
                // Toast.makeText(getApplicationContext(),"Error : "+t.getMessage());
            }
        });
    }

    public void shareapp() {

    }

    private void attendClasses(String url) {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(SubCategoriesActivity.this, null, "Submitting your request please wait....");
        progressDialog.show();
        Log.e("api==>",url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                if (response == null) {
                    Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Something went wrong at server side");
                } else {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jo = response.getJSONObject(i);
                            String status = jo.optString("status");
                            if (status.equals("1")) {
                                Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Successfully submitted");
                            } else {
                                Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Failed");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Utilities.customMessage(mainLLID, SubCategoriesActivity.this, "Something went wrong at server side");
            }
        });
        AppSingleton.getInstance(SubCategoriesActivity.this).addToRequestQueue(arrayRequest, "");
    }


    @Override
    public void onBackPressed() {
        Utilities.finishAnimation(this);

    }

    private void onBackPressedAnimation() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);
        MySharedPreferences.setPreference(SubCategoriesActivity.this, "" + AppConstants.CP_VIEW, "NO");
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SubCategoriesActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(SubCategoriesActivity.this, permission)) {
                ActivityCompat.requestPermissions(SubCategoriesActivity.this, new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(SubCategoriesActivity.this, new String[]{permission}, requestCode);
            }
        } else if (ContextCompat.checkSelfPermission(SubCategoriesActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getApplicationContext(), "Permission was denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 101)
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
