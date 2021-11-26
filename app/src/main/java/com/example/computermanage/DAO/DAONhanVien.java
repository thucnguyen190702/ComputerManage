package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.Admin;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAONhanVien {
    CreateSQL createSQL;
    public DAONhanVien(Context context) {
        createSQL = new CreateSQL(context);
    }
    public long insertNhanVien(NhanVien nhanVien){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NhanVien.COL_MSNV,nhanVien.getMsnv());
        values.put(NhanVien.COL_PASSWORD,nhanVien.getPassword());
        values.put(NhanVien.COL_HOTEN,nhanVien.getHoten());
        values.put(NhanVien.COL_SDT,nhanVien.getSdt());
        values.put(NhanVien.COL_EMAIL,nhanVien.getEmail());
        values.put(NhanVien.COL_HINHANH,nhanVien.getHinhanh());
        long kq = database.insert(NhanVien.TABLE_NHANVIEN,null,values);
        return kq;
    }
    public int updateNhanVien(NhanVien nhanVien){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
                values.put(NhanVien.COL_PASSWORD,nhanVien.getPassword());
        values.put(NhanVien.COL_HOTEN,nhanVien.getHoten());
        values.put(NhanVien.COL_SDT,nhanVien.getSdt());
        values.put(NhanVien.COL_EMAIL,nhanVien.getEmail());
        values.put(NhanVien.COL_HINHANH,nhanVien.getHinhanh());
        int kq = database.update(NhanVien.TABLE_NHANVIEN,values,"msnv =? ",new String[]{nhanVien.getMsnv()});
        return kq;
    }
    public int deleteNhanVien(String id){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(NhanVien.TABLE_NHANVIEN,"msnv =? ",new String[]{id});
        return kq;
    }
    public ArrayList<NhanVien> getAllDK(String sql,String... a){
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<NhanVien> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String msnv = cursor.getString(cursor.getColumnIndex(NhanVien.COL_MSNV));
            String pass = cursor.getString(cursor.getColumnIndex(NhanVien.COL_PASSWORD));
            String hoten = cursor.getString(cursor.getColumnIndex(NhanVien.COL_HOTEN));
            String sdt = cursor.getString(cursor.getColumnIndex(NhanVien.COL_SDT));
            String email = cursor.getString(cursor.getColumnIndex(NhanVien.COL_EMAIL));
            byte[] hinhanh = cursor.getBlob(cursor.getColumnIndex(NhanVien.COL_HINHANH));
            NhanVien nhanVien = new NhanVien(msnv,pass,hoten,sdt,email,hinhanh);
            list.add(nhanVien);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return list;
    }
    public ArrayList<NhanVien> getAll(){
        String sql = "SELECT * FROM "+NhanVien.TABLE_NHANVIEN;
        return getAllDK(sql);
    }
    public NhanVien getID(String id){
        String sql = "SELECT * FROM nhanvien WHERE msnv =? ";
        ArrayList<NhanVien> list = getAllDK(sql,id);
        return list.get(0);
    }
    public int checkLoginNV(String user , String pass){
        String sql = "SELECT * FROM nhanvien WHERE msnv =? AND password =? ";
        ArrayList<NhanVien> list = getAllDK(sql,user,pass);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}
