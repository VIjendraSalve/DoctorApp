package com.wht.rishiherherbocare.Constant;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by User on 2/15/2020.
 */

public interface Interface {

    @FormUrlEncoded
    @POST(IUrls.URL_USER_REGISTRATION)
    Call<ResponseBody> POSTUserRegistration(
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("contact_number") String contact_number,
            @Field("bod") String bod,
            @Field("password") String password);

    @FormUrlEncoded
    @POST(IUrls.URL_OTP_CHECK)
    Call<ResponseBody> getCheckOtp(
            @Field("contact_number") String contact_number,
            @Field("otp_number") String otp_number,
            @Field("notification_token") String notification_token,
            @Field("device") String device);

    @FormUrlEncoded
    @POST(IUrls.URL_RESEND_OTP)
    Call<ResponseBody> POSTResendOtp(
            @Field("contact_number") String contact_number);

    @FormUrlEncoded
    @POST(IUrls.URL_LOGIN)
    public Call<ResponseBody> POSTLogin(
            @Field("contact_number") String contact_number,
            @Field("password") String password,
            @Field("notification_token") String notification_token,
            @Field("device") String device);

    @GET(IUrls.URL_DASHBOARD)
    Call<ResponseBody> getOnLoad();

    @FormUrlEncoded
    @POST(IUrls.URL_GET_CONSULTATION_LIST)
    Call<ResponseBody> getConsultationList(
            @Field("user_id") String user_id
    );


 /*



    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_PROFILE_DATA)
    Call<ResponseBody> POSTUpdateProfileData(@Field("user_reg_id") String user_reg_id,
                                             @Field("user_first_name") String user_first_name,
                                             @Field("user_last_name") String user_last_name,
                                             @Field("biography") String biography,
                                             @Field("facebook_link") String facebook_link,
                                             @Field("twitter_link") String twitter_link,
                                             @Field("linkedin_link") String linkedin_link,
                                             @Field("apiToken") String apiToken);



    @FormUrlEncoded
    @POST(IUrls.URL_GET_SUB_CATEGORIES)
    Call<ResponseBody> POSTSubCategories(
            @Field("apiToken") String apiToken,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_GET_COURSE_LIST)
    Call<ResponseBody> POSTCourseList(
            @Field("category_id") String category_id,
            @Field("apiToken") String apiToken,
            @Field("search") String search,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_TO_CART)
    Call<ResponseBody> POSTAddToCart(
            @Field("user_id") String user_id,
            @Field("course_id") String course_id,
            @Field("apiToken") String apiToken);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_TO_WISHLIST)
    Call<ResponseBody> POSTAddToWishlist(
            @Field("user_id") String user_id,
            @Field("course_id") String course_id,
            @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_REMOVE_CART_AND_WISHLIST)
    Call<ResponseBody> POSTRemove(
            @Field("user_id") String user_id,
            @Field("course_id") String course_id,
            @Field("action") String action,
            @Field("apiToken") String apiToken);


    @FormUrlEncoded
    @POST(IUrls.URL_CART_COUNT)
    public Call<ResponseBody> POSTCartCount(@Field("user_id") String user_id,
                                            @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_CART_LIST)
    public Call<ResponseBody> POSTCartList(@Field("user_id") String user_id,
                                           @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_PLACE_ORDER)
    public Call<ResponseBody> POSTPlaceOrder(@Field("course_id") String course_id,
                                             @Field("user_id") String user_id,
                                             @Field("amount") String amount,
                                             @Field("payment_type") String payment_type,
                                             @Field("apiToken") String apiToken,
                                             @Field("order_id") String order_id,
                                             @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_COURSE_DETAILS)
    Call<ResponseBody> POSTCourseDetails(@Field("course_id") String course_id,
                                         @Field("category_id") String category_id,
                                         @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_WISH_LIST)
    Call<ResponseBody> POSTWishList(@Field("user_id") String user_id,
                                    @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_USER_ENROLLMENTS)
    public Call<ResponseBody> POSTUserEnrollments(@Field("user_id") String user_id,
                                                  @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_BANNER_LIST)
    Call<ResponseBody> POSTBannerList(
            @Field("apiToken") String apiToken,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_WEBSITE_CATEGORY)
    Call<ResponseBody> POSTWebsiteCategory(
            @Field("apiToken") String apiToken);

    @FormUrlEncoded
    @POST(IUrls.URL_WEBSITE_DETAILS)
    Call<ResponseBody> POSTWebsiteDetails(
            @Field("apiToken") String apiToken,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_ENQUIRY_FORM)
    Call<ResponseBody> POSTEnqueryForm(@Field("user_id") String user_id,
                                       @Field("category_id") String category_id,
                                       @Field("item_name") String item_name,
                                       @Field("question") String question,
                                       @Field("name") String name,
                                       @Field("mobile") String mobile,
                                       @Field("email") String email,
                                       @Field("is_feedback") String is_feedback,
                                       @Field("apiToken") String apiToken);


    @FormUrlEncoded
    @POST(IUrls.URL_SETTINGS)
    public Call<ResponseBody> POSTSettings(@Field("setting_id") String setting_id);

    @FormUrlEncoded
    @POST(IUrls.URL_VIDEO_CATEOGORY)
    Call<ResponseBody> POSTVideoCategory(
            @Field("apiToken") String apiToken);


    @FormUrlEncoded
    @POST(IUrls.URL_VIDEO_DETAILS)
    Call<ResponseBody> POSTVideoDetails(
            @Field("apiToken") String apiToken,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_MOTIVATIONAL_IMAGES)
    Call<ResponseBody> POSTMotivationalImages(
            @Field("apiToken") String apiToken,
            @Field("category_id") String category_id,
            @Field("service_id") String service_id);

    @FormUrlEncoded
    @POST(IUrls.URL_POSTERS_DETAILS)
    Call<ResponseBody> POSTPostersImages(
            @Field("apiToken") String apiToken,
            @Field("category_id") String category_id,
            @Field("service_id") String service_id);

    @FormUrlEncoded
    @POST(IUrls.URL_VIDEO_PAGE)
    Call<ResponseBody> POSTVideoPage(@Field("apiToken") String apiToken,
                                     @Field("video_url") String video_url);

    @GET(IUrls.URL_GET_VERSION)
    Call<ResponseBody> getVersion();
*/

/*
















    @FormUrlEncoded
    @POST(IUrls.URL_OFFER_LIST)
    Call<ResponseBody> POSTOfferList(
            @Field("user_id") String user_id,
            @Field("search") String search);


    @FormUrlEncoded
    @POST(IUrls.URL_ALL_ADDRESS_LIST)
    Call<ResponseBody> POSTAddressList(@Field("user_id") String user_id,
                                       @Field("action") String vendor_id);


    @FormUrlEncoded
    @POST(IUrls.URL_ALL_ADDRESS_LIST)
    Call<ResponseBody> POSTAddAddress(@Field("user_id") String user_id,
                                      @Field("address_1") String address_1,
                                      @Field("address_2") String address_2,
                                      @Field("address_3") String address_3,
                                      @Field("landmark") String landmark,
                                      @Field("lat") String lat,
                                      @Field("lng") String lng,
                                      @Field("is_default") String is_default,
                                      @Field("action") String action);


    @FormUrlEncoded
    @POST(IUrls.URL_ALL_ADDRESS_LIST)
    Call<ResponseBody> POSTDeleteAddress(@Field("user_id") String user_id,
                                         @Field("action") String action,
                                         @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST(IUrls.URL_ALL_ADDRESS_LIST)
    Call<ResponseBody> POSTUpdateAddress(@Field("user_id") String user_id,
                                         @Field("address_1") String address_1,
                                         @Field("address_2") String address_2,
                                         @Field("address_3") String address_3,
                                         @Field("landmark") String landmark,
                                         @Field("lat") String lat,
                                         @Field("lng") String lng,
                                         @Field("is_default") String is_default,
                                         @Field("action") String action,
                                         @Field("address_id") String address_id);


    @FormUrlEncoded
    @POST(IUrls.URL_APPLY_OFFERS)
    Call<ResponseBody> POSTApplyCoupon(@Field("user_id") String user_id,
                                       @Field("offer_id") String offer_id,
                                       @Field("cart_amount") String cart_amount);




    @FormUrlEncoded
    @POST(IUrls.URL_FEEDBACK)
    Call<ResponseBody> POSTFeedback(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("title") String title,
            @Field("description") String description);

    @FormUrlEncoded
    @POST(IUrls.URL_AMOUNT_UPDATE)
    Call<ResponseBody> POSTUpdateAmout(@Field("user_id") String user_id);


    @GET(IUrls.URL_GET_VERSION)
    Call<ResponseBody> getVersion();

    @GET(IUrls.URL_GET_RAW_DATA)
    Call<ResponseBody> getRawData();


    @FormUrlEncoded
    @POST(IUrls.URL_LOCAL_POST_ORDER)
    public Call<ResponseBody> POSTLocalOrder(
            @Field("order_details") JSONArray jsonArray,
            @Field("user_id") String user_id,
            @Field("total_price") String total_price,
            @Field("address_id") String address_id,
            @Field("offer_id") String offer_id,
            @Field("discount_price") String discount_price,
            @Field("final_amount") String final_amount,
            @Field("delivery_charges") String delivery_charges,
            @Field("extra_note") String extra_note

    );

    @FormUrlEncoded
    @POST(IUrls.URL_CHECK_AVAILABILITY)
    public Call<ResponseBody> POSTCheckAvailability(
            @Field("user_id") String user_id,
            @Field("order_details") JSONArray jsonArray);*/



       /*

    @FormUrlEncoded
    @POST(IUrls.URL_PAYMENT_MODE)
    public Call<ResponseBody> GETPaymentMode(@Field("user_id") String username);




    */



  /*





    @GET(IUrls.URL_ADDITIONAL_SERVICES)
    Call<ResponseBody> getAdditionalServices();

    @GET(IUrls.URL_PARENTS_DASHBOARD_GRID)
    Call<ResponseBody> getParentsDashboard();


    @GET(IUrls.URL_PRESCHOOL_OWNER_DASHBOARD_GRID)
    Call<ResponseBody> getPreschoolOwnerDashboard();

    @GET(IUrls.URL_ESTORE_DASHBOARD_BANNERS)
    Call<ResponseBody> getEstoreBanners();

    @GET(IUrls.URL_ESTORE_DASHBOARD_CATEGORY)
    Call<ResponseBody> getEstoreCategory();

    @GET(IUrls.URL_PRE_SCHOOL_EVENT_MANAGEMENT)
    Call<ResponseBody> getPreschoolEventBanner();

    @GET(IUrls.URL_PRE_SCHOOL_BRANDING_AND_MARKETING)
    Call<ResponseBody> getPreschoolEventBrandingAndMarketing();



    @GET(IUrls.URL_ESTORE_DASHBOARD_SUB_CATEGORY_PRODUCT)
    Call<ResponseBody> getEstoreSubCategoryProduct(
            @Query("category_id") String catId,
            @Query("subcat_id") String sub_catId
    );





    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_CIRRCULUM_CATEGORY)
    Call<ResponseBody> getPreschoolOwnerSmartCirriculumCategory(
            @Query("pre_owner_dash_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_CIRRCULUM_WEEK)
    Call<ResponseBody> getPreschoolOwnerSmartCirriculumCategoryWeeks(
            @Query("smart_curriculum_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_WORK_SHEET_PDF)
    Call<ResponseBody> getPreschoolOwnerSmartWorksheetPDF(
            @Query("smart_worksheet_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_TRAINING_OPTIONS)
    Call<ResponseBody> getPreschoolOwnerSmartTrainingOptions(
            @Query("pre_owner_dash_id") String catId
    );




    @FormUrlEncoded
    @POST(IUrls.URL_PRESCHOOLOWNER_REG)
    public Call<ResponseBody> registerPreschoolOwner(@Field("username") String username,
                                                     @Field("password") String password,
                                                     @Field("email") String email,
                                                     @Field("mobile") String mobile,
                                                     @Field("preschool_name") String preschool_name,
                                                     @Field("address") String address,
                                                     @Field("no_of_preschoolars_pg") String no_of_preschoolars_pg,
                                                     @Field("no_of_preschoolars_nur") String no_of_preschoolars_nur,
                                                     @Field("no_of_preschoolars_jkg") String no_of_preschoolars_jkg,
                                                     @Field("no_of_preschoolars_skg") String no_of_preschoolars_skg,
                                                     @Field("fees_range") String fees_range,
                                                     @Field("additional_services") String additional_services);

    @GET(IUrls.URL_PRESCHOOL_SMART_CURRICULUM_NEW_WEBCALL)
    Call<ResponseBody> getPreschoolOwnerSmartCurriculumNewWebcall(
            @Query("user_id") String user_id
    );

    @GET(IUrls.URL_PRESCHOOL_ESTORE_PRODUCT)
    Call<ResponseBody> getEstoreProductList();

    @GET(IUrls.URL_PRESCHOOL_ESTORE_PRODUCT_BY_CATEGORY_ID)
    Call<ResponseBody> getProductByCategoryId(
            @Query("category_id") String category_id
    );

    @GET(IUrls.URL_WORKSHEET_STUDY_MATERIAL_LIST)
    Call<ResponseBody> getWorksheetList(
            @Query("smart_worksheet_id") String smart_worksheet_id

    );

    @GET(IUrls.URL_PRESCHOOL_SMART_CURRICULUM_ALl_COURCE_IMAGES)
    Call<ResponseBody> getSmartCurriculamAllCourceImage(
            @Query("smart_curriculum_id") String smart_curriculum_id

    );

    @GET(IUrls.URL_SMART_CURICULAM_WEEK_LIST)
    Call<ResponseBody> getCuriculamWeekList(
            @Query("smart_curriculum_id") String smart_curriculum_id,
            @Query("session_id") String session_id
    );

    @GET(IUrls.URL_SMART_CURICULAM_WEEK_WISE_IMAGE)
    Call<ResponseBody> getWeekWiseImage(
            @Query("smart_curriculum_week_id") String smart_curriculum_week_id
    );

    @GET(IUrls.URL_SMART_ASSESSMENT_MANUAL)
    Call<ResponseBody> get3yrAssessmentManual(
            @Query("smart_assessment_id") String smart_assessment_id

    );



    @GET(IUrls.URL_SMART_ASSESSMENT_GRADING)
    Call<ResponseBody> getAssessmentGradient(
            @Query("smart_assessment_id") String smart_assessment_id

    );

    @GET(IUrls.URL_SMART_TRANING_BANNER)
    Call<ResponseBody> getSmartTraining(
            @Query("join_training_type_id") String join_training_type_id

    );


    @GET(IUrls.URL_SMART_ASSESSMENT_ORDER_KIT_VALUES)
    Call<ResponseBody> getAssessmentOrderValues();

    @GET(IUrls.URL_STATIONARY_LIST)
    Call<ResponseBody> getStatinaryList();

    @GET(IUrls.URL_MY_ORDER_CURRICULUM)
    Call<ResponseBody> getMyOrdersCurriculum(
            @Query("curriculum_user_id") String user_id,
            @Query("order_status") String order_status

    );


    @GET(IUrls.URL_MY_PREVIOUS_ORDER)
    Call<ResponseBody> getMyPreviousOrder(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("menu_id") String menu_id,
            @Query("order_status") String order_status

    );

    @GET(IUrls.URL_MY_PREVIOUS_ORDER_DETAILS)
    Call<ResponseBody> getMyPreviousOrderDetails(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("common_order_id") String common_order_id,
            @Query("order_status") String order_status

    );



    @GET(IUrls.URL_CANCEL_MY_ORDER)
    Call<ResponseBody> getCancelStatus(
            @Query("curriculum_user_id") String curriculum_user_id,
            @Query("curriculum_order_id") String curriculum_order_id,
            @Query("status") String order_status

    );

    @FormUrlEncoded
    @POST(IUrls.URL_SMART_CURICULAM_ORDER_DETAILS)

    public Call<ResponseBody> getCurriculamOrder(@Field("curriculum_user_id") String curriculum_user_id,
                                                 @Field("curriculum_order_person_name") String curriculum_order_person_name,
                                                 @Field("curriculum_order_person_contact") String curriculum_order_person_contact,
                                                 @Field("curriculum_order_person_email") String curriculum_order_person_email,
                                                 @Field("curriculum_order_person_address") String curriculum_order_person_address,
                                                 @Field("curriculum_order_person_delivery_address") String curriculum_order_person_delivery_address,
                                                 @Field("curriculum_pg_total_qty") String curriculum_pg_total_qty,
                                                 @Field("curriculum_pg_total_amount") String curriculum_pg_total_amount,
                                                 @Field("curriculum_nur_total_qty") String curriculum_nur_total_qty,
                                                 @Field("curriculum_nur_total_amount") String curriculum_nur_total_amount,
                                                 @Field("curriculum_jrkg_total_qty") String curriculum_jrkg_total_qty,
                                                 @Field("curriculum_jrkg_total_amount") String curriculum_jrkg_total_amount,
                                                 @Field("curriculum_srkg_total_qty") String curriculum_srkg_total_qty,
                                                 @Field("curriculum_srkg_total_amount") String curriculum_srkg_total_amount,
                                                 @Field("curriculum_total_amount") String curriculum_total_amount,
                                                 @Field("curriculum_discount_percentage") String curriculum_discount_percentage,
                                                 @Field("curriculum_breaking_price") String curriculum_breaking_price,
                                                 @Field("curriculum_delivery_charges") String curriculum_delivery_charges,
                                                 @Field("curriculum_base_amount") String curriculum_base_amount,
                                                 @Field("curriculum_total_items") String curriculum_total_items,
                                                 @Field("curriculum_cgst") String curriculum_cgst,
                                                 @Field("curriculum_sgst") String curriculum_sgst,
                                                 @Field("curriculum_payable_amount") String curriculum_payable_amount);


    @FormUrlEncoded
    @POST(IUrls.URL_SMART_WORKSHEET_ORDER_DETAILS)
    public Call<ResponseBody> getWorksheetOrder(@Field("worksheet_user_id") String worksheet_user_id,
                                                @Field("worksheet_order_person_name") String worksheet_order_person_name,
                                                @Field("worksheet_order_person_contact") String worksheet_order_person_contact,
                                                @Field("worksheet_order_person_email") String worksheet_order_person_email,
                                                @Field("worksheet_order_person_address") String worksheet_order_person_address,
                                                @Field("worksheet_order_person_delivery_address") String worksheet_order_person_delivery_address,
                                                @Field("worksheet_pg_total_qty") String worksheet_pg_total_qty,
                                                @Field("worksheet_pg_total_amount") String worksheet_pg_total_amount,
                                                @Field("worksheet_nur_total_qty") String worksheet_nur_total_qty,
                                                @Field("worksheet_nur_total_amount") String worksheet_nur_total_amount,
                                                @Field("worksheet_jrkg_total_qty") String worksheet_jrkg_total_qty,
                                                @Field("worksheet_jrkg_total_amount") String worksheet_jrkg_total_amount,
                                                @Field("worksheet_srkg_total_qty") String worksheet_srkg_total_qty,
                                                @Field("worksheet_srkg_total_amount") String worksheet_srkg_total_amount,
                                                @Field("worksheet_total_amount") String worksheet_total_amount,
                                                @Field("worksheet_discount_percentage") String worksheet_discount_percentage,
                                                @Field("worksheet_breaking_price") String worksheet_breaking_price,
                                                @Field("worksheet_delivery_charges") String worksheet_delivery_charges,
                                                @Field("worksheet_base_amount") String worksheet_base_amount,
                                                @Field("worksheet_total_items") String worksheet_total_items,
                                                @Field("worksheet_cgst") String worksheet_cgst,
                                                @Field("worksheet_sgst") String worksheet_sgst,
                                                @Field("worksheet_payable_amount") String worksheet_payable_amount);

    @FormUrlEncoded
    @POST(IUrls.URL_SMART_ASSESSMENT_ORDER_DETAILS)
    public Call<ResponseBody> getAssessmentsOrder(@Field("assessment_user_id") String assessment_user_id,
                                                  @Field("assessment_order_person_name") String assessment_order_person_name,
                                                  @Field("assessment_order_person_contact") String assessment_order_person_contact,
                                                  @Field("assessment_order_person_email") String assessment_order_person_email,
                                                  @Field("assessment_order_person_address") String assessment_order_person_address,
                                                  @Field("assessment_order_person_delivery_address") String assessment_order_person_delivery_address,
                                                  @Field("third_yr_assessment_kit_total_qty") String third_yr_assessment_kit_total_qty,
                                                  @Field("third_yr_assessment_kit_total_amount") String third_yr_assessment_kit_total_amount,
                                                  @Field("fourth_yr_assessment_kit_total_qty") String fourth_yr_assessment_kit_total_qty,
                                                  @Field("fourth_yr_assessment_kit_total_amount") String fourth_yr_assessment_kit_total_amount,
                                                  @Field("assessment_total_amount") String assessment_total_amount,
                                                  @Field("assessment_discount_percentage") String assessment_discount_percentage,
                                                  @Field("assessment_breaking_price") String assessment_breaking_price,
                                                  @Field("assessment_delivery_charges") String assessment_delivery_charges,
                                                  @Field("assessment_base_amount") String assessment_base_amount,
                                                  @Field("assessment_total_items") String assessment_total_items,
                                                  @Field("assessment_cgst") String assessment_cgst,
                                                  @Field("assessment_sgst") String assessment_sgst,
                                                  @Field("assessment_payable_amount") String assessment_payable_amount);

    @FormUrlEncoded
    @POST(IUrls.URL_JOIN_TRAINING_CONNTACT_US_BY_MAIL)
    public Call<ResponseBody> getContactByMail(@Field("user_id") String user_id,
                                               @Field("username") String username,
                                               @Field("email") String email,
                                               @Field("mobile") String mobile,
                                               @Field("training_on_mobile") String training_on_mobile,
                                               @Field("workshops") String workshops,
                                               @Field("courses_and_diploma") String courses_and_diploma
    );


    @GET(IUrls.URL_PROFILE)
    Call<ResponseBody> getProfile(
            @Query("user_id") String user_id,
            @Query("user_type") String user_type


    );




    @FormUrlEncoded
    @POST(IUrls.URL_PROFILE_UPDATE)
    public Call<ResponseBody> preschoolOwner_ProfileUpdate(@Field("user_id") String user_id,
                                                           @Field("user_type") String user_type,
                                                           @Field("username") String username,
                                                           @Field("address") String address,
                                                           @Field("preschool_name") String preschool_name);


    @GET(IUrls.URL_ESTORE_NEW_DASHBOARD_PRODUCTS)
    Call<ResponseBody> getEstoreNewDashboardProductlist(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("menu_id") String menu_id
    );

  *//*  @GET(IUrls.URL_CLASSES_INFO)
    Call<ResponseBody> getClassInfo();


    @GET(IUrls.URL_FACULTY)
    Call<ResponseBody> getFacultyInfo();

    @GET(IUrls.URL_SUBJECT)
    Call<ResponseBody> getSubjectInfo();

    @GET(IUrls.URL_PARENTS_DASHBOARD_GRID)
    Call<ResponseBody> getParentsDashboardgrid();*//*


     *//* @GET(IUrls.URL_CLASSES_INFO)
    Call<List<UserBannerObj>> getBanners();*//*


    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_ANNUAL_SPORTS_DAY_CERTIFICATE_FORM)

    public Call<ResponseBody> getAnnualSportsFinalDayCertificateForm(@Field("user_id") String user_id,
                                                                     @Field("stationary_type_id") String stationary_type_id,
                                                                     @Field("design_id") String design_id,
                                                                     @Field("stationary_school_detail_id") String stationary_school_detail_id,
                                                                     @Field("student_name") String student_name,
                                                                     @Field("student_group") String student_group,
                                                                     @Field("student_rollno") String student_rollno,
                                                                     @Field("academic_year") String academic_year,
                                                                     @Field("competition_name") String competition_name,
                                                                     @Field("competition_date") String competition_date,
                                                                     @Field("rank") String rank
    );

    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_UPDATE_ANNUAL_SPORTS_DAY_CERTIFICATE_FORM)
    public Call<ResponseBody> getUpdateAnnualSportsFinalDayCertificateForm(@Field("user_id") String user_id,
                                                                           @Field("stationary_type_id") String stationary_type_id,
                                                                           @Field("aspfinal_id") String aspfinal_id,
                                                                           @Field("student_name") String student_name,
                                                                           @Field("student_group") String student_group,
                                                                           @Field("student_rollno") String student_rollno,
                                                                           @Field("academic_year") String academic_year,
                                                                           @Field("competition_name") String competition_name,
                                                                           @Field("competition_date") String competition_date,
                                                                           @Field("rank") String rank
    );

    @GET(IUrls.URL_STATIONARY_TYPE_WISE_DESIGN_LIST)
    Call<ResponseBody> getStatinaryTypeWiseDesign(
            @Query("stationary_id") String stationary_id,
            @Query("stationary_type_id") String stationary_type_id


    );

    @GET(IUrls.URL_STATIONARY_EXPANDABLE_LIST)
    Call<StationaryPojo> getStationaryList();

    @GET(IUrls.URL_STATIONARY_STUDENT_ID_AND_PROGRESS_LIST)
    Call<ResponseBody> getStudentAndProgressList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_COMMON_ANNUAL_SPORTS_FINAL_LIST)
    Call<ResponseBody> getCommonSportsAnualFinalList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_SUMMER_CAMP_LIST)
    Call<ResponseBody> getSummerCampList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );

    @GET(IUrls.URL_STATIONARY_TRANSFER_CERTIFICATE_LIST)
    Call<ResponseBody> getTrasferCertificateList(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_ANNUL_SPORTS_FINAL)
    Call<ResponseBody> getCancelAnnualSportsFinalDayCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_ID_CARD_PROG_CARD)
    Call<ResponseBody> getCancelIDCardProgressCard(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_TRANSFER_CERTIFICATE)
    Call<ResponseBody> getCancelTransferCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_SUMMER_CAMP_CERTIFICATE)
    Call<ResponseBody> getCancelSummerCampCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );

    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_SUMMER_CAMP_CERTIFICATE_FORM)

    public Call<ResponseBody> getSummerCampCertificateForm(@Field("user_id") String user_id,
                                                           @Field("stationary_type_id") String stationary_type_id,
                                                           @Field("design_id") String design_id,
                                                           @Field("stationary_school_detail_id") String stationary_school_detail_id,
                                                           @Field("student_name") String student_name,
                                                           @Field("summer_camp_year") String summer_camp_year,
                                                           @Field("summer_camp_start_date") String summer_camp_start_date,
                                                           @Field("summer_camp_end_date") String summer_camp_end_date,
                                                           @Field("competition_rank") String competition_rank
    );

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_STATIONARY_SUMMER_CAMP_CERTIFICATE_FORM)

    public Call<ResponseBody> getUpdateSummerCampCertificateForm(@Field("user_id") String user_id,
                                                                 @Field("stationary_type_id") String stationary_type_id,
                                                                 @Field("summer_camp_id") String summer_camp_id,
                                                                 @Field("student_name") String student_name,
                                                                 @Field("summer_camp_year") String summer_camp_year,
                                                                 @Field("summer_camp_start_date") String summer_camp_start_date,
                                                                 @Field("summer_camp_end_date") String summer_camp_end_date,
                                                                 @Field("competition_rank") String competition_rank
    );

    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_TRANSFER_CERTIFICATE_FORM)


    public Call<ResponseBody> getTransferCertificateForm(@Field("user_id") String user_id,
                                                         @Field("stationary_type_id") String stationary_type_id,
                                                         @Field("design_id") String design_id,
                                                         @Field("stationary_school_detail_id") String stationary_school_detail_id,
                                                         @Field("student_name") String student_name,
                                                         @Field("father_name") String father_name,
                                                         @Field("mother_name") String mother_name,
                                                         @Field("birth_date") String birth_date,
                                                         @Field("academic_year") String academic_year,
                                                         @Field("present_group_name") String present_group_name,
                                                         @Field("promoted_group") String promoted_group,
                                                         @Field("reason") String reason,
                                                         @Field("remark") String remark

    );




    @GET(IUrls.URL_STATIONARY_PRE_SCHOOL_LIST)
    Call<ResponseBody> getSchoolList(
            @Query("user_id") String user_id
    );



    //New Web Calls

    @GET(IUrls.URL_CURRICULUM_NEW_PRODUCT_WEBCALL)
    Call<ResponseBody> getCurriculumNewProductList(
            @Query("menu_id") String menu_id,
            @Query("category_id") String category_id,
            @Query("user_id") String user_id,
            @Query("login_type") String login_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_CURRICULUM_ADD_PRODUCT_TO_CART)
    public Call<ResponseBody> postAddProductInList(@Field("user_id") String user_id,
                                                   @Field("login_type") String login_type,
                                                   @Field("menu_id") String menu_id,
                                                   @Field("product_id") String product_id,
                                                   @Field("cart_status") String cart_status);

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_PRODUCT_QTY)
    public Call<ResponseBody> postUpdateCartQty(
            @Field("menu_id") String menu_id,
            @Field("user_id") String user_id,
            @Field("login_type") String login_type,
            @Field("product_id") String product_id,
            @Field("product_qty") String product_qty);


    @GET(IUrls.URL_CART_PRODUCT_LIST)
    public Call<ResponseBody> getUserCartList(
            *//*@Query("menu_id") String menu_id,*//*
            @Query("user_id") String user_id,
            @Query("login_type") String login_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_COMMON_ORDER)
    public Call<ResponseBody> postTotalCartProduct(
            *//*  @Field("menu_id") String menu_id,*//*
            @Field("user_id") String user_id,
            @Field("login_type") String login_type,
            @Field("order_person_name") String order_person_name,
            @Field("order_person_contact") String order_person_contact,
            @Field("order_person_email") String order_person_email,
            @Field("order_person_address") String order_person_address,
            @Field("order_person_delivery_address") String order_person_delivery_address,
            @Field("order_receiver_name") String order_receiver_name,
            @Field("delivery_charges") String delivery_charges,
            @Field("pincode") String pincode);

    @GET(IUrls.URL_CONNTACT_US_BY_MAIL_LIST)
    Call<ResponseBody> getAllMailContactList(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type

    );

    @GET(IUrls.URL_ESTORE_BANNERS)
    Call<ResponseBody> getEstoreBannersList();


    @FormUrlEncoded
    @POST(IUrls.URL_CONNTACT_US_BY_MAILFORM)
    public Call<ResponseBody> getCommonContactByMail(@Field("user_id") String user_id,
                                                     @Field("login_type") String login_type,
                                                     @Field("menu_name") String menu_name,
                                                     @Field("username") String username,
                                                     @Field("email") String email,
                                                     @Field("mobile") String mobile
    );*/


}
