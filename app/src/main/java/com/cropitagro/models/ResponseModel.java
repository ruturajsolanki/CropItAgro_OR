package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("response")
    public Boolean response;

    @SerializedName("message")
    public String message;

}