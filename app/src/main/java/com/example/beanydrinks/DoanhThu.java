package com.example.beanydrinks;

import java.util.Date;

public class DoanhThu {
    private String madon;
    private String tennv;
    private String ban;
    private String tonghoadon;
    private String trangthai;
    private String ngayhd;

    public DoanhThu(String madon, String tennv, String ban, String tonghoadon, String trangthai, String ngayhd) {
        this.madon = madon;
        this.tennv = tennv;
        this.ban = ban;
        this.tonghoadon = tonghoadon;
        this.trangthai = trangthai;
        this.ngayhd = ngayhd;
    }

    public String getMadon() {
        return madon;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public String getNgayhd() {
        return ngayhd;
    }

    public void setNgayhd(String ngayhd) {
        this.ngayhd = ngayhd;
    }

    public String getTonghoadon() {
        return tonghoadon;
    }

    public void setTonghoadon(String tonghoadon) {
        this.tonghoadon = tonghoadon;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
