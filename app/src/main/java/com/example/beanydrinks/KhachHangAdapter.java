package com.example.beanydrinks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder> {
    private List<KhachHang> mListKhachHang;

    public KhachHangAdapter(List<KhachHang> mListKhachHang) {
        this.mListKhachHang = mListKhachHang;
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_khachhang, parent, false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang khachHang = mListKhachHang.get(position);
        if (khachHang == null) {
            return;
        }

        holder.imgKhachHang.setImageResource(khachHang.getHinhAnh());  // Thay thế với hình ảnh mặc định
        holder.tvHoTen.setText(khachHang.getHoTen());
        holder.tvSoDienThoai.setText(khachHang.getSoDienThoai());

    }

    @Override
    public int getItemCount() {
        return (mListKhachHang != null) ? mListKhachHang.size() : 0;
    }

    public static class KhachHangViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgKhachHang;
        private TextView tvHoTen;
        private TextView tvSoDienThoai;

        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKhachHang = itemView.findViewById(R.id.image_nhanvien);
            tvHoTen = itemView.findViewById(R.id.text_name);
            tvSoDienThoai = itemView.findViewById(R.id.textView_soDienThoai);
        }
    }
}
