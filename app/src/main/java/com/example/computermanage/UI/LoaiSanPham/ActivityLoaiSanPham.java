package com.example.computermanage.UI.LoaiSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computermanage.Adapter.AdapterLoaiSanPham;
import com.example.computermanage.DAO.DAOLoaiSanPham;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.R;

import java.util.ArrayList;

public class ActivityLoaiSanPham extends AppCompatActivity {
    RecyclerView rcv_loaisp;
    DAOLoaiSanPham daoLoaiSanPham;
    ArrayList<LoaiSanPham> listLSP;
    AdapterLoaiSanPham adapterLoaiSanPham;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);
        addControl();
        addEvent();
    }

    private void addEvent() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityLoaiSanPham.this);
        rcv_loaisp.setLayoutManager(layoutManager);
        listLSP = daoLoaiSanPham.getAll();
        adapterLoaiSanPham = new AdapterLoaiSanPham(ActivityLoaiSanPham.this,listLSP);
        rcv_loaisp.setAdapter(adapterLoaiSanPham);
    }

    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách loại sản phẩm");
        rcv_loaisp =findViewById(R.id.rcv_loaisp);
        daoLoaiSanPham = new DAOLoaiSanPham(this);
        listLSP = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add,menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                startActivity(new Intent(ActivityLoaiSanPham.this,ActivityAddLoaiSP.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
