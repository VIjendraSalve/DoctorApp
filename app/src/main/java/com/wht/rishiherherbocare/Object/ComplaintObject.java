package com.wht.rishiherherbocare.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ComplaintObject implements Parcelable {

    public String complaint_id;
    public String complaint;
    public String duration;
    public String consult_id;


    public ComplaintObject() {
    }

    public ComplaintObject(String complaint_id, String complaint, String duration, String consult_id) {
        this.complaint_id = complaint_id;
        this.complaint = complaint;
        this.duration = duration;
        this.consult_id = consult_id;
    }

    public ComplaintObject(JSONObject object) {
        try {
            this.complaint_id = object.getString("id");
            this.complaint = object.getString("complaint");
            this.duration = object.getString("duration");
            this.consult_id = object.getString("consult_id");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected ComplaintObject(Parcel in) {
        complaint_id = in.readString();
        complaint = in.readString();
        duration = in.readString();
        consult_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(complaint_id);
        dest.writeString(complaint);
        dest.writeString(duration);
        dest.writeString(consult_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ComplaintObject> CREATOR = new Creator<ComplaintObject>() {
        @Override
        public ComplaintObject createFromParcel(Parcel in) {
            return new ComplaintObject(in);
        }

        @Override
        public ComplaintObject[] newArray(int size) {
            return new ComplaintObject[size];
        }
    };

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConsult_id() {
        return consult_id;
    }

    public void setConsult_id(String consult_id) {
        this.consult_id = consult_id;
    }
}
