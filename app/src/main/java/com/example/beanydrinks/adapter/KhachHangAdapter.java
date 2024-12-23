package com.example.beanydrinks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.model.KhachHang;
import com.example.beanydrinks.R;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder>{
    private ArrayList<KhachHang> mangkh;
    private final FragmentActivity activity;
    public KhachHangAdapter(ArrayList mangkh, FragmentActivity activity) {
        this.mangkh = mangkh;
        this.activity = activity;
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_khachhang, parent, false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang khachHang = mangkh.get(position);
        if (khachHang == null) {
            return;
        }

        holder.tvHoTen.setText(khachHang.getHoTen());
        holder.tvSoDienThoai.setText(khachHang.getSoDienThoai());
        holder.tvDiemThuong.setText(String.format("%.1f điểm", khachHang.getDiemThuong()));
    }

    @Override
    public int getItemCount() {
        return (mangkh != null) ? mangkh.size() : 0;
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