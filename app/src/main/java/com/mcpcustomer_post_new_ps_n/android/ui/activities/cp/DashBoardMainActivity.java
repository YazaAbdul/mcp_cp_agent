package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.CustomExpandableListView;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.LoginActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DashBoardHeaderAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashBordPerformanceResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardChildResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardHeaderResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.NewDashboardResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardMainActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    AlertDialog.Builder builder;
 RecyclerView activitiesRCVID,preActivitiesRCVID;
    CustomExpandableListView leadEXLVID;
    DashBoardHeaderAdapter adapter;
    private List<DashboardHeaderResponse> headerResponses = new ArrayList<>();
    private ArrayList<DashboardChildResponse> overall = new ArrayList<>();
    private ArrayList<DashboardChildResponse> today = new ArrayList<>();

    String userID;
    ProgressBar prePB, perPB;
    private ViewPager viewPager;
    private PerformanceAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    TextView[] dots;
    private ArrayList<PerformanceList> lists = new ArrayList<>();
    RelativeLayout newCreateID;
    TextView preNoDataPB;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_main);
        Utilities.startAnimation(this);
        toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        activitiesRCVID = findViewById(R.id.activitiesRCVID);
        activitiesRCVID.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        preActivitiesRCVID = findViewById(R.id.preActivitiesRCVID);
        preActivitiesRCVID.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        initNavigationDrawer();


        preNoDataPB = findViewById(R.id.preNoDataPB);
        userID = MySharedPreferences.getPreferences(this, "" + AppConstants.USER_ID);
        /*leadEXLVID = findViewById(R.id.leadEXLVID);
        leadEXLVID.setFocusable(false);
        leadEXLVID.setExpanded(true);*/
        prePB = findViewById(R.id.prePB);
        perPB = findViewById(R.id.perPB);

        //new
        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        newCreateID = findViewById(R.id.newCreateID);
        newCreateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardMainActivity.this, CreateLeadActivity.class));
            }
        });

        callApis();

        FloatingActionButton refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApis();
            }
        });

        PackageManager manager = getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String version = info.versionName;
        AppCompatTextView versionID = findViewById(R.id.versionTVID);
        versionID.setText("Version : " + version);

       /* leadEXLVID.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Doing nothing
                return true;
            }
        });*/

    }

    @SuppressLint("SetTextI18n")
    public void initNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.refid:
                        //    Toast.makeText(getApplicationContext(), "Refer Id", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.askconnections:

                        /*   Toast.makeText(getApplicationContext(),"Ask connections",Toast.LENGTH_SHORT).show();*/
                        Intent dashboardaskconnections = new Intent(DashBoardMainActivity.this, CreateLeadActivity.class);
                        startActivity(dashboardaskconnections);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.logout:

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DashBoardMainActivity.this);
                        builder1.setCancelable(false);
                        builder1.setTitle(null);
                        builder1.setMessage("Do You Want to Logout?");
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        });
                        builder1.setNegativeButton("No", null);
                        builder1.create().show();


                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_dwname = header.findViewById(R.id.tv_drwname);
        TextView tv_dwnumber = header.findViewById(R.id.tv_drwnumber);

        TextView adminNameID = findViewById(R.id.adminNameID);
        TextView adminNumberID = findViewById(R.id.adminNumberID);

        String juname = MySharedPreferences.getPreferences(DashBoardMainActivity.this, "" + AppConstants.CUSTOMER_NAME);
        String junumber = MySharedPreferences.getPreferences(DashBoardMainActivity.this, "" + AppConstants.CONTACT_NUMBER);

        tv_dwname.setText("" + juname);
        tv_dwnumber.setText("" + junumber);

        adminNameID.setText("" + juname);
        adminNumberID.setText("" + junumber);
        drawerLayout = findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        //.mutate();
        //yourdrawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.toobariconcolor), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Again To exit App", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dashboardseaerch:
                startActivity(new Intent(DashBoardMainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        MySharedPreferences.setPreference(DashBoardMainActivity.this, "" + AppConstants.USER_ID, "");
        MySharedPreferences.setPreference(DashBoardMainActivity.this, "" + AppConstants.EMAIL_ID, "");
        MySharedPreferences.setPreference(DashBoardMainActivity.this, "" + AppConstants.CONTACT_NUMBER, "");
        MySharedPreferences.setPreference(DashBoardMainActivity.this, "" + AppConstants.CUSTOMER_NAME, "");
        MySharedPreferences.setPreference(DashBoardMainActivity.this, "" + AppConstants.CUSTOMER_TYPE, "");
        MySharedPreferences.setPreference(DashBoardMainActivity.this, "" + AppConstants.LOGIN_STATUS, "");
        Intent intent = new Intent(DashBoardMainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void callApis() {
        if (Utilities.isNetworkAvailable(this)) {
            callOverAllMainCountApi();
            loadDashBoardCount();
            callTodayMainCountApi();
           // leadEXLVID.setExpanded(true);
           // leadEXLVID.setFocusable(false);
        } else {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDashBoardCount() {
        perPB.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DashBordPerformanceResponse>> call = apiInterface.getUserDashBoardCount(userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DashBordPerformanceResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<DashBordPerformanceResponse>> call, Response<ArrayList<DashBordPerformanceResponse>> response) {
                perPB.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<DashBordPerformanceResponse> countResponses = response.body();
                    if (countResponses.size() > 0) {
                        for (int i = 0; i < countResponses.size(); i++) {

                            lists.clear();
                            lists.add(new PerformanceList("Month Performance", "", "" + countResponses.get(i).getMnth_enq(), "" + countResponses.get(i).getMnth_sitevisit(), "" + countResponses.get(i).getMnth_sale(), "0.00%", "", "", ""));
                            lists.add(new PerformanceList("Overall Performance", "", "" + countResponses.get(i).getOverall_enq(), "" + countResponses.get(i).getOverall_sitevisit(), "" + countResponses.get(i).getOverall_sale(), "0.00%", "", "", ""));
                            myViewPagerAdapter = new PerformanceAdapter(DashBoardMainActivity.this, lists);
                            viewPager.setAdapter(myViewPagerAdapter);
                            addBottomDots(0);
                            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


                            try {
                               /* dayPhoneCallTVID.setText(countResponses.get(i).getMnth_enq());
                                dayMeetsTVID.setText(countResponses.get(i).getMnth_sitevisit());
                                daySaleTVID.setText(countResponses.get(i).getMnth_sale());

                                monthMeetsTVID.setText(countResponses.get(i).getOverall_sitevisit());
                                monthSaleTVID.setText(countResponses.get(i).getOverall_sale());
                                monthPhoneCallTVID.setText(countResponses.get(i).getOverall_enq());
*/
                                // Picasso.with(DashBoardActivity.this).load(countResponses.get(i).getPic()).into(crmProfilePic);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(DashBoardMainActivity.this, "Data not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashBoardMainActivity.this, "Data not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DashBordPerformanceResponse>> call, Throwable t) {
                Toast.makeText(DashBoardMainActivity.this, "Data not loaded", Toast.LENGTH_SHORT).show();
                perPB.setVisibility(View.GONE);
            }
        });
    }

    private void callOverAllMainCountApi() {
        prePB.setVisibility(View.VISIBLE);
        activitiesRCVID.setVisibility(View.GONE);
        preNoDataPB.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<NewDashboardResponse>> call = apiInterface.getOverAllMAinCount(userID);
        call.enqueue(new Callback<ArrayList<NewDashboardResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<NewDashboardResponse>> call, Response<ArrayList<NewDashboardResponse>> response) {
                prePB.setVisibility(View.GONE);
                activitiesRCVID.setVisibility(View.VISIBLE);
                preNoDataPB.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200) {
                    ArrayList<NewDashboardResponse> todayMainCountResponses = response.body();
                    if (todayMainCountResponses.size() > 0) {
                        DashBoardActivitiesAdapter adapter = new DashBoardActivitiesAdapter(getApplicationContext(), todayMainCountResponses);
                        activitiesRCVID.setAdapter(adapter);
                        //permissions();
                    }

                    /*if (todayMainCountResponses != null && todayMainCountResponses.size() > 0) {
                        for (int i = 0; i < todayMainCountResponses.size(); i++) {
                        *//*    overallSaleCount.setText("Sale" + "( " + todayMainCountResponses.get(i).getOverallsale_counts() + ")");
                            overallNegotiationCount.setText("Negotiation" + "(" + todayMainCountResponses.get(i).getOverallnego_counts() + ")");
                            overallPhoneCount.setText("Calls" + "(" + todayMainCountResponses.get(i).getOverallph_count() + ")");
                            overallMeetingsCount.setText("Site Visit" + "(" + todayMainCountResponses.get(i).getOverallsitevisit_count() + ")");
                           *//*
                            overall.clear();
                            overall.add(new DashboardChildResponse("activitiesplannedphonecall", "Phone Calls", "", "" + todayMainCountResponses.get(i).getOverallph_count()));

                            overall.add(new DashboardChildResponse("activitiesplannedfollowup", "C.M. Followup", "", "" + todayMainCountResponses.get(i).getOverallcm_count()));
                           overall.add(new DashboardChildResponse("activitiesplannedcustomersmeet", "Customers Meet", "", "" + todayMainCountResponses.get(i).getOverallsitevisit_count()));
                            overall.add(new DashboardChildResponse("activitiesplannednegofollowup", "Nego Followup", "", "" + todayMainCountResponses.get(i).getOverallnego_counts()));
                            callTodayMainCountApi();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.something, Toast.LENGTH_SHORT).show();
                    }*/
                    else {
                        prePB.setVisibility(View.GONE);
                        activitiesRCVID.setVisibility(View.GONE);
                        preNoDataPB.setVisibility(View.VISIBLE);
                    }
                }
             else{
                    prePB.setVisibility(View.GONE);
                    activitiesRCVID.setVisibility(View.GONE);
                    preNoDataPB.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewDashboardResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.something, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callTodayMainCountApi() {
        prePB.setVisibility(View.VISIBLE);
        preActivitiesRCVID.setVisibility(View.GONE);
        preNoDataPB.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<NewDashboardResponse>> call = apiInterface.getTodayMAinCount(userID);
        call.enqueue(new Callback<ArrayList<NewDashboardResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<NewDashboardResponse>> call, Response<ArrayList<NewDashboardResponse>> response) {
                prePB.setVisibility(View.GONE);
                preActivitiesRCVID.setVisibility(View.VISIBLE);
                preNoDataPB.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<NewDashboardResponse> todayMainCountResponses = response.body();
                    if (todayMainCountResponses.size() > 0) {
                        DashBoardPlannedAdapter adapter = new DashBoardPlannedAdapter(getApplicationContext(), todayMainCountResponses);
                        preActivitiesRCVID.setAdapter(adapter);
                        //permissions();

                    }
                    else {
                        prePB.setVisibility(View.GONE);
                        preActivitiesRCVID.setVisibility(View.GONE);
                        preNoDataPB.setVisibility(View.VISIBLE);
                    }
                } else {
                    prePB.setVisibility(View.GONE);
                    preActivitiesRCVID.setVisibility(View.GONE);
                    preNoDataPB.setVisibility(View.VISIBLE);
                }

                    /*if (todayMainCountResponses != null && todayMainCountResponses.size() > 0) {
                        for (int i = 0; i < todayMainCountResponses.size(); i++) {
                        *//*    overallSaleCount.setText("Sale" + "( " + todayMainCountResponses.get(i).getOverallsale_counts() + ")");
                            overallNegotiationCount.setText("Negotiation" + "(" + todayMainCountResponses.get(i).getOverallnego_counts() + ")");
                            overallPhoneCount.setText("Calls" + "(" + todayMainCountResponses.get(i).getOverallph_count() + ")");
                            overallMeetingsCount.setText("Site Visit" + "(" + todayMainCountResponses.get(i).getOverallsitevisit_count() + ")");
                           *//*
                            overall.clear();
                            overall.add(new DashboardChildResponse("activitiesplannedphonecall", "Phone Calls", "", "" + todayMainCountResponses.get(i).getOverallph_count()));

                            overall.add(new DashboardChildResponse("activitiesplannedfollowup", "C.M. Followup", "", "" + todayMainCountResponses.get(i).getOverallcm_count()));
                           overall.add(new DashboardChildResponse("activitiesplannedcustomersmeet", "Customers Meet", "", "" + todayMainCountResponses.get(i).getOverallsitevisit_count()));
                            overall.add(new DashboardChildResponse("activitiesplannednegofollowup", "Nego Followup", "", "" + todayMainCountResponses.get(i).getOverallnego_counts()));
                            callTodayMainCountApi();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.something, Toast.LENGTH_SHORT).show();
                    }*/

            }

            @Override
            public void onFailure(Call<ArrayList<NewDashboardResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.something, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class DashBoardActivitiesAdapter extends RecyclerView.Adapter<DashBoardMainActivity.DashBoardActivitiesAdapter.PreSalesVH> {

        Context context;
        ArrayList<NewDashboardResponse> todayMainCountResponses = new ArrayList<NewDashboardResponse>();
        private String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#2196F3"};
        private int[] activityIcons = {R.drawable.menu_calls, R.drawable.menu_sale, R.drawable.menu_calls, R.drawable.menu_nego, R.drawable.menu_sale};

        private String userType;
        private String typeStr;



        public DashBoardActivitiesAdapter(Context context, ArrayList<NewDashboardResponse> todayMainCountResponses) {
            this.context = context;
            this.todayMainCountResponses = todayMainCountResponses;
            this.userType = userType;
            this.typeStr = typeStr;
        }

        @NonNull
        @Override
        public DashBoardMainActivity.DashBoardActivitiesAdapter.PreSalesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new DashBoardMainActivity.DashBoardActivitiesAdapter.PreSalesVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_pre_sales_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PreSalesVH holder, @SuppressLint("RecyclerView") final int i) {
            holder.activityCountTVID.setText(todayMainCountResponses.get(i).getCount());
            String id = todayMainCountResponses.get(i).getActvty_typ_id();
            if (typeStr.equals("1")) {
                holder.activityCountTVID.setVisibility(View.GONE);
            } else {
                holder.activityCountTVID.setVisibility(View.VISIBLE);
            }

            holder.activityNameTVID.setText(todayMainCountResponses.get(i).getActvty_typ_nm());
            Picasso.get().load(activityIcons[i % 5]).into(holder.activityIconID);
            holder.activityIconID.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            holder.activityLLID.setCardBackgroundColor(Color.parseColor(colors[i % 5]));


           /* holder.activityLLID.setOnClickListener(v -> {

                Intent intent;

                intent = new Intent(context, NewPlannedActivities.class);
                intent.putExtra("ACTIVITY_ID", "" + childArrayList.get(i).getActvty_typ_id());
                intent.putExtra("ACTIVITY_NAME", "" + childArrayList.get(i).getActvty_typ_nm());
                intent.putExtra("" + AppConstants.U_ID, "" + userID);
                intent.putExtra("" + AppConstants.U_NAME, "" + name);
                intent.putExtra("" + AppConstants.U_TYPE, "" + userType);
                intent.putExtra("" + AppConstants.U_PIC, "" + pic);
                intent.putExtra("" + AppConstants.U_MOBILE, "" + mobile);
                intent.putExtra("" + AppConstants.U_EMAIL, "" + email);
                startActivity(intent);
            });*/
        }

        @Override
        public int getItemCount() {
            return todayMainCountResponses.size();
        }

        public class PreSalesVH extends RecyclerView.ViewHolder {

            ImageView  activityIconID;
            AppCompatTextView activityCountTVID, activityNameTVID;
            CardView activityLLID;

            public PreSalesVH(@NonNull View itemView) {
                super(itemView);
                activityIconID = itemView.findViewById(R.id.activityIconID);
                activityCountTVID = itemView.findViewById(R.id.activityCountTVID);
                activityNameTVID = itemView.findViewById(R.id.activityNameTVID);
                activityLLID = itemView.findViewById(R.id.activityLLID);
            }
        }
    }
    public class DashBoardPlannedAdapter extends RecyclerView.Adapter<DashBoardMainActivity.DashBoardPlannedAdapter.PreSalesVH> {

        Context context;
        ArrayList<NewDashboardResponse> todayMainCountResponses = new ArrayList<NewDashboardResponse>();
        private String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#2196F3"};
        private int[] activityIcons = {R.drawable.menu_calls, R.drawable.menu_sale, R.drawable.menu_calls, R.drawable.menu_nego, R.drawable.menu_sale};

        private String userType;
        private String typeStr;



        public DashBoardPlannedAdapter(Context context, ArrayList<NewDashboardResponse> todayMainCountResponses) {
            this.context = context;
            this.todayMainCountResponses = todayMainCountResponses;
            this.userType = userType;
            this.typeStr = typeStr;
        }

        @NonNull
        @Override
        public DashBoardMainActivity.DashBoardPlannedAdapter.PreSalesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new DashBoardMainActivity.DashBoardPlannedAdapter.PreSalesVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_pre_sales_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DashBoardMainActivity.DashBoardPlannedAdapter.PreSalesVH holder, @SuppressLint("RecyclerView") final int i) {
            holder.activityCountTVID.setText(todayMainCountResponses.get(i).getCount());
            String id = todayMainCountResponses.get(i).getActvty_typ_id();
            if (typeStr.equals("1")) {
                holder.activityCountTVID.setVisibility(View.GONE);
            } else {
                holder.activityCountTVID.setVisibility(View.VISIBLE);
            }

            holder.activityNameTVID.setText(todayMainCountResponses.get(i).getActvty_typ_nm());
            Picasso.get().load(activityIcons[i % 5]).into(holder.activityIconID);
            holder.activityIconID.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            holder.activityLLID.setCardBackgroundColor(Color.parseColor(colors[i % 5]));


           /* holder.activityLLID.setOnClickListener(v -> {

                Intent intent;

                intent = new Intent(context, NewPlannedActivities.class);
                intent.putExtra("ACTIVITY_ID", "" + childArrayList.get(i).getActvty_typ_id());
                intent.putExtra("ACTIVITY_NAME", "" + childArrayList.get(i).getActvty_typ_nm());
                intent.putExtra("" + AppConstants.U_ID, "" + userID);
                intent.putExtra("" + AppConstants.U_NAME, "" + name);
                intent.putExtra("" + AppConstants.U_TYPE, "" + userType);
                intent.putExtra("" + AppConstants.U_PIC, "" + pic);
                intent.putExtra("" + AppConstants.U_MOBILE, "" + mobile);
                intent.putExtra("" + AppConstants.U_EMAIL, "" + email);
                startActivity(intent);
            });*/
        }

        @Override
        public int getItemCount() {
            return todayMainCountResponses.size();
        }

        public class PreSalesVH extends RecyclerView.ViewHolder {

            ImageView  activityIconID;
            AppCompatTextView activityCountTVID, activityNameTVID;
            CardView activityLLID;

            public PreSalesVH(@NonNull View itemView) {
                super(itemView);
                activityIconID = itemView.findViewById(R.id.activityIconID);
                activityCountTVID = itemView.findViewById(R.id.activityCountTVID);
                activityNameTVID = itemView.findViewById(R.id.activityNameTVID);
                activityLLID = itemView.findViewById(R.id.activityLLID);
            }
        }
    }
    public class PerformanceList {
        private String title;
        private String dialed;
        private String calls;
        private String meetings;
        private String sale;
        private String performance;
        private String business;
        private String totalSale;
        private String totalMeetings;

        public PerformanceList(String title, String dialed, String calls, String meetings, String sale, String performance, String business, String totalSale, String totalMeetings) {
            this.title = title;
            this.dialed = dialed;
            this.calls = calls;
            this.meetings = meetings;
            this.sale = sale;
            this.performance = performance;
            this.business = business;
            this.totalSale = totalSale;
            this.totalMeetings = totalMeetings;
        }

        public String getDialed() {
            return dialed;
        }

        public void setDialed(String dialed) {
            this.dialed = dialed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCalls() {
            return calls;
        }

        public void setCalls(String calls) {
            this.calls = calls;
        }

        public String getMeetings() {
            return meetings;
        }

        public void setMeetings(String meetings) {
            this.meetings = meetings;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public String getPerformance() {
            return performance;
        }

        public void setPerformance(String performance) {
            this.performance = performance;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getTotalSale() {
            return totalSale;
        }

        public void setTotalSale(String totalSale) {
            this.totalSale = totalSale;
        }

        public String getTotalMeetings() {
            return totalMeetings;
        }

        public void setTotalMeetings(String totalMeetings) {
            this.totalMeetings = totalMeetings;
        }
    }

    public class PerformanceAdapter extends PagerAdapter {
        private LayoutInflater inflater;
        Activity activity;
        ArrayList<PerformanceList> performanceLists = new ArrayList<>();


        public PerformanceAdapter(Activity activity, ArrayList<PerformanceList> performanceLists) {
            inflater = LayoutInflater.from(activity);
            this.activity = activity;
            this.performanceLists = performanceLists;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.cp_dash_board_performance_item, container, false);

            try {
                TextView perTitleID = view.findViewById(R.id.perTitleID);
                TextView dialedTVID = view.findViewById(R.id.dialedTVID);
                TextView perCallsTVID = view.findViewById(R.id.perCallsTVID);
                TextView perMeetTVID = view.findViewById(R.id.perMeetTVID);
                TextView perSaleTVID = view.findViewById(R.id.perSaleTVID);
                TextView perBusTVID = view.findViewById(R.id.perBusTVID);
                TextView perTotalSaleTVID = view.findViewById(R.id.perTotalSaleTVID);
                TextView perTotMeetTVID = view.findViewById(R.id.perTotMeetTVID);
                TextView perPerformanceTVID = view.findViewById(R.id.perPerformanceTVID);


                perTitleID.setText(performanceLists.get(position).getTitle());
                perCallsTVID.setText(performanceLists.get(position).getCalls());
                dialedTVID.setText(performanceLists.get(position).getDialed());
                perMeetTVID.setText(performanceLists.get(position).getMeetings());
                perSaleTVID.setText(performanceLists.get(position).getSale());
                perBusTVID.setText(performanceLists.get(position).getBusiness());
                perTotalSaleTVID.setText(performanceLists.get(position).getTotalSale());
                perTotMeetTVID.setText(performanceLists.get(position).getTotalMeetings());
                perPerformanceTVID.setText(performanceLists.get(position).getPerformance());

                RelativeLayout bgCircleRLID = view.findViewById(R.id.bgCircleRLID);
            } catch (Exception e) {
                e.printStackTrace();
            }


            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return performanceLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    @SuppressLint("SetTextI18n")
    private void addBottomDots(int currentPage) {
        try {
            dots = new TextView[lists.size()];
            int[] colorsActive = getResources().getIntArray(R.array.array_dot_new_active);
            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_new_inactive);

            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                try {
                    dots[i] = new TextView(this);
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(35);
                    dots[i].setTextColor(colorsInactive[currentPage]);
                    dotsLayout.addView(dots[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive[currentPage]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


}
