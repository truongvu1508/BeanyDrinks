package com.example.beanydrinks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi chạy thanhtoan_nvActivity
        Intent intent = new Intent(MainActivity.this, thanhtoan_nvActivity.class);
        startActivity(intent);

        // Kết thúc MainActivity để không quay lại màn hình này nữa
        finish();
    }
}
