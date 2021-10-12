package com.wht.rishiherherbocare.Initial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.MyValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ForgotActivity extends BaseActivity {
    private Activity _act;
    private ProgressDialog progressDialog;
    private EditText etEmail;
    private Button btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        _act = ForgotActivity.this;
        
        init();
    }

    private void init() {

        etEmail = findViewById(R.id.etEmail);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyValidator.isValidEmail(etEmail)){
                    sendEmail();
                }
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        _act.overridePendingTransition(R.anim.activity_stay, R.anim.fade_out);
        //finish();
    }

    private void sendEmail() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
        

        LoginAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(LoginAPI.class);
        Call<ResponseBody> result = api.checkUser(
                etEmail.getText().toString()
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");
                    String reason = jsonObject.getString("reason");

                    if (res) {

                        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(_act, "" + reason, Toast.LENGTH_SHORT).show();


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(_act, "" + reason, Toast.LENGTH_SHORT).show();
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
                //progressDialog.dismiss();
            }
        });


    }

    public interface LoginAPI {

        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_forget_password")
        Call<ResponseBody> checkUser(
                @Field("email") String email
        );
    }
}