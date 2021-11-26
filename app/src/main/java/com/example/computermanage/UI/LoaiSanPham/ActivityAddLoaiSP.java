package com.example.computermanage.UI.LoaiSanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computermanage.DAO.DAOHang;
import com.example.computermanage.DAO.DAOLoaiSanPham;
import com.example.computermanage.Model.Hang;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityAddLoaiSP extends AppCompatActivity {
    ImageView img_anhLoaiSP;
    TextInputEditText ed_maLoaiSP,ed_tenLoaiSP;
    Spinner spn_Hang;
    Bitmap bitmap, bitmap1;
    byte[] image;
    LinearLayout linearCamera, linearGallery;
    ActivityResultLauncher<Intent> launcherCamera;
    ActivityResultLauncher<Intent> launcherGallery;
    DAOLoaiSanPham daoLoaiSanPham;
    ArrayList<LoaiSanPham> listLSP;
    DAOHang daoHang;
    ArrayList<Hang> listH;
    LoaiSanPham loaiSanPham;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loai_sp);
        addControl();
        getDataSpinner();
        addEvent();
    }

    private void getDataSpinner() {
        listH = daoHang.getAll();
        ArrayAdapter adapter = new ArrayAdapter(ActivityAddLoaiSP.this, android.R.layout.simple_spinner_dropdown_item,listH);
        spn_Hang.setAdapter(adapter);

    }

    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm loại sản phẩm");
        img_anhLoaiSP = findViewById(R.id.img_anhLoaiSP);
        ed_maLoaiSP = findViewById(R.id.ed_maLoaiSP);
        ed_tenLoaiSP = findViewById(R.id.ed_tenLoaiSP);

        spn_Hang = findViewById(R.id.spn_Hang);
        daoHang = new DAOHang(this);
        daoLoaiSanPham = new DAOLoaiSanPham(this);
        listLSP = new ArrayList<>();
        listH = new ArrayList<>();

    }
    private void addEvent() {
        launcherCamera =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    img_anhLoaiSP.setImageBitmap(bitmap);
                }
            }
        });
        launcherGallery =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri =result.getData().getData();
                    try {
                        InputStream inputStream =getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img_anhLoaiSP.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bitmap =((BitmapDrawable)img_anhLoaiSP.getDrawable()).getBitmap();
        img_anhLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });
    }
    boolean check(){
        if (ed_maLoaiSP.getText().toString().isEmpty() || ed_tenLoaiSP.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void openBottomSheet() {
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        linearCamera = viewDialog.findViewById(R.id.linearCamera);
        linearGallery = viewDialog.findViewById(R.id.linearGallery);

        linearCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcherCamera.launch(intent);
                bottomSheetDialog.cancel();
            }
        });
        linearGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launcherGallery.launch(intent);
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.show();
    }
    public void convertImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_anhLoaiSP.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        image = byteArray.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
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

            case R.id.menu_reset:
                ed_maLoaiSP.setText("");
                ed_tenLoaiSP.setText("");
                Drawable drawable = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.image_defaut);
                }
                img_anhLoaiSP.setImageDrawable(drawable);
                return true;
            case R.id.menu_save:
                if (check()){
                    loaiSanPham = new LoaiSanPham();
                    loaiSanPham.setMslsp(ed_maLoaiSP.getText().toString());
                    loaiSanPham.setTenlsp(ed_tenLoaiSP.getText().toString());
                    Hang hang = (Hang) spn_Hang.getSelectedItem();
                    loaiSanPham.setMshang(hang.getMshang());

                    bitmap1 = ((BitmapDrawable) img_anhLoaiSP.getDrawable()).getBitmap();
                    if (bitmap != bitmap1) {
                        convertImage();
                        loaiSanPham.setHinhanh(image);
                    }

                    long kq = daoLoaiSanPham.insertLoaiSanPham(loaiSanPham);
                    if (kq>0){
                        listLSP.clear();
                        listLSP.addAll(daoLoaiSanPham.getAll());
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ActivityLoaiSanPham.class));
                    }else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
