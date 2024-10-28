package com.example.beanydrinks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class DoanhThuNv extends Fragment {

    public DoanhThuNv() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doanh_thu_nv, container, false);
        ListView listView;
        ArrayList<DoanhThu> arrayList;
        AdapterDoanhThu adapterDoanhThu;

        listView = view.findViewById(R.id.listviewdoanhthu);
        arrayList = new ArrayList<>();
        arrayList.add(new DoanhThu("01", "Nguyen Truong Vu","01", "180000 đ", "Đã Thanh Toán","30-09-2024");
        arrayList.add(new DoanhThu("02", "Do Thanh Bao","02", "180000 đ", "Chưa Thanh Toán","30-09-2024");


        adapterDoanhThu = new AdapterDoanhThu(getContext(),R.layout.layout_doanhthu,arrayList);
        listView.setAdapter(adapterDoanhThu);

        return view;
    }
}