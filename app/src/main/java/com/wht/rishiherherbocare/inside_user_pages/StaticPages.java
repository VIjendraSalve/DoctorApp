package com.wht.rishiherherbocare.inside_user_pages;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.Initial.BaseActivity;


public class StaticPages extends BaseActivity {

    private TextView tv_rules, tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_pages);

        toolbar();
        init();
    }

    private void init() {
        tv_rules = findViewById(R.id.tv_rules);
        tv_title = findViewById(R.id.tv_title);
        //tv_rules.setText(""+getIntent().getStringExtra(Constants.RideRules));

        tv_title.setText(""+getIntent().getStringExtra(Constants.Title));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_rules.setText(Html.fromHtml("" + getIntent().getStringExtra(Constants.Description),  Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_rules.setText(Html.fromHtml("" + getIntent().getStringExtra(Constants.Description)));
        }
    }

    private void toolbar() {
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(""+getIntent().getStringExtra(Constants.Description));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
