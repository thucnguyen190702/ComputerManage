package com.example.computermanage.Model;

public class NhanVien {
    private String msnv;
    private String password;
    private String hoten;
    private String sdt;
    private String email;
    private byte[] hinhanh;

    public static final String TABLE_NHANVIEN = "nhanvien";
    public static final String COL_MSNV = "msnv";
    public static final String COL_PASSWORD = "password";
    public static final String COL_HOTEN = "hoten";
    public static final String COL_SDT = "sdt";
    public static final String COL_EMAIL = "email";
    public static final String COL_HINHANH = "hinhanh";

    public NhanVien() {
    }

    public NhanVien(String msnv, String password, String hoten, String sdt, String email, byte[] hinhanh) {
        this.msnv = msnv;
        this.password = password;
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.hinhanh = hinhanh;
    }

    public String getMsnv() {
        return msnv;
    }

    public void setMsnv(String msnv) {
        this.msnv = msnv;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }
}
