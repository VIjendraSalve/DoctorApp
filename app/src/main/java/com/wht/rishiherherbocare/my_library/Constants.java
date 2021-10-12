package com.wht.rishiherherbocare.my_library;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wht.rishiherherbocare.R;

/**
 * Created by PC-2 on 08-Jul-17.
 */

public class Constants {

    /*SMS*/
    public static final String SMS_ORIGIN = "HRCABS";
    public static final String OTP_DELIMITER = ": ";
    /*Share Preference Constants*/
    public static final String SP_REG_ID = "reg_id";
    public static final String EMP_ID = "emp_id";
    public static final String SP_PROFILE_IMAGE = "emp_profile_image";
    public static final String SP_FIRST_NAME = "reg_first_name";
    public static final String SP_LAST_NAME = "reg_last_name";
    public static final String SP_MOBILE_NO = "reg_mobile";
    public static final String SP_EMAIL = "reg_email";
    public static final String SP_GENDER = "gender";
    public static final String SP_DOB = "date_of_birth";
    public static final String SP_PRESENT_ADDRESS = "emp_present_address";
    public static final String SP_PERMANENT_ADDRESS = "emp_permanent_address";
    public static final String SP_S_TOKEN = "sToken";
    public static final String isAuthorised = "isAuthorised";

    public static final String CAR = "2";
    public static final String DRIVER = "1";
          /**      Owner : 1
            *      Driver : 2
            *      Vehicle : 3*/
    public static final int S_OWNER = 1;
    public static final int S_DRIVER = 2;
    public static final int S_CAR = 3;

    public static final int REQUEST_OWNER = 141;
    public static final int REQUEST_CAR = 151;
    public static final int REQUEST_PACKAGE = 161;


    public static final int PROFILE_IMAGE = 11;
    public static final int ADHAR_IMAGE = 1;
    public static final int PAN_CARD_IMAGE = 2;
    public static final int PRESENT_ADD_IMAGE = 3;
    public static final int PERMANENT_ADD_IMAGE = 4;
    public static final int DRIVING_LICENCE_IMAGE = 5;
    public static final int POLICE_VERIFICATION_IMAGE = 6;

    public static final int ADHAR_IMAGE_NEW = 9;

    public static final int PRESCRIPTION_IMAGE = 1;
    public static final int REPORT_IMAGE = 2;

    public static final int RC_IMAGE = 1;
    public static final int INSURANCE_IMAGE = 2;
    public static final int PERMIT_IMAGE = 3;
    public static final int RTO_TAX_IMAGE = 6;
    public static final int NOC_IMAGE = 4;
    public static final int FITNESS_IMAGE = 5;
    public static final int FITNESS_PA = 20;
    public static final int FITNESS_PUC = 21;


    public static final int OPTION_SELECT = 3;
    public static final int OPTION_EDIT = 2;
    public static final int OPTION_CREATE = 1;
    public static final int OPTION_ADD = 4;
    public static final int DASH = 1;
    public static final int PACKAGE = 2;
    public static final String OTP_MOB = "otp_mob";

    public static final String RS = "\u20B9 ";
    public static final String REG_TYPE = "Employee";
    public static final int REQUEST_CHANGE = 211;
    public static final String IS_VERIFIED = "is_verified";
    public static final String CounsultMemberName = "CounsultMemberName";
    public static final String CounsultMemberContact = "CounsultMemberContact";
    public static final String CounsultMemberEmail = "CounsultMemberEmail";
    public static final String CounsultMemberID = "CounsultMemberID";
    public static final String FlagToSetInfo = "FlagToSetInfo";
    public static final String CounsultList = "CounsultList";
    public static final String Position = "Position";
    public static final String Path = "Path";
    public static final String TipsList = "TipsList";
    public static final String Title = "Title";
    public static final String Description = "Description";
    public static final String TermsCondition = "TermsCondition";
    public static final String ContactUs = "ContactUs";
    public static final String Website = "Website";






    public static void showDocument(Context context, String string_url) {
        final Dialog dialog = new Dialog(context, R.style.MaterialDialog);
        dialog.setContentView(R.layout.dialog_document_detail_view);
        dialog.setCancelable(false);
        final ImageView iv_doc = (ImageView) dialog.findViewById(R.id.iv_doc);
        //final ProgressBar dialog_progress_bar = (ProgressBar) dialog.findViewById(R.id.dialog_progress_bar);
        Log.d("Dialogue", "showDocument1: "+string_url);
        Glide.with(context).load(string_url)
                .into(iv_doc);

        dialog.findViewById(R.id.iv_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Log.d("Dialogue", "showDocument2: "+string_url);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //dialog.getWindow().setGravity(Gravity.BOTTOM);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.MaterialDialog; //style id
        dialog.show();
    }

    public static void loadImageDoc(Context context, String string_url, final ImageView imageView) {
        Glide.with(context).load(string_url)
                .thumbnail(0.5f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object o, Target<Drawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        imageView.setImageDrawable(drawable);
                        return false;
                    }
                })
                .into(imageView);
    }

}
