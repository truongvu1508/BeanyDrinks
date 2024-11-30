package com.example.beanydrinks.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class XemNhanVienFragment extends Fragment {
    // View references
    private ImageButton btnBack;
    private EditText edtHoTen, edtNgaySinh, edtSoDienThoai, edtDiaChi, edtMatKhau;
    private RadioButton rbNam, rbNu, rbKhac;
    private Spinner spinnerChucVu, spinnerTrangThai;
    private Button btnUpdate, btnDelete;

    private NhanVien nhanVien;  // NhanVien object to hold employee details

    public XemNhanVienFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of the fragment
    public static XemNhanVienFragment newInstance(NhanVien nhanVien) {
        XemNhanVienFragment fragment = new XemNhanVienFragment();
        Bundle args = new Bundle();
        args.putParcelable("thongtinnhanvien", nhanVien); // Passing the employee details
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale();  // Set locale to Vietnamese
        if (getArguments() != null) {
            nhanVien = getArguments().getParcelable("thongtinnhanvien");
            if (nhanVien != null) {
                Log.d("XemNhanVienFragment", "NhanVien received: " + nhanVien.getTenNhanVien());
            } else {
                Log.e("XemNhanVienFragment", "NhanVien is null");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xem_nhan_vien, container, false);

        // Bind views
        btnBack = view.findViewById(R.id.btnbackthemttkhach);
        edtHoTen = view.findViewById(R.id.editText_Name);
        rbNam = view.findViewById(R.id.radioButton_Nam);
        rbNu = view.findViewById(R.id.radioButton_Nu);
        rbKhac = view.findViewById(R.id.radioButton_Khac);
        edtNgaySinh = view.findViewById(R.id.ngaySinh);
        spinnerChucVu = view.findViewById(R.id.spinnerChucVu);
        edtSoDienThoai = view.findViewById(R.id.editTextPhone);
        edtDiaChi = view.findViewById(R.id.editText_User);
        spinnerTrangThai = view.findViewById(R.id.spinnerTrangThaiLamViec);
        btnDelete = view.findViewById(R.id.button_DeleteNV);
        edtMatKhau = view.findViewById(R.id.editTextPassword_2); // Password field
        btnUpdate = view.findViewById(R.id.button_CapNhat);

        // Setup spinners with adapter
        setupSpinner(spinnerChucVu, R.array.chuc_vu_array);
        setupSpinner(spinnerTrangThai, R.array.trang_thai_array);

        // Setup button listeners
        btnBack.setOnClickListener(v -> navigateBack());
        btnUpdate.setOnClickListener(v -> updateInformation());

        // Display employee information if available
        if (nhanVien != null) {
            displayInformation(nhanVien);
        }

        return view;
    }

    // Set Vietnamese locale for the app
    private void setLocale() {
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireContext().getResources().updateConfiguration(config, requireContext().getResources().getDisplayMetrics());
    }

    // Setup Spinner with items from a resource array
    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
    // Display the employee information in the respective fields
    private void displayInformation(NhanVien nhanVien) {
        edtHoTen.setText(nhanVien.getTenNhanVien());
        edtNgaySinh.setText(nhanVien.getNgaySinh());
        edtSoDienThoai.setText(nhanVien.getSoDienThoai());
        edtDiaChi.setText(nhanVien.getDiaChi());
        edtMatKhau.setText(nhanVien.getMatKhau());

        // Set gender radio button
        setGenderRadioButton(nhanVien.getGioiTinh());

        // Set position and status
        setSpinnerSelection(spinnerChucVu, nhanVien.getChucVu());
        setSpinnerSelection(spinnerTrangThai, nhanVien.getTrangThai());
    }

    // Set the selected gender radio button
    private void setGenderRadioButton(String gender) {
        switch (gender) {
            case "Nam":
                rbNam.setChecked(true);
                break;
            case "Nữ":
                rbNu.setChecked(true);
                break;
            case "Khác":
                rbKhac.setChecked(true);
                break;
        }
    }

    // Set the selected item in a spinner
    private void setSpinnerSelection(Spinner spinner, String item) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(item)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    // Update employee information based on user input
    private void updateInformation() {
        String tenNhanVien = edtHoTen.getText().toString();
        String gioiTinh = rbNam.isChecked() ? "Nam" : rbNu.isChecked() ? "Nữ" : "Khác";
        String ngaySinh = edtNgaySinh.getText().toString();
        String soDienThoai = edtSoDienThoai.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String matKhau = edtMatKhau.getText().toString(); // Get password
        String chucVu = spinnerChucVu.getSelectedItem().toString();
        String trangThai = spinnerTrangThai.getSelectedItem().toString();

        // Update NhanVien object
        nhanVien.setTenNhanVien(tenNhanVien);
        nhanVien.setGioiTinh(gioiTinh);
        nhanVien.setNgaySinh(ngaySinh);
        nhanVien.setSoDienThoai(soDienThoai);
        nhanVien.setDiaChi(diaChi);
        nhanVien.setMatKhau(matKhau);
        nhanVien.setChucVu(chucVu);
        nhanVien.setTrangThai(trangThai);

        // Send the updated information to the server
        updateNhanVienToServer(nhanVien);
    }

    // Send update request to the server
    private void updateNhanVienToServer(NhanVien nhanVien) {
        if (CheckConnection.haveNetworkConnection(getContext())) {
            String url = Server.DuongDanUpdateNhanVien;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", nhanVien.getIdNhanVien());
                jsonObject.put("tenNhanVien", nhanVien.getTenNhanVien());
                jsonObject.put("gioiTinh", nhanVien.getGioiTinh());
                jsonObject.put("ngaySinh", nhanVien.getNgaySinh());
                jsonObject.put("chucVu", nhanVien.getChucVu());
                jsonObject.put("soDienThoai", nhanVien.getSoDienThoai());
                jsonObject.put("diaChi", nhanVien.getDiaChi());
                jsonObject.put("matKhau", nhanVien.getMatKhau());
                jsonObject.put("trangThai", nhanVien.getTrangThai());
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                    response -> handleServerResponse(response),
                    error -> handleServerError(error));

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);
        } else {
            Toast.makeText(getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle server response
    private void handleServerResponse(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (success) {
                Toast.makeText(getContext(), "Cập nhật nhân viên thành công!", Toast.LENGTH_SHORT).show();
            } else {
                String message = response.getString("message");
                Toast.makeText(getContext(), "Cập nhật thất bại: " + message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle server error
    private void handleServerError(Throwable error) {
        error.printStackTrace();

        if (error instanceof com.android.volley.NetworkError) {
            // This handles a network error case
            Toast.makeText(getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
        } else if (error instanceof com.android.volley.ServerError) {
            // This handles server error case
            Toast.makeText(getContext(), "Lỗi từ server", Toast.LENGTH_SHORT).show();
        } else if (error instanceof com.android.volley.TimeoutError) {
            // This handles timeout error case
            Toast.makeText(getContext(), "Yêu cầu quá thời gian, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        } else {
            // Default error case
            Toast.makeText(getContext(), "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        }
    }

}
