package com.example.beanydrinks.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beanydrinks.model.Ban;
import com.example.beanydrinks.R;
import com.example.beanydrinks.orderban_nv;

import java.util.List;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    private Context context;
    private List<Ban> banList;

    public BanAdapter(Context context, List<Ban> banList) {
        this.context = context;
        this.banList = banList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ban, parent, false);
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
        // Handle edit button click
        holder.imageButtonEdit.setOnClickListener(v -> showEditDialog(ban));

        // Handle delete button click
        holder.imageButtonDelete.setOnClickListener(v -> showDeleteDialog(position));

        // Handle table click to open the order activity
        holder.tvBan.setOnClickListener(v -> {
            Intent intent = new Intent(context, orderban_nv.class);
            intent.putExtra("ban_name", ban.getTenBan()); // Pass the table name
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return banList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBan;
        ImageButton imageButtonEdit;
        ImageButton imageButtonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBan = itemView.findViewById(R.id.tvBan);
            imageButtonEdit = itemView.findViewById(R.id.imageButton_Edit);
            imageButtonDelete = itemView.findViewById(R.id.imageButton_Delete);
        }
    }

    private void showEditDialog(Ban ban) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.layout_dialog_suaban, null);

        EditText editTableName = dialogView.findViewById(R.id.edit_table_name);
        EditText editArea = dialogView.findViewById(R.id.edit_area);
        Button cancelButton = dialogView.findViewById(R.id.button_huy);
        Button editButton = dialogView.findViewById(R.id.button_sua);

        editTableName.setText(ban.getTenBan());
        editArea.setText(ban.getKhuVuc());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(true);

        AlertDialog alertDialog = builder.create();

        cancelButton.setOnClickListener(v -> alertDialog.dismiss());

        editButton.setOnClickListener(v -> {
            String tableName = editTableName.getText().toString();
            String area = editArea.getText().toString();

            // Update the table data and refresh the RecyclerView
            ban.setTenBan(tableName);
            ban.setKhuVuc(area);
            notifyDataSetChanged();
            Toast.makeText(context, "Table edited successfully!", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    public void showDeleteDialog(int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.layout_dialog_xoaban, null);

        Button cancelButton = dialogView.findViewById(R.id.button_cancel);
        Button confirmButton = dialogView.findViewById(R.id.button_confirm);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(true);

        AlertDialog alertDialog = builder.create();

        cancelButton.setOnClickListener(v -> alertDialog.dismiss());

        confirmButton.setOnClickListener(v -> {
            // Remove the table from the list and refresh the RecyclerView
            banList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Table deleted successfully!", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        alertDialog.show();
    }
    public void showAddTableDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.layout_dialog_themban, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(dialogView);

        EditText editTableName = dialogView.findViewById(R.id.edit_table_name);
        EditText editArea = dialogView.findViewById(R.id.edit_area);
        Button buttonCancel = dialogView.findViewById(R.id.button_huy);
        Button buttonAdd = dialogView.findViewById(R.id.button_them);

        AlertDialog dialog = dialogBuilder.create();

        // Handle the cancel button click
        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        // Handle the add button click
        buttonAdd.setOnClickListener(v -> {
            String tableName = editTableName.getText().toString().trim();
            String area = editArea.getText().toString().trim();

            if (tableName.isEmpty() || area.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập tên bàn và khu vực", Toast.LENGTH_SHORT).show();
            } else {
                // Create new Ban object
                Ban newBan = new Ban(tableName, "Bàn trống", area);
                banList.add(newBan);
                notifyItemInserted(banList.size() - 1); // Notify adapter of the new item
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private Drawable createBorderDrawable(int borderColor) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.WHITE); // Màu nền
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL); // Màu nền
        shapeDrawable.getPaint().setStrokeWidth(5); // Độ dày của viền
        shapeDrawable.getPaint().setColor(borderColor); // Thiết lập màu viền
        return shapeDrawable;
    }
}
