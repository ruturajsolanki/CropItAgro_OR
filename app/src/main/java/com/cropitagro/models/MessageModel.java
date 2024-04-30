package com.cropitagro.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageModel {

    @SerializedName("data")
    public List<Data> data;

    @SerializedName("response")
    public boolean response;

    public static class Data {

        @SerializedName("mid")
        public String id;

        @SerializedName("mdate")
        public String date;

        @SerializedName("mid_from_no")
        public String phone_from;

        @SerializedName("mid_To_phone")
        public String phone_to;

        @SerializedName("msg")
        public String message;
    }
}