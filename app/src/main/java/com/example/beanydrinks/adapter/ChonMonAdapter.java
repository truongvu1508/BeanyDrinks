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

import com.example.beanydrinks.R;
import com.example.beanydrinks.model.Mon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChonMonAdapter extends RecyclerView.Adapter<ChonMonAdapter.MonViewHolder> {

    private final Context context;
    private List<Mon> monList;
    private boolean[] isSelected;
    private final OnAddMonListener onAddMonListener;

    // Constructor
    public ChonMonAdapter(Context context, OnAddMonListener listener) {
        this.context = context;
        this.monList = new ArrayList<>();
        this.isSelected = new boolean[0];
        this.onAddMonListener = listener;
    }

    // Update the list of dishes and refresh adapter
    public void setMonList(List<Mon> monList) {
        this.monList = monList != null ? monList : new ArrayList<>();
        this.isSelected = new boolean[this.monList.size()];
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chonmon, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        Mon mon = monList.get(position);
        holder.textTenMon.setText(mon.getTenMon());
        holder.textSoTien.setText(String.format("%s VNĐ", mon.getGiaTien()));

        // Load image using Picasso
        String imageUrl = mon.getHinhAnh();
        if (imageUrl != null && imageUrl.startsWith("http")) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.errorimage)
                    .resize(400, 200)
                    .into(holder.imageMon);
        } else {
            int resourceId = context.getResources().getIdentifier(imageUrl, "drawable", context.getPackageName());
            Picasso.get()
                    .load(resourceId)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.errorimage)
                    .into(holder.imageMon);
        }

        // Update button state
        boolean selected = isSelected[position];
        holder.buttonChonMon.setText(selected ? "Đã chọn" : "Chọn món");
        holder.buttonChonMon.setBackgroundResource(
                selected ? R.drawable.button_nenxam : R.drawable.button_nenblue
        );

        // Handle button click
        holder.buttonChonMon.setOnClickListener(v -> {
            isSelected[position] = !selected;
            notifyItemChanged(position);

            if (isSelected[position]) {
                onAddMonListener.onMonAdded(mon); // Callback when a dish is selected
            }
        });
    }

    @Override
    public int getItemCount() {
        return monList.size();
    }

    // ViewHolder for dish item
    static class MonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMon;
        TextView textTenMon, textSoTien;
        Button buttonChonMon;

        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMon = itemView.findViewById(R.id.image_mon);
            textTenMon = itemView.findViewById(R.id.text_TenMon);
            textSoTien = itemView.findViewById(R.id.text_SoTien);
            buttonChonMon = itemView.findViewById(R.id.button_chonmon);
        }
    }

    // Listener interface for handling dish selection
    public interface OnAddMonListener {
        void onMonAdded(Mon mon);
    }
}
