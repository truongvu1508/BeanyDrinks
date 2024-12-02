package com.example.beanydrinks.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        Button btnHuy = findViewById(R.id.button_huy); // ID của nút "Hủy"
        btnHuy.setOnClickListener(view -> {
            setResult(RESULT_CANCELED); // Trả về kết quả hủy
            finish(); // Kết thúc Activity
        });

        // Sự kiện khi người dùng nhập số điện thoại
        editTextsoDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Không làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Kiểm tra số điện thoại mỗi khi người dùng nhập vào
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

            // Kiểm tra sự tồn tại của số điện thoại và xử lý cập nhật điểm hoặc thêm mới
            checkPhoneNumberExistsAndHandleOnSave(soDienThoai, ten);
        });
    }

    private void checkPhoneNumberExistsAndHandle(String phoneNumber) {
        // Kiểm tra kết nối mạng trước khi thực hiện
        if (!CheckConnection.haveNetworkConnection(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        // URL kiểm tra số điện thoại
        String url = Server.DuongDanCheckSoDienThoai + "?soDienThoai=" + phoneNumber;

        // Gửi yêu cầu GET tới server
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean exists = response.getBoolean("exists");
                        if (exists) {
                            // Nếu số điện thoại đã tồn tại
                            String tenKhachHang = response.optString("ten", "Không có tên");
                            int diem = response.isNull("diem") ? 0 : response.getInt("diem");

                            // Hiển thị thông tin tên khách hàng
                            editTen.setText(tenKhachHang);
                        } else {
                            // Nếu số điện thoại không tồn tại
                            editTen.setText("");  // Clear the name field if phone number doesn't exist
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

        // Thực hiện yêu cầu
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void saveCustomerData(String phoneNumber, String ten) {
        // Save customer data when the save button is clicked
        if (!CheckConnection.haveNetworkConnection(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the customer object
        KhachHang newKhachHang = new KhachHang(phoneNumber, ten, null);
        addKhachHangToServer(newKhachHang);
    }

    private void addKhachHangToServer(KhachHang khachHang) {
        String url = Server.DuongDanInsertKhachHang;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("soDienThoai", khachHang.getSoDienThoai());
            jsonObject.put("ten", khachHang.getHoTen());
            jsonObject.put("diem", JSONObject.NULL);
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
                            // Return to the previous activity with the new customer data
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("tenKhachHang", editTen.getText().toString());
                            resultIntent.putExtra("soDienThoai", editTextsoDienThoai.getText().toString());
                            setResult(RESULT_OK, resultIntent);
                            finish();
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
    // Kiểm tra sự tồn tại của số điện thoại và cập nhật điểm hoặc thêm mới
    private void checkPhoneNumberExistsAndHandleOnSave(String phoneNumber, String ten) {
        // Kiểm tra kết nối mạng trước khi thực hiện
        if (!CheckConnection.haveNetworkConnection(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        // URL kiểm tra số điện thoại
        String url = Server.DuongDanCheckSoDienThoai + "?soDienThoai=" + phoneNumber;

        // Gửi yêu cầu GET tới server
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean exists = response.getBoolean("exists");
                        if (exists) {
                            // Nếu số điện thoại đã tồn tại
                            String tenKhachHang = response.optString("ten", "Không có tên");
                            int diem = response.isNull("diem") ? 0 : response.getInt("diem");

                            // Hiển thị tên khách hàng
                            editTen.setText(tenKhachHang);

                            // Tăng điểm và cập nhật vào server
                            diem += 1;
                            updateCustomerPoints(phoneNumber, diem);
                        } else {
                            // Nếu số điện thoại không tồn tại, thêm mới khách hàng
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

        // Thực hiện yêu cầu
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    // Cập nhật điểm khách hàng
    private void updateCustomerPoints(String phoneNumber, int newPoints) {
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
                            // Return to the previous activity with the updated customer data
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("tenKhachHang", editTen.getText().toString());
                            resultIntent.putExtra("soDienThoai", editTextsoDienThoai.getText().toString());
                            setResult(RESULT_OK, resultIntent);
                            finish();
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
}
