package com.wht.rishiherherbocare.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MultipartBody;



public class ImagePOJO implements Parcelable{
    private String img_id;
    private String img_name;
    private MultipartBody.Part image_path_multipart;
    private String img_path;

    public ImagePOJO(String img_id, String img_name, MultipartBody.Part image_path_multipart, String img_path) {
        this.img_id     = img_id;
        this.img_name   = img_name;
        this.img_path   = img_path;
        this.image_path_multipart = image_path_multipart;

    }

    public ImagePOJO(JSONObject jsonObject) {
        try {
            this.img_id=""+jsonObject.getString("doc_image_id");
            this.img_name=""+jsonObject.getString("doc_image_name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.img_path="";
    }

    protected ImagePOJO(Parcel in) {
        img_id = in.readString();
        img_name = in.readString();
        img_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img_id);
        dest.writeString(img_name);
        dest.writeString(img_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImagePOJO> CREATOR = new Creator<ImagePOJO>() {
        @Override
        public ImagePOJO createFromParcel(Parcel in) {
            return new ImagePOJO(in);
        }

        @Override
        public ImagePOJO[] newArray(int size) {
            return new ImagePOJO[size];
        }
    };

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public MultipartBody.Part getImage_path_multipart() {
        return image_path_multipart;
    }

    public void setImage_path_multipart(MultipartBody.Part image_path_multipart) {
        this.image_path_multipart = image_path_multipart;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
