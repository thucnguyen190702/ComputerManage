package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.Hang;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.SQL.CreateSQL;

import java.sql.Blob;
import java.util.ArrayList;

public class DAOHang {
    CreateSQL createSQL;
    public DAOHang(Context context){
        createSQL = new CreateSQL(context);
    }
    public long insertHang(Hang hang){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Hang.COL_TENHANG,hang.getTenhang());
        values.put(Hang.COL_HINHANH,hang.getHinhanh());
        long kq = database.insert(Hang.TABLE_HANG,null,values);
        return kq;
    }
    public int updateHang(Hang hang){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Hang.COL_TENHANG,hang.getTenhang());
        values.put(Hang.COL_HINHANH,hang.getHinhanh());
        int kq = database.update(Hang.TABLE_HANG,values,"mshang =? ",new String[]{String.valueOf(hang.getMshang())});
        return kq;
    }
    public int deleteHang(String ma){
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(Hang.TABLE_HANG,"mshang =? ",new String[]{ma});
        return kq;
    }
    public ArrayList<Hang> getAllDK(String sql ,String... a){
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<Hang> listHang = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int mshang = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Hang.COL_MSHANG)));
            String tenhang = cursor.getString(cursor.getColumnIndex(Hang.COL_TENHANG));
            byte[] hinhanh = cursor.getBlob(cursor.getColumnIndex(Hang.COL_HINHANH));
            Hang hang = new Hang(mshang,tenhang,hinhanh);
            listHang.add(hang);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return listHang;
    }
    public ArrayList<Hang> getAll(){
        String sql = "SELECT * FROM " + Hang.TABLE_HANG;
        return getAllDK(sql);
    }
    public Hang getID(String id){
        String sql = "SELECT * FROM hang WHERE mshang =? ";
        ArrayList<Hang> list = getAllDK(sql,id);
        return list.get(0);
    }
    public int checkMaHang(String ma){
        String sql = "SELECT * FROM hang WHERE mahang =? ";
        ArrayList<Hang> listH = getAllDK(sql,ma);
        return listH.size() ==0 ? 1:-1;
    }


}
