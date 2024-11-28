package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.R;

import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ItemHolder> {
    Context context;
    ArrayList<NhanVien> arraynv;

    public NhanVienAdapter(ArrayList<NhanVien> arraynv, Context context) {
        this.arraynv = arraynv;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        NhanVien nhanVien = arraynv.get(position);

        // Kiểm tra xem role có phải là "admin" không, nếu có thì không hiển thị
        if ("admin".equals(nhanVien.getRole())) {
            // Ẩn item khi role là "admin"
            holder.itemView.setVisibility(View.GONE);
        } else {
            // Hiển thị thông tin nhân viên nếu không phải là "admin"
            holder.itemView.setVisibility(View.VISIBLE);
            holder.txtten_nv.setText(nhanVien.getTenNhanVien());
            holder.txtchucvu_nv.setText(nhanVien.getChucVu());
            holder.txttrangthai_nv.setText(nhanVien.getTrangThai());
        }
    }

    @Override
    public int getItemCount() {
        return arraynv.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView txtten_nv,txtchucvu_nv,txttrangthai_nv;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            txtten_nv = (TextView) itemView.findViewById(R.id.text_name);
            txtchucvu_nv =(TextView) itemView.findViewById(R.id.textView_ChucVu);
            txttrangthai_nv =(TextView) itemView.findViewById(R.id.textView_TrangThai);
        }
    }
}
