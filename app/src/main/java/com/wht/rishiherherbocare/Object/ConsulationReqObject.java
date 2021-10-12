package com.wht.rishiherherbocare.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsulationReqObject implements Parcelable {

    public String id;
    public String doctor_id;
    public String user_id;
    public String age;
    public String family_member_id;
    public String isprevioushealthissue;
    public String health_issue;
    public String dr_reference;
    public String date_time;
    public String doctors_comment;
    public String clinic_name;
    public String doctor_name;
    public String degree;
    public String name;
    public String bod;
    public String relation;
    public String disease;
    public String status;

    private ArrayList<PrescriptionImageObject> prescriptionImageObjectArrayList = new ArrayList<>();
    private ArrayList<PrescriptionImageObject> reportImageObjectArrayList = new ArrayList<>();
    private ArrayList<PrescriptionImageObject> drPrescriptionImageObjectArrayList = new ArrayList<>();
    private ArrayList<SymptomsToDisplay> symptomsToDisplayArrayList = new ArrayList<>();

    public ConsulationReqObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.doctor_id = object.getString("doctor_id");
            this.user_id = object.getString("user_id");
            this.age = object.getString("age");
            this.family_member_id = object.getString("family_member_id");
            this.isprevioushealthissue = object.getString("isprevioushealthissue");
            this.health_issue = object.getString("health_issue");
            this.dr_reference = object.getString("dr_reference");
            this.date_time = object.getString("date_time");
            this.doctors_comment = object.getString("doctors_comment");
            this.clinic_name = object.getString("clinic_name");
            this.doctor_name = object.getString("doctor_name");
            this.degree = object.getString("degree");
            this.name = object.getString("name");
            this.bod = object.getString("bod");
            this.relation = object.getString("relation");
            this.disease = object.getString("disease");
            this.status = object.getString("status");

            JSONArray jsonArray = object.getJSONArray("prescription_image");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                prescriptionImageObjectArrayList.add(new PrescriptionImageObject(obj));
            }

            JSONArray jsonArray1 = object.getJSONArray("report_image");
            for (int i = 0; i <jsonArray1.length() ; i++) {
                JSONObject obj = jsonArray1.getJSONObject(i);
                reportImageObjectArrayList.add(new PrescriptionImageObject(obj));
            }


            JSONArray jsonArray2 = object.getJSONArray("symptoms_image");
            for (int i = 0; i <jsonArray2.length() ; i++) {
                JSONObject obj = jsonArray2.getJSONObject(i);
                symptomsToDisplayArrayList.add(new SymptomsToDisplay(obj));
            }

            JSONArray jsonArray3 = object.getJSONArray("dr_pre_report_image");
            for (int i = 0; i <jsonArray3.length() ; i++) {
                JSONObject obj = jsonArray3.getJSONObject(i);
                drPrescriptionImageObjectArrayList.add(new PrescriptionImageObject(obj));
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ConsulationReqObject(Parcel in) {
        id = in.readString();
        doctor_id = in.readString();
        user_id = in.readString();
        age = in.readString();
        family_member_id = in.readString();
        isprevioushealthissue = in.readString();
        health_issue = in.readString();
        dr_reference = in.readString();
        date_time = in.readString();
        doctors_comment = in.readString();
        clinic_name = in.readString();
        doctor_name = in.readString();
        degree = in.readString();
        name = in.readString();
        bod = in.readString();
        relation = in.readString();
        disease = in.readString();
        status = in.readString();
        prescriptionImageObjectArrayList = in.createTypedArrayList(PrescriptionImageObject.CREATOR);
        reportImageObjectArrayList = in.createTypedArrayList(PrescriptionImageObject.CREATOR);
        drPrescriptionImageObjectArrayList = in.createTypedArrayList(PrescriptionImageObject.CREATOR);
        symptomsToDisplayArrayList = in.createTypedArrayList(SymptomsToDisplay.CREATOR);
    }

    public static final Creator<ConsulationReqObject> CREATOR = new Creator<ConsulationReqObject>() {
        @Override
        public ConsulationReqObject createFromParcel(Parcel in) {
            return new ConsulationReqObject(in);
        }

        @Override
        public ConsulationReqObject[] newArray(int size) {
            return new ConsulationReqObject[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFamily_member_id() {
        return family_member_id;
    }

    public void setFamily_member_id(String family_member_id) {
        this.family_member_id = family_member_id;
    }

    public String getIsprevioushealthissue() {
        return isprevioushealthissue;
    }

    public void setIsprevioushealthissue(String isprevioushealthissue) {
        this.isprevioushealthissue = isprevioushealthissue;
    }

    public String getHealth_issue() {
        return health_issue;
    }

    public void setHealth_issue(String health_issue) {
        this.health_issue = health_issue;
    }

    public String getDr_reference() {
        return dr_reference;
    }

    public void setDr_reference(String dr_reference) {
        this.dr_reference = dr_reference;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDoctors_comment() {
        return doctors_comment;
    }

    public void setDoctors_comment(String doctors_comment) {
        this.doctors_comment = doctors_comment;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public ArrayList<PrescriptionImageObject> getPrescriptionImageObjectArrayList() {
        return prescriptionImageObjectArrayList;
    }

    public void setPrescriptionImageObjectArrayList(ArrayList<PrescriptionImageObject> prescriptionImageObjectArrayList) {
        this.prescriptionImageObjectArrayList = prescriptionImageObjectArrayList;
    }

    public ArrayList<PrescriptionImageObject> getReportImageObjectArrayList() {
        return reportImageObjectArrayList;
    }

    public void setReportImageObjectArrayList(ArrayList<PrescriptionImageObject> reportImageObjectArrayList) {
        this.reportImageObjectArrayList = reportImageObjectArrayList;
    }

    public ArrayList<SymptomsToDisplay> getSymptomsToDisplayArrayList() {
        return symptomsToDisplayArrayList;
    }

    public void setSymptomsToDisplayArrayList(ArrayList<SymptomsToDisplay> symptomsToDisplayArrayList) {
        this.symptomsToDisplayArrayList = symptomsToDisplayArrayList;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PrescriptionImageObject> getDrPrescriptionImageObjectArrayList() {
        return drPrescriptionImageObjectArrayList;
    }

    public void setDrPrescriptionImageObjectArrayList(ArrayList<PrescriptionImageObject> drPrescriptionImageObjectArrayList) {
        this.drPrescriptionImageObjectArrayList = drPrescriptionImageObjectArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(doctor_id);
        parcel.writeString(user_id);
        parcel.writeString(age);
        parcel.writeString(family_member_id);
        parcel.writeString(isprevioushealthissue);
        parcel.writeString(health_issue);
        parcel.writeString(dr_reference);
        parcel.writeString(date_time);
        parcel.writeString(doctors_comment);
        parcel.writeString(clinic_name);
        parcel.writeString(doctor_name);
        parcel.writeString(degree);
        parcel.writeString(name);
        parcel.writeString(bod);
        parcel.writeString(relation);
        parcel.writeString(disease);
        parcel.writeString(status);
        parcel.writeTypedList(prescriptionImageObjectArrayList);
        parcel.writeTypedList(reportImageObjectArrayList);
        parcel.writeTypedList(drPrescriptionImageObjectArrayList);
        parcel.writeTypedList(symptomsToDisplayArrayList);
    }
}
