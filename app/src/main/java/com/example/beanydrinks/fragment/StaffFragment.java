package com.example.beanydrinks.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    ArrayList<NhanVien>mangnv;

    public StaffFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        rcvNhanVien = view.findViewById(R.id.rcv_NhanVien);
        btnAddNV = view.findViewById(R.id.btn_addBan);
        mangnv = new ArrayList<>();
        nhanVienAdapter = new NhanVienAdapter(mangnv, getContext().getApplicationContext());
        rcvNhanVien.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), 1));
        rcvNhanVien.setAdapter(nhanVienAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvNhanVien.setLayoutManager(linearLayoutManager);

        if(CheckConnection.haveNetworkConnection(getContext().getApplicationContext())){
            getNV();
        }
        else{
            CheckConnection.ShowToast_Short(getContext().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            getActivity().finish();
        }

        btnAddNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNhanVienFragment addNhanVienFragment = new AddNhanVienFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, addNhanVienFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void getNV() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanNhanVien, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    // Tạo danh sách mới chứa các nhân viên không phải "admin"
                    ArrayList<NhanVien> filteredList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int idNhanVien = jsonObject.getInt("idNhanVien");
                            String tennv = jsonObject.getString("tenNhanVien");
                            String gioitinh = jsonObject.getString("gioiTinh");
                            String ngaySinh = jsonObject.getString("ngaySinh");
                            String chucvunv = jsonObject.getString("chucVu");
                            String sodienthoai = jsonObject.getString("soDienThoai");
                            String diachi = jsonObject.getString("diaChi");
                            String trangthainv = jsonObject.getString("trangThai");
                            String matkhau = jsonObject.getString("matKhau");
                            String role = jsonObject.getString("role");

                            // Chỉ thêm vào danh sách nếu role không phải "admin"
                            if (!"admin".equals(role)) {
                                filteredList.add(new NhanVien(idNhanVien, tennv, gioitinh, ngaySinh, chucvunv, sodienthoai, diachi, trangthainv, matkhau, role));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Cập nhật lại adapter với danh sách đã lọc
                    mangnv.clear();  // Xóa dữ liệu cũ
                    mangnv.addAll(filteredList);  // Thêm dữ liệu đã lọc
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
