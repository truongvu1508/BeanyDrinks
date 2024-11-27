package com.example.beanydrinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.txtten_nv.setText(nhanVien.getTenNhanVien());
        holder.txtchucvu_nv.setText(nhanVien.getChucVu());
        holder.txttrangthai_nv.setText(nhanVien.getTrangThai());
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
