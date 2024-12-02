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
import com.example.beanydrinks.model.KhachHang;

import java.util.ArrayList;

public class themthongtinkhachhangAdapter extends RecyclerView.Adapter<themthongtinkhachhangAdapter.themthongtinkhachhangViewHolder> {
    private Context context;
    private ArrayList<KhachHang> khachHangList; // Danh sách khách hàng đã nhập
    private OnAddKhachHangListener onAddKhachHangListener;

    // Constructor
    public themthongtinkhachhangAdapter(Context context, OnAddKhachHangListener listener) {
        this.context = context;
        this.khachHangList = new ArrayList<>(); // Danh sách tạm thời
        this.onAddKhachHangListener = listener;
    }

    @NonNull
    @Override
    public themthongtinkhachhangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_them_thong_tin_khach_hang, parent, false);
        return new themthongtinkhachhangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull themthongtinkhachhangViewHolder holder, int position) {
        holder.btnLuu.setOnClickListener(v -> {
            // Lấy thông tin từ các trường
            String soDienThoai = holder.editSoDT.getText().toString();
            String tenKhachHang = holder.editTenKH.getText().toString();

            // Kiểm tra dữ liệu hợp lệ
            if (soDienThoai.isEmpty() || tenKhachHang.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng KhachHang
            KhachHang khachHang = new KhachHang();
            khachHang.setSoDienThoai(soDienThoai);
            khachHang.setHoTen(tenKhachHang);

            // Gọi callback để xử lý thêm khách hàng
            onAddKhachHangListener.onKhachHangAdded(khachHang);

            // Xóa dữ liệu nhập vào sau khi lưu
            clearFields(holder);
        });

        holder.btnHuy.setOnClickListener(v -> {
            // Xóa dữ liệu nhập khi bấm nút Huỷ
            clearFields(holder);
            Toast.makeText(context, "Đã huỷ bỏ thông tin", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return 1; // Chỉ hiển thị một form thêm khách hàng
    }

    public static class themthongtinkhachhangViewHolder extends RecyclerView.ViewHolder {
        EditText editSoDT, editTenKH;
        Button btnLuu, btnHuy;

        public themthongtinkhachhangViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ view từ layout
            editSoDT = itemView.findViewById(R.id.editText_soDT);
            editTenKH = itemView.findViewById(R.id.editText_tenKH);
            btnLuu = itemView.findViewById(R.id.button_luuthongtinKH);
            btnHuy = itemView.findViewById(R.id.button_huy);
        }
    }

    // Phương thức để xóa dữ liệu sau khi thêm hoặc huỷ
    private void clearFields(themthongtinkhachhangViewHolder holder) {
        holder.editSoDT.setText("");
        holder.editTenKH.setText("");
    }

    // Interface callback khi thêm khách hàng
    public interface OnAddKhachHangListener {
        void onKhachHangAdded(KhachHang khachHang);
    }
}
