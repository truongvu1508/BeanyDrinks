package com.example.beanydrinks.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private int idHoaDonChiTiet;
    private int idHoaDon;
    private Mon sanPham;
    private int soLuong;
    private double thanhTien;

    public OrderItem(int idHoaDonChiTiet, int idHoaDon, Mon sanPham, int soLuong, double thanhTien) {
        this.idHoaDonChiTiet = idHoaDonChiTiet;
        this.idHoaDon = idHoaDon;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public int getIdHoaDonChiTiet() {
        return idHoaDonChiTiet;
    }

    public void setIdHoaDonChiTiet(int idHoaDonChiTiet) {
        this.idHoaDonChiTiet = idHoaDonChiTiet;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public Mon getSanPham() {
        return sanPham;
    }

    public void setSanPham(Mon sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
