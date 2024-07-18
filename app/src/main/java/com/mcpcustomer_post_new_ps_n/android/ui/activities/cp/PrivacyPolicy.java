package com.mcpcustomer_post_new_ps_n.android.ui.activities.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.mcpcustomer_post_new_ps_n.android.R;

public class PrivacyPolicy extends AppCompatActivity {

    WebView webviewpriccypolicy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        webviewpriccypolicy=findViewById(R.id.webviewpriccypolicy);


        String url="https://mcp.tranquilcrm.in/Mcp/mcpprivacypolicy";
        webviewpriccypolicy.getSettings().setJavaScriptEnabled(true);
        webviewpriccypolicy.loadUrl(url); // Load your URL here

    }
}