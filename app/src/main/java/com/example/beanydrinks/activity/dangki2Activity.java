package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;

public class dangki2Activity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnHoanThanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky2);

        btnBack = findViewById(R.id.btn_Back_Dki1);
        btnHoanThanh = findViewById(R.id.btn_HoanThanh);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangki2Activity.this, dangki1Activity.class);
                startActivity(intent);
            }
        });

        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangki2Activity.this, UI_Login.class);
                startActivity(intent);
            }
        });

    }
}
