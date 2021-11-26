package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.HoaDonChiTiet;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAOHoaDonCT {
    CreateSQL createSQL;

    public DAOHoaDonCT(Context context) {
        createSQL = new CreateSQL(context);
    }

    public long insertHoaDonCT(HoaDonChiTiet hoaDonChiTiet) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HoaDonChiTiet.COL_MSHD, hoaDonChiTiet.getMshd());
        values.put(HoaDonChiTiet.COL_MSSP, hoaDonChiTiet.getMssp());
        values.put(HoaDonChiTiet.COL_DONGIA, hoaDonChiTiet.getDongia());
        values.put(HoaDonChiTiet.COL_BAOHANH, hoaDonChiTiet.getBaohanh());
        values.put(HoaDonChiTiet.COL_GIAMGIA, hoaDonChiTiet.getGiamgia());
        values.put(HoaDonChiTiet.COL_SOLUONG, hoaDonChiTiet.getSoluong());
        long kq = database.insert(HoaDonChiTiet.TABLE_HOADONCT, null, values);
        return kq;
    }

    public int updateHoaDonCT(HoaDonChiTiet hoaDonChiTiet) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HoaDonChiTiet.COL_MSHD, hoaDonChiTiet.getMshd());
        values.put(HoaDonChiTiet.COL_MSSP, hoaDonChiTiet.getMssp());
        values.put(HoaDonChiTiet.COL_DONGIA, hoaDonChiTiet.getDongia());
        values.put(HoaDonChiTiet.COL_BAOHANH, hoaDonChiTiet.getBaohanh());
        values.put(HoaDonChiTiet.COL_GIAMGIA, hoaDonChiTiet.getGiamgia());
        values.put(HoaDonChiTiet.COL_SOLUONG, hoaDonChiTiet.getSoluong());
        int kq = database.update(HoaDonChiTiet.TABLE_HOADONCT, values, "mshdct =? ", new String[]{String.valueOf(hoaDonChiTiet.getMshdct())});
        return kq;
    }

    public int deleteHoaDonCT(String ma) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(HoaDonChiTiet.TABLE_HOADONCT, "mshdct =? ", new String[]{ma});
        return kq;
    }

    public ArrayList<HoaDonChiTiet> getAllDK(String sql, String... a) {
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<HoaDonChiTiet> listHoadonCT = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int mshdct = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_MSHDCT)));
            String mshd = cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_MSHD));
            String mssp = cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_MSSP));
            int soluong = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_SOLUONG)));
            double dongia = Double.parseDouble((cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_DONGIA))));
            int baohanh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_DONGIA)));
            int giamgia = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoaDonChiTiet.COL_GIAMGIA)));

            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(mshdct, mshd, mssp, soluong, dongia, baohanh, giamgia);
            listHoadonCT.add(hoaDonChiTiet);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return listHoadonCT;
    }

    public ArrayList<HoaDonChiTiet> getAll() {
        String sql = "SELECT * FROM " + HoaDonChiTiet.TABLE_HOADONCT;
        return getAllDK(sql);
    }

    public HoaDonChiTiet getID(String id) {
        String sql = "SELECT * FROM hoadonchitiet WHERE mshdct =? ";
        ArrayList<HoaDonChiTiet> list = getAllDK(sql, id);
        return list.get(0);
    }
}
