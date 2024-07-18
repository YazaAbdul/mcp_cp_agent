package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;

public class DirectingReportingActivity extends AppCompatActivity {

    WebView mWebView;
    ProgressBar loading;
    RelativeLayout backRLID;
    TextView headerTitleTVID;
    String userID,type;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directing_reporting);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                type = bundle.getString("TYPE");
            }
        }

        mWebView = findViewById(R.id.directReportingWVID);
        loading = findViewById(R.id.pb);
        backRLID = findViewById(R.id.backRLID);
        headerTitleTVID = findViewById(R.id.headerTittleTVID);
        userID = MySharedPreferences.getPreferences(this, "" + AppConstants.CP_USER_ID);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

     /*   mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(true);*/



        // Enable pinch zoom gestures
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

// Optional: Handle other WebView settings
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");






        if (type.equalsIgnoreCase("1")){
            headerTitleTVID.setText("Direct Team");
        }else if (type.equalsIgnoreCase("2")){
            headerTitleTVID.setText("Total Team");
        }else if (type.equalsIgnoreCase("3")){
            headerTitleTVID.setText("Add Associate");
        }


        backRLID.setOnClickListener(v -> {
            Utilities.finishAnimation(this);
            MySharedPreferences.setPreference(DirectingReportingActivity.this, "" + AppConstants.CP_VIEW, "YES");
        });


        if (Utilities.isNetworkAvailable(this)) {
            if (type.equalsIgnoreCase("1")) {
                renderWebPage("https://mcp.tranquilcrm.in/mobileapp/printassperfdirect?" + "agent_id=" + userID);
                loading.setVisibility(View.VISIBLE);
            }else if (type.equalsIgnoreCase("2")){
                renderWebPage("https://mcp.tranquilcrm.in/mobileapp/printassperf?" + "agent_id=" + userID);
                loading.setVisibility(View.VISIBLE);
            } else if (type.equalsIgnoreCase("3")){
                renderWebPage("https://mcp.tranquilcrm.in/Agentsform/mbaddagent/"  + userID);
                loading.setVisibility(View.VISIBLE);
            } else {

            }
        } else {
        }

        mWebView.setWebChromeClient(new WebChromeClient() {

            // For Lollipop 5.0+ Devices
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    intent = fileChooserParams.createIntent();
                }
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    return false;
                }
                return true;
            }
        });



    }

    // JavaScript interface class
    public class MyJavaScriptInterface {
        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(DirectingReportingActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }


        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != DirectingReportingActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }else if (requestCode == RESULT_OK) {
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
        }




    }


    @SuppressLint("SetJavaScriptEnabled")
    protected void renderWebPage(String urlToRender) {
        Log.e("url==>",urlToRender);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                loading.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView mWebView, String urlToRender) {

                if (urlToRender.startsWith("https://mcp.tranquilcrm.in/agentsform/")) {

                    Utilities.finishAnimation(DirectingReportingActivity.this);
                    MySharedPreferences.setPreference(DirectingReportingActivity.this, "" + AppConstants.CP_VIEW, "YES");


                }else if(urlToRender.startsWith("tel:")) {

                    numberCalling(urlToRender);

                    return true;

                }else if (urlToRender.startsWith("https://api.whatsapp.com/send?phone=91")){

                    final BottomSheetDialog dialog1 = new BottomSheetDialog(DirectingReportingActivity.this);
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
                            sendMsg.setPackage("com.whatsapp");
                            sendMsg.setData(Uri.parse(urlToRender));
                            startActivity(sendMsg);
                       //     mWebView.goBack();
                      //      dialog1.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            /*Utilities.showToast(this,e.getMessage());*/
                            Toast.makeText(DirectingReportingActivity.this, "You don't have WhatsApp in your device", Toast.LENGTH_SHORT).show();
                        }

                    });

                    businessWhatsappIVID.setOnClickListener(view2 -> {

                        try {

                            Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                            sendMsg.setPackage("com.whatsapp.w4b");
                            sendMsg.setData(Uri.parse(urlToRender));

                            startActivity(sendMsg);
                     //       mWebView.goBack();
                       //     dialog1.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            /*Utilities.showToast(this,e.getMessage());*/
                            Toast.makeText(DirectingReportingActivity.this, "You don't have business WhatsApp in your device", Toast.LENGTH_SHORT).show();
                        }

                     //   dialog1.dismiss();

                    });

                    return true;
                }

                return false;
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(urlToRender);


    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack(); // Go back in WebView history
        } else {
            super.onBackPressed(); // Otherwise, perform default back button behavior
        }
    }



    private void numberCalling(String number) {

        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number));
                startActivity(callIntent);
                // Log.d("CONSTRUCTOR_ID_EXE_1->", "" + callID);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}