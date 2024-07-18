package com.mcpcustomer_post_new_ps_n.android.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.CustomExpandableListView;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.AgentUpdateActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.AvailabilityActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.BonanzaActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CPSaleHistory;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CPTravel_Request;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.ClubDetailsActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CreativesActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.DirectTeamActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.LeadsCompleteView;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.LeadsViewActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.MelaUpdateActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.SiteInChargesActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.SiteVisitActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.WebViewTes;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DashBoardHeaderAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DesignationsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.MaterialsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashBordPerformanceResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardChildResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardHeaderResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DesignationsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.EligibleCommissionResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MaterialResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.NewDashboardResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ScrollerResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCP extends Fragment {

    private  CustomExpandableListView leadEXLVID;
    private   DashBoardHeaderAdapter adapter;
    private List<DashboardHeaderResponse> headerResponses = new ArrayList<>();
    private ArrayList<DashboardChildResponse> overall = new ArrayList<>();
    private ArrayList<DashboardChildResponse> today = new ArrayList<>();

    private String userID,userPicStr,travelEligibility;
    private ProgressBar prePB, perPB;
    private ViewPager viewPager;
    private PerformanceAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private ArrayList<PerformanceList> lists = new ArrayList<>();
    private RelativeLayout newCreateID,shareRLID;
    private TextView preNoDataPB;
    RecyclerView activitiesRCVID,preActivitiesRCVID;
    TextView month_customer,month_meetings,month_sale,overall_customer,overall_meetings,overall_sale;
    CardView travelRequestCVID,travelApprovalCVID,withdrawRequestCVID,bonanzaCVID,RequestMaterialCVID,directReportingCVID,directReportingCVID2,melaUpdateCVID,totalTeamCVID,availabilityCVID,saleHistoryCVID,addAgentCVID,clubDetailsCVID,creativesCVID,siteInChargeCVID;

    //List<MaterialResponse> materialResponses = new ArrayList<>();
    List<MaterialResponse> materialResponses = new ArrayList<>();
    List<DesignationsResponse> designationsResponses = new ArrayList<>();
    String materialName,materialID,unitPrice,designationID;
    int material=1;
    int designation=1;
    CircleImageView userPic;
    AppCompatTextView scrollingTextTVID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    String eligibleCommission;
    int eligible;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cp,container,false);

        preNoDataPB =view. findViewById(R.id.preNoDataPB);
        activitiesRCVID = view.findViewById(R.id.activitiesRCVID);
        activitiesRCVID.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        preActivitiesRCVID =view. findViewById(R.id.preActivitiesRCVID);
        preActivitiesRCVID.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        userID = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.CP_USER_ID);
       // userPicStr = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.CP_PROFILE_PIC);
        userPicStr = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.CP_PROFILE_PIC_N);
        Log.e("api==>", "userPicStr"+userPicStr);
        travelEligibility = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.TRAVEL_ELIGIBILITY);
        /*leadEXLVID = view.findViewById(R.id.leadEXLVID);
        leadEXLVID.setFocusable(false);
        leadEXLVID.setExpanded(true);*/
        prePB = view.findViewById(R.id.prePB);
        perPB = view.findViewById(R.id.perPB);

        Log.e("Travel_Eligibility==>", "" + travelEligibility);

        TextView adminNameID=view.findViewById(R.id.adminNameID);
        TextView designation_name=view.findViewById(R.id.designation_name);
        TextView agent_unique_id=view.findViewById(R.id.agent_unique_id);
        TextView adminNumberID=view.findViewById(R.id.adminNumberID);

        month_customer = view.findViewById(R.id.month_customer);
        month_meetings = view.findViewById(R.id.month_meetings);
        month_sale = view.findViewById(R.id.month_sale);
        overall_customer = view.findViewById(R.id.overall_customer);
        overall_meetings = view.findViewById(R.id.overall_meetings);
        overall_sale = view.findViewById(R.id.overall_sale);
        travelRequestCVID = view.findViewById(R.id.travelRequestCVID);
        //travelApprovalCVID = view.findViewById(R.id.travelApprovalCVID);
        withdrawRequestCVID = view.findViewById(R.id.withdrawRequestCVID);
        bonanzaCVID = view.findViewById(R.id.bonanzaCVID);
        RequestMaterialCVID = view.findViewById(R.id.RequestMaterialCVID);
        directReportingCVID = view.findViewById(R.id.directReportingCVID);
        directReportingCVID2 = view.findViewById(R.id.directReportingCVID2);
        melaUpdateCVID = view.findViewById(R.id.melaUpdateCVID);
        shareRLID = view.findViewById(R.id.shareRLID);
        totalTeamCVID = view.findViewById(R.id.totalTeamCVID);
        availabilityCVID = view.findViewById(R.id.availabilityCVID);
        saleHistoryCVID = view.findViewById(R.id.saleHistoryCVID);
        addAgentCVID = view.findViewById(R.id.addAgentCVID);
        clubDetailsCVID = view.findViewById(R.id.clubDetailsCVID);
        creativesCVID = view.findViewById(R.id.creativesCVID);
        siteInChargeCVID = view.findViewById(R.id.siteInChargeCVID);

        userPic = view.findViewById(R.id.userPic);
        scrollingTextTVID = view.findViewById(R.id.scrollingTextTVID);
        scrollingTextTVID.setSelected(true);


        Log.e("api==>", "userPicStr: "+userPicStr);

        try {
            Picasso.get().load(userPicStr).placeholder(R.drawable.userpic).into(userPic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        availabilityCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AvailabilityActivity.class);
            startActivity(intent);
        });

        userPic.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AgentUpdateActivity.class);
            startActivity(intent);
        });

        melaUpdateCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MelaUpdateActivity.class);
            startActivity(intent);
        });

        saleHistoryCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CPSaleHistory.class);
            startActivity(intent);
        });

        clubDetailsCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ClubDetailsActivity.class);
            startActivity(intent);
        });

        creativesCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreativesActivity.class);
            startActivity(intent);
        });

        siteInChargeCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SiteInChargesActivity.class);
            startActivity(intent);
        });

        addAgentCVID.setOnClickListener(v -> {
           // Intent intent = new Intent(getActivity(), DirectingReportingActivity.class);
            Intent intent = new Intent(getActivity(), WebViewTes.class);
            intent.putExtra("TYPE","3");
            startActivity(intent);
        });

        directReportingCVID.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DirectTeamActivity.class);
            intent.putExtra("TYPE","3");

            startActivity(intent);


        });
        directReportingCVID2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DirectTeamActivity.class);
            intent.putExtra("TYPE","1");
            startActivity(intent);
        });


        totalTeamCVID.setOnClickListener(v -> {
            /*Intent intent = new Intent(getActivity(), DirectingReportingActivity.class);
            intent.putExtra("TYPE","2");
            startActivity(intent);*/

            Intent intent = new Intent(getActivity(), DirectTeamActivity.class);
            intent.putExtra("TYPE","2");
            startActivity(intent);
        });



        bonanzaCVID.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BonanzaActivity.class);
            startActivity(intent);
        });

        travelRequestCVID.setOnClickListener(v -> {
            if (travelEligibility.equalsIgnoreCase("1")) {
                Intent intent = new Intent(getActivity(), CPTravel_Request.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "You are not eligible to create travel request", Toast.LENGTH_SHORT).show();
            }
        });



        withdrawRequestCVID.setOnClickListener(v -> {
            showWithdrawAlert();
        });

        RequestMaterialCVID.setOnClickListener(v -> {
            showRequestMaterialAlert();
        });


        adminNameID.setText(MySharedPreferences.getPreferences(getActivity(),""+AppConstants.CP_USER_NAME));
        designation_name.setText(MySharedPreferences.getPreferences(getActivity(),""+AppConstants.designation_name));
        agent_unique_id.setText(MySharedPreferences.getPreferences(getActivity(),""+AppConstants.agent_unique_id));
        adminNumberID.setText(MySharedPreferences.getPreferences(getActivity(),""+AppConstants.CP_USER_NUMBER));

        //new
        viewPager = view.findViewById(R.id.view_pager);
        dotsLayout =view. findViewById(R.id.layoutDots);
        newCreateID =view. findViewById(R.id.newCreateID);
        newCreateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SiteVisitActivity.class));
            }
        });

        //callApis();

        FloatingActionButton refreshBtn = view.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callApis();
            }
        });

        PackageManager manager =getActivity(). getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String version = info.versionName;
        AppCompatTextView versionID =view. findViewById(R.id.versionTVID);
        versionID.setText("Version : " + version);

      /*  leadEXLVID.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Doing nothing
                return true;
            }
        });*/


        shareRLID.setOnClickListener(v -> {
            showDesignationsAlert();
        });

        loadScrollingText();
        loadEligibleCommission();

        return view;
    }

    private void loadEligibleCommission() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EligibleCommissionResponse> call = apiInterface.getEligibleCommission(userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<EligibleCommissionResponse>() {
            @Override
            public void onResponse(Call<EligibleCommissionResponse> call, Response<EligibleCommissionResponse> response) {

                if (response.body() != null && response.code() == 200){

                    try {
                        EligibleCommissionResponse eligibleCommissionResponse = response.body();

                        eligibleCommission = eligibleCommissionResponse.getEligiblecommission();
                        try {
                            eligible = Integer.parseInt(eligibleCommission);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<EligibleCommissionResponse> call, Throwable t) {

            }
        });

    }

    private void loadScrollingText() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ScrollerResponse>> call = apiInterface.getScrollerText();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ScrollerResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ScrollerResponse>> call, Response<ArrayList<ScrollerResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    ArrayList<ScrollerResponse> scrollerResponses = response.body();
                    scrollingTextTVID.setText(scrollerResponses.get(0).getText());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ScrollerResponse>> call, Throwable t) {

                scrollingTextTVID.setVisibility(View.GONE);
            }
        });

    }

    private void shareDetails(Dialog dialog) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://mcp.tranquilcrm.in/Agentsform/addagent/" + userID+"/" +designationID);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
        MySharedPreferences.setPreference(getActivity(), "" + AppConstants.CP_VIEW, "YES");
        dialog.dismiss();
    }



    private void callRequirement(Spinner materialSPID, AppCompatTextView unitPriceTVID) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<MaterialResponse>> call = apiInterface.getMaterials();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<MaterialResponse>>() {

            @Override
            public void onResponse(Call<ArrayList<MaterialResponse>> call, Response<ArrayList<MaterialResponse>> response) {

                if (response.body() != null && response.code() == 200) {
                    ArrayList<MaterialResponse> requirementResponses = new ArrayList<>();
                    requirementResponses = response.body();
                    materialResponses = requirementResponses;
                    if (requirementResponses != null && requirementResponses.size() > 0) {
                        for (int i = 0; i < requirementResponses.size(); i++) {
                            MaterialsAdapter adapter = new MaterialsAdapter(getActivity(), R.layout.custom_spinner_view, requirementResponses);
                            materialSPID.setAdapter(adapter);
                        }

                        materialSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                materialID = materialResponses.get(i).getMaterial_id();

                                Log.e("material==>", "" + materialID);

                                unitPrice = materialResponses.get(i).getUnit_price();
                                //unitPriceTVID.setText(unitPrice);
                                unitPriceTVID.setText(materialResponses.get(i).getUnit_price());

                                Log.e("price==>","" + materialResponses.get(i).getUnit_price() );
                                //actID = activityList.get(i).getActvty_typ_id();
                                if (material == 1) {
                                    material++;
                                    for (int j = 0; j < materialResponses.size(); j++) {

                                        materialSPID.setSelection(j);

                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Details not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Details not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MaterialResponse>> call, Throwable t) {

                Toast.makeText(getActivity(), "Details not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callDesignations(Spinner designationSPID) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DesignationsResponse>> call = apiInterface.getDesignations(userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DesignationsResponse>>() {

            @Override
            public void onResponse(Call<ArrayList<DesignationsResponse>> call, Response<ArrayList<DesignationsResponse>> response) {

                if (response.body() != null && response.code() == 200) {
                    ArrayList<DesignationsResponse> requirementResponses = new ArrayList<>();
                    requirementResponses = response.body();
                    designationsResponses = requirementResponses;
                    if (requirementResponses != null && requirementResponses.size() > 0) {
                        for (int i = 0; i < requirementResponses.size(); i++) {
                            DesignationsAdapter adapter = new DesignationsAdapter(getActivity(), R.layout.custom_spinner_view, requirementResponses);
                            designationSPID.setAdapter(adapter);
                        }

                        designationSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                designationID = designationsResponses.get(i).getDesignation_id();

                                Log.e("designationID==>", "" + designationID);




                                //actID = activityList.get(i).getActvty_typ_id();
                                if (designation == 1) {
                                    designation++;
                                    for (int j = 0; j < designationsResponses.size(); j++) {

                                        designationSPID.setSelection(j);

                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Details not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Details not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DesignationsResponse>> call, Throwable t) {

                Toast.makeText(getActivity(), "Details not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRequestMaterialAlert() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.request_material_alert);
        int height2 = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width2 = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width2, height2);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Spinner materialSPID;
        AppCompatTextView unitPriceTVID;
        AppCompatEditText quantityETID;
        AppCompatButton submitBtn;

        materialSPID = dialog.findViewById(R.id.materialSPID);
        unitPriceTVID = dialog.findViewById(R.id.unitPriceTVID);
        quantityETID = dialog.findViewById(R.id.quantityETID);
        submitBtn = dialog.findViewById(R.id.submitBtn);


        callRequirement(materialSPID,unitPriceTVID);


        submitBtn.setOnClickListener(v -> {

            materialValidations(quantityETID,dialog);

        });

        /**/


    }

    private void showDesignationsAlert() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.designations_alert);
        int height2 = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width2 = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width2, height2);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Spinner designationSPID;
        AppCompatTextView unitPriceTVID;
        AppCompatEditText quantityETID;
        AppCompatButton submitBtn;

        designationSPID = dialog.findViewById(R.id.designationSPID);
        submitBtn = dialog.findViewById(R.id.submitBtn);


        callDesignations(designationSPID);


        submitBtn.setOnClickListener(v -> {

            shareDetails(dialog);

        });

        /**/


    }



    private void materialValidations(AppCompatEditText quantityETID, Dialog dialog) {

        String quantity = quantityETID.getText().toString();

        if (TextUtils.isEmpty(quantity)){
            Toast.makeText(getActivity(), "Enter Quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Utilities.isNetworkAvailable(getActivity())){
            materialRequest(quantity,dialog);
        }else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void materialRequest(String quantity, Dialog dialog) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponseNew>> call = apiInterface.getRequestMaterial(materialID,quantity,unitPrice,userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<StatusResponseNew>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseNew>> call, Response<ArrayList<StatusResponseNew>> response) {

                if (response.body() != null && response.code() == 200){
                    ArrayList<StatusResponseNew> statusResponseNew = new ArrayList<>();
                    statusResponseNew = response.body();

                    if (statusResponseNew.get(0).getStatus().equalsIgnoreCase("Inserted")){

                        Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseNew>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.dismiss();
    }

    @SuppressLint("SetTextI18n")
    private void showWithdrawAlert() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.commission_withdraw_request);
        int height2 = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width2 = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width2, height2);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        AppCompatEditText amountETID,notesETID;
        AppCompatButton submitBtn,submitBtnDummy;
        AppCompatTextView eligibleTVID;
        
        amountETID = dialog.findViewById(R.id.amountETID);
        notesETID = dialog.findViewById(R.id.notesETID);
        submitBtn = dialog.findViewById(R.id.submitBtn);
        submitBtnDummy = dialog.findViewById(R.id.submitBtnDummy);
        eligibleTVID = dialog.findViewById(R.id.eligibleTVID);

        if (eligibleCommission.equalsIgnoreCase("0")){

            /*amountETID.setVisibility(View.GONE);
            submitBtn.setVisibility(View.GONE);
            notesETID.setVisibility(View.GONE);*/
            submitBtn.setVisibility(View.GONE);


        }else {

            /*amountETID.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.VISIBLE);
            notesETID.setVisibility(View.VISIBLE);*/
            submitBtn.setVisibility(View.VISIBLE);


        }

        eligibleTVID.setText("*Max withdraw limit is " +""+ eligibleCommission);

        try {
            amountETID.setText(eligibleCommission);
        } catch (Exception e) {
            e.printStackTrace();
        }

        amountETID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String enteredValue = Objects.requireNonNull(amountETID.getText()).toString();

                try {
                    if (Long.parseLong(enteredValue) > eligible){
                        amountETID.setError("Please enter less then or equal eligible commission");
                        submitBtn.setVisibility(View.GONE);
                        submitBtnDummy.setVisibility(View.VISIBLE);
                        return;
                    }else {
                        submitBtn.setVisibility(View.VISIBLE);
                        submitBtnDummy.setVisibility(View.GONE);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            }
        });

        submitBtn.setOnClickListener(v -> {
            validations(amountETID,notesETID,dialog);
        });

    }

    private void validations(AppCompatEditText amountETID, AppCompatEditText notesETID, Dialog dialog) {
        String amountStr,notesStr;

        amountStr = Objects.requireNonNull(amountETID.getText()).toString();
        notesStr = Objects.requireNonNull(notesETID.getText()).toString();
        
        if (TextUtils.isEmpty(amountStr)){
            Toast.makeText(getActivity(), "Please enter amount", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(notesStr)){
            Toast.makeText(getActivity(), "Please enter notes", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Utilities.isNetworkAvailable(getContext())){
            getWithdrawRequest(amountStr,notesStr,dialog);
        }else {
            Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();
        }

    }

    private void getWithdrawRequest(String amountStr, String notesStr, Dialog dialog) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponseNew>> call = apiInterface.getWithDrawRequest(userID,amountStr,notesStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<StatusResponseNew>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseNew>> call, Response<ArrayList<StatusResponseNew>> response) {

                if (response.body() != null && response.code() == 200){
                    ArrayList<StatusResponseNew> statusResponseNews = new ArrayList<>();

                    statusResponseNews = response.body();

                    if (statusResponseNews.get(0).getStatus().equalsIgnoreCase("Inserted")){

                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();

                    }   else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseNew>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();
    }

    private void callApis() {
        if (Utilities.isNetworkAvailable(getContext())) {
            //callOverAllMainCountApi();
            //callTodayMainCountApi();
            //loadDashBoardCount();
        //    leadEXLVID.setExpanded(true);
         //   leadEXLVID.setFocusable(false);
        } else {
            Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDashBoardCount() {
        perPB.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DashBordPerformanceResponse>> call = apiInterface.getUserDashBoardCount(userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DashBordPerformanceResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<DashBordPerformanceResponse>> call, Response<ArrayList<DashBordPerformanceResponse>> response) {
                perPB.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<DashBordPerformanceResponse> countResponses = response.body();
                    if (countResponses.size() > 0) {
                        for (int i = 0; i < countResponses.size(); i++) {

                           /* lists.clear();
                            lists.add(new PerformanceList("Month Performance", "", "" + countResponses.get(i).getMnth_enq(), "" + countResponses.get(i).getMnth_sitevisit(), "" + countResponses.get(i).getMnth_sale(), "0.00%", "", "", ""));
                            lists.add(new PerformanceList("Overall Performance", "", "" + countResponses.get(i).getOverall_enq(), "" + countResponses.get(i).getOverall_sitevisit(), "" + countResponses.get(i).getOverall_sale(), "0.00%", "", "", ""));
                            myViewPagerAdapter = new PerformanceAdapter(getActivity(), lists);
                            viewPager.setAdapter(myViewPagerAdapter);
                            viewPager.setPageMargin(64);*/
                           // addBottomDots(0);
                           // viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


                            try {
                                month_customer.setText(countResponses.get(i).getMnth_enq());
                                month_meetings.setText(countResponses.get(i).getMnth_sitevisit());
                                month_sale.setText(countResponses.get(i).getMnth_sale());
                                overall_customer.setText(countResponses.get(i).getOverall_enq());
                                overall_meetings.setText(countResponses.get(i).getOverall_sitevisit());
                                overall_sale.setText(countResponses.get(i).getOverall_sale());
                                // Picasso.with(DashBoardActivity.this).load(countResponses.get(i).getPic()).into(crmProfilePic);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Data not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DashBordPerformanceResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Data not loaded", Toast.LENGTH_SHORT).show();
                perPB.setVisibility(View.GONE);
            }
        });
    }

    private void callOverAllMainCountApi() {
        prePB.setVisibility(View.VISIBLE);
        activitiesRCVID.setVisibility(View.GONE);
        preNoDataPB.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<NewDashboardResponse>> call = apiInterface.getOverAllMAinCount(userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<NewDashboardResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<NewDashboardResponse>> call, Response<ArrayList<NewDashboardResponse>> response) {
                prePB.setVisibility(View.GONE);
                activitiesRCVID.setVisibility(View.VISIBLE);
                preNoDataPB.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<NewDashboardResponse> todayMainCountResponses = response.body();
                    if (todayMainCountResponses.size() > 0) {
                        DashBoardActivitiesAdapter adapter = new DashBoardActivitiesAdapter(getActivity(), todayMainCountResponses);
                        activitiesRCVID.setAdapter(adapter);
                        //permissions();

                    }
                    else {
                        prePB.setVisibility(View.GONE);
                        activitiesRCVID.setVisibility(View.GONE);
                        preNoDataPB.setVisibility(View.VISIBLE);
                    }
                } else {
                    prePB.setVisibility(View.GONE);
                    activitiesRCVID.setVisibility(View.GONE);
                    preNoDataPB.setVisibility(View.VISIBLE);
                }

                    /*if (todayMainCountResponses != null && todayMainCountResponses.size() > 0) {
                        for (int i = 0; i < todayMainCountResponses.size(); i++) {
                        *//*    overallSaleCount.setText("Sale" + "( " + todayMainCountResponses.get(i).getOverallsale_counts() + ")");
                            overallNegotiationCount.setText("Negotiation" + "(" + todayMainCountResponses.get(i).getOverallnego_counts() + ")");
                            overallPhoneCount.setText("Calls" + "(" + todayMainCountResponses.get(i).getOverallph_count() + ")");
                            overallMeetingsCount.setText("Site Visit" + "(" + todayMainCountResponses.get(i).getOverallsitevisit_count() + ")");
                           *//*
                            overall.clear();
                            overall.add(new DashboardChildResponse("activitiesplannedphonecall", "Phone Calls", "", "" + todayMainCountResponses.get(i).getOverallph_count()));

                            overall.add(new DashboardChildResponse("activitiesplannedfollowup", "C.M. Followup", "", "" + todayMainCountResponses.get(i).getOverallcm_count()));
                           overall.add(new DashboardChildResponse("activitiesplannedcustomersmeet", "Customers Meet", "", "" + todayMainCountResponses.get(i).getOverallsitevisit_count()));
                            overall.add(new DashboardChildResponse("activitiesplannednegofollowup", "Nego Followup", "", "" + todayMainCountResponses.get(i).getOverallnego_counts()));
                            callTodayMainCountApi();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.something, Toast.LENGTH_SHORT).show();
                    }*/

            }

            @Override
            public void onFailure(Call<ArrayList<NewDashboardResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.something, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callTodayMainCountApi() {
        prePB.setVisibility(View.VISIBLE);
        preActivitiesRCVID.setVisibility(View.GONE);
        preNoDataPB.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<NewDashboardResponse>> call = apiInterface.getTodayMAinCount(userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<NewDashboardResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<NewDashboardResponse>> call, Response<ArrayList<NewDashboardResponse>> response) {
                prePB.setVisibility(View.GONE);
                preActivitiesRCVID.setVisibility(View.VISIBLE);
                preNoDataPB.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<NewDashboardResponse> todayMainCountResponses = response.body();
                    if (todayMainCountResponses.size() > 0) {
                        DashBoardPlannedAdapter adapter = new DashBoardPlannedAdapter(getActivity(), todayMainCountResponses);
                        preActivitiesRCVID.setAdapter(adapter);
                        //permissions();
                    }
                    else {
                        prePB.setVisibility(View.GONE);
                        preActivitiesRCVID.setVisibility(View.GONE);
                        preNoDataPB.setVisibility(View.VISIBLE);
                    }
                } else {
                    prePB.setVisibility(View.GONE);
                    preActivitiesRCVID.setVisibility(View.GONE);
                    preNoDataPB.setVisibility(View.VISIBLE);
                }

                    /*if (todayMainCountResponses != null && todayMainCountResponses.size() > 0) {
                        for (int i = 0; i < todayMainCountResponses.size(); i++) {
                        *//*    overallSaleCount.setText("Sale" + "( " + todayMainCountResponses.get(i).getOverallsale_counts() + ")");
                            overallNegotiationCount.setText("Negotiation" + "(" + todayMainCountResponses.get(i).getOverallnego_counts() + ")");
                            overallPhoneCount.setText("Calls" + "(" + todayMainCountResponses.get(i).getOverallph_count() + ")");
                            overallMeetingsCount.setText("Site Visit" + "(" + todayMainCountResponses.get(i).getOverallsitevisit_count() + ")");
                           *//*
                            overall.clear();
                            overall.add(new DashboardChildResponse("activitiesplannedphonecall", "Phone Calls", "", "" + todayMainCountResponses.get(i).getOverallph_count()));

                            overall.add(new DashboardChildResponse("activitiesplannedfollowup", "C.M. Followup", "", "" + todayMainCountResponses.get(i).getOverallcm_count()));
                           overall.add(new DashboardChildResponse("activitiesplannedcustomersmeet", "Customers Meet", "", "" + todayMainCountResponses.get(i).getOverallsitevisit_count()));
                            overall.add(new DashboardChildResponse("activitiesplannednegofollowup", "Nego Followup", "", "" + todayMainCountResponses.get(i).getOverallnego_counts()));
                            callTodayMainCountApi();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.something, Toast.LENGTH_SHORT).show();
                    }*/

            }

            @Override
            public void onFailure(Call<ArrayList<NewDashboardResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.something, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class PerformanceList {
        private String title;
        private String dialed;
        private String calls;
        private String meetings;
        private String sale;
        private String performance;
        private String business;
        private String totalSale;
        private String totalMeetings;

        public PerformanceList(String title, String dialed, String calls, String meetings, String sale, String performance, String business, String totalSale, String totalMeetings) {
            this.title = title;
            this.dialed = dialed;
            this.calls = calls;
            this.meetings = meetings;
            this.sale = sale;
            this.performance = performance;
            this.business = business;
            this.totalSale = totalSale;
            this.totalMeetings = totalMeetings;
        }

        public String getDialed() {
            return dialed;
        }

        public void setDialed(String dialed) {
            this.dialed = dialed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCalls() {
            return calls;
        }

        public void setCalls(String calls) {
            this.calls = calls;
        }

        public String getMeetings() {
            return meetings;
        }

        public void setMeetings(String meetings) {
            this.meetings = meetings;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public String getPerformance() {
            return performance;
        }

        public void setPerformance(String performance) {
            this.performance = performance;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getTotalSale() {
            return totalSale;
        }

        public void setTotalSale(String totalSale) {
            this.totalSale = totalSale;
        }

        public String getTotalMeetings() {
            return totalMeetings;
        }

        public void setTotalMeetings(String totalMeetings) {
            this.totalMeetings = totalMeetings;
        }
    }

    public class PerformanceAdapter extends PagerAdapter {
        private LayoutInflater inflater;
        Activity activity;
        ArrayList<PerformanceList> performanceLists = new ArrayList<>();


        public PerformanceAdapter(Activity activity, ArrayList<PerformanceList> performanceLists) {
            inflater = LayoutInflater.from(activity);
            this.activity = activity;
            this.performanceLists = performanceLists;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.cp_dash_board_performance_item, container, false);

            try {
                TextView perTitleID = view.findViewById(R.id.perTitleID);
                TextView dialedTVID = view.findViewById(R.id.dialedTVID);
                TextView perCallsTVID = view.findViewById(R.id.perCallsTVID);
                TextView perMeetTVID = view.findViewById(R.id.perMeetTVID);
                TextView perSaleTVID = view.findViewById(R.id.perSaleTVID);
                TextView perBusTVID = view.findViewById(R.id.perBusTVID);
                TextView perTotalSaleTVID = view.findViewById(R.id.perTotalSaleTVID);
                TextView perTotMeetTVID = view.findViewById(R.id.perTotMeetTVID);
                TextView perPerformanceTVID = view.findViewById(R.id.perPerformanceTVID);


                perTitleID.setText(performanceLists.get(position).getTitle());
                perCallsTVID.setText(performanceLists.get(position).getCalls());
                dialedTVID.setText(performanceLists.get(position).getDialed());
                perMeetTVID.setText(performanceLists.get(position).getMeetings());
                perSaleTVID.setText(performanceLists.get(position).getSale());
                perBusTVID.setText(performanceLists.get(position).getBusiness());
                perTotalSaleTVID.setText(performanceLists.get(position).getTotalSale());
                perTotMeetTVID.setText(performanceLists.get(position).getTotalMeetings());
                perPerformanceTVID.setText(performanceLists.get(position).getPerformance());

                RelativeLayout bgCircleRLID = view.findViewById(R.id.bgCircleRLID);
            } catch (Exception e) {
                e.printStackTrace();
            }


            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return performanceLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    @SuppressLint("SetTextI18n")
    private void addBottomDots(int currentPage) {
        try {
            dots = new TextView[lists.size()];
            int[] colorsActive = getResources().getIntArray(R.array.array_dot_new_active);
            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_new_inactive);

            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                try {
                    dots[i] = new TextView(getActivity());
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(35);
                    dots[i].setTextColor(colorsInactive[currentPage]);
                    dotsLayout.addView(dots[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive[currentPage]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public class DashBoardActivitiesAdapter extends RecyclerView.Adapter<DashBoardActivitiesAdapter.PreSalesVH> {

        Context context;
        ArrayList<NewDashboardResponse> todayMainCountResponses = new ArrayList<NewDashboardResponse>();
        private String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#2196F3"};
        private int[] activityIcons = {R.drawable.menu_calls, R.drawable.menu_sale, R.drawable.menu_calls, R.drawable.menu_nego, R.drawable.menu_sale};

        private String userType;
        private String typeStr;



        public DashBoardActivitiesAdapter(Context context, ArrayList<NewDashboardResponse> todayMainCountResponses) {
            this.context = context;
            this.todayMainCountResponses = todayMainCountResponses;
            this.userType = userType;
            this.typeStr = typeStr;
        }

        @NonNull
        @Override
        public DashBoardActivitiesAdapter.PreSalesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new DashBoardActivitiesAdapter.PreSalesVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_pre_sales_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DashBoardActivitiesAdapter.PreSalesVH holder, @SuppressLint("RecyclerView") final int i) {
            holder.activityCountTVID.setText(todayMainCountResponses.get(i).getCount());
            String id = todayMainCountResponses.get(i).getActvty_typ_id();
            holder.activityCountTVID.setVisibility(View.VISIBLE);
            holder.activityNameTVID.setText(todayMainCountResponses.get(i).getActvty_typ_nm());
            Picasso.get().load(activityIcons[i % 5]).into(holder.activityIconID);
            holder.activityIconID.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            holder.activityLLID.setCardBackgroundColor(Color.parseColor(colors[i % 5]));

            holder.activityLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, LeadsViewActivity.class);
                    intent.putExtra("URL", ""+todayMainCountResponses.get(i).getActvty_typ_id());
                    intent.putExtra("Tittle", ""+todayMainCountResponses.get(i).getActvty_typ_nm());
                    context.startActivity(intent);
                }
            });
           /* holder.activityLLID.setOnClickListener(v -> {

                Intent intent;

                intent = new Intent(context, NewPlannedActivities.class);
                intent.putExtra("ACTIVITY_ID", "" + childArrayList.get(i).getActvty_typ_id());
                intent.putExtra("ACTIVITY_NAME", "" + childArrayList.get(i).getActvty_typ_nm());
                intent.putExtra("" + AppConstants.U_ID, "" + userID);
                intent.putExtra("" + AppConstants.U_NAME, "" + name);
                intent.putExtra("" + AppConstants.U_TYPE, "" + userType);
                intent.putExtra("" + AppConstants.U_PIC, "" + pic);
                intent.putExtra("" + AppConstants.U_MOBILE, "" + mobile);
                intent.putExtra("" + AppConstants.U_EMAIL, "" + email);
                startActivity(intent);
            });*/
        }

        @Override
        public int getItemCount() {
            return todayMainCountResponses.size();
        }

        public class PreSalesVH extends RecyclerView.ViewHolder {

          ImageView activityIconID;
            AppCompatTextView activityCountTVID, activityNameTVID;
            CardView activityLLID;

            public PreSalesVH(@NonNull View itemView) {
                super(itemView);
                activityIconID = itemView.findViewById(R.id.activityIconID);
                activityCountTVID = itemView.findViewById(R.id.activityCountTVID);
                activityNameTVID = itemView.findViewById(R.id.activityNameTVID);
                activityLLID = itemView.findViewById(R.id.activityLLID);
            }
        }
    }
    public class DashBoardPlannedAdapter extends RecyclerView.Adapter<DashBoardPlannedAdapter.PreSalesVH> {

        Context context;
        ArrayList<NewDashboardResponse> todayMainCountResponses = new ArrayList<NewDashboardResponse>();
        private String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#2196F3"};
        private int[] activityIcons = {R.drawable.menu_calls, R.drawable.menu_sale, R.drawable.menu_calls, R.drawable.menu_nego, R.drawable.menu_sale};

        private String userType;
        private String typeStr;



        public DashBoardPlannedAdapter(Context context, ArrayList<NewDashboardResponse> todayMainCountResponses) {
            this.context = context;
            this.todayMainCountResponses = todayMainCountResponses;
            this.userType = userType;
            this.typeStr = typeStr;
        }

        @NonNull
        @Override
        public DashBoardPlannedAdapter.PreSalesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new DashBoardPlannedAdapter.PreSalesVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_sales_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DashBoardPlannedAdapter.PreSalesVH holder, @SuppressLint("RecyclerView") final int i) {
            holder.activityCountTVID.setText(todayMainCountResponses.get(i).getCount());
            String id = todayMainCountResponses.get(i).getActvty_typ_id();
            holder.activityCountTVID.setVisibility(View.VISIBLE);
            holder.activityNameTVID.setText(todayMainCountResponses.get(i).getActvty_typ_nm());
            Picasso.get().load(activityIcons[i % 5]).into(holder.activityIconID);
            holder.activityIconID.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            holder.activityLLID.setCardBackgroundColor(Color.parseColor(colors[i % 5]));
            holder.activityLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, LeadsCompleteView.class);
                    intent.putExtra("URL", ""+todayMainCountResponses.get(i).getActvty_typ_id());
                    intent.putExtra("Tittle", ""+todayMainCountResponses.get(i).getActvty_typ_nm());
                    context.startActivity(intent);
                }
            });


           /* holder.activityLLID.setOnClickListener(v -> {

                Intent intent;

                intent = new Intent(context, NewPlannedActivities.class);
                intent.putExtra("ACTIVITY_ID", "" + childArrayList.get(i).getActvty_typ_id());
                intent.putExtra("ACTIVITY_NAME", "" + childArrayList.get(i).getActvty_typ_nm());
                intent.putExtra("" + AppConstants.U_ID, "" + userID);
                intent.putExtra("" + AppConstants.U_NAME, "" + name);
                intent.putExtra("" + AppConstants.U_TYPE, "" + userType);
                intent.putExtra("" + AppConstants.U_PIC, "" + pic);
                intent.putExtra("" + AppConstants.U_MOBILE, "" + mobile);
                intent.putExtra("" + AppConstants.U_EMAIL, "" + email);
                startActivity(intent);
            });*/
        }

        @Override
        public int getItemCount() {
            return todayMainCountResponses.size();
        }

        public class PreSalesVH extends RecyclerView.ViewHolder {

            ImageView activityIconID;
            AppCompatTextView activityCountTVID, activityNameTVID;
            CardView activityLLID;

            public PreSalesVH(@NonNull View itemView) {
                super(itemView);
                activityIconID = itemView.findViewById(R.id.activityIconID);
                activityCountTVID = itemView.findViewById(R.id.activityCountTVID);
                activityNameTVID = itemView.findViewById(R.id.activityNameTVID);
                activityLLID = itemView.findViewById(R.id.activityLLID);
            }
        }
    }

}
