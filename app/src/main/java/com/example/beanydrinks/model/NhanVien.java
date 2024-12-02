package com.example.beanydrinks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NhanVien implements Parcelable {
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

    // Constructor rỗng
    public NhanVien() {
    }
    // Constructor với các tham số được yêu cầu
    public NhanVien(String soDienThoai, String matKhau, String tenNhanVien,
                    String ngaySinh, String diaChi, String gioiTinh, String role) {
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.role = role;
    }

    // Constructor chỉ có số điện thoại, mật khẩu và role
    public NhanVien(String soDienThoai, String matKhau, String role) {
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.role = role;
    }

    // Constructor không yêu cầu id
    public NhanVien(String tenNhanVien, String gioiTinh, String ngaySinh, String chucVu,
                    String soDienThoai, String diaChi, String trangThai, String matKhau, String role) {
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

    // Constructor đầy đủ
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

    // Implement Parcelable methods
    protected NhanVien(Parcel in) {
        idNhanVien = in.readInt();
        tenNhanVien = in.readString();
        gioiTinh = in.readString();
        ngaySinh = in.readString();
        chucVu = in.readString();
        soDienThoai = in.readString();
        diaChi = in.readString();
        trangThai = in.readString();
        matKhau = in.readString();
        role = in.readString();
    }

    public static final Creator<NhanVien> CREATOR = new Creator<NhanVien>() {
        @Override
        public NhanVien createFromParcel(Parcel in) {
            return new NhanVien(in);
        }

        @Override
        public NhanVien[] newArray(int size) {
            return new NhanVien[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idNhanVien);
        dest.writeString(tenNhanVien);
        dest.writeString(gioiTinh);
        dest.writeString(ngaySinh);
        dest.writeString(chucVu);
        dest.writeString(soDienThoai);
        dest.writeString(diaChi);
        dest.writeString(trangThai);
        dest.writeString(matKhau);
        dest.writeString(role);
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
