package com.wht.rishiherherbocare.Object;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SymptomsToDisplay implements Parcelable {

    private String id;
    private String name;


    public SymptomsToDisplay(JSONObject obj) {
        try {
            this.id = "" + obj.getString("symptoms_id");
            this.name = "" + obj.getString("complaint");

        } catch (JSONException e) {
            Log.e("Exception", "Exception: " + e.getMessage());
        }
    }

    protected SymptomsToDisplay(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SymptomsToDisplay> CREATOR = new Creator<SymptomsToDisplay>() {
        @Override
        public SymptomsToDisplay createFromParcel(Parcel in) {
            return new SymptomsToDisplay(in);
        }

        @Override
        public SymptomsToDisplay[] newArray(int size) {
            return new SymptomsToDisplay[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }
}
