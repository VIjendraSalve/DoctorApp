package com.wht.rishiherherbocare.my_library;

import android.content.Context;
import android.content.SharedPreferences;


public class UtilitySharedPreferences {


    static String prefName = "MyJobPreferences";



    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;

    public static void setPrefs(Context context, String prefKey, String prefValue) {
        preferences = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(prefKey, prefValue);
        editor.commit();
    }

    public static String getPrefs(Context context, String prefKey) {
        preferences = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        return preferences.getString(prefKey, null);
    }

    public static void clearPref(Context context) {
        preferences = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear().commit();
    }


    public static void clearPref1(Context context,String prefKey) {
        preferences = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        preferences.edit().remove(prefKey).commit();
        //editor = preferences.edit();
        //editor.clear().commit();
    }


}
