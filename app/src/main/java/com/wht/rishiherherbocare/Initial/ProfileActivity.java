package com.wht.rishiherherbocare.Initial;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Helper.SharedPref;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.UserDashboardActivity;
import com.wht.rishiherherbocare.my_library.Camera;
import com.wht.rishiherherbocare.my_library.CircleImageView;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.MyValidator;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;
import com.wht.rishiherherbocare.my_library.UtilityRuntimePermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class ProfileActivity extends UtilityRuntimePermission implements Camera.AsyncResponse, View.OnClickListener {

    private Camera camera;
    private CircleImageView iv_update_profile_pic;
    private ProgressDialog progressDialog;
    private String profile_image_name = "", profile_image_path = "";
    private Button btn_update_profile;
    private EditText etName, etEmail, etHeight, etWeight;
    private Button btn_submit;
    private Activity _act;
    private String user_profile_path;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _act = ProfileActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.profile);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        init();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_update_profile_pic) {
            // if (ActivityUpdateProfile.super.requestAppPermissions(this))

            Dexter.withContext(ProfileActivity.this)
                    .withPermissions(

                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA

                    )
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                camera.selectImage(iv_update_profile_pic, 0);
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
        /*if (view.getId() == R.id.btn_submit) {
            progressDialog.show();
            *//*uploadFile
         *//**//*if (validateFields() && profile_image_path==null)
                Toast.makeText(this, "Please Select Profile Pic", Toast.LENGTH_SHORT).show();
            else *//*
            if (validateFields()) {
                uploadFile(profile_image_path, profile_image_name);
            }
        }*/
    }

    private void uploadFile(String fileUri, String filename) {
        // create upload service client
        progressDialog.show();
        FileUploadService service = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        MultipartBody.Part body = null;
        if (!profile_image_path.equalsIgnoreCase("")) {

            File file = new File(fileUri);


            // String mimeType= URLConnection.guessContentTypeFromName(file.getName());

            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

            // MultipartBody.Part is used to send also the actual file name

            body = MultipartBody.Part.createFormData("profile_image", filename, requestFile);
        }


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_ID));
        RequestBody full_name = RequestBody.create(MediaType.parse("text/plain"), etName.getText().toString());
        RequestBody height = RequestBody.create(MediaType.parse("text/plain"), etHeight.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
        RequestBody weight = RequestBody.create(MediaType.parse("text/plain"), etWeight.getText().toString());
        RequestBody is_blood_pressure = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody is_diabetes = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody is_asthama = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody is_heart_patient = RequestBody.create(MediaType.parse("text/plain"), "0");


        Call<ResponseBody> call;


        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), "" + filename);
        call = service.upload(
                user_id,
                full_name,
                height,
                email,
                weight,
                is_blood_pressure,
                is_diabetes,
                is_asthama,
                is_heart_patient,
                body
        );


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject i = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(i.getString("result"));
                    String reason = i.getString("reason");

                    if (res) {
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
//                        String member_id = jsonObjectData.getString("member_id");

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
                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProfileActivity.this, UserDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(ProfileActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
                    //  Log.v("Upload", "" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", "");
                progressDialog.dismiss();
            }
        });
    }

    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidField(etName)) {
            result = false;
        }
        if (!MyValidator.isValidEmail(etEmail)) {
            result = false;
        }

        if (!MyValidator.isValidField(etHeight)) {
            result = false;
        }

        if (!MyValidator.isValidField(etWeight)) {
            result = false;
        }

        return result;
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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

    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("" + getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);

        camera = new Camera(this);


        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btn_submit = findViewById(R.id.btn_submit);
        iv_update_profile_pic = (CircleImageView) findViewById(R.id.iv_update_profile_pic);

        iv_update_profile_pic.setOnClickListener(this);
        // btn_submit.setOnClickListener(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_submit) {

                    if (validateFields()) {
                        uploadFile(profile_image_path, profile_image_name);
                    }
                }
            }
        });

        Log.d("FullName", "init: " + Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_FIRST_NAME));

        if (Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_FIRST_NAME) != null
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_FIRST_NAME).equals("")
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_FIRST_NAME).equals("null")) {
            etName.setText(Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_FIRST_NAME));
        } else {
            etName.setText("");
        }

        if (Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_EMAIL) != null
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_EMAIL).equals("")
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_EMAIL).equals("null")) {
            etEmail.setText(Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_EMAIL));
        } else {
            etEmail.setText("");
        }

        if (Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_HEIGHT) != null
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_HEIGHT).equals("")
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_HEIGHT).equals("null")) {
            etHeight.setText(Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_HEIGHT));
        } else {
            etHeight.setText("");
        }

        if (Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_WEIGHT) != null
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_WEIGHT).equals("")
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_WEIGHT).equals("null")) {
            etWeight.setText(Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_WEIGHT));
        } else {
            etWeight.setText("");
        }

        Log.d("Image", "init: "+Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_IMAGE));
        Log.d("Image", "init:1  "+Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_PHOTO));

        if (Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_IMAGE) != null
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_IMAGE).equals("")
                && !Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_IMAGE).equals("null")) {

            Glide.with(ProfileActivity.this)
                    .load(Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_PHOTO))
                    .into(iv_update_profile_pic);
        } else {

            Glide.with(ProfileActivity.this)
                    .load(R.drawable.user_pic)
                    .into(iv_update_profile_pic);
        }


    }

    @Override
    public void processFinish(String result, int img_no) {
        String[] parts = result.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path", result + " " + img_no);
        profile_image_name = imagename;
        profile_image_path = result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void webCallInsertFamilyMember() {

        progressDialog.show();
        API api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(API.class);
        Call<ResponseBody> result = api.checkUser(
                Shared_Preferences.getPrefs(ProfileActivity.this, IConstant.USER_ID),
                etName.getText().toString(),
                etHeight.getText().toString(),
                etEmail.getText().toString(),
                etWeight.getText().toString(),
                "0",
                "0",
                "0",
                "0"
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();
                    Log.d("Response", "response=> " + output);

                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("result"));
                    String reason = jsonObject.getString("reason");

                    if (res) {

                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface FileUploadService {
        @Multipart
        @POST(MyConfig.DRBHOR + "/app_update_profile")
        Call<ResponseBody> upload(
                @Part("user_id") @Nullable RequestBody user_id,
                @Part("full_name") @Nullable RequestBody full_name,
                @Part("height") @Nullable RequestBody height,
                @Part("email") @Nullable RequestBody email,
                @Part("weight") @Nullable RequestBody weight,
                @Part("is_blood_pressure") @Nullable RequestBody is_blood_pressure,
                @Part("is_diabetes") @Nullable RequestBody is_diabetes,
                @Part("is_asthama") @Nullable RequestBody is_asthama,
                @Part("is_heart_patient") @Nullable RequestBody is_heart_patient,
                @Part @Nullable MultipartBody.Part file);
    }

    public interface API {
        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_update_profile")
        Call<ResponseBody> checkUser(
                @Field("user_id") String user_id,
                @Field("full_name") String full_name,
                @Field("height") String height,
                @Field("email") String email,
                @Field("weight") String weight,
                @Field("is_blood_pressure") String is_blood_pressure,
                @Field("is_diabetes") String is_diabetes,
                @Field("is_asthama") String is_asthama,
                @Field("is_heart_patient") String is_heart_patient
        );


    }
}