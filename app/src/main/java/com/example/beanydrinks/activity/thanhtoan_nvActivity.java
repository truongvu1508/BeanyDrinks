package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.DonHangAdapter;
import com.example.beanydrinks.model.DonHang;
import com.example.beanydrinks.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class thanhtoan_nvActivity extends AppCompatActivity {

    private RadioButton radioButton_tienmat, radioButton_thanhtoanqr;
    private Button button_thanhtoan, button_huy;
    private TextView txtTienTamTinh, txtThueVAT, txtTongTien;
    private ListView listView;
    private DonHangAdapter adapter;
    private List<OrderItem> donHangList;

    private String tenKhachHang;
    private String soDienThoai;
    private double tamTinh, thueVAT, tongTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv);

        // Initialize views
        initializeViews();

        // Retrieve order and customer data from the intent
        handleIncomingIntent();

        // Initialize order list
        initializeOrderList();

        // Set up button click listeners
        setUpButtonListeners();
    }

    /**
     * Initializes all the views used in this activity.
     */
    private void initializeViews() {
        txtTienTamTinh = findViewById(R.id.txt_tienTamTinh);
        txtThueVAT = findViewById(R.id.txt_thueVAT);
        txtTongTien = findViewById(R.id.txt_tongTien);

        radioButton_tienmat = findViewById(R.id.radioButton_tienmat);
        radioButton_thanhtoanqr = findViewById(R.id.radioButton_thanhtoanqr);
        button_thanhtoan = findViewById(R.id.button_thanhtoan);
        button_huy = findViewById(R.id.button_huy);

        ImageButton btnBack = findViewById(R.id.btnbackxemtt);
        btnBack.setOnClickListener(v -> navigateToOrderBan(false));
    }

    /**
     * Handles incoming intent data for order and customer information.
     */
    private void handleIncomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            tenKhachHang = intent.getStringExtra("tenKhachHang");
            soDienThoai = intent.getStringExtra("soDienThoai");
            tamTinh = intent.getDoubleExtra("tamTinh", 0.0);
            thueVAT = intent.getDoubleExtra("thueVAT", 0.0);
            tongTien = intent.getDoubleExtra("tongTien", 0.0);
//
//            // Display customer and payment info
//            txtTienTamTinh.setText(String.format("%.2f VNĐ", tamTinh));
//            txtThueVAT.setText(String.format("%.2f VNĐ", thueVAT));
//            txtTongTien.setText(String.format("%.2f VNĐ", tongTien));

            donHangList = (List<OrderItem>) intent.getSerializableExtra("DanhSachOrder");
            if (donHangList == null) {
                donHangList = new ArrayList<>();
            }
        }
    }

    /**
     * Initializes the order list with data passed from the previous screen.
     */
    private void initializeOrderList() {
        listView = findViewById(R.id.list_donhang);
        adapter = new DonHangAdapter(this, donHangList);
        listView.setAdapter(adapter);
    }

    /**
     * Sets up listeners for buttons to handle payment actions.
     */
    private void setUpButtonListeners() {
        // Button: Complete payment
        button_thanhtoan.setOnClickListener(v -> handlePaymentMethodSelection());

        // Button: Cancel payment
        button_huy.setOnClickListener(v -> navigateToOrderBan(true));
    }

    /**
     * Handles payment method selection and navigates to the appropriate activity.
     */
    private void handlePaymentMethodSelection() {
        if (radioButton_tienmat.isChecked()) {
            // Handle cash payment
            navigateToNhanVienActivity();
        } else if (radioButton_thanhtoanqr.isChecked()) {
            // Handle QR code payment
            navigateToThanhtoanNv2Activity();
        }
    }

    /**
     * Navigates to the NhanVienActivity after completing a cash payment.
     */
    private void navigateToNhanVienActivity() {
        Intent intent = new Intent(thanhtoan_nvActivity.this, NhanVienActivity.class);
        intent.putExtra("showQuanLyKhuVuc", true); // Pass data to show QuanLyKhuVuc view
        startActivity(intent);
        finish();
    }

    /**
     * Navigates to the thanhtoan_nv_2Activity for QR code payment processing.
     */
    private void navigateToThanhtoanNv2Activity() {
        Intent intent = new Intent(thanhtoan_nvActivity.this, thanhtoan_nv_2Activity.class);
        intent.putExtra("DanhSachOrder", (ArrayList<OrderItem>) donHangList);
        intent.putExtra("tamTinh", tamTinh);
        intent.putExtra("thueVAT", thueVAT);
        intent.putExtra("tongTien", tongTien);
        startActivity(intent);
        finish();
    }

    /**
     * Navigates back to the order screen (orderban_nvActivity).
     * @param isCancel whether the navigation is due to canceling the payment or not.
     */
    private void navigateToOrderBan(boolean isCancel) {
        Intent intent = new Intent(thanhtoan_nvActivity.this, orderban_nvActivity.class);
        if (isCancel) {
            // Send back updated data if canceling
            intent.putExtra("DanhSachOrder", (ArrayList<OrderItem>) donHangList);
            intent.putExtra("tenKhachHang", tenKhachHang);
            intent.putExtra("soDienThoai", soDienThoai);
            intent.putExtra("tamTinh", tamTinh);
            intent.putExtra("thueVAT", thueVAT);
            intent.putExtra("tongTien", tongTien);
        }
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
