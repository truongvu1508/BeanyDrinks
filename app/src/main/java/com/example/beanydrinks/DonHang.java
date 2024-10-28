package com.example.beanydrinks;

public class DonHang {
    private String tenMon;
    private int soLuong;
    private String giaTien;
    private int imageResId;

    public DonHang(String tenMon, int soLuong, String giaTien, int imageResId) {
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.imageResId = imageResId;
    }

    public String getTenMon() {
        return tenMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public int getImageResId() {
        return imageResId;
    }
}

