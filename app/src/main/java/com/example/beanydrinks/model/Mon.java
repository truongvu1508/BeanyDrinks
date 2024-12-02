package com.example.beanydrinks.model;

import java.io.Serializable;

public class Mon implements Serializable {  // Thêm implements Serializable
    private String idloai;
    private String idSanPham;
    private String tenMon;
    private String giaTien;
    private String hinhAnh;

    // Constructor
    public Mon(String loaiMon, String idSanPham, String tenMon, String giaTien, String hinhAnh) {
        this.idloai = loaiMon;
        this.idSanPham = idSanPham;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    // Getters và Setters
    public String getLoaiMon() {
        return idloai;
    }

    public void setLoaiMon(String loaiMon) {
        this.idloai = loaiMon;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
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

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
