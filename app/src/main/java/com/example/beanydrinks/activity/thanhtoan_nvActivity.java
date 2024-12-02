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
    private ListView listView;
    private Button button_huy;
    private DonHangAdapter adapter;
    private List<DonHang> donHangList;

    private String tenKhachHang;
    private String soDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan_nv);

        // Initialize views
        initializeViews();

        // Initialize ListView and DonHang data
        initializeDonHangList();

        // Retrieve customer data from the intent
        handleIncomingIntent();

        // Set up button click listeners
        setUpButtonListeners();
    }

    private void handleIncomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            tenKhachHang = intent.getStringExtra("tenKhachHang");
            soDienThoai = intent.getStringExtra("soDienThoai");
        }
    }

    /**
     * Initializes all the views used in this activity.
     */
    private void initializeViews() {
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> navigateToOrderBan(false));

        button_huy = findViewById(R.id.button_huy);
        button_huy.setOnClickListener(v -> navigateToOrderBan(true)); // Pass back customer data

        radioButton_tienmat = findViewById(R.id.radioButton_tienmat);
        radioButton_thanhtoanqr = findViewById(R.id.radioButton_thanhtoanqr);
        button_thanhtoan = findViewById(R.id.button_thanhtoan);
    }

    /**
     * Navigates to the order management screen (orderban_nvActivity).
     * @param isCancel whether the navigation is due to canceling the payment or not.
     */
    private void navigateToOrderBan(boolean isCancel) {
        Intent intent = new Intent(thanhtoan_nvActivity.this, orderban_nvActivity.class);
        if (isCancel) {
            // Send back the customer info if canceling
            intent.putExtra("tenKhachHang", tenKhachHang);
            intent.putExtra("soDienThoai", soDienThoai);
        }
        startActivity(intent);
    }

    /**
     * Initializes the order list with sample data.
     */
    private void initializeDonHangList() {
        donHangList = new ArrayList<>();
        donHangList.add(new DonHang("Cafe đen", 4, "50.000 VNĐ", R.drawable.anh_cafe_den_40));

        listView = findViewById(R.id.list_donhang);
        adapter = new DonHangAdapter(this, donHangList);
        listView.setAdapter(adapter);
    }

    /**
     * Sets up the listeners for the buttons in the layout.
     */
    private void setUpButtonListeners() {
        button_thanhtoan.setOnClickListener(v -> handlePaymentMethodSelection());
    }

    /**
     * Handles the payment method selection and navigates to the appropriate activity.
     */
    private void handlePaymentMethodSelection() {
        if (radioButton_tienmat.isChecked()) {
            // Navigate to NhanVienActivity with extra data
            navigateToNhanVienActivity();
        } else if (radioButton_thanhtoanqr.isChecked()) {
            // Navigate to thanhtoan_nv_2Activity for QR payment
            navigateToThanhtoanNv2Activity();
        }
    }

    /**
     * Navigates to the NhanVienActivity to handle cash payment.
     */
    private void navigateToNhanVienActivity() {
        Intent intent = new Intent(thanhtoan_nvActivity.this, NhanVienActivity.class);
        intent.putExtra("showQuanLyKhuVuc", true); // Pass data to show QuanLyKhuVuc view
        startActivity(intent);
    }

    /**
     * Navigates to the thanhtoan_nv_2Activity to handle QR payment.
     */
    private void navigateToThanhtoanNv2Activity() {
        Intent intent = new Intent(thanhtoan_nvActivity.this, thanhtoan_nv_2Activity.class);
        startActivity(intent);
    }
}
