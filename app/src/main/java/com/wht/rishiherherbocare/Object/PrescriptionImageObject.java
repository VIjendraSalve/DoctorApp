package com.wht.rishiherherbocare.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class PrescriptionImageObject implements Parcelable {

    public String name;
    public String file_name;


    public PrescriptionImageObject(JSONObject object) {
        try {
            this.name = object.getString("name");
            this.file_name = object.getString("file_name");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected PrescriptionImageObject(Parcel in) {
        name = in.readString();
        file_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(file_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PrescriptionImageObject> CREATOR = new Creator<PrescriptionImageObject>() {
        @Override
        public PrescriptionImageObject createFromParcel(Parcel in) {
            return new PrescriptionImageObject(in);
        }

        @Override
        public PrescriptionImageObject[] newArray(int size) {
            return new PrescriptionImageObject[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
