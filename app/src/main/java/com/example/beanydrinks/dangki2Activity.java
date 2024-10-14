package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class dangki2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky2); // Đảm bảo bạn đã sử dụng đúng layout

        // Tìm kiếm imageButton5
        ImageButton imageButton5 = findViewById(R.id.imageButton5); // Đảm bảo ID này khớp với ID trong XML

        // Thiết lập sự kiện nhấn
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại dangki1Activity
                Intent intent = new Intent(dangki2Activity.this, dangki1Activity.class);
                startActivity(intent);
                finish(); // Đóng dangki2Activity nếu bạn không muốn quay lại
            }
        });
    }
}
