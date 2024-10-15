package com.example.beanydrinks;

public class NhanVien {
    private int image;
    private String name;
    private String position;
    private String status;

    public NhanVien(int image, String name, String position, String status) {
        this.image = image;
        this.name = name;
        this.position = position;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getStatus() {
        return status;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
