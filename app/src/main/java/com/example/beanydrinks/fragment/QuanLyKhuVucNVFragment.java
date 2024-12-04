package com.example.beanydrinks.fragment;

import android.content.Intent;
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
import com.example.beanydrinks.activity.orderban_nvActivity;
import com.example.beanydrinks.adapter.BanAdapter;
import com.example.beanydrinks.model.Ban;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuanLyKhuVucNVFragment extends Fragment implements BanAdapter.OnTableClickListener {
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
        // Pass 'this' as the listener for table actions
        banAdapter = new BanAdapter(getContext(), banList, this);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rcv_Ban);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        btnVIP = view.findViewById(R.id.button_khuvip);
        btnA = view.findViewById(R.id.button_khuA);
        btnB = view.findViewById(R.id.button_khuB);
        btnC = view.findViewById(R.id.button_khuC);

        btnVIP.setOnClickListener(v -> {
            setSelectedButton(btnVIP);
            getBan("1");
        });
        btnA.setOnClickListener(v -> {
            setSelectedButton(btnA);
            getBan("2");
        });
        btnB.setOnClickListener(v -> {
            setSelectedButton(btnB);
            getBan("3");
        });
        btnC.setOnClickListener(v -> {
            setSelectedButton(btnC);
            getBan("4");
        });

        if (CheckConnection.haveNetworkConnection(getContext().getApplicationContext())) {
            getBan("1");
        } else {
            CheckConnection.ShowToast_Short(getContext().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            getActivity().finish();
        }

        // Set up adapter
        recyclerView.setAdapter(banAdapter);

        FloatingActionButton btnAddBan = view.findViewById(R.id.btn_addBan);
        btnAddBan.setOnClickListener(v -> banAdapter.showAddTableDialog());

        return view;
    }

    private void setSelectedButton(Button selectedButton) {
        // Danh sách các nút trong giao diện
        Button[] buttons = {btnVIP, btnA, btnB, btnC};

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

    // Handling table click event
    @Override
    public void onTableClick(int idBan) {
        // Handle the table click here, for example, navigate to orderban_nvActivity
        Log.d("QuanLyKhuVucNV", "Table clicked: " + idBan);

        // Create an intent to navigate to orderban_nvActivity
        Intent intent = new Intent(getContext(), orderban_nvActivity.class);

        // Pass the idBan to the next activity
        intent.putExtra("idBan", idBan);

        // If you need to send additional data, add them to the intent (e.g., customer info, previous orders)
        // intent.putExtra("tenBan", selectedTableName);
        // intent.putExtra("tenKhachHang", customerName);
        // intent.putExtra("soDienThoai", customerPhoneNumber);

        // Start the activity
        startActivity(intent);
    }

    @Override
    public void onEditTable(Ban ban) {
        // Handle edit table logic here
        Log.d("QuanLyKhuVucNV", "Edit table: " + ban.getTenBan());
        // Show dialog for editing table, and pass the updated data back to the adapter
    }

    @Override
    public void onDeleteTable(int idBan) {
        // Handle delete table logic here
        Log.d("QuanLyKhuVucNV", "Delete table with ID: " + idBan);
        // You can show a confirmation dialog before deleting the table
    }
}
