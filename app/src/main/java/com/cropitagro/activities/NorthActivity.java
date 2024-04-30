package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;

public class NorthActivity extends AppCompatActivity {
    private Button Rice;
    private Button Cotton;
    private Button Wheat;
    private Button Gram;
    private Button Fodder;
    private Button Fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north);
        Gram =(Button) findViewById(R.id.Gram);
        Gram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGramActivity();
            }
        });
        Fodder =(Button) findViewById(R.id.Fodder);
        Fodder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFodderActivity();
            }
        });
        Fruit =(Button) findViewById(R.id.Fruit);
        Fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFruitActivity();
            }
        });
        Wheat =(Button) findViewById(R.id.Wheat);
        Wheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWheatActivity();
            }
        });
        Cotton =(Button) findViewById(R.id.Cotton);
        Cotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCottonActivity();
            }
        });

        Rice =(Button) findViewById(R.id.Rice);
        Rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRiceActivity();
            }
        });
    }
    public void openRiceActivity() {
        Intent intent = new Intent(this, RiceActivity.class);
        startActivity(intent);
    }
    public void openCottonActivity() {
        Intent intent = new Intent(this, CottonActivity.class);
        startActivity(intent);
    }
    public void openWheatActivity() {
        Intent intent = new Intent(this, WheatActivity.class);
        startActivity(intent);
    }
    public void openGramActivity() {
        Intent intent = new Intent(this, GramActivity.class);
        startActivity(intent);
    }
    public void openFodderActivity() {
        Intent intent = new Intent(this, FodderActivity.class);
        startActivity(intent);
    }
    public void openFruitActivity() {
        Intent intent = new Intent(this, FruitActivity.class);
        startActivity(intent);
    }
}