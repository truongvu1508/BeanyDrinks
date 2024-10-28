package com.example.beanydrinks;

public class Mon {
    private String loaiMon;  // Loại món
    private String maMon;    // Mã món
    private String tenMon;   // Tên món
    private double giaTien;  // Giá tiền
    private int hinhAnh;     // Hình ảnh

    // Constructor
    public Mon(String loaiMon, String maMon, String tenMon, double giaTien, int hinhAnh) {
        this.loaiMon = loaiMon;
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    // Getter và Setter
    public String getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        this.loaiMon = loaiMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
