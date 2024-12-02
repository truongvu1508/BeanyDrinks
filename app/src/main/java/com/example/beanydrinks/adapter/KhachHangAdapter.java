package com.example.beanydrinks.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.model.KhachHang;

import java.util.ArrayList;
import java.util.List;


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

        holder.tvHoTen.setText(khachHang.getHoTen());
        holder.tvSoDienThoai.setText(khachHang.getSoDienThoai());
        holder.tvDiemThuong.setText(String.format("%.1f điểm", khachHang.getDiemThuong()));
    }

    @Override
    public int getItemCount() {
        return (mListKhachHang != null) ? mListKhachHang.size() : 0;
    }

    public static class KhachHangViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHoTen;
        private TextView tvSoDienThoai;
        private TextView tvDiemThuong;

        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen = itemView.findViewById(R.id.text_name);
            tvSoDienThoai = itemView.findViewById(R.id.textView_soDienThoai);
            tvDiemThuong=itemView.findViewById(R.id.text_DiemThuongKH);
        }
    }
}
