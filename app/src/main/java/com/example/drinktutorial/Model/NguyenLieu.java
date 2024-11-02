package com.example.drinktutorial.Model;

public class NguyenLieu {
    private String keyID;  // Thêm thuộc tính keyID
    private String Loai;
    private String Ten;
    private String DonViDo;
    private String Anh;

    public NguyenLieu() {
    }

    public NguyenLieu(String keyID, String Loai, String Ten, String DonViDo, String Anh) {
        this.keyID = keyID;
        this.Loai = Loai;
        this.Ten = Ten;
        this.DonViDo = DonViDo;
        this.Anh = Anh;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getDonViDo() {
        return DonViDo;
    }

    public void setDonViDo(String DonViDo) {
        this.DonViDo = DonViDo;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String Anh) {
        this.Anh = Anh;
    }

}
