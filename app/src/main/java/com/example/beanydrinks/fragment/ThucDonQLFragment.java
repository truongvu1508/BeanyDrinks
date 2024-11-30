package com.example.beanydrinks.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.MonAdapter;
import com.example.beanydrinks.model.Mon;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThucDonQLFragment extends Fragment {

    private Spinner spinnerLoaiMon;
    private RecyclerView recyclerViewMon;
    private MonAdapter monAdapter;
    private List<Mon> monList, filteredList;
    private List<String> loaiMonList, idLoaiMonList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuc_don_ql, container, false);

        // Khởi tạo Spinner, RecyclerView và Button
        spinnerLoaiMon = view.findViewById(R.id.spinner_loaiMon);
        recyclerViewMon = view.findViewById(R.id.rcv_DSMon);
        FloatingActionButton btnAddMon = view.findViewById(R.id.btn_addMon);

        recyclerViewMon.setLayoutManager(new GridLayoutManager(getContext(), 2)); // GridLayout 2 cột

        // Danh sách món
        monList = new ArrayList<>();
        filteredList = new ArrayList<>();
        monAdapter = new MonAdapter(filteredList);
        recyclerViewMon.setAdapter(monAdapter);

        // Thêm món
        btnAddMon.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new ThemThucDonQLFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Tải dữ liệu
        getLoaiMon(); // Lấy danh sách loại
        getMon();     // Lấy danh sách món

        return view;
    }

    private void getLoaiMon() {
        loaiMonList = new ArrayList<>();
        idLoaiMonList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanLoaiMon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loaiMonList.add("Tất cả"); // Tùy chọn đầu tiên là "Tất cả"
                idLoaiMonList.add("0");

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String idLoai = jsonObject.getString("idLoai");
                        String tenLoai = jsonObject.getString("ten");

                        loaiMonList.add(tenLoai);
                        idLoaiMonList.add(idLoai);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Gán dữ liệu vào Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, loaiMonList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLoaiMon.setAdapter(adapter);

                // Lọc danh sách món khi chọn loại
                spinnerLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedIdLoai = idLoaiMonList.get(position);
                        filterMonByLoai(selectedIdLoai);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Không làm gì cả
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(requireContext(), "Lỗi khi tải danh sách loại món");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getMon() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanMon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d("ThucDonQLFragment", "API response: " + response.toString());
                    monList.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String loaiMon = jsonObject.getString("idLoai");
                            String maMon = jsonObject.getString("idSanPham");
                            String tenMon = jsonObject.getString("ten");
                            String giaTien = jsonObject.getString("gia");
                            String hinhAnh = jsonObject.getString("anh");

                            monList.add(new Mon(loaiMon, maMon, tenMon, giaTien, hinhAnh));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    filterMonByLoai("0"); // Hiển thị "Tất cả" sau khi tải dữ liệu
                } else {
                    Log.d("ThucDonQLFragment", "API response is null");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(requireContext(), "Lỗi khi tải danh sách món");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void filterMonByLoai(String idLoai) {
        filteredList.clear();

        if (idLoai.equals("0")) {
            filteredList.addAll(monList); // Tất cả món
        } else {
            for (Mon mon : monList) {
                if (mon.getLoaiMon().equals(idLoai)) {
                    filteredList.add(mon);
                }
            }
        }

        monAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }
}
