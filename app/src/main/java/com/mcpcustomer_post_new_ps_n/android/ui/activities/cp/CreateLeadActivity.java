package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.LeadActivitiesAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AllStatusResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateLeadActivity extends AppCompatActivity {

    String userID, projectID;
    KProgressHUD kProgressHUD;
    LinearLayout mainLLID;
    private ArrayList<ProjectsResponse> projectsResponse = new ArrayList<>();
    LeadActivitiesAdapter leadActivitiesAdapter;

    Spinner requirementSPID;
    EditText nameETID, mobileETID, emailETID, notesETID;
    Button submitBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_create_lead);
        Utilities.startAnimation(this);

        userID = MySharedPreferences.getPreferences(this, "" + AppConstants.CP_USER_ID);
        RelativeLayout backRLID = findViewById(R.id.backRLID);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.finishAnimation(CreateLeadActivity.this);
                MySharedPreferences.setPreference(CreateLeadActivity.this, "" + AppConstants.CP_VIEW, "YES");
            }
        });
        MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "YES");
        TextView headerTittleTVID = findViewById(R.id.headerTittleTVID);
        headerTittleTVID.setText("Add Prospect");

        mainLLID = findViewById(R.id.mainLLID);
        requirementSPID = findViewById(R.id.requirementSPID);
        loadProjects();
        nameETID = findViewById(R.id.nameETID);
        mobileETID = findViewById(R.id.mobileETID);
        emailETID = findViewById(R.id.emailETID);
        notesETID = findViewById(R.id.notesETID);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.isNetworkAvailable(CreateLeadActivity.this)) {
                    validations();
                } else {
                    Utilities.customMessage(mainLLID, CreateLeadActivity.this, "No Internet Connection.....!");
                }
            }
        });

        requirementSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectID = projectsResponse.get(position).getProject_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void validations() {
        String name, mobile, email, notes;
        name = nameETID.getText().toString();
        mobile = mobileETID.getText().toString();
        email = emailETID.getText().toString();
        notes = notesETID.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Please enter lead name");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Please enter lead mobile number");
            return;
        }

        if (mobile.length() <= 9) {
            Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Please enter valid mobile number");
            return;
        }

        if (projectID.equals("1111")) {
            Utilities.customMessage(mainLLID, this, "Please Select Project");
            return;
        }


        createLead(name, mobile, email, notes, userID);
    }

    private void createLead(String name, String mobile, String email, String notes, String userID) {
        showProgress();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AllStatusResponse>> call = apiInterface.createLeadApi(name, mobile, email, notes, userID, projectID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<AllStatusResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AllStatusResponse>> call, Response<ArrayList<AllStatusResponse>> response) {
                dismissProgress();
                if (response.body() != null && response.code() == 200) {
                    ArrayList<AllStatusResponse> statusResponses = response.body();
                    if (statusResponses != null) {

                        for (int i = 0; i < statusResponses.size(); i++) {
                            if (statusResponses.get(i).getMsg() == 1) {
                                Utilities.customMessage(mainLLID, CreateLeadActivity.this, "" + statusResponses.get(i).getError_text());
                                Utilities.finishAnimation(CreateLeadActivity.this);
                            } else {
                                Utilities.customMessage(mainLLID, CreateLeadActivity.this, "" + statusResponses.get(i).getError_text());
                            }
                        }
                    }
                } else {
                    Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Error at server");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllStatusResponse>> call, Throwable t) {
                dismissProgress();
                Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Error : " + t.getMessage());
            }
        });
    }

    private void loadProjects() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectsResponse>> call = apiInterface.projectsApi();
        projectsResponse.add(new ProjectsResponse("1111", "Select Project"));
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ProjectsResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectsResponse>> call, Response<ArrayList<ProjectsResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    projectsResponse = response.body();

                    leadActivitiesAdapter = new LeadActivitiesAdapter(CreateLeadActivity.this, R.layout.cp_custom_spinner_view, projectsResponse);
                    requirementSPID.setAdapter(leadActivitiesAdapter);

                } else {
                    Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Projects not loading");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectsResponse>> call, Throwable t) {
                Utilities.customMessage(mainLLID, CreateLeadActivity.this, "Projects not loading");
            }
        });
    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading")
                .setDetailsLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public void dismissProgress() {
        kProgressHUD.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

}
