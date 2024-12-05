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

public class UI_Login extends AppCompatActivity {

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

        // Initialize views
        initViews();

        // Initialize employee list
        nhanVienList = new ArrayList<>();

        // Fetch employee data from server
        fetchNhanVienData();

        // Set event listeners
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
        // Back button event
        btnBackWelcome.setOnClickListener(v -> navigateToWelcomeScreen());

        // Register button event
        textViewDangKy.setOnClickListener(v -> navigateToRegistrationScreen());

        // Login button event
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void navigateToWelcomeScreen() {
        Intent intent = new Intent(UI_Login.this, UI_Welcome2.class);
        startActivity(intent);
    }

    private void navigateToRegistrationScreen() {
        Intent intent = new Intent(UI_Login.this, dangki1Activity.class);
        startActivity(intent);
    }

    private void handleLogin() {
        String username = editTextUser.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();

        // Log to check the input values
        Log.d(TAG, "Username: " + username + ", Password: " + password);

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(UI_Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if credentials are correct
        NhanVien matchedNhanVien = null;
        for (NhanVien nhanVien : nhanVienList) {
            // Log employee info to check if it's correct
            Log.d(TAG, "Checking employee: " + nhanVien.getSoDienThoai() + " with password: " + nhanVien.getMatKhau());

            if (nhanVien.getSoDienThoai().equals(username) && nhanVien.getMatKhau().equals(password)) {
                matchedNhanVien = nhanVien;
                break;
            }
        }

        if (matchedNhanVien != null) {
            // Login successful
            onLoginSuccess(matchedNhanVien);
        } else {
            Toast.makeText(UI_Login.this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoginSuccess(NhanVien nhanVien) {
        // Save user information in session
        UserSession.getInstance(UI_Login.this).setCurrentUser(nhanVien);

        // Navigate based on user role
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
        Log.d(TAG, "API URL: " + Server.DuongDangetNhanVien_ThongTin);
        // Show loading indicator (if necessary)
        String url = Server.DuongDangetNhanVien_ThongTin;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
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
                            Log.e(TAG, "Error parsing JSON: " + e.getMessage());
                            Toast.makeText(UI_Login.this, "Lỗi dữ liệu từ server", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Connection error: " + error.getMessage());
                        Toast.makeText(UI_Login.this, "Không thể kết nối tới server", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the queue
        MySingleton.getInstance(UI_Login.this).addToRequestQueue(request);
    }

    private void parseNhanVienData(JSONArray data) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject nhanVienObj = data.getJSONObject(i);

            int idNhanVien = nhanVienObj.getInt("idNhanVien");
            if (idNhanVien <= 0) {
                Log.e(TAG, "Invalid idNhanVien: " + idNhanVien);
                continue;
            }

            String soDienThoai = nhanVienObj.getString("soDienThoai");
            String matKhau = nhanVienObj.getString("matKhau");
            String tenNhanVien = nhanVienObj.optString("tenNhanVien", "");
            String ngaySinh = nhanVienObj.optString("ngaySinh", "");
            String diaChi = nhanVienObj.optString("diaChi", "");
            String gioiTinh = nhanVienObj.optString("gioiTinh", "Khác");
            String chucVu = nhanVienObj.optString("chucVu", "");
            String trangThai = nhanVienObj.optString("trangThai", "");
            String role = nhanVienObj.getString("role");

            // Log employee info to check if it's parsed correctly
            Log.d(TAG, "Parsed employee: " + soDienThoai + ", Password: " + matKhau + ", Role: " + role);

            // Create NhanVien object
            nhanVienList.add(new NhanVien(idNhanVien, tenNhanVien, gioiTinh, ngaySinh, chucVu,
                    soDienThoai, diaChi, trangThai, matKhau, role));
        }

        Log.d(TAG, "Employee data loaded successfully: " + nhanVienList.size() + " employees.");
    }

}
