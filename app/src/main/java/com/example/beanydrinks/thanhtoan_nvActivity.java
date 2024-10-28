package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class thanhtoan_nvActivity extends AppCompatActivity {

    private RadioButton radioButton2;
    private Button button19;
    private List<DonHang> donHangList;
    private DonHangAdapter adapter; // Thay đổi adapter cho ListView
    private ListView listView; // Thay đổi thành ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv);

        ImageButton btnBack = findViewById(R.id.btnBack);

        // Thiết lập sự kiện click cho btnBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ thanhtoan_nvActivity sang them_mon_cho_ban
                Intent intent = new Intent(thanhtoan_nvActivity.this, them_mon_cho_ban.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });

        // Khởi tạo danh sách đơn hàng
        donHangList = new ArrayList<>();
        donHangList.add(new DonHang("Cafe đen", 4, "50.000 VNĐ", R.drawable.anh_cafe_den_40));
//        donHangList.add(new DonHang("Trà sữa", 1, "30.000 VNĐ", R.drawable.anh_cafe_den_40));
//        donHangList.add(new DonHang("Nước cam", 3, "60.000 VNĐ", R.drawable.anh_cafe_den_40));

        // Thiết lập ListView
        listView = findViewById(R.id.list_donhang); // Sử dụng ListView
        adapter = new DonHangAdapter(this, donHangList); // Khởi tạo adapter
        listView.setAdapter(adapter); // Gán adapter cho ListView

        // Tìm radioButton2 và button19 từ layout
//        radioButton2 = findViewById(R.id.radioButton2);
//        button19 = findViewById(R.id.button19);
//
//        // Thiết lập sự kiện click cho button19
//        button19.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Kiểm tra xem radioButton2 có được chọn hay không
//                if (radioButton2.isChecked()) {
//                    // Tạo Intent để chuyển sang thanhtoan_nv_2Activity
//                    Intent intent = new Intent(thanhtoan_nvActivity.this, thanhtoan_nv_2Activity.class);
//                    startActivity(intent); // Bắt đầu Activity mới
//                }
//            }
//        });
    }
}
