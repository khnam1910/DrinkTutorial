package com.example.drinktutorial.Model;

import java.util.Map;

public class DoUong {
    private String keyID;
    private Map<String, Map<String, Integer>> NguyenLieu;
    private String Loai;
    private String HinhAnh;
    private String Name;
    private String BuocPhaChe;
    private String MoTa;

    public DoUong() {
    }

    public DoUong(Map<String, Map<String, Integer>> NguyenLieu, String Loai, String HinhAnh, String Name, String BuocPhaChe, String MoTa) {
        this.NguyenLieu = NguyenLieu;
        this.Loai = Loai;
        this.HinhAnh = HinhAnh;
        this.Name = Name;
        this.BuocPhaChe = BuocPhaChe;
        this.MoTa = MoTa;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public Map<String, Map<String, Integer>> getNguyenLieu() {
        return NguyenLieu;
    }

    public void setNguyenLieu(Map<String, Map<String, Integer>> NguyenLieu) {
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
}
