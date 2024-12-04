package com.example.beanydrinks.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.Mon;
import com.example.beanydrinks.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class ChonMonAdapter extends RecyclerView.Adapter<ChonMonAdapter.MonViewHolder> {

    private List<Mon> monList;
    private final Context context;
    private List<OrderItem> selectedItems = new ArrayList<>();

    public ChonMonAdapter(Context context) {
        this.context = context;
    }

    public void setMonList(List<Mon> monList) {
        this.monList = monList;
        notifyDataSetChanged();
    }

    public void setSelectedItems(List<OrderItem> items) {
        selectedItems.clear();
        selectedItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<OrderItem> getSelectedItems() {
        return selectedItems;
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chonmon, parent, false);
        return new MonViewHolder(view);
    }

    public void updateData(List<Mon> updatedList) {
        this.monList = updatedList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        if (monList != null && position < monList.size()) {
            Mon mon = monList.get(position);

            // Set tên món và giá
            holder.textTenMon.setText(mon.getTenMon());
            holder.textSoTien.setText(mon.getGiaTien() + " VNĐ");

            // Load ảnh món ăn từ URL
            Glide.with(context)
                    .load(mon.getHinhAnh())
                    .placeholder(R.drawable.noimage)
                    .into(holder.imageMon);

            // Kiểm tra nếu món đã được chọn trước đó
            boolean isSelected = false;
            for (OrderItem item : selectedItems) {
                if (item.getSanPham().getIdSanPham().equals(mon.getIdSanPham())) {
                    isSelected = true;
                    break;
                }
            }

            // Cập nhật trạng thái nút "Chọn" hoặc "Đã chọn"
            if (isSelected) {
                holder.buttonChonMon.setText("Đã chọn");
                holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenblue); // Nền xanh
                holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.white)); // Chữ trắng
            } else {
                holder.buttonChonMon.setText("Chọn");
                holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenxam); // Nền xám
                holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.orange)); // Chữ cam
            }

            // Sự kiện khi nhấn nút chọn món
            holder.buttonChonMon.setOnClickListener(v -> {
                if (holder.buttonChonMon.getText().toString().equals("Chọn")) {
                    // Chuyển nút sang trạng thái "Đã chọn"
                    holder.buttonChonMon.setText("Đã chọn");
                    holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenblue); // Nền xanh
                    holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.white)); // Chữ trắng

                    // Thêm món vào danh sách đã chọn
                    OrderItem orderItem = new OrderItem(0, 0, mon, 1, Double.parseDouble(mon.getGiaTien()));
                    selectedItems.add(orderItem);
                    Log.d("ChonMonAdapter", "Món đã chọn: " + mon.getTenMon());
                } else {
                    // Chuyển nút về trạng thái "Chọn"
                    holder.buttonChonMon.setText("Chọn");
                    holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenxam); // Nền xám
                    holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.orange)); // Chữ cam

                    // Bỏ món ra khỏi danh sách đã chọn
                    selectedItems.removeIf(item -> item.getSanPham().getIdSanPham().equals(mon.getIdSanPham()));
                    Log.d("ChonMonAdapter", "Món đã bỏ chọn: " + mon.getTenMon());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return (monList != null) ? monList.size() : 0;
    }

    static class MonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMon;
        TextView textTenMon;
        TextView textSoTien;
        Button buttonChonMon;

        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMon = itemView.findViewById(R.id.image_mon);
            textTenMon = itemView.findViewById(R.id.text_TenMon);
            textSoTien = itemView.findViewById(R.id.text_SoTien);
            buttonChonMon = itemView.findViewById(R.id.button_chonmon);
        }
    }
}