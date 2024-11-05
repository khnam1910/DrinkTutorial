package com.example.drinktutorial.Model;

public class LoaiNguyenLieu {
    private String keyID;
    private String TenLoai;
    private String HinhAnh;

    public LoaiNguyenLieu() {
    }

    public LoaiNguyenLieu(String keyID, String tenLoai, String hinhAnh) {
        this.keyID = keyID;
        TenLoai = tenLoai;
        HinhAnh = hinhAnh;
    }
    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
