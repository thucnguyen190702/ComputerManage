package com.example.computermanage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computermanage.DAO.DAOKhachHang;
import com.example.computermanage.Model.KhachHang;
import com.example.computermanage.R;
import com.example.computermanage.UI.KhachHang.ActivityChiTietKhachHang;

import java.util.ArrayList;

public class AdapterKhachHang extends RecyclerView.Adapter<AdapterKhachHang.ViewHolderKhachHang> {
    Context context;
    ArrayList<KhachHang> listKH;
    DAOKhachHang daoKhachHang;

    public AdapterKhachHang(Context context, ArrayList<KhachHang> listKH) {
        this.context = context;
        this.listKH = listKH;
        daoKhachHang = new DAOKhachHang(context);
    }

    @Override
    public AdapterKhachHang.ViewHolderKhachHang onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khachhang, parent, false);
        return new ViewHolderKhachHang(view);
    }

    public void onBindViewHolder(AdapterKhachHang.ViewHolderKhachHang holder, int position) {
        KhachHang khachHang = listKH.get(position);
        holder.tv_tenKhachHang.setText("Tên khách hàng: " + khachHang.getHoten());
        holder.tv_sdtKhachHang.setText("Số điện thoại: " + khachHang.getSdt());
        holder.tv_diachiKhachHang.setText("Địa chỉ: " + khachHang.getDiachi());
        if (khachHang.getGioitinh() == 0) {
            holder.tv_gtKhachHang.setText("Giới tính: Nam");
        } else if (khachHang.getGioitinh() == 1) {
            holder.tv_gtKhachHang.setText("Giới tính: Nữ");
        }
        holder.cv_chitietKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityChiTietKhachHang.class);
                intent.putExtra("makh",khachHang.getMskh());
                context.startActivity(intent);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = daoKhachHang.deleteKhachHang(khachHang.getMskh());
                if (kq>0){
                    listKH.clear();
                    listKH.addAll(daoKhachHang.getAll());
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
        return listKH.size();
    }

    class ViewHolderKhachHang extends RecyclerView.ViewHolder {
        TextView tv_tenKhachHang,tv_sdtKhachHang,tv_gtKhachHang,tv_diachiKhachHang;
        ImageView img_delete;
        CardView cv_chitietKH;
        public ViewHolderKhachHang( View itemView) {
            super(itemView);
            tv_tenKhachHang = itemView.findViewById(R.id.tv_tenKhachHang);
            tv_sdtKhachHang = itemView.findViewById(R.id.tv_sdtKhachHang);
            tv_gtKhachHang = itemView.findViewById(R.id.tv_gtKhachHang);
            tv_diachiKhachHang = itemView.findViewById(R.id.tv_diachiKhachHang);
            img_delete = itemView.findViewById(R.id.img_deleteKhachHang);
            cv_chitietKH = itemView.findViewById(R.id.cv_chitietKH);
        }
    }
}
