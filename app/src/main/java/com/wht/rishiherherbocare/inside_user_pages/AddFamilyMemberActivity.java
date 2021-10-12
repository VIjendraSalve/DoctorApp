package com.wht.rishiherherbocare.inside_user_pages;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.CheckNetwork;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.MyValidator;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class AddFamilyMemberActivity extends BaseActivity {

    private EditText etName, etEmail, etAge, etMobile, etRelation;
    private Button btn_submit;
    private ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String birthDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        toolBar();
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAge = findViewById(R.id.etAge);
        etMobile = findViewById(R.id.etMobile);

        etRelation = findViewById(R.id.etRelation);
        btn_submit = findViewById(R.id.btn_submit);
        
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckNetwork.isInternetAvailable(AddFamilyMemberActivity.this)){
                    if(validateFields()){
                        webCallInsertFamilyMember();
                    }
                }else {
                    Toast.makeText(AddFamilyMemberActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        etAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(AddFamilyMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                        calendar.set(Calendar.YEAR, year);
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
                        etAge.setText(sdf1.format(calendar.getTime()));


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                long now = System.currentTimeMillis() - 1000;
                dialog.getDatePicker().setMaxDate(now);// TODO: used to hide previous date,month and year
                //dialog.getDatePicker().setMinDate(oldMillis);// TODO: used to hide previous date,month and year
                calendar.add(Calendar.YEAR, 0);
                //dialog.getDatePicker().setMaxDate(now);// TODO: used to hide future date,month and year
                dialog.show();
            }
        });

    }

    public interface API {
        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_user_family_member_add")
        Call<ResponseBody> checkUser(
                @Field("user_id") String user_id,
                @Field("name") String name,
                @Field("bod") String age,
                @Field("contact") String contact,
                @Field("email") String email,
                @Field("relation") String relation
        );


    }

    private void webCallInsertFamilyMember() {

        progressDialog.show();
        API api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(API.class);
        Call<ResponseBody> result = api.checkUser(
                Shared_Preferences.getPrefs(AddFamilyMemberActivity.this, IConstant.USER_ID),
                etName.getText().toString(),
                birthDate,
                etMobile.getText().toString(),
                etEmail.getText().toString(),
                etRelation.getText().toString()
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

                        Toast.makeText(AddFamilyMemberActivity.this, reason, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddFamilyMemberActivity.this, FamilyMemberListActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(AddFamilyMemberActivity.this, reason, Toast.LENGTH_SHORT).show();
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

    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidField(etName)) {
            result = false;
        }
        if (!MyValidator.isValidField(etRelation)) {
            result = false;
        }
        if (!MyValidator.isValidField(etAge)) {
            result = false;
        }
        if (!MyValidator.isValidEmail(etEmail)) {
            result = false;
        }
        if (!MyValidator.isValidMobile(etMobile)) {
            result = false;
        }
        return result;
    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.add_family_member);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //overridePendingTransition(R.animator.left_right, R.animator.right_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            //overridePendingTransition(R.animator.left_right, R.animator.right_left);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}