package com.wht.rishiherherbocare.Object;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class FamilyMember {

    private String id;
    private String name;
    private String relation;
    private String age;
    private String contact;
    private String email;
    private String address;

    public FamilyMember(JSONObject obj) {
        try {
            this.id = "" + obj.getString("id");
            this.name = "" + obj.getString("name");
            this.relation = "" + obj.getString("relation");
            this.age = "" + obj.getString("age");
            this.contact = "" + obj.getString("contact");
            this.email = "" + obj.getString("email");
            this.address = "" + obj.getString("address");
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
