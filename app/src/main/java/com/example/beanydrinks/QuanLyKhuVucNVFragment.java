package com.example.beanydrinks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLyKhuVucNVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLyKhuVucNVFragment extends Fragment {

    public QuanLyKhuVucNVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_khu_vuc_nv, container, false);
    }
}