package com.example.beanydrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.Mon;

import java.util.List;

public class ChonMonAdapter extends RecyclerView.Adapter<ChonMonAdapter.MonViewHolder> {

    private List<Mon> monList;
    private final Context context;

    public ChonMonAdapter(Context context) {
        this.context = context;
    }

    public void setMonList(List<Mon> monList) {
        this.monList = monList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chonmon, parent, false);
        return new MonViewHolder(view);
    }

    public void updateData(List<Mon> updatedList) {
        this.monList = updatedList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        if (monList != null && position < monList.size()) {
            Mon mon = monList.get(position);

            holder.textTenMon.setText(mon.getTenMon());
            holder.textSoTien.setText(mon.getGiaTien() + " VNĐ");

            // Use Glide to load the image
            Glide.with(context)
                    .load(mon.getHinhAnh())
                    .placeholder(R.drawable.noimage)
                    .into(holder.imageMon);

            // Set initial button state
            holder.buttonChonMon.setText("Chọn");
            holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenxam); // Gray background, orange border
            holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.white)); // Orange text

            holder.buttonChonMon.setOnClickListener(v -> {
                // Toggle between "Select" and "Already Selected"
                if (holder.buttonChonMon.getText().toString().equals("Chọn")) {
                    holder.buttonChonMon.setText("Đã chọn");
                    holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenblue); // Blue background
                    holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.white)); // White text
                } else {
                    holder.buttonChonMon.setText("Chọn");
                    holder.buttonChonMon.setBackgroundResource(R.drawable.button_nenxam); // Gray background, orange border
                    holder.buttonChonMon.setTextColor(context.getResources().getColor(R.color.white)); // Orange text
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (monList != null) ? monList.size() : 0;
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
