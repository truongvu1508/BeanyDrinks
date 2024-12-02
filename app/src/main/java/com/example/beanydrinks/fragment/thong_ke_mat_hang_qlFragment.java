package com.example.beanydrinks.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.beanydrinks.R;
import com.example.beanydrinks.ultil.Server;
import com.example.beanydrinks.ultil.MySingleton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class thong_ke_mat_hang_qlFragment extends Fragment {

    private ProgressDialog pd;
    private ArrayList<BarEntry> yValues;
    private ArrayList<String> xAxisLabels;
    private BarChart chart;
    private RadioGroup radioGroup;

    public thong_ke_mat_hang_qlFragment() {
        // Required empty public constructor
    }

    public static thong_ke_mat_hang_qlFragment newInstance(String param1, String param2) {
        thong_ke_mat_hang_qlFragment fragment = new thong_ke_mat_hang_qlFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_mat_hang_ql, container, false);

        // Initialize progress dialog
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");

        // Initialize chart and radio group
        chart = view.findViewById(R.id.chart);
        radioGroup = view.findViewById(R.id.radioGroup);

        // Find the RadioButton and set it checked by default
        RadioButton radioButtonMonth = view.findViewById(R.id.radioButtonMonth);
        if (radioButtonMonth != null) {
            radioButtonMonth.setChecked(true); // Set default checked
        }

        // Initialize data structures for chart
        yValues = new ArrayList<>();
        xAxisLabels = new ArrayList<>();

        // Set listener to load data when radio button is selected
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String type = getTypeFromRadioButton(checkedId);
            loadDataFromServer(Server.DuongDanThongKeThangHoaDon, type);
        });

        // Default load data for "Theo tháng"
        loadDataFromServer(Server.DuongDanThongKeThangHoaDon, "month");

        return view;
    }

    private String getTypeFromRadioButton(int checkedId) {
        if (checkedId == R.id.radioButtonQuarter) {
            return "quarter";
        } else if (checkedId == R.id.radioButtonYear) {
            return "year";
        } else {
            return "month"; // Default to "month"
        }
    }

    private void loadDataFromServer(String url, String type) {
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                response -> {
                    Log.d("Response", response);  // Log the full server response for debugging
                    parseResponse(response);
                },
                error -> {
                    showError("Error fetching data");
                    pd.dismiss();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", type);  // Pass the selected type (month, quarter, year)
                return params;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void parseResponse(String response) {
        Log.d("Server Response", response);  // In ra phản hồi của server

        try {
            JSONArray jsonArray = new JSONArray(response);

            // Xóa dữ liệu cũ
            xAxisLabels.clear();
            yValues.clear();

            // Duyệt qua từng đối tượng trong mảng JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Kiểm tra sự tồn tại của các trường và lấy giá trị
                String label = jsonObject.has("monthYear") ? jsonObject.getString("monthYear") : "Unknown";
                double totalAmount = jsonObject.has("totalAmount") ? jsonObject.getDouble("totalAmount") : 0.0;

                // Chỉ thêm vào nếu dữ liệu hợp lệ
                if (!label.equals("Unknown") && totalAmount != 0.0) {
                    xAxisLabels.add(label);
                    yValues.add(new BarEntry(i, (float) totalAmount));
                } else {
                    Log.w("Data Warning", "Missing or invalid data for entry: " + i);
                }
            }

            setupChart();
            pd.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
            showError("Error parsing data");
            pd.dismiss();
        }
    }

    private void setupChart() {
        BarDataSet barDataSet = new BarDataSet(yValues, "Tổng doanh thu");
        barDataSet.setColor(Color.rgb(0, 82, 159));
        barDataSet.setValueTextColor(Color.BLACK);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData barData = new BarData(dataSets);
        chart.setData(barData);

        // Configure X-Axis with custom labels
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int position = Math.round(value);
                return (position >= 0 && position < xAxisLabels.size()) ? xAxisLabels.get(position) : "";
            }
        });
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // Set chart description
        Description description = new Description();
        description.setText("Thống kê doanh số");
        description.setTextSize(16f);
        description.setTextColor(Color.BLACK);
        description.setPosition(0.5f, 0.98f);
        chart.setDescription(description);

        // Animate chart and redraw
        chart.animateY(800);
        chart.invalidate();
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}