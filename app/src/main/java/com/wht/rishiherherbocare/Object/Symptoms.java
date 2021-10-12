package com.wht.rishiherherbocare.Object;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Symptoms {

    private String id;
    private String name;


    public Symptoms(JSONObject obj) {
        try {
            this.id = "" + obj.getString("id");
            this.name = "" + obj.getString("name");

        } catch (JSONException e) {
            Log.e("Exception", "Exception: " + e.getMessage());
        }
    }

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
