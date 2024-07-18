package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

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
import com.mcpcustomer_post_new_ps_n.android.ui.models.CustomerLoginResponse;
import com.kaopiz.kprogresshud.KProgressHUD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatEditText emailETID, passwordETID;
    AppCompatButton loginBtn;
    RelativeLayout mainViewRLID;
    KProgressHUD kProgressHUD;
    LinearLayout signUpLLID,cpLoginLLID;
    AppCompatTextView forgotTVID;
    boolean isNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utilities.startAnimation(this);

        mainViewRLID = findViewById(R.id.mainViewRLID);
        cpLoginLLID = findViewById(R.id.cpLoginLLID);
        emailETID = findViewById(R.id.emailETID);
        passwordETID = findViewById(R.id.passwordETID);
        loginBtn = findViewById(R.id.loginBtn);
        signUpLLID = findViewById(R.id.signUpLLID);
        forgotTVID = findViewById(R.id.forgotTVID);

        loginBtn.setOnClickListener(this);
        signUpLLID.setOnClickListener(this);
        forgotTVID.setOnClickListener(this);
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
            case R.id.loginBtn:
                if (Utilities.isNetworkAvailable(this)) {
                    validations();
                } else {
                    Utilities.customMessage(mainViewRLID, this, "" + getString(R.string.no_internet));
                }
                break;

            case R.id.signUpLLID:
                Utilities.finishAnimation(this);
                break;

            case R.id.forgotTVID:
                startActivity(new Intent(this, ForgotActivity.class));
                break;

            case R.id.cpLoginLLID:
                startActivity(new Intent(this, CpLoginActivity.class));
                break;
        }
    }

    private void validations() {
        String emailStr, passwordStr;
        emailStr = emailETID.getText().toString();
        passwordStr = passwordETID.getText().toString();

        //emailStr=emailStr.replace("%40","@");

        if (TextUtils.isEmpty(emailStr)) {
            Utilities.customMessage(mainViewRLID, this, "Please enter email OR mobile number");
            return;
        }

        if (emailStr.matches("[0-9]+")) {
            if (emailStr.length() <= 9) {
                Utilities.customMessage(mainViewRLID, this, "Enter valid Mobile Number");
                return;
            }
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                Utilities.customMessage(mainViewRLID, this, "Enter valid email-id");
                return;
            }
        }

        if (TextUtils.isEmpty(passwordStr)) {
            Utilities.customMessage(mainViewRLID, this, "Please enter password");
            return;
        }

        callLoginApi(emailStr, passwordStr);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void callLoginApi(final String emailStr, final String passwordStr) {
        showProgress();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerLoginResponse> call = apiInterface.customerLogin(emailStr, passwordStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<CustomerLoginResponse>() {
            @Override
            public void onResponse(Call<CustomerLoginResponse> call, Response<CustomerLoginResponse> response) {
                dismissProgress();
                if (response.body() != null && response.code() == 200) {
                    CustomerLoginResponse customerLoginResponse = response.body();
                    if (customerLoginResponse.getStatus() == 1) {
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.USER_ID, "" + customerLoginResponse.getUser_id());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_ID, "" + customerLoginResponse.getCustomer_id());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_TYPE, "" + customerLoginResponse.getCustomer_type());

                        if (customerLoginResponse.getCustomer_type().equals("0")) {
                            MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_NAME, "" + customerLoginResponse.getUser_name());
                            MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CONTACT_NUMBER, "" + customerLoginResponse.getUser_mobile());
                            MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.EMAIL_ID, "" + customerLoginResponse.getUser_email());
                        } else {
                            MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_NAME, "" + customerLoginResponse.getCustomer_name());
                            MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CONTACT_NUMBER, "" + customerLoginResponse.getContact_number());
                            MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.EMAIL_ID, "" + customerLoginResponse.getUser_email());
                        }


                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_PROJECT_NAME, "" + customerLoginResponse.getProject_name());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_PROJECT_ID, "" + customerLoginResponse.getProject_id());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_PLOT_NUMBER, "" + customerLoginResponse.getPlot_number());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.CUSTOMER_APPLICATION_STATUS, "" + customerLoginResponse.getApplication_status());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.FATHER_NAME, "" + customerLoginResponse.getFather_name());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.NOMINEE_NAME, "" + customerLoginResponse.getNominee_name());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.SPOUSE_NAME, "" + customerLoginResponse.getSpouse_name());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.ALTERNATE_CONTACT_NUMBER, "" + customerLoginResponse.getAlternative_contact_number());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.RELATIONSHIP_WITH, "" + customerLoginResponse.getRelation_with_applicant());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.ADDRESS, "" + customerLoginResponse.getAddress_for_communication());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.PERMANENT_ADDRESS, "" + customerLoginResponse.getPermanent_address());


                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.FAMILY_PHOTO, "" + customerLoginResponse.getFamily_photo());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.PASSPORT_PHOTO, "" + customerLoginResponse.getPassport_size_photo());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.ADDRESS_PROOF, "" + customerLoginResponse.getAddress_proof());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.PDC_PHOTO, "" + customerLoginResponse.getPdc_cheques());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.FORM_32A_PHOTO, "" + customerLoginResponse.getForm_32A());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.REQUISITION_FORM, "" + customerLoginResponse.getRequesition_form());
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.ID_PROOF, "" + customerLoginResponse.getId_proof());


                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.LOGIN_EMAIL, "" + emailStr);
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.LOGIN_PASSWORD, "" + passwordStr);
                        MySharedPreferences.setPreference(LoginActivity.this, "" + AppConstants.LOGIN_STATUS, "1");

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        Utilities.customMessage(mainViewRLID, LoginActivity.this, "" + customerLoginResponse.getMsg());

                    }
                } else {
                    Utilities.customMessage(mainViewRLID, LoginActivity.this, "Login Failed");

                }
            }

            @Override
            public void onFailure(Call<CustomerLoginResponse> call, Throwable t) {
                dismissProgress();
                Utilities.customMessage(mainViewRLID, LoginActivity.this, "Error : " + t.getMessage());
            }
        });

    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Login under process")
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
