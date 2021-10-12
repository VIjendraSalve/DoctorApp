package com.wht.rishiherherbocare.Initial;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Constant.IUrls;
import com.wht.rishiherherbocare.Constant.Interface;
import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.SharedPref;
import com.wht.rishiherherbocare.Helper.Validations;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.UserDashboardActivity;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpActivity extends BaseActivity {

    private Activity _act;
    private TextView tvTimer;
    private Validations validations;
    private SharedPref sharedPref;
    private ConnectionDetector connectionDetector;
    private OtpView otpView;
    private TextView btnSubmit, tvNote, tvResend;
    private String otpNo;
    private boolean flagOtpComplete = false;
    private String strMobile = null, stringUserToken = null, user_profile_path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        strMobile = getIntent().getStringExtra("Mobile");

        _act = OtpActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);


        otpView = findViewById(R.id.otp_view);
        btnSubmit = findViewById(R.id.btnSubmit);
        otpView.requestFocus();

        tvNote = findViewById(R.id.tvNote);
        tvResend = findViewById(R.id.tvResend);
        tvNote.setVisibility(View.GONE);
        tvResend.setVisibility(View.GONE);

        tvTimer = findViewById(R.id.tvTimer);
        Timer();

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted", otp);
                otpNo = otp;
                flagOtpComplete = true;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionDetector.checkConnection(_act)) {

                    if (flagOtpComplete) {
                        webcallOtp();
                    } else {
                        Helper_Method.toaster(_act, "Enter OTP");
                    }

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }

            }
        });


        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (connectionDetector.checkConnection(_act)) {

                    webcallResendOtp();

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }


            }
        });

    }

    private void webcallResendOtp() {

        Helper_Method.showProgressBar(_act, "Resending...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTResendOtp(strMobile);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                //EraseLocalData();
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                            // Helper_Method.intentActivity(_act, LoginActivity.class, true);
                            // finish();
                            tvNote.setVisibility(View.GONE);
                            tvResend.setVisibility(View.GONE);
                            otpView.requestFocus();
                            Timer();


                        } else {
                            Helper_Method.logD("OTP", stringMsg);
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }


                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                        Helper_Method.logD("JSONException", "onResponse: " + e.getMessage());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.logD("IOException", "onResponse: " + e.getMessage());
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void webcallOtp() {

        Helper_Method.showProgressBar(_act, "Verifying...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.getCheckOtp(strMobile, otpNo, stringUserToken, "2");

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                //EraseLocalData();
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            Helper_Method.hideSoftInput(_act);
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);

                            user_profile_path = i.getString("user_profile_path");

                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                            String id = jsonObjectData.getString("user_id");
                            String full_name = jsonObjectData.getString("full_name");
                            String email = jsonObjectData.getString("email");
                            String mobile_no = jsonObjectData.getString("mobile_no");
                            String image = jsonObjectData.getString("image");
                            String role = jsonObjectData.getString("role");
                            String height = jsonObjectData.getString("height");
                            String weight = jsonObjectData.getString("weight");
                            String is_blood_pressure = jsonObjectData.getString("is_blood_pressure");
                            String is_diabetes = jsonObjectData.getString("is_diabetes");
                            String is_asthama = jsonObjectData.getString("is_asthama");
                            String is_heart_patient = jsonObjectData.getString("is_heart_patient");

                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_ID, id);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_FIRST_NAME, full_name);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_EMAIL, email);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_MOBILE, mobile_no);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_IMAGE, image);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_PHOTO, user_profile_path + image);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_ROLE_ID, role);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_HEIGHT, height);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_WEIGHT, weight);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_IS_BLOOD_PRESSURE, is_blood_pressure);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_IS_DIABETIC, is_diabetes);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_IS_ASTHAMATIC, is_asthama);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_IS_HEART_PATIENT, is_heart_patient);
                            Shared_Preferences.setPrefs(OtpActivity.this, IConstant.USER_IS_LOGIN, "true");


                            sharedPref.setPrefs(_act,IConstant.USER_ID, id);
                            sharedPref.setPrefs(_act,IConstant.USER_FIRST_NAME, full_name);
                            sharedPref.setPrefs(_act,IConstant.USER_EMAIL, email);
                            sharedPref.setPrefs(_act,IConstant.USER_MOBILE, mobile_no);
                            sharedPref.setPrefs(_act,IConstant.USER_PHOTO, user_profile_path + image);
                            sharedPref.setPrefs(_act,IConstant.USER_ROLE_ID, role);
                            sharedPref.setPrefs(_act,IConstant.USER_HEIGHT, height);
                            sharedPref.setPrefs(_act,IConstant.USER_WEIGHT, weight);
                            sharedPref.setPrefs(_act,IConstant.USER_IS_BLOOD_PRESSURE, is_blood_pressure);
                            sharedPref.setPrefs(_act,IConstant.USER_IS_DIABETIC, is_diabetes);
                            sharedPref.setPrefs(_act,IConstant.USER_IS_ASTHAMATIC, is_asthama);
                            sharedPref.setPrefs(_act,IConstant.USER_IS_HEART_PATIENT, is_heart_patient);
                            sharedPref.setPrefs(_act,IConstant.USER_IS_LOGIN, "true");

                            Helper_Method.intentActivity(_act, UserDashboardActivity.class, true);
                            finish();


                        } else {
                            Helper_Method.logD("OTP", stringMsg);
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }


                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                        Helper_Method.logD("JSONException", "onResponse: " + e.getMessage());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.logD("IOException", "onResponse: " + e.getMessage());
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    public void Timer() {
        tvTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("0:" + String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvTimer.setText("0:0");
                Animation shake = AnimationUtils.loadAnimation(_act, R.anim.shake);
                tvNote.startAnimation(shake);
                tvResend.startAnimation(shake);
                tvNote.setVisibility(View.VISIBLE);
                tvResend.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        _act.overridePendingTransition(R.anim.activity_stay, R.anim.fade_out);
        //finish();
    }
}