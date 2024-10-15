package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuanLyKhuVucNVFragment extends Fragment {

    public QuanLyKhuVucNVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly_khu_vuc_nv, container, false);

        // Find the TextView by its ID
        TextView banVip01 = view.findViewById(R.id.banVip01);

        // Set an OnClickListener to open OrderBanBV activity
        banVip01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start OrderBanBV activity
                Intent intent = new Intent(getActivity(), orderban_nv.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
