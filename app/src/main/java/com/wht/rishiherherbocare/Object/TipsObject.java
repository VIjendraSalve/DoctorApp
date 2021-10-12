package com.wht.rishiherherbocare.Object;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TipsObject implements Parcelable {


    private static final long serialVersionUID = 0L;
    public String id;
    public String tips;
    public String description;


    public TipsObject() {
    }

    public TipsObject(String id, String tips, String description) {
        this.id = id;
        this.tips = tips;
        this.description = description;
    }

    protected TipsObject(Parcel in) {
        id = in.readString();
        tips = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(tips);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TipsObject> CREATOR = new Creator<TipsObject>() {
        @Override
        public TipsObject createFromParcel(Parcel in) {
            return new TipsObject(in);
        }

        @Override
        public TipsObject[] newArray(int size) {
            return new TipsObject[size];
        }
    };
}
