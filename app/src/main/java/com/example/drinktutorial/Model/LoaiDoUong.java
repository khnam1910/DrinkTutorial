package com.example.drinktutorial.Model;

import java.io.Serializable;

public class LoaiDoUong implements Serializable {
    private String idLDU;
    private String TenLoai;
    private String HinhAnh;

    public LoaiDoUong(String idLDU, String TenLoai, String HinhAnh) {
        this.idLDU = idLDU;
        this.TenLoai = TenLoai;
        this.HinhAnh = HinhAnh;
    }

    public LoaiDoUong() {
    }

    public String getIdLDU() {
        return idLDU;
    }

    public void setIdLDU(String idLDU) {
        this.idLDU = idLDU;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String TenLoai) {
        this.TenLoai = TenLoai;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
}
