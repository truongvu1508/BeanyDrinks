package com.example.beanydrinks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder> {
    private List<NhanVien> mListNhanVien;

    public NhanVienAdapter(List<NhanVien> mListNhanVien) {
        this.mListNhanVien = mListNhanVien;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien, parent, false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien= mListNhanVien.get(position);
        if (nhanVien==null){
            return;
        }
        holder.imgNhanVien.setImageResource(nhanVien.getImage());
        holder.tvName.setText(nhanVien.getName());
        holder.tvPosition.setText(nhanVien.getPosition());
        holder.tvStatus.setText(nhanVien.getStatus());
    }

    @Override
    public int getItemCount() {
        if (mListNhanVien!=null){
            return mListNhanVien.size();
        }
        return 0;
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imgNhanVien;
        private TextView tvName;
        private TextView tvPosition;
        private TextView tvStatus;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNhanVien=itemView.findViewById(R.id.image_nhanvien);
            tvName=itemView.findViewById(R.id.text_name);
            tvPosition=itemView.findViewById(R.id.textView_ChucVu);
            tvStatus=itemView.findViewById(R.id.textView_TrangThai);
        }
    }
}
