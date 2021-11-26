package com.example.computermanage.UI.LoaiSanPham;

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
import com.example.computermanage.DAO.DAOHang;
import com.example.computermanage.DAO.DAOLoaiSanPham;
import com.example.computermanage.Model.Hang;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.R;

import java.util.Random;

public class ActivityChiTietLoaiSP extends AppCompatActivity {
    ImageView img_anhChiTietLoaiSP;
    TextView tv_chitietMaLoaiSP, tv_chitietTenLoaiSP, tv_chitietTenHangSP;
    String mslsp;
    DAOLoaiSanPham daoLoaiSanPham;
    DAOHang daoHang;
    LoaiSanPham loaiSanPham;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_sp);
        addControl();
        Intent intent = getIntent();
        mslsp = intent.getStringExtra("mslsp");
        loaiSanPham = daoLoaiSanPham.getID(mslsp);

        tv_chitietMaLoaiSP.setText(loaiSanPham.getMslsp());

        tv_chitietTenLoaiSP.setText(loaiSanPham.getTenlsp());

        Hang hang = daoHang.getID(String.valueOf(loaiSanPham.getMshang()));
        tv_chitietTenHangSP.setText(hang.getTenhang());

        if (loaiSanPham.getHinhanh() == null){
            TextDrawable textDrawable = TextDrawable.builder().beginConfig().width(120).height(120).endConfig().buildRect(loaiSanPham.getTenlsp().substring(0, 1).toUpperCase(), getRandomColor());
            img_anhChiTietLoaiSP.setImageDrawable(textDrawable);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(loaiSanPham.getHinhanh(), 0, loaiSanPham.getHinhanh().length);
            img_anhChiTietLoaiSP.setImageBitmap(bitmap);
        }
    }


    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết loại sản phẩm");
        img_anhChiTietLoaiSP = findViewById(R.id.img_anhChiTietLoaiSP);
        tv_chitietMaLoaiSP = findViewById(R.id.tv_chitietMaLoaiSP);
        tv_chitietTenLoaiSP = findViewById(R.id.tv_chitietTenLoaiSP);
        tv_chitietTenHangSP = findViewById(R.id.tv_chitietTenHangSP);
        daoLoaiSanPham = new DAOLoaiSanPham(this);
        daoHang = new DAOHang(this);
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
        switch (item.getItemId()){
            case R.id.home:
                this.finish();
                return true;
            case R.id.menu_edit:
                Intent intent = new Intent(ActivityChiTietLoaiSP.this,ActivityEditLoaiSP.class);
                intent.putExtra("malsp",mslsp);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
