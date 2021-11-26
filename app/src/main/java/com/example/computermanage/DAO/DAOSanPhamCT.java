package com.example.computermanage.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.SanPhamChiTiet;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;
import java.util.List;

public class DAOSanPhamCT {
    CreateSQL createSQL;

    public DAOSanPhamCT(Context context) {
        createSQL = new CreateSQL(context);
    }

    public long insertSanPhamCT(SanPhamChiTiet sanPhamChiTiet) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SanPhamChiTiet.COL_MSSP, sanPhamChiTiet.getMssp());
        values.put(SanPhamChiTiet.COL_CARDMH, sanPhamChiTiet.getCardmh());
        values.put(SanPhamChiTiet.COL_CPU, sanPhamChiTiet.getCpu());
        values.put(SanPhamChiTiet.COL_MANHINH, sanPhamChiTiet.getManhinh());
        values.put(SanPhamChiTiet.COL_OCUNG, sanPhamChiTiet.getOcung());
        values.put(SanPhamChiTiet.COL_HEDIEUHANH, sanPhamChiTiet.getHedieuhanh());
        values.put(SanPhamChiTiet.COL_PIN, sanPhamChiTiet.getPin());
        values.put(SanPhamChiTiet.COL_RAM, sanPhamChiTiet.getRam());
        values.put(SanPhamChiTiet.COL_TRONGLUONG, sanPhamChiTiet.getTrongluong());
        long kq = database.insert(SanPhamChiTiet.TABLE_SPCT, null, values);
        return kq;
    }

    public int updateSanPhamCT(SanPhamChiTiet sanPhamChiTiet) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SanPhamChiTiet.COL_MSSP, sanPhamChiTiet.getMssp());
        values.put(SanPhamChiTiet.COL_CARDMH, sanPhamChiTiet.getCardmh());
        values.put(SanPhamChiTiet.COL_CPU, sanPhamChiTiet.getCpu());
        values.put(SanPhamChiTiet.COL_MANHINH, sanPhamChiTiet.getManhinh());
        values.put(SanPhamChiTiet.COL_OCUNG, sanPhamChiTiet.getOcung());
        values.put(SanPhamChiTiet.COL_HEDIEUHANH, sanPhamChiTiet.getHedieuhanh());
        values.put(SanPhamChiTiet.COL_PIN, sanPhamChiTiet.getPin());
        values.put(SanPhamChiTiet.COL_RAM, sanPhamChiTiet.getRam());
        values.put(SanPhamChiTiet.COL_TRONGLUONG, sanPhamChiTiet.getTrongluong());
        int kq = database.update(SanPhamChiTiet.TABLE_SPCT,  values,"msspct =? ",new String[]{String.valueOf(sanPhamChiTiet.getMsspct())});
        return kq;
    }

    public int deleteSanPhamCT(String id) {
        SQLiteDatabase database = createSQL.getWritableDatabase();
        int kq = database.delete(SanPhamChiTiet.TABLE_SPCT, "msspct =? ", new String[]{id});
        return kq;
    }

    public ArrayList<SanPhamChiTiet> getAllDK(String sql, String... a) {
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<SanPhamChiTiet> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int msspct = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_MSSPCT)));
            String mssp = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_MSSP));
            String cpu = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_CPU));
            String ram = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_RAM));
            String ocung = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_OCUNG));
            String hedh = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_HEDIEUHANH));
            String manhinh = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_MANHINH));
            String cardmh = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_CARDMH));
            String pin = cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_PIN));
            Float trongluong = Float.valueOf(cursor.getString(cursor.getColumnIndex(SanPhamChiTiet.COL_TRONGLUONG)));

            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(msspct, mssp, cpu, ram, ocung, hedh,manhinh,cardmh,pin,trongluong);
            list.add(sanPhamChiTiet);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return list;
    }

    public ArrayList<SanPhamChiTiet> getAll() {
        String sql = "SELECT * FROM " + SanPhamChiTiet.TABLE_SPCT;
        return getAllDK(sql);
    }
    public int checkTTMaSP(String mssp) {
        String sql = "SELECT * FROM sanphamchitiet WHERE mssp =? ";
        List<SanPhamChiTiet> list = getAllDK(sql, mssp);
        return list.size() == 0 ? -1 : 1;
    }
    public SanPhamChiTiet getID(String id) {
        String sql = "SELECT * FROM sanphamchitiet WHERE mssp =? ";
        ArrayList<SanPhamChiTiet> list = getAllDK(sql, id);
        return list.get(0);
    }
}
