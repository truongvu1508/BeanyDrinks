package com.example.beanydrinks;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThemThucDonQL extends Fragment {
    private FloatingActionButton btnAddMon;
    public ThemThucDonQL() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_them_thuc_don_q_l, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnbackthemttkh);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new thucDonQl())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
