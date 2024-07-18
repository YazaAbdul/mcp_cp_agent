package com.mcpcustomer_post_new_ps_n.android.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by venkei on 18-Mar-19.
 */
public class MySharedPreferences {

    public static Context mContext;
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor editor;

    public static void setPreference(Context context, String key, String value) {
        mContext = context;
        editor = mContext.getSharedPreferences("customer_app", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreferences(Context context, String key) {
        mContext = context;
        prefs = mContext.getSharedPreferences("customer_app", Context.MODE_PRIVATE);
        String text = prefs.getString(key, "");
        return text;
    }

    public static void clearPreferences(Activity context) {
        mContext = context;
        SharedPreferences settings = mContext.getSharedPreferences("customer_app", Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }
}
