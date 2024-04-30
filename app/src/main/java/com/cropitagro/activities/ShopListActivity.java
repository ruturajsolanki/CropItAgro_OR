package com.cropitagro.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cropitagro.adapters.ShopListAdapter;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityShopListBinding;
import com.cropitagro.models.ShopModel;
import com.cropitagro.tools.Loader;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListActivity extends AppCompatActivity {

    ActivityShopListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    String query = binding.etSearch.getText().toString().trim();
                    if (query.isEmpty()) {
                        getShops();
                    } else {
                        getShopsByArea(query);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShops();
    }

    void getShops() {
        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<ShopModel> call = connect.getShops(new HashMap<>());

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<ShopModel>() {
            @Override
            public void onResponse(@NonNull Call<ShopModel> call, @NonNull Response<ShopModel> response) {
                loader.dismiss();

                ShopModel shopModel = response.body();
                assert shopModel != null;
                Log.d("onResponse>>", "JSON: " + new Gson().toJson(shopModel));

                ShopListAdapter adapter = new ShopListAdapter(ShopListActivity.this, shopModel.data);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ShopModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(ShopListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getShopsByArea(String area) {
        API.Connect connect = Client.getClient().create(API.Connect.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("area", area);
        Call<ShopModel> call = connect.getShopsByArea(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<ShopModel>() {
            @Override
            public void onResponse(@NonNull Call<ShopModel> call, @NonNull Response<ShopModel> response) {
                loader.dismiss();

                ShopModel shopModel = response.body();
                assert shopModel != null;
                Log.d("onResponse>>", "JSON: " + new Gson().toJson(shopModel));

                ShopListAdapter adapter = new ShopListAdapter(ShopListActivity.this, shopModel.data);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ShopModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(ShopListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}