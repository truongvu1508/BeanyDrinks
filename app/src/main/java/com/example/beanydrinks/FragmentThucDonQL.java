package com.example.beanydrinks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentThucDonQL extends Fragment {

    private RecyclerView recyclerViewMon;
    private MonAdapter monAdapter;
    private List<Mon> monList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuc_don_ql, container, false);

        // Initialize RecyclerView
        recyclerViewMon = view.findViewById(R.id.rcv_NhanVien);
        recyclerViewMon.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize list of dishes
        monList = new ArrayList<>();
        monList.add(new Mon("Cà phê", "M01", "Cà phê sữa", 22000, R.drawable.__ca_phe_sua_1586320543_3124));
        monList.add(new Mon("Nước ép", "M02", "Nước ép dưa hấu", 22000, R.drawable.nuoc));

        // Initialize adapter and set it to the RecyclerView
        monAdapter = new MonAdapter(monList);
        recyclerViewMon.setAdapter(monAdapter);

        // Setup FloatingActionButton
        FloatingActionButton btnAddMon = view.findViewById(R.id.btn_addMon);
        btnAddMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ThemThucDonQL fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new ThemThucDonQL())
                        .addToBackStack(null) // Thêm vào back stack để có thể quay lại
                        .commit();
            }
        });

        return view;
    }
}
