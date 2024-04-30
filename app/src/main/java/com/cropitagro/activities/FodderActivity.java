package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;

public class FodderActivity extends AppCompatActivity {
    private Button fodderfertilizer;
    private Button fodderhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fodder);
        fodderhome =(Button) findViewById(R.id.fodderhome);
        fodderfertilizer =(Button) findViewById(R.id.fodderfertifizer);
        fodderfertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopListActivity();
            }
        });


        fodderhome.setOnClickListener(new View.OnClickListener() {
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