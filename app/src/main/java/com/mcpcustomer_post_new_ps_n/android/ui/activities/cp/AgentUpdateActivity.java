package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import static androidx.core.content.FileProvider.getUriForFile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentUpdateActivity extends AppCompatActivity {

    RelativeLayout picRLID,backRLID;
    AppCompatEditText nameETID,emailETID,mobileETID;
    AppCompatButton updateBtn;
    CircleImageView userPic;
    TextView headerTittleTVID;
    Bitmap bitmap;
    String nameStr,mobileStr,emailStr,userPicStr,agentID;
    String picUrl;
    private String selectImagePath = "null";
    Uri selectImageUri,photoURI;
    private static final int PICK_IMAGE = 100;
    private static final int PERMISSION_STORAGE = 2;
    public static final int REQUEST_IMAGE_CAPTURE_USER = 0;
    KProgressHUD hud;
    private File photoFile;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int CAMERA_PERMISSION_CODE = 102;
    private static final int REQUEST_GALLERY = 102;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    Bitmap imageBitmap;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_update);

        picRLID =findViewById(R.id.picRLID);
        backRLID =findViewById(R.id.backRLID);
        nameETID =findViewById(R.id.nameETID);
        emailETID =findViewById(R.id.emailETID);
        mobileETID =findViewById(R.id.mobileETID);
        updateBtn =findViewById(R.id.updateBtn);
        userPic =findViewById(R.id.userPic);
        headerTittleTVID =findViewById(R.id.headerTittleTVID);

        nameETID.setText(MySharedPreferences.getPreferences(this,""+ AppConstants.CP_USER_NAME));
        mobileETID.setText(MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_NUMBER));
        emailETID.setText(MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_EMAIL));

    //    userPicStr =MySharedPreferences.getPreferences(this,""+AppConstants.CP_PROFILE_PIC);
        userPicStr =MySharedPreferences.getPreferences(this,""+AppConstants.CP_PROFILE_PIC_N);

        agentID =MySharedPreferences.getPreferences(this,""+AppConstants.CP_USER_ID);

        Log.e("USER_PIC==>", "" + userPicStr);

        try {
            Picasso.get().load(userPicStr).placeholder(R.drawable.userpic).into(userPic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerTittleTVID.setText("Update Details");


        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(this, "" + AppConstants.CP_VIEW, "YES");
        });

        picRLID.setOnClickListener(v -> {
            alertDisplay();
        });

        updateBtn.setOnClickListener(v -> {
            validation();
        });
    }

    private void validation() {
        nameStr = nameETID.getText().toString();
        emailStr = emailETID.getText().toString();
        mobileStr = mobileETID.getText().toString();
        
        if (Utilities.isNetworkAvailable(this)){
            updateDetails(nameStr,emailStr,mobileStr);
        }
    }



    private void alertDisplay() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_image);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cameraTVID = dialog.findViewById(R.id.cameraTVID);
        TextView galleryTVID = dialog.findViewById(R.id.galleryTVID);




        cameraTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgentUpdateActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request the permission
                    ActivityCompat.requestPermissions(AgentUpdateActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION);
                } else {
                    dispatchTakePictureIntent();
                }

                dialog.dismiss();
            }
        });

     /*   cameraTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {

                    dispatchTakePictureIntent();
                } else {
                    requestCameraPermission();
                }
                dialog.dismiss();
            }
        });*/


     /*   cameraTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AgentUpdateActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
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
        });*/

     /*   galleryTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AgentUpdateActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        *//*&& ContextCompat.checkSelfPermission(AgentUpdateActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED*//*) {

                    ActivityCompat.requestPermissions(AgentUpdateActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE*//*, android.Manifest.permission.WRITE_EXTERNAL_STORAGE*//*},
                            PERMISSION_STORAGE);
                } else {
                    openImage();
                }

                dialog.dismiss();
            }
        });*/


        galleryTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                if (ContextCompat.checkSelfPermission(AgentUpdateActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(AgentUpdateActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AgentUpdateActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_STORAGE);
                } else {
                    // openImage();
                    showOptionsDialog();
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


    private void showOptionsDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        Intent chooserIntent = Intent.createChooser(intent, "Select Image");
        startActivityForResult(chooserIntent, REQUEST_GALLERY);
    }
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }

       /* Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            String photoFile = null;
            try {
                photoFile = String.valueOf(createImageFile());
                Log.e("api==>", "photoFile"+photoFile);
            } catch (IOException ex) {
                Log.e("MainActivity", "Error creating image file: " + ex.getMessage());
            }
            if (photoFile != null) {
                 photoURI = Utilities.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(photoFile));
                Log.e("api==>", "photoFile"+photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }


        }*/
    }
        private File createImageFile() throws IOException {
          /*  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            return File.createTempFile(imageFileName, ".jpg", storageDir);
*/
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(null);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            //currentPhotoPath = image.getAbsolutePath();
            selectImagePath = image.getAbsolutePath();
            return image;

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


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                //  Toast.makeText(this, "Image captured from camera", Toast.LENGTH_SHORT).show();
                if (data != null && data.getExtras() != null) {
                    imageBitmap = (Bitmap) data.getExtras().get("data");
                    if (imageBitmap != null) {
                        userPic.setImageBitmap(imageBitmap);
                        // Send the imageBitmap as a response
                        //   sendImageResponse(imageBitmap);
                    }
                }
            } else if (requestCode == REQUEST_GALLERY) {
                //   Toast.makeText(this, "Image selected from gallery", Toast.LENGTH_SHORT).show();
                if (data != null && data.getData() != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        imageBitmap = BitmapFactory.decodeStream(inputStream);
                        if (imageBitmap != null) {
                            userPic.setImageBitmap(imageBitmap);
                            // Send the imageBitmap as a response
                            //    sendImageResponse(imageBitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Action canceled", Toast.LENGTH_SHORT).show();
        }


        /*if(requestCode==PICK_IMAGE){
            if (resultCode == Activity.RESULT_OK) {
               // Toast.makeText(this, "bitmap20", Toast.LENGTH_SHORT).show();
                selectImageUri = data.getData();
                selectImagePath = getRealPathFromURI(selectImageUri);
                loadProfile(selectImageUri.toString());
                //picTitleTVID.setText("1 Image selected");
                //decodeImage(selectImagePath);
            }

        }else{
            try {
              //  Toast.makeText(this, "bitmap11", Toast.LENGTH_SHORT).show();
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                Log.e("api==>", "bitmap:"+bitmap);
                selectImageUri = data.getData();
                if (bitmap != null) {
                    userPic.setImageBitmap(bitmap);
                }
               // bitmap = (Bitmap) data.getExtras().get("data");


                //userPic.setImageBitmap(bitmap);
                setPic();

                selectImagePath = getRealPathFromURI(photoURI);
                loadProfile(selectImageUri.toString());
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                //selectImagePath = String.valueOf(finalFile);
              //  selectImagePath =  photoFile.getAbsolutePath();
                Log.e("api==>", "selectImagePath:"+selectImagePath);
                //loadProfile(imageUri.toString());
                //selectImagePath = getRealPathFromURI2(yourUri);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            /*Toast.makeText(this, "bitmap0", Toast.LENGTH_SHORT).show();
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Toast.makeText(this, "bitmap", Toast.LENGTH_SHORT).show();
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        userPic.setImageBitmap(imageBitmap);
                    }
                }
            }*/


        }

       /* switch (requestCode) {

            case PICK_IMAGE:
                Toast.makeText(this, "bitmap10", Toast.LENGTH_SHORT).show();
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "bitmap20", Toast.LENGTH_SHORT).show();
                    selectImageUri = data.getData();
                    selectImagePath = getRealPathFromURI(selectImageUri);
                    loadProfile(selectImageUri.toString());
                    //picTitleTVID.setText("1 Image selected");
                    //decodeImage(selectImagePath);
                }
                break;

            case 0:

                Toast.makeText(this, "bitmap0", Toast.LENGTH_SHORT).show();

               if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                   Toast.makeText(this, "bitmap", Toast.LENGTH_SHORT).show();
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        if (imageBitmap != null) {
                            userPic.setImageBitmap(imageBitmap);
                        }
                    }
                }

               *//* if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "bitmap", Toast.LENGTH_SHORT).show();
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Log.e("api==>", "bitmap:"+bitmap);

                        userPic.setImageBitmap(bitmap);

                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        selectImagePath = String.valueOf(finalFile);

                        //loadProfile(imageUri.toString());
                        //selectImagePath = getRealPathFromURI2(yourUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
*//*
                break;

        }*/



    private void setPic() {
        // Get the dimensions of the ImageView
        int targetW = userPic.getWidth();
        int targetH = userPic.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectImagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the ImageView
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(selectImagePath, bmOptions);
        userPic.setImageBitmap(bitmap);
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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

        picUrl=url;

        Log.d("api==>", "Image cache path: " + url);
        //picTitleTVID.setText("1 File Selected");

        Picasso.get().load(url).rotate(0).into(userPic);


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
        /*Cursor cursor = getContentResolver().query(selectImageUri, null, null, null, null);
        if (cursor == null) {
            return selectImageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }*/

        Cursor cursor = getContentResolver().query(selectImageUri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(this, getPackageName() + ".provider", image);
    }

    private void updateDetails(String nameStr, String emailStr, String mobileStr) {

       /* RequestBody reqFile;
         MultipartBody.Part imageBody;
        File file = new File(selectImagePath);*/


    /*    //newcode add
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // Create a request body for the image file
        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/png"), byteArray);
        // Create multipart body part for the image
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("upload_pic", "image.png", imageRequestBody);
//up to here*/

/*
        if (selectImagePath.equalsIgnoreCase("null")) {

            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageBody = MultipartBody.Part.createFormData("upload_pic", "", reqFile);
        } else {

            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageBody = MultipartBody.Part.createFormData("upload_pic", file.getName(), reqFile);
        }

        Log.e("IMAGE==>", "" + imageBody);
        Log.e("IMAGE==>", "" + selectImagePath);*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), byteArray);
        // Create MultipartBody.Part from RequestBody
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("upload_pic", "image.jpg", requestBody);


        ApiInterface apiInterface  = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<StatusResponseNew>> call = apiInterface.updateAgentProfile(imagePart,agentID,nameStr,emailStr,mobileStr);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<StatusResponseNew>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseNew>> call, Response<ArrayList<StatusResponseNew>> response) {
                if (response.body() != null && response.code() == 200){
                    ArrayList<StatusResponseNew> statusResponseNew = response.body();

                    if (statusResponseNew.get(0).getStatus().equalsIgnoreCase("1")){
                        MySharedPreferences.setPreference(AgentUpdateActivity.this,""+AppConstants.CP_PROFILE_PIC,""+picUrl);
                        MySharedPreferences.setPreference(AgentUpdateActivity.this,""+AppConstants.CP_PROFILE_PIC_N,""+statusResponseNew.get(0).getUpload_pic());
                        Log.e("api==>","picUrl"+picUrl);
                        MySharedPreferences.setPreference(AgentUpdateActivity.this,""+AppConstants.CP_USER_ID,agentID);
                        Toast.makeText(AgentUpdateActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(AgentUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(AgentUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseNew>> call, Throwable t) {
                Toast.makeText(AgentUpdateActivity.this, "Errorr", Toast.LENGTH_SHORT).show();
            }
        });
    }
}