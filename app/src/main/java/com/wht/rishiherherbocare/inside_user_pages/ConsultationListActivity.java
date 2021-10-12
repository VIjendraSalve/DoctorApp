package com.wht.rishiherherbocare.inside_user_pages;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.wht.rishiherherbocare.Adapter.ConsulatationListActivityAdapter;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Constant.IUrls;
import com.wht.rishiherherbocare.Constant.Interface;
import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.Validations;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.Object.ConsulationReqObject;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        ConsulatationListActivityAdapter.RefreshActivity {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;

    private RecyclerView rvConsultantList;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ConsulationReqObject> consulationReqObjectArrayList;
    private RelativeLayout progress, noInternet;
    private ConsulatationListActivityAdapter consultationListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_list);

        _act = ConsultationListActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Consultations");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srExplore);
        swipeRefreshLayout.setOnRefreshListener(this);
        consulationReqObjectArrayList = new ArrayList<>();
        consultationListAdapter = new ConsulatationListActivityAdapter(
                consulationReqObjectArrayList,
                path,
                ConsultationListActivity.this);
        progress = (RelativeLayout) findViewById(R.id.progress);
        noInternet = (RelativeLayout) findViewById(R.id.noInternet);
        rvConsultantList = (RecyclerView) findViewById(R.id.rvConsultantList);

        if (connectionDetector.checkConnection(_act)) {

            webServiceConsultationList();

        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

        }

        findViewById(R.id.fabReq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Helper_Method.intentActivity(_act, AddConsultationRequestActivity.class, false);
                Intent intent = new Intent(ConsultationListActivity.this, AddConsultationRequestActivity.class);
                intent.putExtra(Constants.FlagToSetInfo, "1");
                startActivity(intent);
            }
        });

    }

    private void webServiceConsultationList() {

        progress.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        rvConsultantList.setVisibility(View.GONE);
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.getConsultationList(
                Shared_Preferences.getPrefs(ConsultationListActivity.this, IConstant.USER_ID)
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    consulationReqObjectArrayList.clear();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);
                        String path = jsonObject.getString("path");

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray("consultations_list");
                            // Parsing json
                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    consulationReqObjectArrayList.add(new ConsulationReqObject(obj));

                                } catch (JSONException e) {
                                    //Log.d("Progress Dialog","Progress Dialog");
                                    e.printStackTrace();
                                }
                            }

                            if (consulationReqObjectArrayList.size() == 0) {


                                progress.setVisibility(View.GONE);
                                noInternet.setVisibility(View.VISIBLE);
                                rvConsultantList.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);

                            } else {

                                linearLayoutManager = new LinearLayoutManager(_act);
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                rvConsultantList.setLayoutManager(linearLayoutManager);
                                rvConsultantList.setItemAnimator(new DefaultItemAnimator());
                                consultationListAdapter = new ConsulatationListActivityAdapter(consulationReqObjectArrayList, path, ConsultationListActivity.this);
                                rvConsultantList.setAdapter(consultationListAdapter);
                                consultationListAdapter.notifyDataSetChanged();
                                progress.setVisibility(View.GONE);
                                noInternet.setVisibility(View.GONE);
                                rvConsultantList.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setRefreshing(false);

                            }

                        } else {
                            //showToast(stringMsg);
                            progress.setVisibility(View.GONE);
                            noInternet.setVisibility(View.VISIBLE);
                            rvConsultantList.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                        noInternet.setVisibility(View.VISIBLE);
                        rvConsultantList.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    progress.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                    rvConsultantList.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progress.setVisibility(View.GONE);
                noInternet.setVisibility(View.VISIBLE);
                rvConsultantList.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (connectionDetector.checkConnection(_act)) {

            webServiceConsultationList();

        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

        }
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

    @Override
    public void Refresh() {
        webServiceConsultationList();
    }
}