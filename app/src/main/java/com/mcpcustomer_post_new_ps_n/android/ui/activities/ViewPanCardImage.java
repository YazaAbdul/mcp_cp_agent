package com.mcpcustomer_post_new_ps_n.android.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DocumentsObj;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mcpcustomer_post_new_ps_n.android.data.Utilities.*;

public class ViewPanCardImage extends AppCompatActivity  implements View.OnClickListener {

    LinearLayout bottomLLID;
    String title, picUrl, applicationStatus, picType, picFrom, customerIDStr;
    AppCompatButton cancelBtn, updateBtn;
    AppCompatImageView imageID;

    private String selectImagePath = "null";
    Uri selectImageUri;
    private static final int PICK_IMAGE = 100;
    private static final int PERMISSION_STORAGE = 2;
    LinearLayout mainViewLLID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_view_pan_card_image);

        startAnimation(this);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                title = bundle.getString("TITLE");
                picUrl = bundle.getString("PIC_URL");
                picType = bundle.getString("PIC_TYPE");
                applicationStatus = bundle.getString("" + AppConstants.APPLICATION_STATUS);
                picFrom = bundle.getString("" + AppConstants.IS_PIC_FROM);
            }
        }
        bottomLLID = findViewById(R.id.bottomLLID);
        customerIDStr = MySharedPreferences.getPreferences(this, "" + AppConstants.CUSTOMER_ID);


        RelativeLayout headerBackRLID = findViewById(R.id.headerBackRLID);
        headerBackRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAnimation(ViewPanCardImage.this);
            }
        });

        AppCompatTextView headerTitleTVID = findViewById(R.id.headerTitleTVID);
        headerTitleTVID.setText(title);

        mainViewLLID = findViewById(R.id.mainViewLLID);
        imageID = findViewById(R.id.imageID);
        updateBtn = findViewById(R.id.updateBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);


        if (picUrl.equals("")) {
            Picasso.get().load(R.drawable.image_not_found).into(imageID);
        } else {

            Picasso.get().load(picUrl).error(R.drawable.crmappicon).into(imageID);
        }


        if (picFrom.equals(AppConstants.IS_PIC_FROM_APPLICATION)) {
            bottomLLID.setVisibility(View.VISIBLE);
        } else {
            bottomLLID.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAnimation(this);
    }

    private void documentUpdate(String picFrom, final ProgressBar pb, final AppCompatImageView image, final String picType) {
        pb.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);

        RequestBody reqFile;
        final MultipartBody.Part imageBody;

        File file = new File(selectImagePath);

        if (selectImagePath.equalsIgnoreCase("null")) {
            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageBody = MultipartBody.Part.createFormData("upload_file", "", reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageBody = MultipartBody.Part.createFormData("upload_file", file.getName(), reqFile);
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DocumentsObj> call = apiInterface.uploadDocuments(imageBody, applicationStatus, customerIDStr, picType);
        call.enqueue(new Callback<DocumentsObj>() {
            @Override
            public void onResponse(Call<DocumentsObj> call, Response<DocumentsObj> response) {
                pb.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    DocumentsObj documentsObj = response.body();
                    if (documentsObj.getStatus() == 1) {
                        image.setVisibility(View.VISIBLE);
                        customMessage(mainViewLLID, ViewPanCardImage.this, "" + documentsObj.getMsg());
                    } else {
                        customMessage(mainViewLLID, ViewPanCardImage.this, "" + documentsObj.getMsg());
                        image.setVisibility(View.VISIBLE);
                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        customMessage(mainViewLLID, ViewPanCardImage.this, "Pic not Uploaded");
                    }
                } else {
                    image.setVisibility(View.VISIBLE);
                    image.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                    customMessage(mainViewLLID, ViewPanCardImage.this, "Pic not Uploaded");
                }
            }

            @Override
            public void onFailure(Call<DocumentsObj> call, Throwable t) {
                customMessage(mainViewLLID, ViewPanCardImage.this, "Error : " + t.getMessage());
                image.setVisibility(View.VISIBLE);
                image.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
            }
        });

    }


    private void picUpload() {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(this, null, "Uploading...");
        progressDialog.show();
        RequestBody reqFile;
        final MultipartBody.Part imageBody;

        File file = new File(selectImagePath);

        if (selectImagePath.equalsIgnoreCase("null")) {
            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageBody = MultipartBody.Part.createFormData("id_proof", "", reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageBody = MultipartBody.Part.createFormData("id_proof", file.getName(), reqFile);
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DocumentsObj> call = apiInterface.uploadpancard(imageBody,  customerIDStr);
        call.enqueue(new Callback<DocumentsObj>() {
            @Override
            public void onResponse(Call<DocumentsObj> call, Response<DocumentsObj> response) {
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    DocumentsObj documentsObj = response.body();
                    if (documentsObj.getStatus() == 1) {
                        finishAnimation(ViewPanCardImage.this);
                        MySharedPreferences.setPreference(ViewPanCardImage.this, "" + AppConstants.REFRESH_PAGE, "YES");
                        //Utilities.customMessage(mainViewLLID, ViewImageActivity.this, "" + documentsObj.getMsg());
                        Toast.makeText(ViewPanCardImage.this, "" + documentsObj.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        customMessage(mainViewLLID, ViewPanCardImage.this, "" + documentsObj.getMsg());
                        customMessage(mainViewLLID, ViewPanCardImage.this, "Pic not Uploaded");
                    }
                } else {
                    customMessage(mainViewLLID, ViewPanCardImage.this, "Pic not Uploaded");
                }
            }

            @Override
            public void onFailure(Call<DocumentsObj> call, Throwable t) {
                progressDialog.dismiss();
                customMessage(mainViewLLID, ViewPanCardImage.this, "Error : " + t.getMessage());
            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelBtn:
                finishAnimation(this);
                break;

            case R.id.updateBtn:
                String update = updateBtn.getText().toString();
                if (update.equalsIgnoreCase("Upload")) {
                    if (picFrom.equalsIgnoreCase("" + AppConstants.IS_PIC_FROM_APPLICATION)) {
                        picUpload();

                        Toast.makeText(this, "Uploading application " + title, Toast.LENGTH_SHORT).show();
                    } else {
                        picUpload();
                        //Toast.makeText(this, "Uploading banker " + title, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showAlert();
                }
                break;
        }
    }

    private void showAlert() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_image);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cameraTVID = dialog.findViewById(R.id.cameraTVID);
        TextView galleryTVID = dialog.findViewById(R.id.galleryTVID);

        cameraTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(ViewPanCardImage.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
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
                dialog.dismiss();
            }
        });

        galleryTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ViewPanCardImage.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(ViewPanCardImage.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ViewPanCardImage.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_STORAGE);
                } else {
                    openImage();
                }

                dialog.dismiss();
            }
        });

        TextView closeTVID = dialog.findViewById(R.id.closeTVID);
        closeTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void image() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("IntentReset")
    private void openImage() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, PICK_IMAGE);
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
                    loadProfile(selectImageUri.toString());

                }
                break;

            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        selectImagePath = getRealPathFromURI2(tempUri);
                        loadProfile(tempUri.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
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
        Picasso.get().load(url).into(imageID);
        updateBtn.setText("Upload");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImage();
                }

                return;
            }
        }
    }

    private String getRealPathFromURI(Uri selectImageUri) {
        Cursor cursor = getContentResolver().query(selectImageUri, null, null, null, null);
        if (cursor == null) {
            return selectImageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
