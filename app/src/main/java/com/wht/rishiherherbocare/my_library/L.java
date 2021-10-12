package com.wht.rishiherherbocare.my_library;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.DrawableCompat;


/**
 * Created by SIR.WilliamRamsay on 24-Mar-16.
 */
public class L {
    private static AlertDialog.Builder builder;
    private static final String MYTAG = "mytag";
    private static ProgressDialog dialog;

    public static void t(String txt) {
        Toast.makeText(MyApplication.getAppContext(), txt, Toast.LENGTH_SHORT).show();
    }

    public static void t_long(String txt) {
        Toast.makeText(MyApplication.getAppContext(), txt, Toast.LENGTH_LONG).show();
    }


    public static void l(Context context, String txt) {
        if (txt.length() > 0) {
            Log.d(context.getClass().getSimpleName(), txt);
        }
    }

    public static void l(String txt) {
        if (txt.length() > 0) {
            Log.d("mytag", txt);
        }
    }

    public static void RatingColor(RatingBar color, int blxk){
        Drawable progress = color.getProgressDrawable();
        DrawableCompat.setTint(progress, blxk);
    }
    public static void pd(String msg, Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.show();
    }

    public static void pd(String title, String msg, Context context) {

        dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void pd(String title, String msg, int icon, Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setIcon(icon);
        dialog.show();
    }

    public static void dismiss_pd() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showMessageDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(MyApplication.getAppContext().getString(android.R.string.ok), null)
                .show();
    }


    public static String getText(EditText editText){
        return editText.getText().toString().trim();
    }
    public static String getText(TextView textView){
        return textView.getText().toString().trim();
    }
    public static String getRbText(RadioButton radioButton){
        return radioButton.getText().toString().trim();
    }


}
