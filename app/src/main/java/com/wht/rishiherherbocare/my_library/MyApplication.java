package com.wht.rishiherherbocare.my_library;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;*/


/**
 * Created by SIR.WilliamRamsay on 15-Jan-16.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance=null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
      // FacebookSdk.sdkInitialize(getApplicationContext());
      //  AppEventsLogger.activateApp(this);
        printHashKey();
       // AppEventsLogger.activateApp(this);
    }
    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.windhans.client.smartfeatures", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
