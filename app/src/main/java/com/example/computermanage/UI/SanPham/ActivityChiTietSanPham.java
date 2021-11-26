package com.example.computermanage.UI.SanPham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.computermanage.DAO.DAOLoaiSanPham;
import com.example.computermanage.DAO.DAOSanPham;
import com.example.computermanage.DAO.DAOSanPhamCT;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.Model.SanPham;
import com.example.computermanage.Model.SanPhamChiTiet;
import com.example.computermanage.R;

import java.text.DecimalFormat;
import java.util.Random;

public class ActivityChiTietSanPham extends AppCompatActivity {
    ImageView img_anhChiTietSP;
    TextView tv_chitietTenSP, tv_chitietMaSP, tv_chitietLoaiSP, tv_chitietGiaSP, tv_chitietTinhTrangSP, tv_chitietCPU, tv_chitietRam, tv_chitietOcung, tv_chitietHDH, tv_chitietManHinh, tv_chitietCardMH, tv_chitietPin, tv_chitietTrongluong, tv_motaSP;
    DAOSanPham daoSanPham;
    DAOLoaiSanPham daoLoaiSanPham;
    DAOSanPhamCT daoSanPhamCT;
    SanPham sanPham;
    String mssp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        addControl();
        getData();
    }



    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");
        img_anhChiTietSP = findViewById(R.id.img_anhChiTietSP);
        tv_chitietTenSP = findViewById(R.id.tv_chitietTenSP);
        tv_chitietMaSP = findViewById(R.id.tv_chitietMaSP);
        tv_chitietLoaiSP = findViewById(R.id.tv_chitietLoaiSP);
        tv_chitietGiaSP = findViewById(R.id.tv_chitietGiaSP);
        tv_chitietTinhTrangSP = findViewById(R.id.tv_chitietTinhTrangSP);
        tv_chitietCPU = findViewById(R.id.tv_chitietCPU);
        tv_chitietRam = findViewById(R.id.tv_chitietRam);
        tv_chitietOcung = findViewById(R.id.tv_chitietOcung);
        tv_chitietHDH = findViewById(R.id.tv_chitietHDH);
        tv_chitietManHinh = findViewById(R.id.tv_chitietManHinh);
        tv_chitietCardMH = findViewById(R.id.tv_chitietCardMH);
        tv_chitietPin = findViewById(R.id.tv_chitietPin);
        tv_chitietTrongluong = findViewById(R.id.tv_chitietTrongluong);
        tv_motaSP = findViewById(R.id.tv_motaSP);
        daoSanPham = new DAOSanPham(this);
        daoLoaiSanPham = new DAOLoaiSanPham(this);
        daoSanPhamCT = new DAOSanPhamCT(this);
    }
    private void getData() {
        Intent intent = getIntent();
        mssp = intent.getStringExtra("mssp");
        SanPham sanPham = daoSanPham.getID(mssp);
        tv_chitietMaSP.setText(sanPham.getMssp());
        tv_chitietTenSP.setText(sanPham.getTensp());
        if (sanPham.getHinhanh() == null){
            TextDrawable textDrawable = TextDrawable.builder().beginConfig().width(120).height(120).endConfig().buildRect(sanPham.getTensp().substring(0, 1).toUpperCase(), getRandomColor());
            img_anhChiTietSP.setImageDrawable(textDrawable);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinhanh(), 0, sanPham.getHinhanh().length);
            img_anhChiTietSP.setImageBitmap(bitmap);
        }
        LoaiSanPham loaiSanPham = daoLoaiSanPham.getID(sanPham.getMslsp());
        tv_chitietLoaiSP.setText(loaiSanPham.getTenlsp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_chitietGiaSP.setText(decimalFormat.format(sanPham.getGiatien())+" VNĐ");
        if (sanPham.getTinhtrang() == 0){
            tv_chitietTinhTrangSP.setText("Cũ");
        }else if (sanPham.getTinhtrang() == 1){
            tv_chitietTinhTrangSP.setText("Like New 99%");
        }else if (sanPham.getTinhtrang() == 2){
            tv_chitietTinhTrangSP.setText("Mới");
        }
        tv_motaSP.setText(sanPham.getMota());
        if (daoSanPhamCT.checkTTMaSP(sanPham.getMssp())>0){
            SanPhamChiTiet sanPhamChiTiet = daoSanPhamCT.getID(sanPham.getMssp());
            tv_chitietCPU.setText(sanPhamChiTiet.getCpu());
            tv_chitietRam.setText(sanPhamChiTiet.getRam());
            tv_chitietOcung.setText(sanPhamChiTiet.getOcung());
            tv_chitietHDH.setText(sanPhamChiTiet.getHedieuhanh());
            tv_chitietManHinh.setText(sanPhamChiTiet.getManhinh());
            tv_chitietCardMH.setText(sanPhamChiTiet.getCardmh());
            tv_chitietPin.setText(sanPhamChiTiet.getPin()+" mAh");
            tv_chitietTrongluong.setText(sanPhamChiTiet.getTrongluong()+" kg");
        }else {
            tv_chitietCPU.setText("Đang cập nhật..");
            tv_chitietRam.setText("Đang cập nhật..");
            tv_chitietOcung.setText("Đang cập nhật..");
            tv_chitietHDH.setText("Đang cập nhật..");
            tv_chitietManHinh.setText("Đang cập nhật..");
            tv_chitietCardMH.setText("Đang cập nhật..");
            tv_chitietPin.setText("Đang cập nhật..");
            tv_chitietTrongluong.setText("Đang cập nhật..");
        }
    }
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent intent = new Intent(ActivityChiTietSanPham.this,ActivityEditSanPham.class);
                intent.putExtra("mssp",mssp);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}