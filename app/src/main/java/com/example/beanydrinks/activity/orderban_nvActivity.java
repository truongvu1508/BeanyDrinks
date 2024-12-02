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
import com.example.beanydrinks.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class orderban_nvActivity extends AppCompatActivity {
    private ListView listViewOrderBan;
    private OrderAdapter orderAdapter;
    private List<OrderItem> danhSachOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);

        listViewOrderBan = findViewById(R.id.listview_orderban);
        danhSachOrder = new ArrayList<>();

        // Nhận dữ liệu từ them_mon_cho_banActivity
        Intent intent = getIntent();
        List<OrderItem> receivedOrders = (List<OrderItem>) intent.getSerializableExtra("DanhSachMon");
        if (receivedOrders != null) {
            danhSachOrder.addAll(receivedOrders);
            updateTamTinh();  // Cập nhật tạm tính và tổng tiền ngay sau khi nhận dữ liệu
        }

        // Khởi tạo adapter
        orderAdapter = new OrderAdapter(this, danhSachOrder);
        listViewOrderBan.setAdapter(orderAdapter);

        // Nút thêm món
        Button btnAddMon = findViewById(R.id.button_AddMon);
        btnAddMon.setOnClickListener(v -> {
            Intent addIntent = new Intent(orderban_nvActivity.this, them_mon_cho_banActivity.class);
            startActivityForResult(addIntent, 1); // Chuyển sang them_mon_cho_banActivity
        });

        // Nút quay lại
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> finish());


        // Checkbox VAT
        CheckBox checkBoxVAT = findViewById(R.id.checkBox);
        TextView txtTamTinh = findViewById(R.id.txt_tienTamTinh);
        TextView txtTongTien = findViewById(R.id.txt_tongTien);
        TextView txtThueVAT = findViewById(R.id.txt_thueVAT);

        final double VAT_RATE = 0.1;


        checkBoxVAT.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Get current temporary total
            double tamTinh = 0.0;
            try {
                tamTinh = Double.parseDouble(txtTamTinh.getText().toString().replace(" VNĐ", "").replace(" đ", ""));
            } catch (NumberFormatException e) {
                tamTinh = 0.0;
            }

            // Calculate VAT if checked
            if (isChecked) {
                double vatAmount = tamTinh * VAT_RATE;
                double totalWithVAT = tamTinh + vatAmount;
                txtThueVAT.setText(String.format("%.2f VNĐ", vatAmount));
                txtTongTien.setText(String.format("%.2f VNĐ", totalWithVAT));
            } else {
                txtThueVAT.setText(String.format("0 VNĐ"));
                txtTongTien.setText(String.format("%.2f VNĐ", tamTinh));
            }
        });

        Button btnThanhToan = findViewById(R.id.button_thanhtoan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(orderban_nvActivity.this, thanhtoan_nvActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<OrderItem> newOrders = (List<OrderItem>) data.getSerializableExtra("DanhSachMon");
            if (newOrders != null) {
                danhSachOrder.addAll(newOrders);  // Thêm món mới vào danh sách đơn hàng
                orderAdapter.notifyDataSetChanged();  // Cập nhật giao diện
                updateTamTinh();  // Cập nhật lại tạm tính và tổng tiền sau khi thêm món
            }
        }
    }

    public void updateTamTinh() {
        double tamTinh = 0.0;
        for (OrderItem orderItem : danhSachOrder) {
            tamTinh += orderItem.getThanhTien();
        }

        // Cập nhật TextView tạm tính
        TextView txtTamTinh = findViewById(R.id.txt_tienTamTinh);
        txtTamTinh.setText(String.format("%.2f VNĐ", tamTinh));

        // Tính và hiển thị VAT
        CheckBox checkBoxVAT = findViewById(R.id.checkBox);
        TextView txtTongTien = findViewById(R.id.txt_tongTien);
        TextView txtThueVAT = findViewById(R.id.txt_thueVAT);

        if (checkBoxVAT.isChecked()) {
            double vatAmount = tamTinh * 0.1;  // Tính VAT 10%
            double totalWithVAT = tamTinh + vatAmount;

            txtThueVAT.setText(String.format("%.2f VNĐ", vatAmount));  // Hiển thị thuế VAT
            txtTongTien.setText(String.format("%.2f VNĐ", totalWithVAT));  // Cập nhật tổng tiền với VAT
        } else {
            txtThueVAT.setText("0 VNĐ");  // Không áp dụng VAT
            txtTongTien.setText(String.format("%.2f VNĐ", tamTinh));  // Cập nhật tổng tiền không có VAT
        }
    }

}
