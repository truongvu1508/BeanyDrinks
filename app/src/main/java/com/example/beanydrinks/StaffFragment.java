package com.example.beanydrinks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends Fragment {

    private RecyclerView rcvNhanVien;
    private NhanVienAdapter nhanVienAdapter;
    private FloatingActionButton btnAddNV;

    public StaffFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        rcvNhanVien = view.findViewById(R.id.rcv_NhanVien);
        btnAddNV = view.findViewById(R.id.btn_addBan);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvNhanVien.setLayoutManager(linearLayoutManager);
        nhanVienAdapter = new NhanVienAdapter(getListNhanVien());
        rcvNhanVien.setAdapter(nhanVienAdapter);

        btnAddNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNhanVienFragment addNhanVienFragment = new AddNhanVienFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, addNhanVienFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private List<NhanVien> getListNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        list.add(new NhanVien(R.drawable.user_img, "Đỗ Thành Bảo", "Quản lý", "Đang làm việc"));
        list.add(new NhanVien(R.drawable.user_img, "Lê Quang Mạnh Hùng", "Bảo vệ", "Đang làm việc"));
        list.add(new NhanVien(R.drawable.user_img, "Nguyễn Trường Vũ", "Nhân viên", "Đang làm việc"));
        list.add(new NhanVien(R.drawable.user_img, "Nguyễn Văn A", "Nhân viên", "Đang làm việc"));
        return list;
    }
}
