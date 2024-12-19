package com.example.beanydrinks.model;

import java.util.Date;
import java.util.List;

public class HoaDon {
    private int idBan;
    private int idNhanVien;
    private int idKhachHang;
    private double tamTinh;
    private double thueVAT;
    private double tongTien;
    private List<OrderItem> products;

    // Constructor, getters and setters
    public HoaDon(int idBan, int idNhanVien, int idKhachHang, double tamTinh, double thueVAT, double tongTien, List<OrderItem> products) {
        this.idBan = idBan;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.tamTinh = tamTinh;
        this.thueVAT = thueVAT;
        this.tongTien = tongTien;
        this.products = products;
    }
    public HoaDon() {
        // constructor mặc định
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public double getTamTinh() {
        return tamTinh;
    }

    public void setTamTinh(double tamTinh) {
        this.tamTinh = tamTinh;
    }

    public double getThueVAT() {
        return thueVAT;
    }

    public void setThueVAT(double thueVAT) {
        this.thueVAT = thueVAT;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }
}
