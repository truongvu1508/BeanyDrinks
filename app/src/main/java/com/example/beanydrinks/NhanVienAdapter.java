package com.example.beanydrinks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder> {
    private List<NhanVien> mListNhanVien;
    private FragmentManager fragmentManager;

    public NhanVienAdapter(List<NhanVien> mListNhanVien) {
        this.mListNhanVien = mListNhanVien;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien, parent, false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = mListNhanVien.get(position);
        if (nhanVien == null) {
            return;
        }
        holder.imgNhanVien.setImageResource(nhanVien.getImage());
        holder.tvName.setText(nhanVien.getName());
        holder.tvPosition.setText(nhanVien.getPosition());
        holder.tvStatus.setText(nhanVien.getStatus());

        // Set OnClickListener for button
        holder.buttonXemThongTin.setOnClickListener(v -> {
            // Mở XemNhanVienFragment
            XemNhanVienFragment xemNhanVienFragment = new XemNhanVienFragment();
            FragmentTransaction transaction = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, xemNhanVienFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }


    @Override
    public int getItemCount() {
        return (mListNhanVien != null) ? mListNhanVien.size() : 0;
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgNhanVien;
        private TextView tvName;
        private TextView tvPosition;
        private TextView tvStatus;
        private Button buttonXemThongTin;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNhanVien = itemView.findViewById(R.id.image_nhanvien);
            tvName = itemView.findViewById(R.id.text_name);
            tvPosition = itemView.findViewById(R.id.textView_ChucVu);
            tvStatus = itemView.findViewById(R.id.textView_TrangThai);
            buttonXemThongTin = itemView.findViewById(R.id.button_XemThongTin);
        }
    }
}
