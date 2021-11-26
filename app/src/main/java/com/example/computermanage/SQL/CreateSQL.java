package com.example.computermanage.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.computermanage.Model.Admin;

public class CreateSQL extends SQLiteOpenHelper {
    public CreateSQL(@Nullable Context context) {
        super(context, "computermanage.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table admin
        String CREATE_TABLE_ADMIN = "CREATE TABLE admin(" +
                "user TEXT UNIQUE PRIMARY KEY NOT NULL," +
                "password TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_ADMIN);
        // insert u p admin
        String ADD_VALUE_ADMIN = "INSERT INTO admin(user,password) VALUES('admin','admin')";
        db.execSQL(ADD_VALUE_ADMIN);
        //create table nhanvien
        String CREATE_TABLE_NHANVIEN = "CREATE TABLE nhanvien(" +
                "msnv TEXT UNIQUE PRIMARY KEY NOT NULL," +
                "password TEXT NOT NULL," +
                "hoten TEXT NOT NULL," +
                "sdt TEXT NOT NULL," +
                "email TEXT," +
                "hinhanh BLOB)";
        db.execSQL(CREATE_TABLE_NHANVIEN);
        //create table khachhang
        String CREATE_TABLE_KHACHHANG = "CREATE TABLE khachhang(" +
                "mskh TEXT UNIQUE PRIMARY KEY NOT NULL," +
                "hoten TEXT NOT NULL," +
                "sdt TEXT NOT NULL," +
                "gioitinh TEXT NOT NULL," +
                "diachi TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_KHACHHANG);
        //create table hang
        String CREATE_TABLE_HANG = "CREATE TABLE hang(" +
                "mshang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenhang TEXT NOT NULL , hinhanh BLOB)";
        db.execSQL(CREATE_TABLE_HANG);
        //create table loaisanpham
        String CREATE_TABLE_LOAISANPHAM = "CREATE TABLE loaisanpham(" +
                "mslsp TEXT UNIQUE PRIMARY KEY NOT NULL," +
                "mshang INTEGER NOT NULL REFERENCES hang(mshang) ON DELETE CASCADE ON UPDATE CASCADE," +
                "tenlsp TEXT NOT NULL," +
                "hinhanh BLOB)";
        db.execSQL(CREATE_TABLE_LOAISANPHAM);
        String CREATE_TABLE_SANPHAM = "CREATE TABLE sanpham(" +
                "mssp TEXT PRIMARY KEY NOT NULL," +
                "mslsp TEXT NOT NULL REFERENCES loaisanpham(mslsp) ON DELETE CASCADE ON UPDATE CASCADE," +
                "tensp TEXT NOT NULL," +
                "giatien DECIMAL(16,0) NOT NULL," +
                "tinhtrang INTEGER NOT NULL," +
                "hinhanh BLOB," +
                "mota TEXT)";
        db.execSQL(CREATE_TABLE_SANPHAM);
        //create table sanphamchitiet
        String CREATE_TABLE_SPCHITIET = "CREATE TABLE sanphamchitiet(" +
                "msspct INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mssp TEXT NOT NULL REFERENCES sanpham(mssp) ON DELETE CASCADE ON UPDATE CASCADE ," +
                "cpu TEXT," +
                "ram TEXT," +
                "ocung TEXT," +
                "hedieuhanh TEXT," +
                "manhinh TEXT," +
                "cardmh TEXT," +
                "pin TEXT," +
                "trongluong FLOAT)";
        db.execSQL(CREATE_TABLE_SPCHITIET);
        //create table hoadon
        String CREATE_TABLE_HOADON = "CREATE TABLE hoadon(" +
                "mshd TEXT UNIQUE PRIMARY KEY NOT NULL," +
                "msnv TEXT NOT NULL REFERENCES nhanvien(msnv) ON DELETE CASCADE ON UPDATE CASCADE," +
                "mskh TEXT NOT NULL REFERENCES khachhang(mskh) ON DELETE CASCADE ON UPDATE CASCADE," +
                "ngaymua DATE NOT NULL," +
                "trangthai TEXT)";
        db.execSQL(CREATE_TABLE_HOADON);
        //create table hoadonchitiet
        String CREATE_TABLE_HDCHITIET = "CREATE TABLE hoadonchitiet(" +
                "mshdct INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "mshd TEXT NOT NULL REFERENCES hoadon(mshd) ON DELETE CASCADE ON UPDATE CASCADE," +
                "mssp TEXT NOT NULL REFERENCES sanpham(mssp) ON DELETE CASCADE ON UPDATE CASCADE," +
                "soluong INTEGER NOT NULL," +
                "dongia DOUBLE NOT NULL," +
                "baohanh INTEGER," +
                "giamgia INTEGER)";
        db.execSQL(CREATE_TABLE_HDCHITIET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_table_admin = "DROP TABLE IF EXISTS "+ Admin.TABLE_ADMIN;
        db.execSQL(drop_table_admin);
    }
}
