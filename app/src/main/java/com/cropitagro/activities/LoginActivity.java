package com.cropitagro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cropitagro.R;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityLoginBinding;
import com.cropitagro.models.SellerModel;
import com.cropitagro.models.UserModel;
import com.cropitagro.tools.Constants;
import com.cropitagro.tools.Loader;
import com.cropitagro.tools.ValidationHelper;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.cell_user_type, getResources().getStringArray(R.array.users));
        binding.spinner.setAdapter(adapter);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidationHelper.isValidPhoneNumber(binding.etPhone)) {
                    return;
                }
                if (!ValidationHelper.isNonEmpty(binding.etPassword)) {
                    return;
                }
                checkLocationPermission();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        if (Constants.shared().exists()) {
            String uid = Constants.shared().get("uid");
            if (uid.isEmpty()) {
                startActivity(new Intent(LoginActivity.this, ProductListActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
            finish();
        }

    }

    void checkLocationPermission() {
        int isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (isGranted != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 0);
            return;
        }

        int index = binding.spinner.getSelectedItemPosition();
        String userTypeString = binding.spinner.getItemAtPosition(index).toString();

        if (userTypeString.equalsIgnoreCase("customer")) {
            loginCustomer();
        } else {
            loginSeller();
        }
    }

    private void loginCustomer() {
        String phone = binding.etPhone.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<UserModel> call = connect.loginCustomer(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                loader.dismiss();

                try {
                    UserModel userModel = response.body();
                    assert userModel != null;

                    if (!userModel.response) {
                        Toast.makeText(LoginActivity.this, userModel.message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Constants.shared().set(userModel.data);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginSeller() {
        String phone = binding.etPhone.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<SellerModel> call = connect.loginSeller(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();
        call.enqueue(new Callback<SellerModel>() {
            @Override
            public void onResponse(@NonNull Call<SellerModel> call, @NonNull Response<SellerModel> response) {
                loader.dismiss();

                try {
                    SellerModel SellerModel = response.body();
                    assert SellerModel != null;
                    Log.d("onResponse>>", "JSON: " + new Gson().toJson(SellerModel));

                    if (!SellerModel.response) {
                        Toast.makeText(LoginActivity.this, SellerModel.message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Constants.shared().set(SellerModel.data);
                    startActivity(new Intent(LoginActivity.this, ProductListActivity.class));

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SellerModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}