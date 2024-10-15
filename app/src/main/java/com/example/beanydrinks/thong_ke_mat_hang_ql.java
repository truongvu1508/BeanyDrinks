package com.example.beanydrinks;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thong_ke_mat_hang_ql#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thong_ke_mat_hang_ql extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public thong_ke_mat_hang_ql() {
        // Required empty public constructor
    }

    public static thong_ke_mat_hang_ql newInstance(String param1, String param2) {
        thong_ke_mat_hang_ql fragment = new thong_ke_mat_hang_ql();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_mat_hang_ql, container, false);

        // Thiết lập biểu đồ hình tròn
        PieChart pieChart = view.findViewById(R.id.pieChart);

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(508, "2016"));
        visitors.add(new PieEntry(600, "2017"));
        visitors.add(new PieEntry(750, "2018"));
        visitors.add(new PieEntry(600, "2019"));
        visitors.add(new PieEntry(670, "2020"));

        PieDataSet pieDataSet = new PieDataSet(visitors, "Thống kê");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Thống kê");
        pieChart.animate();

        return view;
    }
}
