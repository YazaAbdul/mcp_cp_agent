package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.ExpandableHeightGridView;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectsAdapter;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AddCustomerResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.RequirementResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.VeichleResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CPTravel_Request extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    LinearLayout projectLLID,veichleNewLLID,dateLLID;
    TextInputEditText nameTIETID,mobilenumberETID,dateETID,timeETID,addressETID,noofpassETID,ibaETID,ibaNumberETID;
    String nameStr,mobilenumberStr,dateStr,pick_up_point,projectsstr,addressStr,noofPassStr,ibaNameStr,ibaNumberStr,coststr,veichlestr="",projectstr="",sribalajitownship="yes",royalcountry="yes",eliteenclave="yes", requirementTypeStr="";
    Spinner agentSPID,projectSPID;
    int day, month, year, hour, minute,seconds;
    int myday, myMonth, myYear, myHour, myMinute;
    Button addEnqSubmit;
    String pageFrom;
    String agentid ;
    String userid ;
    Dialog dialog;
    ExpandableHeightGridView dropDownLVID;
    ProgressBar drPB;
    String VeichleId="2";
    String projects;
    String key,value;
    private RecyclerView veichleRCVID,projecttRCVID;
    MediaPlayer mPlayer;
    String[] dummyData = new String[] {"1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7", };
    //ArrayList<AgentResponse> agentResponses = new ArrayList<>();
    ArrayList<RequirementResponse> requirementResponses = new ArrayList<>();

    ArrayList<String> selectedList = new ArrayList<>();

    // ArrayList<VeichleResponse> veichleResponses = new ArrayList<>();
    private ArrayList<VeichleResponse> veichleResponses;

    //RequirementAdapter requirementAdapter;
    private ProjectsAdapter projectsAdapter;
   // private VeichleAdapter veichleAdapter;
    ArrayList<String> frnames[];
    List<String> list;
    DatePickerDialog datePickerDialog;
    RelativeLayout dateRLID;

    private Calendar calendar;
    private int mYear, mMonth, mHour, mMinute, mDay, finalHour;
    private String mDate;

    TextView dateTIETID,timeTVID;
    LinearLayout dateTILID,timeLLID;
    String timeStr;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p_travel__request);
        agentid = MySharedPreferences.getPreferences(getApplicationContext(), "" + AppConstants.CP_USER_ID);

        TextView headerTittleTVID = findViewById(R.id.headerTittleTVID);

      /*  if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                VeichleId = bundle.getString("veichle_id");


            }
        }
*/
        headerTittleTVID.setText("Vehicle Request");
        nameTIETID = findViewById(R.id.nameTIETID);
        mobilenumberETID = findViewById(R.id.mobilenumberETID);

        timeETID = findViewById(R.id.timeETID);
        addressETID = findViewById(R.id.addressETID);
        noofpassETID = findViewById(R.id.noofpassETID);

        // selectprojectTIETID = findViewById(R.id.selectprojectTIETID);
        agentSPID = findViewById(R.id.agentSPID);
        veichleRCVID = findViewById(R.id.veichleRCVID);
        projecttRCVID = findViewById(R.id.projecttRCVID);

        dateTIETID = findViewById(R.id.dateTIETID);
        timeTVID = findViewById(R.id.timeTVID);
        dateTILID = findViewById(R.id.dateTILID);
        veichleRCVID.setHasFixedSize(true);
        veichleRCVID.setLayoutManager(new LinearLayoutManager(this));
        addEnqSubmit = findViewById(R.id.addEnqSubmit);
        RelativeLayout backRLID = findViewById(R.id.backRLID);

        ibaETID = findViewById(R.id.ibaETID);
        ibaNumberETID = findViewById(R.id.ibaNumberETID);
        timeLLID = findViewById(R.id.timeLLID);

       // backRLID.setOnClickListener(v -> Utilities.finishAnimation(CPTravel_Request.this));
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.finishAnimation(CPTravel_Request.this);
                MySharedPreferences.setPreference(CPTravel_Request.this, "" + AppConstants.CP_VIEW, "YES");
            }
        });

       //callProject();
      //  callVeichle();
        callRequirement();
        addEnqSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                validations();
            }
        });
        userid=MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_ID);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //calendar.set(Calendar.HOUR_OF_DAY, 24);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        seconds =calendar.get(Calendar.SECOND);

        String AM_PM ;
        if(hour < 12) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }

        timeTVID.setText(hour+ ":" + minute+ " " + AM_PM);

        timeStr = timeTVID.getText().toString();

        dateTIETID.setText("" + year + "-" + (month + 1) + "-" + day);
        dateStr = dateTIETID.getText().toString().replace(" ", "%20");
        dateTIETID.setText("" + year + "-" + (month + 1) + "-" + day);

        dateTILID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                seconds=c.get(Calendar.SECOND);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CPTravel_Request.this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                           // TimePickerDialog timePickerDialog = new TimePickerDialog(CPTravel_Request.this, CPTravel_Request.this, hour, minute, DateFormat.is24HourFormat(CPTravel_Request.this));

                            dateTIETID.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            dateStr = dateTIETID.getText().toString().replace(" ", "%20");
                            dateTIETID.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        //    timePickerDialog.show();
                        }, year, month, day);

                datePickerDialog.show();
            }
               /* Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH)+1;
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CPTravel_Request.this,CPTravel_Request.this,year,month,day);
                datePickerDialog.show();
            }*/
        });

        timeLLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                seconds=c.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CPTravel_Request.this, CPTravel_Request.this, hour, minute, false);

                timePickerDialog.show();

               /* TimePickerDialog.OnTimeSetListener onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {

                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM ;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }

                        timeTVID.setText(hourOfDay + " : " + minute + " " + AM_PM );
                    }
                };*/

            }

        });



        /*.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                new DatePickerDialog(SubCategoriesActivity.this,datePickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }});*/



    }


    private void callRequirement() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<RequirementResponse>> call = apiInterface.getRequirementResponse();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<RequirementResponse>>() {

            @Override
            public void onResponse(Call<ArrayList<RequirementResponse>> call, Response<ArrayList<RequirementResponse>> response) {

                if (response.body() != null && response.code() == 200) {
                    requirementResponses = response.body();
                    if (requirementResponses.size() > 0) {

                        final JSONArray jArrayObj = new JSONArray(requirementResponses);
                        for (int i = 0; i < jArrayObj.length(); i++) {
                            JSONObject json_data = null;
                            try {

                                json_data = jArrayObj.getJSONObject(i);
                                requirementResponses = new ArrayList<>();
                                RequirementResponse veichleResponse = new RequirementResponse();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            //   recyclerView.setHasFixedSize(true);
                            projectsAdapter = new ProjectsAdapter(CPTravel_Request.this,requirementResponses);
                            projecttRCVID.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            projectsAdapter.setClick(new ProjectsAdapter.ServiceClickListener() {
                                @Override
                                public void onAdd(RequirementResponse itemsData) {
                                    Log.e("added-->", "" + itemsData.getProject_id());
                                    selectedList.add(itemsData.getProject_id());
                                }

                                @Override
                                public void onRemove(RequirementResponse itemsData) {
                                    Log.e("removed-->", "" + itemsData.getProject_id());
                                    selectedList.remove(itemsData.getProject_id());
                                }
                            });
                            JSONArray arr = new JSONArray(requirementResponses);
                            list = new ArrayList<String>();
                            for(int j = 0; j < arr.length(); j++){
                                try {
                                    list.add(arr.getJSONObject(j).getString("project_id"));
                                    projectsstr = arr.getJSONObject(j).getString("project_id");

                                    JSONObject cate = arr.getJSONObject(j);
                                    String s = cate.getString("project_id");



                                    //get list of keys

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            //  frnames=friendsnames.toArray(new String[friendsnames.size()]);

                            projecttRCVID.setAdapter(projectsAdapter);


                        }
                    } else {
                        Toast.makeText(CPTravel_Request.this, "Source Details not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CPTravel_Request.this, "Source Details not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RequirementResponse>> call, Throwable t) {

                Toast.makeText(CPTravel_Request.this, "Details not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  public class RequirementAdapter extends BaseAdapter implements Filterable {
        private ArrayList<RequirementResponse> _Contacts;
        private Activity context;
        private LayoutInflater inflater;
        private ValueFilter valueFilter;
        private ArrayList<RequirementResponse> mStringFilterList;

        public RequirementAdapter(Activity context, ArrayList<RequirementResponse> _Contacts) {
            super();
            this.context = context;
            this._Contacts = _Contacts;
            mStringFilterList = _Contacts;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            getFilter();
        }

        @Override
        public int getCount() {
            return _Contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return _Contacts.get(position).getProject_name();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class ViewHolder {
            TextView tname;
            LinearLayout dropDownListItem;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.creat_lead_list_item, null);

                holder.tname = convertView.findViewById(R.id.titleTVID);
                holder.dropDownListItem = convertView.findViewById(R.id.dropDownListItem);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            holder.tname.setText(_Contacts.get(position).getProject_name());
            holder.dropDownListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    requirementTypeStr = _Contacts.get(position).getProject_id();
                }
            });



            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (valueFilter == null) {

                valueFilter = new ValueFilter();
            }

            return valueFilter;
        }

        private class ValueFilter extends Filter {

            //Invoked in a worker thread to filter the data according to the constraint.
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<RequirementResponse> filterList = new ArrayList<RequirementResponse>();
                    for (int i = 0; i < mStringFilterList.size(); i++) {
                        if ((mStringFilterList.get(i).getProject_name().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            RequirementResponse contacts = new RequirementResponse();
                            contacts.setProject_name(mStringFilterList.get(i).getProject_name());
                            contacts.setProject_id(mStringFilterList.get(i).getProject_id());
                            filterList.add(contacts);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mStringFilterList.size();
                    results.values = mStringFilterList;
                }
                return results;
            }


            //Invoked in the UI thread to publish the filtering results in the user interface.
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                _Contacts = (ArrayList<RequirementResponse>) results.values;
                notifyDataSetChanged();
            }
        }
    }*/

    /*private void callProject() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AgentResponse>> call = apiInterface.getAgentResponse();
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<AgentResponse>>() {

            @Override
            public void onResponse(Call<ArrayList<AgentResponse>> call, Response<ArrayList<AgentResponse>> response) {

                if (response.body() != null && response.code() == 200) {
                    agentResponses = response.body();
                    if (agentResponses != null && agentResponses.size() > 0) {
                        for (int i = 0; i < agentResponses.size(); i++) {
                            AgentAdapter agentAdapter = new AgentAdapter(CPTravel_Request.this, R.layout.custom_spinner_view, agentResponses);
                            agentSPID.setAdapter(agentAdapter);
                        }
                    } else {
                        Toast.makeText(CPTravel_Request.this, "Details not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CPTravel_Request.this, "Details not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AgentResponse>> call, Throwable t) {

                Toast.makeText(CPTravel_Request.this, "Details not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*private void callVeichle() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<VeichleResponse>> call = apiInterface.getVehicleResponse();
        call.enqueue(new Callback<ArrayList<VeichleResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<VeichleResponse>> call, Response<ArrayList<VeichleResponse>> response) {

                if (response.body() != null && response.code() == 200) {
                    veichleResponses = response.body();
                    if (veichleResponses.size() > 0) {

                        final JSONArray jArrayObj = new JSONArray(veichleResponses);
                        for (int i = 0; i < jArrayObj.length(); i++) {
                            JSONObject json_data = null;
                            try {

                                json_data = jArrayObj.getJSONObject(i);
                                veichleResponses = new ArrayList<>();
                                VeichleResponse veichleResponse = new VeichleResponse();
                                veichleResponse.vehicle_name = json_data.getString("vehicle_name");
                                veichleResponse.vehicle_pic = json_data.getString("vehicle_pic");
                                veichleResponse.id = json_data.getString("id");
                                VeichleId = json_data.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            //   recyclerView.setHasFixedSize(true);
                            veichleAdapter = new VeichleAdapter(getApplicationContext(), veichleResponses);
                            veichleRCVID.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            veichleRCVID.setAdapter(veichleAdapter);


                        }
                    } else {
                        Toast.makeText(CPTravel_Request.this, "Source Details not loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CPTravel_Request.this, "Source Details not loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VeichleResponse>> call, Throwable t) {

                Toast.makeText(CPTravel_Request.this, "Source Details not loaded", Toast.LENGTH_SHORT).show();

            }
        });
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void validations() {

        String ids = getSelectedList();
        Log.e("selected_as-->", "" + ids);

        int count = 0;
        nameStr = nameTIETID.getText().toString();
        mobilenumberStr = mobilenumberETID.getText().toString();
        dateStr = dateTIETID.getText().toString().replace(" ", "%20");
        addressStr = addressETID.getText().toString();
        noofPassStr = noofpassETID.getText().toString();
        ibaNameStr = ibaETID.getText().toString();
        ibaNumberStr = ibaNumberETID.getText().toString();

        //  projectsstr = selectprojectTIETID.getText().toString();
        //  coststr = costTIETID.getText().toString();


        if (TextUtils.isEmpty(ibaNameStr)) {
            Utilities.showToast(this, "Enter Agent Name");
            return;
        } else {
            count++;
        }

        if (TextUtils.isEmpty(ibaNumberStr)) {
            Utilities.showToast(this, "Enter Agent Number");
            return;
        } else {
            count++;
        }

        if (ibaNumberStr.length() <= 9) {
            Utilities.showToast(this, "Enter Valid Agent Number");
            return;
        } else {
            count++;
        }

        if (TextUtils.isEmpty(nameStr)) {
            Utilities.showToast(this, "Enter Customer Name");
            return;
        } else {
            count++;
        }

        if (TextUtils.isEmpty(mobilenumberStr)) {
            Utilities.showToast(this, "Enter Customer Number");
            return;
        } else {
            count++;
        }

        if (mobilenumberStr.length() <= 9) {
            Utilities.showToast(this, "Enter Valid Customer Number");
            return;
        } else {
            count++;
        }

        if (mobilenumberStr.startsWith("0") || mobilenumberStr.startsWith("+91")) {
            mobilenumberStr = "" + mobilenumberStr.substring(1);
        }


           /* if (TextUtils.isEmpty(veichlestr)) {
                Utilities.showToast(this, "Select Veichle");
                return;
            } else {
                count++;
            }
*/
           /* if (TextUtils.isEmpty(projectstr)) {
                Utilities.showToast(this, "Select Project");
                return;
            } else {
                count++;
            }*/


        if (Utilities.isNetworkAvailable(this)) {
            if (TextUtils.isEmpty(ids)) {
                Toast.makeText(this, "Select Requirement", Toast.LENGTH_SHORT).show();
                return;
            }
            createLeadResponse(ids);


        } else {
            Utilities.showToast(this, "No Internet connection");
        }

      /* veichleSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                veichlestr = veichleResponses.get(i).getVehicle_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

       /* agentSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                projectstr = agentResponses.get(i).getAgent_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
    }
    @Override
    public void onBackPressed() {
        backPressedAnimation();
    }

    private void backPressedAnimation() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);
    }

    private String getSelectedList() {

        StringBuilder productIDs = new StringBuilder();

        for (String eachstring : selectedList) {
            productIDs.append(eachstring).append(",");
        }

        String productIDStr = productIDs.toString();


        if (productIDStr.length() > 0)
            productIDStr = productIDStr.substring(0, productIDStr.length() - 1);


        Log.e("selected_ids---->", "" + productIDStr);

        return productIDStr;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createLeadResponse(String selectedList) {
        Utilities.showSimpleProgressDialog(this, null, "Adding Lead...", false);

        JSONObject innerObject = new JSONObject();
        try {
            innerObject.put( "projects","1");
            innerObject.put( "projects","2");
            innerObject.put( "projects","3");


            //get list of keys
            Iterator<String> keys=innerObject.keys();
            while(keys.hasNext()){
                key=keys.next();
                value=innerObject.getString(key);
                Log.e("key", key);
                Log.e("value", value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(innerObject);
        try {

            jsonObject.put("agent_id",agentid);
            jsonObject.put("customer_name",nameStr);
            jsonObject.put("mobile_no",mobilenumberStr);
            jsonObject.put("date_time", dateStr);
            jsonObject.put("pick_up_point", pick_up_point);
            jsonObject.put("no_of_pass", noofPassStr);
            //jsonObject.put("vehicle_id", VeichleId);
            jsonObject.put("projects", jsonArray);
            jsonObject.put("address", addressStr);
            //  jsonObject.put("projects", jsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**/
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<AddCustomerResponse>> call = apiInterface.getAddCustomer(agentid,nameStr,mobilenumberStr,dateStr,noofPassStr,selectedList,addressStr,userid,ibaNameStr,ibaNumberStr,timeStr);
        Log.e("customerapi==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<AddCustomerResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AddCustomerResponse>> call, Response<ArrayList<AddCustomerResponse> >response) {

                if (response.body() != null && response.code() == 200) {
                    ArrayList<AddCustomerResponse> addCustomerResponse = response.body();
                    if (addCustomerResponse.size() > 0) {
                        for (int i = 0; i < addCustomerResponse.size(); i++) {
                          //  SuccessAlertDialog("Added Customer");

                          //  MySharedPreferences.setPreference(CPTravel_Request.this, "" + AppConstants.MAIN_PAGE_REFRESH, "" + AppConstants.YES);
                             Utilities.showToast(CPTravel_Request.this, "Travel Request Added");
                           // finish();
                            backPressedAnimation();

                            // SuccessAlertDialog("Added Travel Request");
                        }


                    }else {
                       Utilities.showToast(CPTravel_Request.this, "Lead Already Exist..!");
                    }
                } else {
                    Utilities.showToast(CPTravel_Request.this, "Error : Lead Not Created");
                }

            }




            @Override
            public void onFailure(Call<ArrayList<AddCustomerResponse> >call, Throwable t) {
                Utilities.removeSimpleProgressDialog();
                  Utilities.showToast(CPTravel_Request.this, "Error : " + t.getMessage());
            }
        });
    }

   /* private void SuccessAlertDialog(String msg) {
       MySharedPreferences.setPreference(this, "" + AppConstants.PAGE_REFRESH, "" + AppConstants.YES);
        final Dialog dialog = new Dialog(CPTravel_Request.this);
        dialog.setContentView(R.layout.animated_success_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        TextView dataTVID = dialog.findViewById(R.id.dataTVID);

        dataTVID.setText("" + msg);

        mPlayer = MediaPlayer.create(CPTravel_Request.this, R.raw.ringtone);
        mPlayer.start();

        Button okTVID = dialog.findViewById(R.id.okTVID);
        okTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                backPressedAnimation();
            }
        });

    }*/

  /*
    private void showActivitiesAlert(String project) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.search_drop_down_view);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.show();
        dropDownLVID = dialog.findViewById(R.id.dropDownLVID);
        dropDownLVID.setExpanded(true);
        dropDownLVID.setFocusable(false);

        drPB = dialog.findViewById(R.id.drPB);
        drPB.setVisibility(View.GONE);
        final EditText searchHintETID = dialog.findViewById(R.id.searchHintETID);
        final LinearLayout searchLLID = dialog.findViewById(R.id.searchLLID);
        searchLLID.setFocusable(false);


     */
/*   if (Utilities.isNetworkAvailable(this)) {

            if (project.equalsIgnoreCase("VEICHLE")) {
               // callVeichle();
            } else if (project.equalsIgnoreCase("PROJECT")) {
              //  callProject();
            }
        } else {
            Utilities.showToast(this, "No Internet connection");
        }*//*



       */
/* searchHintETID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int i, int i1, int i2) {
                //activitiesAdapter.getFilter().filter(arg0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (project.equalsIgnoreCase("VEICHLE")) {
                    String te = searchHintETID.getText().toString();
                    veichleAdapter.getFilter().filter(te);
                } else if (project.equalsIgnoreCase("PROJECT")) {
                    String te = searchHintETID.getText().toString();
                    requirementAdapter.getFilter().filter(te);
                }
            }
        });*//*

    }
*/

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CPTravel_Request.this, CPTravel_Request.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

        String AM_PM ;
        if(hourOfDay < 12) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }

        String minutes;

        if(minute < 10) {
            minutes = ":0";
        } else {
            minutes = ":";
        }

        timeTVID.setText(hourOfDay + minutes + minute + " " + AM_PM );

        timeStr = timeTVID.getText().toString();

        /*if (myMinute < 10) {
            timeTVID.setText(myHour + ":0" + myMinute);
        } else {
            timeTVID.setText(myHour + ":" + myMinute);
        }*/

       /* dateETID.setText("" + myYear +
                "-" + myMonth +
                "-" + myday +
                " " + myHour +
                ":" + myMinute);*/
    }
}