package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAOLoaiSanPham {
    CreateSQL createSQL;
    public DAOLoaiSanPham(Context context){
        createSQL = new CreateSQL(context);
    }
    public long insertLoaiSanPham(LoaiSanPham loaiSanPham){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LoaiSanPham.COL_MSLSP,loaiSanPham.getMslsp());
        values.put(LoaiSanPham.COL_MSHANG,loaiSanPham.getMshang());
        values.put(LoaiSanPham.COL_TENLSP,loaiSanPham.getTenlsp());
        values.put(LoaiSanPham.COL_HINHANH,loaiSanPham.getHinhanh());
        long kq = database.insert(LoaiSanPham.TABLE_LOAISANPHAM,null,values);
        return kq;
    }
    public int updateLoaiSanPham(LoaiSanPham loaiSanPham){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LoaiSanPham.COL_MSHANG,loaiSanPham.getMshang());
        values.put(LoaiSanPham.COL_TENLSP,loaiSanPham.getTenlsp());
        values.put(LoaiSanPham.COL_HINHANH,loaiSanPham.getHinhanh());
        int kq = database.update(LoaiSanPham.TABLE_LOAISANPHAM,values,"mslsp =? ",new String[]{loaiSanPham.getMslsp()});
        return kq;
    }
    public int deleteLoaiSanPham(String id){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(LoaiSanPham.TABLE_LOAISANPHAM,"mslsp =? ",new String[]{id});
        return kq;
    }
    public ArrayList<LoaiSanPham> getAllDK(String sql , String... a){
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<LoaiSanPham> listLSP = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String mslsp = cursor.getString(cursor.getColumnIndex(LoaiSanPham.COL_MSLSP));
            int mshang = Integer.parseInt(cursor.getString(cursor.getColumnIndex(LoaiSanPham.COL_MSHANG)));
            String tenlsp = cursor.getString(cursor.getColumnIndex(LoaiSanPham.COL_TENLSP));
            byte[] hinhanh = cursor.getBlob(cursor.getColumnIndex(LoaiSanPham.COL_HINHANH));
            LoaiSanPham loaiSanPham = new LoaiSanPham(mslsp,mshang,tenlsp,hinhanh);
            listLSP.add(loaiSanPham);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return listLSP;
    }
    public ArrayList<LoaiSanPham> getAll(){
        String sql = "SELECT * FROM "+LoaiSanPham.TABLE_LOAISANPHAM;
        return getAllDK(sql);
    }
    public LoaiSanPham getID(String id){
        String sql = "SELECT * FROM loaisanpham WHERE mslsp=? ";
        ArrayList<LoaiSanPham> list = getAllDK(sql,id);
        return list.get(0);
    }
}
