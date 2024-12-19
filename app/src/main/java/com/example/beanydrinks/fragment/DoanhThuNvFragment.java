package com.example.beanydrinks.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.AdapterDoanhThu;
import com.example.beanydrinks.model.DoanhThu;
import com.example.beanydrinks.model.UserSession;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DoanhThuNvFragment extends Fragment {

    public DoanhThuNvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu_nv, container, false);
        ListView listView;
        ArrayList<DoanhThu> arrayList;
        AdapterDoanhThu adapterDoanhThu;

        listView = view.findViewById(R.id.listview_doanhthu);
        arrayList = new ArrayList<>();
        adapterDoanhThu = new AdapterDoanhThu(getContext(), R.layout.layout_doanhthu, arrayList);
        listView.setAdapter(adapterDoanhThu);

        int idNhanVien = UserSession.getInstance(getContext()).getCurrentUser().getIdNhanVien();
        Log.d("DoanhThuNvFragment", "ID Nhan Vien: " + idNhanVien);
        getHoaDonTheoNhanVien(arrayList, adapterDoanhThu, idNhanVien);

        return view;
    }

    private void getHoaDonTheoNhanVien(ArrayList<DoanhThu> arrayList, AdapterDoanhThu adapter, int idNhanVien) {
        String url = Server.DuongDanGetHoaDonTheoNhanVien + "?idNhanVien=" + idNhanVien;
        Log.d("DoanhThuNvFragment", "Request URL: " + url);  // Ghi log URL gọi API

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("DoanhThuNvFragment", "Response: " + response.toString());  // Log toàn bộ phản hồi JSON
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            JSONArray dsHoaDon = response.getJSONArray("dsHoaDon");
                            arrayList.clear();

                            for (int i = 0; i < dsHoaDon.length(); i++) {
                                JSONObject jsonHoaDon = dsHoaDon.getJSONObject(i);
                                Log.d("HoaDon", "HoaDon " + i + ": " + jsonHoaDon.toString());  // Log từng hóa đơn

                                String maDon = "Mã Đơn: " + jsonHoaDon.getInt("idHoaDon");
                                String tenNhanVien = "Nhân viên ID: " + jsonHoaDon.getInt("idNhanVien");
                                String tongTien = jsonHoaDon.getString("tongTien") + " VNĐ";
                                String ngayLap = jsonHoaDon.getString("thoiGian");

                                arrayList.add(new DoanhThu(maDon, tenNhanVien, "", tongTien, "", ngayLap));
                            }

                            adapter.notifyDataSetChanged();  // Cập nhật giao diện
                        } else {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            Log.d("DoanhThuNvFragment", "API Error Message: " + message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("DoanhThuNvFragment", "JSON Parsing Error: " + e.getMessage());
                        Toast.makeText(getContext(), "Lỗi khi phân tích dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Log.e("DoanhThuNvFragment", "Volley Error: " + error.getMessage());
                    Toast.makeText(getContext(), "Lỗi khi tải hóa đơn!", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

}