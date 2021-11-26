package com.example.computermanage.Model;

public class HoaDonChiTiet {
    private int mshdct;
    private String mshd;
    private String mssp;
    private int soluong;
    private double dongia;
    private int baohanh;
    private int giamgia;

    public static final String TABLE_HOADONCT = "hoadonchitiet";
    public static final String COL_MSHDCT = "mshdct";
    public static final String COL_MSHD = "mshd";
    public static final String COL_MSSP = "mssp";
    public static final String COL_SOLUONG = "soluong";
    public static final String COL_DONGIA = "dongia";
    public static final String COL_BAOHANH = "baohanh";
    public static final String COL_GIAMGIA = "giamgia";

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int mshdct, String mshd, String mssp, int soluong, double dongia, int baohanh, int giamgia) {
        this.mshdct = mshdct;
        this.mshd = mshd;
        this.mssp = mssp;
        this.soluong = soluong;
        this.dongia = dongia;
        this.baohanh = baohanh;
        this.giamgia = giamgia;
    }

    public int getMshdct() {
        return mshdct;
    }

    public void setMshdct(int mshdct) {
        this.mshdct = mshdct;
    }

    public String getMshd() {
        return mshd;
    }

    public void setMshd(String mshd) {
        this.mshd = mshd;
    }

    public String getMssp() {
        return mssp;
    }

    public void setMssp(String mssp) {
        this.mssp = mssp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public int getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(int baohanh) {
        this.baohanh = baohanh;
    }

    public int getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(int giamgia) {
        this.giamgia = giamgia;
    }
}
