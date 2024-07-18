package com.mcpcustomer_post_new_ps_n.android.data;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectDetailsAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by venkei on 20-Nov-19.
 */
public class Utilities {

    public static NetworkInfo netInfo;
    public static AlertDialog.Builder builder;
    private static ProgressDialog mProgressDialog;
    KProgressHUD hud;

    public static boolean isNetworkAvailable(Context _Context) {

        ConnectivityManager cm = (ConnectivityManager) _Context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public static void startAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.act_pull_in_right, R.anim.act_push_out_left);
    }

    public static void startAnimationTop(Activity activity) {
        activity.overridePendingTransition(R.anim.act_pull_in_top, R.anim.act_push_out_bottom);
    }

    public static void finishAnimation(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);
    }

    public static void finishAnimationTop(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.act_pull_in_bottom, R.anim.act_push_out_top);
    }

    public static void AlertDaiolog(Activity activity, String tittle, String discrption, int visibility, String yes, String no){
        LayoutInflater inflater = from_Context(activity);
        View layout;
        if (inflater != null) {
            layout = inflater.inflate(R.layout.custom_alert, (ViewGroup) activity.findViewById(R.id.customAlert));

            final Dialog dialog=new Dialog(activity);
            dialog.setContentView(layout);
            dialog.dismiss();

            TextView tv =  layout.findViewById(R.id.alertTittle);
            tv.setText(tittle);

            TextView disc =  layout.findViewById(R.id.discTittle);
            disc.setText(discrption);

            Button yesBtn=layout.findViewById(R.id.alertYesBtn);
            Button noBtn=layout.findViewById(R.id.alertNoBtn);

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            yesBtn.setText((CharSequence) yes);
            noBtn.setText((CharSequence) no);

        }
    }


    public static Uri getUriForFile(Context context, String authority, File file) {
        return FileProvider.getUriForFile(context, authority, file);
    }

    private static LayoutInflater from_Context(Context context) {
        LayoutInflater layoutInflater = null;
        try {
            if (context != null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            if (layoutInflater == null) {
                throw new AssertionError("LayoutInflater not found.");
            }
        } catch (Exception e) {
            Log.w("HARI-->DEBUG", e);
            layoutInflater = null;
        }
        return layoutInflater;
    }


    public static void showProgress(KProgressHUD hud,Activity activity,String msg){
        hud= KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(msg)
                .setDetailsLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public static void  dismissProgress(KProgressHUD hud){
        hud.dismiss();
    }

    public static void showToast(Activity activity,String msg){
        LayoutInflater inflater =activity. getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 40);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void customMessage(View linearLayout, Activity activity,String msg) {
        final Snackbar snackbar = Snackbar.make(linearLayout, "", 2000);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Inflate your custom view with an Edit Text
        LayoutInflater objLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.custom_snac_layout, null);

        TextView textTVID=snackView.findViewById(R.id.textTVID);
        textTVID.setText(msg);


        layout.addView(snackView, 0);
        snackbar.show();

    }

    public static void statusBarSetup(Activity _activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = _activity.getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                _activity.getWindow().setStatusBarColor(_activity.getResources().getColor(R.color.black));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    _activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    _activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    _activity.getWindow().setStatusBarColor(_activity.getResources().getColor(R.color.black));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String CapitalText(String title) {

        String upperString = null;
        try {
            upperString = title.substring(0, 1).toUpperCase() + title.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upperString;
    }


    public static void startAnimationTop(ProjectDetailsAdapter projectDetailsAdapter) {
        projectDetailsAdapter.overridePendingTransition();
    }

}
