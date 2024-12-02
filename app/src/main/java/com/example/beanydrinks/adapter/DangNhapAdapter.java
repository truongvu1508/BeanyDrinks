package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.model.NhanVien;

import java.util.ArrayList;

public class DangNhapAdapter extends RecyclerView.Adapter<DangNhapAdapter.DangNhapViewHolder> {

    private Context context;
    private ArrayList<NhanVien> nhanVienList; // Danh sách nhân viên
    private OnDangNhapListener onDangNhapListener;

    // Constructor
    public DangNhapAdapter(Context context, ArrayList<NhanVien> nhanVienList, OnDangNhapListener listener) {
        this.context = context;
        this.nhanVienList = nhanVienList;
        this.onDangNhapListener = listener;
    }

    @NonNull
    @Override
    public DangNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_ui_login, parent, false);
        return new DangNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DangNhapViewHolder holder, int position) {
        // Không cần xử lý danh sách vì adapter này dành cho một nhân viên đang đăng nhập
        holder.btnLogin.setOnClickListener(v -> {
            String username = holder.editTextUser.getText().toString().trim();
            String password = holder.editTextPass.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isAuthenticated = false;
            for (NhanVien nhanVien : nhanVienList) {
                if (nhanVien.getSoDienThoai().equals(username) && nhanVien.getMatKhau().equals(password)) {
                    isAuthenticated = true;
                    onDangNhapListener.onLoginSuccess(nhanVien);
                    break;
                }
            }

            if (!isAuthenticated) {
                Toast.makeText(context, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1; // Hiển thị một màn hình đăng nhập
    }

    public static class DangNhapViewHolder extends RecyclerView.ViewHolder {
        EditText editTextUser, editTextPass;
        Button btnLogin;

        public DangNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextUser = itemView.findViewById(R.id.editText_User);
            editTextPass = itemView.findViewById(R.id.editText_Pass);
            btnLogin = itemView.findViewById(R.id.btn_Login);
        }
    }

    public interface OnDangNhapListener {
        void onLoginSuccess(NhanVien nhanVien); // Gọi khi đăng nhập thành công
    }
}
