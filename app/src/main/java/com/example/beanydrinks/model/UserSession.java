package com.example.beanydrinks.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static UserSession instance;
    private NhanVien currentUser;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Constructor
    private UserSession(Context context) {
        sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Singleton instance
    public static UserSession getInstance(Context context) {
        if (instance == null) {
            instance = new UserSession(context);
        }
        return instance;
    }

    // Lưu thông tin người dùng vào SharedPreferences
    public void saveUser(NhanVien user) {
        editor.putInt("idNhanVien", user.getIdNhanVien());
        editor.putString("matKhau", user.getMatKhau());
        // Lưu các thông tin khác nếu có
        editor.apply();
    }

    // Lấy thông tin người dùng hiện tại từ SharedPreferences
    public NhanVien getCurrentUser() {
        int idNhanVien = sharedPreferences.getInt("idNhanVien", -1);
        String matKhau = sharedPreferences.getString("matKhau", null);
        // Lấy các thông tin khác nếu có
        if (idNhanVien != -1 && matKhau != null) {
            return new NhanVien(idNhanVien, matKhau);  // Giả sử bạn có constructor như thế này
        } else {
            return null;
        }
    }

    // Cập nhật lại thông tin người dùng
    public void setCurrentUser(NhanVien nhanVien) {
        this.currentUser = nhanVien;
        saveUser(nhanVien); // Lưu thông tin người dùng mỗi khi cập nhật
    }
}
