package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.OrderBan;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Context context;
    private final List<OrderBan> danhSachOrder;

    public OrderAdapter(Context context, List<OrderBan> danhSachOrder) {
        this.context = context;
        this.danhSachOrder = danhSachOrder;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderban_nv, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderBan orderItem = danhSachOrder.get(position);

        // Bind dữ liệu vào ViewHolder
        holder.textTenMon.setText(orderItem.getTenMon());
        holder.textSoTien.setText(orderItem.getGiaTien() + " VNĐ");
        holder.textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));

        // Sử dụng Glide để tải ảnh
        Glide.with(context)
                .load(orderItem.getHinhMon()) // URL hoặc resource ID của ảnh
                .placeholder(R.drawable.noimage) // Ảnh mặc định khi tải
                .into(holder.imageMon);

        // Xử lý sự kiện tăng số lượng
        holder.btnAdd.setOnClickListener(v -> {
            int currentQuantity = orderItem.getSoLuong();
            orderItem.setSoLuong(currentQuantity + 1);
            holder.textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
        });

        // Xử lý sự kiện giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            int currentQuantity = orderItem.getSoLuong();
            if (currentQuantity > 0) {
                orderItem.setSoLuong(currentQuantity - 1);
                holder.textSoLuong.setText(String.valueOf(orderItem.getSoLuong()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachOrder.size();
    }

    // ViewHolder để ánh xạ các thành phần trong layout
    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMon;
        TextView textTenMon, textSoTien, textSoLuong;
        ImageButton btnAdd, btnMinus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMon = itemView.findViewById(R.id.image_mon);
            textTenMon = itemView.findViewById(R.id.text_TenMon);
            textSoTien = itemView.findViewById(R.id.text_SoTien);
            textSoLuong = itemView.findViewById(R.id.text_soluong);
            btnAdd = itemView.findViewById(R.id.imageButton_add);
            btnMinus = itemView.findViewById(R.id.imageButton_minus);
        }
    }
}
