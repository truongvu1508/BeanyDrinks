package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.OrderAdapter;
import com.example.beanydrinks.fragment.QuanLyKhuVucNVFragment;
import com.example.beanydrinks.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class orderban_nvActivity extends AppCompatActivity {

    private TextView textView_TenKH, textView_SDT, txtTienTamTinh, txtTongTien, txtThueVAT;
    private ListView listViewOrderBan;
    private OrderAdapter orderAdapter;
    private List<OrderItem> danhSachOrder;

    private final double VAT_RATE = 0.1;

    // Variables to store customer data temporarily
    private String tenKhachHang = "Chưa có thông tin khách hàng";
    private String soDienThoai = "Chưa có số điện thoại";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);

        // Initialize views
        textView_TenKH = findViewById(R.id.textView_TenKH);
        textView_SDT = findViewById(R.id.textView_SDT);
        txtTienTamTinh = findViewById(R.id.txt_tienTamTinh);
        txtTongTien = findViewById(R.id.txt_tongTien);
        txtThueVAT = findViewById(R.id.txt_thueVAT);
        listViewOrderBan = findViewById(R.id.listview_orderban);

        danhSachOrder = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, danhSachOrder, txtTienTamTinh, txtTongTien);
        listViewOrderBan.setAdapter(orderAdapter);

        // Handle intent data
        handleIncomingIntent();

        // Button: Add item
        Button btnAddMon = findViewById(R.id.button_AddMon);
        btnAddMon.setOnClickListener(v -> {
            Intent intent = new Intent(orderban_nvActivity.this, them_mon_cho_banActivity.class);
            startActivityForResult(intent, 1);
        });

        // Button: Back
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(orderban_nvActivity.this, QuanLyKhuVucNVFragment.class);
            startActivity(intent);
        });

        // Button: Thanh toán
        Button btnThanhToan = findViewById(R.id.button_thanhtoan);
        btnThanhToan.setOnClickListener(v -> {
            Intent intent = new Intent(orderban_nvActivity.this, thanhtoan_nvActivity.class);
            intent.putExtra("DanhSachOrder", (ArrayList<OrderItem>) danhSachOrder);
            intent.putExtra("tenKhachHang", tenKhachHang);
            intent.putExtra("soDienThoai", soDienThoai);
            startActivity(intent);
        });

        // TextView: Add customer info
        TextView textView_ThemKH = findViewById(R.id.textView_ThemKH);
        textView_ThemKH.setOnClickListener(v -> {
            Intent intent = new Intent(orderban_nvActivity.this, themthongtinkhachhangActivity.class);
            startActivityForResult(intent, 2);
        });

        // Checkbox: VAT
        CheckBox checkBoxVAT = findViewById(R.id.checkBox);
        checkBoxVAT.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalWithVAT(isChecked));
    }

    private void handleIncomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            tenKhachHang = intent.getStringExtra("tenKhachHang");
            soDienThoai = intent.getStringExtra("soDienThoai");

            // Set customer info in TextViews
            textView_TenKH.setText(tenKhachHang != null ? tenKhachHang : "Chưa có thông tin khách hàng");
            textView_SDT.setText(soDienThoai != null ? soDienThoai : "Chưa có số điện thoại");

            List<OrderItem> receivedOrders = (List<OrderItem>) intent.getSerializableExtra("DanhSachMon");
            if (receivedOrders != null) {
                danhSachOrder.addAll(receivedOrders);
                updateTamTinh();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tenKhachHang", tenKhachHang);
        outState.putString("soDienThoai", soDienThoai);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            tenKhachHang = savedInstanceState.getString("tenKhachHang", "Chưa có thông tin khách hàng");
            soDienThoai = savedInstanceState.getString("soDienThoai", "Chưa có số điện thoại");
            textView_TenKH.setText(tenKhachHang);
            textView_SDT.setText(soDienThoai);
        }
    }

    private void calculateTotalWithVAT(boolean isVATChecked) {
        double tamTinh = 0.0;
        try {
            tamTinh = Double.parseDouble(txtTienTamTinh.getText().toString().replace(" VNĐ", ""));
        } catch (NumberFormatException e) {
            tamTinh = 0.0;
        }

        if (isVATChecked) {
            double vatAmount = tamTinh * VAT_RATE;
            double totalWithVAT = tamTinh + vatAmount;
            txtThueVAT.setText(String.format("%.2f VNĐ", vatAmount));
            txtTongTien.setText(String.format("%.2f VNĐ", totalWithVAT));
        } else {
            txtThueVAT.setText("0 VNĐ");
            txtTongTien.setText(String.format("%.2f VNĐ", tamTinh));
        }
    }

    public void updateTamTinh() {
        double tamTinh = 0.0;
        for (OrderItem orderItem : danhSachOrder) {
            tamTinh += orderItem.getThanhTien();
        }
        txtTienTamTinh.setText(String.format("%.2f VNĐ", tamTinh));
        CheckBox checkBoxVAT = findViewById(R.id.checkBox);
        calculateTotalWithVAT(checkBoxVAT.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<OrderItem> newOrders = (List<OrderItem>) data.getSerializableExtra("DanhSachMon");
            if (newOrders != null) {
                danhSachOrder.addAll(newOrders);
                orderAdapter.notifyDataSetChanged();
                updateTamTinh();
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            tenKhachHang = data.getStringExtra("tenKhachHang");
            soDienThoai = data.getStringExtra("soDienThoai");
            textView_TenKH.setText(tenKhachHang);
            textView_SDT.setText(soDienThoai);
        }
    }
}
