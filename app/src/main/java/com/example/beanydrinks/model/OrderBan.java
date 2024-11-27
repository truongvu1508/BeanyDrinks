package com.example.beanydrinks.model;

public class OrderBan {
    private String tenMon; // Tên món
    private String giaTien; // Giá tiền
    private int soLuong; // Số lượng
    private int hinhMon; // Tài nguyên hình ảnh

    public OrderBan(String tenMon, String giaTien, int soLuong, int hinhMon) {
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.hinhMon = hinhMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setHinhMon(int hinhMon) {
        this.hinhMon = hinhMon;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public int getHinhMon() {
        return hinhMon;
    }
}

