package com.wht.rishiherherbocare.inside_user_pages;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.Validations;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.R;

public class PaitentToDoctorChatActivity extends BaseActivity {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paitent_to_doctor_chat);

        _act = PaitentToDoctorChatActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        _act.overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_to_bottom);
        //finish();
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
}