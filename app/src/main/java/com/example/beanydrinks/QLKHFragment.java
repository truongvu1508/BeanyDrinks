package com.example.beanydrinks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beanydrinks.adapter.KhachHangAdapter;
import com.example.beanydrinks.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class QLKHFragment extends Fragment {

    private RecyclerView rcvKhachHang;
    private KhachHangAdapter khachHangAdapter;
    public QLKHFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QLKHFragment newInstance(String param1, String param2) {
        QLKHFragment fragment = new QLKHFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__q_l_k_h, container, false);
        rcvKhachHang = view.findViewById(R.id.rcv_KhachHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvKhachHang.setLayoutManager(linearLayoutManager);
        khachHangAdapter = new KhachHangAdapter(getListKhachHang());
        rcvKhachHang.setAdapter(khachHangAdapter);
        // Inflate the layout for this fragment
        return view;
    }
    private List<KhachHang> getListKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        list.add(new KhachHang("0999999999", "Đỗ Thành Bảo", 0.0));
        list.add(new KhachHang("0999999999", "Đỗ Thành Bảo", 0.0));
        list.add(new KhachHang("0999999999", "Đỗ Thành Bảo", 0.0));
        list.add(new KhachHang("0999999999", "Đỗ Thành Bảo", 0.0));
        return list;
    }
}