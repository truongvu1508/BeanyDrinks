package com.example.beanydrinks.model;

public class Mon {
    private String loaiMon;
    private String maMon;
    private String tenMon;
    private String giaTien;
    private int hinhAnh;

    // Constructor
    public Mon(String loaiMon, String maMon, String tenMon, String giaTien, int hinhAnh) {
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

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
