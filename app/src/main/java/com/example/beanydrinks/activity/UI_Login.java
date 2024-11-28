package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beanydrinks.R;

public class UI_Login extends AppCompatActivity {
    ImageButton btnBackWelcome;
    TextView textViewDangKy;
    Button btnLogin;
    EditText editTextUser, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ui_login);

        btnBackWelcome = findViewById(R.id.btnBack_Welcome);
        textViewDangKy = findViewById(R.id.textView_DangKy);
        btnLogin = findViewById(R.id.btn_Login);
        editTextUser = findViewById(R.id.editText_User);
        editTextPass = findViewById(R.id.editText_Pass);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBackWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UI_Login.this, UI_Welcome2.class);
                startActivity(intent);
            }
        });

        textViewDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UI_Login.this, dangki1Activity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUser.getText().toString().trim();
                String password = editTextPass.getText().toString().trim();

                // Admin login
                if (username.equals("admin") && password.equals("12345")) {
                    Intent intent = new Intent(UI_Login.this, QuanLyActivity.class);
                    startActivity(intent);
                }
                // Employee login
                else if (username.equals("nhanvien") && password.equals("12345")) {
                    Intent intent = new Intent(UI_Login.this, NhanVienActivity.class);
                    startActivity(intent);
                }
                // Invalid credentials
                else {
                    Toast.makeText(UI_Login.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
