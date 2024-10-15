package com.example.beanydrinks;

import android.content.Intent; // Thêm import này
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class thong_ke_mat_hang_qlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke_mat_hang_ql);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Thiết lập biểu đồ
        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(508, "2016"));
        visitors.add(new PieEntry(600, "2017"));
        visitors.add(new PieEntry(750, "2018"));
        visitors.add(new PieEntry(600, "2019"));
        visitors.add(new PieEntry(670, "2020"));

        PieDataSet pieDataSet = new PieDataSet(visitors, "Thống kê");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Thống kê");
        pieChart.animate();

        // Thiết lập ImageButton và sự kiện nhấn
        ImageButton imageButton2 = findViewById(R.id.imageButton2); // Khai báo ImageButton
        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(thong_ke_mat_hang_qlActivity.this, thong_ke_doanh_thu_qlActivity.class);
            startActivity(intent);
            finish(); // Kết thúc activity hiện tại để không trở lại
        });
    }
}
