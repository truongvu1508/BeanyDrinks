package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.model.OrderItem;

import java.util.List;

public class DonHangAdapter extends ArrayAdapter<OrderItem> {
    private final Context context;
    private final List<OrderItem> orderItemList;

    public DonHangAdapter(Context context, List<OrderItem> orderItemList) {
        super(context, R.layout.item_donhang_thanhtoan, orderItemList);
        this.context = context;
        this.orderItemList = orderItemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout if not already created
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_donhang_thanhtoan, parent, false);
        }

        // Get the current order item
        OrderItem orderItem = orderItemList.get(position);

        // Find views in the layout
        ImageView imageView = convertView.findViewById(R.id.image_mon);
        TextView tenMonTextView = convertView.findViewById(R.id.text_name);
        TextView soLuongTextView = convertView.findViewById(R.id.text_soluong);
        TextView giaTienTextView = convertView.findViewById(R.id.text_sotien);

//        // Bind data to views
//        imageView.setImageResource(orderItem.getImageResId()); // Placeholder for image resource
//        tenMonTextView.setText(orderItem.getTenMon());
//        soLuongTextView.setText(String.valueOf(orderItem.getSoLuong()));
//        giaTienTextView.setText(String.format("%.2f VNĐ", orderItem.getGiaTien()));

        return convertView;
    }
}
