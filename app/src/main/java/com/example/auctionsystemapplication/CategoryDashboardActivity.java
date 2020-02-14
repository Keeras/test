package com.example.auctionsystemapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class CategoryDashboardActivity extends AppCompatActivity {
    private CardView cvGuitar, cvMike, cvPedal, cvOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_dashboard);
        cvGuitar = findViewById(R.id.cvGuitar);
        cvMike = findViewById(R.id.cvMike);
        cvPedal = findViewById(R.id.cvPedal);
        cvOther = findViewById(R.id.cvOther);

        cvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryDashboardActivity.this,OtherActivity.class);
                startActivity(intent);
            }
        });

        cvGuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryDashboardActivity.this,GuitarActivity.class);
                startActivity(intent);
            }
        });

        cvMike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryDashboardActivity.this,MikeActivity.class);
                startActivity(intent);
            }
        });

        cvPedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryDashboardActivity.this,PedalActivity.class);
                startActivity(intent);
            }
        });
    }
}
