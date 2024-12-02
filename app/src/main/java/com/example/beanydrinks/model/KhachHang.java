package com.example.beanydrinks.model;

public class KhachHang {
    private int idKhachHang;
    private String soDienThoai;
    private String hoTen;
    private Double diemThuong; // Đổi từ Float sang Double



    public KhachHang(int idKhachHang, String soDienThoai, String hoTen, Double diemThuong) {
        this.idKhachHang = idKhachHang;
        this.soDienThoai = soDienThoai;
        this.hoTen = hoTen;
        this.diemThuong = diemThuong;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

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

    public Double getDiemThuong() { // Thay Float bằng Double ở đây
        return diemThuong;
    }

    public void setDiemThuong(Double diemThuong) { // Thay Float bằng Double ở đây
        this.diemThuong = diemThuong;
    }
}