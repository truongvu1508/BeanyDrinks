package com.example.beanydrinks.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.ThongTinNVAdapter;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.model.UserSession;

public class ThongTinNVActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextNgaySinh;
    private EditText editTextPhone;
    private EditText editTextDiaChi;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton radioButtonNam;
    private RadioButton radioButtonNu;
    private RadioButton radioButtonKhac;

    private ThongTinNVAdapter thongTinNVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nvactivity);

        // Ánh xạ các view từ layout
        editTextName = findViewById(R.id.editText_Name);
        editTextNgaySinh = findViewById(R.id.ngaySinh);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDiaChi = findViewById(R.id.editTextText);
        radioGroupGioiTinh = findViewById(R.id.radioGroup_GioiTinh);
        radioButtonNam = findViewById(R.id.radioButton_Nam);
        radioButtonNu = findViewById(R.id.radioButton_Nu);
        radioButtonKhac = findViewById(R.id.radioButton_Khac);

        // Lấy thông tin người dùng từ UserSession
        NhanVien currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Kiểm tra dữ liệu từ currentUser
        System.out.println("Dữ liệu nhân viên: " + currentUser.toString());

        // Khởi tạo adapter và đổ dữ liệu
        thongTinNVAdapter = new ThongTinNVAdapter(
                this,
                editTextName,
                editTextNgaySinh,
                editTextPhone,
                editTextDiaChi,
                radioGroupGioiTinh,
                radioButtonNam,
                radioButtonNu,
                radioButtonKhac
        );

        thongTinNVAdapter.bindData(currentUser);
    }
}
