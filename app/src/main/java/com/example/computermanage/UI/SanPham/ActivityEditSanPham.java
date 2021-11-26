package com.example.computermanage.UI.SanPham;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.computermanage.DAO.DAOLoaiSanPham;
import com.example.computermanage.DAO.DAOSanPham;
import com.example.computermanage.DAO.DAOSanPhamCT;
import com.example.computermanage.Model.LoaiSanPham;
import com.example.computermanage.Model.SanPham;
import com.example.computermanage.Model.SanPhamChiTiet;
import com.example.computermanage.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityEditSanPham extends AppCompatActivity {
    ImageView img_editAnhSanPham;
    TextInputEditText ed_editMaSanPham, ed_editHDHSP, ed_editTenSanPham, ed_editGiaSP, ed_editCPUSP, ed_editRamSP, ed_editOcungSP, ed_editManHinhSP, ed_editCardMHSP, ed_editPinSP, ed_editTrongLuongSP, ed_editMoTaSP;
    RadioGroup rdg_edittinhTrang;
    RadioButton rdo_editCu, rdo_editLikeNew, rdo_editMoi;
    Spinner spn_editLoaiSP;
    DAOSanPham daoSanPham;
    DAOSanPhamCT daoSanPhamCT;
    DAOLoaiSanPham daoLoaiSanPham;
    ArrayList<SanPham> listSP;
    ArrayList<SanPhamChiTiet> listSPCT;
    ArrayList<LoaiSanPham> listLSP;
    Bitmap bitmap, bitmap1;
    byte[] image;
    LinearLayout linearCamera, linearGallery;
    ActivityResultLauncher<Intent> launcherCamera;
    ActivityResultLauncher<Intent> launcherGallery;
    Drawable drawable;
    LoaiSanPham loaiSanPham;
    String mssp;
    SanPham sanPham;
    SanPhamChiTiet sanPhamChiTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_san_pham);
        addControl();
        addClickCamera();
        addDataSpinner();
        setDataOld();
    }

    private void setDataOld() {
        Intent intent = getIntent();
        mssp = intent.getStringExtra("mssp");
        sanPham = daoSanPham.getID(mssp);
        ed_editMaSanPham.setText(sanPham.getMssp());
        ed_editTenSanPham.setText(sanPham.getTensp());
        ed_editGiaSP.setText(sanPham.getGiatien() + "");
        if (sanPham.getHinhanh() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getDrawable(R.drawable.image_defaut);
            }
            img_editAnhSanPham.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinhanh(), 0, sanPham.getHinhanh().length);
            img_editAnhSanPham.setImageBitmap(bitmap);
        }
        bitmap = ((BitmapDrawable) img_editAnhSanPham.getDrawable()).getBitmap();
        loaiSanPham = daoLoaiSanPham.getID(sanPham.getMslsp());
        listLSP = daoLoaiSanPham.getAll();
        int pos = -1;
        for (int i = 0; i < listLSP.size(); i++) {
            if (listLSP.get(i).getMslsp() == sanPham.getMslsp()) {
                pos = i;
                break;
            }
        }
        spn_editLoaiSP.setSelection(pos);
        if (sanPham.getTinhtrang() == 0) {
            rdo_editCu.setChecked(true);
        } else if (sanPham.getTinhtrang() == 1) {
            rdo_editLikeNew.setChecked(true);
        } else if (sanPham.getTinhtrang() == 2) {
            rdo_editMoi.setChecked(true);
        }

        ed_editMoTaSP.setText(sanPham.getMota());
        if (daoSanPhamCT.checkTTMaSP(sanPham.getMssp()) > 0) {
            sanPhamChiTiet = daoSanPhamCT.getID(sanPham.getMssp());
            ed_editCPUSP.setText(sanPhamChiTiet.getCpu());
            ed_editRamSP.setText(sanPhamChiTiet.getRam());
            ed_editOcungSP.setText(sanPhamChiTiet.getOcung());
            ed_editHDHSP.setText(sanPhamChiTiet.getHedieuhanh());
            ed_editManHinhSP.setText(sanPhamChiTiet.getManhinh());
            ed_editCardMHSP.setText(sanPhamChiTiet.getCardmh());
            ed_editPinSP.setText(sanPhamChiTiet.getPin());
            ed_editTrongLuongSP.setText(sanPhamChiTiet.getTrongluong() + "");
        } else {
            ed_editCPUSP.setText("");
            ed_editRamSP.setText("");
            ed_editOcungSP.setText("");
            ed_editHDHSP.setText("");
            ed_editManHinhSP.setText("");
            ed_editCardMHSP.setText("");
            ed_editPinSP.setText("");
            ed_editTrongLuongSP.setText("");
        }
    }

    private void addControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa sản phẩm");
        img_editAnhSanPham = findViewById(R.id.img_editAnhSanPham);
        ed_editMaSanPham = findViewById(R.id.ed_editMaSanPham);
        ed_editTenSanPham = findViewById(R.id.ed_editTenSanPham);
        ed_editGiaSP = findViewById(R.id.ed_editGiaSP);
        ed_editCPUSP = findViewById(R.id.ed_editCPUSP);
        ed_editRamSP = findViewById(R.id.ed_editRamSP);
        ed_editOcungSP = findViewById(R.id.ed_editOcungSP);
        ed_editManHinhSP = findViewById(R.id.ed_editManHinhSP);
        ed_editCardMHSP = findViewById(R.id.ed_editCardMHSP);
        ed_editPinSP = findViewById(R.id.ed_editPinSP);
        ed_editTrongLuongSP = findViewById(R.id.ed_editTrongLuongSP);
        ed_editMoTaSP = findViewById(R.id.ed_editMoTaSP);
        ed_editHDHSP = findViewById(R.id.ed_editHDHSP);
        rdg_edittinhTrang = findViewById(R.id.rdg_edittinhTrang);
        rdo_editCu = findViewById(R.id.rdo_editCu);
        rdo_editMoi = findViewById(R.id.rdo_editMoi);
        rdo_editLikeNew = findViewById(R.id.rdo_editLikeNew);
        spn_editLoaiSP = findViewById(R.id.spn_editLoaiSP);
        daoSanPham = new DAOSanPham(this);
        daoSanPhamCT = new DAOSanPhamCT(this);
        daoLoaiSanPham = new DAOLoaiSanPham(this);
        listSP = new ArrayList<>();
        listSPCT = new ArrayList<>();
        listLSP = new ArrayList<>();
    }

    void addDataSpinner() {
        listLSP = daoLoaiSanPham.getAll();
        ArrayAdapter adapter = new ArrayAdapter(ActivityEditSanPham.this, android.R.layout.simple_spinner_dropdown_item, listLSP);
        spn_editLoaiSP.setAdapter(adapter);
    }

    private void addClickCamera() {
        launcherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    img_editAnhSanPham.setImageBitmap(bitmap);
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
                        img_editAnhSanPham.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        img_editAnhSanPham.setOnClickListener(new View.OnClickListener() {
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
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_editAnhSanPham.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        image = byteArray.toByteArray();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }
    boolean check(){
        if (ed_editMaSanPham.getText().toString().isEmpty()
                || ed_editTenSanPham.getText().toString().isEmpty()
                || ed_editGiaSP.getText().toString().isEmpty()
                || ed_editMoTaSP.getText().toString().isEmpty()
                || ed_editRamSP.getText().toString().isEmpty()
                || ed_editOcungSP.getText().toString().isEmpty()
                || ed_editPinSP.getText().toString().isEmpty()
                || ed_editTrongLuongSP.getText().toString().isEmpty()
                || ed_editManHinhSP.getText().toString().isEmpty()
                || ed_editCardMHSP.getText().toString().isEmpty()
                || ed_editCPUSP.getText().toString().isEmpty()
                || ed_editHDHSP.getText().toString().isEmpty()){
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
                    bitmap1 = ((BitmapDrawable) img_editAnhSanPham.getDrawable()).getBitmap();
                    if (bitmap != bitmap1) {
                        convertImage();
                        sanPham.setHinhanh(image);
                    }
                    sanPham.setTensp(ed_editTenSanPham.getText().toString());
                    LoaiSanPham loaiSanPham = (LoaiSanPham) spn_editLoaiSP.getSelectedItem();
                    sanPham.setMslsp(loaiSanPham.getMslsp());
                    sanPham.setGiatien(Double.parseDouble(ed_editGiaSP.getText().toString()));
                    int checkRadio = rdg_edittinhTrang.getCheckedRadioButtonId();
                    if (checkRadio == R.id.rdo_editCu) {
                        sanPham.setTinhtrang(0);
                    } else if (checkRadio == R.id.rdo_editLikeNew) {
                        sanPham.setTinhtrang(1);
                    } else if (checkRadio == R.id.rdo_editMoi) {
                        sanPham.setTinhtrang(2);
                    }
                    sanPham.setMota(ed_editMoTaSP.getText().toString());
                    long kq = daoSanPham.updateSanPham(sanPham);
                    //add san pham chi tiet
                    sanPhamChiTiet.setMssp(ed_editMaSanPham.getText().toString());
                    sanPhamChiTiet.setCpu(ed_editCPUSP.getText().toString());
                    sanPhamChiTiet.setRam(ed_editRamSP.getText().toString());
                    sanPhamChiTiet.setOcung(ed_editOcungSP.getText().toString());
                    sanPhamChiTiet.setHedieuhanh(ed_editHDHSP.getText().toString());
                    sanPhamChiTiet.setManhinh(ed_editManHinhSP.getText().toString());
                    sanPhamChiTiet.setCardmh(ed_editCardMHSP.getText().toString());
                    sanPhamChiTiet.setPin(ed_editPinSP.getText().toString());
                    sanPhamChiTiet.setTrongluong(Float.parseFloat(ed_editTrongLuongSP.getText().toString()));
                    long kq1 = daoSanPhamCT.updateSanPhamCT(sanPhamChiTiet);
                    if (kq > 0 && kq1 > 0){
                        listSP.clear();
                        listSPCT.clear();
                        listSP.addAll(daoSanPham.getAll());
                        listSPCT.addAll(daoSanPhamCT.getAll());
                        startActivity(new Intent(ActivityEditSanPham.this,ActivitySanPham.class));
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}