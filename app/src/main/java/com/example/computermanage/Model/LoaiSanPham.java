package com.example.computermanage.Model;

public class LoaiSanPham {
    private String mslsp;
    private int mshang;
    private String tenlsp;
    private byte[] hinhanh;

    public static final String TABLE_LOAISANPHAM = "loaisanpham";
    public static final String COL_MSLSP = "mslsp";
    public static final String COL_MSHANG = "mshang";
    public static final String COL_TENLSP = "tenlsp";
    public static final String COL_HINHANH = "hinhanh";

    public LoaiSanPham() {
    }

    public LoaiSanPham(String mslsp, int mshang, String tenlsp, byte[] hinhanh) {
        this.mslsp = mslsp;
        this.mshang = mshang;
        this.tenlsp = tenlsp;
        this.hinhanh = hinhanh;
    }

    public String getMslsp() {
        return mslsp;
    }

    public void setMslsp(String mslsp) {
        this.mslsp = mslsp;
    }

    public int getMshang() {
        return mshang;
    }

    public void setMshang(int mshang) {
        this.mshang = mshang;
    }

    public String getTenlsp() {
        return tenlsp;
    }

    public void setTenlsp(String tenlsp) {
        this.tenlsp = tenlsp;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return getTenlsp();
    }
}
