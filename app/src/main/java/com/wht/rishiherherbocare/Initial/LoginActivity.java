package com.wht.rishiherherbocare.Initial;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Constant.IErrors;
import com.wht.rishiherherbocare.Constant.IUrls;
import com.wht.rishiherherbocare.Constant.Interface;
import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.SharedPref;
import com.wht.rishiherherbocare.Helper.Validations;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.UserDashboardActivity;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private boolean doubleBackToExitPressedOnce = false;
    private EditText etMobile, etPassword;
    private String stringUserToken = "1", user_profile_path;
    private TextView tvNewUser;
    private SharedPref sharedPref;
    private static final int CREDENTIAL_PICKER_REQUEST = 1;
    private CrystalRangeSeekbar crystalRangeSeekbar;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            // Obtain the phone number from the result
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);
            //
            // EditText.setText(credentials.getId().substring(3)); //get the selected phone number

            Log.d("MobileData", "getId: "+credentials.getId());
            Toast.makeText(_act, ""+credentials.getId().substring(3), Toast.LENGTH_SHORT).show();
//Do what ever you want to do with your selected phone number here


        }
        else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            // *** No phone numbers available ***
            Toast.makeText(LoginActivity.this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*crystalRangeSeekbar = findViewById(R.id.rangeSeekbar3);

        crystalRangeSeekbar.setMaxValue(100);
        crystalRangeSeekbar.setMinValue(0);

        crystalRangeSeekbar.setMaxStartValue(80);
        crystalRangeSeekbar.setMinStartValue(20);

        crystalRangeSeekbar.apply();*/

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();


        PendingIntent intent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try
        {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0,new Bundle());
        }
        catch (IntentSender.SendIntentException e)
        {
            e.printStackTrace();
        }

        _act = LoginActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        tvNewUser = findViewById(R.id.tvNewUser);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.edt_login_password);

        String first = "<font color='#BDBDBD'>Don't have an account ? </font>";
        String second = "<font color='#e31e25'> Sign Up</font>";
        tvNewUser.setText(Html.fromHtml(first + second));

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity_animate_fade(_act, RegistrationActivity.class, false);
            }
        });

        findViewById(R.id.tvForgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity_animate_fade(_act, ForgotActivity.class, false);
            }
        });


        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (connectionDetector.checkConnection(_act)) {

                    if (isValid()) {

                        Dexter.withContext(LoginActivity.this)
                                .withPermissions(

                                        Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.READ_SMS,
                                        Manifest.permission.READ_PHONE_NUMBERS

                                )
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                        // check if all permissions are granted
                                        if (report.areAllPermissionsGranted()) {
                                            // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                            Helper_Method.hideSoftInput(_act);
                                            webcallLogin();
                                        }

                                        // check for permanent denial of any permission
                                        if (!report.areAllPermissionsGranted()) {
                                            // show alert dialog navigating to Settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).
                                withErrorListener(new PermissionRequestErrorListener() {
                                    @Override
                                    public void onError(DexterError error) {
                                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .onSameThread()
                                .check();


                    }

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }


            }
        });

        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("Notification_Token1", "onComplete: " + token);
                        stringUserToken = token;

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, msg);
                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void webcallLogin() {

        Helper_Method.showProgressBar(_act, "Sign In...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTLogin(
                etMobile.getText().toString().trim(),
                etPassword.getText().toString().trim(),
                stringUserToken,
                "2");

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();

                    if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.



                        return;
                    }
                    TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                    String mPhoneNumber = tMgr.getLine1Number();
                    Log.d("MobileNumber", "onResponse: "+mPhoneNumber);


                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String is_verified="";
                        if(i.has("is_verified")) {
                            is_verified = i.getString("is_verified");
                        }
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");
                        if(stringCode.equals("true")) {
                            if (is_verified.equalsIgnoreCase("1")) {



                                if (stringCode.equalsIgnoreCase("true")) {


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
                                    String member_id = jsonObjectData.getString("member_id");

                                    Shared_Preferences.setPrefs(_act, IConstant.USER_ID, id);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_FIRST_NAME, full_name);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_EMAIL, email);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_MOBILE, mobile_no);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_IMAGE, image);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_PHOTO, user_profile_path + image);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_ROLE_ID, role);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_HEIGHT, height);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_WEIGHT, weight);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_IS_BLOOD_PRESSURE, is_blood_pressure);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_IS_DIABETIC, is_diabetes);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_IS_ASTHAMATIC, is_asthama);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_IS_HEART_PATIENT, is_heart_patient);
                                    Shared_Preferences.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");

                                    Shared_Preferences.setPrefs(_act, Constants.CounsultMemberName, full_name);
                                    Shared_Preferences.setPrefs(_act, Constants.CounsultMemberEmail, email);
                                    Shared_Preferences.setPrefs(_act, Constants.CounsultMemberContact, mobile_no);
                                    Shared_Preferences.setPrefs(_act, Constants.CounsultMemberID, member_id);


                                    sharedPref.setPrefs(_act, IConstant.USER_ID, id);
                                    sharedPref.setPrefs(_act, IConstant.USER_FIRST_NAME, full_name);
                                    sharedPref.setPrefs(_act, IConstant.USER_EMAIL, email);
                                    sharedPref.setPrefs(_act, IConstant.USER_MOBILE, mobile_no);
                                    sharedPref.setPrefs(_act, IConstant.USER_PHOTO, user_profile_path + image);
                                    sharedPref.setPrefs(_act, IConstant.USER_ROLE_ID, role);
                                    sharedPref.setPrefs(_act, IConstant.USER_HEIGHT, height);
                                    sharedPref.setPrefs(_act, IConstant.USER_WEIGHT, weight);
                                    sharedPref.setPrefs(_act, IConstant.USER_IS_BLOOD_PRESSURE, is_blood_pressure);
                                    sharedPref.setPrefs(_act, IConstant.USER_IS_DIABETIC, is_diabetes);
                                    sharedPref.setPrefs(_act, IConstant.USER_IS_ASTHAMATIC, is_asthama);
                                    sharedPref.setPrefs(_act, IConstant.USER_IS_HEART_PATIENT, is_heart_patient);
                                    sharedPref.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");

                                    Helper_Method.intentActivity(_act, UserDashboardActivity.class, true);
                                    finish();

                                } else {
                                    Helper_Method.toaster(_act, stringMsg);
                                    Helper_Method.dismissProgessBar();

                                }
                            } else {
                                Helper_Method.dismissProgessBar();
                                //Helper_Method.toaster(_act, stringMsg);
                                Intent intent = new Intent(_act, OtpActivity.class);
                                // intent.putExtra("UserName", etName.getText().toString().trim());
                                //intent.putExtra("Email", etEmail.getText().toString().trim());
                                intent.putExtra("Mobile", etMobile.getText().toString().trim());
                                intent.putExtra("Password", etPassword.getText().toString().trim());
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                startActivity(intent);
                            }
                        }
                        else {
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(LoginActivity.this, ""+stringMsg, Toast.LENGTH_SHORT).show();
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
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Helper_Method.toaster(_act, "Press BACK again to close the app.");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}