package com.example.beanydrinks.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.activity.orderban_nvActivity;
import com.example.beanydrinks.adapter.AddNhanVienAdapter;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNhanVienFragment extends Fragment implements AddNhanVienAdapter.OnAddNhanVienListener {

    private RecyclerView recyclerView;
    private AddNhanVienAdapter addNhanVienAdapter;
    private ArrayList<NhanVien> addedNhanViens; // Lưu danh sách nhân viên được thêm

    // Biến ánh xạ các View trong layout
    private EditText editTextName;
    private EditText editTextNgaySinh;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextPassword;
    private Button buttonAdd;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton radioButtonNam;
    private RadioButton radioButtonNu;
    private RadioButton radioButtonKhac;
    private ImageButton btnBack;

    private Spinner spinnerChucVu;

    public AddNhanVienFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Đặt ngôn ngữ mặc định là tiếng Việt
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);  // Dùng setLocale thay vì sử dụng locale trực tiếp
        requireContext().getResources().updateConfiguration(config, requireContext().getResources().getDisplayMetrics());  // Dùng requireContext() thay vì getBaseContext()

        View view = inflater.inflate(R.layout.fragment_add_nhan_vien, container, false);

        // Sử dụng các biến toàn cục đã khai báo
        editTextName = view.findViewById(R.id.editText_Name_2);
        editTextNgaySinh = view.findViewById(R.id.ngaySinh_2);
        btnBack = view.findViewById(R.id.btnbackthemttkhach);
        editTextPhone = view.findViewById(R.id.editTextPhone_2);
        editTextAddress = view.findViewById(R.id.editText_User_2);
        editTextPassword = view.findViewById(R.id.editTextPassword_2);
        buttonAdd = view.findViewById(R.id.button_addnv2);

        // Ánh xạ RadioButton
        radioButtonNam = view.findViewById(R.id.radioButton_Nam_1);
        radioButtonNu = view.findViewById(R.id.radioButton_Nu_1);
        radioButtonKhac = view.findViewById(R.id.radioButton_Khac_1);

        // Ánh xạ Spinner
        // Ánh xạ Spinner
        spinnerChucVu = view.findViewById(R.id.spinnerChucVu_1);

        // Thêm giá trị vào Spinner
        String[] chucVuArray = new String[] {"Phục Vụ", "Pha Chế"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, chucVuArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChucVu.setAdapter(adapter);
        // Ánh xạ RadioGroup
        radioGroupGioiTinh = view.findViewById(R.id.radioGroup_GioiTinh);

        // Gán sự kiện kiểm tra trạng thái cho RadioGroup (nếu cần)
        radioGroupGioiTinh.setOnCheckedChangeListener((group, checkedId) -> {
            // Xử lý sự kiện khi có lựa chọn thay đổi
            if (checkedId == R.id.radioButton_Nam_1) {
                Log.d("RadioGroup", "Chọn Nam");
            } else if (checkedId == R.id.radioButton_Nu_1) {
                Log.d("RadioGroup", "Chọn Nữ");
            } else if (checkedId == R.id.radioButton_Khac_1) {
                Log.d("RadioGroup", "Chọn Khác");
            }
        });
        // Gán sự kiện cho nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), orderban_nvActivity.class);
                startActivity(intent);
            }
        });
        if (CheckConnection.haveNetworkConnection(getContext().getApplicationContext())) {
            EventButton();  // Gọi sự kiện khi kết nối mạng có sẵn
        } else {
            CheckConnection.ShowToast_Short(getContext().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

        return view;
    }

    private void EventButton(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập liệu
                String ten = editTextName.getText().toString().trim();
                String ngaySinh = editTextNgaySinh.getText().toString().trim();
                String soDienThoai = editTextPhone.getText().toString().trim();
                String diaChi = editTextAddress.getText().toString().trim();
                String matKhau = editTextPassword.getText().toString().trim();

                // Lấy giá trị giới tính
                String gioiTinh = "";
                int selectedId = radioGroupGioiTinh.getCheckedRadioButtonId();
                if (selectedId == R.id.radioButton_Nam_1) {
                    gioiTinh = "Nam";
                } else if (selectedId == R.id.radioButton_Nu_1) {
                    gioiTinh = "Nữ";
                } else if (selectedId == R.id.radioButton_Khac_1) {
                    gioiTinh = "Khác";
                }

                String chucVu = spinnerChucVu.getSelectedItem().toString();

                // Kiểm tra dữ liệu hợp lệ
                if (ten.isEmpty() || ngaySinh.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!soDienThoai.matches("^\\d{10}$")) {
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ngaySinh.length() != 10 || !ngaySinh.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    Toast.makeText(getContext(), "Ngày sinh không hợp lệ (định dạng: YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng NhanVien mới
                NhanVien newNhanVien = new NhanVien(ten, gioiTinh, ngaySinh, chucVu, soDienThoai, diaChi, "Đang làm việc", matKhau, "staff");

                // Gửi dữ liệu lên server
                addNhanVienToServer(newNhanVien);
            }
        });
    }


    @Override
    public void onNhanVienAdded(NhanVien nhanVien) {
        // Xử lý khi một nhân viên được thêm
        addedNhanViens.add(nhanVien); // Thêm vào danh sách
        Toast.makeText(getContext(), "Đã thêm nhân viên: " + nhanVien.getTenNhanVien(), Toast.LENGTH_SHORT).show();

        // Gửi dữ liệu lên server
        addNhanVienToServer(nhanVien);
    }

    private void addNhanVienToServer(NhanVien nhanVien) {
        if (CheckConnection.haveNetworkConnection(getContext())) {
            // URL API thêm nhân viên
            String url = Server.DuongDanInsertNhanVien;

            // Tạo đối tượng JSON chứa dữ liệu nhân viên
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("tenNhanVien", nhanVien.getTenNhanVien());
                jsonObject.put("gioiTinh", nhanVien.getGioiTinh());
                jsonObject.put("ngaySinh", nhanVien.getNgaySinh());
                jsonObject.put("chucVu", nhanVien.getChucVu());
                jsonObject.put("soDienThoai", nhanVien.getSoDienThoai());
                jsonObject.put("diaChi", nhanVien.getDiaChi());
                jsonObject.put("trangThai", nhanVien.getTrangThai());
                jsonObject.put("matKhau", nhanVien.getMatKhau());

                // Đặt role mặc định là "staff" nếu chưa có
                jsonObject.put("role", "staff");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi yêu cầu POST đến server
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    response -> {
                        try {
                            // Kiểm tra phản hồi từ server
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(getContext(), "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();

                                // Trở lại StaffFragment sau khi thêm thành công
                                StaffFragment staffFragment = new StaffFragment();
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout, staffFragment)
                                        .commit();
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(getContext(), "Thất bại: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        if (error.networkResponse != null) {
                            Log.e("Volley Error", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("Volley Error", "Response Data: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(getContext(), "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
                    }
            );

            // Thêm request vào hàng đợi
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);
        } else {
            Toast.makeText(getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }

    // Navigate back to the previous fragment
    private void navigateBack() {
        // Assuming StaffFragment has a proper newInstance method that accepts parameters
        StaffFragment staffFragment = new StaffFragment();  // Create the fragment instance

        // If StaffFragment requires arguments, you can pass them here
        // Bundle args = new Bundle();
        // staffFragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, staffFragment)  // Replace the current fragment with StaffFragment
                .addToBackStack(null)  // Add the transaction to the back stack
                .commit();
    }
}