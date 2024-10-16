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

        Button button20 = findViewById(R.id.button20);

        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, thanhtoan_nvActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, thanhtoan_nvActivity.class);
                startActivity(intent);
            }
        });

        Button btnXacNhan = findViewById(R.id.button_XacNhan);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thanhtoan_nv_2Activity.this, NhanVienActivity.class);
                intent.putExtra("showQuanLyKhuVuc", true);
                startActivity(intent);
            }
        });

    }
}