package com.example.beanydrinks.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.NhanVienAdapter;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaffFragment extends Fragment {

    private RecyclerView rcvNhanVien;
    private NhanVienAdapter nhanVienAdapter;

    private FloatingActionButton btnAddNV;
    ArrayList<NhanVien> mangnv;

    private ArrayList<NhanVien> mangnv;


    public StaffFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        // Ánh xạ RecyclerView và danh sách nhân viên
        rcvNhanVien = view.findViewById(R.id.rcv_NhanVien);
        mangnv = new ArrayList<>();
        nhanVienAdapter = new NhanVienAdapter(mangnv, getContext().getApplicationContext());

        // Thiết lập LayoutManager và Adapter
        rcvNhanVien.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvNhanVien.setAdapter(nhanVienAdapter);

        // Kiểm tra kết nối và tải danh sách nhân viên
        if (CheckConnection.haveNetworkConnection(getContext().getApplicationContext())) {
            getNV();
        } else {
            CheckConnection.ShowToast_Short(getContext().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            getActivity().finish();  // Nếu không có kết nối, thoát ứng dụng
        }

        FloatingActionButton btnAddNV = view.findViewById(R.id.btn_addnv);
        if (btnAddNV != null) {
            btnAddNV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, new AddNhanVienFragment())  // Chuyển sang AddNhanVienFragment
                            .addToBackStack(null)  // Để quay lại khi nhấn back
                            .commit();
                }
            });
        } else {
            Log.e("StaffFragment", "Button not found!");
        }
        return view;
    }



    private void getNV() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetNhanVien, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    // Tạo danh sách mới chứa các nhân viên không phải "admin"
                    ArrayList<NhanVien> filteredList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String role = jsonObject.getString("role");
                            // Chỉ thêm vào danh sách nếu role không phải "admin"
                            if (!"admin".equals(role)) {
                                filteredList.add(new NhanVien(
                                        jsonObject.getInt("idNhanVien"),
                                        jsonObject.getString("tenNhanVien"),
                                        jsonObject.getString("gioiTinh"),
                                        jsonObject.getString("ngaySinh"),
                                        jsonObject.getString("chucVu"),
                                        jsonObject.getString("soDienThoai"),
                                        jsonObject.getString("diaChi"),
                                        jsonObject.getString("trangThai"),
                                        jsonObject.getString("matKhau"),
                                        role
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Cập nhật lại adapter với danh sách đã lọc
                    mangnv.clear();
                    mangnv.addAll(filteredList);
                    nhanVienAdapter.notifyDataSetChanged();  // Cập nhật RecyclerView
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.e("Volley Error", "Status Code: " + error.networkResponse.statusCode);
                    Log.e("Volley Error", "Response Data: " + new String(error.networkResponse.data));
                }
                CheckConnection.ShowToast_Short(requireContext(), "Lỗi khi tải dữ liệu");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
