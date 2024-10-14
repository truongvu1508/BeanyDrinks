package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class them_mon_cho_ban extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_mon_cho_ban); // Gắn layout them_mon_cho_ban

        // Tìm imageButton11 trong layout
        ImageButton imageButton11 = findViewById(R.id.imageButton11);
        imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để quay lại MainActivity và hiển thị orderban_nv
                Intent intent = new Intent(them_mon_cho_ban.this, MainActivity.class);
                intent.putExtra("showOrderBanNv", true); // Truyền dữ liệu để biết cần hiển thị lại Fragment orderban_nv
                startActivity(intent);
            }
        });
    }
}
