package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.MainActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CpLoginActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.CustomerLoginResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.LoginResponse;
import com.kaopiz.kprogresshud.KProgressHUD;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    String emailStr, passwordStr,customerstr,cppasswordStr;
    String isagentlogin="false",userLogin,cpProfile;

    KProgressHUD kProgressHUD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Utilities.startAnimation(this);

        emailStr = MySharedPreferences.getPreferences(this, "" + AppConstants.LOGIN_EMAIL);
        passwordStr = MySharedPreferences.getPreferences(this, "" + AppConstants.LOGIN_PASSWORD);

        customerstr = MySharedPreferences.getPreferences(this, "" + AppConstants.CP_USER_EMAIL);
        cppasswordStr = MySharedPreferences.getPreferences(this,  AppConstants.CP_PASSWORD);
        userLogin = MySharedPreferences.getPreferences(this,  AppConstants.LOGIN_STATUS);

        isagentlogin=MySharedPreferences.getPreferences(this, AppConstants.CP_VIEW);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //callLoginApi(emailStr, passwordStr);
                        launchApp();
                    }
                },
                2000
        );
    }

    private void launchApp() {

        if (userLogin.equalsIgnoreCase("1")) {

            /*MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.LOGIN_STATUS, "1");
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            Utilities.finishAnimation(SplashScreenActivity.this);*/

            callLoginApi(emailStr,passwordStr);

        } else if (isagentlogin.equalsIgnoreCase("YES")){

            /*MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_VIEW, "YES");
            //MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.LOGIN_STATUS, "1");
            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_PROFILE_PIC, "" + cpProfile);
            Intent in = new Intent(SplashScreenActivity.this, MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);*/

            callCPLoginApi(customerstr,cppasswordStr);

        }else {

            startActivity(new Intent(SplashScreenActivity.this, CpLoginActivity.class));
            finish();

        }
    }

    private void callCPLoginApi(String customerStr, final String passwordStr) {
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
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_ID, "" + loginResponse.get(i).getCp_id());
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_TYPE, "" + loginResponse.get(i).getCare_taker());
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_NAME, "" + loginResponse.get(i).getUser_name());
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_EMAIL, "" + loginResponse.get(i).getUser_email());
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_NUMBER, "" + loginResponse.get(i).getMobile());
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_PROFILE_PIC, "" + loginResponse.get(i).getUpload_pic());
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_PASSWORD, "" + passwordStr);
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_LOGIN_STATUS, "10");

                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_VIEW, "YES");

                                MySharedPreferences.setPreference(SplashScreenActivity.this, "ISAGENTLOGIN" , "true");
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_USER_EMAIL, "" + customerStr);
                                MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CP_PASSWORD, "" + passwordStr);

                                //startActivity(new Intent(CpLoginActivity.this, DashBoardMainActivity.class));
                                Intent in = new Intent(SplashScreenActivity.this, MainActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                Utilities.finishAnimation(SplashScreenActivity.this);
                            } else {
                               // Utilities.customMessage(mainViewRLID, CpLoginActivity.this, "Invalid credentials");
                            }
                        }

                    } else {
                       // Utilities.customMessage(mainViewRLID, CpLoginActivity.this, getString(R.string.error_at));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoginResponse>> call, Throwable t) {
                dismissProgress();
               // Utilities.customMessage(mainViewRLID, CpLoginActivity.this, getString(R.string.error_at));


            }
        });
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
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.USER_ID, "" + customerLoginResponse.getUser_id());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_ID, "" + customerLoginResponse.getCustomer_id());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_TYPE, "" + customerLoginResponse.getCustomer_type());

                        if (customerLoginResponse.getCustomer_type().equals("0")) {
                            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_NAME, "" + customerLoginResponse.getUser_name());
                            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CONTACT_NUMBER, "" + customerLoginResponse.getUser_mobile());
                            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.EMAIL_ID, "" + customerLoginResponse.getUser_email());
                        } else {
                            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_NAME, "" + customerLoginResponse.getCustomer_name());
                            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CONTACT_NUMBER, "" + customerLoginResponse.getContact_number());
                            MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.EMAIL_ID, "" + customerLoginResponse.getUser_email());
                        }

                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_PROJECT_NAME, "" + customerLoginResponse.getProject_name());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_PROJECT_ID, "" + customerLoginResponse.getProject_id());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_PLOT_NUMBER, "" + customerLoginResponse.getPlot_number());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.CUSTOMER_APPLICATION_STATUS, "" + customerLoginResponse.getApplication_status());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.FATHER_NAME, "" + customerLoginResponse.getFather_name());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.NOMINEE_NAME, "" + customerLoginResponse.getNominee_name());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.SPOUSE_NAME, "" + customerLoginResponse.getSpouse_name());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.ALTERNATE_CONTACT_NUMBER, "" + customerLoginResponse.getAlternative_contact_number());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.RELATIONSHIP_WITH, "" + customerLoginResponse.getRelation_with_applicant());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.ADDRESS, "" + customerLoginResponse.getAddress_for_communication());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.PERMANENT_ADDRESS, "" + customerLoginResponse.getPermanent_address());

                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.FAMILY_PHOTO, "" + customerLoginResponse.getFamily_photo());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.PASSPORT_PHOTO, "" + customerLoginResponse.getPassport_size_photo());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.ADDRESS_PROOF, "" + customerLoginResponse.getAddress_proof());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.PDC_PHOTO, "" + customerLoginResponse.getPdc_cheques());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.FORM_32A_PHOTO, "" + customerLoginResponse.getForm_32A());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.REQUISITION_FORM, "" + customerLoginResponse.getRequesition_form());
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.ID_PROOF, "" + customerLoginResponse.getId_proof());

                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.LOGIN_EMAIL, "" + emailStr);
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.LOGIN_PASSWORD, "" + passwordStr);
                        MySharedPreferences.setPreference(SplashScreenActivity.this, "" + AppConstants.LOGIN_STATUS, "1");
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        Utilities.finishAnimation(SplashScreenActivity.this);

                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
                        finish();

                        // Utilities.customMessage(mainViewRLID, SplashScreenActivity.this, "" + customerLoginResponse.getMsg());

                    }
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
                    finish();
                    // Utilities.customMessage(mainViewRLID, SplashScreenActivity.this, "Login Failed");

                }
            }

            @Override
            public void onFailure(Call<CustomerLoginResponse> call, Throwable t) {
                dismissProgress();
                startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
                finish();
                //Utilities.customMessage(mainViewRLID, SplashScreenActivity.this, "Error : " + t.getMessage());
            }
        });

    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Checking details")
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
