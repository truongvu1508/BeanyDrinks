package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class orderban_nv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);  // Thiết lập layout cho Activity


        View themttkhach = findViewById(R.id.themttkhach);
        themttkhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderban_nv.this,themthongtinkhachhang.class);
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
    }
}
