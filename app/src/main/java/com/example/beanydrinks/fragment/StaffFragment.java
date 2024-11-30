    package com.example.beanydrinks.fragment;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonArrayRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.beanydrinks.R;
    import com.example.beanydrinks.adapter.NhanVienAdapter;
    import com.example.beanydrinks.model.NhanVien;
    import com.example.beanydrinks.ultil.CheckConnection;
    import com.example.beanydrinks.ultil.Server;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;

    public class StaffFragment extends Fragment {

        private RecyclerView rcvNhanVien;
        private NhanVienAdapter nhanVienAdapter;
        private ArrayList<NhanVien> mangnv;

        public StaffFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_staff, container, false);

            rcvNhanVien = view.findViewById(R.id.rcv_NhanVien);
            mangnv = new ArrayList<>();

            if (getActivity() != null) {
                nhanVienAdapter = new NhanVienAdapter(mangnv, getActivity());
            } else {
                Log.e("StaffFragment", "Activity is null, cannot initialize NhanVienAdapter.");
                return view;
            }

            rcvNhanVien.setLayoutManager(new LinearLayoutManager(requireContext()));
            rcvNhanVien.setAdapter(nhanVienAdapter);

            if (CheckConnection.haveNetworkConnection(requireContext())) {
                getNV();
            } else {
                CheckConnection.ShowToast_Short(requireContext(), "Không có kết nối mạng. Vui lòng thử lại.");
            }

            FloatingActionButton btnAddNV = view.findViewById(R.id.btn_addnv);
            if (btnAddNV != null) {
                btnAddNV.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new AddNhanVienFragment())
                        .addToBackStack(null)
                        .commit());
            }

            return view;
        }

        private void getNV() {
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetNhanVien, response -> {
                if (response != null) {
                    ArrayList<NhanVien> filteredList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String role = jsonObject.getString("role");
                            if (!"admin".equals(role)) {
                                filteredList.add(new NhanVien(
                                        jsonObject.getInt("idNhanVien"),
                                        jsonObject.getString("tenNhanVien"),
                                        jsonObject.getString("gioiTinh"),
                                        jsonObject.getString("ngaySinh"),
                                        jsonObject.getString("chucVu"),
                                        jsonObject.getString("soDienThoai"),
                                        jsonObject.getString("diaChi"),
                                        jsonObject.getString("trangThai"),
                                        jsonObject.getString("matKhau"),
                                        role
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangnv.clear();
                    mangnv.addAll(filteredList);
                    nhanVienAdapter.notifyDataSetChanged();
                }
            }, error -> {
                Log.e("Volley Error", "Error: " + error.getMessage());
                CheckConnection.ShowToast_Short(requireContext(), "Lỗi khi tải dữ liệu");
            });

            requestQueue.add(jsonArrayRequest);
        }
    }
