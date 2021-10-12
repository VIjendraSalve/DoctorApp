package com.wht.rishiherherbocare.inside_user_pages.ui.home;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wht.rishiherherbocare.Adapter.SliderAdapterExample;
import com.wht.rishiherherbocare.Adapter.TipsAdapter;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Constant.IUrls;
import com.wht.rishiherherbocare.Constant.Interface;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Object.SliderItem;
import com.wht.rishiherherbocare.Object.TipsObject;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.ConsultationListActivity;
import com.wht.rishiherherbocare.inside_user_pages.PaitentToDoctorChatActivity;
import com.wht.rishiherherbocare.inside_user_pages.PaymentGateway;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    public static Activity _act;

    //Slider Category
    private SliderView sliderView;
    private SliderAdapterExample adapter;
    private List<SliderItem> sliderItemList;
    private String sliders_path = null;

    //Blog Data
    private ArrayList<TipsObject> tipsObjectArrayList;
    private RecyclerView rvTips;
    private LinearLayoutManager linearLayoutManagerBlogList;
    private TipsAdapter tipsAdapter;

    private TextView btnReq;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        _act = getActivity();
        //Slider Code
        sliderItemList = new ArrayList<>();
        tipsObjectArrayList = new ArrayList<>();
        rvTips = (RecyclerView) root.findViewById(R.id.rvTips);
        sliderView = root.findViewById(R.id.imageSlider);
        btnReq = root.findViewById(R.id.btnReq);

        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Helper_Method.intentActivity(_act, ConsultationListActivity.class, false);
                Helper_Method.intentActivity(_act, PaymentGateway.class, false);

            }
        });

       /* root.findViewById(R.id.btnReferel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_act, PaitentToDoctorChatActivity.class);
                intent.putExtra("consultation_id", "0");
                startActivityForResult(intent, 900);
                _act.overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
            }
        });*/


        webCallDashboard();
        return root;
    }

    private void webCallDashboard() {
        if (!_act.isFinishing()) {
            Helper_Method.showProgressBar(_act, "Loading...");
        } else {

        }
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.getOnLoad();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    sliderItemList.clear();
                    tipsObjectArrayList.clear();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        Shared_Preferences.setPrefs(getContext(), Constants.Website, MyConfig.JSON_BASE_URL + MyConfig.DRBHOR);
                        Shared_Preferences.setPrefs(getContext(), Constants.ContactUs, jsonObject.getString("contact_us"));
                        Shared_Preferences.setPrefs(getContext(), Constants.TermsCondition, jsonObject.getString("privacy_policy"));

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            sliders_path = jsonObject.getString("sliders_path");

                            ////////////////////////BLOGS//////////////////////////////////
                            JSONArray jsonArrayBlogs = jsonObject.getJSONArray("tips");
                            for (int index = 0; index < jsonArrayBlogs.length(); index++) {
                                try {

                                    tipsObjectArrayList.add(parseObjectBlog(jsonArrayBlogs.getJSONObject(index)));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Helper_Method.dismissProgessBar();
                                }
                            }

                            if (tipsObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();
                            } else {


                                tipsAdapter = new TipsAdapter(_act, tipsObjectArrayList);
                                linearLayoutManagerBlogList = new LinearLayoutManager(_act);
                                linearLayoutManagerBlogList.setOrientation(RecyclerView.VERTICAL);
                                rvTips.setLayoutManager(linearLayoutManagerBlogList);
                                rvTips.setItemAnimator(new DefaultItemAnimator());
                                rvTips.setAdapter(tipsAdapter);
                                rvTips.setHasFixedSize(true);
                                rvTips.setNestedScrollingEnabled(false);
                                // categoryIconsAdapter.setSelected(0);
                                tipsAdapter.notifyDataSetChanged();
                            }

                            ////////////////////////Sliders//////////////////////////////////
                            JSONArray jsonArraySlider = jsonObject.getJSONArray("sliders");
                            for (int index = 0; index < jsonArraySlider.length(); index++) {
                                try {
                                    sliderItemList.add(parseObjectSlider(jsonArraySlider.getJSONObject(index), sliders_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Helper_Method.dismissProgessBar();
                                }
                            }


                            if (sliderItemList.size() == 0) {

                                Helper_Method.dismissProgessBar();
                            } else {

                                Helper_Method.dismissProgessBar();
                                adapter = new SliderAdapterExample(getContext(), sliderItemList);
                                sliderView.setSliderAdapter(adapter);
                                sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                                sliderView.setIndicatorSelectedColor(Color.WHITE);
                                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                                sliderView.setScrollTimeInSec(3);
                                sliderView.setAutoCycle(true);
                                sliderView.startAutoCycle();
                            }
                        } else {
                            //showToast(stringMsg);
                            Helper_Method.dismissProgessBar();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                } finally {

                    //category_id = sliderItemList.get(0).id;
                    //page_count = 1;
                    // webServiceCallProductFirstCall(keyWord, category_id);
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, t.getMessage());
                Log.d("Error", "onFailure: " + t.getMessage());
                if (t instanceof NetworkErrorException)
                    Helper_Method.warnUser(_act, "Network Error", getString(R.string.error_network), true);
                else if (t instanceof IOException)
                    Helper_Method.warnUser(_act, "Connection Error", getString(R.string.error_network), true);
                    //else if (t instanceof ServerError)
                    //   Helper_Method.warnUser(_act, "Server Error", getString(R.string.error_server), true);
                else if (t instanceof ConnectException)
                    Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                    //else if (t instanceof ConnectException)
                    //Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                else if (t instanceof TimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else if (t instanceof SocketTimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else
                    Helper_Method.warnUser(_act, "Unknown Error", getString(R.string.error_something_wrong), true);

            }
        });
    }

    public TipsObject parseObjectBlog(JSONObject object) {
        TipsObject blogObject = new TipsObject();
        try {
            blogObject.id = object.getString("id");
            blogObject.tips = object.getString("tips");
            blogObject.description = object.getString("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogObject;
    }


    public SliderItem parseObjectSlider(JSONObject object, String sliders_path) {
        SliderItem sliderItem = new SliderItem();
        try {
            sliderItem.id = object.getString("id");
            //sliderItem.description = object.getString("slider_title");
            sliderItem.imageUrl = sliders_path + object.getString("filename");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sliderItem;
    }

}