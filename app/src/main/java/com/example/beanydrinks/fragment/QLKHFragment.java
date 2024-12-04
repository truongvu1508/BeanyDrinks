package com.example.beanydrinks.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QLKHFragment extends Fragment {
    private ArrayList<KhachHang> mangkh;
    private ArrayList<KhachHang> mangkhFull;
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
        mangkh = new ArrayList<>();
        mangkhFull = new ArrayList<>();
        rcvKhachHang = view.findViewById(R.id.rcv_KhachHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvKhachHang.setLayoutManager(linearLayoutManager);
        khachHangAdapter = new KhachHangAdapter(mangkh, getActivity());
        rcvKhachHang.setAdapter(khachHangAdapter);

        if (CheckConnection.haveNetworkConnection(requireContext())) {
            getKH();  // Load the staff data
        } else {
            CheckConnection.ShowToast_Short(requireContext(), "Không có kết nối mạng. Vui lòng thử lại.");
        }

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterKhachHangList(newText);
                return true;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
    private void getKH() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetKhachHang,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            ArrayList<KhachHang> filteredList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // Parse từng phần tử JSON
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int idKhachHang = jsonObject.getInt("idKhachHang");
                                    String soDienThoai = jsonObject.getString("soDienThoai");
                                    String ten = jsonObject.getString("ten");
                                    double diem = jsonObject.getDouble("diem");

                                    // Thêm vào danh sách
                                    filteredList.add(new KhachHang(idKhachHang, soDienThoai, ten, diem));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Update the list and notify adapter
                            mangkh.clear();
                            mangkh.addAll(filteredList);
                            mangkhFull.addAll(filteredList); // Save the full list for filtering
                            khachHangAdapter.notifyDataSetChanged();
                            Log.d("StaffFragment", "Data loaded successfully. List size: " + mangkh.size());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        CheckConnection.ShowToast_Short(requireContext(), "Lỗi khi tải dữ liệu");
                    }
                });

        // Add the request to the queue
        requestQueue.add(jsonArrayRequest);
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
    private void filterKhachHangList(String query) {
        ArrayList<KhachHang> filteredList = new ArrayList<>();
        for (KhachHang khachhang : mangkhFull ) {
            if (khachhang.getHoTen().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(khachhang);
            }
        }

        // If no results found, show a message or empty state
        if (filteredList.isEmpty()) {
            Log.d("Search", "No results found.");
        }

        mangkh.clear();
        mangkh.addAll(filteredList);
        khachHangAdapter.notifyDataSetChanged();
        Log.d("Search", "Filtered list size: " + filteredList.size());
    }
}
