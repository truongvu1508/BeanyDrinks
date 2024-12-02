package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.DonHangAdapter;
import com.example.beanydrinks.model.DonHang;

import java.util.ArrayList;
import java.util.List;

public class thanhtoan_nvActivity extends AppCompatActivity {

    private RadioButton radioButton_tienmat, radioButton_thanhtoanqr;
    private Button button_thanhtoan;
    private List<DonHang> donHangList;
    private DonHangAdapter adapter;
    private ListView listView;
    private Button button_huy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv);

        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thanhtoan_nvActivity.this, orderban_nvActivity.class);
                startActivity(intent);
            }
        });


        button_huy = findViewById(R.id.button_huy);
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thanhtoan_nvActivity.this, orderban_nvActivity.class);
                startActivity(intent);
            }
        });




        // Initialize ListView and DonHang data
        donHangList = new ArrayList<>();
        donHangList.add(new DonHang("Cafe đen", 4, "50.000 VNĐ", R.drawable.anh_cafe_den_40));
        listView = findViewById(R.id.list_donhang);
        adapter = new DonHangAdapter(this, donHangList);
        listView.setAdapter(adapter);

        // Initialize RadioButtons and Button
        radioButton_tienmat = findViewById(R.id.radioButton_tienmat);
        radioButton_thanhtoanqr = findViewById(R.id.radioButton_thanhtoanqr);
        button_thanhtoan = findViewById(R.id.button_thanhtoan);

        // Set click listener for button_thanhtoan
        button_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_tienmat.isChecked()) {
                    // Navigate to NhanVienActivity with extra data
                    Intent intent = new Intent(thanhtoan_nvActivity.this, NhanVienActivity.class);
                    intent.putExtra("showQuanLyKhuVuc", true); // Pass data to show QuanLyKhuVuc view
                    startActivity(intent);
                } else if (radioButton_thanhtoanqr.isChecked()) {
                    // Navigate to thanhtoan_nv_2Activity
                    Intent intent = new Intent(thanhtoan_nvActivity.this, thanhtoan_nv_2Activity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
