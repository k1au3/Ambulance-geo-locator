package com.dmrot.dominiccue.amtechtimesapp.adapters;

/**
 * Created by dominiccue on 10/16/2017.
 */

import java.util.ArrayList;


public class DateAdapter {

    String tdate;
    String place;
    String patient;
    String ambulance;
    String email;

//    public DateAdapter(String tdate, String place, String patient, String ambulance, String email) {
//        this.tdate = tdate;
//        this.place = place;
//        this.patient = patient;
//        this.ambulance = ambulance;
//        this.email = email;
//    }

    public DateAdapter() {

    }

    public String getTdate() { return tdate; }

    public void setTdate(String tdate) { this.tdate = tdate; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place; }

    public String getPatient() { return patient; }

    public void setPatient(String patient) { this.patient = patient; }

    public String getAmbulance() { return ambulance; }

    public void setAmbulance(String ambulance) { this.ambulance = ambulance; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

}
