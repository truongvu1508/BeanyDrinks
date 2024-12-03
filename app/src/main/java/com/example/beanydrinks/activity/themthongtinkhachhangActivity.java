package com.example.beanydrinks.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.beanydrinks.adapter.themthongtinkhachhangAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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
import android.text.Editable;
import android.text.TextWatcher;
import java.util.ArrayList;
import java.util.Locale;

public class themthongtinkhachhangActivity extends AppCompatActivity {

    private ArrayList<KhachHang> khachHangList;
    private themthongtinkhachhangAdapter adapter;
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

        // Khởi tạo danh sách khách hàng
        khachHangList = new ArrayList<>();

        // Sử dụng các biến toàn cục đã khai báo
        editTen = findViewById(R.id.editText_tenKH);
        editTextsoDienThoai = findViewById(R.id.editText_soDT);
        btnLuu = findViewById(R.id.button_luuthongtinKH);

        // Khởi tạo adapter với callback
        adapter = new themthongtinkhachhangAdapter(this, khachHang -> {
            // Thêm khách hàng mới vào danh sách
            khachHangList.add(khachHang);
            // Gửi dữ liệu khách hàng lên server
            addKhachHangToServer(khachHang);
        });

        // Xử lý nút quay lại
        Button btnBack = findViewById(R.id.button_huy);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(themthongtinkhachhangActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });



        // Kiểm tra kết nối mạng
        if (CheckConnection.haveNetworkConnection(this.getApplicationContext())) {
            EventButton();  // Gọi sự kiện khi kết nối mạng có sẵn
        } else {
            CheckConnection.ShowToast_Short(this.getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

        editTextsoDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Kiểm tra số điện thoại khi có sự thay đổi
                String phoneNumber = editTextsoDienThoai.getText().toString().trim();

                if (phoneNumber.isEmpty()) {
                    // Nếu số điện thoại bị xóa hết, xóa tên khách hàng
                    editTen.setText("");
                } else if (phoneNumber.matches("^\\d{10}$")) {
                    // Nếu số điện thoại hợp lệ, kiểm tra xem số điện thoại có tồn tại không
                    checkPhoneNumberExists(phoneNumber);
                } else {
                    // Nếu số điện thoại không hợp lệ, xóa tên khách hàng
                    editTen.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void EventButton(){
        btnLuu.setOnClickListener(view -> {
            // Lấy dữ liệu từ các trường nhập liệu
            String ten = editTen.getText().toString().trim();
            String soDienThoai = editTextsoDienThoai.getText().toString().trim();

            // Kiểm tra dữ liệu hợp lệ
            if (ten.isEmpty() || soDienThoai.isEmpty()) {
                Toast.makeText(themthongtinkhachhangActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!soDienThoai.matches("^\\d{10}$")) {
                Toast.makeText(themthongtinkhachhangActivity.this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra số điện thoại có tồn tại hay không
            checkPhoneNumberExistsAndHandle(soDienThoai, ten);
        });
    }

    // Gọi phương thức thêm khách hàng từ adapter
    private void onKhachHangAdded(KhachHang khachHang) {
        // Xử lý khi một khách hàng được thêm
        khachHangList.add(khachHang); // Thêm vào danh sách
        Toast.makeText(this, "Đã thêm khách hàng: " + khachHang.getHoTen(), Toast.LENGTH_SHORT).show();

        // Gửi dữ liệu lên server
        addKhachHangToServer(khachHang);
    }

    private void addKhachHangToServer(KhachHang khachHang) {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            // URL API thêm khách hàng
            String url = Server.DuongDanInsertKhachHang;

            // Tạo đối tượng JSON chứa dữ liệu khách hàng
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("soDienThoai", khachHang.getSoDienThoai());
                jsonObject.put("ten", khachHang.getHoTen());
                jsonObject.put("diem", JSONObject.NULL); // Đặt diem là NULL
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi yêu cầu POST đến server
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    response -> {
                        try {
                            // Kiểm tra phản hồi từ server
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(this, "Thêm khách hàng thành công!", Toast.LENGTH_SHORT).show();
                                // Quay lại màn hình đặt hàng
                                Intent intent = new Intent(themthongtinkhachhangActivity.this, orderban_nvActivity.class);
                                startActivity(intent);
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
                    }
            );

            // Thêm request vào hàng đợi
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPhoneNumberExists(String phoneNumber) {
        if (CheckConnection.haveNetworkConnection(this.getApplicationContext())) {
            // URL để kiểm tra số điện thoại (thay đổi thành GET)
            String url = Server.DuongDanCheckSoDienThoai + "?soDienThoai=" + phoneNumber;

            // Gửi yêu cầu GET để kiểm tra số điện thoại
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            boolean exists = response.getBoolean("exists");
                            if (exists) {
                                // Nếu số điện thoại tồn tại, lấy tên khách hàng
                                String ten = response.getString("ten");
                                editTen.setText(ten);  // Tự động điền tên vào trường editTen
                            } else {
                                // Nếu số điện thoại không tồn tại, không làm gì cả
                                editTen.setText("");  // Xóa tên khách hàng nếu không tồn tại
                                Toast.makeText(this, "Số điện thoại chưa đăng ký! Bạn có thể nhập tên khách hàng mới.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(this, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
                    }
            );

            // Thêm request vào hàng đợi
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPhoneNumberExistsAndHandle(String phoneNumber, String ten) {
        if (CheckConnection.haveNetworkConnection(this.getApplicationContext())) {
            // URL để kiểm tra số điện thoại (thay đổi thành GET)
            String url = Server.DuongDanCheckSoDienThoai + "?soDienThoai=" + phoneNumber;

            // Gửi yêu cầu GET để kiểm tra số điện thoại
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            boolean exists = response.getBoolean("exists");
                            if (exists) {
                                // Nếu số điện thoại đã tồn tại và có điểm, kiểm tra và tăng điểm
                                int diem = response.isNull("diem") ? 0 : response.getInt("diem"); // Kiểm tra nếu diem là null
                                diem += 1; // Tăng điểm lên 1

                                // Gửi yêu cầu PUT hoặc POST để cập nhật điểm
                                updateCustomerPoints(phoneNumber, diem);
                            } else {
                                // Nếu số điện thoại không tồn tại, tạo mới khách hàng
                                KhachHang newKhachHang = new KhachHang(phoneNumber, ten, null); // Để diem là null
                                addKhachHangToServer(newKhachHang);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(this, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
                    }
            );

            // Thêm request vào hàng đợi
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCustomerPoints(String phoneNumber, int newPoints) {
        // URL API cập nhật điểm của khách hàng
        String url = Server.DuongDanUpdatePointsKhachHang;

        // Tạo đối tượng JSON chứa dữ liệu khách hàng
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("soDienThoai", phoneNumber);
            jsonObject.put("diem", newPoints);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi yêu cầu POST hoặc PUT đến server để cập nhật điểm
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                response -> {
                    try {
                        // Kiểm tra phản hồi từ server
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(this, "Cập nhật điểm khách hàng thành công!", Toast.LENGTH_SHORT).show();
                            // Quay lại màn hình đặt hàng
                            Intent intent = new Intent(themthongtinkhachhangActivity.this, orderban_nvActivity.class);
                            startActivity(intent);
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
                }
        );

        // Thêm request vào hàng đợi
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
