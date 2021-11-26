package com.example.computermanage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.MenuItem;

import com.example.computermanage.UI.FragmentHoaDon;
import com.example.computermanage.UI.FragmentThem;
import com.example.computermanage.UI.FragmentThongKe;
import com.example.computermanage.UI.FragmentTrangChu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportActionBar().hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new FragmentTrangChu()).commit();
        }
        addControl();
        addEvent();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.trangchu:
                            selectedFragment = new FragmentTrangChu();
                            break;
                        case R.id.hoadon:
                            selectedFragment = new FragmentHoaDon();
                            break;
                        case R.id.thongke:
                            selectedFragment = new FragmentThongKe();
                            break;
                        case R.id.them:
                            selectedFragment = new FragmentThem();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            selectedFragment).commit();

                    return true;
                }
            };



    private void addEvent() {
    }

    private void addControl() {
        toolbar = getSupportActionBar();
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
    }
}
