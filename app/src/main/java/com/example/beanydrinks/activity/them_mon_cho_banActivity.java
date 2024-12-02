package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.ChonMonAdapter;
import com.example.beanydrinks.model.Mon;
import com.example.beanydrinks.ultil.CheckConnection;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class them_mon_cho_banActivity extends AppCompatActivity {

    private RecyclerView rcvChonMon;
    private ChonMonAdapter chonMonAdapter;
    private List<Mon> monList, filteredList;
    private List<String> loaiMonList, idLoaiMonList;
    private Spinner spinnerLoaiMon;
    private LinearLayout linearLayoutLoaiMon;
    private EditText editTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_mon_cho_ban);

        // Initialize RecyclerView and Adapter
        rcvChonMon = findViewById(R.id.rcv_chonmon);
        rcvChonMon.setLayoutManager(new LinearLayoutManager(this));

        monList = new ArrayList<>();
        filteredList = new ArrayList<>();
        chonMonAdapter = new ChonMonAdapter(this);
        rcvChonMon.setAdapter(chonMonAdapter);

        getLoaiMon();
        getMon();

        // Button Back
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> startActivity(new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class)));

        // Button Cancel
        Button btnHuy = findViewById(R.id.button_huy);
        btnHuy.setOnClickListener(v -> startActivity(new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class)));

        // Button Confirm
        Button btnDongY = findViewById(R.id.button_dongy);
        btnDongY.setOnClickListener(v -> startActivity(new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class)));

        // Initialize EditText for searching
        editTimKiem = findViewById(R.id.edit_timkiem);  // Ensure this ID matches the one in your XML

        // TextWatcher to handle search input
        editTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMonBySearchAndLoai(s.toString(), getSelectedCategory());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        // Initialize LinearLayout for dynamic category buttons
        linearLayoutLoaiMon = findViewById(R.id.linearLayoutLoaiMon);
    }

    private void getLoaiMon() {
        loaiMonList = new ArrayList<>();
        idLoaiMonList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanLoaiMon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loaiMonList.add("Tất cả");
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

                // Create dynamic buttons based on the received data
                createLoaiMonButtons();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(them_mon_cho_banActivity.this, "Lỗi khi tải danh sách loại món");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void createLoaiMonButtons() {
        // Biến lưu nút hiện tại đang được chọn
        final Button[] selectedButton = {null};
        final Button[] tatCaButton = {null}; // Lưu nút "Tất cả"

        for (int i = 0; i < loaiMonList.size(); i++) {
            final String idLoai = idLoaiMonList.get(i);
            Button button = new Button(this);
            button.setText(loaiMonList.get(i));
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            // Thiết lập giao diện nút
            if (i == 0) {
                // Nút "Tất cả"
                tatCaButton[0] = button;
                button.setBackgroundResource(R.drawable.button_nencam); // "Tất cả" được chọn mặc định
                button.setTextColor(getResources().getColor(R.color.white));
            } else {
                // Các nút khác
                button.setBackgroundResource(R.drawable.button_viencam);
                button.setTextColor(getResources().getColor(R.color.orange));
            }

            // Lắng nghe sự kiện click của nút
            button.setOnClickListener(v -> {
                // Đặt lại giao diện mặc định cho nút hiện đang được chọn (nếu có)
                if (selectedButton[0] != null) {
                    selectedButton[0].setBackgroundResource(R.drawable.button_viencam);
                    selectedButton[0].setTextColor(getResources().getColor(R.color.orange));
                }

                // Nếu nhấn vào nút khác nút "Tất cả", đặt lại trạng thái mặc định cho nút "Tất cả"
                if (tatCaButton[0] != null && tatCaButton[0] != button) {
                    tatCaButton[0].setBackgroundResource(R.drawable.button_viencam);
                    tatCaButton[0].setTextColor(getResources().getColor(R.color.orange));
                }

                // Thay đổi giao diện của nút được chọn
                button.setBackgroundResource(R.drawable.button_nencam);
                button.setTextColor(getResources().getColor(R.color.white));

                // Cập nhật nút đang được chọn
                selectedButton[0] = button;

                // Reset tìm kiếm khi thay đổi loại món
                editTimKiem.setText("");

                // Lọc danh sách món dựa vào loại được chọn
                filterMonByLoai(idLoai);
            });

            // Thêm nút vào layout
            linearLayoutLoaiMon.addView(button);
        }
    }


    private void getMon() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanMon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
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

                    filterMonByLoai("0"); // Show all items after loading data
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(them_mon_cho_banActivity.this, "Lỗi khi tải danh sách món");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void filterMonByLoai(String idLoai) {
        filteredList.clear();

        for (Mon mon : monList) {
            if ("0".equals(idLoai) || mon.getLoaiMon().equals(idLoai)) {
                filteredList.add(mon);
            }
        }

        chonMonAdapter.setMonList(filteredList);
    }

    private void filterMonBySearchAndLoai(String searchKeyword, String idLoai) {
        filteredList.clear();

        for (Mon mon : monList) {
            boolean matchesSearch = mon.getTenMon().toLowerCase().contains(searchKeyword.toLowerCase());
            boolean matchesLoai = idLoai.equals("0") || mon.getLoaiMon().equals(idLoai);

            if (matchesSearch && matchesLoai) {
                filteredList.add(mon);
            }
        }

        chonMonAdapter.updateData(filteredList); // Update the adapter

        if (filteredList.isEmpty()) {
            CheckConnection.ShowToast_Short(them_mon_cho_banActivity.this, "Không tìm thấy món nào phù hợp!");
        }
    }

    // Get the selected category from the dynamic buttons (currently selected category)
    private String getSelectedCategory() {
        // You can return the id of the currently selected category
        // For simplicity, you can retrieve the id directly from the "selectedButton" logic.
        return "0"; // Default to "Tất cả" if not selected
    }
}
