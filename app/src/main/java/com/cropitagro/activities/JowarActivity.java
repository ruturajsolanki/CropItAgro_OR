package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;


public class JowarActivity extends AppCompatActivity {
    private Button jowarhome;
    private Button jowarfertilizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jowar);
        jowarhome =(Button) findViewById(R.id.jowarhome);
        jowarfertilizer =(Button) findViewById(R.id.jowarfertifizer);
        jowarfertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopListActivity();
            }
        });


        jowarhome.setOnClickListener(new View.OnClickListener() {
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