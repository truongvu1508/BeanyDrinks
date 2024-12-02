package com.example.beanydrinks.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.model.NhanVien;

import java.util.ArrayList;

public class AddNhanVienAdapter extends RecyclerView.Adapter<AddNhanVienAdapter.AddNhanVienViewHolder> {
    private Context context;
    private ArrayList<NhanVien> nhanVienList; // Danh sách để lưu nhân viên đã nhập
    private OnAddNhanVienListener onAddNhanVienListener;

    // Constructor
    public AddNhanVienAdapter(Context context, OnAddNhanVienListener listener) {
        this.context = context;
        this.nhanVienList = new ArrayList<>(); // Danh sách tạm thời
        this.onAddNhanVienListener = listener;
    }

    @NonNull
    @Override
    public AddNhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_add_nhan_vien, parent, false);
        return new AddNhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddNhanVienViewHolder holder, int position) {
        holder.btnAdd.setOnClickListener(v -> {
            // Lấy thông tin từ các field
            String hoVaTen = holder.editName.getText().toString();
            String ngaySinh = holder.editNgaySinh.getText().toString();
            String soDienThoai = holder.editPhone.getText().toString();
            String diaChi = holder.editDiaChi.getText().toString();
            String matKhau = holder.editMatKhau.getText().toString();

            // Giới tính
            String gioiTinh = "Nam";
            if (holder.radioNu.isChecked()) {
                gioiTinh = "Nữ";
            } else if (holder.radioKhac.isChecked()) {
                gioiTinh = "Khác";
            }

            // Tạo đối tượng NhanVien
            NhanVien nhanVien = new NhanVien();
            nhanVien.setTenNhanVien(hoVaTen);
            nhanVien.setNgaySinh(ngaySinh);
            nhanVien.setSoDienThoai(soDienThoai);
            nhanVien.setDiaChi(diaChi);
            nhanVien.setMatKhau(matKhau);
            nhanVien.setGioiTinh(gioiTinh);
            // Chức vụ lấy từ Spinner
            nhanVien.setChucVu(holder.spinnerChucVu.getSelectedItem().toString());

            // Gọi callback để xử lý logic thêm nhân viên
            onAddNhanVienListener.onNhanVienAdded(nhanVien);

            // Xóa dữ liệu nhập vào
            clearFields(holder);
        });
    }

    @Override
    public int getItemCount() {
        return 1; // Adapter chỉ hiển thị một form để thêm nhân viên
    }

    public static class AddNhanVienViewHolder extends RecyclerView.ViewHolder {
        EditText editName, editNgaySinh, editPhone, editDiaChi, editMatKhau;
        RadioButton radioNam, radioNu, radioKhac;
        Spinner spinnerChucVu;
        Button btnAdd;

        public AddNhanVienViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ view
            editName = itemView.findViewById(R.id.editText_Name_2);
            editNgaySinh = itemView.findViewById(R.id.ngaySinh_2);
            editPhone = itemView.findViewById(R.id.editTextPhone_2);
            editDiaChi = itemView.findViewById(R.id.editText_User_2);
            editMatKhau = itemView.findViewById(R.id.editTextPassword_2);
            radioNam = itemView.findViewById(R.id.radioButton_Nam_1);
            radioNu = itemView.findViewById(R.id.radioButton_Nu_1);
            radioKhac = itemView.findViewById(R.id.radioButton_Khac_1);
            spinnerChucVu = itemView.findViewById(R.id.spinnerChucVu);
            btnAdd = itemView.findViewById(R.id.button_addnv2);
        }
    }

    // Phương thức để xóa dữ liệu sau khi thêm
    private void clearFields(AddNhanVienViewHolder holder) {
        holder.editName.setText("");
        holder.editNgaySinh.setText("");
        holder.editPhone.setText("");
        holder.editDiaChi.setText("");
        holder.editMatKhau.setText("");
        holder.radioNam.setChecked(true); // Mặc định là Nam
        holder.spinnerChucVu.setSelection(0); // Mặc định chọn mục đầu tiên
    }

    // Interface callback khi thêm nhân viên
    public interface OnAddNhanVienListener {
        void onNhanVienAdded(NhanVien nhanVien);
    }
}
