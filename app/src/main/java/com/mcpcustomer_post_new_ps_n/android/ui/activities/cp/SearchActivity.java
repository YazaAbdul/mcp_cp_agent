package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamResponse;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {

    RecyclerView searchRCID;
    EditText searchETID;
    ProgressBar pbSearch;
    TextView noDataTVID;
    String userID;
    private static final int REQUEST_CALL = 1;
    ArrayList<DirectTeamResponse> searchResponse = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_search);
        Utilities.startAnimation(this);
        MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "YES");
        RelativeLayout backRLID=findViewById(R.id.backRLID);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.finishAnimation(SearchActivity.this);
            }
        });

        searchRCID = findViewById(R.id.searchRCID);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        searchRCID.setLayoutManager(layoutManager);

        searchETID = findViewById(R.id.searchETID);
        pbSearch = findViewById(R.id.pbSearch);
        noDataTVID = findViewById(R.id.noDataTVID);
        userID= MySharedPreferences.getPreferences(this,""+ AppConstants.CP_USER_ID);
        searchETID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadSearchItems(s.toString());
            }
        });

        noDataTVID.setVisibility(View.GONE);

    }

    private void loadSearchItems(String keyword) {
        pbSearch.setVisibility(View.VISIBLE);
        searchRCID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DirectTeamResponse>>call=apiInterface.searchAgent(keyword,userID);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<DirectTeamResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DirectTeamResponse>> call, Response<ArrayList<DirectTeamResponse>> response) {
                pbSearch.setVisibility(View.GONE);
                searchRCID.setVisibility(View.VISIBLE);
                noDataTVID.setVisibility(View.GONE);
                if (response.body()!=null && response.code()==200){

                    searchResponse = response.body();

                    if (searchResponse.size()>0){

                        SearchAdapter searchAdapter=new SearchAdapter(SearchActivity.this,searchResponse);
                        searchRCID.setAdapter(searchAdapter);
                    }else{
                        errorViews();
                    }
                }else{
                    errorViews();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DirectTeamResponse>> call, Throwable t) {
                errorViews();
            }
        });
    }

    private void errorViews() {
        pbSearch.setVisibility(View.GONE);
        searchRCID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchVH> {

        String[] mColors = {"#3F51B5", "#FF9800", "#009688", "#673AB7", "#999999", "#454545", "#00FF00",
                "#FF0000", "#0000FF", "#800000", "#808000", "#00FF00", "#008000", "#00FFFF",
                "#000080", "#800080", "#f40059", "#0080b8", "#350040", "#650040", "#750040",
                "#45ddc0", "#dea42d", "#b83800", "#dd0244", "#c90000", "#465400",
                "#ff004d", "#ff6700", "#5d6eff", "#3955ff", "#0a24ff", "#004380", "#6b2e53",
                "#a5c996", "#f94fad", "#ff85bc", "#ff906b", "#b6bc68", "#296139"};

        private Activity activity;

        private ArrayList<DirectTeamResponse> searchResponse = new ArrayList<>();

        public SearchAdapter(Activity activity, ArrayList<DirectTeamResponse> searchResponse) {
            this.activity = activity;
            this.searchResponse = searchResponse;
        }

        @NonNull
        @Override
        public SearchVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //return new SearchVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cp_lead_list_item, null));
            return new SearchVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.direct_team_item, null));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull SearchVH searchVH, @SuppressLint("RecyclerView") final int i) {

            /*searchVH.newProjectNameTVID.setText(searchResponses.get(i).getProject_name());
            searchVH.date.setText(searchResponses.get(i).getActivity_date());*/

            DirectTeamResponse item = searchResponse.get(i);

            searchVH.associateTVID.setText(item.getAssociate());
            searchVH.serialNoTVID.setText(item.getS_no()+")");
            searchVH.salesTVID.setText("ID : "+item.getId());

            searchVH.upLineTVID.setText(item.getDesignation());
            searchVH.newPhoneRL.setVisibility(View.VISIBLE);


            searchVH.newPhoneRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //numberCalling(searchResponses.get(i).getMobile());
                    numberCalling(item.getMobile_no());
                }
            });

            searchVH.whatsAppCallRLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //numberCalling(searchResponses.get(i).getMobile());
                    loadWhatsApp(item.getMobile_no());
                }
            });

            /*GradientDrawable bgShape = (GradientDrawable) searchVH.imageViewIcon.getBackground();
            bgShape.setColor(Color.parseColor(mColors[i % 40]));
            *//*String title = searchResponses.get(i).getCustomer_name();*//*
            searchVH.iconTitleID.setText(Utilities.CapitalText(title));*/
        }

        @Override
        public int getItemCount() {
            return searchResponse.size();
        }

        public class SearchVH extends RecyclerView.ViewHolder {
            AppCompatTextView associateTVID,idTVID,salesTVID,saleAmountTVID,upLineTVID,upLineNameTVID,serialNoTVID;
            TextView newProjectNameTVID;
            TextView date;
            RelativeLayout newPhoneRL,whatsAppCallRLID;
            RelativeLayout imageViewIcon;
            TextView iconTitleID ;


            public SearchVH(@NonNull View itemView) {
                super(itemView);

                associateTVID = itemView.findViewById(R.id.associateTVID);
                serialNoTVID = itemView.findViewById(R.id.serialNoTVID);
                idTVID = itemView.findViewById(R.id.idTVID);
                salesTVID = itemView.findViewById(R.id.salesTVID);
                saleAmountTVID = itemView.findViewById(R.id.saleAmountTVID);
                upLineTVID = itemView.findViewById(R.id.upLineTVID);
                upLineNameTVID = itemView.findViewById(R.id.upLineNameTVID);


                newProjectNameTVID = itemView.findViewById(R.id.newProjectNameTVID);
                date = itemView.findViewById(R.id.date);
                newPhoneRL = itemView.findViewById(R.id.newPhoneRL);
                whatsAppCallRLID = itemView.findViewById(R.id.whatsAppCallRLID);

                imageViewIcon = itemView.findViewById(R.id.imageViewIcon);


                iconTitleID = itemView.findViewById(R.id.iconTitleID);
            }
        }

        private void loadWhatsApp(String mobile_no) {

            Log.e("mobile_number==>","" + mobile_no);

            final BottomSheetDialog dialog1 = new BottomSheetDialog(activity);
            dialog1.setContentView(R.layout.alert_business_whatsapp);

            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            int width1 = ViewGroup.LayoutParams.MATCH_PARENT;
            int height1 = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog1.getWindow().setLayout(width1, height1);
            dialog1.show();

            AppCompatImageView whatsappIVID = dialog1.findViewById(R.id.whatsappIVID);
            AppCompatImageView businessWhatsappIVID = dialog1.findViewById(R.id.businessWhatsappIVID);

            whatsappIVID.setOnClickListener(view1 -> {

                try {
                    Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone=" + "+91" + mobile_no + "&text=" + URLEncoder.encode("", "UTF-8");
                    sendMsg.setPackage("com.whatsapp");
                    sendMsg.setData(Uri.parse(url));
                    activity.startActivity(sendMsg);
                    dialog1.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    /*Utilities.showToast(this,e.getMessage());*/
                    Toast.makeText(activity, "You don't have WhatsApp in your device", Toast.LENGTH_SHORT).show();
                }

            });

            businessWhatsappIVID.setOnClickListener(view2 -> {

                try {

                    Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone=" + "+91" + mobile_no + "&text=" + URLEncoder.encode("", "UTF-8");
                    sendMsg.setPackage("com.whatsapp.w4b");
                    sendMsg.setData(Uri.parse(url));
                    activity.startActivity(sendMsg);



                    dialog1.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    /*Utilities.showToast(this,e.getMessage());*/
                    Toast.makeText(activity, "You don't have business WhatsApp in your device", Toast.LENGTH_SHORT).show();
                }

                dialog1.dismiss();

            });


        }
    }

    private void numberCalling(String mobile) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);

        }
    }



}