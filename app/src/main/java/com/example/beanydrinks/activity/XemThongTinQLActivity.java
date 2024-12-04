package com.example.beanydrinks.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class XemThongTinQLActivity extends AppCompatActivity {

    private EditText editTextName, editTextNgaySinh, editTextPhone, editTextDiaChi;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton radioButtonNam, radioButtonNu, radioButtonKhac;
    private Button btnSelectPhoto, buttonCancel, buttonUpdate;
    private ImageView imgAvt;
    private ImageButton btnBack;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_thong_tin_qlactivity);

        // Initialize components
        initializeComponents();
        registerImageSelector();
        setupListeners();

        // Get the current user ID from the session
        int idNhanVien = UserSession.getInstance(this).getCurrentUser().getIdNhanVien();
        fetchUserInfo(idNhanVien); // Fetch user info from server
    }

    private void initializeComponents() {
        editTextName = findViewById(R.id.editText_Name);
        editTextNgaySinh = findViewById(R.id.ngaySinh);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDiaChi = findViewById(R.id.editTextText);
        radioGroupGioiTinh = findViewById(R.id.radioGroup);
        radioButtonNam = findViewById(R.id.radioButton_Nam);
        radioButtonNu = findViewById(R.id.radioButton_Nu);
        radioButtonKhac = findViewById(R.id.radioButton_Khac);
        btnSelectPhoto = findViewById(R.id.btn_select_img_avt);
        imgAvt = findViewById(R.id.image_avt);
        btnBack = findViewById(R.id.btnbackthemttkhach);
        buttonCancel = findViewById(R.id.button_Cancel);
        buttonUpdate = findViewById(R.id.button_DoiMK);
    }

    private void registerImageSelector() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            imgAvt.setImageURI(imageUri);
                        } else {
                            Toast.makeText(XemThongTinQLActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        buttonCancel.setOnClickListener(v -> finish());
        buttonUpdate.setOnClickListener(v -> updateUserDetails());
        btnSelectPhoto.setOnClickListener(v -> openImageSelector());
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void fetchUserInfo(int idNhanVien) {
        String url = Server.DuongDanGetNhanVien_2 + "?idNhanVien=" + idNhanVien;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        if (response.has("status") && response.getString("status").equals("success")) {
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

                                displayUserInfo(user); // Display user info on the UI
                            } else {
                                Toast.makeText(this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Không tìm thấy nhân viên!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi phân tích dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi kết nối đến server!", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

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
                radioButtonKhac.setChecked(true); // Default
            }
        }
    }

    private void updateUserDetails() {
        NhanVien updatedNhanVien = getUpdatedNhanVien();
        if (validateInput(updatedNhanVien)) {
            sendUpdateRequest(updatedNhanVien);
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
    }

    private NhanVien getUpdatedNhanVien() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTextDiaChi.getText().toString();
        String dob = editTextNgaySinh.getText().toString();
        String gender = "Khác"; // Default

        if (radioButtonNam.isChecked()) {
            gender = "Nam";
        } else if (radioButtonNu.isChecked()) {
            gender = "Nữ";
        }

        return new NhanVien(
                UserSession.getInstance(this).getCurrentUser().getIdNhanVien(),
                name, gender, dob, UserSession.getInstance(this).getCurrentUser().getChucVu(),
                phone, address, UserSession.getInstance(this).getCurrentUser().getTrangThai(),
                UserSession.getInstance(this).getCurrentUser().getMatKhau(),
                UserSession.getInstance(this).getCurrentUser().getRole()
        );
    }

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

    private void sendUpdateRequest(NhanVien nhanVien) {
        String url = Server.DuongDanUpdateNhanVien_thongtin;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("idNhanVien", nhanVien.getIdNhanVien());
            jsonBody.put("tenNhanVien", nhanVien.getTenNhanVien());
            jsonBody.put("gioiTinh", nhanVien.getGioiTinh());
            jsonBody.put("ngaySinh", nhanVien.getNgaySinh());
            jsonBody.put("soDienThoai", nhanVien.getSoDienThoai());
            jsonBody.put("diaChi", nhanVien.getDiaChi());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tạo dữ liệu cập nhật!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (success) {
                            UserSession.getInstance(this).setCurrentUser(nhanVien);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Phản hồi không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi mạng hoặc server!", Toast.LENGTH_SHORT).show()
        );

        queue.add(jsonObjectRequest);
    }
}
