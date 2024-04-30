package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;

public class RagiActivity extends AppCompatActivity {
    private Button ragihome;
    private Button ragifertilizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragi);
        ragihome =(Button) findViewById(R.id.ragihome);
        ragifertilizer =(Button) findViewById(R.id.ragifertilizer);
        ragifertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopListActivity();
            }
        });


        ragihome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });
    }
    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void openShopListActivity() {
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }
}