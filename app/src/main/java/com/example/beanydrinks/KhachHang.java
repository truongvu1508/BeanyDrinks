package com.example.beanydrinks;

public class KhachHang {
    private String soDienThoai;
    private String hoTen;
    private Double diemThuong; // Đổi từ Float sang Double

    public KhachHang(String soDienThoai, String hoTen, Double diemThuong) { // Thay Float bằng Double ở đây
        this.soDienThoai = soDienThoai;
        this.hoTen = hoTen;
        this.diemThuong = diemThuong;
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
