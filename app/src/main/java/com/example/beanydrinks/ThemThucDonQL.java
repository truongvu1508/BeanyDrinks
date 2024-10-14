package com.example.beanydrinks;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ThemThucDonQL extends Fragment {

    public ThemThucDonQL() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_them_thuc_don_q_l, container, false);

        // Tìm imageButton6 và thiết lập sự kiện nhấn
        ImageButton imageButton6 = view.findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về fragment_thuc_don_ql
                Fragment thucDonQlFragment = new thucDonQl();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, thucDonQlFragment) // ID của container
                        .addToBackStack(null) // Thêm vào back stack nếu muốn quay lại
                        .commit();
            }
        });

        return view;
    }
}
