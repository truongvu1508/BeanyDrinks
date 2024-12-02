package com.example.beanydrinks.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.KhachHangAdapter;
import com.example.beanydrinks.model.Ban;
import com.example.beanydrinks.model.KhachHang;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QLKHFragment extends Fragment {

    private RecyclerView rcvKhachHang;
    private KhachHangAdapter khachHangAdapter;
    public QLKHFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QLKHFragment newInstance(String param1, String param2) {
        QLKHFragment fragment = new QLKHFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__q_l_k_h, container, false);
        rcvKhachHang = view.findViewById(R.id.rcv_KhachHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvKhachHang.setLayoutManager(linearLayoutManager);
        khachHangAdapter = new KhachHangAdapter(getListKhachHang());
        rcvKhachHang.setAdapter(khachHangAdapter);
        // Inflate the layout for this fragment
        return view;
    }
    private List<KhachHang> getListKhachHang() {
        List<KhachHang> list = new ArrayList<>();

        // Tạo một RequestQueue từ Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        // Tạo JsonArrayRequest để lấy dữ liệu từ API
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Server.DuongDanGetKhachHang, // URL API
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Xử lý dữ liệu trả về từ API
                        if (response != null) {
                            ArrayList<KhachHang> khachhangs = new ArrayList<>();
                            khachhangs.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // Parse từng phần tử JSON
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int idKhachHang = jsonObject.getInt("idKhachHang");
                                    String soDienThoai = jsonObject.getString("soDienThoai");
                                    String ten = jsonObject.getString("ten");
                                    double diem = jsonObject.getDouble("diem");

                                    // Thêm vào danh sách
                                    khachhangs.add(new KhachHang(idKhachHang, soDienThoai, ten, diem));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            list.clear();
                            list.addAll(khachhangs);
                            // Cập nhật giao diện (nếu bạn sử dụng RecyclerView hoặc Adapter)
                            khachHangAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi khi gọi API
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Thêm request vào RequestQueue
        requestQueue.add(jsonArrayRequest);

        // Trả về danh sách
        return list;
    }
}
