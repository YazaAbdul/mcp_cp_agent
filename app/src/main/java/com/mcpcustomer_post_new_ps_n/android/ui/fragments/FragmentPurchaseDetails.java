package com.mcpcustomer_post_new_ps_n.android.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.ViewAadharCardImage;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.ViewPanCardImage;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.checklist.CustomerDetailsActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.ViewImageActivity;

import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.ViewGallery;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DiscountGivenAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.PaidAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectsPurchaseAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.RegistrationDelayAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.RegistrationPaymentsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BankerCheckListDTO;

import com.mcpcustomer_post_new_ps_n.android.ui.models.DueResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.PersonalDetailsOBJ;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;
import com.mcpcustomer_post_new_ps_n.android.ui.models.TotalmilesAmountOBJ;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewRegistrationDelayChargesResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewRegistrationPaymentDetailsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewTransactionPaidResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewTrasactionDiscountResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPurchaseDetails extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainViewRLID;
    private FloatingActionButton uploadDocs;


    //personal Views
    private LinearLayout personalViewLLID;
    private ProgressBar personalPB;
    private AppCompatTextView customerNameTVID, customerMobileTVID, customerAlternateNumberTVID, customerFatherNameTVID,
            nomineeNameTVID, customerEmailTVID, relationTVID, spouseNameTVID, addressTVID, perAddressTVID,
            projectNameTVID, plotNumberTVID, sqydTVID, totalAmountTVID;

    //Application
    private AppCompatTextView aNameTVID, aNomineeNameTVID, aSpouseTVID, aAlternateTVID, aRelationTVID, aAddressTVID, aPAddressTVID, aContactTVID, aEmailTVID;
    private AppCompatImageView familyIMVID, passportIMVID, idIMVID, addressIMVID, pdcIMVID, formIMVID, requisitionIMVID;

    //documents
    private LinearLayout familyLLID, personalLLID, idLLID, addressLLID, pdcLLID, formLLID, requisitionLLID;

    //transaction
    private RecyclerView paidRCID, receivedRCID, paidNewRCID;


    private Float finalMileStoneAmount = 0.000f;
    private Float finalDiscountAmount = 0.000f;
    private Float finalPaidAmount = 0.000f;

    private Float globalScheduledAmount = 0.000f;
    private Float globalTotalAmount = 0.000f;

    private Float finalRgistratiionDelayAmount = 0.000f;
    private Float finalRgistratiionPaidAmount = 0.000f;

    private Float globalRgistrationPaidAmount = 0.000f;

    private AppCompatTextView scheduledFinalAMountTVID, paidFinalAMountTVID, finalTotalAmountTVID;
    private AppCompatTextView regDelayFinalAMountTVID, regPayFinalAMountTVID, regFinalAMountTVID,headerTitleTVID;

    //other payments
    RecyclerView registrationDelayCID, registrationPaidCID;


    //checklist
    private RecyclerView checkListRCID;
    private ProgressBar checkListPB;
    private AppCompatTextView checkListTVID;
    RelativeLayout headerBackRLID;

    //agreements
    private AppCompatTextView linkTVID, saleDeedTVID,agreementTVID;
    private LinearLayout agreementLLID;


    private ArrayList<DueResponse> dueResponses = new ArrayList<>();
    private ArrayList<DueResponse> paidResponse = new ArrayList<>();

    private DueTransactionAdapter dueAdapter;
    private DueTransactionAdapter paidAdapter;

    private String customerIDStr, familyStr, passportStr, idStr, addressStr, pdcStr, formStr, requisitionStr,
            linkStr, saleStr, agreementStr;

    Button referCustomerBtn;
    ArrayList<ProjectsResponse> projectsResponses = new ArrayList<>();
    int project=1;
    String projectID,customerID,soldBY;



    private String bankerCheckListID;

    //image upload
    private String selectImagePath = "null";
    Uri selectImageUri;
    private static final int PICK_IMAGE = 100;
    private static final int PERMISSION_STORAGE = 2;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_purchase_details);
        customerIDStr = MySharedPreferences.getPreferences(FragmentPurchaseDetails.this, "" + AppConstants.CUSTOMER_ID);

        //customerIDStr = "10";
        mainViewRLID = findViewById(R.id.mainViewRLID);

        uploadDocs = findViewById(R.id.uploadDocs);
        uploadDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FragmentPurchaseDetails.this, CustomerDetailsActivity.class));
            }
        });

        //personal
        personalViewLLID = findViewById(R.id.personalViewLLID);
        personalPB = findViewById(R.id.personalPB);

        headerTitleTVID=findViewById(R.id.headerTitleTVID);
        headerTitleTVID.setText("Purchase Details");

        headerBackRLID=findViewById(R.id.headerBackRLID);
        headerBackRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        customerNameTVID = findViewById(R.id.customerNameTVID);
        customerMobileTVID = findViewById(R.id.customerMobileTVID);
        customerAlternateNumberTVID = findViewById(R.id.customerAlternateNumberTVID);
        customerFatherNameTVID = findViewById(R.id.customerFatherNameTVID);
        nomineeNameTVID = findViewById(R.id.nomineeNameTVID);
        customerEmailTVID = findViewById(R.id.customerEmailTVID);
        relationTVID = findViewById(R.id.relationTVID);
        spouseNameTVID = findViewById(R.id.spouseNameTVID);
        addressTVID = findViewById(R.id.addressTVID);
        perAddressTVID = findViewById(R.id.perAddressTVID);

        //purchase details
        projectNameTVID = findViewById(R.id.projectNameTVID);
        plotNumberTVID = findViewById(R.id.plotNumberTVID);
        sqydTVID = findViewById(R.id.sqydTVID);
        totalAmountTVID = findViewById(R.id.totalAmountTVID);

        //application
        aNameTVID = findViewById(R.id.aNameTVID);
        aNomineeNameTVID = findViewById(R.id.aNomineeNameTVID);
        aSpouseTVID = findViewById(R.id.aSpouseTVID);
        aAlternateTVID = findViewById(R.id.aAlternateTVID);
        aRelationTVID = findViewById(R.id.aRelationTVID);
        aAddressTVID = findViewById(R.id.aAddressTVID);
        aPAddressTVID = findViewById(R.id.aPAddressTVID);
        aContactTVID = findViewById(R.id.aContactTVID);
        aEmailTVID = findViewById(R.id.aEmailTVID);
        familyIMVID = findViewById(R.id.familyIMVID);
        passportIMVID = findViewById(R.id.passportIMVID);
        idIMVID = findViewById(R.id.idIMVID);
        addressIMVID = findViewById(R.id.addressIMVID);
        pdcIMVID = findViewById(R.id.pdcIMVID);
        formIMVID = findViewById(R.id.formIMVID);
        requisitionIMVID = findViewById(R.id.requisitionIMVID);

        //Transaction History
        paidRCID = findViewById(R.id.paidRCID);
        receivedRCID = findViewById(R.id.receivedRCID);
        LinearLayoutManager pa = new LinearLayoutManager(FragmentPurchaseDetails.this, RecyclerView.VERTICAL, false);


        paidRCID.setLayoutManager(pa);
        LinearLayoutManager re = new LinearLayoutManager(FragmentPurchaseDetails.this, RecyclerView.VERTICAL, false);
        receivedRCID.setLayoutManager(re);


        paidNewRCID = findViewById(R.id.paidNewRCID);
        paidNewRCID.setLayoutManager(new GridLayoutManager(FragmentPurchaseDetails.this, 1));

        scheduledFinalAMountTVID = findViewById(R.id.scheduledFinalAMountTVID);
        paidFinalAMountTVID = findViewById(R.id.paidFinalAMountTVID);
        finalTotalAmountTVID = findViewById(R.id.finalTotalAmountTVID);

        //other payments history
        registrationDelayCID = findViewById(R.id.registrationDelayCID);
        registrationDelayCID.setLayoutManager(new GridLayoutManager(FragmentPurchaseDetails.this, 1));

        registrationPaidCID = findViewById(R.id.registrationPaidCID);
        registrationPaidCID.setLayoutManager(new GridLayoutManager(FragmentPurchaseDetails.this, 1));


        regDelayFinalAMountTVID = findViewById(R.id.regDelayFinalAMountTVID);
        regPayFinalAMountTVID = findViewById(R.id.regPayFinalAMountTVID);
        regFinalAMountTVID = findViewById(R.id.regFinalAMountTVID);


        //documents
        familyLLID = findViewById(R.id.familyLLID);
        personalLLID = findViewById(R.id.personalLLID);
        idLLID = findViewById(R.id.idLLID);
        addressLLID = findViewById(R.id.addressLLID);
        pdcLLID = findViewById(R.id.pdcLLID);
        formLLID = findViewById(R.id.formLLID);
        requisitionLLID = findViewById(R.id.requisitionLLID);

        referCustomerBtn = findViewById(R.id.referCustomerBtn);

        familyLLID.setOnClickListener(this);
        personalLLID.setOnClickListener(this);
        idLLID.setOnClickListener(this);
        addressLLID.setOnClickListener(this);
        pdcLLID.setOnClickListener(this);
        formLLID.setOnClickListener(this);
        requisitionLLID.setOnClickListener(this);
        referCustomerBtn.setOnClickListener(this);

        //checklist
        checkListRCID = findViewById(R.id.checkListRCID);
        checkListPB = findViewById(R.id.checkListPB);
        checkListTVID = findViewById(R.id.checkListTVID);
        LinearLayoutManager ch = new LinearLayoutManager(FragmentPurchaseDetails.this, RecyclerView.VERTICAL, false);
        checkListRCID.setLayoutManager(ch);


        //agreements details
        linkTVID = findViewById(R.id.linkTVID);
        saleDeedTVID = findViewById(R.id.saleDeedTVID);
        agreementLLID = findViewById(R.id.agreementLLID);
        agreementTVID = findViewById(R.id.agreementTVID);

        agreementLLID.setOnClickListener(this);
        saleDeedTVID.setOnClickListener(this);
        agreementTVID.setOnClickListener(this);

        linkTVID.setOnClickListener(this);

        if (Utilities.isNetworkAvailable(FragmentPurchaseDetails.this)) {
            callApis();
        } else {
            Utilities.customMessage(mainViewRLID, FragmentPurchaseDetails.this, "No Internet connection");
        }

    }

    private void callApis() {
        loadPersonalDetails();
        loadMileStoneAPI(customerIDStr);
        loadBankerCheckList(customerIDStr);
        loadTotalamount(customerIDStr);

        loadRegistrationDelayCharges(customerIDStr);
    }
    private void loadTotalamount(String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TotalmilesAmountOBJ> call = apiInterface.totalmilestoneamtAPi(customerIDStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<TotalmilesAmountOBJ>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TotalmilesAmountOBJ> call, Response<TotalmilesAmountOBJ> response) {
                personalPB.setVisibility(View.GONE);
                personalViewLLID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    TotalmilesAmountOBJ detailsOBJ = response.body();
                    scheduledFinalAMountTVID.setText("₹"+detailsOBJ.getTotal());
                    finalTotalAmountTVID.setText("₹"+detailsOBJ.getDueamt());
                    paidFinalAMountTVID.setText("₹"+detailsOBJ.getPaidamt());




                } else {
                    personalViewLLID.setVisibility(View.VISIBLE);
                    Utilities.customMessage(mainViewRLID, FragmentPurchaseDetails.this, "Details not loading");
                }
            }

            @Override
            public void onFailure(Call<TotalmilesAmountOBJ> call, Throwable t) {
                personalPB.setVisibility(View.GONE);
                personalViewLLID.setVisibility(View.VISIBLE);
                Utilities.customMessage(mainViewRLID, FragmentPurchaseDetails.this, "Details not loading");
            }
        });
    }

    private void loadBankerCheckList(String customerIDStr) {
        checkListPB.setVisibility(View.VISIBLE);
        checkListRCID.setVisibility(View.GONE);
        checkListTVID.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<BankerCheckListDTO>> call = apiInterface.bankerCheckListApi(customerIDStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<BankerCheckListDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<BankerCheckListDTO>> call, Response<ArrayList<BankerCheckListDTO>> response) {
                checkListPB.setVisibility(View.GONE);
                checkListRCID.setVisibility(View.VISIBLE);
                checkListTVID.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200) {
                    ArrayList<BankerCheckListDTO> bankerCheckListDTOArrayList = response.body();
                    if (bankerCheckListDTOArrayList.size() > 0) {
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.REFRESH_PAGE, "NO");
                        CheckListAdapter checkListAdapter = new CheckListAdapter(FragmentPurchaseDetails.this, bankerCheckListDTOArrayList);
                        checkListRCID.setAdapter(checkListAdapter);
                    } else {
                        checkListRCID.setVisibility(View.GONE);
                        checkListTVID.setVisibility(View.GONE);
                        checkListTVID.setVisibility(View.VISIBLE);
                    }
                } else {
                    checkListRCID.setVisibility(View.GONE);
                    checkListTVID.setVisibility(View.GONE);
                    checkListTVID.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BankerCheckListDTO>> call, Throwable t) {
                checkListPB.setVisibility(View.GONE);
                checkListRCID.setVisibility(View.GONE);
                checkListTVID.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadPersonalDetails() {
        personalPB.setVisibility(View.VISIBLE);
        personalViewLLID.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PersonalDetailsOBJ> call = apiInterface.personalDetailsApi(customerIDStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<PersonalDetailsOBJ>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<PersonalDetailsOBJ> call, Response<PersonalDetailsOBJ> response) {
                personalPB.setVisibility(View.GONE);
                personalViewLLID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    PersonalDetailsOBJ detailsOBJ = response.body();
                    if (detailsOBJ.getStatus().equals("1")) {

                        customerNameTVID.setText(detailsOBJ.getCustomer_name());
                        customerMobileTVID.setText(detailsOBJ.getContact_number());
                        customerAlternateNumberTVID.setText(MySharedPreferences.getPreferences(FragmentPurchaseDetails.this, "" + AppConstants.ALTERNATE_CONTACT_NUMBER));
                        customerFatherNameTVID.setText(detailsOBJ.getFather_name());
                        nomineeNameTVID.setText(detailsOBJ.getNominee_name());
                        customerEmailTVID.setText(detailsOBJ.getEmail_id());
                        relationTVID.setText(detailsOBJ.getRelation_with_applicant());
                        spouseNameTVID.setText(detailsOBJ.getSpouse_name());
                        addressTVID.setText(detailsOBJ.getAddress_for_communication());
                        perAddressTVID.setText(detailsOBJ.getPermanent_address());

                        projectNameTVID.setText(detailsOBJ.getProject_name());
                        plotNumberTVID.setText(detailsOBJ.getPlot_number());
                        sqydTVID.setText(detailsOBJ.getPlot_size());
                        totalAmountTVID.setText(detailsOBJ.getTotal_sale_amount());


                        aNameTVID.setText(detailsOBJ.getCustomer_name());
                        aNomineeNameTVID.setText(detailsOBJ.getNominee_name());
                        aSpouseTVID.setText(detailsOBJ.getSpouse_name());
                        aAlternateTVID.setText(detailsOBJ.getAddress_for_communication());
                        aRelationTVID.setText(MySharedPreferences.getPreferences(FragmentPurchaseDetails.this, "" + AppConstants.ALTERNATE_CONTACT_NUMBER));

                        aAddressTVID.setText(detailsOBJ.getAddress_for_communication());
                        aPAddressTVID.setText(detailsOBJ.getPermanent_address());
                        aContactTVID.setText(detailsOBJ.getContact_number());
                        aEmailTVID.setText(detailsOBJ.getEmail_id());

                        familyStr = detailsOBJ.getFamily_photo();
                        passportStr = detailsOBJ.getPassport_size_photo();
                        idStr = detailsOBJ.getId_proof();
                        addressStr = detailsOBJ.getAddress_proof();
                        pdcStr = detailsOBJ.getPdc_cheques();
                        formStr = detailsOBJ.getForm_32A();
                        requisitionStr = detailsOBJ.getRequesition_form();


                        customerID = detailsOBJ.getCustomer_id();
                        soldBY = detailsOBJ.getSold_by();

                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.FAMILY_PHOTO, "" + detailsOBJ.getFamily_photo());
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.PASSPORT_PHOTO, "" + detailsOBJ.getPassport_size_photo());
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.ADDRESS_PROOF, "" + detailsOBJ.getAddress_proof());
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.PDC_PHOTO, "" + detailsOBJ.getPdc_cheques());
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.FORM_32A_PHOTO, "" + detailsOBJ.getForm_32A());
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.REQUISITION_FORM, "" + detailsOBJ.getRequesition_form());
                        MySharedPreferences.setPreference(FragmentPurchaseDetails.this, "" + AppConstants.ID_PROOF, "" + detailsOBJ.getId_proof());


                        if (familyStr.equals("")) {
                            familyIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            familyIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }

                        if (passportStr.equals("")) {
                            passportIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            passportIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }

                        if (idStr.equals("")) {
                            idIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            idIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }

                        if (addressStr.equals("")) {
                            addressIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            addressIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }

                        if (pdcStr.equals("")) {
                            pdcIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            pdcIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }

                        if (formStr.equals("")) {
                            formIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            formIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }

                        if (requisitionStr.equals("")) {
                            requisitionIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        } else {
                            requisitionIMVID.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }


                        linkStr = detailsOBJ.getLink_documents();
                        // saleStr = detailsOBJ.getSale_deed();
                        // agreementStr = detailsOBJ.getAgreement_of_sale();
                        saleStr = detailsOBJ.getRegistration_files();
                        agreementStr = detailsOBJ.getPending_docs();
                        if (!linkStr.equals("")) {
                            linkTVID.setText(linkStr);
                        } else {
                            linkTVID.setText("Link Not Available");
                        }

                        if (!saleStr.equals("")) {
                            saleDeedTVID.setText(saleStr);
                        } else {
                            saleDeedTVID.setText("File Not Available");
                        }
                        if (!agreementStr.equals("")) {
                            agreementTVID.setText(agreementStr);
                        } else {
                            agreementTVID.setText("File Not Available");
                        }

                    } else {
                        personalViewLLID.setVisibility(View.VISIBLE);
                        Utilities.customMessage(mainViewRLID, FragmentPurchaseDetails.this, "Details not loading");
                    }
                } else {
                    personalViewLLID.setVisibility(View.VISIBLE);
                    Utilities.customMessage(mainViewRLID, FragmentPurchaseDetails.this, "Details not loading");
                }
            }

            @Override
            public void onFailure(Call<PersonalDetailsOBJ> call, Throwable t) {
                personalPB.setVisibility(View.GONE);
                personalViewLLID.setVisibility(View.VISIBLE);
                Utilities.customMessage(mainViewRLID, FragmentPurchaseDetails.this, "Details not loading");
            }
        });
    }

    private void loadMileStoneAPI(final String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DueResponse>> call = apiInterface.scheduledApi(customerIDStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DueResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DueResponse>> call, Response<ArrayList<DueResponse>> response) {
                paidRCID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<DueResponse> dueResponses = response.body();
                    if (dueResponses.size() > 0) {
                        DueTransactionAdapter transactionAdapter = new DueTransactionAdapter(FragmentPurchaseDetails.this, dueResponses, "1");
                        paidRCID.setAdapter(transactionAdapter);
                        loadDiscountGiven(customerIDStr);
                        mileStoneTotal(dueResponses);
                    } else {
                        paidRCID.setVisibility(View.GONE);
                    }
                } else {
                    paidRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DueResponse>> call, Throwable t) {
                paidRCID.setVisibility(View.GONE);
            }
        });
    }


/*
    private void loadMileStoneAPI(final String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DueResponse>> call = apiInterface.scheduledApi(customerIDStr);
        call.enqueue(new Callback<ArrayList<DueResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DueResponse>> call, Response<ArrayList<DueResponse>> response) {
                paidRCID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<DueResponse> dueResponses = response.body();
                    if (dueResponses.size() > 0) {
                        DueTransactionAdapter transactionAdapter = new DueTransactionAdapter(FragmentPurchaseDetails.this, dueResponses, "1");
                        paidRCID.setAdapter(transactionAdapter);
                        loadDiscountGiven(customerIDStr);
                        mileStoneTotal(dueResponses);
                    } else {
                        paidRCID.setVisibility(View.GONE);
                    }
                } else {
                    paidRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DueResponse>> call, Throwable t) {
                paidRCID.setVisibility(View.GONE);
            }
        });
    }
*/

    @SuppressLint("DefaultLocale")
    private void mileStoneTotal(ArrayList<DueResponse> dueResponses) {
        float milsStoneTotal = 0.00f;
        float mileStoneTotalAmount = 0.00f;
        for (int i = 0; i < dueResponses.size(); i++) {
            mileStoneTotalAmount = Float.valueOf(dueResponses.get(i).getMilestone_amount());
            milsStoneTotal = milsStoneTotal + mileStoneTotalAmount;
            String.format("%.2f", milsStoneTotal);

            finalMileStoneAmount = milsStoneTotal;
            Log.d("FINAL_AMOUNT_DISPLAY", "\u20B9 " + finalMileStoneAmount);
        }
    }

    private void loadDiscountGiven(final String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<_NewTrasactionDiscountResponse>> call = apiInterface.discountGivenApi(customerIDStr);
        call.enqueue(new Callback<ArrayList<_NewTrasactionDiscountResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<_NewTrasactionDiscountResponse>> call, Response<ArrayList<_NewTrasactionDiscountResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    ArrayList<_NewTrasactionDiscountResponse> dueResponses = response.body();
                    if (dueResponses.size() > 0) {
                        DiscountGivenAdapter transactionAdapter = new DiscountGivenAdapter(FragmentPurchaseDetails.this, dueResponses);
                        receivedRCID.setAdapter(transactionAdapter);
                        loadDiscountValue(dueResponses);

                        loadPaid(customerIDStr);
                    } else {
                        receivedRCID.setVisibility(View.GONE);
                    }
                } else {
                    receivedRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<_NewTrasactionDiscountResponse>> call, Throwable t) {
                receivedRCID.setVisibility(View.GONE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadDiscountValue(ArrayList<_NewTrasactionDiscountResponse> dueResponses) {
        float milsStoneTotal = 0.00f;
        float mileStoneTotalAmount = 0.00f;
        for (int i = 0; i < dueResponses.size(); i++) {
            mileStoneTotalAmount = Float.valueOf(dueResponses.get(i).getDiscount_amount());
            milsStoneTotal = milsStoneTotal + mileStoneTotalAmount;
            String.format("%.2f", milsStoneTotal);

            finalDiscountAmount = milsStoneTotal;
            Log.d("FINAL_AMOUNT_DISPLAY", "\u20B9 " + finalDiscountAmount);

            globalScheduledAmount = finalMileStoneAmount - finalDiscountAmount;

            scheduledFinalAMountTVID.setText("\u20b9" + String.valueOf(globalScheduledAmount));

        }
    }

    private void loadPaid(String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<_NewTransactionPaidResponse>> call = apiInterface.paidApi(customerIDStr);
        call.enqueue(new Callback<ArrayList<_NewTransactionPaidResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<_NewTransactionPaidResponse>> call, Response<ArrayList<_NewTransactionPaidResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    ArrayList<_NewTransactionPaidResponse> dueResponses = response.body();
                    if (dueResponses.size() > 0) {
                        PaidAdapter transactionAdapter = new PaidAdapter(FragmentPurchaseDetails.this, dueResponses);
                        paidNewRCID.setAdapter(transactionAdapter);
                        paidTotal(dueResponses);
                    } else {
                        paidNewRCID.setVisibility(View.GONE);
                    }
                } else {
                    paidNewRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<_NewTransactionPaidResponse>> call, Throwable t) {
                paidNewRCID.setVisibility(View.GONE);
            }
        });
    }

    private void paidTotal(ArrayList<_NewTransactionPaidResponse> dueResponses) {
        float milsStoneTotal = 0.00f;
        float mileStoneTotalAmount = 0.00f;
        for (int i = 0; i < dueResponses.size(); i++) {
            mileStoneTotalAmount = Float.valueOf(dueResponses.get(i).getMilestone_amount());
            milsStoneTotal = milsStoneTotal + mileStoneTotalAmount;
            String.format("%.2f", milsStoneTotal);

            finalPaidAmount = milsStoneTotal;
            Log.d("FINAL_AMOUNT_DISPLAY", "\u20B9 " + finalPaidAmount);
            paidFinalAMountTVID.setText(String.valueOf(finalPaidAmount));

            globalTotalAmount = finalMileStoneAmount - finalDiscountAmount - finalPaidAmount;

            finalTotalAmountTVID.setText("\u20B9 " +String.valueOf(globalTotalAmount));
        }
    }

    private void loadRegistrationDelayCharges(final String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<_NewRegistrationDelayChargesResponse>> call = apiInterface.registrationDelayApi(customerIDStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<_NewRegistrationDelayChargesResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<_NewRegistrationDelayChargesResponse>> call, Response<ArrayList<_NewRegistrationDelayChargesResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    ArrayList<_NewRegistrationDelayChargesResponse> dueResponses = response.body();
                    if (dueResponses.size() > 0) {
                        RegistrationDelayAdapter transactionAdapter = new RegistrationDelayAdapter(FragmentPurchaseDetails.this, dueResponses);
                        registrationDelayCID.setAdapter(transactionAdapter);
                        loadRegistrationPaymentDetailCharges(customerIDStr);
                        registrationDelayCharges(dueResponses);
                    } else {
                        paidNewRCID.setVisibility(View.GONE);
                    }
                } else {
                    paidNewRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<_NewRegistrationDelayChargesResponse>> call, Throwable t) {
                paidNewRCID.setVisibility(View.GONE);
            }
        });
    }

    private void registrationDelayCharges(ArrayList<_NewRegistrationDelayChargesResponse> dueResponses) {

        float milsStoneTotal = 0.00f;
        float mileStoneTotalAmount = 0.00f;
        for (int i = 0; i < dueResponses.size(); i++) {
            mileStoneTotalAmount = Float.valueOf(dueResponses.get(i).getDelay_amount());
            milsStoneTotal = milsStoneTotal + mileStoneTotalAmount;
            String.format("%.2f", milsStoneTotal);

            finalRgistratiionDelayAmount = milsStoneTotal;
            Log.d("FINAL_AMOUNT_DISPLAY", "\u20B9 " + finalMileStoneAmount);
            regDelayFinalAMountTVID.setText("\u20B9 " + String.valueOf(finalRgistratiionDelayAmount));
        }

    }

    private void loadRegistrationPaymentDetailCharges(String customerIDStr) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<_NewRegistrationPaymentDetailsResponse>> call = apiInterface.registrationPaidApi(customerIDStr);
        call.enqueue(new Callback<ArrayList<_NewRegistrationPaymentDetailsResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<_NewRegistrationPaymentDetailsResponse>> call, Response<ArrayList<_NewRegistrationPaymentDetailsResponse>> response) {
                if (response.body() != null && response.code() == 200) {
                    ArrayList<_NewRegistrationPaymentDetailsResponse> dueResponses = response.body();
                    if (dueResponses.size() > 0) {
                        RegistrationPaymentsAdapter transactionAdapter = new RegistrationPaymentsAdapter(FragmentPurchaseDetails.this, dueResponses);
                        registrationPaidCID.setAdapter(transactionAdapter);
                        registrationPaidAmount(dueResponses);
                    } else {
                        paidNewRCID.setVisibility(View.GONE);
                    }
                } else {
                    paidNewRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<_NewRegistrationPaymentDetailsResponse>> call, Throwable t) {
                paidNewRCID.setVisibility(View.GONE);
            }
        });
    }

    private void registrationPaidAmount(ArrayList<_NewRegistrationPaymentDetailsResponse> dueResponses) {

        float milsStoneTotal = 0.00f;
        float mileStoneTotalAmount = 0.00f;
        for (int i = 0; i < dueResponses.size(); i++) {
            mileStoneTotalAmount = Float.valueOf(dueResponses.get(i).getReceived_amount());
            milsStoneTotal = milsStoneTotal + mileStoneTotalAmount;
            String.format("%.2f", milsStoneTotal);

            finalRgistratiionPaidAmount = milsStoneTotal;
            Log.d("FINAL_AMOUNT_DISPLAY", "\u20B9 " + finalRgistratiionPaidAmount);

            regPayFinalAMountTVID.setText("\u20B9 " + String.valueOf(finalRgistratiionPaidAmount));

            globalRgistrationPaidAmount = finalRgistratiionDelayAmount - finalRgistratiionPaidAmount;

            regFinalAMountTVID.setText("\u20B9 " + String.valueOf(globalRgistrationPaidAmount));

        }

    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.familyLLID:
                Intent intent = new Intent(FragmentPurchaseDetails.this, ViewImageActivity.class);
                intent.putExtra("TITLE", "Family Photo Graph");
                intent.putExtra("PIC_URL", "" + familyStr);
                intent.putExtra("PIC_TYPE", "1");
                intent.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent);
                break;
            case R.id.personalLLID:
                Intent intent2 = new Intent(FragmentPurchaseDetails.this, ViewImageActivity.class);
                intent2.putExtra("TITLE", "Passport Photo");
                intent2.putExtra("PIC_URL", "" + passportStr);
                intent2.putExtra("PIC_TYPE", "2");
                intent2.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent2);
                break;
            case R.id.idLLID:
                Intent intent3 = new Intent(FragmentPurchaseDetails.this, ViewPanCardImage.class);
                intent3.putExtra("TITLE", "ID Proof(PAN)");
                intent3.putExtra("PIC_URL", "" + idStr);
                intent3.putExtra("PIC_TYPE", "3");
                intent3.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent3);
                break;
            case R.id.addressLLID:
                Intent intent4 = new Intent(FragmentPurchaseDetails.this, ViewAadharCardImage.class);
                intent4.putExtra("TITLE", "Address Proof (Aadhar)");
                intent4.putExtra("PIC_URL", "" + addressStr);
                intent4.putExtra("PIC_TYPE", "4");
                intent4.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent4);
                break;
            case R.id.pdcLLID:
                Intent intent5 = new Intent(FragmentPurchaseDetails.this, ViewImageActivity.class);
                intent5.putExtra("TITLE", "PDC Photo");
                intent5.putExtra("PIC_URL", "" + pdcStr);
                intent5.putExtra("PIC_TYPE", "5");
                intent5.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent5);
                break;
            case R.id.formLLID:
                Intent intent6 = new Intent(FragmentPurchaseDetails.this, ViewImageActivity.class);
                intent6.putExtra("TITLE", "Form 32A");
                intent6.putExtra("PIC_URL", "" + formStr);
                intent6.putExtra("PIC_TYPE", "6");
                intent6.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent6);
                break;
            case R.id.requisitionLLID:
                Intent intent7 = new Intent(FragmentPurchaseDetails.this, ViewImageActivity.class);
                intent7.putExtra("TITLE", "Requisition Form");
                intent7.putExtra("PIC_URL", "" + requisitionStr);
                intent7.putExtra("PIC_TYPE", "7");
                intent7.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent7);
                break;

            case R.id.referCustomerBtn:

                showReferCustomerAlert();

                break;

            case R.id.agreementLLID:
                if (!agreementStr.equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(agreementStr));
                    startActivity(browserIntent);
                } else {
                    Utilities.customMessage(mainViewRLID, Objects.requireNonNull(FragmentPurchaseDetails.this), "Agreement not found");
                }
                break;

            case R.id.linkTVID:
                if (!linkStr.equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkStr));
                    startActivity(browserIntent);
                } else {
                    Utilities.customMessage(mainViewRLID, Objects.requireNonNull(FragmentPurchaseDetails.this), "Link not available");
                }
                break;

            case R.id.saleDeedTVID:
                if (!saleStr.equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(saleStr));
                    startActivity(browserIntent);
                } else {
                    Utilities.customMessage(mainViewRLID, Objects.requireNonNull(FragmentPurchaseDetails.this), "Sale file not available");
                }
                break;
        }
    }

    private void showReferCustomerAlert() {

        Dialog dialog = new Dialog(FragmentPurchaseDetails.this);
        dialog.setContentView(R.layout.refer_customer_alert);
        int height2 = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width2 = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width2, height2);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Spinner projectsSPID;
        AppCompatEditText customerNameETID;
        AppCompatEditText mobileETID;
        AppCompatEditText emailETID;
        AppCompatButton submitBtn;

        projectsSPID = dialog.findViewById(R.id.projectsSPID);
        customerNameETID = dialog.findViewById(R.id.customerNameETID);
        mobileETID = dialog.findViewById(R.id.mobileETID);
        emailETID = dialog.findViewById(R.id.emailETID);
        submitBtn = dialog.findViewById(R.id.submitBtn);


        callProjects(projectsSPID);


        submitBtn.setOnClickListener(v -> {

            referCustomerValidations(customerNameETID,mobileETID,emailETID,dialog);

        });
    }

    private void referCustomerValidations(AppCompatEditText customerNameETID, AppCompatEditText mobileETID, AppCompatEditText emailETID, Dialog dialog) {

        String customerName = customerNameETID.getText().toString();
        String mobileStr = mobileETID.getText().toString();
        String emailStr = emailETID.getText().toString();

        if (TextUtils.isEmpty(customerName)){
            Toast.makeText(this, "Please enter customer name", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(mobileStr)){
            Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            return;
        }/*else if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (Utilities.isNetworkAvailable(this)){
            callReferCustomer(customerName,mobileStr,emailStr,dialog);
        }
    }


    private void callProjects(Spinner projectsSPID) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProjectsResponse>> call = apiInterface.getProjects();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<ProjectsResponse>>() {

            @Override
            public void onResponse(Call<ArrayList<ProjectsResponse>> call, Response<ArrayList<ProjectsResponse>> response) {

                if (response.body() != null && response.code() == 200) {

                    projectsResponses = response.body();

                    if (projectsResponses != null && projectsResponses.size() > 0) {
                        for (int i = 0; i < projectsResponses.size(); i++) {
                            ProjectsPurchaseAdapter adapter = new ProjectsPurchaseAdapter(FragmentPurchaseDetails.this, R.layout.custom_spinner_view, projectsResponses);
                            projectsSPID.setAdapter(adapter);
                        }

                        projectsSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                projectID = projectsResponses.get(i).getProject_id();

                                Log.e("projectID==>", "" + projectID);

                                if (project == 1) {
                                    project++;
                                    for (int j = 0; j < projectsResponses.size(); j++) {

                                        projectsSPID.setSelection(j);

                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        Toast.makeText(FragmentPurchaseDetails.this, "Details not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FragmentPurchaseDetails.this, "Details not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectsResponse>> call, Throwable t) {

                Toast.makeText(FragmentPurchaseDetails.this, "Details not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callReferCustomer(String customerName, String mobileStr, String emailStr, Dialog dialog) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponseNew>> call = apiInterface.getReferCustomer(customerName,mobileStr,emailStr,projectID,customerID,soldBY);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<StatusResponseNew>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseNew>> call, Response<ArrayList<StatusResponseNew>> response) {
                if (response.body() != null && response.code() == 200){
                    ArrayList<StatusResponseNew> statusResponse = new ArrayList<>();
                    statusResponse = response.body();

                    if (statusResponse.get(0).getStatus().equalsIgnoreCase("Inserted")){
                        Toast.makeText(FragmentPurchaseDetails.this, "Success", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FragmentPurchaseDetails.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(FragmentPurchaseDetails.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseNew>> call, Throwable t) {
                Toast.makeText(FragmentPurchaseDetails.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();

    }


    @Override
    public void onResume() {
        super.onResume();
        String pageRefresh = MySharedPreferences.getPreferences(FragmentPurchaseDetails.this, "" + AppConstants.REFRESH_PAGE);
        if (pageRefresh.equalsIgnoreCase("YES")) {
            loadBankerCheckList(customerIDStr);
        }
    }

    public class DueTransactionAdapter extends RecyclerView.Adapter<DueTransactionAdapter.DueTransactionVH> {
        private ArrayList<DueResponse> dueResponses = new ArrayList<>();
        private Activity activity;
        private String type;

        private String[] colors = {"#F5F5F5", "#ffffff"};


        public DueTransactionAdapter(Activity activity, ArrayList<DueResponse> dueResponses, String type) {
            this.activity = activity;
            this.dueResponses = dueResponses;
            this.type = type;
        }

        @NonNull
        @Override
        public DueTransactionAdapter.DueTransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DueTransactionAdapter.DueTransactionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.due_main_list_item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull DueTransactionAdapter.DueTransactionVH holder, @SuppressLint("RecyclerView") int position) {
            if (type.equals("1")) {
                holder.scheduleDateTVID.setVisibility(View.VISIBLE);
                holder.scheduleAmountTVID.setVisibility(View.VISIBLE);
                holder.paidDateTVID.setVisibility(View.GONE);
                holder.paidAmountTVID.setVisibility(View.GONE);
            } else {
                holder.scheduleDateTVID.setVisibility(View.GONE);
                holder.scheduleAmountTVID.setVisibility(View.GONE);
                holder.paidDateTVID.setVisibility(View.VISIBLE);
                holder.paidAmountTVID.setVisibility(View.VISIBLE);
            }

            holder.particularTVID.setText(dueResponses.get(position).getMilestone_title());
            holder.scheduleDateTVID.setText(dueResponses.get(position).getDaysfrom_booking_date());
            holder.scheduleAmountTVID.setText("\u20B9" + dueResponses.get(position).getMilestone_amount());


            holder.paidDateTVID.setText(dueResponses.get(position).getSchedule_date());
            holder.paidAmountTVID.setText("\u20B9" + dueResponses.get(position).getMilestone_amount());
            holder.mainLLID.setBackgroundColor(Color.parseColor(colors[position % 2]));

            holder.mainLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity, ViewGallery.class);
                    intent.putExtra("Title",dueResponses.get(position).getMilestone_title());
                    intent.putExtra("Payment_ID",dueResponses.get(position).getPayment_id());
                    activity.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return dueResponses.size();
        }

        public class DueTransactionVH extends RecyclerView.ViewHolder {

            AppCompatTextView particularTVID;
            AppCompatTextView scheduleDateTVID;
            AppCompatTextView scheduleAmountTVID;
            AppCompatTextView paidDateTVID;
            AppCompatTextView paidAmountTVID;
            LinearLayout mainLLID;

            public DueTransactionVH(@NonNull View itemView) {
                super(itemView);
                particularTVID = itemView.findViewById(R.id.particularTVID);
                scheduleDateTVID = itemView.findViewById(R.id.scheduleDateTVID);
                scheduleAmountTVID = itemView.findViewById(R.id.scheduleAmountTVID);
                paidDateTVID = itemView.findViewById(R.id.paidDateTVID);
                paidAmountTVID = itemView.findViewById(R.id.paidAmountTVID);
                mainLLID = itemView.findViewById(R.id.mainLLID);
            }
        }
    }

    public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckListVH> {
        private ArrayList<BankerCheckListDTO> bankerCheckListDTOArrayList = new ArrayList<>();
        private Activity activity;

        public CheckListAdapter(Activity activity, ArrayList<BankerCheckListDTO> bankerCheckListDTOArrayList) {
            this.activity = activity;
            this.bankerCheckListDTOArrayList = bankerCheckListDTOArrayList;
        }

        @NonNull
        @Override
        public CheckListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CheckListVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.banker_check_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final CheckListVH holder, @SuppressLint("RecyclerView") final int position) {
            holder.checkListTVID.setText(bankerCheckListDTOArrayList.get(position).getPerticular());
            String documentStr = bankerCheckListDTOArrayList.get(position).getDocument();

            if (documentStr.equals("")) {
                holder.uploadLLID.setVisibility(View.VISIBLE);
                holder.successLLID.setVisibility(View.GONE);
            } else {
                holder.uploadLLID.setVisibility(View.GONE);
                holder.successLLID.setVisibility(View.VISIBLE);
            }
            bankerCheckListID = bankerCheckListDTOArrayList.get(position).getChecklist_id();


            holder.uploadLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewImageActivity.class);
                    intent.putExtra("TITLE", "" + bankerCheckListDTOArrayList.get(position).getPerticular());
                    intent.putExtra("PIC_URL", "" + bankerCheckListDTOArrayList.get(position).getDocument());
                    intent.putExtra("PIC_TYPE", "" + bankerCheckListDTOArrayList.get(position).getChecklist_id());
                    intent.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_BANKER);
                    startActivity(intent);
                }
            });

            holder.successLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewImageActivity.class);
                    intent.putExtra("TITLE", "" + bankerCheckListDTOArrayList.get(position).getPerticular());
                    intent.putExtra("PIC_URL", "" + bankerCheckListDTOArrayList.get(position).getDocument());
                    intent.putExtra("PIC_TYPE", "" + bankerCheckListDTOArrayList.get(position).getChecklist_id());
                    intent.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_BANKER);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return bankerCheckListDTOArrayList.size();
        }

        public class CheckListVH extends RecyclerView.ViewHolder {

            LinearLayout uploadLLID, successLLID;
            AppCompatTextView checkListTVID;
            ProgressBar uploadPB;

            public CheckListVH(@NonNull View itemView) {
                super(itemView);
                uploadLLID = itemView.findViewById(R.id.uploadLLID);
                successLLID = itemView.findViewById(R.id.successLLID);
                checkListTVID = itemView.findViewById(R.id.checkListTVID);
                uploadPB = itemView.findViewById(R.id.uploadPB);
            }
        }
    }
}
