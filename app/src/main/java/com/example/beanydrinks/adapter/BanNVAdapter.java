package com.example.beanydrinks.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.fragment.QuanLyKhuVucNVFragment;
import com.example.beanydrinks.model.Ban;
import com.example.beanydrinks.R;
import com.example.beanydrinks.activity.orderban_nvActivity;
import com.example.beanydrinks.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanNVAdapter extends RecyclerView.Adapter<BanNVAdapter.ViewHolder> {
    private Context context;
    private List<Ban> banList;
    private OnTableClickListener onTableClickListener;

    public BanNVAdapter(Context context, List<Ban> banList, OnTableClickListener listener) {
        this.context = context;
        this.banList = banList;
        this.onTableClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ban_nv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ban ban = banList.get(position);
        holder.tvBan.setText(ban.getTenBan());
        switch (ban.getTrangThai()) {
            case "Bàn trống":
                holder.tvBan.setBackgroundResource(R.drawable.bg_circle); // Viền trắng
                break;
            case "Đang phục vụ":
                holder.tvBan.setBackgroundResource(R.drawable.bg_circle_red);
                break;
            case "Đã thanh toán":
                holder.tvBan.setBackgroundResource(R.drawable.bg_circle_green);
                break;
            case "Yêu cầu thanh toán":
                holder.tvBan.setBackgroundResource(R.drawable.bg_circle_orange);
                break;
            default:
                holder.tvBan.setBackgroundResource(R.drawable.bg_circle);
                break;
        }


        // Handle table click to open the order activity
        holder.tvBan.setOnClickListener(v -> {
            Intent intent = new Intent(context, orderban_nvActivity.class);
            intent.putExtra("ban_name", ban.getTenBan()); // Pass the table name
            context.startActivity(intent);
        });

        // Sự kiện nhấn vào bàn
        holder.tvBan.setOnClickListener(v -> {
            if (onTableClickListener != null) {
                onTableClickListener.onTableClick(ban.getIdBan()); // Trả về ID bàn
            }
        });

    }

    @Override
    public int getItemCount() {
        return banList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBan;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBan = itemView.findViewById(R.id.tvBan);
        }
    }

    private Drawable createBorderDrawable(int borderColor) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.WHITE); // Màu nền
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL); // Màu nền
        shapeDrawable.getPaint().setStrokeWidth(5); // Độ dày của viền
        shapeDrawable.getPaint().setColor(borderColor); // Thiết lập màu viền
        return shapeDrawable;
    }

    private void removeTableFromList(int idBan) {
        for (int i = 0; i < banList.size(); i++) {
            if (banList.get(i).getIdBan() == idBan) {
                banList.remove(i);
                notifyItemRemoved(i); // Thông báo vị trí đã bị xóa
                return;
            }
        }
    }

    public interface OnTableClickListener {
        void onTableClick(int idBan); // Trả về ID bàn
        void onEditTable(Ban ban);    // Trả về thông tin bàn
        void onDeleteTable(int idBan); // Trả về ID bàn
    }

}