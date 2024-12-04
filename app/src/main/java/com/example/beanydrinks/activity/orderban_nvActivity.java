package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.OrderAdapter;
import com.example.beanydrinks.fragment.QuanLyKhuVucNVFragment;
import com.example.beanydrinks.model.HoaDon;
import com.example.beanydrinks.model.Mon;
import com.example.beanydrinks.model.OrderItem;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class orderban_nvActivity extends AppCompatActivity {

    private TextView textView_TenKH, textView_SDT, textView_DiemThuong, txtTienTamTinh, txtTongTien, txtThueVAT, txtTienTietKiem;
    private RecyclerView rcvOrderBan;
    private OrderAdapter orderAdapter;
    private List<OrderItem> danhSachOrder;

    private final double VAT_RATE = 0.1;

    // Variables to store customer data temporarily
    private String tenKhachHang = "Chưa có thông tin khách hàng";
    private String soDienThoai = "Chưa có số điện thoại";
    private Double diemThuong = 0.0;

    private Switch switchDungDiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderban_nv);

        // Initialize views
        textView_TenKH = findViewById(R.id.textView_TenKH);
        textView_SDT = findViewById(R.id.textView_SDT);
        textView_DiemThuong = findViewById(R.id.textView_diemThuong);
        txtTienTamTinh = findViewById(R.id.txt_tienTamTinh);
        txtTongTien = findViewById(R.id.txt_tongTien);
        txtThueVAT = findViewById(R.id.txt_thueVAT);
        txtTienTietKiem = findViewById(R.id.tientietkiem);
        rcvOrderBan = findViewById(R.id.listview_orderban);
        rcvOrderBan.setLayoutManager(new LinearLayoutManager(this));

        danhSachOrder = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, danhSachOrder, txtTienTamTinh, txtTongTien);
        rcvOrderBan.setAdapter(orderAdapter);

        // Initialize Switch for using points
        switchDungDiem = findViewById(R.id.switch_dungDiem);
        switchDungDiem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateTotalWithPoints(isChecked);
        });

        // Handle intent data
        handleIncomingIntent();

        // Button: Add item
        Button btnAddMon = findViewById(R.id.button_AddMon);
        btnAddMon.setOnClickListener(v -> {
            Intent intent = new Intent(orderban_nvActivity.this, them_mon_cho_banActivity.class);

            // Gửi danh sách các món đã chọn
            intent.putExtra("DanhSachMonDaChon", (ArrayList<OrderItem>) danhSachOrder);
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
            // Thực hiện insert hóa đơn vào server
            HoaDon hoaDon = new HoaDon();
            hoaDon.setIdBan(1); // Giả sử bạn có idBan
            hoaDon.setIdNhanVien(2); // Giả sử bạn có idNhanVien
            hoaDon.setIdKhachHang(2); // Giả sử bạn có idKhachHang
            hoaDon.setTamTinh(Double.parseDouble(txtTienTamTinh.getText().toString().replace(" VNĐ", "")));
            hoaDon.setThueVAT(Double.parseDouble(txtThueVAT.getText().toString().replace(" VNĐ", "")));
            hoaDon.setTongTien(Double.parseDouble(txtTongTien.getText().toString().replace(" VNĐ", "")));

            // Gọi hàm insertHoaDonToServer để gửi hóa đơn lên server
            insertHoaDonToServer(hoaDon);

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

            // Lấy điểm thưởng từ intent và xử lý nếu cần
            diemThuong = intent.getDoubleExtra("diem", 0.0);

            textView_TenKH.setText(tenKhachHang != null ? tenKhachHang : "Chưa có thông tin khách hàng");
            textView_SDT.setText(soDienThoai != null ? soDienThoai : "Chưa có số điện thoại");
            textView_DiemThuong.setText(String.format("%.0f", diemThuong));  // Hiển thị điểm với định dạng

            List<OrderItem> receivedOrders = (List<OrderItem>) intent.getSerializableExtra("DanhSachMon");
            if (receivedOrders != null) {
                danhSachOrder.clear();
                danhSachOrder.addAll(receivedOrders);
                orderAdapter.notifyDataSetChanged();
                updateTamTinh();
            }

            double tamTinh = intent.getDoubleExtra("tamTinh", 0.0);
            double thueVAT = intent.getDoubleExtra("thueVAT", 0.0);
            double tongTien = intent.getDoubleExtra("tongTien", 0.0);
            double disscount = intent.getDoubleExtra("disscount", 0.0);

            txtTienTamTinh.setText(String.format("%.2f VNĐ", tamTinh));
            txtThueVAT.setText(String.format("%.2f VNĐ", thueVAT));
            textView_DiemThuong.setText(String.format("%.2f VNĐ", disscount));
            txtTongTien.setText(String.format("%.2f VNĐ", tongTien));
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

    private void updateTotalWithPoints(boolean isUsingPoints) {
        double tamTinh = 0.0;
        try {
            tamTinh = Double.parseDouble(txtTienTamTinh.getText().toString().replace(" VNĐ", ""));
        } catch (NumberFormatException e) {
            tamTinh = 0.0;
        }

        // Trừ điểm thưởng nếu sử dụng điểm
        double discount = 0.0;
        if (isUsingPoints && diemThuong > 0) {
            discount = diemThuong; // Giả sử mỗi điểm = 100 VNĐ
            if (discount > tamTinh) {
                discount = tamTinh;  // Không trừ vượt quá tổng tiền
            }
        }

        // Cập nhật tiền tiết kiệm
        txtTienTietKiem.setText(String.format("%.2f VNĐ", discount));

        // Cập nhật lại tổng tiền sau khi trừ điểm
        double totalAfterDiscount = tamTinh - discount;

        // Tính lại VAT
        CheckBox checkBoxVAT = findViewById(R.id.checkBox);
        calculateTotalWithVAT(checkBoxVAT.isChecked());

        // Cập nhật lại tổng tiền cuối cùng
        txtTongTien.setText(String.format("%.2f VNĐ", totalAfterDiscount));
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
                for (OrderItem newOrder : newOrders) {
                    // Kiểm tra nếu món đã tồn tại, thì chỉ cập nhật số lượng
                    boolean exists = false;
                    for (OrderItem existingOrder : danhSachOrder) {
                        if (existingOrder.getSanPham().getIdSanPham().equals(newOrder.getSanPham().getIdSanPham())) {
                            existingOrder.setSoLuong(existingOrder.getSoLuong() + newOrder.getSoLuong());
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        danhSachOrder.add(newOrder);
                    }
                }
                orderAdapter.notifyDataSetChanged();
                updateTamTinh();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            tenKhachHang = data.getStringExtra("tenKhachHang");
            soDienThoai = data.getStringExtra("soDienThoai");
            diemThuong = data.getDoubleExtra("diem", 0.0);
            Log.d("OrderBanNV", "Received diemThuong in onActivityResult: " + diemThuong);
            textView_TenKH.setText(tenKhachHang);
            textView_SDT.setText(soDienThoai);
            textView_DiemThuong.setText(String.format("%.0f", diemThuong));
        }

        if (requestCode == 3 && resultCode == RESULT_CANCELED && data != null) {
            // Nhận lại danh sách order
            List<OrderItem> updatedOrders = (List<OrderItem>) data.getSerializableExtra("DanhSachOrder");
            if (updatedOrders != null) {
                danhSachOrder.clear();
                danhSachOrder.addAll(updatedOrders);
                orderAdapter.notifyDataSetChanged();
            }

            // Nhận lại các thông tin khác
            tenKhachHang = data.getStringExtra("tenKhachHang");
            soDienThoai = data.getStringExtra("soDienThoai");
            textView_TenKH.setText(tenKhachHang);
            textView_SDT.setText(soDienThoai);

            double tamTinh = data.getDoubleExtra("tamTinh", 0);
            double thueVAT = data.getDoubleExtra("thueVAT", 0);
            double disscount = data.getDoubleExtra("tongTien", 0);
            double tongTien = data.getDoubleExtra("tongTien", 0);

            txtTienTamTinh.setText(String.format("%.2f VNĐ", tamTinh));
            txtThueVAT.setText(String.format("%.2f VNĐ", thueVAT));
            textView_DiemThuong.setText(String.format("%.2f VNĐ", disscount));
            txtTongTien.setText(String.format("%.2f VNĐ", tongTien));
        }
    }
    private void insertHoaDonToServer(HoaDon hoaDon) {
        String url = Server.DuongDanInsertHoaDon;

        // Lấy thời gian hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        // Tạo đối tượng JSON cho hóa đơn
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idBan", hoaDon.getIdBan());
            jsonObject.put("idNhanVien", hoaDon.getIdNhanVien());
            jsonObject.put("idKhachHang", hoaDon.getIdKhachHang());
            jsonObject.put("tamTinh", hoaDon.getTamTinh());
            jsonObject.put("thueVAT", hoaDon.getThueVAT());
            jsonObject.put("tongTien", hoaDon.getTongTien());
            jsonObject.put("thoiGian", currentTime); // Thêm thời gian hiện tại

            // Thêm danh sách sản phẩm vào hóa đơn
            JSONArray orderItemsArray = new JSONArray();
            for (OrderItem orderItem : danhSachOrder) {
                JSONObject itemObject = new JSONObject();
                itemObject.put("idSanPham", orderItem.getSanPham().getIdSanPham());
                itemObject.put("soLuong", orderItem.getSoLuong());
                itemObject.put("thanhTien", orderItem.getThanhTien());
                orderItemsArray.put(itemObject);
            }
            jsonObject.put("products", orderItemsArray);

            // Log JSON gửi lên server để kiểm tra
            Log.d("JSON Request", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error preparing data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi request đến server
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(this, "Hóa đơn được thêm thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Thêm hóa đơn thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
                });

        // Thêm request vào hàng đợi
        requestQueue.add(jsonObjectRequest);
    }


    private void returnResult() {
        Intent intent = new Intent(this, QuanLyKhuVucNVFragment.class); // Hoặc activity khác
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}