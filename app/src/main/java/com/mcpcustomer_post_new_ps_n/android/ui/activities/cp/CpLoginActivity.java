package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.mcpcustomer_post_new_ps_n.android.ui.activities.MainActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.LoginResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CpLoginActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatEditText customerETID,passwordETID;
 //   TextInputEditText passwordETID;
    AppCompatButton loginBtn;
    RelativeLayout mainViewRLID;
    KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_login_form);
        Utilities.startAnimation(this);
        mainViewRLID = findViewById(R.id.mainViewRLID);
        customerETID = findViewById(R.id.customerETID);
        passwordETID = findViewById(R.id.passwordETID);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
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
        }
    }

    private void validations() {
        String customerStr, passwordStr;
        customerStr = customerETID.getText().toString();
        passwordStr = passwordETID.getText().toString();

        if (TextUtils.isEmpty(customerStr)) {
            Utilities.customMessage(mainViewRLID, this, "Please enter email");
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            Utilities.customMessage(mainViewRLID, this, "Please enter password");
            return;
        }

        callLoginApi(customerStr, passwordStr);
    }

    private void callLoginApi(String customerStr, final String passwordStr) {
        showProgress();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<LoginResponse>> call = apiInterface.loginApi(customerStr, passwordStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<LoginResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<LoginResponse>> call, Response<ArrayList<LoginResponse>> response) {
                {
                    dismissProgress();
                    if (response.body() != null && response.code() == 200) {
                        ArrayList<LoginResponse> loginResponse = response.body();

                        for (int i = 0; i < loginResponse.size(); i++) {
                            if (loginResponse.get(i).getStatus() == 1) {
                                //Utilities.customMessage(mainViewRLID, LoginActivity.this, "Login Successfully" );
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_ID, "" + loginResponse.get(i).getCp_id());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_TYPE, "" + loginResponse.get(i).getCare_taker());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_NAME, "" + loginResponse.get(i).getUser_name());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.designation_name, "" + loginResponse.get(i).getDesignation_name());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.agent_unique_id, "" + loginResponse.get(i).getAgent_unique_id());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_EMAIL, "" + loginResponse.get(i).getUser_email());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_NUMBER, "" + loginResponse.get(i).getMobile());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_PROFILE_PIC, "" + loginResponse.get(i).getUpload_pic());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_PROFILE_PIC_N, "" + loginResponse.get(i).getUpload_pic());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_DESIGNATION, "" + loginResponse.get(i).getDesignation());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.TRAVEL_ELIGIBILITY, "" + loginResponse.get(i).getTravel_eligible());
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_PASSWORD, "" + passwordStr);
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_LOGIN_STATUS, "10");
                                //MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.LOGIN_STATUS, "1");

                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_VIEW, "YES");

                                MySharedPreferences.setPreference(CpLoginActivity.this, "ISAGENTLOGIN" , "true");
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_USER_EMAIL, "" + customerStr);
                                MySharedPreferences.setPreference(CpLoginActivity.this, "" + AppConstants.CP_PASSWORD, "" + passwordStr);

                                startActivity(new Intent(CpLoginActivity.this, MainActivity.class));
                                Utilities.finishAnimation(CpLoginActivity.this);
                            } else {
                                Utilities.customMessage(mainViewRLID, CpLoginActivity.this, "Invalid credentials");
                            }
                        }

                    } else {
                        Utilities.customMessage(mainViewRLID, CpLoginActivity.this, getString(R.string.error_at));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoginResponse>> call, Throwable t) {
                dismissProgress();
                Utilities.customMessage(mainViewRLID, CpLoginActivity.this, getString(R.string.error_at));


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
