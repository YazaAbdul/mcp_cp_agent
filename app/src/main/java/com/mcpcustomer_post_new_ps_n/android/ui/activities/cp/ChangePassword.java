package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AgentnewpasswordResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {


    KProgressHUD kProgressHUD;
    String userid,pasword,RePasword;
    AppCompatEditText newPasswordETID,reEnternewPasswordETID;
    RelativeLayout okRLID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        RelativeLayout backRLID = findViewById(R.id.backRLID);

        TextView headerTittleTVID = findViewById(R.id.headerTittleTVID);
        newPasswordETID = findViewById(R.id.newPasswordETID);
        reEnternewPasswordETID = findViewById(R.id.reEnternewPasswordETID);
        okRLID = findViewById(R.id.okRLID);
        okRLID.setVisibility(View.VISIBLE);
        userid = MySharedPreferences.getPreferences(ChangePassword.this, "" + AppConstants.CP_USER_ID);
        Log.e("api==>", "userid: "+userid);
        headerTittleTVID.setText("Change Password");
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressedAnimation();
            }
        });

        okRLID.setOnClickListener( v-> {

            validation();

        });

    }

    private void validation() {
        pasword=newPasswordETID.getText().toString();
        RePasword=reEnternewPasswordETID.getText().toString();
        if(pasword.equalsIgnoreCase(RePasword)){
            changepassword();

        }else{
            Toast.makeText(this, "MissMatch Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void changepassword() {
        showProgress();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AgentnewpasswordResponse>> call = apiInterface.getChangePasswordResponse(userid,pasword);
        Log.e("api==>", call.request().toString());
        call.enqueue(new Callback<ArrayList<AgentnewpasswordResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AgentnewpasswordResponse>> call, Response<ArrayList<AgentnewpasswordResponse>> response) {

                if (response.body() != null && response.code() == 200) {
                    ArrayList<AgentnewpasswordResponse> statusResponses = response.body();
                    if(statusResponses.get(0).getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                        dismissProgress();
                        MySharedPreferences.clearPreferences(ChangePassword.this);
                        Intent newIntent = new Intent(ChangePassword.this, CpLoginActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                        finish();
                    }



                }

            }

            @Override
            public void onFailure(Call<ArrayList<AgentnewpasswordResponse>> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "onFa", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void logoutAlert() {
        final Dialog dialog;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_logout);

        AppCompatButton alertNoBtn = dialog.findViewById(R.id.noBtn);
        AppCompatButton alertYesBtn = dialog.findViewById(R.id.yesBtn);
        alertNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        alertYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                MySharedPreferences.clearPreferences(ChangePassword.this);
                Intent newIntent = new Intent(ChangePassword.this, CpLoginActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void backPressedAnimation() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);
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