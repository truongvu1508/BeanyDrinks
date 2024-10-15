package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class thanhtoan_nv_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv_2);

        // Tìm button20 từ layout
        Button button20 = findViewById(R.id.button20);

        // Thiết lập sự kiện click cho button20
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ thanhtoan_nv_2Activity sang thanhtoan_nvActivity
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, thanhtoan_nvActivity.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });

        // Tìm imageButton15 từ layout
        ImageButton imageButton15 = findViewById(R.id.imageButton15);

        // Thiết lập sự kiện click cho imageButton15
        imageButton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ thanhtoan_nv_2Activity sang thanhtoan_nvActivity
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, thanhtoan_nvActivity.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });
    }
}
