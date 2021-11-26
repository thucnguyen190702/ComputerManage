package com.example.computermanage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.computermanage.DAO.DAOHang;
import com.example.computermanage.DAO.DAOLoaiSanPham;
import com.example.computermanage.Model.Hang;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.R;
import com.example.computermanage.UI.LoaiSanPham.ActivityChiTietLoaiSP;

import java.util.ArrayList;
import java.util.Random;

public class AdapterLoaiSanPham extends RecyclerView.Adapter<AdapterLoaiSanPham.ViewHolderLoaiSP> {
    Context context;
    ArrayList<LoaiSanPham> listLSP;
    DAOLoaiSanPham daoLoaiSanPham;
    DAOHang daoHang;
    TextDrawable textDrawable;
    public AdapterLoaiSanPham(Context context, ArrayList<LoaiSanPham> listLSP) {
        this.context = context;
        this.listLSP = listLSP;
        daoLoaiSanPham = new DAOLoaiSanPham(context);
        daoHang = new DAOHang(context);
    }

    @Override
    public ViewHolderLoaiSP onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaisanpham, null);
        return new ViewHolderLoaiSP(view);
    }

    @Override
    public void onBindViewHolder(AdapterLoaiSanPham.ViewHolderLoaiSP holder, int position) {
        LoaiSanPham loaiSanPham = listLSP.get(position);
        holder.tv_maLoaiSP.setText("Mã loại: " + loaiSanPham.getMslsp());
        holder.tv_tenLoaiSP.setText(loaiSanPham.getTenlsp());
        Hang hang = daoHang.getID(String.valueOf(loaiSanPham.getMshang()));
        holder.tv_tenHangSP.setText("Hãng: " + hang.getTenhang());
        if (loaiSanPham.getHinhanh()==null){
            textDrawable = TextDrawable.builder().beginConfig().width(48).height(48).endConfig().buildRect(loaiSanPham.getTenlsp().substring(0, 1).toUpperCase(), getRandomColor());
            holder.img_viewAnhLoaiSP.setImageDrawable(textDrawable);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(loaiSanPham.getHinhanh(), 0, loaiSanPham.getHinhanh().length);
            holder.img_viewAnhLoaiSP.setImageBitmap(bitmap);
        }
        holder.img_deleteLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = daoLoaiSanPham.deleteLoaiSanPham(loaiSanPham.getMslsp());
                if (kq > 0 ){
                    listLSP.clear();
                    listLSP.addAll(daoLoaiSanPham.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.cv_chitietLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityChiTietLoaiSP.class);
                intent.putExtra("mslsp",loaiSanPham.getMslsp());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLSP.size();
    }
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    class ViewHolderLoaiSP extends RecyclerView.ViewHolder {
        TextView tv_tenLoaiSP, tv_maLoaiSP, tv_tenHangSP;
        ImageView img_viewAnhLoaiSP,img_deleteLoaiSP;
        CardView cv_chitietLoaiSP;
        public ViewHolderLoaiSP(View itemView) {
            super(itemView);
            tv_maLoaiSP = itemView.findViewById(R.id.tv_maLoaiSP);
            tv_tenLoaiSP = itemView.findViewById(R.id.tv_tenLoaiSP);
            tv_tenHangSP = itemView.findViewById(R.id.tv_tenHangSP);
            img_viewAnhLoaiSP = itemView.findViewById(R.id.img_viewAnhLoaiSP);
            img_deleteLoaiSP = itemView.findViewById(R.id.img_deleteLoaiSP);
            cv_chitietLoaiSP = itemView.findViewById(R.id.cv_chitietLoaiSP);
        }
    }
}
