package com.example.drinktutorial.Model;

import java.util.Map;

public class DoUong {
    private String keyID;
    private Map<String, Map<String, Double>> NguyenLieu;
    private String Loai;
    private String HinhAnh;
    private String Name;
    private String BuocPhaChe;
    private String MoTa;
    private String Ngay;

    public DoUong() {
    }

    public DoUong(String keyID, Map<String, Map<String, Double>> nguyenLieu, String loai, String hinhAnh, String name, String buocPhaChe, String moTa, String ngay) {
        this.keyID = keyID;
        NguyenLieu = nguyenLieu;
        Loai = loai;
        HinhAnh = hinhAnh;
        Name = name;
        BuocPhaChe = buocPhaChe;
        MoTa = moTa;
        Ngay = ngay;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public Map<String, Map<String, Double>> getNguyenLieu() {
        return NguyenLieu;
    }

    public void setNguyenLieu(Map<String, Map<String, Double>> NguyenLieu) {
        this.NguyenLieu = NguyenLieu;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBuocPhaChe() {
        return BuocPhaChe;
    }

    public void setBuocPhaChe(String BuocPhaChe) {
        this.BuocPhaChe = BuocPhaChe;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
