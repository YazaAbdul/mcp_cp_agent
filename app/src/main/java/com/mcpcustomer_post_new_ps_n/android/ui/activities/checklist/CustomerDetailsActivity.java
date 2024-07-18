package com.mcpcustomer_post_new_ps_n.android.ui.activities.checklist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mcpcustomer_post_new_ps_n.android.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout mainViewLLID;
    AppCompatTextView headerTitleTVID;
    AppCompatEditText fatherNameETID, nomineeNameETID, spouseNameETID, alternativeNumberETID, relationshipETID, addressETID, permanentAddressETID;
    AppCompatTextView contactNumberETID, emailIdETID;
    RelativeLayout nextRLID;
    String projectIdStr, plotNumberStr, customerIdStr, applicationStatusStr, fatherNameStr, nomineeNameStr, spouseNameStr, alternativeNumberStr, relationshipStr, addressStr, permanentAddressStr, contactNumberStr, emailIdStr;
    KProgressHUD kProgressHUD;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        RelativeLayout headerBackRLID = findViewById(R.id.headerBackRLID);
        headerBackRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.mcpcustomer_post_new_ps_n.android.data.Utilities.finishAnimation(CustomerDetailsActivity.this);
            }
        });
        com.mcpcustomer_post_new_ps_n.android.data.Utilities.startAnimation(this);

        com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.setPreference(this,""+ com.mcpcustomer_post_new_ps_n.android.data.AppConstants.BACK_FINISH,""+ com.mcpcustomer_post_new_ps_n.android.data.AppConstants.BACK_NO);
        customerIdStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.CUSTOMER_ID);
        applicationStatusStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.CUSTOMER_APPLICATION_STATUS);

        projectIdStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.CUSTOMER_PROJECT_ID);
        plotNumberStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.CUSTOMER_PLOT_NUMBER);

        headerTitleTVID = findViewById(R.id.headerTitleTVID);
        headerTitleTVID.setText("Details");

        mainViewLLID = findViewById(R.id.mainViewLLID);
        fatherNameETID = findViewById(R.id.fatherNameETID);
        nomineeNameETID = findViewById(R.id.nomineeNameETID);
        spouseNameETID = findViewById(R.id.spouseNameETID);
        alternativeNumberETID = findViewById(R.id.alternativeNumberETID);
        relationshipETID = findViewById(R.id.relationshipETID);
        addressETID = findViewById(R.id.addressETID);
        permanentAddressETID = findViewById(R.id.permanentAddressETID);
        contactNumberETID = findViewById(R.id.contactNumberETID);
        emailIdETID = findViewById(R.id.emailIdETID);

        nextRLID = findViewById(R.id.nextRLID);
        nextRLID.setOnClickListener(this);

        if (!applicationStatusStr.equals("0")) {
            fatherNameStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.FATHER_NAME);
            nomineeNameStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.NOMINEE_NAME);
            spouseNameStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.SPOUSE_NAME);
            alternativeNumberStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.ALTERNATE_CONTACT_NUMBER);
            relationshipStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.RELATIONSHIP_WITH);
            addressStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.ADDRESS);
            permanentAddressStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.PERMANENT_ADDRESS);
            contactNumberStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.CONTACT_NUMBER);
            emailIdStr = com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.EMAIL_ID);

            fatherNameETID.setText("" + fatherNameStr);
            nomineeNameETID.setText("" + nomineeNameStr);
            spouseNameETID.setText("" + spouseNameStr);
            alternativeNumberETID.setText("" + alternativeNumberStr);
            relationshipETID.setText("" + relationshipStr);
            addressETID.setText("" + addressStr);
            permanentAddressETID.setText("" + permanentAddressStr);
            contactNumberETID.setText("" + contactNumberStr);
            emailIdETID.setText("" + emailIdStr);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        com.mcpcustomer_post_new_ps_n.android.data.Utilities.finishAnimation(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String onBack= com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.getPreferences(this,""+ com.mcpcustomer_post_new_ps_n.android.data.AppConstants.BACK_FINISH);
        if (onBack.equalsIgnoreCase(com.mcpcustomer_post_new_ps_n.android.data.AppConstants.BACK_YES)){
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.finishAnimation(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextRLID:
                if (com.mcpcustomer_post_new_ps_n.android.data.Utilities.isNetworkAvailable(this)) {
                    validations();
                } else {
                    com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "No internet connection");
                }
                break;

        }
    }

    private void validations() {
        fatherNameStr = fatherNameETID.getText().toString();
        nomineeNameStr = nomineeNameETID.getText().toString();
        spouseNameStr = spouseNameETID.getText().toString();
        alternativeNumberStr = alternativeNumberETID.getText().toString();
        relationshipStr = relationshipETID.getText().toString();
        addressStr = addressETID.getText().toString();
        permanentAddressStr = permanentAddressETID.getText().toString();
        contactNumberStr = contactNumberETID.getText().toString();
        emailIdStr = emailIdETID.getText().toString();

        if (TextUtils.isEmpty(fatherNameStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Father Name");
            return;
        }

        if (TextUtils.isEmpty(nomineeNameStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Nominee Name");
            return;
        }

        if (TextUtils.isEmpty(spouseNameStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Spouse Name");
            return;
        }

        if (TextUtils.isEmpty(alternativeNumberStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Alternate Number");
            return;
        }

        if (TextUtils.isEmpty(relationshipStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Relationship with customer");
            return;
        }

        if (TextUtils.isEmpty(addressStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Address");
            return;
        }

        if (TextUtils.isEmpty(permanentAddressStr)) {
            com.mcpcustomer_post_new_ps_n.android.data.Utilities.customMessage(mainViewLLID, this, "Enter Permanent Address");
            return;
        }

       /* if (TextUtils.isEmpty(contactNumberStr)) {
            Utilities.customMessage(mainViewLLID, this, "Enter Contact Number");
            return;
        }

        if (TextUtils.isEmpty(emailIdStr)) {
            Utilities.customMessage(mainViewLLID, this, "Enter Email ID");
            return;
        }*/

        insertCustomerDetails(customerIdStr, applicationStatusStr, fatherNameStr, nomineeNameStr, spouseNameStr, alternativeNumberStr, relationshipStr, addressStr, permanentAddressStr);

    }

    private void insertCustomerDetails(String customerIdStr, String applicationStatusStr, String fatherNameStr, String nomineeNameStr, String spouseNameStr, String alternativeNumberStr, String relationshipStr, String addressStr, String permanentAddressStr) {
        showProgress();
        com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface apiInterface = com.mcpcustomer_post_new_ps_n.android.domain.ApiClient.getClient().create(com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface.class);
        Call<com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse> call = apiInterface.submitCustomerDetailsAPi(customerIdStr, applicationStatusStr, fatherNameStr, spouseNameStr, nomineeNameStr, alternativeNumberStr, relationshipStr, addressStr, permanentAddressStr, projectIdStr, plotNumberStr);
        Log.e("api==>", call.request().toString());
        call.enqueue(new Callback<com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse>() {
            @Override
            public void onResponse(Call<com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse> call, Response<com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse> response) {
                dismissProgress();
                if (response.body() != null && response.code() == 200) {
                    com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse submitResponse = response.body();
                    if (submitResponse.getStatus() == 1) {
                        com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences.setPreference(CustomerDetailsActivity.this, "" + com.mcpcustomer_post_new_ps_n.android.data.AppConstants.CUSTOMER_APPLICATION_STATUS, "" + submitResponse.getApplication_status());
                        Intent intent = new Intent(CustomerDetailsActivity.this, com.mcpcustomer_post_new_ps_n.android.ui.activities.checklist.CustomerDocumentsActivity.class);
                        intent.putExtra("CUSTOMER_APPLICATION_STATUS", "" + submitResponse.getApplication_status());
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse> call, Throwable t) {
            }
        });

    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Updating")
                .setDetailsLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public void dismissProgress() {
        kProgressHUD.dismiss();
    }

}

