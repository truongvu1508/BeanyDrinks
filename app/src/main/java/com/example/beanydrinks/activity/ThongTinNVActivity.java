package com.example.beanydrinks.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.model.UserSession;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

public class ThongTinNVActivity extends AppCompatActivity {

    private EditText editTextName, editTextNgaySinh, editTextPhone, editTextDiaChi;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton radioButtonNam, radioButtonNu, radioButtonKhac;
    private Button buttonCapNhat;
    private Button buttonCanCel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nvactivity);

        // Bind các view
        bindViews();

        // Lấy thông tin người dùng hiện tại từ session
        NhanVien currentUser = UserSession.getInstance(this).getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Gửi yêu cầu lấy thông tin nhân viên từ server
        fetchUserInfo(currentUser.getIdNhanVien());

        // Thiết lập sự kiện cho nút Cập nhật
        buttonCapNhat.setOnClickListener(v -> {
            // Lấy thông tin đã cập nhật
            NhanVien updatedNhanVien = getUpdatedNhanVien();

            // Kiểm tra dữ liệu đầu vào
            if (validateInput(updatedNhanVien)) {
                // Gửi yêu cầu cập nhật
                sendUpdateRequest(updatedNhanVien);
            } else {
                Toast.makeText(ThongTinNVActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });
        buttonCanCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước đó
                finish();
            }
        });
    }

    // Phương thức gắn kết các view
    private void bindViews() {
        editTextName = findViewById(R.id.editText_Name);
        editTextNgaySinh = findViewById(R.id.ngaySinh);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDiaChi = findViewById(R.id.editTextText);
        radioGroupGioiTinh = findViewById(R.id.radioGroup_GioiTinh);
        radioButtonNam = findViewById(R.id.radioButton_Nam);
        radioButtonNu = findViewById(R.id.radioButton_Nu);
        radioButtonKhac = findViewById(R.id.radioButton_Khac);
        buttonCapNhat = findViewById(R.id.button_DoiMK);
        buttonCanCel = findViewById(R.id.button_Cancel);
    }

    // Phương thức hiển thị thông tin người dùng lên giao diện
    private void displayUserInfo(NhanVien user) {
        if (user != null) {
            editTextName.setText(user.getTenNhanVien());
            editTextNgaySinh.setText(user.getNgaySinh());
            editTextPhone.setText(user.getSoDienThoai());
            editTextDiaChi.setText(user.getDiaChi());

            if (user.getGioiTinh() != null) {
                if (user.getGioiTinh().equalsIgnoreCase("Nam")) {
                    radioButtonNam.setChecked(true);
                } else if (user.getGioiTinh().equalsIgnoreCase("Nữ")) {
                    radioButtonNu.setChecked(true);
                } else {
                    radioButtonKhac.setChecked(true);
                }
            } else {
                radioButtonKhac.setChecked(true);  // Mặc định
            }
        }
    }

    // Phương thức lấy thông tin người dùng từ server (GET request)
    private void fetchUserInfo(int idNhanVien) {
        String url = Server.DuongDanGetNhanVien_2 + "?idNhanVien=" + idNhanVien;

        // Tạo yêu cầu GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Kiểm tra trạng thái phản hồi
                            if (response.has("status") && response.getString("status").equals("success")) {
                                // Lấy thông tin nhân viên từ phần "data"
                                if (response.has("data")) {
                                    JSONObject data = response.getJSONObject("data");

                                    NhanVien user = new NhanVien(
                                            data.getInt("idNhanVien"),
                                            data.getString("tenNhanVien"),
                                            data.getString("gioiTinh"),
                                            data.getString("ngaySinh"),
                                            data.getString("chucVu"),
                                            data.getString("soDienThoai"),
                                            data.getString("diaChi")
                                    );

                                    // Hiển thị thông tin nhân viên lên giao diện
                                    displayUserInfo(user);
                                } else {
                                    Toast.makeText(ThongTinNVActivity.this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ThongTinNVActivity.this, "Không tìm thấy nhân viên!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ThongTinNVActivity.this, "Lỗi phân tích dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThongTinNVActivity.this, "Lỗi kết nối đến server!", Toast.LENGTH_SHORT).show();
                    }
                });

        // Thêm yêu cầu vào hàng đợi
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    // Phương thức lấy thông tin đã cập nhật từ UI
    private NhanVien getUpdatedNhanVien() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTextDiaChi.getText().toString();
        String dob = editTextNgaySinh.getText().toString();
        String gender = "Khác"; // Mặc định

        if (radioButtonNam.isChecked()) {
            gender = "Nam";
        } else if (radioButtonNu.isChecked()) {
            gender = "Nữ";
        }

        NhanVien updatedNhanVien = new NhanVien(
                UserSession.getInstance(this).getCurrentUser().getIdNhanVien(),
                name, gender, dob, UserSession.getInstance(this).getCurrentUser().getChucVu(),
                phone, address, UserSession.getInstance(this).getCurrentUser().getTrangThai(),
                UserSession.getInstance(this).getCurrentUser().getMatKhau(),
                UserSession.getInstance(this).getCurrentUser().getRole()
        );
        return updatedNhanVien;
    }

    // Phương thức kiểm tra dữ liệu nhập vào
    private boolean validateInput(NhanVien nhanVien) {
        if (nhanVien.getTenNhanVien().isEmpty()) {
            editTextName.setError("Tên không được để trống");
            return false;
        }
        if (nhanVien.getNgaySinh().isEmpty()) {
            editTextNgaySinh.setError("Ngày sinh không được để trống");
            return false;
        }
        if (nhanVien.getSoDienThoai().isEmpty()) {
            editTextPhone.setError("Số điện thoại không được để trống");
            return false;
        }
        if (nhanVien.getDiaChi().isEmpty()) {
            editTextDiaChi.setError("Địa chỉ không được để trống");
            return false;
        }
        return true;
    }

    // Phương thức gửi yêu cầu cập nhật lên server
    private void sendUpdateRequest(NhanVien nhanVien) {
        String url = Server.DuongDanUpdateNhanVien_thongtin;

        // Tạo JSON body để gửi
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("idNhanVien", nhanVien.getIdNhanVien());
            jsonBody.put("tenNhanVien", nhanVien.getTenNhanVien());
            jsonBody.put("gioiTinh", nhanVien.getGioiTinh());
            jsonBody.put("ngaySinh", nhanVien.getNgaySinh());
            jsonBody.put("soDienThoai", nhanVien.getSoDienThoai());
            jsonBody.put("diaChi", nhanVien.getDiaChi());
            jsonBody.put("trangThai", nhanVien.getTrangThai());
            jsonBody.put("matKhau", nhanVien.getMatKhau());
            jsonBody.put("role", nhanVien.getRole());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tạo dữ liệu cập nhật!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo queue yêu cầu
        RequestQueue queue = Volley.newRequestQueue(this);

        // Tạo yêu cầu POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");

                            Log.d("UpdateResponse", "Phản hồi từ server: " + response.toString());
                            Toast.makeText(ThongTinNVActivity.this, message, Toast.LENGTH_SHORT).show();

                            if (success) {
                                // Cập nhật thông tin người dùng trong UserSession
                                UserSession.getInstance(ThongTinNVActivity.this).setCurrentUser(nhanVien);
                                finish();
                            } else {
                                Toast.makeText(ThongTinNVActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ThongTinNVActivity.this, "Phản hồi không hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThongTinNVActivity.this, "Lỗi mạng hoặc server!", Toast.LENGTH_SHORT).show();
                    }
                });

        // Thêm yêu cầu vào queue
        queue.add(jsonObjectRequest);
    }
}
