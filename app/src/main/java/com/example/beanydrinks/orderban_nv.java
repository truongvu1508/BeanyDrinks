package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class orderban_nv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);  // Thiết lập layout cho Activity

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

        String tableName = getIntent().getStringExtra("ban_name");

        // Assuming you have a TextView to show the table name
        TextView tvTableName = findViewById(R.id.textView_Ban); // Replace with your TextView ID
        tvTableName.setText(tableName);
    }
}
