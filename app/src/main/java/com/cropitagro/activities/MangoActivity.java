package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;


public class MangoActivity extends AppCompatActivity {
    private Button mangohome;
    private Button mfertilizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mango);
        mangohome =(Button) findViewById(R.id.mangohome);
        mfertilizer =(Button) findViewById(R.id.mfertifizer);
        mfertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopListActivity();
            }
        });


        mangohome.setOnClickListener(new View.OnClickListener() {
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