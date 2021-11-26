package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.HoaDon;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAOHoaDon {
    CreateSQL createSQL;
    public DAOHoaDon(Context context){
        createSQL = new CreateSQL(context);
    }
    public long insertHoaDon(HoaDon hoaDon){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HoaDon.COL_MSHD,hoaDon.getMshd());
        values.put(HoaDon.COL_MSKH,hoaDon.getMskh());
        values.put(HoaDon.COL_MSNV,hoaDon.getMsnv());
        values.put(HoaDon.COL_NGAYMUA,hoaDon.getNgaymua());
        values.put(HoaDon.COL_TRANGTHAI,hoaDon.getTrangthai());
        long kq = database.insert(HoaDon.TABLE_HOADON,null,values);
        return kq;
    }
    public int updateHoaDon(HoaDon hoaDon){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HoaDon.COL_MSHD,hoaDon.getMshd());
        values.put(HoaDon.COL_MSKH,hoaDon.getMskh());
        values.put(HoaDon.COL_MSNV,hoaDon.getMsnv());
        values.put(HoaDon.COL_NGAYMUA,hoaDon.getNgaymua());
        values.put(HoaDon.COL_TRANGTHAI,hoaDon.getTrangthai());
        int kq = database.update(HoaDon.TABLE_HOADON,values,"mshd =? ",new String[]{String.valueOf(hoaDon.getMshd())});
        return kq;
    }
    public int deleteHoaDon(String ma){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(HoaDon.TABLE_HOADON,"mshd =? ",new String[]{ma});
        return kq;
    }
    public ArrayList<HoaDon> getAllDK(String sql ,String... a){
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<HoaDon> listHoadon = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String mshd = cursor.getString(cursor.getColumnIndex(HoaDon.COL_MSHD));
            String mskh = cursor.getString(cursor.getColumnIndex(HoaDon.COL_MSKH));
            String msnv = cursor.getString(cursor.getColumnIndex(HoaDon.COL_MSNV));
            String ngaymua = cursor.getString(cursor.getColumnIndex(HoaDon.COL_NGAYMUA));
            String trangthai = cursor.getString(cursor.getColumnIndex(HoaDon.COL_TRANGTHAI));
            HoaDon hoaDon = new HoaDon(mshd,mskh,msnv,ngaymua,trangthai);
            listHoadon.add(hoaDon);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return listHoadon;
    }
    public ArrayList<HoaDon> getAll(){
        String sql = "SELECT * FROM " + HoaDon.TABLE_HOADON;
        return getAllDK(sql);
    }
    public HoaDon getID(String id){
        String sql = "SELECT * FROM hoadon WHERE mshd =? ";
        ArrayList<HoaDon> list = getAllDK(sql,id);
        return list.get(0);
    }
}
