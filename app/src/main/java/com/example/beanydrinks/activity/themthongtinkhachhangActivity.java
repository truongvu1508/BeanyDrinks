package com.example.beanydrinks.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.KhachHang;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class themthongtinkhachhangActivity extends AppCompatActivity {

    private EditText editTen;
    private EditText editTextsoDienThoai;
    private Button btnLuu;
    private double currentDiem = 0;
    private String idKhachHang; // Add this variable to hold customer ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set locale for the app
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_them_thong_tin_khach_hang);

        editTen = findViewById(R.id.editText_tenKH);
        editTextsoDienThoai = findViewById(R.id.editText_soDT);
        btnLuu = findViewById(R.id.button_luuthongtinKH);

        Button btnHuy = findViewById(R.id.button_huy);
        btnHuy.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // Sự kiện khi người dùng nhập số điện thoại
        editTextsoDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Không làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String soDienThoai = charSequence.toString().trim();
                if (soDienThoai.length() == 10 && soDienThoai.matches("^\\d{10}$")) {
                    checkPhoneNumberExistsAndHandle(soDienThoai);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không làm gì ở đây
            }
        });

        // Sự kiện khi nhấn nút "Lưu"
        btnLuu.setOnClickListener(view -> {
            String ten = editTen.getText().toString().trim();
            String soDienThoai = editTextsoDienThoai.getText().toString().trim();

            if (ten.isEmpty() || soDienThoai.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!soDienThoai.matches("^\\d{10}$")) {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            checkPhoneNumberExistsAndHandleOnSave(soDienThoai, ten);
        });
    }

    private void checkPhoneNumberExistsAndHandle(String phoneNumber) {
        if (!CheckConnection.haveNetworkConnection(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Server.DuongDanCheckSoDienThoai + "?soDienThoai=" + phoneNumber;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean exists = response.getBoolean("exists");
                        if (exists) {
                            String tenKhachHang = response.optString("ten", "Không có tên");
                            currentDiem = response.isNull("diem") ? 0 : response.getInt("diem");
                            editTen.setText(tenKhachHang);
                        } else {
                            editTen.setText("");
                            currentDiem = 0;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi xử lý dữ liệu phản hồi từ server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void checkPhoneNumberExistsAndHandleOnSave(String phoneNumber, String ten) {
        if (!CheckConnection.haveNetworkConnection(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Server.DuongDanCheckSoDienThoai + "?soDienThoai=" + phoneNumber;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean exists = response.getBoolean("exists");
                        if (exists) {
                            currentDiem = response.isNull("diem") ? 0 : response.getDouble("diem");
                            Log.d("KhachHangInfo", "SDT: " + phoneNumber + ", Tên: " + response.optString("ten", "Không có tên") + ", Điểm: " + currentDiem);
                            currentDiem += 1;
                            // Gọi phương thức lấy idKhachHang
                            getCustomerId(phoneNumber);
                        } else {
                            KhachHang newKhachHang = new KhachHang(phoneNumber, ten, null);
                            addKhachHangToServer(newKhachHang);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi xử lý dữ liệu phản hồi từ server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void addKhachHangToServer(KhachHang khachHang) {
        String url = Server.DuongDanInsertKhachHang;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("soDienThoai", khachHang.getSoDienThoai());
            jsonObject.put("ten", khachHang.getHoTen());
            jsonObject.put("diem", 0);  // Điểm mặc định là 0
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            returnResult(); // Pass the customer ID here
                        } else {
                            String message = response.getString("message");
                            Toast.makeText(this, "Thất bại: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void updateCustomerPoints(String phoneNumber, double newPoints) {
        String url = Server.DuongDanUpdatePointsKhachHang;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("soDienThoai", phoneNumber);
            jsonObject.put("diem", newPoints);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            returnResult(); // Pass the customer ID here
                        } else {
                            String message = response.getString("message");
                            Toast.makeText(this, "Thất bại: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getCustomerId(String phoneNumber) {
        if (!CheckConnection.haveNetworkConnection(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Server.DuongDanGetKhachHangId + "?soDienThoai=" + phoneNumber;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean exists = response.getBoolean("exists");
                        if (exists) {
                            idKhachHang = response.getString("idKhachHang"); // Set the customer ID
                            returnResult(); // Pass the customer ID to returnResult
                        } else {
                            Toast.makeText(this, "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi xử lý dữ liệu phản hồi từ server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void returnResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("tenKhachHang", editTen.getText().toString());
        resultIntent.putExtra("soDienThoai", editTextsoDienThoai.getText().toString());
        resultIntent.putExtra("diem", currentDiem);
        resultIntent.putExtra("idKhachHang", idKhachHang);  // Pass the customer ID here
        Log.d("KhachHangResult", "SDT: " + editTextsoDienThoai.getText().toString() + ", Tên: " + editTen.getText().toString() + ", Điểm: " + currentDiem);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}