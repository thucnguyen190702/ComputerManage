package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.KhachHang;
import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAOKhachHang {
    CreateSQL createSQL;
    public DAOKhachHang(Context context) {
        createSQL = new CreateSQL(context);
    }
    public long insertKhachHang(KhachHang khachHang){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KhachHang.COL_MSKH,khachHang.getMskh());
        values.put(KhachHang.COL_HOTEN,khachHang.getHoten());
        values.put(KhachHang.COL_SDT,khachHang.getSdt());
        values.put(KhachHang.COL_GIOITINH,khachHang.getGioitinh());
        values.put(KhachHang.COL_DIACHI,khachHang.getDiachi());
        long kq = database.insert(KhachHang.TABLE_KHACHHANG,null,values);
        return kq;
    }
    public int updateKhachHang(KhachHang khachHang){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KhachHang.COL_HOTEN,khachHang.getHoten());
        values.put(KhachHang.COL_SDT,khachHang.getSdt());
        values.put(KhachHang.COL_GIOITINH,khachHang.getGioitinh());
        values.put(KhachHang.COL_DIACHI,khachHang.getDiachi());
        int kq = database.update(KhachHang.TABLE_KHACHHANG,values,"mskh =? ",new String[]{khachHang.getMskh()});
        return kq;
    }
    public int deleteKhachHang(String id){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(KhachHang.TABLE_KHACHHANG,"mskh =? ",new String[]{id});
        return kq;
    }
    public ArrayList<KhachHang> getAllDK(String sql,String... a){
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<KhachHang> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String mskh = cursor.getString(cursor.getColumnIndex(KhachHang.COL_MSKH));
            String hoten = cursor.getString(cursor.getColumnIndex(KhachHang.COL_HOTEN));
            String sdt = cursor.getString(cursor.getColumnIndex(KhachHang.COL_SDT));
            int gioitinh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KhachHang.COL_GIOITINH)));
            String diachi = cursor.getString(cursor.getColumnIndex(KhachHang.COL_DIACHI));
            KhachHang khachHang = new KhachHang(mskh,hoten,sdt,gioitinh,diachi);
            list.add(khachHang);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return list;
    }
    public ArrayList<KhachHang> getAll(){
        String sql = "SELECT * FROM "+KhachHang.TABLE_KHACHHANG;
        return getAllDK(sql);
    }
    public KhachHang getID(String id){
        String sql = "SELECT * FROM khachhang WHERE mskh =? ";
        ArrayList<KhachHang> list = getAllDK(sql,id);
        return list.get(0);
    }
}
