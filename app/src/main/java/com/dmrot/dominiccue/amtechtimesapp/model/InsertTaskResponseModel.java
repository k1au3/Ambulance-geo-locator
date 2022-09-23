package com.dmrot.dominiccue.amtechtimesapp.model;

/**
 * Created by dominiccue on 10/13/2017.
 */

import com.google.gson.annotations.SerializedName;


public class InsertTaskResponseModel {
    @SerializedName("success")
    private int status;
    @SerializedName("message")
    private String message;

    public InsertTaskResponseModel(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public InsertTaskResponseModel() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
