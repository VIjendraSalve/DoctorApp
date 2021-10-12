package com.wht.rishiherherbocare.inside_user_pages;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wht.rishiherherbocare.Adapter.AdapterPrescriptionImages;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.Object.ConsulationReqObject;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.DateTimeFormat;

import java.util.ArrayList;

public class ConsulatationRequestDetailsActivity extends BaseActivity {

    private TextView tvHospitalName, tvDoctor, tvPatientName, tvPatientBod, tvDisease, tvSymtoms, tvReqDateAndTime,
            tvPreviousIssues, tvDrComment;
    private RecyclerView rv_prescription_doc, rv_report_doc, rv_old_prescription_doc;
    private ArrayList<ConsulationReqObject> consulationReqObjectArrayList = new ArrayList<>();
    private int position;
    private AdapterPrescriptionImages adapterPrescriptionImages;
    private AdapterPrescriptionImages adapterReportImages;
    private AdapterPrescriptionImages adapterOldPrescriptionImages;
    private LinearLayout ll_prescription, ll_oldreport, ll_oldprescription;
    private String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulatation_request_details);

        consulationReqObjectArrayList = getIntent().getParcelableArrayListExtra(Constants.CounsultList);
        position = getIntent().getIntExtra(Constants.Position, 0);
        path = getIntent().getStringExtra(Constants.Path);

        toolBar();
        init();

    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.details);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    private void init() {

        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvDoctor = findViewById(R.id.tvDoctor);
        tvPatientName = findViewById(R.id.tvPatientName);
        tvPatientBod = findViewById(R.id.tvPatientBod);
        tvDisease = findViewById(R.id.tvDisease);
        tvSymtoms = findViewById(R.id.tvSymtoms);
        tvReqDateAndTime = findViewById(R.id.tvReqDateAndTime);
        rv_prescription_doc = findViewById(R.id.rv_prescription_doc);
        rv_report_doc = findViewById(R.id.rv_report_doc);
        rv_old_prescription_doc = findViewById(R.id.rv_old_prescription_doc);
        ll_prescription = findViewById(R.id.ll_prescription);
        ll_oldreport = findViewById(R.id.ll_oldreport);
        ll_oldprescription = findViewById(R.id.ll_oldprescription);
        tvPreviousIssues = findViewById(R.id.tvPreviousIssues);
        tvDrComment = findViewById(R.id.tvDrComment);

        tvHospitalName.setTypeface(tvHospitalName.getTypeface(), Typeface.BOLD);
        tvPatientName.setTypeface(tvPatientName.getTypeface(), Typeface.BOLD);
        tvDrComment.setTypeface(tvPatientName.getTypeface(), Typeface.BOLD);


        if(!consulationReqObjectArrayList.get(position).getHealth_issue().equals("")) {
            tvPreviousIssues.setVisibility(View.VISIBLE);
            tvPreviousIssues.setText(consulationReqObjectArrayList.get(position).getHealth_issue());
        }else {
            tvPreviousIssues.setText("No Health Issues");
            tvPreviousIssues.setVisibility(View.VISIBLE);
        }

        if(!consulationReqObjectArrayList.get(position).getDoctors_comment().equals("null")) {
            tvDrComment.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDrComment.setText(Html.fromHtml(consulationReqObjectArrayList.get(position).getDoctors_comment(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvDrComment.setText(Html.fromHtml(consulationReqObjectArrayList.get(position).getDoctors_comment()));
            }

        }else {
            tvDrComment.setVisibility(View.VISIBLE);
            tvDrComment.setText("-------");
        }

        tvHospitalName.setText(consulationReqObjectArrayList.get(position).getClinic_name());
        tvDoctor.setText(consulationReqObjectArrayList.get(position).getDoctor_name());
        tvPatientName.setText(consulationReqObjectArrayList.get(position).getName());
        tvPatientBod.setText("Birth Date : "+consulationReqObjectArrayList.get(position).getBod());
        tvDisease.setText(consulationReqObjectArrayList.get(position).getDisease());

        if (consulationReqObjectArrayList.get(position).getSymptomsToDisplayArrayList().size() > 0) {
            String symptoms = "";
            for (int i = 0; i < consulationReqObjectArrayList.get(position).getSymptomsToDisplayArrayList().size(); i++) {
                symptoms = symptoms + consulationReqObjectArrayList.get(position).getSymptomsToDisplayArrayList().get(i).getName() + ", ";
            }
            tvSymtoms.setText(symptoms);
        } else {
            tvSymtoms.setText("No Symptoms");
        }

        tvReqDateAndTime.setText(DateTimeFormat.getDate(consulationReqObjectArrayList.get(position).getDate_time()));

        Log.d("ImageSize", "position: "+position);
        Log.d("ImageSize", "getId: "+consulationReqObjectArrayList.get(position).getId());

        Log.d("ImageSize", "getDrPrescriptionImageObjectArrayList: "+consulationReqObjectArrayList.get(position).getDrPrescriptionImageObjectArrayList().size());
        Log.d("ImageSize", "getPrescriptionImageObjectArrayList: "+consulationReqObjectArrayList.get(position).getPrescriptionImageObjectArrayList().size());
        Log.d("ImageSize", "getReportImageObjectArrayList: "+consulationReqObjectArrayList.get(position).getReportImageObjectArrayList().size());

        if(consulationReqObjectArrayList.get(position).getDrPrescriptionImageObjectArrayList().size()==0){
            ll_prescription.setVisibility(View.GONE);
        }else {
            ll_prescription.setVisibility(View.VISIBLE);
        }

        if(consulationReqObjectArrayList.get(position).getPrescriptionImageObjectArrayList().size()==0){
            ll_oldprescription.setVisibility(View.GONE);
        }else {
            ll_oldprescription.setVisibility(View.VISIBLE);
        }

        if(consulationReqObjectArrayList.get(position).getReportImageObjectArrayList().size()==0){
            ll_oldreport.setVisibility(View.GONE);
        }else {
            ll_oldreport.setVisibility(View.VISIBLE);
        }

        adapterPrescriptionImages = new AdapterPrescriptionImages(ConsulatationRequestDetailsActivity.this,
                path, consulationReqObjectArrayList.get(position).getDrPrescriptionImageObjectArrayList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_prescription_doc.setLayoutManager(mLayoutManager);
        rv_prescription_doc.setItemAnimator(new DefaultItemAnimator());
        rv_prescription_doc.setAdapter(adapterPrescriptionImages);
        adapterPrescriptionImages.notifyDataSetChanged();

        adapterOldPrescriptionImages = new AdapterPrescriptionImages(ConsulatationRequestDetailsActivity.this,
                path, consulationReqObjectArrayList.get(position).getPrescriptionImageObjectArrayList());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_old_prescription_doc.setLayoutManager(mLayoutManager1);
        rv_old_prescription_doc.setItemAnimator(new DefaultItemAnimator());
        rv_old_prescription_doc.setAdapter(adapterOldPrescriptionImages);
        adapterOldPrescriptionImages.notifyDataSetChanged();

        adapterReportImages = new AdapterPrescriptionImages(ConsulatationRequestDetailsActivity.this,
                path, consulationReqObjectArrayList.get(position).getReportImageObjectArrayList());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_report_doc.setLayoutManager(mLayoutManager2);
        rv_report_doc.setItemAnimator(new DefaultItemAnimator());
        rv_report_doc.setAdapter(adapterReportImages);
        adapterReportImages.notifyDataSetChanged();

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