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
import com.example.computermanage.Model.Hang;
import com.example.computermanage.R;
import com.example.computermanage.UI.Hang.ActivityChiTietHang;

import java.util.ArrayList;
import java.util.Random;

public class AdapterHang extends RecyclerView.Adapter<AdapterHang.HangViewHolder> {
    private Context context;
    ArrayList<Hang> hangArrayList;
    TextDrawable textDrawable;
    DAOHang daoHang;

    public AdapterHang(Context context, ArrayList<Hang> hangArrayList) {
        this.context = context;
        this.hangArrayList = hangArrayList;
        daoHang = new DAOHang(context);
    }

    @NonNull
    @Override
    public HangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hang, parent,false);
        return new HangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHang.HangViewHolder holder, int position) {
        Hang hang = hangArrayList.get(position);
        if (hang==null){
            return;
        }
        holder.tvMaHang_itemhang.setText("Mã hãng: " + hang.getMshang());
        holder.tvTenHang_itemhang.setText("Tên hãng: " + hang.getTenhang());
        String tenhang = hang.getTenhang();
        if (hang.getHinhanh() == null) {
            textDrawable = TextDrawable.builder().beginConfig().width(48).height(48).endConfig().buildRect(tenhang.substring(0, 1).toUpperCase(), getRandomColor());
            holder.imgAnhHang_itemhang.setImageDrawable(textDrawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hang.getHinhanh(), 0, hang.getHinhanh().length);
            holder.imgAnhHang_itemhang.setImageBitmap(bitmap);
        }
        holder.cv_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityChiTietHang.class);
                intent.putExtra("mahang", hang.getMshang());
                context.startActivity(intent);
            }
        });
        holder.img_deleteHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = daoHang.deleteHang(String.valueOf(hang.getMshang()));
                if (kq > 0) {
                    hangArrayList.clear();
                    hangArrayList.addAll(daoHang.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return hangArrayList.size();
    }

    public class HangViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHang_itemhang;
        TextView tvTenHang_itemhang;
        ImageView imgAnhHang_itemhang, img_deleteHang;
        CardView cv_chitiet;

        public HangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHang_itemhang = itemView.findViewById(R.id.tvMaHang_itemhang);
            tvTenHang_itemhang = itemView.findViewById(R.id.tvTenHang_itemhang);
            imgAnhHang_itemhang = itemView.findViewById(R.id.imgAnhHang_itemhang);
            img_deleteHang = itemView.findViewById(R.id.img_deleteHang);
            cv_chitiet = itemView.findViewById(R.id.cv_chitiet);

        }
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
