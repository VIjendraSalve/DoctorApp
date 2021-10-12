package com.wht.rishiherherbocare.inside_user_pages;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.Object.TipsObject;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.Constants;

import java.util.ArrayList;

public class TipsDetailsActivity extends BaseActivity {

    private ArrayList<TipsObject> tipsObjectArrayList = new ArrayList<>();
    private int position;
    private TextView tvBlogTitle, tvShotDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        tipsObjectArrayList = getIntent().getParcelableArrayListExtra(Constants.TipsList);
        position = getIntent().getIntExtra(Constants.Position, 0);

        init();

    }

    private void init() {

        tvBlogTitle = findViewById(R.id.tvBlogTitle);
        tvShotDesc = findViewById(R.id.tvShotDesc);

        tvBlogTitle.setText(tipsObjectArrayList.get(position).tips);
        tvShotDesc.setText(tipsObjectArrayList.get(position).description);
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