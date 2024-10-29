package com.example.beanydrinks;

public class KhachHang {
    private String soDienThoai;  // Số điện thoại
    private String hoTen;        // Họ tên     // Hình ảnh

    public KhachHang(String soDienThoai, String hoTen) {
        this.soDienThoai = soDienThoai;
        this.hoTen = hoTen;
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
}
