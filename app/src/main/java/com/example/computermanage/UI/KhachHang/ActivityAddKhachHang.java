package com.example.computermanage.UI.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computermanage.DAO.DAOKhachHang;
import com.example.computermanage.Model.KhachHang;
import com.example.computermanage.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ActivityAddKhachHang extends AppCompatActivity {
    TextInputEditText ed_maKhachHang,ed_tenKhachHang,ed_sdtKhachHang,ed_diaChiKhachHang;
    RadioButton rdo_nam, rdo_nu;
    RadioGroup rdg_gioiTinh;
    DAOKhachHang daoKhachHang;
    ArrayList<KhachHang> listKH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khach_hang);
        addControl();
    }
    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm khách hàng");
        ed_maKhachHang = findViewById(R.id.ed_maKhachHang);
        ed_tenKhachHang = findViewById(R.id.ed_tenKhachHang);
        ed_sdtKhachHang = findViewById(R.id.ed_sdtKhachHang);
        ed_diaChiKhachHang = findViewById(R.id.ed_diaChiKhachHang);
        rdg_gioiTinh = findViewById(R.id.rdg_gioiTinh);
        rdo_nam = findViewById(R.id.rdo_nam);
        rdo_nu = findViewById(R.id.rdo_nu);
        daoKhachHang = new DAOKhachHang(this);
        listKH = new ArrayList<>();
        rdo_nam.setChecked(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }
    boolean check(){
        if (ed_maKhachHang.getText().toString().isEmpty()
                || ed_tenKhachHang.getText().toString().isEmpty()
                || ed_diaChiKhachHang.getText().toString().isEmpty()
                || ed_sdtKhachHang.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_reset:
                ed_maKhachHang.setText("");
                ed_tenKhachHang.setText("");
                ed_sdtKhachHang.setText("");
                ed_diaChiKhachHang.setText("");
                rdo_nam.setChecked(true);
                return true;
            case R.id.menu_save:
                if (check()){
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMskh(ed_maKhachHang.getText().toString());
                    khachHang.setHoten(ed_tenKhachHang.getText().toString());
                    khachHang.setSdt(ed_sdtKhachHang.getText().toString());
                    khachHang.setDiachi(ed_diaChiKhachHang.getText().toString());
                    int checkrdo = rdg_gioiTinh.getCheckedRadioButtonId();
                    if (checkrdo == R.id.rdo_nam){
                        khachHang.setGioitinh(0);
                    }else if (checkrdo == R.id.rdo_nu){
                        khachHang.setGioitinh(1);
                    }
                    long kq = daoKhachHang.insertKhachHang(khachHang);
                    if (kq>0){
                        listKH.clear();
                        listKH.addAll(daoKhachHang.getAll());
                        startActivity(new Intent(ActivityAddKhachHang.this,ActivityKhachHang.class));
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}