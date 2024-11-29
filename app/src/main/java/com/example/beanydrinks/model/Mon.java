package com.example.beanydrinks.model;

public class Mon {
    private String idloai;
    private String idSanPham;  // Thay maMon thành idSanPham
    private String tenMon;
    private String giaTien;
    private String hinhAnh;

    // Constructor
    public Mon(String loaiMon, String idSanPham, String tenMon, String giaTien, String hinhAnh) {
        this.idloai = loaiMon;
        this.idSanPham = idSanPham;  // Gán idSanPham thay vì maMon
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    // Getter và Setter
    public String getLoaiMon() {
        return idloai;
    }

    public void setLoaiMon(String loaiMon) {
        this.idloai = loaiMon;
    }

    public String getIdSanPham() {  // Thêm phương thức getter cho idSanPham
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {  // Thêm phương thức setter cho idSanPham
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
