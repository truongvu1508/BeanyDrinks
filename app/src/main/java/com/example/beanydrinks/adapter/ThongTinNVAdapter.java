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

    private int idNhanVien;  // Thêm biến idNhanVien

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

    // Gán dữ liệu từ NhanVien vào các view và lấy idNhanVien
    public void bindData(NhanVien nhanVien) {
        if (nhanVien == null) return;

        this.idNhanVien = nhanVien.getIdNhanVien();

        // Gán dữ liệu vào các EditText
        editTextName.setText(nhanVien.getTenNhanVien() != null ? nhanVien.getTenNhanVien() : "");
        editTextNgaySinh.setText(nhanVien.getNgaySinh() != null ? nhanVien.getNgaySinh() : "");
        editTextPhone.setText(nhanVien.getSoDienThoai() != null ? nhanVien.getSoDienThoai() : "");
        editTextDiaChi.setText(nhanVien.getDiaChi() != null ? nhanVien.getDiaChi() : "");

        // Gán giới tính
        String gioiTinh = nhanVien.getGioiTinh();
        if ("Nam".equalsIgnoreCase(gioiTinh)) {
            radioButtonNam.setChecked(true);
        } else if ("Nữ".equalsIgnoreCase(gioiTinh)) {
            radioButtonNu.setChecked(true);
        } else {
            radioButtonKhac.setChecked(true);
        }
    }

    // Cập nhật dữ liệu vào các view từ các tham số truyền vào
    public void updateData(String name, String ngaySinh, String phone, String diaChi, String gioiTinh) {
        editTextName.setText(name != null ? name : "");
        editTextNgaySinh.setText(ngaySinh != null ? ngaySinh : "");
        editTextPhone.setText(phone != null ? phone : "");
        editTextDiaChi.setText(diaChi != null ? diaChi : "");

        if ("Nam".equalsIgnoreCase(gioiTinh)) {
            radioButtonNam.setChecked(true);
        } else if ("Nữ".equalsIgnoreCase(gioiTinh)) {
            radioButtonNu.setChecked(true);
        } else {
            radioButtonKhac.setChecked(true);
        }
    }

    // Trả về một đối tượng NhanVien với dữ liệu đã cập nhật, bao gồm cả idNhanVien
    public NhanVien getUpdatedNhanVien() {
        NhanVien nhanVien = new NhanVien();

        // Cập nhật các trường dữ liệu
        nhanVien.setIdNhanVien(this.idNhanVien);  // Đảm bảo trả về idNhanVien
        nhanVien.setTenNhanVien(editTextName.getText().toString().trim());
        nhanVien.setNgaySinh(editTextNgaySinh.getText().toString().trim());
        nhanVien.setSoDienThoai(editTextPhone.getText().toString().trim());
        nhanVien.setDiaChi(editTextDiaChi.getText().toString().trim());

        // Lấy giới tính từ RadioButton
        if (radioButtonNam.isChecked()) {
            nhanVien.setGioiTinh("Nam");
        } else if (radioButtonNu.isChecked()) {
            nhanVien.setGioiTinh("Nữ");
        } else {
            nhanVien.setGioiTinh("Khác");
        }

        return nhanVien;
    }
}
