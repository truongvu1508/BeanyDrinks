package com.example.beanydrinks.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
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
import com.example.beanydrinks.adapter.BanNVAdapter;
import com.example.beanydrinks.model.Ban;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuanLyKhuVucNVFragment extends Fragment implements BanNVAdapter.OnTableClickListener {
    private RecyclerView recyclerView;
    private BanNVAdapter banAdapter;
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
        banAdapter = new BanNVAdapter(getContext(), banList, this);

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

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBanList(newText);
                return true;
            }
        });

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
        Ban clickedBan = findBanById(idBan);

        if (clickedBan != null && "Đang phục vụ".equals(clickedBan.getTrangThai())) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Thông báo")
                    .setMessage("Bàn này đã dọn dẹp xong. Bạn có muốn đặt lại trạng thái bàn thành 'Bàn trống' không?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        try {
                            updateBanStatus(clickedBan, "Bàn trống");
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Lỗi khi cập nhật trạng thái bàn!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        } else if (clickedBan != null) {
            navigateToOrderActivity(idBan);
        } else {
            Toast.makeText(getContext(), "Không tìm thấy bàn.", Toast.LENGTH_SHORT).show();
        }
    }

    private Ban findBanById(int idBan) {
        for (Ban ban : banList) {
            if (ban.getIdBan() == idBan) {
                return ban;
            }
        }
        return null;
    }

    private void updateBanStatus(Ban clickedBan, String newStatus) {
        clickedBan.setTrangThai(newStatus);

        // Đảm bảo adapter được cập nhật
        if (banAdapter != null) {
            banAdapter.notifyDataSetChanged();
        }

        // Cập nhật trạng thái trên server
        banAdapter.updateBanStatusOnServer(clickedBan.getIdBan(), newStatus);
    }

    private void navigateToOrderActivity(int idBan) {
        Intent intent = new Intent(getContext(), orderban_nvActivity.class);
        intent.putExtra("idBan", idBan);
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

    private void filterBanList(String query) {
        // Create a filtered list to store matching Ban items
        ArrayList<Ban> filteredList = new ArrayList<>();

        // Loop through the original banList to check for matches
        for (Ban ban : banList) {
            // Check if the 'tenban' field contains the query (case-insensitive)
            if (ban.getTenBan().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ban);
            }
        }

        // If no results found, show a message or empty state
        if (filteredList.isEmpty()) {
            Log.d("Search", "No results found.");
        }

        // Update the original banList with the filtered results
        banList.clear();
        banList.addAll(filteredList);

        // Notify the adapter to update the RecyclerView
        banAdapter.notifyDataSetChanged();

        Log.d("Search", "Filtered list size: " + filteredList.size());
    }
}