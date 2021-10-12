package com.wht.rishiherherbocare.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 2/15/2018.
 */

public class IUrls {

    public static Dispatcher dispatcher;
    public static final String BASE_URL = "http://wetap.in/herbocare/";

   // http://wetap.in/herbocare/
 //   public static final String BASE_URL = "http://santoshgare.com/";
    // public static final String BASE_URL = "http://godapark.com/groceryApp/";
    //public static final String BASE_URL = "http://www.godapark.com/lms/";
  //  public static final String IMAGE_PATH = BASE_URL + "assets/uploads/products/";
   // public static final String PDF_PATH = BASE_URL + "assets/uploads/lesson/";
    //public static final String COURSE_IMAGE_PATH = BASE_URL + "assets/uploads/thumbnails/course_thumbnails/";


    public static final String URL_USER_REGISTRATION = "app_user_register";
    public static final String URL_OTP_CHECK = "app_otp_verification";
    public static final String URL_RESEND_OTP = "app_resend_otp";
    public static final String URL_LOGIN = "app_user_login";
    public static final String URL_DASHBOARD = "onload";
    public static final String URL_GET_CONSULTATION_LIST = "app_consultationsRequest_list";



   /*




    public static final String URL_GET_CATEGORY = "get_categories";
    public static final String URL_GET_SUB_CATEGORIES = "get_sub_categories";
    public static final String URL_GET_COURSE_LIST = "get_courses";
    public static final String URL_ADD_TO_CART = "add_to_cart";
    public static final String URL_ADD_TO_WISHLIST = "add_to_wishlist";
    public static final String URL_REMOVE_CART_AND_WISHLIST = "delete_course";
    public static final String URL_CART_COUNT = "cart_count";
    public static final String URL_CART_LIST = "view_cart";
    public static final String URL_PLACE_ORDER = "book_now";
    public static final String URL_COURSE_DETAILS = "get_courses_details";
    public static final String URL_WISH_LIST = "view_wishlist";
    public static final String URL_USER_ENROLLMENTS = "user_enrollments";
    public static final String URL_BANNER_LIST = "banner_list";
    public static final String URL_WEBSITE_CATEGORY = "get_website_categories";
    public static final String URL_WEBSITE_DETAILS = "website_details";
    public static final String URL_ENQUIRY_FORM = "submit_enquiry";
    public static final String URL_SETTINGS = "app_settings";
    public static final String URL_VIDEO_CATEOGORY = "get_video_categories";
    public static final String URL_VIDEO_DETAILS = "video_details";
    public static final String URL_MOTIVATIONAL_IMAGES = "motivational_details";
    public static final String URL_POSTERS_DETAILS = "posters_details";
    public static final String URL_VIDEO_PAGE = "play_video";
    public static final String URL_GET_VERSION = "get_version";*/


/*


    public static final String URL_ADD_TO_CART = "temp_cart";
    public static final String URL_UPDATE_CART = "update_cart";
    public static final String URL_REMOVE_FROM_CART = "remove_cart_item";
    public static final String URL_CART_LIST = "my_cart";
    public static final String URL_CART_COUNT = "cart_item_count";
    public static final String URL_PLACE_ORDER = "book_order";


    public static final String URL_PREVIOUS_ORDER_LIST = "orders_list";

    public static final String URL_OFFER_LIST = "offers_list";
    public static final String URL_ALL_ADDRESS_LIST = "addressCRUD";
    public static final String URL_APPLY_OFFERS = "apply_offers";
    public static final String URL_FEEDBACK = "add_feedback";
    public static final String URL_AMOUNT_UPDATE = "cart_amount";
    public static final String URL_ADD_REVIEW = "review_submit";
    public static final String URL_GET_VERSION = "get_version";
    public static final String URL_GET_RAW_DATA = "raw_data";
    public static final String URL_LOCAL_POST_ORDER = "place_order";
    public static final String URL_CHECK_AVAILABILITY = "check_availability";*/


    public static final String TERMS_URL = "https://windhans.com/terms-conditions";
    public static final String PRIVACY_URL = "https://windhans.com/privacy-policy";
    public static final String app_url = "https://play.google.com/store/apps/details?id=com.wht.lmsapp&hl=en";
    //https://play.google.com/store/apps/details?id=com.wht.lmsapp

   /*
    public static final String URL_CHANGE_PASSWORD = "change_password";



    public static final String URL_LEAVE_LIST = "leave_list";

    public static final String URL_UPDATE_ORDER_STATUS = "update_order_status";*/


    public static Retrofit getRetrofit(String BASE_URL) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(50, TimeUnit.SECONDS);
        httpClient.readTimeout(50, TimeUnit.SECONDS);
        httpClient.writeTimeout(50, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);


        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        httpClient.dispatcher(dispatcher);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        Interface service = retrofit.create(Interface.class);
        return retrofit;
    }


    /*    public static Retrofit getRetrofit(String BASE_URL) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(50, TimeUnit.SECONDS);
            httpClient.readTimeout(50, TimeUnit.SECONDS);
            httpClient.writeTimeout(50, TimeUnit.SECONDS);
            httpClient.cache(null);
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);
            //.addConverterFactory(GsonConverterFactory.create());
            dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);
            httpClient.dispatcher(dispatcher);    Retrofit retrofit = builder.client(httpClient.build()).build();
            return retrofit;
        }*/
    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetroClient().create(ApiService.class);
    }

}
