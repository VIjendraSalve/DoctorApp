package com.wht.rishiherherbocare.Helper;

import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;


public class Helper_Variable {

    // location updates will be received if another app is requesting the locations than your app can handle
    public static final long FASTEST_UPDATE_INTERVAL = 1000 * 60 * 3;
    // location updates interval - 2min
    public static final long UPDATE_INTERVAL = 1000 * 60 * 5;

    public static int galleryCalledby;

    public static String fragmentTitle;
    public static String galleryId;

    public static String payerName;
    public static String payerEmail;
    public static String payerAmount;
    public static String payerMobile;
    public static String payerPurpose;

    public static String todayTithi = "";
    public static String eventImagePath = "";
    public static String templeImagePath = "";
    public static String user_reg_lang = "en";

    public static JSONObject addedVihar;
    public static JSONArray homeAds;
    public static JSONArray cityList;
    public static JSONArray carsList;
    public static JSONArray tapsList;
    public static JSONArray ipadsList;
    public static JSONArray bottomAds;
    public static JSONArray kidsNames;
    public static JSONArray mobileList;
    public static JSONArray templesList;
    public static JSONArray galleryList;
    public static JSONArray calendarAds;
    public static JSONArray holidaysList;
    public static JSONArray sampradayData;
    public static JSONArray businessPackages;
    public static JSONArray businessCategories;

    public static RelativeLayout relativeLayoutKnowledge;

}
