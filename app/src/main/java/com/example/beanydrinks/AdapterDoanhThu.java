package com.example.beanydrinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterDoanhThu extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DoanhThu> arraylist;

    public AdapterDoanhThu(Context context, int layout, List<DoanhThu> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size() ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        DoanhThu doanhThu = arraylist.get(position);

        TextView madon = convertView.findViewById(R.id.tv_ma_don);
        TextView tennv = convertView.findViewById(R.id.tv_customer_name);
        TextView ban = convertView.findViewById(R.id.tv_table);
        TextView tonghoadon = convertView.findViewById(R.id.tv_price);
        TextView trangthai = convertView.findViewById(R.id.tv_payment_status);
        TextView ngayhd = convertView.findViewById(R.id.tv_date);


        madon.setText(doanhThu.getMadon());
        tennv.setText(doanhThu.getTennv());
        ban.setText(doanhThu.getBan());
        tonghoadon.setText(doanhThu.getTonghoadon());
        trangthai.setText(doanhThu.getTrangthai());
        ngayhd.setText(doanhThu.getNgayhd());



        return convertView;
    }
}
