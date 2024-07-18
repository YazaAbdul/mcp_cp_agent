package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText emailETID;
    AppCompatButton submitBtn;
    RelativeLayout mainViewRLID;
    KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Utilities.startAnimation(this);

        mainViewRLID = findViewById(R.id.mainViewRLID);
        emailETID = findViewById(R.id.emailETID);
        submitBtn = findViewById(R.id.submitBtn);

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
            case R.id.submitBtn:
                String emailStr = emailETID.getText().toString();
                if (TextUtils.isEmpty(emailStr)) {
                    Utilities.customMessage(mainViewRLID, this, "Please enter your registered mail-id");
                    return;
                }

                callForgotApi(emailStr);
                break;
        }
    }

    private void callForgotApi(String emailStr) {
        showProgress();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponse>> call = apiInterface.forgotPasswordApi(emailStr);
        call.enqueue(new Callback<ArrayList<StatusResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponse>> call, Response<ArrayList<StatusResponse>> response) {
                dismissProgress();
                if (response.body() != null && response.code() == 200) {
                    ArrayList<StatusResponse> statusResponses = response.body();
                    for (int i = 0; i < statusResponses.size(); i++) {
                        if (statusResponses.get(i).getStatus() == 1) {
                            Utilities.customMessage(mainViewRLID, ForgotActivity.this, "Password send to registered mobile number");
                            Utilities.finishAnimation(ForgotActivity.this);
                        } else {
                            Utilities.customMessage(mainViewRLID, ForgotActivity.this, "Given Mail not found");
                        }
                    }
                } else {
                    Utilities.customMessage(mainViewRLID, ForgotActivity.this, "Error");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponse>> call, Throwable t) {
                dismissProgress();
                Utilities.customMessage(mainViewRLID, ForgotActivity.this, "Error : " + t.getMessage());
            }
        });

    }

    public void showProgress() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading....")
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
