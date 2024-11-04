package com.example.drinktutorial.Model;

public class NguoiDung {
    String HoTen, Email, TaiKhoan, MatKhau;

    public String getHoten() {
        return HoTen;
    }

    public void setHoten(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTaikhoan() {
        return TaiKhoan;
    }

    public void setTaikhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMatkhau() {
        return MatKhau;
    }

    public void setMatkhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public NguoiDung(String hoTen, String email, String taiKhoan, String matKhau) {
        HoTen = hoTen;
        Email = email;
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
    }

    public NguoiDung() {
    }
}
