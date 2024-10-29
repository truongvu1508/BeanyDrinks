package com.example.beanydrinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChonMonAdapter extends RecyclerView.Adapter<ChonMonAdapter.MonViewHolder> {

    private List<Mon> monList;
    private final Context context;

    // List to track selection state of each item
    private boolean[] isSelected;

    public ChonMonAdapter(Context context) {
        this.context = context;
        this.monList = null; // Khởi tạo monList là null
        this.isSelected = new boolean[0]; // Khởi tạo mảng rỗng
    }

    public void setMonList(List<Mon> monList) {
        this.monList = monList;

        // Kiểm tra monList có null hoặc rỗng không
        if (monList != null && !monList.isEmpty()) {
            this.isSelected = new boolean[monList.size()]; // Initialize selection array
        } else {
            this.isSelected = new boolean[0]; // Nếu rỗng, khởi tạo mảng rỗng
        }

        notifyDataSetChanged(); // Notify that the data set has changed
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chonmon, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        if (monList != null && position < monList.size()) { // Kiểm tra null và vị trí
            Mon mon = monList.get(position);
            holder.textTenMon.setText(mon.getTenMon());
            holder.textSoTien.setText(mon.getGiaTien());
            holder.imageMon.setImageResource(mon.getHinhAnh());

            // Set initial button state based on selection
            if (isSelected[position]) {
                holder.buttonChonMon.setText("Đã chọn");
                holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenxam);
            } else {
                holder.buttonChonMon.setText("Chọn món");
                holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenblue);
            }

            // Set the button click listener
            holder.buttonChonMon.setOnClickListener(v -> {
                // Toggle selection state
                isSelected[position] = !isSelected[position];
                notifyItemChanged(position); // Notify the adapter to refresh the specific item
            });
        }
    }

    @Override
    public int getItemCount() {
        return (monList != null) ? monList.size() : 0; // Return 0 if monList is null
    }

    static class MonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMon;
        TextView textTenMon;
        TextView textSoTien;
        Button buttonChonMon;

        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMon = itemView.findViewById(R.id.image_mon);
            textTenMon = itemView.findViewById(R.id.text_TenMon);
            textSoTien = itemView.findViewById(R.id.text_SoTien);
            buttonChonMon = itemView.findViewById(R.id.button_chonmon);
        }
    }
}
