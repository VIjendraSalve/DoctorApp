package com.wht.rishiherherbocare.Initial;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.wht.rishiherherbocare.Constant.IErrors;
import com.wht.rishiherherbocare.Constant.IUrls;
import com.wht.rishiherherbocare.Constant.Interface;
import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.Validations;
import com.wht.rishiherherbocare.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity {

    private Activity _act;
    private TextView tvTermsAndCond;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private EditText etName, etEmail, etMobile, etPassword, etBirthDate;
    private Button btnSignUp;
    private String stringUserToken = "null", user_profile_path, birthDate="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String statusDate = "", statusTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        _act = RegistrationActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);


        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvTermsAndCond = findViewById(R.id.tvTermsAndCond);
        etBirthDate = findViewById(R.id.etBirthDate);

        tvTermsAndCond = findViewById(R.id.tvTermsAndCond);
        String first = "<font color='#000000'>By Clicking Sign Up you agree to the following </font>";
        String second = "<font color='#ff0000'> Terms and Conditions</font>";
        String third = "<font color='#000000'> without opposition</font>";
        tvTermsAndCond.setText(Html.fromHtml(first + second + third));

        /*    findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity_animate_fade(_act, OtpActivity.class, false);
            }
        });*/

        tvTermsAndCond.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -18);
                DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                        calendar.set(Calendar.YEAR, year);
                        //calendar.add(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, (month));
                        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                        String myFormat = "yyyy-MM-dd"; // vijChange
                        String myFormat2 = "dd MMM yyyy"; // vijChange
                        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        final SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat2, Locale.getDefault());

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);
                        // Launch Time Picker Dialog
                        birthDate = sdf.format(calendar.getTime());
                        Log.d("BirthDate", "onDateSet: "+birthDate);
                        etBirthDate.setText(sdf1.format(calendar.getTime()));


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                long now = calendar.getTimeInMillis();
                dialog.getDatePicker().setMaxDate(now);// TODO: used to hide previous date,month and year
                //dialog.getDatePicker().setMinDate(oldMillis);// TODO: used to hide previous date,month and year
                //calendar.add(Calendar.YEAR, 0);
                //dialog.getDatePicker().setMaxDate(now);// TODO: used to hide future date,month and year
                dialog.show();
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (connectionDetector.checkConnection(_act)) {

                    if (isValid()) {
                        Helper_Method.hideSoftInput(_act);
                        webcallRegistration();
                    }

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }


            }
        });
    }

    private void webcallRegistration() {

        Helper_Method.showProgressBar(_act, "Registration...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTUserRegistration(
                etName.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etMobile.getText().toString().trim(),
                birthDate,
                etPassword.getText().toString().trim()
        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");

                        if (stringCode.equalsIgnoreCase("true")) {


                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                            Intent intent = new Intent(_act, OtpActivity.class);
                            intent.putExtra("UserName", etName.getText().toString().trim());
                            intent.putExtra("Email", etEmail.getText().toString().trim());
                            intent.putExtra("Mobile", etMobile.getText().toString().trim());
                            intent.putExtra("Password", etPassword.getText().toString().trim());
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            startActivity(intent);
                            // finish();


                        } else {
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }
                    } catch (JSONException e) {

                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(_act, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private boolean isValid() {


        if (validations.isBlank(etName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etName.startAnimation(shake);
            etName.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etBirthDate)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etBirthDate.startAnimation(shake);
            etBirthDate.setError(IErrors.EMPTY);
            return false;
        }


        if (validations.isBlank(etEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etEmail.startAnimation(shake);
            etEmail.setError(IErrors.EMPTY);
            return false;
        }

        if (!validations.isValidEmail(etEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etEmail.startAnimation(shake);
            etEmail.setError(IErrors.INVALID_EMAIL);
            return false;
        }
        if (!validations.isValidPhone(etMobile)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(IErrors.INVALID_PHONE);
            return false;
        }

        if (validations.isBlank(etMobile)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etPassword)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etPassword.startAnimation(shake);
            etPassword.setError(IErrors.EMPTY);
            return false;
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        _act.overridePendingTransition(R.anim.activity_stay, R.anim.fade_out);
        //finish();
    }

}