package com.example.beanydrinks.model;

public class NhanVien {
    private int idNhanVien;
    private String tenNhanVien;
    private String gioiTinh;
    private String ngaySinh;
    private String chucVu;
    private String soDienThoai;
    private String diaChi;
    private String trangThai; // Trạng thái (Đang làm việc, Off ca)
    private String matKhau;   // Mật khẩu
    private String role;      // Vai trò (admin, staff)

    // Constructor
    public NhanVien(int idNhanVien, String tenNhanVien, String gioiTinh, String ngaySinh, String chucVu,
                    String soDienThoai, String diaChi, String trangThai, String matKhau, String role) {
        this.idNhanVien = idNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.matKhau = matKhau;
        this.role = role;
    }

    // Getter và Setter
    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Phương thức toString() để hiển thị thông tin đối tượng
    @Override
    public String toString() {
        return "NhanVien{" +
                "idNhanVien=" + idNhanVien +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
