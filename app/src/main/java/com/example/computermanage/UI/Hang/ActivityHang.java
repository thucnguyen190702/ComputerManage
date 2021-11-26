package com.example.computermanage.UI.Hang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.computermanage.Adapter.AdapterHang;
import com.example.computermanage.DAO.DAOHang;
import com.example.computermanage.Model.Hang;
import com.example.computermanage.R;

import java.util.ArrayList;

public class ActivityHang extends AppCompatActivity {
    RecyclerView rcv_hang;
    AdapterHang adapterHang;
    ArrayList<Hang> hangArrayList;
    DAOHang daoHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv_hang=findViewById(R.id.rcv_hang);
        hangArrayList=new ArrayList<>();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ActivityHang.this);
        rcv_hang.setLayoutManager(layoutManager);
        daoHang=new DAOHang(ActivityHang.this);
        hangArrayList=daoHang.getAll();
        adapterHang=new AdapterHang(ActivityHang.this,hangArrayList);
        rcv_hang.setAdapter(adapterHang);



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
                startActivity(new Intent(ActivityHang.this,ActivityAddHang.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}