package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {

    @SerializedName("data")
    public List<Data> data;

    @SerializedName("response")
    public boolean response;

    public static class Data {

        @SerializedName("pid")
        public String pid;

        @SerializedName("pname")
        public String pname;

        @SerializedName("price")
        public String price;

        @SerializedName("pdesc")
        public String pdesc;

        @SerializedName("sid")
        public String sid;

    }
}