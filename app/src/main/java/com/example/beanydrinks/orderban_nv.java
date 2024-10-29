package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class orderban_nv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);  // Thiết lập layout cho Activity

        View viewthemttkh = findViewById(R.id.viewthemttkh);
        viewthemttkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderban_nv.this, themthongtinkhachhang.class);
                startActivity(intent);
            }
        });

        // Tìm button_AddMon và thiết lập sự kiện nhấn
        Button btnAddMon = findViewById(R.id.button_AddMon);
        btnAddMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở them_mon_cho_ban Activity
                Intent intent = new Intent(orderban_nv.this, them_mon_cho_ban.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nút "Back"
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về NhanVienActivity
                Intent intent = new Intent(orderban_nv.this, NhanVienActivity.class);
                startActivity(intent);
                finish(); // Close current activity
            }
        });

        // Thiết lập sự kiện cho button_thanhtoan
        Button btnThanhToan = findViewById(R.id.button_thanhtoan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến thanhtoan_nvActivity
                Intent intent = new Intent(orderban_nv.this, thanhtoan_nvActivity.class);
                startActivity(intent);
            }
        });

        // Hiển thị tên bàn
        String tableName = getIntent().getStringExtra("ban_name");
        TextView tvTableName = findViewById(R.id.textView_Ban);
        tvTableName.setText(tableName);
    }
}
