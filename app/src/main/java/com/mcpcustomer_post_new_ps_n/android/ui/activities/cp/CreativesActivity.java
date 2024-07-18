package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;


import com.mcpcustomer_post_new_ps_n.android.ui.fragments.FragmentCreatives;
import com.mcpcustomer_post_new_ps_n.android.ui.fragments.FragmentCreativesWithOut;

public class CreativesActivity extends AppCompatActivity {

    RelativeLayout backRLID;

    TextView headerTittleTVID;

    AppCompatButton withVisibleBtn,withGoneBtn,withoutVisibleBtn,withoutGoneBtn;

    Fragment fragment;

    SwitchCompat switchUser;


    @SuppressLint({"SetTextI18n", "WrongThread"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatives);


        backRLID = findViewById(R.id.backRLID);

        headerTittleTVID = findViewById(R.id.headerTittleTVID);

        withVisibleBtn = findViewById(R.id.withVisibleBtn);
        withGoneBtn = findViewById(R.id.withGoneBtn);
        withoutVisibleBtn = findViewById(R.id.withoutVisibleBtn);
        withoutGoneBtn = findViewById(R.id.withoutGoneBtn);
        switchUser = findViewById(R.id.switchUser);

        fragment = new FragmentCreatives();
        loadFragment(fragment);

        switchUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    fragment = new FragmentCreatives();
                    loadFragment(fragment);
                }else {
                    fragment = new FragmentCreativesWithOut();
                    loadFragment(fragment);
                }

            }
        });


        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(CreativesActivity.this, "" + AppConstants.CP_LOGIN_STATUS, "10");
            MySharedPreferences.setPreference(CreativesActivity.this, "" + AppConstants.CP_VIEW, "YES");
        });

        headerTittleTVID.setText("Creatives");


    }





    private void loadFragment(final Fragment fragment) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.CreativesViewPager, fragment, "")
                        .commit();
                //.commitAllowingStateLoss();
            }
        }, 30);
    }
}