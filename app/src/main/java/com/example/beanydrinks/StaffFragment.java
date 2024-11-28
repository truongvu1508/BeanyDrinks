package com.example.beanydrinks;

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
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                if (response!=null){
                    int idNhanVien = 0;
                    String tennv ="";
                    String gioitinh="";
                    String ngaySinh="";
                    String chucvunv = "";
                    String sodienthoai="";
                    String diachi="";
                    String trangthainv= "";
                    for(int i=0;i< response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idNhanVien = jsonObject.getInt("idNhanVien");
                            tennv = jsonObject.getString("tenNhanVien");
                            gioitinh =jsonObject.getString("gioiTinh");
                            ngaySinh =jsonObject.getString("ngaySinh");
                            chucvunv = jsonObject.getString("chucVu");
                            sodienthoai=jsonObject.getString("soDienThoai");
                            diachi =jsonObject.getString("diaChi");
                            trangthainv=jsonObject.getString("trangThai");
                            mangnv.add(new NhanVien(idNhanVien,tennv,gioitinh,ngaySinh,chucvunv,sodienthoai,diachi,trangthainv));
                            nhanVienAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                           e.printStackTrace();
                        }
                    }
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
