package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;

public class RegionActivity extends AppCompatActivity {
    private Button north;
    private Button east;
    private Button west;
    private Button south;
    private Button soil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        north =(Button) findViewById(R.id.north);
        east =(Button) findViewById(R.id.east);
        west =(Button) findViewById(R.id.west);
        south =(Button) findViewById(R.id.south);
        soil =(Button) findViewById(R.id.soil);
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSoilActivity();
            }
        });
        south.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSouthActivity();
            }
        });
        west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWestActivity();
            }
        });
        east.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        openEastActivity();
                                    }
                                });
                north.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        {
                            openNorthActivity();
                        }
                    }
                });

    }
    public void openNorthActivity() {
        Intent intent = new Intent (this, NorthActivity.class);
        startActivity(intent);
    }
    public void openEastActivity() {
        Intent intent = new Intent (this, EastActivity.class);
        startActivity(intent);
    }
    public void openWestActivity() {
        Intent intent = new Intent (this, WestActivity.class);
        startActivity(intent);
    }
    public void openSouthActivity() {
        Intent intent = new Intent (this, SouthActivity.class);
        startActivity(intent);
    }
    public void openSoilActivity() {
        Intent intent = new Intent (this, SoilActivity.class);
        startActivity(intent);
    }
}