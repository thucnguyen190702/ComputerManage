package com.example.computermanage.Model;

public class Hang {
    private int mshang;
    private String tenhang;
    private byte[] hinhanh;

    public static final String TABLE_HANG = "hang";
    public static final String COL_MSHANG = "mshang";
    public static final String COL_TENHANG = "tenhang";
    public static final String COL_HINHANH = "hinhanh";

    public Hang() {
    }

    public Hang(int mshang, String tenhang, byte[] hinhanh) {
        this.mshang = mshang;
        this.tenhang = tenhang;
        this.hinhanh = hinhanh;
    }

    public Hang(int mshang) {
        this.mshang = mshang;
    }

    public int getMshang() {
        return mshang;
    }

    public void setMshang(int mshang) {
        this.mshang = mshang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return getTenhang();
    }
}
