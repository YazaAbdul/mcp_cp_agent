package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codesgood.views.JustifiedTextView;
import com.mcpcustomer_post_new_ps_n.android.FloorPlansActivityRcv;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.VideosRecyclerView;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.MainActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.SubCategoriesActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.PhotosTabLayout;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AmenitiesResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardList;
import com.mcpcustomer_post_new_ps_n.android.ui.models.FloorPlansResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MenuResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectDashBoardResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectImagesResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.VideosResponce;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by venkei on 20-Jan-18.
 */

public class ProjectDetailsAdapter extends BaseAdapter{
    private final ArrayList<DashboardList> dashboardLists;
    private final Activity activity;
    private final LayoutInflater inflater;

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    ArrayList<ProjectDashBoardResponce> projectDashBoardResponce = new ArrayList<>();
    ArrayList<MenuResponce> menu_responce = new ArrayList<>();
    ArrayList<ProjectImagesResponce> project_images = new ArrayList<>();
    ArrayList<FloorPlansResponce> floor_plans = new ArrayList<>();
    ArrayList<VideosResponce> project_videos = new ArrayList<>();
    ArrayList<AmenitiesResponce> amenities_responce = new ArrayList<>();
    String menu_id,content_type;
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
            statusTVID2;

    AppCompatButton scheduleBtn, offerTVID;
    LinearLayout brochureTVID,layoutTVID,applicationTVID,mapLLID,floorTVID,videosTVID;

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public ProjectDetailsAdapter(Activity activity, ArrayList<DashboardList> dashboardLists, String menu_id) {

        this.activity = activity;
        this.dashboardLists = dashboardLists;
        this.menu_id = menu_id;
        inflater = LayoutInflater.from(activity);

        calendar=   Calendar.getInstance();
    }

    @Override
    public int getCount() {
        return dashboardLists.size();
    }

    @Override
    public Object getItem(int position) {
        return dashboardLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "CutPasteId"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.sub_categories_list_item, null);

        Utilities.startAnimationTop(this);
        imageView = view.findViewById(R.id.dashImageID);
        brochureTVID = view.findViewById(R.id.brochureTVID);
        layoutTVID = view.findViewById(R.id.layoutTVID);
        applicationTVID =view.findViewById(R.id.applicationTVID);
        mapLLID = view.findViewById(R.id.mapLLID);
        floorTVID = view.findViewById(R.id.floorTVID);
        videosTVID = view.findViewById(R.id.videosTVID);
        backBtn = view.findViewById(R.id.backBtn);
        scheduleBtn = view.findViewById(R.id.scheduleBtn);
        offerTVID = view.findViewById(R.id.offerTVID);
        priceTVID = view.findViewById(R.id.priceTVID);
        flatDetailsTVID = view.findViewById(R.id.flatDetailsTVID);
        ProjectTVID = view.findViewById(R.id.ProjectTVID);
        byCompanyTVID = view.findViewById(R.id.byCompanyTVID);
        locationNameTVID = view.findViewById(R.id.locationNameTVID);
        converted_areaTVID = view.findViewById(R.id.converted_areaTVID);
        locationTVID = view.findViewById(R.id.locationTVID);
        socityTVID = view.findViewById(R.id.socityTVID);
        configTVID = view.findViewById(R.id.configTVID);
        possessionTVID = view.findViewById(R.id.possessionTVID);
        statusTVID2 = view.findViewById(R.id.statusTVID2);

        /*loadProjectData();*/


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };

        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // TODO Auto-generated method stub
                new DatePickerDialog(activity, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }});

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, PhotosTabLayout.class);
            activity.startActivity(intent);
        });

        Utilities.startAnimationTop(this);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.finish();
        });

        floorTVID.setOnClickListener(v -> {
            Intent intent = new Intent(activity, FloorPlansActivityRcv.class);
            activity.startActivity(intent);
        });

        videosTVID.setOnClickListener(v -> {
            Intent intent = new Intent(activity, VideosRecyclerView.class);
            activity.startActivity(intent);
        });

        AppCompatImageView dashImageID=view.findViewById(R.id.dashImageID);
        //Picasso.with(activity).load(dashboardLists.get(position).getDasImage()).into(dashImageID);

        JustifiedTextView dashTittle=view.findViewById(R.id.dashTittle);
        dashTittle.setText(dashboardLists.get(position).getDasDesc());

        AppCompatTextView locationID=view.findViewById(R.id.locationID);
        locationID.setText(SubCategoriesActivity.locationStr);

        mapLLID.setOnClickListener(view1 -> {
            if (Utilities.isNetworkAvailable(activity)) {
               // openMap(Float.valueOf(dashboardLists.get(position).getLat()),Float.valueOf(dashboardLists.get(position).getLan()));
            } else {
               // Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        });

        brochureTVID.setOnClickListener(view12 -> showAlert("Brochure"));

        layoutTVID.setOnClickListener(view13 -> showAlert("Layout"));

        applicationTVID.setOnClickListener(view14 -> showAlert("Application"));

        return view;
    }

  /*  private void loadProjectData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectDashBoardResponce>> call = apiInterface.projectDashboardApi(menu_id);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ProjectDashBoardResponce>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectDashBoardResponce>> call, Response<ArrayList<ProjectDashBoardResponce>> response) {

                if (response.body() != null && response.code() == 200){
                    projectDashBoardResponce = response.body();

                    for (int i = 0; i <projectDashBoardResponce.size(); i++){
                        menu_responce = projectDashBoardResponce.get(i).getMenu_responce();
                        project_images = projectDashBoardResponce.get(i).getProject_images();
                        floor_plans = projectDashBoardResponce.get(i).getFloor_plans();
                        project_videos = projectDashBoardResponce.get(i).getProject_videos();
                        amenities_responce = projectDashBoardResponce.get(i).getAmenities_responce();

                        for (int j = 0; j<menu_responce.size(); j++){
                            Picasso.get().load(menu_responce.get(j).getMenu_icon()).into(imageView);
                            priceTVID.setText(menu_responce.get(j).getNo_units());
                            ProjectTVID.setText(menu_responce.get(j).getProject_name());
                            content_type = menu_responce.get(j).getContent_type();
                            byCompanyTVID.setText(menu_responce.get(j).getBuilder_name());
                            converted_areaTVID.setText(menu_responce.get(j).getCovered_area());
                            locationTVID.setText(menu_responce.get(j).getAddress());


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectDashBoardResponce>> call, Throwable t) {

            }
        });
    }
*/
    private void showAlert(final String type) {
        final Dialog dialog = new Dialog(activity);
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

                if (!SubCategoriesActivity.brochureID.equals("")) {
                    shareBody = SubCategoriesActivity.brochureID;
                } else {
                    Toast.makeText(activity, "Brochure not available", Toast.LENGTH_SHORT).show();
                }

            } else if (type.equalsIgnoreCase("Layout")) {
                if (!SubCategoriesActivity.layoutID.equals("")) {
                    shareBody = SubCategoriesActivity.layoutID;
                } else {
                    Toast.makeText(activity, "Layout not available", Toast.LENGTH_SHORT).show();
                }
            } else if (type.equalsIgnoreCase("Application")) {
                if (!SubCategoriesActivity.applicationID.equals("")) {
                    shareBody = SubCategoriesActivity.applicationID;
                } else {
                    Toast.makeText(activity, "Application not available", Toast.LENGTH_SHORT).show();
                }
            }

            if (shareBody.equalsIgnoreCase("")) {
                Toast.makeText(activity, "File not available", Toast.LENGTH_SHORT).show();
            } else {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.share_using)));
            }
            dialog.dismiss();
        });

        downloadLLID.setOnClickListener(view -> {
            if (type.equalsIgnoreCase("Brochure")) {


                if (Utilities.isNetworkAvailable(activity)) {
                    openPdf(SubCategoriesActivity.brochureID);
                } else {
                    Toast.makeText(activity, "Please check your internet connection..!", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            } else if (type.equalsIgnoreCase("Layout")) {


                if (Utilities.isNetworkAvailable(activity)) {
                    openPdf(SubCategoriesActivity.layoutID);
                } else {
                    Toast.makeText(activity, "Please check your internet connection..!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            } else if (type.equalsIgnoreCase("Application")) {

                if (Utilities.isNetworkAvailable(activity)) {
                    openPdf(SubCategoriesActivity.applicationID);
                } else {
                    Toast.makeText(activity, "Please check your internet connection..!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    private void openMap(Float lat,Float lan) {

        try {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lan);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            activity.startActivity(mapIntent);
        } catch (ActivityNotFoundException innerEx) {
            Toast.makeText(activity, "Install Google Maps application", Toast.LENGTH_LONG).show();
        }
    }

    private void openPdf(String url) {

        if (url.equalsIgnoreCase("")) {
            Toast.makeText(activity, "File not available", Toast.LENGTH_SHORT).show();
        } else {
            pdf(url);
        }
    }

    private void pdf(final String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static void startAnimationTop(Activity activity) {
        activity.overridePendingTransition(R.anim.act_pull_in_top, R.anim.act_push_out_bottom);
    }

    public void overridePendingTransition() {
        startAnimationTop(activity);
    }
}



