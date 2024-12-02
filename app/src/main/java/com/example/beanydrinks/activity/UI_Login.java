package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.model.UserSession;
import com.example.beanydrinks.ultil.MySingleton;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UI_Login extends AppCompatActivity { // URL của file PHP
    private static final String TAG = "UI_Login";

    private ImageButton btnBackWelcome;
    private TextView textViewDangKy;
    private Button btnLogin;
    private EditText editTextUser, editTextPass;
    private ArrayList<NhanVien> nhanVienList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_login);

        // Khởi tạo các view
        initViews();

        // Danh sách nhân viên
        nhanVienList = new ArrayList<>();

        // Gọi API để lấy dữ liệu nhân viên
        fetchNhanVienData();

        // Xử lý các sự kiện
        setupEventListeners();
    }

    private void initViews() {
        btnBackWelcome = findViewById(R.id.btnBack_Welcome);
        textViewDangKy = findViewById(R.id.textView_DangKy);
        btnLogin = findViewById(R.id.btn_Login);
        editTextUser = findViewById(R.id.editText_User);
        editTextPass = findViewById(R.id.editText_Pass);
    }

    private void setupEventListeners() {
        // Sự kiện quay lại
        btnBackWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(UI_Login.this, UI_Welcome2.class);
            startActivity(intent);
        });

        // Sự kiện đăng ký tài khoản mới
        textViewDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(UI_Login.this, dangki1Activity.class);
            startActivity(intent);
        });

        // Sự kiện đăng nhập
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = editTextUser.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(UI_Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isAuthenticated = false;

        for (NhanVien nhanVien : nhanVienList) {
            // Kiểm tra số điện thoại và mật khẩu
            if (nhanVien.getSoDienThoai().equals(username) && nhanVien.getMatKhau().equals(password)) {
                isAuthenticated = true;

                // Lưu thông tin người dùng vào session
                onLoginSuccess(nhanVien);
                break;
            }
        }

        if (!isAuthenticated) {
            Toast.makeText(UI_Login.this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoginSuccess(NhanVien nhanVien) {
        // Lưu thông tin người dùng vào session
        UserSession.getInstance().setCurrentUser(nhanVien);

        // Phân quyền và chuyển đến màn hình chính của người dùng
        Intent intent;
        if ("admin".equalsIgnoreCase(nhanVien.getRole())) {
            intent = new Intent(UI_Login.this, QuanLyActivity.class);
        } else if ("staff".equalsIgnoreCase(nhanVien.getRole())) {
            intent = new Intent(UI_Login.this, NhanVienActivity.class);
        } else {
            Toast.makeText(UI_Login.this, "Không xác định được quyền truy cập", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(UI_Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void fetchNhanVienData() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Server.DuongDangetNhanVien_ThongTin,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if ("success".equals(status)) {
                                JSONArray data = response.getJSONArray("data");
                                parseNhanVienData(data);
                            } else {
                                String message = response.optString("message", "Không tải được dữ liệu");
                                Toast.makeText(UI_Login.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Lỗi khi phân tích JSON: " + e.getMessage());
                            Toast.makeText(UI_Login.this, "Lỗi dữ liệu từ server", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Lỗi kết nối: " + error.getMessage());
                        Toast.makeText(UI_Login.this, "Không thể kết nối tới server", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        MySingleton.getInstance(UI_Login.this).addToRequestQueue(request);
    }

    private void parseNhanVienData(JSONArray data) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject nhanVienObj = data.getJSONObject(i);

            // Lấy tất cả các trường từ JSON
            int idNhanVien = nhanVienObj.getInt("idNhanVien");
            String soDienThoai = nhanVienObj.getString("soDienThoai");
            String matKhau = nhanVienObj.getString("matKhau");
            String tenNhanVien = nhanVienObj.optString("tenNhanVien", "");
            String ngaySinh = nhanVienObj.optString("ngaySinh", "");
            String diaChi = nhanVienObj.optString("diaChi", "");
            String gioiTinh = nhanVienObj.optString("gioiTinh", "Khác");
            String chucVu = nhanVienObj.optString("chucVu", ""); // Nếu không có, trả về giá trị mặc định
            String trangThai = nhanVienObj.optString("trangThai", ""); // Trạng thái có thể không có
            String role = nhanVienObj.getString("role");

            // Khởi tạo đối tượng NhanVien với đầy đủ thông tin
            nhanVienList.add(new NhanVien(idNhanVien, tenNhanVien, gioiTinh, ngaySinh, chucVu,
                    soDienThoai, diaChi, trangThai, matKhau, role));
        }

        Log.d(TAG, "Dữ liệu nhân viên tải thành công: " + nhanVienList.size() + " nhân viên.");
    }
}
