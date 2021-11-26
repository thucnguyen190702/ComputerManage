package com.example.computermanage.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.computermanage.LoginActivity;
import com.example.computermanage.MainActivity;
import com.example.computermanage.R;
import com.example.computermanage.UI.Hang.ActivityHang;
import com.example.computermanage.UI.KhachHang.ActivityKhachHang;
import com.example.computermanage.UI.LoaiSanPham.ActivityLoaiSanPham;
import com.example.computermanage.UI.NhanVien.ActivityNhanVien;
import com.example.computermanage.UI.SanPham.ActivitySanPham;

public class FragmentThem extends Fragment {
    TextView tv_hang,tvKhachHang_Them,tvLoaiSanPham_Them,tvNhanVien_Them,tvSanPham_Them,tvThoat_Them;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);
        tv_hang = view.findViewById(R.id.tv_hang);
        tvKhachHang_Them = view.findViewById(R.id.tvKhachHang_Them);
        tvLoaiSanPham_Them = view.findViewById(R.id.tvLoaiSanPham_Them);
        tvNhanVien_Them = view.findViewById(R.id.tvNhanVien_Them);
        tvSanPham_Them = view.findViewById(R.id.tvSanPham_Them);
        tvThoat_Them = view.findViewById(R.id.tvThoat_Them);
        tv_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(), ActivityHang.class));
            }
        });
        tvKhachHang_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityKhachHang.class));
            }
        });
        tvLoaiSanPham_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityLoaiSanPham.class));
            }
        });
        tvNhanVien_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityNhanVien.class));
            }
        });
        tvSanPham_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivitySanPham.class));
            }
        });
        tvThoat_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return view;
    }
}
