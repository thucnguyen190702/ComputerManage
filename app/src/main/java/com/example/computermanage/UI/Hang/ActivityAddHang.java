package com.example.computermanage.UI.Hang;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
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

public class ActivityAddHang extends AppCompatActivity {
    ActionBar actionBar;
    ImageView img_anhHang, img_cameraHang;
    TextInputEditText ed_mahang, ed_tenhang;
    Bitmap bitmap, bitmap1;
    byte[] image;
    LinearLayout linearCamera, linearGallery;
    ActivityResultLauncher<Intent> launcherCamera;
    ActivityResultLauncher<Intent> launcherGallery;
    DAOHang daoHang;
    ArrayList<Hang> listHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hang);

        addConTrol();
        addEvent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void addEvent() {
        launcherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    img_anhHang.setImageBitmap(bitmap);
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
                        img_anhHang.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bitmap = ((BitmapDrawable) img_anhHang.getDrawable()).getBitmap();
        img_cameraHang.setOnClickListener(new View.OnClickListener() {
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


    private void addConTrol() {
        actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm Hãng");
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        img_anhHang = findViewById(R.id.img_anhHang);
        img_cameraHang = findViewById(R.id.img_cameraHang);
        ed_mahang = findViewById(R.id.ed_mahang);
        ed_tenhang = findViewById(R.id.ed_tenhang);
        daoHang = new DAOHang(ActivityAddHang.this);
        listHang = new ArrayList<>();
    }

    public boolean check() {
        if (ed_tenhang.getText().toString().isEmpty()) {
            Toast.makeText(this, "Không được bỏ trống tên hãng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void convertImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_anhHang.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        image = byteArray.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reset:
                Drawable drawable = getDrawable(R.drawable.image_defaut);
                img_anhHang.setImageDrawable(drawable);
                ed_mahang.setText("");
                ed_tenhang.setText("");
                return true;
            case R.id.menu_save:

                if (check()){
                    bitmap1 = ((BitmapDrawable) img_anhHang.getDrawable()).getBitmap();
                    Hang hang = new Hang();

                    hang.setTenhang(ed_tenhang.getText().toString());
                    if (bitmap != bitmap1) {
                        convertImage();
                        hang.setHinhanh(image);
                    }
                    long kq = daoHang.insertHang(hang);
                    if (kq > 0) {
                        listHang.clear();
                        listHang.addAll(daoHang.getAll());
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityAddHang.this, ActivityHang.class));
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return true;
    }

}
