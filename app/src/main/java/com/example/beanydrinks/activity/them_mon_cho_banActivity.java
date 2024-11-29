package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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
    private List<Mon> monList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_mon_cho_ban);

        // Initialize the RecyclerView
        rcvChonMon = findViewById(R.id.rcv_chonmon);
        rcvChonMon.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list and adapter
        monList = new ArrayList<>();
        chonMonAdapter = new ChonMonAdapter(this); // Chỉ truyền context vào constructor
        rcvChonMon.setAdapter(chonMonAdapter);

        // Load the data from API
        loadMonData();

        // Back button listener
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });

        // Cancel button listener
        Button btnHuy = findViewById(R.id.button_huy);
        btnHuy.setOnClickListener(v -> {
            Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });

        // Confirm button listener
        Button btnDongY = findViewById(R.id.button_dongy);
        btnDongY.setOnClickListener(v -> {
            Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
            startActivity(intent);
        });
    }

    // Method to load data from API
    private void loadMonData() {
        // URL API để lấy danh sách món
        String url = Server.DuongDanMon; // Đảm bảo URL đúng trong `Server.DuongDanMon`

        // Khởi tạo RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Tạo yêu cầu JsonArrayRequest để lấy dữ liệu từ API
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách
                monList.clear();

                // Duyệt qua từng đối tượng trong JSON Array
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        // Lấy thông tin món từ JSON
                        String loaiMon = jsonObject.getString("loaiMon");
                        String maMon = jsonObject.getString("maMon");
                        String tenMon = jsonObject.getString("tenMon");
                        String giaTien = jsonObject.getString("giaTien");
                        String hinhAnh = jsonObject.getString("hinhAnh");

                        // Thêm món vào danh sách
                        monList.add(new Mon(loaiMon, maMon, tenMon, giaTien, hinhAnh));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Cập nhật lại danh sách món trong adapter
                chonMonAdapter.setMonList(monList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Nếu có lỗi khi lấy dữ liệu từ API
                error.printStackTrace();
                CheckConnection.ShowToast_Short(them_mon_cho_banActivity.this, "Lỗi khi tải dữ liệu");
            }
        });

        // Thêm yêu cầu vào RequestQueue để thực thi
        requestQueue.add(jsonArrayRequest);
    }
}
