package com.example.drinktutorial.Model;

public class LoaiDoUong
{
    public String id_LDU;
    public String tenLoai;
    public String hinhAnh;

    public LoaiDoUong(String id_LDU, String tenLoai, String hinhAnh) {
        this.id_LDU = id_LDU;
        this.tenLoai = tenLoai;
        this.hinhAnh = hinhAnh;
    }

    public LoaiDoUong() {
    }

    public String getId_LDU() {
        return id_LDU;
    }

    public void setId_LDU(String id_LDU) {
        this.id_LDU = id_LDU;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

}
