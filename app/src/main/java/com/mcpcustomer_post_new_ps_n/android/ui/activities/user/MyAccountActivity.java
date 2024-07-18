package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponse;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tittle;
    String name, email, mobilenumber;
    TextView nameId, emailId, numberId;
    EditText password, confirmpassword;
    String passwordS, cnfmpasswordS;
    Button submitBtn;
    LinearLayout mainLLID;
    KProgressHUD kProgressHUD;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Utilities.startAnimation(this);

        name = MySharedPreferences.getPreferences(MyAccountActivity.this, "" + AppConstants.CUSTOMER_NAME);
        email = MySharedPreferences.getPreferences(MyAccountActivity.this, "" + AppConstants.EMAIL_ID);
        mobilenumber = MySharedPreferences.getPreferences(MyAccountActivity.this, "" + AppConstants.CONTACT_NUMBER);
        tittle = findViewById(R.id.headerTitleTVID);
        tittle.setText("My Account");

        mainLLID = findViewById(R.id.mainLLID);
        RelativeLayout back = findViewById(R.id.headerBackRLID);
        back.setVisibility(View.VISIBLE);
        nameId = findViewById(R.id.mynameTVID);
        emailId = findViewById(R.id.myemailTVID);
        numberId = findViewById(R.id.mymobileTVID);
        password = findViewById(R.id.mypasswordETID);
        confirmpassword = findViewById(R.id.myconfirmpasswordETID);
        submitBtn = findViewById(R.id.updateBtn);

        nameId.setText(name);
        emailId.setText(email);
        numberId.setText(mobilenumber);

        back.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                validation();
                break;

            case R.id.headerBackRLID:
                Utilities.finishAnimation(this);
                break;
        }
    }

    private void validation() {
        passwordS = password.getText().toString().replace(" ", "%20");
        cnfmpasswordS = confirmpassword.getText().toString().replace(" ", "%20");
        int count = 0;
        if (TextUtils.isEmpty(passwordS)) {
            Utilities.customMessage(mainLLID, MyAccountActivity.this, "Enter new password");
            return;
        }

        if (!passwordS.matches(cnfmpasswordS)) {
            Utilities.customMessage(mainLLID, MyAccountActivity.this, "Password doesn't matches");
            return;
        }

        changePasswordResponse(passwordS);

    }

    private void changePasswordResponse(String passwordS) {
        showProgress();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponse>> call = apiInterface.changePasswordApi(email, passwordS);
        call.enqueue(new Callback<ArrayList<StatusResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponse>> call, Response<ArrayList<StatusResponse>> response) {
                dismissProgress();
                if (response.body() != null && response.code() == 200) {
                    ArrayList<StatusResponse> statusResponses = response.body();
                    for (int i = 0; i < statusResponses.size(); i++) {
                        if (statusResponses.get(i).getStatus() == 1) {
                            Utilities.customMessage(mainLLID, MyAccountActivity.this, "Profile updated");
                            Utilities.finishAnimation(MyAccountActivity.this);
                        } else {
                            Utilities.customMessage(mainLLID, MyAccountActivity.this, "Not Updating");
                        }
                    }
                } else {
                    Utilities.customMessage(mainLLID, MyAccountActivity.this, "Error");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponse>> call, Throwable t) {
                dismissProgress();
                Utilities.customMessage(mainLLID, MyAccountActivity.this, "Error " + t.getMessage());
            }
        });
    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Updating...")
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
