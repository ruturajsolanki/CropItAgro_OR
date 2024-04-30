package com.cropitagro.tools;

import android.content.Context;
import android.content.SharedPreferences;


import com.cropitagro.models.SellerModel;
import com.cropitagro.models.UserModel;

import org.json.JSONObject;

/**
 * Created by Atirek Pothiwala on 10/17/2018.
 */

public class Constants {

    private static Constants constants;
    private final SharedPreferences sharedPreferences;

    private Constants(Context context) {
        sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
    }

    public static Constants shared() {
        if (constants == null) {
            constants = new Constants(MyApplication.shared());
        }
        return constants;
    }

    public void set(String json) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", json);
        editor.apply();
    }


    public void set(UserModel.Data model) {
        String jsonString = MyApplication.toJson(model);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", jsonString);
        editor.apply();
    }

    public void set(SellerModel.Data model) {
        String jsonString = MyApplication.toJson(model);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", jsonString);
        editor.apply();
    }


    public String get(String key) {
        try {
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString("user", ""));
            return jsonObject.optString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public UserModel.Data getUser() {
        if (sharedPreferences.contains("user")) {
            return MyApplication.fromJson(sharedPreferences.getString("user", ""), UserModel.Data.class);
        }
        return null;
    }

    public SellerModel.Data getSeller() {
        if (sharedPreferences.contains("user")) {
            return MyApplication.fromJson(sharedPreferences.getString("user", ""), SellerModel.Data.class);
        }
        return null;
    }

    public boolean exists() {
        return sharedPreferences.contains("user");
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
