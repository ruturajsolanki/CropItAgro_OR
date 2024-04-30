package com.cropitagro.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cropitagro.R;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityAddProductBinding;
import com.cropitagro.models.ResponseModel;
import com.cropitagro.tools.Constants;
import com.cropitagro.tools.Loader;
import com.cropitagro.tools.ValidationHelper;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }

    private void save() {
        if (!ValidationHelper.isNonEmpty(binding.etName)) {
            return;
        }
        if (!ValidationHelper.isNonEmpty(binding.etDescription)) {
            return;
        }
        if (!ValidationHelper.isNonEmpty(binding.etPrice)) {
            return;
        }

        String name = binding.etName.getText().toString().trim();
        String description = binding.etDescription.getText().toString().trim();
        String price = binding.etPrice.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("sellerid", Constants.shared().get("sid"));
        params.put("product_name", name);
        params.put("product_desc", description);
        params.put("price", price);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<ResponseModel> call = connect.addProduct(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                loader.dismiss();

                try {
                    ResponseModel ResponseModel = response.body();
                    assert ResponseModel != null;

                    if (!ResponseModel.response) {
                        Toast.makeText(AddProductActivity.this, ResponseModel.message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AddProductActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    finish();

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(AddProductActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            onBackPressed();
        } else if (id == R.id.btnSave) {
            save();
        }
    }
}