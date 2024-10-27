package com.example.beanydrinks;

import android.graphics.Color;

public class Ban {
    private String tenBan;
    private String trangThai;
    private String khuVuc;
    private int borderColor;

    // Constructor with parameters
    public Ban(String tenBan, String trangThai, String khuVuc) {
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.khuVuc = khuVuc;
        this.borderColor = getColorForStatus(trangThai);
    }

    // Default constructor
    public Ban() {
        this.tenBan = "";
        this.trangThai = "Available"; // Default status
        this.khuVuc = "";
    }

    // Getters
    public String getTenBan() {
        return tenBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    // Setters with simple validation
    public void setTenBan(String tenBan) {
        if (tenBan == null || tenBan.isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be empty");
        }
        this.tenBan = tenBan;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setTrangThai(String trangThai) {
        if (trangThai == null || trangThai.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        this.trangThai = trangThai;
        this.borderColor = getColorForStatus(trangThai);
    }

    public void setKhuVuc(String khuVuc) {
        if (khuVuc == null || khuVuc.isEmpty()) {
            throw new IllegalArgumentException("Area cannot be empty");
        }
        this.khuVuc = khuVuc;
    }

    // toString method for easier debugging
    @Override
    public String toString() {
        return "Ban{" +
                "tenBan='" + tenBan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", khuVuc='" + khuVuc + '\'' +
                '}';
    }
    public static int getColorForStatus(String status) {
        switch (status) {
            case "Bàn trống":
                return Color.GRAY; // Màu cho "Bàn trống"
            case "Đang phục vụ":
                return Color.RED; // Màu cho "Đang phục vụ"
            case "Đã thanh toán":
                return Color.GREEN; // Màu cho "Đã thanh toán"
            case "Yêu cầu thanh toán":
                return Color.BLUE; // Màu cho "Yêu cầu thanh toán"
            default:
                return Color.BLACK; // Màu mặc định
        }
    }
}
