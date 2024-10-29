package com.example.beanydrinks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MonViewHolder> implements Filterable {
    private List<Mon> monList; // Danh sách món gốc
    private List<Mon> monListFiltered; // Danh sách món đã lọc

    public MonAdapter(List<Mon> monList) {
        this.monList = monList;
        this.monListFiltered = new ArrayList<>(monList); // Khởi tạo danh sách đã lọc bằng danh sách gốc
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thucdon, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        Mon currentMon = monListFiltered.get(position);
        holder.tvTenMon.setText(currentMon.getTenMon());
        holder.tvGiaTien.setText(String.valueOf(currentMon.getGiaTien()));
        holder.imgMon.setImageResource(currentMon.getHinhAnh());
    }

    @Override
    public int getItemCount() {
        return monListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Mon> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(monList); // Không lọc
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Mon mon : monList) {
                        if (mon.getTenMon().toLowerCase().contains(filterPattern)) {
                            filteredList.add(mon);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                monListFiltered.clear();
                monListFiltered.addAll((List<Mon>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class MonViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMon;
        TextView tvTenMon;
        TextView tvGiaTien;

        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMon = itemView.findViewById(R.id.imageView_mon);
            tvTenMon = itemView.findViewById(R.id.text_name);
            tvGiaTien = itemView.findViewById(R.id.text_GiaTien);
        }
    }
}
