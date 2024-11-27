package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.adapter.OrderAdapter;
import com.example.beanydrinks.model.OrderBan;

import java.util.ArrayList;
import java.util.List;

public class orderban_nv extends AppCompatActivity {
    private ListView listViewOrderBan;
    private OrderAdapter orderAdapter;
    private List<OrderBan> danhSachOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);

        TextView textView_ThemKH = findViewById(R.id.textView_ThemKH);
        textView_ThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderban_nv.this, themthongtinkhachhang.class);
                startActivity(intent);
            }
        });

        listViewOrderBan = findViewById(R.id.listview_orderban);
        danhSachOrder = new ArrayList<>();

        // Thêm một số món vào danh sách
        danhSachOrder.add(new OrderBan("Cà phê đen", "22.000 VNĐ", 4, R.drawable.cafe_01));
        danhSachOrder.add(new OrderBan("Cà phê sữa", "25.000 VNĐ", 2, R.drawable.cafe_02));

        // Khởi tạo adapter
        orderAdapter = new OrderAdapter(this, danhSachOrder);
        listViewOrderBan.setAdapter(orderAdapter);

        Button btnAddMon = findViewById(R.id.button_AddMon);
        btnAddMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orderban_nv.this, them_mon_cho_ban.class);
                startActivity(intent);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orderban_nv.this, NhanVienActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnThanhToan = findViewById(R.id.button_thanhtoan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orderban_nv.this, thanhtoan_nvActivity.class);
                startActivity(intent);
            }
        });
    }
}
