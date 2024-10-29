package com.example.beanydrinks;

import android.content.Intent;
import android.graphics.Color;
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
                // Chuyển đến màn hình orderban_nv
                Intent intent = new Intent(them_mon_cho_ban.this, orderban_nv.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nút "Hủy"
        Button btnHuy = findViewById(R.id.button_huy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình orderban_nv
                Intent intent = new Intent(them_mon_cho_ban.this, orderban_nv.class);
                startActivity(intent);
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

        // Xử lý sự kiện cho button12
        Button button12 = findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button12.setEnabled(false); // Vô hiệu hóa button12
                button12.setBackgroundColor(Color.GRAY); // Đổi màu sang màu xám
            }
        });

        // Xử lý sự kiện cho button13
        Button button13 = findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button13.setEnabled(false); // Vô hiệu hóa button13
                button13.setBackgroundColor(Color.GRAY); // Đổi màu sang màu xám
            }
        });
    }
}
