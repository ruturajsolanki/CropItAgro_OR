package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("response")
    public Boolean response;

    @SerializedName("data")
    public Data data;

    @SerializedName("message")
    public String message;


    public static class Data {

        @SerializedName("uname")
        public String name;

        @SerializedName("uid")
        public String id;

        @SerializedName("upwd")
        public String password;

        @SerializedName("uemail")
        public String email;

        @SerializedName("ucontact")
        public String phone;

        @SerializedName("ulatitude")
        public String latitude;

        @SerializedName("ulongitude")
        public String longitude;

    }

}