package com.example.beanydrinks.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.fragment.XemNhanVienFragment;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.R;

import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ItemHolder> {

    private final FragmentActivity activity;
    private final ArrayList<NhanVien> nhanVienList;

    /**
     * Constructor của adapter
     * @param nhanVienList danh sách nhân viên
     * @param activity Activity chứa RecyclerView (phải là FragmentActivity)
     */
    public NhanVienAdapter(ArrayList<NhanVien> nhanVienList, FragmentActivity activity) {
        this.activity = activity;
        this.nhanVienList = nhanVienList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        NhanVien nhanVien = nhanVienList.get(position);

        // Gán dữ liệu vào các TextView
        holder.txtTenNhanVien.setText(nhanVien.getTenNhanVien());
        holder.txtChucVu.setText(nhanVien.getChucVu());
        holder.txtTrangThai.setText(nhanVien.getTrangThai());

        // Đổi màu chữ nếu trạng thái là "Off ca"
        if ("Off ca".equalsIgnoreCase(nhanVien.getTrangThai())) {
            holder.txtTrangThai.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red)); // Thay R.color.red bằng màu bạn định nghĩa
        } else {
            holder.txtTrangThai.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green)); // Màu mặc định
        }

        // Xử lý sự kiện click item
        holder.itemView.setOnClickListener(view -> {
            // Mở fragment XemNhanVienFragment và truyền dữ liệu nhân viên
            XemNhanVienFragment fragment = new XemNhanVienFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable("thongtinnhanvien", nhanVien);
            fragment.setArguments(bundle);

            // Thực hiện giao dịch fragment
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        Log.d("NhanVienAdapter", "NhanVien: " + nhanVien.getTenNhanVien());
    }


    @Override
    public int getItemCount() {
        return nhanVienList.size();
    }

    /**
     * Lớp ItemHolder để ánh xạ view của từng item trong RecyclerView
     */
    public static class ItemHolder extends RecyclerView.ViewHolder {

        public TextView txtTenNhanVien;
        public TextView txtChucVu;
        public TextView txtTrangThai;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ các view trong layout item_nhanvien
            txtTenNhanVien = itemView.findViewById(R.id.text_name);
            txtChucVu = itemView.findViewById(R.id.textView_ChucVu);
            txtTrangThai = itemView.findViewById(R.id.textView_TrangThai);
        }
    }
    public void updateNhanVienList(ArrayList<NhanVien> newList) {
        nhanVienList.clear();
        nhanVienList.addAll(newList);
        notifyDataSetChanged();
    }
}
