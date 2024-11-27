package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beanydrinks.model.DonHang;
import com.example.beanydrinks.R;

import java.util.List;

public class DonHangAdapter extends ArrayAdapter<DonHang> {
    private final Context context;
    private final List<DonHang> donHangList;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        super(context, R.layout.item_donhang_thanhtoan, donHangList);
        this.context = context;
        this.donHangList = donHangList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_donhang_thanhtoan, parent, false);
        }

        DonHang donHang = donHangList.get(position);

        ImageView imageView = convertView.findViewById(R.id.image_mon);
        TextView tenMonTextView = convertView.findViewById(R.id.text_name);
        TextView soLuongTextView = convertView.findViewById(R.id.text_soluong);
        TextView giaTienTextView = convertView.findViewById(R.id.text_sotien);

        imageView.setImageResource(donHang.getImageResId());
        tenMonTextView.setText(donHang.getTenMon());
        soLuongTextView.setText(String.valueOf(donHang.getSoLuong()));
        giaTienTextView.setText(donHang.getGiaTien());

        return convertView;
    }
}
