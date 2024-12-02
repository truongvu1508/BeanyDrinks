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
        holder.imageButtonDelete.setOnClickListener(v -> showDeleteDialog(ban.getIdBan()));


        // Handle table click to open the order activity
        holder.tvBan.setOnClickListener(v -> {
            Intent intent = new Intent(context, orderban_nvActivity.class);
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
        builder.setView(dialogView).setCancelable(true);

        AlertDialog alertDialog = builder.create();

        cancelButton.setOnClickListener(v -> alertDialog.dismiss());

        editButton.setOnClickListener(v -> {
            String tableName = editTableName.getText().toString().trim();
            String area = editArea.getText().toString().trim();

            if (tableName.isEmpty() || area.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                // Gửi dữ liệu cập nhật lên server
                String url = Server.DuongDanUpdateBan;
                JSONObject params = new JSONObject();
                try {
                    params.put("id", ban.getIdBan()); // Gửi ID của bàn
                    params.put("ten", tableName);
                    params.put("idKhuVuc", area);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                        response -> {
                            try {
                                if (response.has("success") && response.getBoolean("success")) {
                                    Toast.makeText(context, "Cập nhật bàn thành công!", Toast.LENGTH_SHORT).show();

                                    // Cập nhật dữ liệu trong danh sách
                                    ban.setTenBan(tableName);
                                    ban.setKhuVuc(area);

                                    // Cập nhật lại giao diện
                                    notifyDataSetChanged();

                                    // Đóng dialog
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            Toast.makeText(context, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);
            }
        });

        alertDialog.show();
    }




    private void showDeleteDialog(int idBan) {
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
            // Xóa bàn từ cơ sở dữ liệu
            deleteTableFromDatabase(idBan);

            // Đóng hộp thoại
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
                // Gửi dữ liệu lên server
                String url = Server.DuongDanInsertBan; // Đường dẫn API
                JSONObject params = new JSONObject();
                try {
                    params.put("ten", tableName);
                    params.put("idKhuVuc", area);
                    params.put("trangthai", "Bàn trống");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                        response -> {
                            Log.d("ServerResponse", response.toString());
                            try {
                                if (response.has("success") && response.getBoolean("success")) {
                                    Log.d("ServerResponse", response.toString());

                                    // Thêm bàn mới vào danh sách và cập nhật giao diện
                                    Ban newBan = new Ban(tableName, "Bàn trống", area);
                                    banList.add(newBan);
                                    notifyItemInserted(banList.size() - 1);
                                    Toast.makeText(context, "Thêm bàn thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    String message = response.optString("message", "Thêm bàn thất bại");
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Lỗi xử lý phản hồi từ server", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            Toast.makeText(context, "Lỗi khi gửi dữ liệu lên server", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });

                // Thêm yêu cầu vào hàng đợi
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);
            }
        });


        dialog.show();

    }

    private void deleteTableFromDatabase(int idBan) {
        String url = Server.DuongDanDeleteBan;

        JSONObject postData = new JSONObject();
        try {
            postData.put("id", idBan);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                response -> {
                    try {
                        if (response.getString("status").equals("success")) {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                            // Xóa bàn khỏi danh sách
                            removeTableFromList(idBan);
                        } else {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Lỗi xử lý phản hồi từ server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(context, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }


    /*private void updateTableList() {
        // Cập nhật lại danh sách bàn trong adapter hoặc bất kỳ nơi nào bạn lưu trữ
        banList.removeIf(ban -> ban.getId() == tableId);  // Xóa bàn khỏi danh sách banList
        tableAdapter.notifyDataSetChanged();  // Cập nhật giao diện người dùng
    }*/

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

}

