package com.example.computermanage.Model;

public class SanPham {
    private String mssp;
    private String mslsp;
    private String tensp;
    private double giatien;
    private int tinhtrang;
    private byte[] hinhanh;
    private String mota;

    public static final String TABLE_SANPHAM = "sanpham";
    public static final String COL_MSSP = "mssp";
    public static final String COL_MSLSP = "mslsp";
    public static final String COL_TENSP = "tensp";
    public static final String COL_GIATIEN = "giatien";
    public static final String COL_TINHTRANG = "tinhtrang";
    public static final String COL_HINHANH = "hinhanh";
    public static final String COL_MOTA = "mota";

    public SanPham() {
    }

    public SanPham(String mssp, String mslsp, String tensp, double giatien, int tinhtrang, byte[] hinhanh, String mota) {
        this.mssp = mssp;
        this.mslsp = mslsp;
        this.tensp = tensp;
        this.giatien = giatien;
        this.tinhtrang = tinhtrang;
        this.hinhanh = hinhanh;
        this.mota = mota;
    }

    public String getMssp() {
        return mssp;
    }

    public void setMssp(String mssp) {
        this.mssp = mssp;
    }

    public String getMslsp() {
        return mslsp;
    }

    public void setMslsp(String mslsp) {
        this.mslsp = mslsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public double getGiatien() {
        return giatien;
    }

    public void setGiatien(double giatien) {
        this.giatien = giatien;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public String toString() {
        return getTensp();
    }
}
