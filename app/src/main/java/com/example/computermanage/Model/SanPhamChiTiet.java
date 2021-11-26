package com.example.computermanage.Model;

public class SanPhamChiTiet {
    private int msspct;
    private String mssp;
    private String cpu;
    private String ram;
    private String ocung;
    private String hedieuhanh;
    private String manhinh;
    private String cardmh;
    private String pin;
    private float trongluong;

    public static final String TABLE_SPCT = "sanphamchitiet";
    public static final String COL_MSSPCT = "msspct";
    public static final String COL_MSSP = "mssp";
    public static final String COL_CPU = "cpu";
    public static final String COL_RAM = "ram";
    public static final String COL_OCUNG = "ocung";
    public static final String COL_HEDIEUHANH = "hedieuhanh";
    public static final String COL_MANHINH = "manhinh";
    public static final String COL_CARDMH = "cardmh";
    public static final String COL_PIN = "pin";
    public static final String COL_TRONGLUONG = "trongluong";

    public SanPhamChiTiet() {
    }

    public SanPhamChiTiet(int msspct, String mssp, String cpu, String ram, String ocung, String hedieuhanh, String manhinh, String cardmh, String pin, float trongluong) {
        this.msspct = msspct;
        this.mssp = mssp;
        this.cpu = cpu;
        this.ram = ram;
        this.ocung = ocung;
        this.hedieuhanh = hedieuhanh;
        this.manhinh = manhinh;
        this.cardmh = cardmh;
        this.pin = pin;
        this.trongluong = trongluong;
    }

    public int getMsspct() {
        return msspct;
    }

    public void setMsspct(int msspct) {
        this.msspct = msspct;
    }

    public String getMssp() {
        return mssp;
    }

    public void setMssp(String mssp) {
        this.mssp = mssp;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getOcung() {
        return ocung;
    }

    public void setOcung(String ocung) {
        this.ocung = ocung;
    }

    public String getHedieuhanh() {
        return hedieuhanh;
    }

    public void setHedieuhanh(String hedieuhanh) {
        this.hedieuhanh = hedieuhanh;
    }

    public String getManhinh() {
        return manhinh;
    }

    public void setManhinh(String manhinh) {
        this.manhinh = manhinh;
    }

    public String getCardmh() {
        return cardmh;
    }

    public void setCardmh(String cardmh) {
        this.cardmh = cardmh;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public float getTrongluong() {
        return trongluong;
    }

    public void setTrongluong(float trongluong) {
        this.trongluong = trongluong;
    }
}
