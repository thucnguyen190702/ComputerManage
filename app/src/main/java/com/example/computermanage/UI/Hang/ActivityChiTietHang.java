package com.example.computermanage.UI.Hang;

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
import com.example.computermanage.DAO.DAOHang;
import com.example.computermanage.Model.Hang;
import com.example.computermanage.R;

import java.util.Random;

public class ActivityChiTietHang extends AppCompatActivity {
    TextView tv_tenHangCT;
    ImageView img_anhHangCT;
    DAOHang daoHang;
    Hang hang;
    int mahang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hang);
        tv_tenHangCT = findViewById(R.id.tv_tenHangCT);
        img_anhHangCT = findViewById(R.id.img_anhHangCT);
        getSupportActionBar().setTitle("Chi Tiết Hãng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        daoHang = new DAOHang(this);

        Intent intent = getIntent();
        mahang = intent.getIntExtra("mahang",0);

        hang = daoHang.getID(String.valueOf(mahang));
        if (hang.getHinhanh() == null){
            TextDrawable textDrawable = TextDrawable.builder().beginConfig().width(120).height(120).endConfig().buildRect(hang.getTenhang().substring(0, 1).toUpperCase(), getRandomColor());
            img_anhHangCT.setImageDrawable(textDrawable);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hang.getHinhanh(), 0, hang.getHinhanh().length);
            img_anhHangCT.setImageBitmap(bitmap);
        }
        tv_tenHangCT.setText(hang.getTenhang());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit,menu);
        return true;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent intent = new Intent(ActivityChiTietHang.this,ActivityEditHang.class);
                intent.putExtra("mahang",mahang);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}