package com.cropitagro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cropitagro.R;
import com.cropitagro.adapters.ProductListAdapter;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityProductListBinding;
import com.cropitagro.models.ProductModel;
import com.cropitagro.models.ShopModel;
import com.cropitagro.tools.Constants;
import com.cropitagro.tools.Loader;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityProductListBinding binding;
    String sid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sid = Constants.shared().get("sid");
        if (sid.isEmpty()) {
            binding.btnAdd.setVisibility(View.GONE);
            binding.btnLogout.setVisibility(View.GONE);
            binding.btnBack.setVisibility(View.VISIBLE);

            String args = getIntent().getStringExtra("shop");
            ShopModel.Data model = new Gson().fromJson(args, ShopModel.Data.class);
            binding.tvTitle.setText(model.name);
            sid = model.id;
        } else {
            binding.tvTitle.setText(Constants.shared().get("shop_name"));
            binding.btnAdd.setVisibility(View.VISIBLE);
            binding.btnLogout.setVisibility(View.VISIBLE);
            binding.btnBack.setVisibility(View.GONE);
        }

        binding.btnBack.setOnClickListener(this);
        binding.btnAdd.setOnClickListener(this);
        binding.btnLogout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }

    void getProducts() {
        API.Connect connect = Client.getClient().create(API.Connect.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("sellerid", sid);
        Call<ProductModel> call = connect.getProducts(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductModel> call, @NonNull Response<ProductModel> response) {
                loader.dismiss();

                ProductModel productModel = response.body();
                assert productModel != null;
                Log.d("onResponse>>", "JSON: " + new Gson().toJson(productModel));

                ProductListAdapter adapter = new ProductListAdapter(ProductListActivity.this, productModel.data);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(ProductListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            onBackPressed();
        } else if (id == R.id.btnAdd) {
            startActivity(new Intent(this, AddProductActivity.class));
        } else if (id == R.id.btnLogout) {
            logout();
        }
    }

    void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.shared().clear();

                        startActivity(new Intent(ProductListActivity.this, SplashActivity.class));
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}