package com.wht.rishiherherbocare.my_library;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PRASANNA PC-2 on 07-Apr-17.
 */
public class MyConfig {


    /*public static final String JSON_BASE_URL = "http://godapark.com";
    public static final String JSON_URL_EMP_SERVICE = "/hrcabs/mobile_app/EmployeeService";
    public static final String JSON_URL_ENQUIRY = "/hrcabs/mobile_app/Enquiry";
    public static final String DRBHOR = "/hrcabs";

    public static final String IMG_URL_OTHER = "http://godapark.com/hrcabs/files/otherImages/";
    public static final String IMG_URL_CAR = "http://godapark.com/hrcabs/files/carDocuments/";
    public static final String IMG_URL_CAR_MODEL = "http://godapark.com/hrcabs/files/vehicleModels/";
    public static final String IMG_URL_CAR_BRAND = "http://godapark.com/hrcabs/files/vehicleBrands/";
    public static final String JSON_URL_OWNER = "/mobile_app/OwnerService";
    public static final String JSON_URL_RATE_CHART = "/mobile_app/RateChartService";

    // public static final String JSON_URL_EMP_SERVICE = "/mobile_app/Profile";
    public static final String IMG_URL_PROFILE = "http://godapark.com/hrcabs/files/employee_profile/";
    public static final String IMG_URL_OP_PROFILE = "http://godapark.com/hrcabs/files/ownerProfile/";
    public static final String IMG_URL_OP_DOC = "http://godapark.com/hrcabs/files/ownerDocuments/";

    //  http://192.168.1.2/harshrajcabservices/deleteDocImage

    public static final String IMG_URL_DR_PROFILE = "http://godapark.com/hrcabs/files/driverProfile/";
    public static final String IMG_URL_DR_DOC = "http://godapark.com/hrcabs/files/driverDocuments/";*/

    //TestServer
   /* public static final String JSON_BASE_URL = "http://wetap.in";
    public static final String JSON_URL_EMP_SERVICE = "/hrcabs/mobile_app/EmployeeService";
    public static final String JSON_URL_ENQUIRY = "/hrcabs/mobile_app/Enquiry";
    public static final String DRBHOR = "/hrcabs";

    public static final String IMG_URL_OTHER = "http://wetap.in/hrcabs/files/otherImages/";
    public static final String IMG_URL_CAR = "http://wetap.in/hrcabs/files/carDocuments/";
    public static final String IMG_URL_CAR_MODEL = "http://wetap.in/hrcabs/files/vehicleModels/";
    public static final String IMG_URL_CAR_BRAND = "http://wetap.in/hrcabs/files/vehicleBrands/";
    public static final String JSON_URL_OWNER = "/mobile_app/OwnerService";
    public static final String JSON_URL_RATE_CHART = "/mobile_app/RateChartService";

    // public static final String JSON_URL_EMP_SERVICE = "/mobile_app/Profile";
    public static final String IMG_URL_PROFILE = "http://wetap.in/hrcabs/files/employee_profile/";
    public static final String IMG_URL_OP_PROFILE = "http://wetap.in/hrcabs/files/ownerProfile/";
    public static final String IMG_URL_OP_DOC = "http://wetap.in/hrcabs/files/ownerDocuments/";

    //  http://192.168.1.2/harshrajcabservices/deleteDocImage

    public static final String IMG_URL_DR_PROFILE = "http://wetap.in/hrcabs/files/driverProfile/";
    public static final String IMG_URL_DR_DOC = "http://wetap.in/hrcabs/files/driverDocuments/";*/

   //Live Server
    public static final String JSON_BASE_URL = "http://wetap.in";
    public static final String JSON_URL_EMP_SERVICE = "/mobile_app/EmployeeService";
    public static final String JSON_URL_ENQUIRY = "/mobile_app/Enquiry";
    public static final String DRBHOR = "/herbocare";

    public static final String IMG_URL_OTHER = "http://hrcabs.com/files/otherImages/";
    public static final String IMG_URL_CAR = "http://hrcabs.com/files/carDocuments/";
    public static final String IMG_URL_CAR_MODEL = "http://hrcabs.com/files/vehicleModels/";
    public static final String IMG_URL_CAR_BRAND = "http://hrcabs.com/files/vehicleBrands/";
    public static final String JSON_URL_OWNER = "/mobile_app/OwnerService";
    public static final String JSON_URL_RATE_CHART = "/mobile_app/RateChartService";

    // public static final String JSON_URL_EMP_SERVICE = "/mobile_app/Profile";
    public static final String IMG_URL_PROFILE = "http://hrcabs.com/files/employee_profile/";
    public static final String IMG_URL_OP_PROFILE = "http://hrcabs.com/files/ownerProfile/";
    public static final String IMG_URL_OP_DOC = "http://hrcabs.com/files/ownerDocuments/";

    //  http://192.168.1.2/harshrajcabservices/deleteDocImage

    public static final String IMG_URL_DR_PROFILE = "http://hrcabs.com/files/driverProfile/";
    public static final String IMG_URL_DR_DOC = "http://hrcabs.com/files/driverDocuments/";

    /*http://godapark.com/hrcabs/employeeLogin*/
    /*public static final String JSON_BASE_URL = "http://www.hrcabs.com";
    public static final String JSON_URL_EMP_SERVICE = "/mobile_app/EmployeeService";
    public static final String JSON_URL_ENQUIRY = "/mobile_app/Enquiry";
    public static final String DRBHOR = "";

    public static final String IMG_URL_OTHER = "http://www.hrcabs.com/files/otherImages/";
    public static final String IMG_URL_CAR = "http://www.hrcabs.com/files/carDocuments/";
    public static final String IMG_URL_CAR_MODEL = "http://www.hrcabs.com/files/vehicleModels/";
    public static final String IMG_URL_CAR_BRAND = "http://www.hrcabs.com/files/vehicleBrands/";
    public static final String JSON_URL_OWNER = "/mobile_app/OwnerService";
    public static final String JSON_URL_RATE_CHART = "/mobile_app/RateChartService";

    // public static final String JSON_URL_EMP_SERVICE = "/mobile_app/Profile";
    public static final String IMG_URL_PROFILE = "http://www.hrcabs.com/files/employee_profile/";
    public static final String IMG_URL_OP_PROFILE = "http://www.hrcabs.com/files/ownerProfile/";
    public static final String IMG_URL_OP_DOC = "http://www.hrcabs.com/files/ownerDocuments/";

    //  http://192.168.1.2/harshrajcabservices/deleteDocImage

    public static final String IMG_URL_DR_PROFILE = "http://www.hrcabs.com/files/driverProfile/";
    public static final String IMG_URL_DR_DOC = "http://www.hrcabs.com/files/driverDocuments/";*/





   /* public static final String JSON_BASE_URL = "http://192.168.0.117";
    public static final String DRBHOR = "/harshrajcabservices";
    public static final String JSON_URL_EMP_SERVICE = "/harshrajcabservices/mobile_app/EmployeeService";
    public static final String JSON_URL_ENQUIRY = "/harshrajcabservices/mobile_app/Enquiry";
    public static final String JSON_URL_OWNER = "/harshrajcabservices/mobile_app/OwnerService";
    public static final String JSON_URL_RATE_CHART = "/harshrajcabservices/mobile_app/RateChartService";
    public static final String IMG_URL_OTHER = "http://192.168.0.117/harshrajcabservices/files/otherImages/";
    public static final String IMG_URL_CAR = "http://192.168.0.117/harshrajcabservices/files/carDocuments/";
    public static final String IMG_URL_CAR_MODEL = "http://192.168.0.117/harshrajcabservices/files/vehicleModels/";
    public static final String IMG_URL_CAR_BRAND = "http://192.168.0.117/harshrajcabservices/files/vehicleBrands/";

    // public static final String JSON_URL_EMP_SERVICE = "/mobile_app/Profile";
    public static final String IMG_URL_PROFILE = "http://192.168.0.117/harshrajcabservices/files/employee_profile/";
    public static final String IMG_URL_OP_PROFILE = "http://192.168.0.117/harshrajcabservices/files/ownerProfile/";
    public static final String IMG_URL_OP_DOC = "http://192.168.0.117/harshrajcabservices/files/ownerDocuments/";


    public static final String IMG_URL_DR_PROFILE = "http://192.168.0.117/harshrajcabservices/files/driverProfile/";
    public static final String IMG_URL_DR_DOC = "http://192.168.0.117/harshrajcabservices/files/driverDocuments/";*/


    public static Retrofit getRetrofit(String BASE_URL) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.writeTimeout(1, TimeUnit.MINUTES);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit;
    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse("text/plain"), s);
    }

    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
