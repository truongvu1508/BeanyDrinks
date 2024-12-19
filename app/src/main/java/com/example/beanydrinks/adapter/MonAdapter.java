package com.example.beanydrinks.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.model.Mon;
import com.example.beanydrinks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MonViewHolder> {

    private List<Mon> monList;

    public MonAdapter(List<Mon> monList) {
        this.monList = monList;
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thucdon, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        Mon currentMon = monList.get(position);

        // Kiểm tra dữ liệu trong onBindViewHolder
        Log.d("MonAdapter", "Binding item: " + currentMon.getTenMon() + " at position: " + position);

        holder.tvTenMon.setText(currentMon.getTenMon());
        holder.tvGiaTien.setText(String.format("%s VNĐ", currentMon.getGiaTien()));

        // Xử lý hình ảnh
        String hinhAnh = currentMon.getHinhAnh();
        if (hinhAnh != null && hinhAnh.startsWith("http")) {
            Picasso.get()
                    .load(hinhAnh)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.errorimage)
                    .resize(400, 400)
                    .into(holder.imgMon);
        } else {
            int resourceId = holder.itemView.getContext().getResources().getIdentifier(hinhAnh, "drawable", holder.itemView.getContext().getPackageName());
            Picasso.get()
                    .load(resourceId)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.errorimage)
                    .into(holder.imgMon);
        }
    }

    @Override
    public int getItemCount() {
        return monList.size();
    }

    static class MonViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMon;
        TextView tvTenMon;
        TextView tvGiaTien;

        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMon = itemView.findViewById(R.id.imageView_mon);
            tvTenMon = itemView.findViewById(R.id.text_name);
            tvGiaTien = itemView.findViewById(R.id.text_GiaTien);
        }
    }
}