package com.example.beanydrinks.adapter;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.beanydrinks.model.NhanVien;

public class ThongTinNVAdapter {
    private final Context context;
    private final EditText editTextName;
    private final EditText editTextNgaySinh;
    private final EditText editTextPhone;
    private final EditText editTextDiaChi;
    private final RadioGroup radioGroupGioiTinh;
    private final RadioButton radioButtonNam;
    private final RadioButton radioButtonNu;
    private final RadioButton radioButtonKhac;

    public ThongTinNVAdapter(Context context, EditText editTextName, EditText editTextNgaySinh,
                             EditText editTextPhone, EditText editTextDiaChi, RadioGroup radioGroupGioiTinh,
                             RadioButton radioButtonNam, RadioButton radioButtonNu, RadioButton radioButtonKhac) {
        this.context = context;
        this.editTextName = editTextName;
        this.editTextNgaySinh = editTextNgaySinh;
        this.editTextPhone = editTextPhone;
        this.editTextDiaChi = editTextDiaChi;
        this.radioGroupGioiTinh = radioGroupGioiTinh;
        this.radioButtonNam = radioButtonNam;
        this.radioButtonNu = radioButtonNu;
        this.radioButtonKhac = radioButtonKhac;
    }

    public void bindData(NhanVien nhanVien) {
        if (nhanVien == null) return;

        // Kiểm tra dữ liệu không hợp lệ và gán giá trị mặc định nếu cần
        editTextName.setText(nhanVien.getTenNhanVien() != null ? nhanVien.getTenNhanVien() : "");
        editTextNgaySinh.setText(nhanVien.getNgaySinh() != null ? nhanVien.getNgaySinh() : "");
        editTextPhone.setText(nhanVien.getSoDienThoai() != null ? nhanVien.getSoDienThoai() : "");
        editTextDiaChi.setText(nhanVien.getDiaChi() != null ? nhanVien.getDiaChi() : "");

        // Cập nhật giới tính
        String gioiTinh = nhanVien.getGioiTinh();
        if (gioiTinh != null) {
            if ("Nam".equalsIgnoreCase(gioiTinh)) {
                radioButtonNam.setChecked(true);
            } else if ("Nữ".equalsIgnoreCase(gioiTinh)) {
                radioButtonNu.setChecked(true);
            } else {
                radioButtonKhac.setChecked(true);
            }
        } else {
            radioButtonKhac.setChecked(true); // Giá trị mặc định
        }
    }
}
