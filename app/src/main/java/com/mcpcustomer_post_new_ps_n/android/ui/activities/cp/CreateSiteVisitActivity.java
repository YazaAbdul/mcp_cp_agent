 package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

 import android.Manifest;
 import android.annotation.SuppressLint;
 import android.app.Activity;
 import android.content.Context;
 import android.content.Intent;
 import android.database.Cursor;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.location.Location;
 import android.net.Uri;
 import android.os.Bundle;
 import android.provider.MediaStore;

 import android.text.Editable;
 import android.text.TextUtils;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.RelativeLayout;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.AppCompatImageView;
 import androidx.appcompat.widget.AppCompatSpinner;


 import com.mcpcustomer_post_new_ps_n.android.R;
 import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
 import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
 import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
 import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
 import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
 import com.mcpcustomer_post_new_ps_n.android.ui.adapter.LeadActivitiesAdapter;
 import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse;
 import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;
 import com.kaopiz.kprogresshud.KProgressHUD;
 import com.karumi.dexter.Dexter;
 import com.karumi.dexter.MultiplePermissionsReport;
 import com.karumi.dexter.PermissionToken;
 import com.karumi.dexter.listener.PermissionRequest;
 import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
 import com.squareup.picasso.Picasso;

 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;
 import de.hdodenhof.circleimageview.CircleImageView;
 import okhttp3.MediaType;
 import okhttp3.MultipartBody;
 import okhttp3.RequestBody;
 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;

 public class CreateSiteVisitActivity extends AppCompatActivity {

     LinearLayout mainViewRLID;
     LinearLayout textDisplayTVID;
     TextView locationTVID;

     RelativeLayout okRLID;
     String logoutStatus;

     //location
     Location location;

     String userID;
     String nameStr, mobileStr, ibaNameStr,ibaNumberStr,projectID;


     private CircleImageView img_icon;
     private String selectImagePath = "null";
     Uri selectImageUri;

     private static final int PICK_IMAGE = 100;


     EditText etCompanyName, etContactNumber, etCustomerName,ibaContactETID;
     private Button btnSubmit;

     String submitType = "0";
     String lead_source_type = "0";
     StatusResponseNew directModelResponse;
     KProgressHUD kProgressHUD;
     RelativeLayout imageRLID;
     AppCompatImageView imageID;
     AppCompatSpinner projectSPID;

     private ArrayList<ProjectsResponse> projectsResponse = new ArrayList<>();
     LeadActivitiesAdapter leadActivitiesAdapter;
     LinearLayout mainLLID;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_create_site_visit);


         etCompanyName = findViewById(R.id.etCompanyName);
         mainLLID = findViewById(R.id.mainLLID);
         etContactNumber = findViewById(R.id.etContactNumber);
         etCustomerName = findViewById(R.id.etCustomerName);
         ibaContactETID = findViewById(R.id.ibaContactETID);
         btnSubmit = findViewById(R.id.btnSubmit);
         imageRLID = findViewById(R.id.imageRLID);
         imageID = findViewById(R.id.imageID);
         projectSPID = findViewById(R.id.projectSPID);


         Utilities.startAnimation(this);
         RelativeLayout backRLID = findViewById(R.id.backRLID);
         backRLID.setOnClickListener(v -> {
             Utilities.finishAnimation(CreateSiteVisitActivity.this);
             MySharedPreferences.setPreference(CreateSiteVisitActivity.this, "" + AppConstants.CP_LOGIN_STATUS, "10");
             MySharedPreferences.setPreference(CreateSiteVisitActivity.this, "" + AppConstants.CP_VIEW, "YES");
         });

         userID = MySharedPreferences.getPreferences(CreateSiteVisitActivity.this, "" + AppConstants.CP_USER_ID);


         okRLID = findViewById(R.id.okRLID);

         etContactNumber.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }


             @Override
             public void afterTextChanged(Editable s) {
                 String number = s.toString();
                 if (s.length() == 10) {
                     //searchCustomer(number);
                 }
             }
         });

         img_icon = findViewById(R.id.img_icon);

         okRLID.setOnClickListener(v -> {

             validations();

         });
        

         imageRLID.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (Utilities.isNetworkAvailable(CreateSiteVisitActivity.this)) {
                     Dexter.withActivity(CreateSiteVisitActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                         @Override
                         public void onPermissionsChecked(MultiplePermissionsReport report) {
                             if (report.areAllPermissionsGranted()) {
                                 image();
                             }
                         }

                         @Override
                         public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                             token.continuePermissionRequest();
                         }

                     }).check();

                    // alertDisplay();
                 } else {
                     //AlertUtilities.bottomDisplayStaticAlert(LeadCreateActivity.this, "No Internet connection..", "Make sure your device is connected to internet");
                 }
             }
         });

         if (Utilities.isNetworkAvailable(this)){
             loadProjects();
         }else {
             Toast.makeText(this, "Projects Not Loading..", Toast.LENGTH_SHORT).show();
         }

         projectSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 projectID = projectsResponse.get(position).getProject_id();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });


     }



     public void showProgress(String msg) {
         kProgressHUD = KProgressHUD.create(this)
                 .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                 .setLabel(msg)
                 .setDetailsLabel("Please wait")
                 .setCancellable(false)
                 .setAnimationSpeed(2)
                 .setDimAmount(0.5f)
                 .show();
     }

     public void dismissProgress() {
         kProgressHUD.dismiss();
     }


     private void validations() {
         nameStr = etCustomerName.getText().toString();
         mobileStr = etContactNumber.getText().toString();
         ibaNameStr = etCompanyName.getText().toString();
         ibaNumberStr = ibaContactETID.getText().toString();

         // passwordStr = passwordETID.getText().toString();

         if (TextUtils.isEmpty(nameStr)) {
             Utilities.showToast( this, "Enter customer name");
             return;
         }


         if (TextUtils.isEmpty(mobileStr)) {
             Utilities.showToast(this, "Enter mobile number");
             return;
         }

         if (mobileStr.length() <= 9) {
             Utilities.showToast(this, "Enter valid mobile number");
             return;
         }

         /*if (TextUtils.isEmpty(ibaNameStr)) {
             Utilities.showToast( this, "Enter IBA Name");
             return;
         }

         if (TextUtils.isEmpty(ibaNumberStr)) {
             Utilities.showToast( this, "Enter IBA Number");
             return;
         }*/


         if (selectImagePath.equalsIgnoreCase("null")) {

             Utilities.showToast( this, "Upload Image");
             return;


         }else {

         }

         if (Utilities.isNetworkAvailable(this)) {
             calldirectmeetingApi(nameStr, mobileStr, ibaNameStr,ibaNumberStr);
         }else {
             Toast.makeText(this, "Please check your network", Toast.LENGTH_SHORT).show();
         }


     }

     private void loadProjects() {
         ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
         Call<ArrayList<ProjectsResponse>> call = apiInterface.projectsApi();
         projectsResponse.add(new ProjectsResponse("1111", "Select Project"));
         Log.e("api==>",call.request().toString());
         call.enqueue(new Callback<ArrayList<ProjectsResponse>>() {
             @Override
             public void onResponse(Call<ArrayList<ProjectsResponse>> call, Response<ArrayList<ProjectsResponse>> response) {
                 if (response.body() != null && response.code() == 200) {
                     projectsResponse = response.body();

                     leadActivitiesAdapter = new LeadActivitiesAdapter(CreateSiteVisitActivity.this, R.layout.cp_custom_spinner_view, projectsResponse);
                     projectSPID.setAdapter(leadActivitiesAdapter);

                 } else {
                     Utilities.customMessage(mainLLID, CreateSiteVisitActivity.this, "Projects not loading");
                 }
             }

             @Override
             public void onFailure(Call<ArrayList<ProjectsResponse>> call, Throwable t) {
                 Utilities.customMessage(mainLLID, CreateSiteVisitActivity.this, "Projects not loading");
             }
         });
     }

     @Override
     protected void onResume() {
         super.onResume();
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         Utilities.finishAnimation(this);
     }

     @SuppressLint("IntentReset")
     private void openImage() {
         Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         pickIntent.setType("image/*");
         startActivityForResult(pickIntent, PICK_IMAGE);
     }


     private void image() {
         try {
             Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
             startActivityForResult(intent, 0);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     @SuppressLint("SetTextI18n")
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         switch (requestCode) {

             case PICK_IMAGE:
                 if (resultCode == Activity.RESULT_OK) {
                     selectImageUri = data.getData();
                     selectImagePath = getRealPathFromURI(selectImageUri);
                     decodeImage(selectImagePath);
                     //changeProfilePic(selectImagePath);
                 }
                 break;

             case 0:
                 if (resultCode == Activity.RESULT_OK) {
                     try {
                         Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                         //picTitleTVID.setText("1 Image Captured");
                         Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                         loadProfile(tempUri.toString());
                         selectImagePath = getRealPathFromURI2(tempUri);
                         //changeProfilePic(selectImagePath);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }


         }
     }

     public Uri getImageUri(Context inContext, Bitmap inImage) {
         ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
         inImage.compress(Bitmap.CompressFormat.JPEG, 0, bmpStream);
         String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
         return Uri.parse(path);
     }




     public String getRealPathFromURI2(Uri uri) {
         String path = "";
         if (getContentResolver() != null) {
             Cursor cursor = getContentResolver().query(uri, null, null, null, null);
             if (cursor != null) {
                 cursor.moveToFirst();
                 int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                 path = cursor.getString(idx);
                 cursor.close();
             }
         }
         return path;
     }

     @SuppressLint("SetTextI18n")
     private void loadProfile(String url) {

         Log.d("", "Image cache path: " + url);

         Picasso.get().load(url).rotate(0).into(imageID);
     }

     private String getRealPathFromURI(Uri selectImageUri) {
         @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(selectImageUri, null, null, null, null);
         if (cursor == null) {
             return selectImageUri.getPath();
         } else {
             cursor.moveToFirst();
             int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
             return cursor.getString(idx);
         }
     }

     private void decodeImage(String selectImagePath) {
         int targetW = imageID.getWidth();
         int targetH = imageID.getHeight();

         final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
         bmOptions.inJustDecodeBounds = true;
         BitmapFactory.decodeFile(selectImagePath, bmOptions);
         int photoW = bmOptions.outWidth;
         int photoH = bmOptions.outHeight;
         int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

         bmOptions.inJustDecodeBounds = false;
         bmOptions.inSampleSize = scaleFactor;
         Bitmap bitmap = BitmapFactory.decodeFile(selectImagePath, bmOptions);
         if (bitmap != null) {
             imageID.setImageBitmap(bitmap);
         }
     }



     private void calldirectmeetingApi(String nameStr, String mobileStr, String ibaNameStr, String submitType) {

         RequestBody reqFile;
         final MultipartBody.Part imageBody;

         File file = new File(selectImagePath);

         if (selectImagePath.equalsIgnoreCase("null")) {
             reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
             imageBody = MultipartBody.Part.createFormData("upload_pic", "", reqFile);
             Log.e("api==>", String.valueOf(imageBody));
         } else {
             reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
             imageBody = MultipartBody.Part.createFormData("upload_pic", file.getName(), reqFile);
         }

         ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
         Call<StatusResponseNew> call = apiInterface.insertSiteVisitor(nameStr, mobileStr,userID,projectID,imageBody);
         Log.e("api==>", call.request().toString());
         call.enqueue(new Callback<StatusResponseNew>() {
             @Override
             public void onResponse(Call<StatusResponseNew> call, Response<StatusResponseNew> response) {
                 if (response.body() != null & response.code() == 200) {
                     directModelResponse = response.body();
                     if (directModelResponse.getStatus().equalsIgnoreCase("Inserted")) {
                         Toast.makeText(CreateSiteVisitActivity.this, "Site Visit Created Successfully", Toast.LENGTH_SHORT).show();
                         Utilities.finishAnimation(CreateSiteVisitActivity.this);
                         MySharedPreferences.setPreference(CreateSiteVisitActivity.this, "" + AppConstants.CP_LOGIN_STATUS, "10");
                         MySharedPreferences.setPreference(CreateSiteVisitActivity.this, "" + AppConstants.CP_VIEW, "YES");
                         MySharedPreferences.setPreference(CreateSiteVisitActivity.this, "" + AppConstants.PAGE_REFRESH, "" + AppConstants.YES);
                     }else {
                         Toast.makeText(CreateSiteVisitActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                     }

                 }else {
                     Toast.makeText(CreateSiteVisitActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<StatusResponseNew> call, Throwable t) {
                 Toast.makeText(CreateSiteVisitActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

             }
         });
     }


 }
