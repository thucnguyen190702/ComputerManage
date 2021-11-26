package com.example.computermanage.UI.Hang;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.computermanage.DAO.DAOHang;
import com.example.computermanage.Model.Hang;
import com.example.computermanage.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityEditHang extends AppCompatActivity {
    ImageView img_editAnhHang,img_editCameraHang;
    TextInputEditText ed_editMaHang,ed_editTenHang;
    byte[] image;
    LinearLayout linearCamera, linearGallery;
    ActivityResultLauncher<Intent> launcherCamera;
    ActivityResultLauncher<Intent> launcherGallery;
    DAOHang daoHang;
    Bitmap bitmap, bitmap1;
    Hang hang;
    Drawable drawable;
    ArrayList<Hang> listH;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img_editAnhHang = findViewById(R.id.img_editAnhHang);
        img_editCameraHang = findViewById(R.id.img_editCameraHang);
        ed_editMaHang = findViewById(R.id.ed_editMaHang);
        ed_editTenHang = findViewById(R.id.ed_editTenHang);
        daoHang = new DAOHang(this);
        listH = new ArrayList<>();
        addEvent();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addEvent() {
        launcherCamera =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    img_editAnhHang.setImageBitmap(bitmap);
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
                        img_editAnhHang.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Intent intent = getIntent();
        int mahang = intent.getIntExtra("mahang",0);
        hang = daoHang.getID(String.valueOf(mahang));
        ed_editMaHang.setText(""+ hang.getMshang());
        ed_editTenHang.setText(hang.getTenhang());
        if (hang.getHinhanh() == null) {
            drawable = getDrawable(R.drawable.image_defaut);
            img_editAnhHang.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hang.getHinhanh(), 0, hang.getHinhanh().length);
            img_editAnhHang.setImageBitmap(bitmap);
        }
        bitmap =((BitmapDrawable)img_editAnhHang.getDrawable()).getBitmap();
        img_editCameraHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });
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
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_editAnhHang.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        image = byteArray.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean check(){
        if (ed_editTenHang.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được bỏ trống tên hãng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_done:

                if (check()){
                    bitmap1=((BitmapDrawable)img_editAnhHang.getDrawable()).getBitmap();
                    if (bitmap!=bitmap1){
                        convertImage();
                        hang.setHinhanh(image);
                    }
                    hang.setTenhang(ed_editTenHang.getText().toString());
                    long result=daoHang.updateHang(hang);
                    if (result>0){
                        listH.clear();
                        listH.addAll(daoHang.getAll());
//                    adapterHang.notifyDataSetChanged();
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityEditHang.this,ActivityHang.class));
                    }else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}