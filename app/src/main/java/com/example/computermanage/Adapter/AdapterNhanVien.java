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
import com.example.computermanage.DAO.DAONhanVien;
import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.R;
import com.example.computermanage.UI.NhanVien.ActivityChiTietNhanVien;

import java.util.ArrayList;
import java.util.Random;

public class AdapterNhanVien extends RecyclerView.Adapter<AdapterNhanVien.NhanVienViewHolder> {
    private Context context;
    ArrayList<NhanVien> listNhanVien;
    TextDrawable textDrawable;
    DAONhanVien daoNhanVien;
    public AdapterNhanVien(Context context, ArrayList<NhanVien> listNhanVien) {
        this.context = context;
        this.listNhanVien = listNhanVien;
        daoNhanVien = new DAONhanVien(context);
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nhanvien, null);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = listNhanVien.get(position);
        holder.tv_maNhanVien.setText("Mã nhân viên: "+ nhanVien.getMsnv());
        holder.tv_passwordNhanVien.setText("Pass: "+ nhanVien.getPassword());
        holder.tv_tenNhanVien.setText("Họ tên: "+ nhanVien.getHoten());
        holder.tv_sdtNhanVien.setText("Số điện thoại: "+ nhanVien.getSdt());
        holder.tv_emailNhanVien.setText("Email: "+ nhanVien.getEmail());
        String tennhanvien = nhanVien.getHoten();
        if (nhanVien.getHinhanh()==null){
            textDrawable = TextDrawable.builder().beginConfig().width(48).height(48).endConfig().buildRect(tennhanvien.substring(0, 1).toUpperCase(), getRandomColor());
            holder.img_viewAnhNhanVien.setImageDrawable(textDrawable);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getHinhanh(), 0, nhanVien.getHinhanh().length);
            holder.img_viewAnhNhanVien.setImageBitmap(bitmap);
        }
        holder.cv_chitietNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityChiTietNhanVien.class);
                intent.putExtra("manv",nhanVien.getMsnv());
                context.startActivity(intent);
            }
        });
        holder.img_deleteNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = daoNhanVien.deleteNhanVien(String.valueOf(nhanVien.getMsnv()));
                if (kq>0){
                    listNhanVien.clear();
                    listNhanVien.addAll(daoNhanVien.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNhanVien.size();
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenNhanVien;
        TextView tv_maNhanVien;
        TextView tv_passwordNhanVien;
        TextView tv_sdtNhanVien;
        TextView tv_emailNhanVien;
        ImageView img_viewAnhNhanVien,img_deleteNhanVien;
        CardView cv_chitietNV;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenNhanVien = itemView.findViewById(R.id.tv_tenNhanVien);
            tv_maNhanVien = itemView.findViewById(R.id.tv_maNhanVien);
            tv_passwordNhanVien = itemView.findViewById(R.id.tv_passwordNhanVien);
            tv_sdtNhanVien = itemView.findViewById(R.id.tv_sdtNhanVien);
            tv_emailNhanVien = itemView.findViewById(R.id.tv_emailNhanVien);
            img_viewAnhNhanVien = itemView.findViewById(R.id.img_viewAnhNhanVien);
            img_deleteNhanVien = itemView.findViewById(R.id.img_deleteNhanVien);
            cv_chitietNV = itemView.findViewById(R.id.cv_nhanvien);

        }
    }
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
