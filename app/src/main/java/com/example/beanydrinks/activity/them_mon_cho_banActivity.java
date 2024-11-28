package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.ChonMonAdapter;
import com.example.beanydrinks.model.Mon;

import java.util.ArrayList;
import java.util.List;

public class them_mon_cho_banActivity extends AppCompatActivity {

    private RecyclerView rcvChonMon;
    private ChonMonAdapter chonMonAdapter;
    private List<Mon> monList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_mon_cho_ban);

        // Initialize the RecyclerView
        rcvChonMon = findViewById(R.id.rcv_chonmon);
        rcvChonMon.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list and adapter
        monList = new ArrayList<>();
        chonMonAdapter = new ChonMonAdapter(this); // Chỉ truyền context vào constructor
        rcvChonMon.setAdapter(chonMonAdapter);

        // Load the data
        loadMonData();

        // Back button listener
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });

        // Cancel button listener
        Button btnHuy = findViewById(R.id.button_huy);
        btnHuy.setOnClickListener(v -> {
            Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });

        // Confirm button listener
        Button btnDongY = findViewById(R.id.button_dongy);
        btnDongY.setOnClickListener(v -> {
            Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });
    }

    private void loadMonData() {
        // Sample data - replace with your actual data source
        monList.add(new Mon("Cà phê", "1", "Cà phê đen", "22.000 VNĐ", R.drawable.cafe_04));
        monList.add(new Mon("Cà phê", "2", "Cà phê sữa", "22.000 VNĐ", R.drawable.cafe_03));
        monList.add(new Mon("Nước ép", "3", "Nước ép cam", "22.000 VNĐ", R.drawable.nuoc_ep_cam));

        chonMonAdapter.setMonList(monList); // Cập nhật danh sách món trong adapter
    }
}