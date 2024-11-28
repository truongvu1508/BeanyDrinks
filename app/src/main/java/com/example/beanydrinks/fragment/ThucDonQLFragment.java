package com.example.beanydrinks.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.MonAdapter;
import com.example.beanydrinks.model.Mon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ThucDonQLFragment extends Fragment {

    private RecyclerView recyclerViewMon;
    private MonAdapter monAdapter;
    private List<Mon> monList;
    private List<Mon> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuc_don_ql, container, false);

        // Initialize RecyclerView
        recyclerViewMon = view.findViewById(R.id.rcv_DSMon);
        recyclerViewMon.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Hiển thị 2 cột

        // Initialize list of dishes
        monList = new ArrayList<>();
        monList.add(new Mon("Cà phê", "M01", "Cà phê sữa", "22.000 VNĐ", R.drawable.cafe_01));
        monList.add(new Mon("Cà phê", "M02", "Cà phê", "22.000 VNĐ", R.drawable.cafe_02));
        monList.add(new Mon("Nước ép", "M03", "Nước ép cam", "25.000 VNĐ", R.drawable.nuoc_ep_cam));
        monList.add(new Mon("Nước ép", "M04", "Nước ép dưa hấu", "25.000 VNĐ", R.drawable.nuoc_ep_dua_hau));

        filteredList = new ArrayList<>(monList);
        monAdapter = new MonAdapter(filteredList);
        recyclerViewMon.setAdapter(monAdapter);

        Spinner spinner = view.findViewById(R.id.spinner_loaiMon);
        String[] categories = {"Tất cả", "Cà phê", "Nước ép", "Nước ngọt"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                filterByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không chọn gì
            }
        });

        FloatingActionButton btnAddMon = view.findViewById(R.id.btn_addMon);
        btnAddMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ThemThucDonQL fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new ThemThucDonQLFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void filterByCategory(String category) {
        filteredList.clear();
        if (category.equals("Tất cả")) {
            filteredList.addAll(monList);
        } else {
            for (Mon mon : monList) {
                if (mon.getLoaiMon().equals(category)) {
                    filteredList.add(mon);
                }
            }
        }
        monAdapter.notifyDataSetChanged();
    }
}
