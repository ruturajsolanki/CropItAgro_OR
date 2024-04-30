package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AgricultureModel {

    @SerializedName("data")
    public List<Data> data;

    @SerializedName("response")
    public boolean response;

    public static class Data {

        @SerializedName("aid")
        public String id;

        @SerializedName("acrop")
        public String title;

        @SerializedName("acropdtls")
        public String details;

        @SerializedName("alatitude")
        public String latitude;

        @SerializedName("alongitude")
        public String longitude;
    }
}