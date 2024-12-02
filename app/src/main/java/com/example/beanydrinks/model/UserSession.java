package com.example.beanydrinks.model;

public class UserSession {
    private static UserSession instance;
    private NhanVien currentUser;

    // Phương thức lấy ra phiên người dùng
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Lưu thông tin người dùng
    public void setCurrentUser(NhanVien nhanVien) {
        this.currentUser = nhanVien;
    }

    // Lấy thông tin người dùng hiện tại
    public NhanVien getCurrentUser() {
        return currentUser;
    }
}