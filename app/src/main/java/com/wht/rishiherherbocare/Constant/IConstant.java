package com.wht.rishiherherbocare.Constant;

public interface IConstant {


    int SPLASH_TIME = 3000;

    int BACK_PRESSED = 1000;

    int EXIT = 500;

    /**
     * SHOW TO USER
     */
    String RESPONSE_MESSAGE = "reason";


    /**
     * FOR PERFORMING CONDITIONS
     */
    String RESPONSE_CODE = "result";
    /**
     * for getting array of inserted id
     */
    String RESPONSE_KEY = "key";
    String RESPONSE_KEY1 = "Data";

    /**
     * KEY FOR ID'S RETURN BY RESPONSE_KEY
     */
    String RESPONSE_ID = "ID";

    String RESPONSE_SUCCESS = "true";
    String RESPONSE_ERROR = "0";
    String RESPONSE_ALREADY_EXISTS = "2";
    String RESPONSE_NULL_PARAMETER = "3";
    String RESPONSE_METHOD_NOT_ALLOWED = "4";
    String RESPONSE_NOT_EXIST = "5";
    String RESPONSE_UPDATE = "6";
    String RESPONSE_DELETE = "7";


    String USER_ID = "USER_ID";
    String USER_FIRST_NAME = "USER_FIRST_NAME";
    String USER_LAST_NAME = "USER_LAST_NAME";
    String USER_MOBILE = "USER_MOBILE";

    String USER_EMAIL = "USER_EMAIL";
    String USER_PHOTO = "USER_PHOTO";
    String USER_IMAGE = "USER_IMAGE";
    String USER_ROLE_ID = "USER_ROLE_ID";


    String USER_ADDRESS_ID = "USER_ADDRESS_ID";
    String USER_ADDRESS = "USER_ADDRESS";
    String USER_LAT = "USER_LAT";
    String USER_LAGN = "USER_LAGN";

    String USER_BIOGRAPHY = "USER_BIOGRAPHY";
    String USER_API_TOKEN = "USER_API_TOKEN";
    String USER_FACEBOOK_LINK = "USER_FACEBOOK_LINK";
    String USER_TWITTER_LINK = "USER_TWITTER_LINK";
    String USER_LINKEDIN_LINK = "USER_LINKEDIN_LINK";



    public static final String SP_MIN_ORDER_VALUE = "min_order_value";
    public static final String SP_DELIVERY_CHARGES = "delivery_charges";
    public static final String SP_VERSION_CODE = "version_code";
    public static final String SP_VERSION_NAME = "version_name";
    public static final String DATA_VERSION = "DATA_VERSION";
    public static final String SubscriptionTitle = "SubscriptionTitle";
    public static final String SubscriptionDesc = "SubscriptionDesc";
    public static final String BannerPath = "BannerPath";


    //Newly Add
    String USER_IS_LOGIN = "USER_IS_LOGIN";
    String USER_HEIGHT = "USER_HEIGHT";
    String USER_WEIGHT = "USER_WEIGHT";
    String USER_IS_BLOOD_PRESSURE = "USER_IS_BLOOD_PRESSURE";
    String USER_IS_DIABETIC = "USER_IS_DIABETIC";
    String USER_IS_ASTHAMATIC = "USER_IS_ASTHAMATIC";
    String USER_IS_HEART_PATIENT = "USER_IS_HEART_PATIENT";




}
