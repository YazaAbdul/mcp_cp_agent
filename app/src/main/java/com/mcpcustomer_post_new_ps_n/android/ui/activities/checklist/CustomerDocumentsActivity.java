package com.mcpcustomer_post_new_ps_n.android.ui.activities.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.ViewImageActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DocumentsObj;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.FileProvider.getUriForFile;

public class CustomerDocumentsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout mainViewLLID;
    RelativeLayout familyPicRLID, passportPicRLID, idPicRLID, addressPicRLID, pdcPicRLID, formPicRLID, requisitionPicRLID;
    //image upload
    private String selectImagePath = "null";
    Uri selectImageUri;
    private static final int PICK_IMAGE = 100;
    private static final int PERMISSION_STORAGE = 2;

    String picFrom;
    ProgressBar familyPB, passportPB, idPB, addressPB, pdcPB, formPB, requisitionPB;
    AppCompatImageView familyVerificationIMVID, passportVerificationIMVID, idVerificationIMVID, addressVerificationIMVID, pdcVerificationIMVID, formVerificationIMVID, requisitionVerificationIMVID;

    String applicationStatus, customerIdStr, familyStr, passportStr, idStr, addressStr, pdcStr, formStr, requiaitionStr;
    AppCompatButton submitBtn;

    CardView familyCVID, passportCVID, idCVID, addressCVID, pdcCVID, formCVID, requisitionCVID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_documents);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                applicationStatus = bundle.getString("" + AppConstants.CUSTOMER_APPLICATION_STATUS);
            }
        }

        Utilities.startAnimation(this);

        AppCompatTextView headerTitleTVID = findViewById(R.id.headerTitleTVID);
        headerTitleTVID.setText("Documents");

        RelativeLayout headerBackRLID = findViewById(R.id.headerBackRLID);
        headerBackRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.finishAnimation(CustomerDocumentsActivity.this);
                MySharedPreferences.setPreference(CustomerDocumentsActivity.this,""+AppConstants.BACK_FINISH,""+AppConstants.BACK_YES);
            }
        });

        customerIdStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.CUSTOMER_ID);
        familyStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.FAMILY_PHOTO);
        passportStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.PASSPORT_PHOTO);
        addressStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.ADDRESS_PROOF);
        pdcStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.PDC_PHOTO);
        formStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.FORM_32A_PHOTO);
        requiaitionStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.REQUISITION_FORM);
        idStr = MySharedPreferences.getPreferences(CustomerDocumentsActivity.this, "" + AppConstants.ID_PROOF);


        mainViewLLID = findViewById(R.id.mainViewLLID);
        familyPicRLID = findViewById(R.id.familyPicRLID);
        passportPicRLID = findViewById(R.id.passportPicRLID);
        idPicRLID = findViewById(R.id.idPicRLID);
        addressPicRLID = findViewById(R.id.addressPicRLID);
        pdcPicRLID = findViewById(R.id.pdcPicRLID);
        formPicRLID = findViewById(R.id.formPicRLID);
        requisitionPicRLID = findViewById(R.id.requisitionPicRLID);
        submitBtn = findViewById(R.id.submitBtn);

        familyCVID = findViewById(R.id.familyCVID);
        passportCVID = findViewById(R.id.passportCVID);
        idCVID = findViewById(R.id.idCVID);
        addressCVID = findViewById(R.id.addressCVID);
        pdcCVID = findViewById(R.id.pdcCVID);
        formCVID = findViewById(R.id.formCVID);
        requisitionCVID = findViewById(R.id.requisitionCVID);

        familyCVID.setOnClickListener(this);
        passportCVID.setOnClickListener(this);
        idCVID.setOnClickListener(this);
        addressCVID.setOnClickListener(this);
        pdcCVID.setOnClickListener(this);
        formCVID.setOnClickListener(this);
        requisitionCVID.setOnClickListener(this);

        familyPicRLID.setOnClickListener(this);
        passportPicRLID.setOnClickListener(this);
        idPicRLID.setOnClickListener(this);
        addressPicRLID.setOnClickListener(this);
        pdcPicRLID.setOnClickListener(this);
        formPicRLID.setOnClickListener(this);
        requisitionPicRLID.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        familyPB = findViewById(R.id.familyPB);
        passportPB = findViewById(R.id.passportPB);
        idPB = findViewById(R.id.idPB);
        addressPB = findViewById(R.id.addressPB);
        pdcPB = findViewById(R.id.pdcPB);
        formPB = findViewById(R.id.formPB);
        requisitionPB = findViewById(R.id.requisitionPB);

        familyVerificationIMVID = findViewById(R.id.familyVerification);
        passportVerificationIMVID = findViewById(R.id.passportVerification);
        idVerificationIMVID = findViewById(R.id.idVerification);
        addressVerificationIMVID = findViewById(R.id.addressVerification);
        pdcVerificationIMVID = findViewById(R.id.pdcVerification);
        formVerificationIMVID = findViewById(R.id.formVerificationIMVID);
        requisitionVerificationIMVID = findViewById(R.id.requisitionVerification);

        displayImagesData();

    }

    private void displayImagesData() {

        if (!familyStr.equals("")) {
            familyVerificationIMVID.setVisibility(View.VISIBLE);
        }

        if (!passportStr.equals("")) {
            passportVerificationIMVID.setVisibility(View.VISIBLE);
        }

        if (!addressStr.equals("")) {
            addressVerificationIMVID.setVisibility(View.VISIBLE);
        }

        if (!pdcStr.equals("")) {
            pdcVerificationIMVID.setVisibility(View.VISIBLE);
        }
        if (!formStr.equals("")) {
            formVerificationIMVID.setVisibility(View.VISIBLE);
        }

        if (!requiaitionStr.equals("")) {
            requisitionVerificationIMVID.setVisibility(View.VISIBLE);
        }

        if (!idStr.equals("")) {
            idVerificationIMVID.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.familyPicRLID:
                picFrom = "family";
                showAlert("family");
                break;

            case R.id.passportPicRLID:
                picFrom = "passport";
                showAlert("passport");
                break;
            case R.id.idPicRLID:
                picFrom = "id";
                showAlert("id");
                break;
            case R.id.addressPicRLID:
                picFrom = "address";
                showAlert("address");
                break;
            case R.id.pdcPicRLID:
                picFrom = "pdc";
                showAlert("pdc");
                break;
            case R.id.formPicRLID:
                picFrom = "form";
                showAlert("form");
                break;
            case R.id.requisitionPicRLID:
                picFrom = "requisition";
                showAlert("requisition");
                break;

            case R.id.submitBtn:
                Utilities.finishAnimation(this);
                break;

            case R.id.familyCVID:
                if (!familyStr.equals("")) {
                    Intent intent = new Intent(this, ViewImageActivity.class);
                    intent.putExtra("TITLE", "Family Photo Graph");
                    intent.putExtra("PIC_URL", "" + familyStr);
                    intent.putExtra("PIC_TYPE", "1");
                    intent.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                    intent.putExtra("" + AppConstants.IS_PIC_FROM, "");
                    startActivity(intent);
                }
                break;

            case R.id.passportCVID:
                if (!passportStr.equals("")) {
                    Intent intent2 = new Intent(this, ViewImageActivity.class);
                    intent2.putExtra("TITLE", "Passport Photo");
                    intent2.putExtra("PIC_URL", "" + passportStr);
                    intent2.putExtra("PIC_TYPE", "2");
                    intent2.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                    intent2.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                    startActivity(intent2);
                }
                break;

            case R.id.idCVID:
                if (!idStr.equals("")) {
                    Intent intent3 = new Intent(this, ViewImageActivity.class);
                    intent3.putExtra("TITLE", "ID Proof(PAN)");
                    intent3.putExtra("PIC_URL", "" + idStr);
                    intent3.putExtra("PIC_TYPE", "3");
                    intent3.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                    intent3.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                    startActivity(intent3);
                }
                break;

            case R.id.addressCVID:
                if (!addressStr.equals("")) {
                    Intent intent4 = new Intent(this, ViewImageActivity.class);
                    intent4.putExtra("TITLE", "Address Proof (Aadhar)");
                    intent4.putExtra("PIC_URL", "" + addressStr);
                    intent4.putExtra("PIC_TYPE", "4");
                    intent4.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                    intent4.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                    startActivity(intent4);
                }
                break;

            case R.id.pdcCVID:
                if (!pdcStr.equals("")) {
                    Intent intent5 = new Intent(this, ViewImageActivity.class);
                    intent5.putExtra("TITLE", "PDC Photo");
                    intent5.putExtra("PIC_URL", "" + pdcStr);
                    intent5.putExtra("PIC_TYPE", "5");
                    intent5.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                    intent5.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                    startActivity(intent5);
                }
                break;

            case R.id.formCVID:
                if (!formStr.equals("")) {
                    Intent intent6 = new Intent(this, ViewImageActivity.class);
                    intent6.putExtra("TITLE", "Form 32A");
                    intent6.putExtra("PIC_URL", "" + formStr);
                    intent6.putExtra("PIC_TYPE", "6");
                    intent6.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                    intent6.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                    startActivity(intent6);
                }
                break;

            case R.id.receivedRCID:
                Intent intent7 = new Intent(this, ViewImageActivity.class);
                intent7.putExtra("TITLE", "Requisition Form");
                intent7.putExtra("PIC_URL", "" + requiaitionStr);
                intent7.putExtra("PIC_TYPE", "7");
                intent7.putExtra(""+AppConstants.APPLICATION_STATUS, ""+applicationStatus);
                intent7.putExtra("" + AppConstants.IS_PIC_FROM, "" + AppConstants.IS_PIC_FROM_APPLICATION);
                startActivity(intent7);
                break;

        }
    }

    private void showAlert(String picFrom) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_image);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cameraTVID = dialog.findViewById(R.id.cameraTVID);
        TextView galleryTVID = dialog.findViewById(R.id.galleryTVID);

        cameraTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(CustomerDocumentsActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
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
                if (ContextCompat.checkSelfPermission(CustomerDocumentsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(CustomerDocumentsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CustomerDocumentsActivity.this,
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

                    if (picFrom.equalsIgnoreCase("family")) {
                        picUpload("family", familyPB, familyVerificationIMVID, "1");
                    } else if (picFrom.equalsIgnoreCase("passport")) {
                        picUpload("passport", passportPB, passportVerificationIMVID, "2");
                    } else if (picFrom.equalsIgnoreCase("id")) {
                        picUpload("id", idPB, idVerificationIMVID, "3");
                    } else if (picFrom.equalsIgnoreCase("address")) {
                        picUpload("address", addressPB, addressVerificationIMVID, "4");
                    } else if (picFrom.equalsIgnoreCase("pdc")) {
                        picUpload("pdc", pdcPB, pdcVerificationIMVID, "5");
                    } else if (picFrom.equalsIgnoreCase("form")) {
                        picUpload("form", formPB, formVerificationIMVID, "6");
                    } else if (picFrom.equalsIgnoreCase("requisition")) {
                        picUpload("requisition", requisitionPB, requisitionVerificationIMVID, "7");
                    }
                }

                break;

            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        selectImagePath = getRealPathFromURI2(tempUri);

                        if (picFrom.equalsIgnoreCase("family")) {
                            picUpload("family", familyPB, familyVerificationIMVID, "1");
                        } else if (picFrom.equalsIgnoreCase("passport")) {
                            picUpload("passport", passportPB, passportVerificationIMVID, "2");
                        } else if (picFrom.equalsIgnoreCase("id")) {
                            picUpload("id", idPB, idVerificationIMVID, "3");
                        } else if (picFrom.equalsIgnoreCase("address")) {
                            picUpload("address", addressPB, addressVerificationIMVID, "4");
                        } else if (picFrom.equalsIgnoreCase("pdc")) {
                            picUpload("pdc", pdcPB, pdcVerificationIMVID, "5");
                        } else if (picFrom.equalsIgnoreCase("form")) {
                            picUpload("form", formPB, formVerificationIMVID, "6");
                        } else if (picFrom.equalsIgnoreCase("requisition")) {
                            picUpload("requisition", requisitionPB, requisitionVerificationIMVID, "7");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private void picUpload(String picFrom, final ProgressBar pb, final AppCompatImageView image, final String picType) {
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
        Call<DocumentsObj> call = apiInterface.uploadDocuments(imageBody, applicationStatus, customerIdStr, picType);
        call.enqueue(new Callback<DocumentsObj>() {
            @Override
            public void onResponse(Call<DocumentsObj> call, Response<DocumentsObj> response) {
                pb.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    DocumentsObj documentsObj = response.body();
                    if (documentsObj.getStatus() == 1) {
                        image.setVisibility(View.VISIBLE);
                        Utilities.customMessage(mainViewLLID, CustomerDocumentsActivity.this, "" + documentsObj.getMsg());
                    } else {
                        Utilities.customMessage(mainViewLLID, CustomerDocumentsActivity.this, "" + documentsObj.getMsg());
                        image.setVisibility(View.VISIBLE);
                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                        Utilities.customMessage(mainViewLLID, CustomerDocumentsActivity.this, "Pic not Uploaded");
                    }
                } else {
                    image.setVisibility(View.VISIBLE);
                    image.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
                    Utilities.customMessage(mainViewLLID, CustomerDocumentsActivity.this, "Pic not Uploaded");
                }
            }

            @Override
            public void onFailure(Call<DocumentsObj> call, Throwable t) {
                Utilities.customMessage(mainViewLLID, CustomerDocumentsActivity.this, "Error : " + t.getMessage());
                image.setVisibility(View.VISIBLE);
                image.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cancel_music));
            }
        });

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 0, bmpStream);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(this, getPackageName() + ".provider", image);
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
        //picTitleTVID.setText("1 File Selected");
        //Picasso.with(this).load(url).into(leadPic);
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
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
        @SuppressLint("Recycle")
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
