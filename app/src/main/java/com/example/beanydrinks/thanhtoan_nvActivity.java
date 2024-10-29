package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class thanhtoan_nvActivity extends AppCompatActivity {

    private RadioButton radioButton2;
    private Button button19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv);

        ImageButton btnBack = findViewById(R.id.btnbackthemttkh);

        // Thiết lập sự kiện click cho imageButton15
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ thanhtoan_nv_2Activity sang thanhtoan_nvActivity
                Intent intent = new Intent(thanhtoan_nvActivity.this, them_mon_cho_ban.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });

        // Tìm radioButton2 và button19 từ layout
        radioButton2 = findViewById(R.id.radioButton2);
        button19 = findViewById(R.id.button19);

        // Thiết lập sự kiện click cho button19
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem radioButton2 có được chọn hay không
                if (radioButton2.isChecked()) {
                    // Tạo Intent để chuyển sang thanhtoan_nv_2Activity
                    Intent intent = new Intent(thanhtoan_nvActivity.this, thanhtoan_nv_2Activity.class);
                    startActivity(intent); // Bắt đầu Activity mới
                }
            }
        });
    }
}