package com.example.drinktutorial.Model;

import java.util.Map;

public class NguoiDung {
    String HoTen, Email, MatKhau, Address, Phone, DOB;
    Map<String, Boolean> Yeuthich;

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public Map<String, Boolean> getYeuthich() {
        return Yeuthich;
    }

    public void setYeuthich(Map<String, Boolean> yeuthich) {
        Yeuthich = yeuthich;
    }

    public NguoiDung() {
    }

    public NguoiDung(String hoTen, String email, String matKhau, String address, String phone, String DOB, Map<String, Boolean> yeuthich) {
        HoTen = hoTen;
        Email = email;
        MatKhau = matKhau;
        Address = address;
        Phone = phone;
        this.DOB = DOB;
        Yeuthich = yeuthich;
    }
}
