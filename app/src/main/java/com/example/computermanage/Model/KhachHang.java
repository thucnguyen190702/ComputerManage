package com.example.computermanage.Model;

public class KhachHang {
    private String mskh;
    private String hoten;
    private String sdt;
    private int gioitinh;
    private String diachi;

    public static final String TABLE_KHACHHANG = "khachhang";
    public static final String COL_MSKH = "mskh";
    public static final String COL_HOTEN = "hoten";
    public static final String COL_SDT = "sdt";
    public static final String COL_GIOITINH = "gioitinh";
    public static final String COL_DIACHI = "diachi";

    public KhachHang() {
    }

    public KhachHang(String mskh, String hoten, String sdt, int gioitinh, String diachi) {
        this.mskh = mskh;
        this.hoten = hoten;
        this.sdt = sdt;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
    }

    public String getMskh() {
        return mskh;
    }

    public void setMskh(String mskh) {
        this.mskh = mskh;
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

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
