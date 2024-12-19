package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;
import com.example.beanydrinks.fragment.QuanLyKhuVucNVFragment;

public class thanhtoan_nv_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv_2);

        Button button20 = findViewById(R.id.button20);

        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, orderban_nvActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, orderban_nvActivity.class);
                startActivity(intent);
            }
        });

        Button btnXacNhan = findViewById(R.id.button_XacNhan);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi sang Fragment QuanLyKhuVucNVFragment
                QuanLyKhuVucNVFragment fragment = new QuanLyKhuVucNVFragment();

                // Lấy FragmentManager và bắt đầu FragmentTransaction
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment) // fragmentContainer là ID của View chứa Fragment
                        .addToBackStack(null) // Thêm vào BackStack nếu muốn quay lại được
                        .commit();
            }
        });

    }
}