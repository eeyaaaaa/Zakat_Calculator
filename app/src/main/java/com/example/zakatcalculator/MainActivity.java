package com.example.zakatcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button btncalzakat = findViewById(R.id.btncalzakat);
        Button btnabout = findViewById(R.id.btnabout);

        btncalzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Link to ActivityCalculator.java
                Intent intent = new Intent(MainActivity.this, ActivityCalculator.class);
                startActivity(intent);
            }
        });

        btnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Link to AboutActivity.java
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
