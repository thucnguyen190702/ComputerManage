package com.example.computermanage.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computermanage.Model.Admin;
import com.example.computermanage.SQL.CreateSQL;

import java.util.ArrayList;

public class DAOAdmin {
    CreateSQL createSQL;
    public DAOAdmin(Context context){
        createSQL = new CreateSQL(context);
    }
    public ArrayList<Admin> getAllDK(String sql,String... a){
        SQLiteDatabase database = createSQL.getReadableDatabase();
        ArrayList<Admin> listAD = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,a);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String user = cursor.getString(0);
            String password = cursor.getString(1);
            Admin admin = new Admin(user,password);
            listAD.add(admin);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return listAD;
    }
    public int checkLogin(String user , String password){
        String sql = "SELECT * FROM admin WHERE user =? AND password =? ";
        ArrayList<Admin> list = getAllDK(sql,user,password);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}
