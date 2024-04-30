package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;


public class TomatoActivity extends AppCompatActivity {
    private Button tomaatohome;
    private Button tfertilizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato);
        tomaatohome =(Button) findViewById(R.id.fodderhome);
        tfertilizer =(Button) findViewById(R.id.tfertifizer);
        tfertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopListActivity();
            }
        });


        tomaatohome.setOnClickListener(new View.OnClickListener() {
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