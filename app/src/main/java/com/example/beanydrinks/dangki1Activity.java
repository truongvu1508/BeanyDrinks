package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // Hoặc ImageButton nếu bạn sử dụng hình ảnh
import androidx.appcompat.app.AppCompatActivity;

public class dangki1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky1); // Đảm bảo layout của bạn đúng

        Button button2 = findViewById(R.id.button2); // Thay ID này theo ID của button2 trong layout
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến dangki2Activity
                Intent intent = new Intent(dangki1Activity.this, dangki2Activity.class);
                startActivity(intent);
            }
        });
    }
}
