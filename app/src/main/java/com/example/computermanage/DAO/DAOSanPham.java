package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.Model.SanPham;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAOSanPham {
    CreateSQL createSQL;

    public DAOSanPham(Context context) {
        createSQL = new CreateSQL(context);
    }

    public long insertSanPham(SanPham sanPham) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SanPham.COL_MSSP, sanPham.getMssp());
        values.put(SanPham.COL_MSLSP, sanPham.getMslsp());
        values.put(SanPham.COL_TENSP, sanPham.getTensp());
        values.put(SanPham.COL_GIATIEN, sanPham.getGiatien());
        values.put(SanPham.COL_TINHTRANG, sanPham.getTinhtrang());
        values.put(SanPham.COL_HINHANH, sanPham.getHinhanh());
        values.put(SanPham.COL_MOTA, sanPham.getMota());
        long kq = database.insert(SanPham.TABLE_SANPHAM, null, values);
        return kq;
    }

    public int updateSanPham(SanPham sanPham) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SanPham.COL_MSLSP, sanPham.getMslsp());
        values.put(SanPham.COL_TENSP, sanPham.getTensp());
        values.put(SanPham.COL_GIATIEN, sanPham.getGiatien());
        values.put(SanPham.COL_TINHTRANG, sanPham.getTinhtrang());
        values.put(SanPham.COL_HINHANH, sanPham.getHinhanh());
        values.put(SanPham.COL_MOTA, sanPham.getMota());
        int kq = database.update(SanPham.TABLE_SANPHAM,  values,"mssp =? ",new String[]{sanPham.getMssp()});
        return kq;
    }

    public int deleteSanPham(String id) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(SanPham.TABLE_SANPHAM, "mssp =? ", new String[]{id});
        return kq;
    }

    public ArrayList<SanPham> getAllDK(String sql, String... a) {
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<SanPham> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String mssp = cursor.getString(cursor.getColumnIndex(SanPham.COL_MSSP));
            String mslsp = cursor.getString(cursor.getColumnIndex(SanPham.COL_MSLSP));
            String tensp = cursor.getString(cursor.getColumnIndex(SanPham.COL_TENSP));
            double giatien = Double.parseDouble(cursor.getString(cursor.getColumnIndex(SanPham.COL_GIATIEN)));
            int tinhtrang = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanPham.COL_TINHTRANG)));
            byte[] hinhanh = cursor.getBlob(cursor.getColumnIndex(SanPham.COL_HINHANH));
            String mota = cursor.getString(cursor.getColumnIndex(SanPham.COL_MOTA));
            SanPham sanPham = new SanPham(mssp, mslsp, tensp, giatien, tinhtrang, hinhanh,mota);
            list.add(sanPham);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return list;
    }

    public ArrayList<SanPham> getAll() {
        String sql = "SELECT * FROM " + SanPham.TABLE_SANPHAM;
        return getAllDK(sql);
    }

    public SanPham getID(String id) {
        String sql = "SELECT * FROM sanpham WHERE mssp =? ";
        ArrayList<SanPham> list = getAllDK(sql, id);
        return list.get(0);
    }
}
