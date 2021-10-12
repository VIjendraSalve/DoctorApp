package com.wht.rishiherherbocare.my_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;


public class DatabaseSqlite extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
    private Context context;
    private Calendar cal = Calendar.getInstance();

    // Database Name
    private static final String DATABASE_NAME = "Cabs";

    //table name
    private static final String TABLE_KILOMETER = "tbl_kilometer";
    private static final String TABLE_RATE_CHART = "tbl_rate_chart";
    private static final String TABLE_SEGMENT = "tbl_segment";


    private static final String KEY_KILOMETER_ID = "kilometer_id";
    private static final String KEY_KILOMETER = "kilometer";

    private static final String KEY_SEGMENT_ID = "segment_id";
    private static final String KEY_SEGMENT_NAME = "segment_name";

    private static final String KEY_RATE_CHART_ID = "rate_chart_id";
    private static final String KEY_BASE_FARE = "base_fare";
    private static final String KEY_PER_KM_CHARGE = "per_km_charge";
    private static final String KEY_PER_MIN_CHARGE = "per_min_charge";
    private static final String KEY_IS_ROUND = "is_round";

    private String[] kilometer = {"50 KMS TO 120 KMS & RETURN TRIP", "ABOVE 121 KMS DROP ONLY UPTO UNLIMITED KMS & RETURN UPTO 200 KMS",
            "SERVICE NOT AVAILABLE AREA RATE CHART UPTO 300 KMS ONLY FOR DROP", "ABOVE 200 KMS & RETURN TRIP",
            "ABOVE 300 KMS & RETURN TRIP", "SERVICE NOT AVAILABLE AREA RATE CHART ABOVE 400 KMS ONLY FOR DROP", "ABOVE 500 KMS & RETURN TRIP",
            "SERVICE NOT AVAILABLE AREA RATE CHART ABOVE 500 KMS ONLY FOR DROP", "ABOVE 600 KMS & RETURN TRIP",
            "SERVICE NOT AVAILABLE AREA RATE CHART ABOVE 600 KMS ONLY FOR DROP"};
    private String[] segment = {"HATCHBACK", "SEDAN", "PREM. SEDAN", "SUV", "PREM. SUV"};

    private static DatabaseSqlite instance;

    public static DatabaseSqlite getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseSqlite(context);
        }
        return instance;
    }

    public DatabaseSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_KILOMETER_TABLE = "CREATE TABLE " + TABLE_KILOMETER + "("
                + KEY_KILOMETER_ID + " INTEGER PRIMARY KEY," + KEY_KILOMETER + " TEXT" + ")";


        String CREATE_SEGMENT_TABLE = "CREATE TABLE " + TABLE_SEGMENT + "("
                + KEY_SEGMENT_ID + " INTEGER PRIMARY KEY," + KEY_SEGMENT_NAME + " TEXT" + ")";
        //+ KEY_IMAGE_URL + " TEXT" +")";

        String CREATE_RATE_CHART_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RATE_CHART + "("
                + KEY_RATE_CHART_ID + " INTEGER PRIMARY KEY," + KEY_BASE_FARE + " TEXT,"
                + KEY_PER_KM_CHARGE + " TEXT," + KEY_PER_MIN_CHARGE + " TEXT,"
                + KEY_SEGMENT_ID + " INTEGER," + KEY_KILOMETER_ID + " INTEGER,"
                + KEY_IS_ROUND + " INTEGER"
                + ")";

        db.execSQL(CREATE_KILOMETER_TABLE);
        db.execSQL(CREATE_SEGMENT_TABLE);
        db.execSQL(CREATE_RATE_CHART_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KILOMETER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEGMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE_CHART);
        // Create tables again
        onCreate(db);
    }

    public void add_data() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < kilometer.length; i++)
                insert_kilometer(kilometer[i]);
            for (int i = 0; i < segment.length; i++)
                insert_segment(segment[i]);
            add_rate_chart();
            db.setTransactionSuccessful();
        } finally {
            // db.endTransaction();
        }
    }

    public void insert_kilometer(String km) {
        final SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KILOMETER, km);
        // Inserting Row
        db.insert(TABLE_KILOMETER, null, values);
        //db.close();
    }

    public void insert_segment(String seg_name) {
        final SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SEGMENT_NAME, seg_name);
        // Inserting Row
        db.insert(TABLE_SEGMENT, null, values);
        //db.close();
    }

    private void add_rate_chart() {

        //50 KMS TO 120 KMS & RETURN TRIP
        insert_rate_chart("100", "6", "1.5", 1, 1, 1);
        insert_rate_chart("200", "7", "1.5", 2, 1, 1);
        insert_rate_chart("200", "8", "1.5", 3, 1, 1);
        insert_rate_chart("200", "9", "1.5", 4, 1, 1);
        insert_rate_chart("200", "10", "2", 5, 1, 1);

        //ABOVE 121 KMS DROP ONLY UPTO UNLIMITED KMS & RETURN UPTO 200 KMS
        insert_rate_chart("500", "6", "1.5", 1, 2, 1);
        insert_rate_chart("600", "7", "2", 2, 2, 1);
        insert_rate_chart("700", "8", "2", 3, 2, 1);
        insert_rate_chart("700", "9", "3", 4, 2, 1);
        insert_rate_chart("800", "10", "3.5", 5, 2, 1);

        //SERVICE NOT AVAILABLE AREA RATE CHART UPTO 300 KMS ONLY FOR DROP
        insert_rate_chart("600", "6", "1", 1, 3, 0);
        insert_rate_chart("700", "7", "1.5", 2, 3, 0);
        insert_rate_chart("800", "8", "2", 3, 3, 0);
        insert_rate_chart("900", "9", "2", 4, 3, 0);
        insert_rate_chart("1000", "10", "2.5", 5, 3, 0);

        //ABOVE 200 KMS & RETURN TRIP
        insert_rate_chart("500", "6", "1", 1, 4, 1);
        insert_rate_chart("600", "7", "1", 2, 4, 1);
        insert_rate_chart("700", "8", "1", 3, 4, 1);
        insert_rate_chart("800", "9", "1", 4, 4, 1);
        insert_rate_chart("1000", "10", "1", 5, 4, 1);

        //ABOVE 300 KMS & RETURN TRIP
        insert_rate_chart("500", "6", "1", 1, 5, 1);
        insert_rate_chart("600", "7", "1", 2, 5, 1);
        insert_rate_chart("700", "8", "1", 3, 5, 1);
        insert_rate_chart("800", "9", "1", 4, 5, 1);
        insert_rate_chart("1000", "10", "1", 5, 5, 1);

        //SERVICE NOT AVAILABLE AREA RATE CHART ABOVE 400 KMS ONLY FOR DROP
        insert_rate_chart("700", "6", "1", 1, 6, 1);
        insert_rate_chart("800", "7", "1.5", 2, 6, 1);
        insert_rate_chart("900", "8", "2", 3, 6, 1);
        insert_rate_chart("1000", "9", "2", 4, 6, 1);
        insert_rate_chart("1200", "10", "2.5", 5, 6, 1);

        //ABOVE 500 KMS & RETURN TRIP
        insert_rate_chart("500", "6", "1", 1, 7, 1);
        insert_rate_chart("600", "7", "1", 2, 7, 1);
        insert_rate_chart("700", "8", "1", 3, 7, 1);
        insert_rate_chart("800", "9", "1", 4, 7, 1);
        insert_rate_chart("1000", "10", "1", 5, 7, 1);

        //SERVICE NOT AVAILABLE AREA RATE CHART ABOVE 500 KMS ONLY FOR DROP
        insert_rate_chart("800", "6", "1", 1, 8, 1);
        insert_rate_chart("900", "7", "1.5", 2, 8, 1);
        insert_rate_chart("1000", "8", "2", 3, 8, 1);
        insert_rate_chart("1200", "9", "1.5", 4, 8, 1);
        insert_rate_chart("1500", "10", "2", 5, 8, 1);

        //ABOVE 600 KMS & RETURN TRIP
        insert_rate_chart("500", "6", "1", 1, 9, 1);
        insert_rate_chart("600", "7", "1", 2, 9, 1);
        insert_rate_chart("700", "8", "1", 3, 9, 1);
        insert_rate_chart("800", "9", "1", 4, 9, 1);
        insert_rate_chart("1000", "10", "1", 5, 9, 1);

        //SERVICE NOT AVAILABLE AREA RATE CHART ABOVE 600 KMS ONLY FOR DROP
        insert_rate_chart("900", "6", "1", 1, 10, 1);
        insert_rate_chart("1000", "7", "1.5", 2, 10, 1);
        insert_rate_chart("1100", "8", "2", 3, 10, 1);
        insert_rate_chart("1200", "9", "2", 4, 10, 1);
        insert_rate_chart("1600", "10", "2", 5, 10, 1);
        UtilitySharedPreferences.setPrefs(context,"sqlite_data","inserted");
    }

    public void insert_rate_chart(String base_fare, String per_km, String per_min, int seg_id, int km_id, int is_round) {
        final SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BASE_FARE, base_fare);
        values.put(KEY_PER_KM_CHARGE, per_km);
        values.put(KEY_PER_MIN_CHARGE, per_min);
        values.put(KEY_SEGMENT_ID, seg_id);
        values.put(KEY_KILOMETER_ID, km_id);
        values.put(KEY_IS_ROUND, is_round);
        // Inserting Row
        db.insert(TABLE_RATE_CHART, null, values);
        //db.close();
    }

    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, TABLE_KILOMETER);
        //db.close();
        return cnt;
    }

    public Double getTotalAmount(int seg_id, int km_id, String km, String minutes) {
        Double total = 0.0;
        String selectQuery = "SELECT  * FROM " + TABLE_RATE_CHART + " where " + KEY_SEGMENT_ID + " = " + seg_id + " and " + KEY_KILOMETER_ID + " = " + km_id;
        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Log.d("my_tag", "getAmount: " + cursor.getString(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3));
                        //you could add additional columns here..
                        total = cursor.getInt(1) + (Double.parseDouble(cursor.getString(2)) * Double.parseDouble(km)) + (Double.parseDouble(cursor.getString(3)) * Integer.parseInt(minutes));

                    } while (cursor.moveToNext());
                }
            } finally {
                try {
                    // cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                // db.close();
            } catch (Exception ignore) {
            }
        }
        return total;
    }


}
