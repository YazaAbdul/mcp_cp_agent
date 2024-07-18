package com.mcpcustomer_post_new_ps_n.android.ui.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.AppSingleton;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.OnLoadMoreListener;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.DashBoardCategoriesAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.HomeMainResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.NumberResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentDashBoard extends Fragment {

    private RecyclerView dashBoardRCID;
    private ProgressBar pb;
    private AppCompatTextView noData;
    AppCompatImageView callEnqID;

    private boolean isFirstListLoaded = true;
    private DashBoardCategoriesAdapter dashBoardCategoriesAdapter;
    private ArrayList<HomeMainResponse> mainResponses = new ArrayList<>();
    private Integer start = 0, pages = 1;
    private RelativeLayout mainRLID;
    private String fullname, email, mobile, userIdStr;
  /*  String customer_number;
    String number = "+91"+customer_number;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mainRLID = view.findViewById(R.id.mainRLID);
        dashBoardRCID = view.findViewById(R.id.dashBoardRCID);
        pb = view.findViewById(R.id.pb);
        noData = view.findViewById(R.id.noData);
        callEnqID = view.findViewById(R.id.callEnqID);
        callEnqID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCallRecordResponse();
            }
        });

        fullname = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.CUSTOMER_NAME);
        email = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.EMAIL_ID);
        mobile = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.CONTACT_NUMBER);
        userIdStr = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.USER_ID);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        dashBoardRCID.setLayoutManager(layoutManager);

        dashBoardCategoriesAdapter = new DashBoardCategoriesAdapter(getActivity(), dashBoardRCID);
        dashBoardRCID.setAdapter(dashBoardCategoriesAdapter);
        dashBoardCategoriesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (pages == 1) {
                    mainResponses.add(null);
                    dashBoardCategoriesAdapter.notifyItemInserted(mainResponses.size() - 1);
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            start = start + 10;
                            mainResponses.remove(mainResponses.size() - 1);
                            dashBoardCategoriesAdapter.notifyItemChanged(mainResponses.size());
                            loadHomeData(AppConstants.BASE_URL + "/customerapp/" + "menu?" + "&start=" + start);
                            //Toast.makeText(getActivity(), start, Toast.LENGTH_LONG).show();
                        }
                    }, 2000);
                }
            }
        });

        loadHomeData(AppConstants.BASE_URL + "/customerapp/" + "menu?" + "&start=" + start);
        return view;
    }

    public void loadHomeData(String url) {
        pb.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        Log.e("api==>",url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pb.setVisibility(View.GONE);
                noData.setVisibility(View.GONE);
                dashBoardCategoriesAdapter.setLoaded();
                dashBoardCategoriesAdapter.notifyDataSetChanged();

                if (response == null) {
                    noData.setVisibility(View.VISIBLE);
                } else if (response.equals("null")) {
                    pages = 0;
                    noData.setVisibility(View.VISIBLE);
                } else {
                    try {
                        int count = 0;
                        String menu_id;
                        String menu_name;
                        String location;
                        String menu_icon;
                        String brochure_dtl;
                        String layout_dtl;
                        String application_dtl;

                        while (count < response.length()) {
                            isFirstListLoaded = false;
                            JSONObject jo = response.getJSONObject(count);
                            menu_id = jo.getString("menu_id");
                            menu_name = jo.getString("menu_name");
                            location = jo.getString("location");
                            menu_icon = jo.getString("menu_icon");
                            brochure_dtl = jo.getString("brochure_dtl");
                            layout_dtl = jo.getString("layout_dtl");
                            application_dtl = jo.getString("application_dtl");

                            mainResponses.add(new HomeMainResponse(menu_id, menu_name, location, menu_icon, brochure_dtl, layout_dtl, application_dtl));
                            count++;
                        }
                        if (count < 10) {
                            pages = 0;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dashBoardCategoriesAdapter.setPaginationAdapter(mainResponses);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.GONE);
                if (start == 0) {
                    noData.setVisibility(View.VISIBLE);
                    dashBoardRCID.setVisibility(View.GONE);
                }
            }
        });

        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppSingleton.getInstance(getActivity()).addToRequestQueue(arrayRequest, "");
    }

    private void phoneCallRecordResponse() {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(getActivity(), null, "Getting call please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponse>> call = apiInterface.phoneCallRecord(fullname, email, mobile, userIdStr);
        call.enqueue(new Callback<ArrayList<StatusResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponse>> call, retrofit2.Response<ArrayList<StatusResponse>> response) {
                phoneCall();
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    ArrayList<StatusResponse> status = response.body();
                    if (status != null && status.size() > 0) {
                        for (int i = 0; i < status.size(); i++) {
                            if (status.get(i).getStatus() == 1) {
                                Log.d("Debug-->", "Successfully Submitted");
                            } else {
                                Utilities.customMessage(mainRLID, getActivity(), "Not Submitted");
                            }
                        }
                    } else {
                        Utilities.customMessage(mainRLID, getActivity(), "Not Submitted");
                    }
                } else {
                    Utilities.customMessage(mainRLID, getActivity(), "Not Submitted");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Utilities.customMessage(mainRLID, getActivity(), "Error : " + t.getMessage());
            }
        });
    }

    private void phoneCall() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NumberResponse> call = apiInterface.getCustomerappcall();
        call.enqueue(new Callback<NumberResponse>() {
            @Override
            public void onResponse(Call<NumberResponse> call, retrofit2.Response<NumberResponse> response) {

                if (response.body() != null && response.code() == 200) {
                    NumberResponse numberResponse = response.body();
                    if (numberResponse != null) {

                        String customer_number = numberResponse.getCustomer_number();
                        String number = "+91" + customer_number;
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 0);
                        } else {
                            // String prodcatnumber = "+91 8143300555";
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + number));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        }

                    } else {

                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<NumberResponse> call, Throwable t) {
                // Toast.makeText(getApplicationContext(),"Error : "+t.getMessage());
            }
        });
    }


    private void numberCalling() {


    }
}
