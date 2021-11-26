package com.example.computermanage.UI.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computermanage.DAO.DAOKhachHang;
import com.example.computermanage.Model.KhachHang;
import com.example.computermanage.R;

public class ActivityChiTietKhachHang extends AppCompatActivity {
    DAOKhachHang daoKhachHang;
    KhachHang khachHang;
    TextView tv_tenKhachHangCT,tv_sdtKhachHangCT,tv_gtKhachHangCT,tv_diachiKhachHangCT;
    String ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khach_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết khách hàng");
        tv_diachiKhachHangCT = findViewById(R.id.tv_diachiKhachHangCT);
        tv_tenKhachHangCT = findViewById(R.id.tv_tenKhachHangCT);
        tv_sdtKhachHangCT = findViewById(R.id.tv_sdtKhachHangCT);
        tv_gtKhachHangCT = findViewById(R.id.tv_gtKhachHangCT);
        daoKhachHang = new DAOKhachHang(this);

        Intent intent = getIntent();
        ma = intent.getStringExtra("makh");
        khachHang = daoKhachHang.getID(ma);
        tv_tenKhachHangCT.setText(khachHang.getHoten());
        tv_sdtKhachHangCT.setText(khachHang.getSdt());
        tv_diachiKhachHangCT.setText(khachHang.getDiachi());
        if (khachHang.getGioitinh()==0){
            tv_gtKhachHangCT.setText("Nam");
        }else if (khachHang.getGioitinh()==1){
            tv_gtKhachHangCT.setText("Nữ");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit,menu);
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
            case R.id.menu_edit:
                Intent intent = new Intent(ActivityChiTietKhachHang.this,ActivityEditKhachHang.class);
                intent.putExtra("ma",ma);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}