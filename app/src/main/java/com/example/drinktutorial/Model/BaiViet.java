package com.example.drinktutorial.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class BaiViet {
    private String keyID;
    private String TieuDe;
    private HashMap<String, String> HinhAnh;
    private String NoiDung;
    private String NgayDang;

    public BaiViet() {
    }

    public BaiViet(String keyID, String tieuDe, HashMap<String, String> hinhAnh, String noiDung, String ngayDang) {
        this.keyID = keyID;
        this.TieuDe = tieuDe;
        this.HinhAnh = hinhAnh;
        this.NoiDung = noiDung;
        this.NgayDang = ngayDang;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public HashMap<String, String> getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(HashMap<String, String> hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getNgayDang() {
        return NgayDang;
    }

    public void getNgayDang(String ngayDang) {
        this.NgayDang = ngayDang;
    }
}
