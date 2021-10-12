package com.wht.rishiherherbocare.inside_user_pages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Helper.SharedPref;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.Initial.LoginActivity;
import com.wht.rishiherherbocare.Initial.ProfileActivity;
import com.wht.rishiherherbocare.Notification.ActivityNotification;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;

public class UserDashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_CODE = 1;
    private DrawerLayout drawer;
    private ImageView ivProfile;
    private TextView tvVersionCode, tvProfileName, tvDesignation;
    private RelativeLayout rlProfile;
    private int intNotificationCount = 0;
    private Activity _act;
    private NavigationView navigationView;
    private String version = "0.0";
    private Menu nav_Menu;
    private RelativeLayout rlAreaSelections;
    private TextView tvAreaSet, tvName, tvContact, tvEmail;
    private AppUpdateManager mAppUpdateManager;
    private int RC_APP_UPDATE = 999;
    private int inAppUpdateType;
    private Task<AppUpdateInfo> appUpdateInfoTask;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onDestroy() {
        mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        try {
            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() ==
                            UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        // If an in-app update is already running, resume the update.
                        try {
                            mAppUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo,
                                    inAppUpdateType,
                                    UserDashboardActivity.this,
                                    RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    //For flexible update
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        UserDashboardActivity.this.popupSnackbarForCompleteUpdate();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            //when user clicks update button
            if (resultCode == RESULT_OK) {
                Toast.makeText(UserDashboardActivity.this, "App download starts...", Toast.LENGTH_LONG).show();
            } else if (resultCode != RESULT_CANCELED) {
                //if you want to request the update again just call checkUpdate()
                Toast.makeText(UserDashboardActivity.this, "App download canceled.", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_IN_APP_UPDATE_FAILED) {
                Toast.makeText(UserDashboardActivity.this, "App download failed.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void inAppUpdate() {
//        Toast.makeText(_act, "Testing", Toast.LENGTH_SHORT).show();
        try {
            // Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            // For a flexible update, use AppUpdateType.FLEXIBLE
                            && appUpdateInfo.isUpdateTypeAllowed(inAppUpdateType)) {
                        // Request the update.

                        try {
                            mAppUpdateManager.startUpdateFlowForResult(
                                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                    appUpdateInfo,
                                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                    inAppUpdateType,
                                    // The current activity making the update request.
                                    UserDashboardActivity.this,
                                    // Include a request code to later monitor this update request.
                                    RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException ignored) {

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void popupSnackbarForCompleteUpdate() {
        try {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(
                    parentLayout,
                    "An update has just been downloaded.\nRestart to update",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("INSTALL", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAppUpdateManager != null) {
                        mAppUpdateManager.completeUpdate();
                    }
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.black));
            snackbar.show();

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Creates instance of the manager.
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        // Returns an intent object that you use to check for an update.
        appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        //lambda operation used for below listener
        //For flexible update
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                    UserDashboardActivity.this.popupSnackbarForCompleteUpdate();
                }
            }
        };
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        //For Immediate
        inAppUpdateType = AppUpdateType.FLEXIBLE; //1
        inAppUpdate();

        _act = UserDashboardActivity.this;
        TextView textView = (TextView) this.findViewById(R.id.tvTermsAndCond);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        // textView.setText("Text text text text");
        textView.setSelected(true);
        textView.setSingleLine(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RISHI HERBOCARE");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.primary_light));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(UserDashboardActivity.this);
        View headerView = navigationView.getHeaderView(0);
       /*  rlProfile = headerView.findViewById(R.id.rlProfile);*/
         ivProfile = headerView.findViewById(R.id.ivProfile);

        if (Shared_Preferences.getPrefs(UserDashboardActivity.this, IConstant.USER_IMAGE) != null
                && !Shared_Preferences.getPrefs(UserDashboardActivity.this, IConstant.USER_IMAGE).equals("")
                && !Shared_Preferences.getPrefs(UserDashboardActivity.this, IConstant.USER_IMAGE).equals("null")) {

            Glide.with(UserDashboardActivity.this)
                    .load(Shared_Preferences.getPrefs(UserDashboardActivity.this,IConstant.USER_PHOTO))
                    .into(ivProfile);
        }else {
            Glide.with(UserDashboardActivity.this)
                    .load(R.drawable.user_pic)
                    .into(ivProfile);
        }

        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvVersionCode = headerView.findViewById(R.id.tvVersionCode);

        tvName.setText(Shared_Preferences.getPrefs(UserDashboardActivity.this, IConstant.USER_FIRST_NAME));
        tvEmail.setText(Shared_Preferences.getPrefs(UserDashboardActivity.this, IConstant.USER_EMAIL));
        tvVersionCode.setText("Version Code : 1");

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }


    private void changeFragment(Fragment fm, String TagSome) {
        /* android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();*/
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fm, TagSome);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

//            Intent intent = new Intent( PreschoolOwnerDashboardActivity.this,PreschoolOwnerDashboardActivity.class);
//            startActivity(intent);


        } /*else if (id == R.id.nav_cart) {
            Intent intent = new Intent(DashboardActivity.this, MyCartActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_wish_list) {

            Intent intent = new Intent(DashboardActivity.this, WishListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_user_enroll) {

            Intent intent = new Intent(DashboardActivity.this, UserEnrollmentActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_notification) {

            Intent intent = new Intent(DashboardActivity.this, ActivityNotification.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_previous_order) {

            Intent intent = new Intent(DashboardActivity.this, MyOrdersListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_leave_application) {
            Intent intent = new Intent(DashboardActivity.this, LeaveListActivity.class);
            startActivity(intent);

        }
         else if (id == R.id.nav_add_member) {
            Intent intent = new Intent(_act, AddMemberActivity.class);
            startActivity(intent);

        }*/ else if (id == R.id.nav_notification) {
            Intent intent = new Intent(_act, ActivityNotification.class);
            startActivity(intent);

        }else if (id == R.id.nav_share) {

            // showToast("Coming Soon!!!");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at:https://play.google.com/store/apps/details?id=com.wht.doctorapp&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            //Helper_Method.intentActivity_animate(DashboardActivity.this, FriendsActivity.class, false);


        }else if (id == R.id.nav_terms_and_conditions) {
            Intent intent = new Intent(UserDashboardActivity.this, InformationActivity.class);
            intent.putExtra(Constants.Title, "Terms Conditions");
            intent.putExtra(Constants.Description, Shared_Preferences.getPrefs(UserDashboardActivity.this, Constants.TermsCondition));
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {
            Intent intent = new Intent(UserDashboardActivity.this, InformationActivity.class);
            intent.putExtra(Constants.Title, "Contact Us");
            intent.putExtra(Constants.Description, Shared_Preferences.getPrefs(UserDashboardActivity.this, Constants.ContactUs));
            startActivity(intent);
        } else if (id == R.id.nav_website) {
            Intent intent = new Intent(UserDashboardActivity.this, InformationActivity.class);
            intent.putExtra(Constants.Title, "Website");
            intent.putExtra(Constants.Description, Shared_Preferences.getPrefs(UserDashboardActivity.this, Constants.Website));
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            // Logout(R.style.DialogTheme, "Left - Right Animation!");
            new AlertDialog.Builder(UserDashboardActivity.this)
                    .setTitle("Logout?")
                    .setMessage("Are you sure you want to Logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {


                            SharedPref.clearPref(_act);
                            Intent i = new Intent(_act, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();

                        }
                    }).create().show();

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                        /*Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);*/
                            ActivityCompat.finishAffinity(UserDashboardActivity.this);
                            //  DashboardAcivity.super.onBackPressed();

                        }
                    }).create().show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.user_dashboard, menu);
        Log.d("MyTag", "onCreateOptionsMenu: " + intNotificationCount);
        //ActionItemBadge.update(this, menu.findItem(R.id.action_notify), getResources().getDrawable(R.drawable.ic_notify), ActionItemBadge.BadgeStyles.RED, intNotificationCount);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_notify:
                Intent intent = new Intent(_act, ActivityNotification.class);
                startActivity(intent);
                // cv_CategoryTitle.setVisibility(View.GONE);
                return true;

         /*   case R.id.action_cart:
                Intent intent = new Intent(_act, MyCartActivity.class);
                // Intent intent = new Intent(CheckoutActivity.this, ActivityGetLocation.class);
                intent.putExtra("Activity_Name", "CategoryWiseProductListActivity");
                startActivityForResult(intent, 500);
                // cv_CategoryTitle.setVisibility(View.GONE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}