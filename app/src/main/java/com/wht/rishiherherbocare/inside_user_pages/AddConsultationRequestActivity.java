package com.wht.rishiherherbocare.inside_user_pages;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sayantan.advancedspinner.MultiSpinner;
import com.suke.widget.SwitchButton;
import com.wht.rishiherherbocare.Adapter.AdapterImages;
import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Helper.ConnectionDetector;
import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Helper.Validations;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.Object.ImagePOJO;
import com.wht.rishiherherbocare.Object.Symptoms;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.my_library.Camera;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.MyValidator;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import static android.view.View.GONE;
import static com.wht.rishiherherbocare.my_library.Constants.PRESCRIPTION_IMAGE;
import static com.wht.rishiherherbocare.my_library.Constants.REPORT_IMAGE;
import static com.wht.rishiherherbocare.my_library.Constants.S_OWNER;
import static com.wht.rishiherherbocare.my_library.MyConfig.prepareFilePart;

public class AddConsultationRequestActivity extends BaseActivity implements Camera.AsyncResponse, AdapterImages.ImageUpdate {

    public static int image_type = 0;
    Camera camera;
    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView tvChangeMember, tvProfileName, tvContact, tvEmail, tvMySelf, tvReschedule, tvReqDateAndTime;
    private EditText etDrRecommandation, etHealthIssue, etDisease;
    private ProgressDialog progressDialog;
    private boolean isEdit = false;
    private boolean isSame = false;
    private int type = 0;
    private ArrayList<ImagePOJO> PRESCRIPTION_IMAGE_DATA = new ArrayList<>();
    private ArrayList<ImagePOJO> REPORTS_IMAGE_DATA = new ArrayList<>();
    private RecyclerView rv_prescription_doc, rv_report_doc;
    private AdapterImages adapterImagesBank, adapterImagesReport;
    private boolean isUpdate = false;
    private int update_position = -1;

    private String op_reg_id = "0";

    private TextView tv_select_prescription_image, tv_select_report_image;
    private ImageView iv_prescription_doc, iv_report_doc;
    private MultiSpinner spinnerSymptoms;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String statusDate = "", statusTime = "", appointmentDateTime="";
    long oldMillis;
    private ArrayList<Symptoms> symptomsArrayList = new ArrayList<>();
    private TextInputLayout til_healthIssues;
    private SwitchButton sb_healthIssues;
    private Button btn_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consultation_request);


        camera = new Camera(AddConsultationRequestActivity.this);
        _act = AddConsultationRequestActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Request");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        init();
    }

    private void init() {
        tv_select_prescription_image = findViewById(R.id.tv_select_prescription_image);
        iv_prescription_doc = findViewById(R.id.iv_prescription_doc);
        tv_select_report_image = findViewById(R.id.tv_select_report_image);
        iv_report_doc = findViewById(R.id.iv_report_doc);
        spinnerSymptoms = findViewById(R.id.spinnerSymptoms);
        tvChangeMember = findViewById(R.id.tvChangeMember);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvContact = findViewById(R.id.tvContact);
        tvEmail = findViewById(R.id.tvEmail);
        tvMySelf = findViewById(R.id.tvMySelf);
        tvReschedule = findViewById(R.id.tvReschedule);
        tvReqDateAndTime = findViewById(R.id.tvReqDateAndTime);
        til_healthIssues = findViewById(R.id.til_healthIssues);
        sb_healthIssues = findViewById(R.id.sb_healthIssues);
        btn_request = findViewById(R.id.btn_request);
        etDrRecommandation = findViewById(R.id.etDrRecommandation);
        etHealthIssue = findViewById(R.id.etHealthIssue);
        etDisease = findViewById(R.id.etDisease);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date today =calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
        String dateToStr = format.format(today);
        String dateToStr1 = format1.format(today);
        appointmentDateTime = dateToStr;
        tvReqDateAndTime.setText(""+dateToStr1);


        sb_healthIssues.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    til_healthIssues.setVisibility(View.VISIBLE);
                }else {
                    til_healthIssues.setVisibility(GONE);
                }
            }
        });

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!appointmentDateTime.equals("")) {
                    if(MyValidator.isValidField(etDisease)) {
                        uploadData();
                    }
                }else {
                    Toast.makeText(AddConsultationRequestActivity.this, "Please Select Appointment Date and Time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvMySelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProfileName.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_FIRST_NAME));
                tvContact.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_MOBILE));
                tvEmail.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_EMAIL));
            }
        });

        if (getIntent().getStringExtra(Constants.FlagToSetInfo).equals("1")) {
            tvProfileName.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_FIRST_NAME));
            tvContact.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_MOBILE));
            tvEmail.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_EMAIL));
        }
        else {
            tvProfileName.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, Constants.CounsultMemberName));
            tvContact.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, Constants.CounsultMemberContact));
            tvEmail.setText(Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, Constants.CounsultMemberEmail));
        }

        tv_select_prescription_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(AddConsultationRequestActivity.this)
                        .withPermissions(

                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE

                        )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    isUpdate = false;
                                    camera.selectImage(iv_prescription_doc, 0);
                                    image_type = PRESCRIPTION_IMAGE;
                                }

                                // check for permanent denial of any permission
                                if (!report.areAllPermissionsGranted()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();


            }
        });

        tv_select_report_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(AddConsultationRequestActivity.this)
                        .withPermissions(

                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE

                        )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();

                                    isUpdate = false;
                                    camera.selectImage(iv_report_doc, 0);
                                    image_type = REPORT_IMAGE;
                                }

                                // check for permanent denial of any permission
                                if (!report.areAllPermissionsGranted()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();


            }
        });

        tvChangeMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddConsultationRequestActivity.this, FamilyMemberListActivity.class);
                startActivity(intent);
            }
        });

        tvReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(AddConsultationRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, (month));
                        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                        String myFormat = "dd MMM yyyy"; // vijChange
                        String myFormatToSend = "yyyy-MM-dd"; // vijChange
                        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        final SimpleDateFormat sdf1 = new SimpleDateFormat(myFormatToSend, Locale.getDefault());

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);


                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddConsultationRequestActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        // tv_date_from.setVisibility(View.VISIBLE);
                                        //tv_date_from.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" "+hourOfDay + ":" + minute+ ":00");
                                        statusDate = sdf1.format(calendar.getTime());
                                        statusTime = hourOfDay + ":" + minute + ":00";
                                        appointmentDateTime = statusDate + " "+statusTime;
                                        tvReqDateAndTime.setText(
                                                sdf.format(calendar.getTime()) + " "
                                                        + hourOfDay + ":" + minute + ":00");
                                        // Log.d("date", "onTimeSet: "+DateTimeFormat.getDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" "+hourOfDay + ":" + minute+ ":00"));
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                        //edt_deliveredDate.setText(sdf.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                long now = System.currentTimeMillis() - 1000;
                dialog.getDatePicker().setMinDate(now + (1000 * 60 * 60 * 24 * 1));// TODO: used to hide previous date,month and year
                //dialog.getDatePicker().setMinDate(oldMillis);// TODO: used to hide previous date,month and year
                calendar.add(Calendar.YEAR, 0);
                //dialog.getDatePicker().setMaxDate(now);// TODO: used to hide future date,month and year
                dialog.show();
            }
        });

        setupRecyclerViews();

        getSymptomsList();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddConsultationRequestActivity.this);
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void setupRecyclerViews() {
        rv_prescription_doc = (RecyclerView) findViewById(R.id.rv_prescription_doc);
        rv_prescription_doc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterImagesBank = new AdapterImages(AddConsultationRequestActivity.this,
                PRESCRIPTION_IMAGE, MyConfig.IMG_URL_OP_DOC, PRESCRIPTION_IMAGE_DATA, S_OWNER, op_reg_id, "op_doc_bank_image");
        rv_prescription_doc.setAdapter(adapterImagesBank);

        rv_report_doc = (RecyclerView) findViewById(R.id.rv_report_doc);
        rv_report_doc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterImagesReport = new AdapterImages(AddConsultationRequestActivity.this,
                REPORT_IMAGE, MyConfig.IMG_URL_OP_DOC, REPORTS_IMAGE_DATA, S_OWNER, op_reg_id, "op_doc_bank_image");
        rv_report_doc.setAdapter(adapterImagesReport);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void processFinish(String result, int img_no) {
        String[] parts = result.split("/");
        String imagename = parts[parts.length - 1];
        //  Log.d("Image_path",result+" "+img_no);
        switch (image_type) {


            case PRESCRIPTION_IMAGE:

                if (!isUpdate) {
                    PRESCRIPTION_IMAGE_DATA.add(new ImagePOJO("0", "" + imagename, prepareFilePart("op_doc_bank_image", result), result));
                    adapterImagesBank.notifyDataSetChanged();
                } else {
                    PRESCRIPTION_IMAGE_DATA.get(update_position).setImg_name(imagename);
                    PRESCRIPTION_IMAGE_DATA.get(update_position).setImage_path_multipart(prepareFilePart("op_doc_bank_image", result));
                    PRESCRIPTION_IMAGE_DATA.get(update_position).setImg_path(result);
                    adapterImagesBank.notifyItemChanged(update_position);

                }

                break;

            case REPORT_IMAGE:

                if (!isUpdate) {
                    REPORTS_IMAGE_DATA.add(new ImagePOJO("0", "" + imagename, prepareFilePart("op_doc_bank_image", result), result));
                    adapterImagesReport.notifyDataSetChanged();
                } else {
                    REPORTS_IMAGE_DATA.get(update_position).setImg_name(imagename);
                    REPORTS_IMAGE_DATA.get(update_position).setImage_path_multipart(prepareFilePart("op_doc_bank_image", result));
                    REPORTS_IMAGE_DATA.get(update_position).setImg_path(result);
                    adapterImagesReport.notifyItemChanged(update_position);

                }

                break;

        }
    }

    @Override
    public void onUpdateImage(int IMG_TYPE, int position, ImageView imageView) {
        image_type = IMG_TYPE;
        update_position = position;
        isUpdate = true;
        camera.selectImage(imageView, 1);
    }

    private void getSymptomsList() {
        progressDialog = new ProgressDialog(AddConsultationRequestActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getSymptomsList(
                Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_ID)
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

                    if (res) {

                        JSONArray jsonArray = jsonObject.getJSONArray("symptoms_list");
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                symptomsArrayList.add(new Symptoms(obj));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();

                  /*  ArrayAdapter<Symptoms> dataAdapter = new ArrayAdapter<Symptoms>(AddConsultationRequestActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, symptomsArrayList);
                    spinnerSymptoms.setAdapter(dataAdapter);*/

                    ArrayList<String> stringArrayList = new ArrayList<>();
                    for (int i = 0; i <symptomsArrayList.size() ; i++) {
                        stringArrayList.add(symptomsArrayList.get(i).getName());
                    }

                    spinnerSymptoms.setSpinnerList(stringArrayList);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void uploadData() {

        progressDialog.show();
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i <spinnerSymptoms.getSelectedItems().size() ; i++) {
            Log.d("SpinnerDate", "getSelectedItems: "+spinnerSymptoms.getSelectedItems().get(i));

            for (int j = 0; j <symptomsArrayList.size() ; j++) {
                obj = new JSONObject();
                Log.d("Symptoms", "getSelectedItems: "+spinnerSymptoms.getSelectedItems().get(i));
                Log.d("Symptoms", "getName: "+symptomsArrayList.get(j).getName());
                if(spinnerSymptoms.getSelectedItems().get(i).equals(symptomsArrayList.get(j).getName())){
                    Log.d("Symptoms", "inserted: ");
                    try {
                    obj.put("id", symptomsArrayList.get(j).getId());
                    obj.put("name", symptomsArrayList.get(j).getName());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
            jsonArray.put(obj);

        }

        Log.d("JSONArray", "uploadData: "+jsonArray);

        String isHealthIssue = "0";

        if(sb_healthIssues.isChecked()){
            isHealthIssue = "1";
        }else {
            isHealthIssue = "0";
        }

        Retrofit adapter = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL);
        GetOrderAPI api = adapter.create(GetOrderAPI.class);
        HashMap<String, RequestBody> mapParams = new HashMap();

        mapParams.put("user_id", MyConfig.createRequestBody("" + Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, IConstant.USER_ID)));
        mapParams.put("family_member_id", MyConfig.createRequestBody("" + Shared_Preferences.getPrefs(AddConsultationRequestActivity.this, Constants.CounsultMemberID)));
        mapParams.put("isprevioushealthissue", MyConfig.createRequestBody("" + isHealthIssue));
        mapParams.put("dr_reference", MyConfig.createRequestBody("" + etDrRecommandation.getText().toString()));
        mapParams.put("date_time", MyConfig.createRequestBody("" + appointmentDateTime));
        mapParams.put("health_issue", MyConfig.createRequestBody("" + etHealthIssue.getText().toString()));
        mapParams.put("disease", MyConfig.createRequestBody("" + etDisease.getText().toString()));
        mapParams.put("symptoms_id", MyConfig.createRequestBody("" + jsonArray));

        List<MultipartBody.Part> prescriptionPartList = new ArrayList<>();
        List<MultipartBody.Part> reportPartList = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < PRESCRIPTION_IMAGE_DATA.size(); i++) {

            if (PRESCRIPTION_IMAGE_DATA.get(i).getImage_path_multipart() != null) {
                prescriptionPartList.add(prepareFilePart("prescription_image[" + j + "]", PRESCRIPTION_IMAGE_DATA.get(i).getImg_path()));
                j++;
            }
        }

        int k = 0;

        for (int i = 0; i < REPORTS_IMAGE_DATA.size(); i++) {

            if (REPORTS_IMAGE_DATA.get(i).getImage_path_multipart() != null) {
                reportPartList.add(prepareFilePart("reports_image[" + k + "]", REPORTS_IMAGE_DATA.get(i).getImg_path()));
                k++;
            }
        }



        Call<ResponseBody> result = api.addConsulatationRequest(
                mapParams,
                prescriptionPartList,
                reportPartList
        );
        //  Log.d("METHOD CALL", "uploadData: ");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("onResponse", "onResponse: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    boolean res = jsonObject.getBoolean("result");
                    String reason = jsonObject.getString("reason");

                    if (res) {
                            Intent intent = new Intent(AddConsultationRequestActivity.this, ConsultationListActivity.class);
                            startActivity(intent);
                            finish();

                    } else {

                        Toast.makeText(AddConsultationRequestActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
                       /* if (jsonObject.getBoolean(Constants.isAuthorised)==false)
                        {
                            Constants.securityOut(CreateUpdateOwnerActivity.this,jsonObject.getString("reason"));
                        }*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                //Log.d("onResponse", "onResponse: fail");

            }
        });

    }

    private interface GetOrderAPI {

        @Multipart
        @POST(MyConfig.DRBHOR + "/consultation_request")
        public Call<ResponseBody> addConsulatationRequest(
                @PartMap() Map<String, RequestBody> parametersMap,
                @Part List<MultipartBody.Part> prescriptionPartList,
                @Part List<MultipartBody.Part> reportPartList

        );

        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_symptoms_list")
        public Call<ResponseBody> getSymptomsList(
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