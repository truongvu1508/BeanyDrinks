package com.example.beanydrinks;

public class KhachHang {
    private String soDienThoai;  // Số điện thoại
    private String hoTen;        // Họ tên
    private int hinhAnh;         // Hình ảnh

    public KhachHang(String soDienThoai, String hoTen, int hinhAnh) {
        this.soDienThoai = soDienThoai;
        this.hoTen = hoTen;
        this.hinhAnh = hinhAnh;
    }

    // Getter cho soDienThoai
    public String getSoDienThoai() {
        return soDienThoai;
    }

    // Setter cho soDienThoai
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    // Getter cho hoTen
    public String getHoTen() {
        return hoTen;
    }

    // Setter cho hoTen
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    // Getter cho hinhAnh
    public int getHinhAnh() {
        return hinhAnh;
    }

    // Setter cho hinhAnh
    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
