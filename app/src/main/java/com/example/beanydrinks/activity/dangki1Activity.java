package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;

public class dangki1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky1);

        Button button2 = findViewById(R.id.btn_TiepTheo);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangki1Activity.this, dangki2Activity.class);
                startActivity(intent);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack_Welcome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangki1Activity.this, UI_Welcome2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
