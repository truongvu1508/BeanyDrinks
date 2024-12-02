package com.example.beanydrinks.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.BanAdapter;
import com.example.beanydrinks.adapter.NhanVienAdapter;
import com.example.beanydrinks.model.Ban;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuanLyKhuVucNVFragment extends Fragment {
    private RecyclerView recyclerView;
    private BanAdapter banAdapter;
    private List<Ban> banList;
    private Button btnVIP;
    private Button btnA;
    private Button btnB;
    private Button btnC;


    public QuanLyKhuVucNVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_khu_vuc_nv, container, false);
    
        banList = new ArrayList<>();
        banAdapter = new BanAdapter( getContext().getApplicationContext(), banList);
        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rcv_Ban);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        btnVIP= view.findViewById(R.id.button_khuvip);
        btnA= view.findViewById(R.id.button_khuA);
        btnB= view.findViewById(R.id.button_khuB);
        btnC= view.findViewById(R.id.button_khuC);



        btnVIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(btnVIP);
                getBan("1");
            }
        });
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(btnA);
                getBan("2");


            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(btnB);
                getBan("3");
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(btnC);
                getBan("4");
            }
        });

        if(CheckConnection.haveNetworkConnection(getContext().getApplicationContext())){
            getBan("1");
        }
        else{
            CheckConnection.ShowToast_Short(getContext().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            getActivity().finish();
        }




        // Set up adapter
        banAdapter = new BanAdapter(getContext(), banList);
        recyclerView.setAdapter(banAdapter);

        FloatingActionButton btnAddBan = view.findViewById(R.id.btn_addBan);
        btnAddBan.setOnClickListener(v ->{
            banAdapter.showAddTableDialog();
        });


        return view;
    }

    private void setSelectedButton(Button selectedButton) {
        // Danh sách các nút trong giao diện
        Button[] buttons = {btnVIP, btnA, btnB, btnC}; // Thêm các nút khác vào đây

        // Màu mặc định và màu được chọn
        int defaultBackground = R.drawable.button_viencam; // Nền mặc định
        int defaultTextColor = ContextCompat.getColor(getContext(), R.color.orange); // Chữ mặc định
        int selectedBackground = R.drawable.button_nencam; // Nền được chọn
        int selectedTextColor = ContextCompat.getColor(getContext(), R.color.white); // Chữ được chọn

        // Cập nhật trạng thái từng nút
        for (Button button : buttons) {
            if (button == selectedButton) {
                // Nút được chọn
                button.setBackground(ContextCompat.getDrawable(getContext(), selectedBackground));
                button.setTextColor(selectedTextColor);
            } else {
                // Nút không được chọn
                button.setBackground(ContextCompat.getDrawable(getContext(), defaultBackground));
                button.setTextColor(defaultTextColor);
            }
        }
    }



    public void getBan(String idKhuVuc) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanBan, response -> {
            try {
                ArrayList<Ban> bans = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    int idBan = jsonObject.getInt("idBan");
                    String tenban = jsonObject.getString("ten");
                    String trangthai = jsonObject.getString("trangthai");
                    String khuvuc = jsonObject.getString("idKhuVuc");

                    if (khuvuc.equals(idKhuVuc)) {
                        bans.add(new Ban(idBan, tenban, trangthai, khuvuc));
                    }
                }
                banList.clear();
                banList.addAll(bans);
                banAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("getBan", "JSON Parsing Error: " + e.getMessage());
            }
        }, error -> {
            Log.e("getBan", "Volley Error: " + error.getMessage());
            Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonArrayRequest);
    }



//    public void updateTableStatus(int position, String newStatus) {
//        Ban ban = banList.get(position);
//        ban.setTrangThai(newStatus); // Cập nhật trạng thái
//        banAdapter.notifyItemChanged(position); // Thông báo cho adapter để cập nhật giao diện
//    }
}
