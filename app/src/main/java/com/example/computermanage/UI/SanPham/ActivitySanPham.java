package com.example.computermanage.UI.SanPham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.computermanage.Adapter.AdapterSanPham;
import com.example.computermanage.DAO.DAOSanPham;
import com.example.computermanage.Model.SanPham;
import com.example.computermanage.R;

import java.util.ArrayList;

public class ActivitySanPham extends AppCompatActivity {
    RecyclerView rcv_SanPham;
    DAOSanPham daoSanPham;
    ArrayList<SanPham> listSP;
    AdapterSanPham adapterSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        addControl();
        addDataRecycleView();
    }

    private void addDataRecycleView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivitySanPham.this);
        rcv_SanPham.setLayoutManager(layoutManager);
        listSP = daoSanPham.getAll();
        adapterSanPham = new AdapterSanPham(ActivitySanPham.this,listSP);
        rcv_SanPham.setAdapter(adapterSanPham);
    }

    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách sản phẩm");
        rcv_SanPham = findViewById(R.id.rcv_SanPham);
        daoSanPham = new DAOSanPham(this);
        listSP = new ArrayList<>();
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
                startActivity(new Intent(ActivitySanPham.this,ActivityAddSanPham.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}