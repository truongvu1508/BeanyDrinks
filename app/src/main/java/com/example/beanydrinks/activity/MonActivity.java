package com.example.beanydrinks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.R;
import com.example.beanydrinks.adapter.MonAdapter;
import com.example.beanydrinks.model.Mon;

import java.util.ArrayList;
import java.util.List;

public class MonActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MonAdapter monAdapter;
    private List<Mon> monList;
    private Button btnAddMon;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_thuc_don_ql); // Sử dụng layout cho MonActivity

        recyclerView = findViewById(R.id.rcv_NhanVien);
        btnAddMon = findViewById(R.id.btn_addMon);
        edtSearch = findViewById(R.id.editTextText6);

        monList = new ArrayList<>();

        monList.add(new Mon("Cà phê", "M01", "Cà phê sữa", "22.000 VNĐ", R.drawable.cafe_01));
        monList.add(new Mon("Cà phê", "M02", "Cà phê", "22.000 VNĐ", R.drawable.cafe_02));
        monList.add(new Mon("Cà phê", "M03", "Cà phê", "22.000 VNĐ", R.drawable.cafe_02));
        monList.add(new Mon("Cà phê", "M04", "Cà phê", "22.000 VNĐ", R.drawable.cafe_02));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        monAdapter = new MonAdapter(monList);
        recyclerView.setAdapter(monAdapter);

        btnAddMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonActivity.this, ThemThucDonQLActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Tìm kiếm
        edtSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                monAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Mon newMon = (Mon) data.getSerializableExtra("NEW_MON");
            if (newMon != null) {
                monList.add(newMon);
                monAdapter.notifyItemInserted(monList.size() - 1);
            }
        }
    }
}