package com.example.computermanage.UI.LoaiSanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class ActivityEditLoaiSP extends AppCompatActivity {
    TextInputEditText ed_editMaLoaiSP, ed_editTenLoaiSP;
    ImageView img_editAnhLoaiSP;
    Spinner spn_editHang;
    DAOLoaiSanPham daoLoaiSanPham;
    DAOHang daoHang;
    ArrayList<LoaiSanPham> listLSP;
    ArrayList<Hang> listH;
    LoaiSanPham loaiSanPham;
    String malsp;
    Bitmap bitmap, bitmap1;
    byte[] image;
    LinearLayout linearCamera, linearGallery;
    ActivityResultLauncher<Intent> launcherCamera;
    ActivityResultLauncher<Intent> launcherGallery;
    Drawable drawable;
    Hang hang;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loai_sp);
        addControl();
        getDataSpinner();
        addEvent();
    }

    private void getDataSpinner() {
        listH = daoHang.getAll();
        ArrayAdapter adapter = new ArrayAdapter(ActivityEditLoaiSP.this, android.R.layout.simple_spinner_dropdown_item, listH);
        spn_editHang.setAdapter(adapter);
    }

    private void addEvent() {
        launcherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    img_editAnhLoaiSP.setImageBitmap(bitmap);
                }
            }
        });
        launcherGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img_editAnhLoaiSP.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Intent intent = getIntent();
        malsp = intent.getStringExtra("malsp");
        loaiSanPham = daoLoaiSanPham.getID(malsp);
        ed_editMaLoaiSP.setText(loaiSanPham.getMslsp());
        ed_editTenLoaiSP.setText(loaiSanPham.getTenlsp());
        hang = daoHang.getID(String.valueOf(loaiSanPham.getMshang()));
        listH = daoHang.getAll();
        int posi = -1;
        for (int i = 0; i < listH.size(); i++) {
            if (listH.get(i).getMshang() == hang.getMshang()) {
                posi = i;
                break;
            }
        }
        spn_editHang.setSelection(posi);
        if (loaiSanPham.getHinhanh() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getDrawable(R.drawable.image_defaut);
            }
            img_editAnhLoaiSP.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(loaiSanPham.getHinhanh(), 0, loaiSanPham.getHinhanh().length);
            img_editAnhLoaiSP.setImageBitmap(bitmap);
        }
        bitmap = ((BitmapDrawable) img_editAnhLoaiSP.getDrawable()).getBitmap();
        img_editAnhLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });

    }

    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa loại sản phẩm");
        ed_editMaLoaiSP = findViewById(R.id.ed_editMaLoaiSP);
        ed_editTenLoaiSP = findViewById(R.id.ed_editTenLoaiSP);
        img_editAnhLoaiSP = findViewById(R.id.img_editAnhLoaiSP);
        spn_editHang = findViewById(R.id.spn_editHang);
        daoLoaiSanPham = new DAOLoaiSanPham(this);
        daoHang = new DAOHang(this);
        listH = new ArrayList<>();
        listLSP = new ArrayList<>();

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
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_editAnhLoaiSP.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        image = byteArray.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    boolean check(){
        if (ed_editMaLoaiSP.getText().toString().isEmpty() || ed_editTenLoaiSP.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                if (check()){
                    bitmap1 = ((BitmapDrawable) img_editAnhLoaiSP.getDrawable()).getBitmap();
                    if (bitmap != bitmap1) {
                        convertImage();
                        loaiSanPham.setHinhanh(image);
                    }
                    loaiSanPham.setTenlsp(ed_editTenLoaiSP.getText().toString());
                    hang = (Hang) spn_editHang.getSelectedItem();
                    loaiSanPham.setMshang(hang.getMshang());


                    int kq = daoLoaiSanPham.updateLoaiSanPham(loaiSanPham);
                    if (kq > 0) {
                        listLSP.clear();
                        listLSP.addAll(daoLoaiSanPham.getAll());
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ActivityLoaiSanPham.class));
                    } else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
