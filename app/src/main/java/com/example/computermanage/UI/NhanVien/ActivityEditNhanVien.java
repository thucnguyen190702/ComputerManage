package com.example.computermanage.UI.NhanVien;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computermanage.DAO.DAONhanVien;
import com.example.computermanage.Model.NhanVien;
import com.example.computermanage.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityEditNhanVien extends AppCompatActivity {
    ImageView img_editAnhNhanVien;
    TextInputEditText ed_editmaNhanVien, ed_editPasswordNV, ed_editTenNhanVien, ed_editSdtNhanVien, ed_editEmailNhanVien;
    Bitmap bitmap, bitmap1;
    byte[] image;
    LinearLayout linearCamera, linearGallery;
    ActivityResultLauncher<Intent> launcherCamera;
    ActivityResultLauncher<Intent> launcherGallery;
    DAONhanVien daoNhanVien;
    ArrayList<NhanVien> listNhanVien;
    NhanVien nhanVien;
    Drawable drawable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nhan_vien);
        addControl();
        addEvent();
    }



    private void addControl() {
        img_editAnhNhanVien = findViewById(R.id.img_editAnhNhanVien);
        ed_editmaNhanVien = findViewById(R.id.ed_editmaNhanVien);
        ed_editPasswordNV = findViewById(R.id.ed_editPasswordNV);
        ed_editTenNhanVien = findViewById(R.id.ed_editTenNhanVien);
        ed_editSdtNhanVien = findViewById(R.id.ed_editSdtNhanVien);
        ed_editEmailNhanVien = findViewById(R.id.ed_editEmailNhanVien);
        getSupportActionBar().setTitle("Thêm Nhân Viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        daoNhanVien = new DAONhanVien(ActivityEditNhanVien.this);
        listNhanVien = new ArrayList<>();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addEvent() {
        launcherCamera=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK&&result.getData()!=null){
                    Bundle bundle=result.getData().getExtras();
                    Bitmap bitmap= (Bitmap) bundle.get("data");
                    img_editAnhNhanVien.setImageBitmap(bitmap);
                }
            }
        });
        launcherGallery=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK&&result.getData()!=null){
                    Uri uri=result.getData().getData();
                    try {
                        InputStream inputStream=getContentResolver().openInputStream(uri);
                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        img_editAnhNhanVien.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Intent intent=getIntent();
        String id=intent.getStringExtra("manv");
        nhanVien=daoNhanVien.getID(id);
        ed_editmaNhanVien.setText(nhanVien.getMsnv());
        ed_editTenNhanVien.setText(nhanVien.getHoten());
        ed_editPasswordNV.setText(nhanVien.getPassword());
        ed_editSdtNhanVien.setText(nhanVien.getSdt());
        ed_editEmailNhanVien.setText(nhanVien.getEmail());
        if (nhanVien.getHinhanh()==null){
            drawable=getDrawable(R.drawable.image_defaut);
            img_editAnhNhanVien.setImageDrawable(drawable);
        }else {
            Bitmap bitmap=BitmapFactory.decodeByteArray(nhanVien.getHinhanh(),0,nhanVien.getHinhanh().length);
            img_editAnhNhanVien.setImageBitmap(bitmap);
        }
        bitmap=((BitmapDrawable)img_editAnhNhanVien.getDrawable()).getBitmap();
        img_editAnhNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });


    }

    private void openBottomSheet() {
        View view=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        linearCamera=bottomSheetDialog.findViewById(R.id.linearCamera);
        linearGallery=bottomSheetDialog.findViewById(R.id.linearGallery);
        linearCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcherCamera.launch(intent);
                bottomSheetDialog.cancel();
            }
        });
        linearGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launcherGallery.launch(intent);
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.show();
    }
    private void convertImage(){
        BitmapDrawable bitmapDrawable= (BitmapDrawable) img_editAnhNhanVien.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        image=byteArray.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_done,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    boolean check(){
        if (ed_editmaNhanVien.getText().toString().isEmpty()
                || ed_editTenNhanVien.getText().toString().isEmpty()
                || ed_editPasswordNV.getText().toString().isEmpty()
                || ed_editEmailNhanVien.getText().toString().isEmpty()
                || ed_editSdtNhanVien.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_done:
                if (check()){
                    bitmap1=((BitmapDrawable)img_editAnhNhanVien.getDrawable()).getBitmap();
                    if (bitmap!=bitmap1){
                        convertImage();
                        nhanVien.setHinhanh(image);
                    }
                    nhanVien.setMsnv(ed_editmaNhanVien.getText().toString());
                    nhanVien.setPassword(ed_editPasswordNV.getText().toString());
                    nhanVien.setHoten(ed_editTenNhanVien.getText().toString());
                    nhanVien.setSdt(ed_editSdtNhanVien.getText().toString());
                    nhanVien.setEmail(ed_editEmailNhanVien.getText().toString());
                    long result=daoNhanVien.updateNhanVien(nhanVien);
                    if (result>0){
                        listNhanVien.clear();
                        listNhanVien.addAll(daoNhanVien.getAll());
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityEditNhanVien.this,ActivityNhanVien.class));
                    }else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}