package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.OrderItem;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final Context context;
    private final List<OrderItem> orderItems;
    private final TextView txtTienTamTinh;
    private final TextView txtTongTien;

    // Constructor
    public OrderAdapter(Context context, List<OrderItem> orderItems, TextView txtTienTamTinh, TextView txtTongTien) {
        this.context = context;
        this.orderItems = orderItems;
        this.txtTienTamTinh = txtTienTamTinh;
        this.txtTongTien = txtTongTien;
        updateTamTinh(); // Tự động cập nhật tổng tiền khi khởi tạo
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderban_nv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);

        holder.textTenMon.setText(orderItem.getSanPham().getTenMon());
        double price = Double.parseDouble(orderItem.getSanPham().getGiaTien());
        holder.textSoTien.setText(String.format("%.2f VNĐ", price * orderItem.getSoLuong()));
        holder.textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));

        Glide.with(context)
                .load(orderItem.getSanPham().getHinhAnh())
                .placeholder(R.drawable.noimage)
                .into(holder.imageMon);

        // Update total price for this item
        updateThanhTien(orderItem, holder.textSoTien);

        // Decrease quantity button
        holder.btnMinus.setOnClickListener(v -> {
            if (orderItem.getSoLuong() > 1) {
                orderItem.setSoLuong(orderItem.getSoLuong() - 1);
                holder.textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
                updateThanhTien(orderItem, holder.textSoTien);
                notifyDataSetChanged();
                updateTamTinh(); // Update total after quantity change
            }
        });

        // Increase quantity button
        holder.btnAdd.setOnClickListener(v -> {
            orderItem.setSoLuong(orderItem.getSoLuong() + 1);
            holder.textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
            updateThanhTien(orderItem, holder.textSoTien);
            notifyDataSetChanged();
            updateTamTinh(); // Update total after quantity change
        });
    }

    // ViewHolder class to optimize RecyclerView performance
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMon;
        TextView textTenMon, textSoTien, textSoLuong;
        ImageButton btnMinus, btnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            imageMon = itemView.findViewById(R.id.image_mon);
            textTenMon = itemView.findViewById(R.id.text_TenMon);
            textSoTien = itemView.findViewById(R.id.text_SoTien);
            textSoLuong = itemView.findViewById(R.id.text_soluong);
            btnMinus = itemView.findViewById(R.id.imageButton_minus);
            btnAdd = itemView.findViewById(R.id.imageButton_add);
        }
    }

    // Method to update the total price (thanhTien)
    private void updateThanhTien(OrderItem orderItem, TextView textSoTien) {
        double price = Double.parseDouble(orderItem.getSanPham().getGiaTien());
        double thanhTien = price * orderItem.getSoLuong();
        orderItem.setThanhTien(thanhTien);
        textSoTien.setText(String.format("%.2f VNĐ", thanhTien)); // Update TextView with the new total
    }

    // Method to update subtotal and total price
    private void updateTamTinh() {
        double tamTinh = 0.0;
        for (OrderItem orderItem : orderItems) {
            tamTinh += orderItem.getThanhTien();
        }
        txtTienTamTinh.setText(String.format("%.2f VNĐ", tamTinh));
        txtTongTien.setText(String.format("%.2f VNĐ", tamTinh));
    }
}