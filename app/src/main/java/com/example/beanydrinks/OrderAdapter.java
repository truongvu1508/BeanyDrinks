package com.example.beanydrinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderBan> danhSachOrder;

    public OrderAdapter(Context context, List<OrderBan> danhSachOrder) {
        this.context = context;
        this.danhSachOrder = danhSachOrder;
    }

    @Override
    public int getCount() {
        return danhSachOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return danhSachOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orderban_nv, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tenMon = convertView.findViewById(R.id.text_TenMon);
            viewHolder.giaTien = convertView.findViewById(R.id.text_SoTien);
            viewHolder.soLuong = convertView.findViewById(R.id.text_soluong);
            viewHolder.hinhMon = convertView.findViewById(R.id.image_mon);
            viewHolder.btnAdd = convertView.findViewById(R.id.imageButton_add);
            viewHolder.btnMinus = convertView.findViewById(R.id.imageButton_minus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrderBan orderItem = danhSachOrder.get(position);
        viewHolder.tenMon.setText(orderItem.getTenMon());
        viewHolder.giaTien.setText(orderItem.getGiaTien());
        viewHolder.soLuong.setText(String.valueOf(orderItem.getSoLuong()));
        viewHolder.hinhMon.setImageResource(orderItem.getHinhMon());

        // Xử lý sự kiện cho nút thêm
        viewHolder.btnAdd.setOnClickListener(v -> {
            int currentQuantity = orderItem.getSoLuong();
            orderItem.setSoLuong(currentQuantity + 1); // Tăng số lượng
            viewHolder.soLuong.setText(String.valueOf(orderItem.getSoLuong())); // Cập nhật giao diện
        });

        // Xử lý sự kiện cho nút giảm
        viewHolder.btnMinus.setOnClickListener(v -> {
            int currentQuantity = orderItem.getSoLuong();
            if (currentQuantity > 0) { // Kiểm tra để không giảm dưới 0
                orderItem.setSoLuong(currentQuantity - 1); // Giảm số lượng
                viewHolder.soLuong.setText(String.valueOf(orderItem.getSoLuong())); // Cập nhật giao diện
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tenMon;
        TextView giaTien;
        TextView soLuong;
        ImageView hinhMon;
        ImageButton btnAdd;
        ImageButton btnMinus;
    }
}

