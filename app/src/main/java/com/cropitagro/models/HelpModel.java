package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelpModel {

    @SerializedName("data")
    public List<Data> data;

    @SerializedName("response")
    public boolean response;

    public static class Data {

        @SerializedName("hid")
        public String id;

        @SerializedName("htitle")
        public String title;

        @SerializedName("hdetails")
        public String details;

        @SerializedName("hcontact")
        public String phone;

    }
}