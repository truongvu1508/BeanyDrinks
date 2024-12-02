package com.example.beanydrinks.model;

public class KhachHang {
    private String soDienThoai;
    private String hoTen;
    private Double diemThuong; // Đổi từ Float sang Double

    public KhachHang() {
    }

    // Constructor không yêu cầu id
    public KhachHang(String soDienThoai, String hoTen, Double diemThuong) {
        this.soDienThoai = soDienThoai;
        this.hoTen = hoTen;
        this.diemThuong = diemThuong;
    }

    // Getters và setters
    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Double getDiemThuong() {
        return diemThuong;
    }

    public void setDiemThuong(Double diemThuong) {
        this.diemThuong = diemThuong;
    }
}
