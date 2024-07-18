package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.MainActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CpLoginActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.RegistrationResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText nameETID, emailETID, mobileETID, passwordETID;
    AppCompatButton signUpBtn;
    LinearLayout loginLLID,cpLoginLLID;
    RelativeLayout mainViewRLID;
    KProgressHUD kProgressHUD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Utilities.startAnimation(this);
        mainViewRLID = findViewById(R.id.mainViewRLID);
        nameETID = findViewById(R.id.nameETID);
        emailETID = findViewById(R.id.emailETID);
        cpLoginLLID = findViewById(R.id.cpLoginLLID);
        mobileETID = findViewById(R.id.mobileETID);
        passwordETID = findViewById(R.id.passwordETID);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginLLID = findViewById(R.id.loginLLID);

        signUpBtn.setOnClickListener(this);
        loginLLID.setOnClickListener(this);
        cpLoginLLID.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                if (Utilities.isNetworkAvailable(this)) {
                    validations();
                } else {
                    Utilities.customMessage(mainViewRLID, this, getString(R.string.no_internet));
                }

                break;
            case R.id.loginLLID:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.cpLoginLLID:
                startActivity(new Intent(this, CpLoginActivity.class));
                break;

        }
    }

    private void validations() {
        String nameStr, emailStr, mobileStr, passwordStr;
        nameStr = nameETID.getText().toString();
        emailStr = emailETID.getText().toString();
        mobileStr = mobileETID.getText().toString();
        passwordStr = passwordETID.getText().toString();

        if (TextUtils.isEmpty(nameStr)) {
            Utilities.customMessage(mainViewRLID, this, "Enter name");
            return;
        }

        if (TextUtils.isEmpty(emailStr)) {
            Utilities.customMessage(mainViewRLID, this, "Enter email");
            return;
        }

        if (TextUtils.isEmpty(mobileStr)) {
            Utilities.customMessage(mainViewRLID, this, "Enter mobile number");
            return;
        }

        if (mobileStr.length() <= 9) {
            Utilities.customMessage(mainViewRLID, this, "Enter valid mobile number");
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            Utilities.customMessage(mainViewRLID, this, "Enter password");
            return;
        }

        callRegistrationApi(nameStr, emailStr, mobileStr, passwordStr);
    }

    private void callRegistrationApi(String nameStr, final String emailStr, String mobileStr, final String passwordStr) {
        showProgress();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegistrationResponse> call = apiInterface.registerApi(nameStr, emailStr, mobileStr, passwordStr, "852");
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                dismissProgress();
                if (response.body() != null && response.code() == 200) {
                    RegistrationResponse registrationResponse = response.body();
                    if (registrationResponse.getStatus() == 1) {

                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.USER_ID, "" + registrationResponse.getUser_id());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_ID, "" + registrationResponse.getCustomer_id());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_TYPE, "" + registrationResponse.getCustomer_type());

                        if (registrationResponse.getCustomer_type().equals("0")) {
                            MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_NAME, "" + registrationResponse.getUser_name());
                            MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CONTACT_NUMBER, "" + registrationResponse.getUser_mobile());
                            MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.EMAIL_ID, "" + registrationResponse.getUser_email());
                        } else {
                            MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_NAME, "" + registrationResponse.getCustomer_name());
                            MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CONTACT_NUMBER, "" + registrationResponse.getContact_number());
                            MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.EMAIL_ID, "" + registrationResponse.getUser_email());
                        }

                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_PROJECT_NAME, "" + registrationResponse.getProject_name());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_PROJECT_ID, "" + registrationResponse.getProject_id());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_PLOT_NUMBER, "" + registrationResponse.getPlot_number());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.CUSTOMER_APPLICATION_STATUS, "" + registrationResponse.getApplication_status());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.FATHER_NAME, "" + registrationResponse.getFather_name());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.NOMINEE_NAME, "" + registrationResponse.getNominee_name());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.SPOUSE_NAME, "" + registrationResponse.getSpouse_name());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.ALTERNATE_CONTACT_NUMBER, "" + registrationResponse.getAlternative_contact_number());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.RELATIONSHIP_WITH, "" + registrationResponse.getRelation_with_applicant());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.ADDRESS, "" + registrationResponse.getAddress_for_communication());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.PERMANENT_ADDRESS, "" + registrationResponse.getPermanent_address());


                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.FAMILY_PHOTO, "0" );
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.PASSPORT_PHOTO, "" + registrationResponse.getPassport_size_photo());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.ADDRESS_PROOF, "" + registrationResponse.getAddress_proof());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.PDC_PHOTO, "" + registrationResponse.getPdc_cheques());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.FORM_32A_PHOTO, "" + registrationResponse.getForm_32A());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.REQUISITION_FORM, "" + registrationResponse.getRequesition_form());
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.ID_PROOF, "" + registrationResponse.getId_proof());


                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.LOGIN_EMAIL, "" + emailStr);
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.LOGIN_PASSWORD, "" + passwordStr);
                        MySharedPreferences.setPreference(RegistrationActivity.this, "" + AppConstants.LOGIN_STATUS, "1");
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        Utilities.finishAnimation(RegistrationActivity.this);

                    } else {
                        Utilities.customMessage(mainViewRLID, RegistrationActivity.this, "" + registrationResponse.getMsg());
                    }
                } else {
                    Utilities.customMessage(mainViewRLID, RegistrationActivity.this, "Something went Wrong");
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                dismissProgress();
                Utilities.customMessage(mainViewRLID, RegistrationActivity.this, "Error : " + t.getMessage());

            }
        });
    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Registration under process")
                .setDetailsLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public void dismissProgress() {
        kProgressHUD.dismiss();
    }
}
