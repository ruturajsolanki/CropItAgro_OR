package com.cropitagro.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cropitagro.R;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityRegisterBinding;
import com.cropitagro.models.ResponseModel;
import com.cropitagro.tools.Loader;
import com.cropitagro.tools.ValidationHelper;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.cell_user_type, getResources().getStringArray(R.array.users));
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String userTypeString = binding.spinner.getItemAtPosition(i).toString();
                if (userTypeString.equalsIgnoreCase("customer")) {
                    binding.etName.setVisibility(View.VISIBLE);
                    binding.etEmail.setVisibility(View.VISIBLE);
                    binding.etShopName.setVisibility(View.GONE);
                    binding.etAddress.setVisibility(View.GONE);
                    binding.etArea.setVisibility(View.GONE);
                } else {
                    binding.etName.setVisibility(View.GONE);
                    binding.etEmail.setVisibility(View.GONE);
                    binding.etShopName.setVisibility(View.VISIBLE);
                    binding.etAddress.setVisibility(View.VISIBLE);
                    binding.etArea.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = binding.spinner.getSelectedItemPosition();
                String userTypeString = binding.spinner.getItemAtPosition(index).toString();
                if (userTypeString.equalsIgnoreCase("customer")) {
                    registerCustomer();
                } else {
                    registerSeller();
                }
            }
        });
    }

    void registerCustomer() {
        if (!ValidationHelper.isValidString(binding.etFirstName, 3)) {
            return;
        }
        if (!ValidationHelper.isValidString(binding.etLastName, 3)) {
            return;
        }
        if (!ValidationHelper.isValidEmail(binding.etEmail)) {
            return;
        }
        if (!ValidationHelper.isValidPhoneNumber(binding.etPhone)) {
            return;
        }
        if (!ValidationHelper.isNonEmpty(binding.etPassword)) {
            return;
        }

        String firstName = binding.etFirstName.getText().toString().trim();
        String lastName = binding.etLastName.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("name", firstName + " " + lastName);
        params.put("phone", phone);
        params.put("email", email);
        params.put("password", password);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<ResponseModel> call = connect.registerCustomer(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                loader.dismiss();

                try {
                    ResponseModel responseModel = response.body();
                    assert responseModel != null;

                    if (responseModel.response) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void registerSeller() {
        if (!ValidationHelper.isNonEmpty(binding.etShopName)) {
            return;
        }
        if (!ValidationHelper.isNonEmpty(binding.etAddress)) {
            return;
        }
        if (!ValidationHelper.isNonEmpty(binding.etArea)) {
            return;
        }
        if (!ValidationHelper.isValidPhoneNumber(binding.etPhone)) {
            return;
        }
        if (!ValidationHelper.isNonEmpty(binding.etPassword)) {
            return;
        }

        String shopName = binding.etShopName.getText().toString().trim();
        String address = binding.etAddress.getText().toString().trim();
        String area = binding.etArea.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("sname", shopName);
        params.put("contact", phone);
        params.put("address", address);
        params.put("area", area);
        params.put("pwd", password);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<ResponseModel> call = connect.registerSeller(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                loader.dismiss();

                try {
                    ResponseModel responseModel = response.body();
                    assert responseModel != null;

                    if (responseModel.response) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}