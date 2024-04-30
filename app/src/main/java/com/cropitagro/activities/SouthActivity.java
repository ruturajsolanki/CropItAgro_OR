package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;

public class SouthActivity extends AppCompatActivity {

    private Button Coffee;
    private Button Tea;
    private Button Mango;
    private Button Banana;
    private Button Tomato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south);
        Mango =(Button) findViewById(R.id.Mango);
        Mango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMangoActivity();
            }
        });
        Banana =(Button) findViewById(R.id.Banana);
        Banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBananaActivity();
            }
        });
        Tomato =(Button) findViewById(R.id.Tomato);
        Tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTomatoActivity();
            }
        });
        Tea =(Button) findViewById(R.id.Tea);
        Tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTeaActivity();
            }
        });
        Coffee =(Button) findViewById(R.id.Coffee);
        Coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCoffeeActivity();
            }
        });
    }
    public void openCoffeeActivity() {
        Intent intent = new Intent(this, CoffeeActivity.class);
        startActivity(intent);
    }
    public void openTeaActivity() {
        Intent intent = new Intent(this, TeaActivity.class);
        startActivity(intent);
    }
    public void openMangoActivity() {
        Intent intent = new Intent(this, MangoActivity.class);
        startActivity(intent);
    }
    public void openBananaActivity() {
        Intent intent = new Intent(this, BananaActivity.class);
        startActivity(intent);
    }
    public void openTomatoActivity() {
        Intent intent = new Intent(this, TomatoActivity.class);
        startActivity(intent);
    }
}