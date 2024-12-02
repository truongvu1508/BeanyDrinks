package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.beanydrinks.R;
import com.example.beanydrinks.activity.orderban_nvActivity;
import com.example.beanydrinks.model.OrderItem;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderItem> orderItems;

    public OrderAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orderban_nv, parent, false);
        }

        // Ánh xạ các view từ item_orderban_nv
        ImageView imageMon = convertView.findViewById(R.id.image_mon);
        TextView textTenMon = convertView.findViewById(R.id.text_TenMon);
        TextView textSoTien = convertView.findViewById(R.id.text_SoTien);
        TextView textSoLuong = convertView.findViewById(R.id.text_soluong);
        ImageButton btnMinus = convertView.findViewById(R.id.imageButton_minus);
        ImageButton btnAdd = convertView.findViewById(R.id.imageButton_add);

        // Lấy đối tượng OrderItem
        OrderItem orderItem = orderItems.get(position);

        // Cập nhật tên món, giá, số lượng, và hình ảnh
        textTenMon.setText(orderItem.getSanPham().getTenMon());
        double price = Double.parseDouble(orderItem.getSanPham().getGiaTien());
        textSoTien.setText(price + " VNĐ");
        textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));

        // Hiển thị hình ảnh món (Sử dụng Glide để tải hình ảnh nếu có URL hoặc đường dẫn hợp lệ)
        Glide.with(context)
                .load(orderItem.getSanPham().getHinhAnh())  // Đây là đường dẫn ảnh từ `OrderItem`
                .placeholder(R.drawable.noimage)  // Placeholder khi chưa có ảnh
                .into(imageMon);

        // Cập nhật thanh tiền
        updateThanhTien(orderItem, textSoTien);  // Update total price based on quantity

        btnMinus.setOnClickListener(v -> {
            if (orderItem.getSoLuong() > 1) {
                orderItem.setSoLuong(orderItem.getSoLuong() - 1);
                textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
                updateThanhTien(orderItem, textSoTien);  // Update the total price for this item
                notifyDataSetChanged();

                // Update temporary total and total with VAT in orderban_nvActivity
                ((orderban_nvActivity) context).updateTamTinh();  // This will update txt_tienTamTinh and txt_tongTien
            }
        });

        btnAdd.setOnClickListener(v -> {
            orderItem.setSoLuong(orderItem.getSoLuong() + 1);
            textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
            updateThanhTien(orderItem, textSoTien);  // Update the total price for this item
            notifyDataSetChanged();

            // Update temporary total and total with VAT in orderban_nvActivity
            ((orderban_nvActivity) context).updateTamTinh();  // This will update txt_tienTamTinh and txt_tongTien
        });

        return convertView;
    }

    // Helper method to update the total price (thanhTien)
    private void updateThanhTien(OrderItem orderItem, TextView textSoTien) {
        double price = Double.parseDouble(orderItem.getSanPham().getGiaTien());
        int quantity = orderItem.getSoLuong();
        double thanhTien = price * quantity;  // Calculate total price
        orderItem.setThanhTien(thanhTien);  // Update the total price in the OrderItem
        textSoTien.setText(String.format("%.2f VNĐ", thanhTien));  // Update the TextView with the new total
    }



}
