package com.example.beanydrinks;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class thucDonQl extends Fragment {

    public thucDonQl() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thuc_don_ql, container, false);

        // Khởi tạo nút và thiết lập sự kiện click
        Button buttonNavigate = view.findViewById(R.id.button4);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Fragment ThemThucDonQL
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ThemThucDonQL themThucDonQLFragment = new ThemThucDonQL();
                fragmentTransaction.replace(R.id.fragment_container, themThucDonQLFragment); // `fragment_container` là ID của container chứa các Fragment
                fragmentTransaction.addToBackStack(null); // Thêm vào back stack để có thể quay lại
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
