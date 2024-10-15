package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class them_mon_cho_ban extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_mon_cho_ban); // Gắn layout them_mon_cho_ban

        // Xử lý sự kiện nút "Back"
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng finish() để quay lại màn hình orderban_nv
                finish();
            }
        });

        // Xử lý sự kiện nút "Đồng ý"
        Button btnDongY = findViewById(R.id.button_dongy);
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến màn hình thanhtoan_nv
                Intent intent = new Intent(them_mon_cho_ban.this, thanhtoan_nvActivity.class);
                startActivity(intent);
            }
        });
    }
}
