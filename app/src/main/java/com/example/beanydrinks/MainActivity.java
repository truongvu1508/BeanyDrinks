package com.example.beanydrinks;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Layout này sẽ chứa fragment

        // Kiểm tra xem fragment đã được thêm vào chưa để tránh thêm lại trên xoay
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new orderban_nv()) // Sử dụng Fragment mới tạo
                    .commit();
        }
    }
}
