package com.wht.rishiherherbocare.Initial;

import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Constant.IUrls;
import com.wht.rishiherherbocare.Helper.AppController;
import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.SharedPref;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.UserDashboardActivity;

import java.io.File;

public class SplashActivity extends BaseActivity {

    private Activity _act;
    String TAG = "SplashScreen";
    private ImageView iv_app_logo;

    private Dialog dialog;
    private Button buttonUpdate, buttonExit;
    private String versionCode, versionName;
    private TextView tvVersion;
    private ProgressBar prog;
    public static boolean versionFlag;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        _act = SplashActivity.this;
        connectionDetector = ConnectionDetector.getInstance(this);
        iv_app_logo = findViewById(R.id.iv_app_logo);
        prog = findViewById(R.id.prog);


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = String.valueOf(pInfo.versionCode);
            versionName = String.valueOf(pInfo.versionName);
//            tvVersion.setText("Version : " + versionName);
            Log.d(TAG, "onCreate: App Version Code : " + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (AppController.isInternet(this)) {


            final Thread timer = new Thread() {
                public void run() {
                    try {

                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                        iv_app_logo.startAnimation(animation2);
                        animation2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                // Toast.makeText(SplashScreenActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                                deleteCache(_act);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN) != null && !SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN).isEmpty() && !SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN).equals("null") && !SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN).equals("")) {
                                    Helper_Method.intentActivity_animate_fade(_act, UserDashboardActivity.class, true);
                                } else {
                                    //Helper_Method.intentActivity_animate_fade(_act, LoginActivity.class, true);
                                    Helper_Method.intentActivity_animate_fade(_act, LoginActivity.class, true);
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    } finally {
                    }
                }
            };
            timer.start();

        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void UpdateVersion() {

        int width = (int) (getResources().getDisplayMetrics().widthPixels);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        dialog = new Dialog(_act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dailog_update_version);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();

        buttonExit = dialog.findViewById(R.id.buttonExit);
        buttonUpdate = dialog.findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(IUrls.app_url)));
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ActivityCompat.finishAffinity(_act);

            }
        });

    }

/*    private void webServiceGetRawData() {


        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.getVersion();

        result.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {


                    output = response.body().string();
                    Log.d("mytag", "onResponse: " + output);
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        //String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            //String version_code = jsonObject.getString(IConstant.SP_VERSION_CODE);
                            //String version_name = jsonObject.getString("version_name");

                            if (Integer.parseInt(versionCode) > jsonObject.getInt(IConstant.SP_VERSION_CODE)) {
                                //versionFlag = false;
                                UpdateVersion();
                                return;
                            } else {
                                //versionFlag = true;
                                if (islogin) {

                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    startActivityForResult(intent, 1);
                                    MainActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();

                                } else {

                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivityForResult(intent, 1);
                                    MainActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();
                                }
                            }

                            // equal to not get
                            // mismatch asel tr get


                            // JSONArray jsonArrayBanners = jsonObject.getJSONArray("banners");

                        } else {
                            //Toast.makeText(MainActivity.this, stringMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Technical Error", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
