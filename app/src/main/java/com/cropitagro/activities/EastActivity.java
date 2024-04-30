package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cropitagro.R;

public class EastActivity extends AppCompatActivity {
    private Button Ragi;
    private Button Jowar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_east);
        Ragi =(Button) findViewById(R.id.Ragi);
        Jowar =(Button) findViewById(R.id.Jowar);
        Jowar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJowarActivity();
            }
        });
        Ragi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRagiActivity();
            }
        });
    }
    public void openJowarActivity() {
        Intent intent = new Intent(this, JowarActivity.class);
        startActivity(intent);
    }
    public void openRagiActivity() {
        Intent intent = new Intent(this, RagiActivity.class);
        startActivity(intent);
    }
}