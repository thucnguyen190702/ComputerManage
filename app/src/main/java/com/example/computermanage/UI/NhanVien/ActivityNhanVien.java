package com.example.computermanage.UI.NhanVien;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computermanage.Adapter.AdapterNhanVien;
import com.example.computermanage.DAO.DAONhanVien;
import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.R;

import java.util.ArrayList;

public class ActivityNhanVien extends AppCompatActivity {
    RecyclerView rcv_nhanvien;
    AdapterNhanVien adapterNhanVien;
    ArrayList<NhanVien> listNhanVien;
    DAONhanVien daoNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        getSupportActionBar().setTitle("Danh sách nhân viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv_nhanvien = findViewById(R.id.rcv_nhanvien);
        listNhanVien = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityNhanVien.this);
        rcv_nhanvien.setLayoutManager(layoutManager);
        daoNhanVien = new DAONhanVien(ActivityNhanVien.this);
        listNhanVien = daoNhanVien.getAll();
        adapterNhanVien = new AdapterNhanVien(ActivityNhanVien.this, listNhanVien);
        rcv_nhanvien.setAdapter(adapterNhanVien);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(ActivityNhanVien.this, ActivityAddNhanVien.class));

        }
        return super.onOptionsItemSelected(item);
    }
}