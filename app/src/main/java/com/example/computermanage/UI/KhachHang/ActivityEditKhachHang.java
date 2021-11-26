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

public class ActivityEditKhachHang extends AppCompatActivity {
    TextInputEditText ed_editMaKhachHang,ed_editTenKhachHang,ed_editSdtKhachHang,ed_editDiaChiKhachHang;
    RadioGroup rdg_gioitinh;
    RadioButton rdo_editNam,rdo_editNu;
    DAOKhachHang daoKhachHang;
    ArrayList<KhachHang> listKH;
    String ma;
    KhachHang khachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_khach_hang);
        addControl();
    }

    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa khách hàng");
        ed_editMaKhachHang = findViewById(R.id.ed_editMaKhachHang);
        ed_editTenKhachHang = findViewById(R.id.ed_editTenKhachHang);
        ed_editSdtKhachHang = findViewById(R.id.ed_editSdtKhachHang);
        ed_editDiaChiKhachHang = findViewById(R.id.ed_editDiaChiKhachHang);
        rdg_gioitinh = findViewById(R.id.rdg_gioitinh);
        rdo_editNam = findViewById(R.id.rdo_editNam);
        rdo_editNu = findViewById(R.id.rdo_editNu);
        daoKhachHang = new DAOKhachHang(this);
        listKH = new ArrayList<>();
        Intent intent = getIntent();
        ma = intent.getStringExtra("ma");
        khachHang = daoKhachHang.getID(ma);
        ed_editMaKhachHang.setText(ma);
        ed_editTenKhachHang.setText(khachHang.getHoten());
        ed_editSdtKhachHang.setText(khachHang.getSdt());
        ed_editDiaChiKhachHang.setText(khachHang.getDiachi());
        if (khachHang.getGioitinh()==0){
            rdo_editNam.setChecked(true);
        }else if (khachHang.getGioitinh()==1){
            rdo_editNu.setChecked(true);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done,menu);
        return true;
    }
    boolean check(){
        if (ed_editMaKhachHang.getText().toString().isEmpty()
                || ed_editTenKhachHang.getText().toString().isEmpty()
                || ed_editDiaChiKhachHang.getText().toString().isEmpty()
                || ed_editSdtKhachHang.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_done:
                if (check()){
                    khachHang.setHoten(ed_editTenKhachHang.getText().toString());
                    khachHang.setSdt(ed_editSdtKhachHang.getText().toString());
                    int rdog = rdg_gioitinh.getCheckedRadioButtonId();
                    if (rdog == R.id.rdo_editNam){
                        khachHang.setGioitinh(0);
                    }else if (rdog == R.id.rdo_editNu){
                        khachHang.setGioitinh(1);
                    }
                    khachHang.setDiachi(ed_editDiaChiKhachHang.getText().toString());
                    long result=daoKhachHang.updateKhachHang(khachHang);
                    if (result>0){
                        listKH.clear();
                        listKH.addAll(daoKhachHang.getAll());
//                    adapterHang.notifyDataSetChanged();
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityEditKhachHang.this, ActivityKhachHang.class));
                    }else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
        }
        return super.onOptionsItemSelected(item);
    }
}