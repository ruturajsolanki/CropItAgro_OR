package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopModel {

    @SerializedName("data")
    public List<Data> data;

    @SerializedName("response")
    public boolean response;

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