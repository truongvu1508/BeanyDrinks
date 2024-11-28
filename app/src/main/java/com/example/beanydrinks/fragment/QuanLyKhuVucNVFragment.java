package com.example.beanydrinks.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.BanAdapter;
import com.example.beanydrinks.model.Ban;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuanLyKhuVucNVFragment extends Fragment {
    private RecyclerView recyclerView;
    private BanAdapter banAdapter;
    private List<Ban> banList;

    public QuanLyKhuVucNVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_khu_vuc_nv, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rcv_Ban);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Initialize data
        banList = new ArrayList<>();
        banList.add(new Ban("V.I.P-01", "Bàn trống", "Khu VIP"));
        banList.add(new Ban("V.I.P-02", "Đang phục vụ", "Khu VIP"));
        banList.add(new Ban("V.I.P-03", "Đã thanh toán", "Khu VIP"));
        banList.add(new Ban("V.I.P-04", "Yêu cầu thanh toán", "Khu VIP"));

        // Set up adapter
        banAdapter = new BanAdapter(getContext(), banList);
        recyclerView.setAdapter(banAdapter);

        FloatingActionButton btnAddBan = view.findViewById(R.id.btn_addBan);
        btnAddBan.setOnClickListener(v -> banAdapter.showAddTableDialog());


        return view;
    }
//    public void updateTableStatus(int position, String newStatus) {
//        Ban ban = banList.get(position);
//        ban.setTrangThai(newStatus); // Cập nhật trạng thái
//        banAdapter.notifyItemChanged(position); // Thông báo cho adapter để cập nhật giao diện
//    }
}
