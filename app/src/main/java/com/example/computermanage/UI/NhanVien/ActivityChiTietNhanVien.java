package com.example.computermanage.UI.NhanVien;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.computermanage.DAO.DAONhanVien;
import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.R;

import java.util.Random;

public class ActivityChiTietNhanVien extends AppCompatActivity {
    TextView tv_tennhanvienCT, tv_passnhanvienCT, tv_sdtnhanvienCT, tv_emailnhanvienCT;
    ImageView img_anhChiTietNhanVien;
    DAONhanVien daoNhanVien;
    NhanVien nhanVien;
    String manv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nhan_vien);
        tv_tennhanvienCT = findViewById(R.id.tv_tennhanvienCT);
        tv_passnhanvienCT = findViewById(R.id.tv_passnhanvienCT);
        tv_sdtnhanvienCT = findViewById(R.id.tv_sdtnhanvienCT);
        tv_emailnhanvienCT = findViewById(R.id.tv_emailnhanvienCT);
        img_anhChiTietNhanVien = findViewById(R.id.img_anhChiTietNhanVien);
        getSupportActionBar().setTitle("Chi Tiết Nhân Viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        daoNhanVien = new DAONhanVien(this);

        Intent intent = getIntent();
        manv = intent.getStringExtra("manv");
        nhanVien = daoNhanVien.getID(manv);

        tv_tennhanvienCT.setText(nhanVien.getHoten());
        tv_passnhanvienCT.setText(nhanVien.getPassword());
        tv_sdtnhanvienCT.setText(nhanVien.getSdt());
        tv_emailnhanvienCT.setText(nhanVien.getEmail());


        if (nhanVien.getHinhanh() == null) {
            TextDrawable textDrawable = TextDrawable.builder().beginConfig().width(120).height(120).endConfig().buildRect(nhanVien.getHoten().substring(0, 1).toUpperCase(), getRandomColor());
            img_anhChiTietNhanVien.setImageDrawable(textDrawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getHinhanh(), 0, nhanVien.getHinhanh().length);
            img_anhChiTietNhanVien.setImageBitmap(bitmap);
        }
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
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
                Intent intent = new Intent(ActivityChiTietNhanVien.this, ActivityEditNhanVien.class);
                intent.putExtra("manv",manv );
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}