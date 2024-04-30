package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

public class SellerModel {

    @SerializedName("response")
    public Boolean response;

    @SerializedName("data")
    public Data data;

    @SerializedName("message")
    public String message;


    public static class Data {

        @SerializedName("shop_name")
        public String name;

        @SerializedName("sid")
        public String id;

        @SerializedName("pwd")
        public String password;

        @SerializedName("address")
        public String address;

        @SerializedName("area")
        public String area;

        @SerializedName("contact")
        public String phone;

        @SerializedName("Latitude")
        public String latitude;

        @SerializedName("Longitude")
        public String longitude;

    }

}