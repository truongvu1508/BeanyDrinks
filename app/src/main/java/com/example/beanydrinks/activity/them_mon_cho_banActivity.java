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

        // Initialize RecyclerView
        rcvChonMon = findViewById(R.id.rcv_chonmon);
        rcvChonMon.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list and adapter
        monList = new ArrayList<>();
        chonMonAdapter = new ChonMonAdapter(this, this::onMonSelected);
        rcvChonMon.setAdapter(chonMonAdapter);

        // Load data from API
        loadMonData();

        // Back button listener
        ImageButton btnBack = findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> navigateToOrderBanActivity());

        // Cancel button listener
        Button btnHuy = findViewById(R.id.button_huy);
        btnHuy.setOnClickListener(v -> navigateToOrderBanActivity());

        // Confirm button listener
        Button btnDongY = findViewById(R.id.button_dongy);
        btnDongY.setOnClickListener(v -> navigateToOrderBanActivity());
    }

    // Load dish data from API
    private void loadMonData() {
        String url = Server.DuongDanMon; // Ensure Server.DuongDanMon contains the correct URL

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                monList.clear();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        // Extract data from JSON object
                        String loaiMon = jsonObject.getString("loaiMon");
                        String maMon = jsonObject.getString("maMon");
                        String tenMon = jsonObject.getString("tenMon");
                        String giaTien = jsonObject.getString("giaTien");
                        String hinhAnh = jsonObject.getString("hinhAnh");

                        // Add to dish list
                        monList.add(new Mon(loaiMon, maMon, tenMon, giaTien, hinhAnh));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Update adapter
                chonMonAdapter.setMonList(monList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(them_mon_cho_banActivity.this, "Lỗi khi tải dữ liệu");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    // Callback for dish selection
    private void onMonSelected(Mon mon) {
        CheckConnection.ShowToast_Short(this, "Đã chọn món: " + mon.getTenMon());
    }

    // Navigate back to the order table activity
    private void navigateToOrderBanActivity() {
        Intent intent = new Intent(them_mon_cho_banActivity.this, orderban_nvActivity.class);
        startActivity(intent);
    }
}
