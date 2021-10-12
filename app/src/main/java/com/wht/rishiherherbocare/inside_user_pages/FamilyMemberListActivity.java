package com.wht.rishiherherbocare.inside_user_pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wht.rishiherherbocare.Adapter.FamilyMemberListActivityAdapter;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.Object.FamilyMember;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.CheckNetwork;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.view.View.GONE;

public class FamilyMemberListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "intent123";
    private ArrayList<FamilyMember> familyMemberArrayList = new ArrayList<>();
    private ArrayList<FamilyMember> filteredfamilyMemberArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private FamilyMemberListActivityAdapter mAdapter;
    private LinearLayout noRecordLayout, noConnectionLayout;
    private ProgressBar progressView, progressBar_endless;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_count = 1, remainingCount = 0;
    private  int shipFlag = 1;
    private Button btnRetry;
    //public static final String IMAGE_URL = "http://annadata.windhans.in/";
    private View retView;
    private Bundle bundle;
    private SearchView mSearchView;
    private String branch = "", flag = "", name = "", city = "", state = "";
    private Handler mHandler;
    private String query = "";
    private ImageView iv_gif_image;
    private EditText edt_ship_code;
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    WindowManager.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    FloatingActionButton floatingbtn_add;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Family Member");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Member");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        noRecordLayout = (LinearLayout) findViewById(R.id.noRecordLayout);
        noConnectionLayout = (LinearLayout) findViewById(R.id.noConnectionLayout);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        // swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(this);

        progressView = (ProgressBar) findViewById(R.id.progress_view);
        progressBar_endless = (ProgressBar) findViewById(R.id.progressBar_endless);



    }

    private void getFamilyMember() {
        progressDialog.show();
        filteredfamilyMemberArrayList.clear();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getFamilyMembers(
                Shared_Preferences.getPrefs(FamilyMemberListActivity.this, IConstant.USER_ID)
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponse11: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");

                    // Log.d(TAG, "onResponse: " + object.getString("Message"));
                    if (res) {
                       /* remainingCount = Integer.parseInt(jsonObject.getString("remaining"));
                        Log.d(TAG, "onResponse: " + remainingCount);*/
                        JSONArray jsonArray = jsonObject.getJSONArray("member_list");
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                familyMemberArrayList.add(new FamilyMember(obj));
                                filteredfamilyMemberArrayList.add(new FamilyMember(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        progressDialog.dismiss();
                        progressView.setVisibility(GONE);
                        progressBar_endless.setVisibility(GONE);
                        noRecordLayout.setVisibility(View.VISIBLE);
                    }
                    progressDialog.dismiss();


                    if (filteredfamilyMemberArrayList.isEmpty()) {
                        noRecordLayout.setVisibility(View.VISIBLE);
                    } else
                        noRecordLayout.setVisibility(GONE);
                    progressView.setVisibility(GONE);
                    progressBar_endless.setVisibility(GONE);
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar_endless.setVisibility(GONE);
                progressView.setVisibility(GONE);
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))  //if connection available
        {
            noConnectionLayout.setVisibility(GONE);
            noRecordLayout.setVisibility(GONE);
            init();
        } else {
          /*  Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.internet_not_avilable,
                    Snackbar.LENGTH_INDEFINITE).setAction("RETRY",
                    v -> check_connection()).show();*/
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        mHandler = new Handler();
        //bundle = getActivity().getIntent().getExtras();
        //    exhibitorsPojo = bundle.getParcelable(Constants.TOUR_PLAN_DATA);
        progressDialog = new ProgressDialog(FamilyMemberListActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_family_member_list);
        mAdapter = new FamilyMemberListActivityAdapter(filteredfamilyMemberArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        floatingbtn_add = findViewById(R.id.floatingbtn_add);
        floatingbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FamilyMemberListActivity.this, AddFamilyMemberActivity.class);
                startActivity(intent);
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1 && filteredfamilyMemberArrayList.size() > 9) {
                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            getFamilyMember();
                        }
                    }
                }
            }
        });


        familyMemberArrayList.clear();
        filteredfamilyMemberArrayList.clear();
        mAdapter.notifyDataSetChanged();
        progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(GONE);
        getFamilyMember();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry) {
            //orderList.clear();
            check_connection();
        }
    }


    @Override
    public void onRefresh() {
        if (familyMemberArrayList.size() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                swipeRefreshLayout.setRefreshing(true);
                familyMemberArrayList.clear();
                filteredfamilyMemberArrayList.clear();
                mAdapter.notifyDataSetChanged();
                //page_count = dash1;count = dash1;
                page_count = 1;
                remainingCount = 0;
                //swipe=false;
                //getOrderList();
                getFamilyMember();
                //swipe=true;
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar_endless.setVisibility(GONE);
            remainingCount = 0;
            page_count = 1;
            //get_my_rides(2);
            getFamilyMember();
        }
    }
    

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        myActionMenuItem.setVisible(true);


        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        query = s;
                        filteredfamilyMemberArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        progressView.setVisibility(View.VISIBLE);
                        getFamilyMember();
                    }
                }, 300);
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });

        return true;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        page_count=1;
        check_connection();
    }

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_user_family_member")
        public Call<ResponseBody> getFamilyMembers(
                @Field("user_id") String user_id
        );
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
