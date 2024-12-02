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
import com.example.beanydrinks.model.OrderItem;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

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

        // Map views from layout
        ImageView imageMon = convertView.findViewById(R.id.image_mon);
        TextView textTenMon = convertView.findViewById(R.id.text_TenMon);
        TextView textSoTien = convertView.findViewById(R.id.text_SoTien);
        TextView textSoLuong = convertView.findViewById(R.id.text_soluong);
        ImageButton btnMinus = convertView.findViewById(R.id.imageButton_minus);
        ImageButton btnAdd = convertView.findViewById(R.id.imageButton_add);

        // Get the current OrderItem
        OrderItem orderItem = orderItems.get(position);

        // Populate item data
        textTenMon.setText(orderItem.getSanPham().getTenMon());
        double price = Double.parseDouble(orderItem.getSanPham().getGiaTien());
        textSoTien.setText(String.format("%.2f VNĐ", price * orderItem.getSoLuong()));
        textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));

        // Load item image
        Glide.with(context)
                .load(orderItem.getSanPham().getHinhAnh())
                .placeholder(R.drawable.noimage)
                .into(imageMon);

        // Update the total price for this item
        updateThanhTien(orderItem, textSoTien);

        // Button to decrease quantity
        btnMinus.setOnClickListener(v -> {
            if (orderItem.getSoLuong() > 1) {
                orderItem.setSoLuong(orderItem.getSoLuong() - 1);
                textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
                updateThanhTien(orderItem, textSoTien);
                notifyDataSetChanged();
                updateTamTinh(); // Update totals after quantity change
            }
        });

        // Button to increase quantity
        btnAdd.setOnClickListener(v -> {
            orderItem.setSoLuong(orderItem.getSoLuong() + 1);
            textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
            updateThanhTien(orderItem, textSoTien);
            notifyDataSetChanged();
            updateTamTinh(); // Update totals after quantity change
        });

        return convertView;
    }

    // Helper method to update the total price (thanhTien)
    private void updateThanhTien(OrderItem orderItem, TextView textSoTien) {
        double price = Double.parseDouble(orderItem.getSanPham().getGiaTien());
        double thanhTien = price * orderItem.getSoLuong();
        orderItem.setThanhTien(thanhTien); // Update total price in the OrderItem
        textSoTien.setText(String.format("%.2f VNĐ", thanhTien)); // Update the TextView with the new total
    }

    // Method to update the subtotal and total price
    private void updateTamTinh() {
        double tamTinh = 0.0;
        for (OrderItem orderItem : orderItems) {
            tamTinh += orderItem.getThanhTien();
        }
        txtTienTamTinh.setText(String.format("%.2f VNĐ", tamTinh));

        // Example: You can add VAT or other calculations here if needed
        txtTongTien.setText(String.format("%.2f VNĐ", tamTinh));
    }
}
