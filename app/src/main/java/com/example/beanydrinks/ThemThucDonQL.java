package com.example.beanydrinks;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // Import Button
import android.widget.ImageButton;

public class ThemThucDonQL extends Fragment {

    public ThemThucDonQL() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_thuc_don_q_l, container, false);

        // Tìm kiếm và thiết lập btnBack
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại FragmentThucDonQL
                requireActivity().getSupportFragmentManager().popBackStack(); // Quay lại fragment trước đó
            }
        });

        // Thiết lập button_add
        Button buttonAdd = view.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại FragmentThucDonQL khi nhấn nút thêm
                requireActivity().getSupportFragmentManager().popBackStack(); // Quay lại fragment trước đó
            }
        });

        return view;
    }
}
