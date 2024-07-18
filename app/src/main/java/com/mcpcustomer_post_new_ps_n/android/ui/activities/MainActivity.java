package com.mcpcustomer_post_new_ps_n.android.ui.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.ChangePassword;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CpLoginActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.CreateLeadActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.PrivacyPolicy;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.cp.SearchActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.MakePaymentActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.MyAccountActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.fragments.FragmentCP;
import com.mcpcustomer_post_new_ps_n.android.ui.fragments.FragmentDashBoard;
import com.mcpcustomer_post_new_ps_n.android.ui.fragments.FragmentPurchaseDetails;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //side menu
    String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    CharSequence mDrawerTitle;
    CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    LinearLayout nav_menu_layout;
    RelativeLayout logoutRLID, homeRLID,purchaseRLID, cpRLID, cpLoginRLID, myAccountRLID, makePaymentRLID,forgenetRLID,privaypolicyRLID;
    Fragment fragment;
    boolean isHome = true;
    boolean isPurchase = true;
    boolean isCP = true;
    String cpLoginStatus;
    // MenuItem addBtn;
    MenuItem addBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utilities.startAnimation(this);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        nav_menu_layout = findViewById(R.id.nav_menu_layout);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        privaypolicyRLID = findViewById(R.id.privaypolicyRLID);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String name = MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_NAME);
        String agentName = MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_NAME);
        String mobileNumber = MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_PROJECT_NAME);
        String customerType = MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_TYPE);
        String cpStatus = MySharedPreferences.getPreferences(this, AppConstants.CP_VIEW);
        String userLogin = MySharedPreferences.getPreferences(this,  AppConstants.LOGIN_STATUS);
        //String customerType = "1";

        Log.e("CP_STATUS==>", "" + cpStatus);

        TextView sideMenuUsernameTVID = findViewById(R.id.sideMenuUsernameTVID);
        TextView accountTVID = findViewById(R.id.accountTVID);

        if (userLogin.equalsIgnoreCase("1")) {
            sideMenuUsernameTVID.setText(name);
        }else if (cpStatus.equalsIgnoreCase("YES")){
            sideMenuUsernameTVID.setText(agentName);
        }

        //MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "NO");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        logoutRLID = findViewById(R.id.logoutRLID);
        forgenetRLID = findViewById(R.id.forgenetRLID);
        homeRLID = findViewById(R.id.homeRLID);
        purchaseRLID = findViewById(R.id.purchaseRLID);
        cpRLID = findViewById(R.id.cpRLID);
        cpLoginRLID = findViewById(R.id.cpLoginRLID);
        myAccountRLID = findViewById(R.id.myAccountRLID);
        makePaymentRLID = findViewById(R.id.makePaymentRLID);

        logoutRLID.setOnClickListener(this);
        forgenetRLID.setOnClickListener(this);
        homeRLID.setOnClickListener(this);
        purchaseRLID.setOnClickListener(this);
        privaypolicyRLID.setOnClickListener(this);
        cpRLID.setOnClickListener(this);
        cpLoginRLID.setOnClickListener(this);
        myAccountRLID.setOnClickListener(this);
        makePaymentRLID.setOnClickListener(this);
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        MySharedPreferences.getPreferences(this,AppConstants.CONTACT_NUMBER);
        MySharedPreferences.getPreferences(this,AppConstants.CUSTOMER_PLOT_NUMBER);
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String version = info.versionName;
        AppCompatTextView versionID = findViewById(R.id.versionTVID);
        versionID.setText("Version : " + version);

        //addBtn.setVisible(false);
        /*fragment = new FragmentDashBoard();
        loadFragment(fragment);*/

        if (cpStatus.equalsIgnoreCase("YES")) {
            isHome = false;
            isPurchase = false;
            isCP = true;
            //addBtn.setVisible(true);
            fragment = new FragmentCP();
            loadFragment(fragment);
            accountTVID.setText(MySharedPreferences.getPreferences(this,AppConstants.CP_USER_NUMBER));
            //MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "NO");
            cpLoginRLID.setVisibility(View.GONE);
            cpRLID.setVisibility(View.VISIBLE);
        } else if (userLogin.equalsIgnoreCase("1")){
            isHome = true;
            isPurchase = false;
            isCP = false;
            //addBtn.setVisible(false);
            fragment = new FragmentDashBoard();
            fragment = new FragmentDashBoard();
            loadFragment(fragment);
            accountTVID.setText(MySharedPreferences.getPreferences(this,AppConstants.CONTACT_NUMBER));
            MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "NO");

        }

        /*if (customerType.equalsIgnoreCase("0")){
            purchaseRLID.setVisibility(View.GONE);
            accountTVID.setText(MySharedPreferences.getPreferences(this,AppConstants.CONTACT_NUMBER));
        }else{
            accountTVID.setText(mobileNumber+",Plot No : "+MySharedPreferences.getPreferences(this,AppConstants.CUSTOMER_PLOT_NUMBER));
            purchaseRLID.setVisibility(View.VISIBLE);
        }*/

        cpLoginStatus = MySharedPreferences.getPreferences(this, "" + AppConstants.CP_LOGIN_STATUS);

        if (cpLoginStatus.equals("10")) {
            cpLoginRLID.setVisibility(View.GONE);
            cpRLID.setVisibility(View.VISIBLE);
        } else {
            cpLoginRLID.setVisibility(View.VISIBLE);
            cpRLID.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String cpStatus = MySharedPreferences.getPreferences(this, AppConstants.CP_VIEW);

        if (cpStatus.equalsIgnoreCase("YES")) {
            isHome = false;
            isPurchase = false;
            isCP = true;
            //addBtn.setVisible(true);
            fragment = new FragmentCP();
            loadFragment(fragment);
            //MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "NO");
            cpLoginRLID.setVisibility(View.GONE);
            cpRLID.setVisibility(View.VISIBLE);
        } else {
            isHome = true;
            isPurchase = false;
            isCP = false;
            //addBtn.setVisible(false);
            fragment = new FragmentDashBoard();
            fragment = new FragmentDashBoard();
            loadFragment(fragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.logoutRLID:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                logoutAlert();
                break;

            case R.id.privaypolicyRLID:
                startActivity(new Intent(this, PrivacyPolicy.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.forgenetRLID:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(this, ChangePassword.class));
                break;

            case R.id.homeRLID:
                isHome = true;
                isPurchase = false;
                isCP = false;
                addBtn.setVisible(false);
                fragment = new FragmentDashBoard();
                loadFragment(fragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.purchaseRLID:
                isHome = false;
                isPurchase = true;
                addBtn.setVisible(false);
                isCP = false;
                Intent intent=new Intent(this,FragmentPurchaseDetails.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.cpRLID:
                isHome = false;
                isPurchase = false;
                isCP = true;
                //addBtn.setVisible(true);
                fragment = new FragmentCP();
                loadFragment(fragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.cpLoginRLID:

                startActivity(new Intent(this, CpLoginActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.myAccountRLID:
                startActivity(new Intent(this, MyAccountActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.makePaymentRLID:
                startActivity(new Intent(this, MakePaymentActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        addBtn = menu.findItem(R.id.addBtn);
        //addBtn.setVisible(isCP);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        String cpStatus = MySharedPreferences.getPreferences(this, AppConstants.CP_VIEW);

        if (cpStatus.equalsIgnoreCase("YES")) {

            switch (item.getItemId()) {
                case R.id.search:
                    setVisible(true);
                    startActivity(new Intent(this, SearchActivity.class));
                    break;
            }
        }

        switch (item.getItemId()) {
            case R.id.refresh:

                if (isHome) {
                    addBtn.setVisible(false);
                    fragment = new FragmentDashBoard();
                    loadFragment(fragment);
                } else if (isPurchase) {
                    addBtn.setVisible(false);
                    Intent intent=new Intent(this,FragmentPurchaseDetails.class);
                    startActivity(intent);
                } else if (isCP) {
                    addBtn.setVisible(false);
                    fragment = new FragmentCP();
                    loadFragment(fragment);
                    //MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "NO");
                    cpLoginRLID.setVisibility(View.GONE);
                    cpRLID.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.addBtn:
                startActivity(new Intent(this, CreateLeadActivity.class));
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        mDrawerToggle.syncState();
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
                MySharedPreferences.clearPreferences(MainActivity.this);
                Intent newIntent = new Intent(MainActivity.this, CpLoginActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void loadFragment(final Fragment fragment) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame_container, fragment, "")
                        .commit();
                //.commitAllowingStateLoss();
            }
        }, 30);
    }
}
