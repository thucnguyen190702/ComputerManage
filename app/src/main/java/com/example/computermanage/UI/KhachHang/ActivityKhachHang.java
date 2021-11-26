package com.example.computermanage.UI.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computermanage.Adapter.AdapterKhachHang;
import com.example.computermanage.DAO.DAOKhachHang;
import com.example.computermanage.Model.KhachHang;
import com.example.computermanage.R;

import java.util.ArrayList;

public class ActivityKhachHang extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterKhachHang adapterKhachHang;
    DAOKhachHang daoKhachHang;
    ArrayList<KhachHang> listKH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        addControl();
        addEvent();
    }

    private void addEvent() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listKH = daoKhachHang.getAll();
        adapterKhachHang = new AdapterKhachHang(ActivityKhachHang.this,listKH);
        recyclerView.setAdapter(adapterKhachHang);
    }

    private void addControl() {
        getSupportActionBar().setTitle("Danh sách khách hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rcv_khachhang);
        daoKhachHang = new DAOKhachHang(this);
        listKH = new ArrayList<>();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                startActivity(new Intent(ActivityKhachHang.this,ActivityAddKhachHang.class));
        }
        return super.onOptionsItemSelected(item);
    }
}