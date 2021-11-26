package com.example.computermanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.computermanage.DAO.DAOAdmin;
import com.example.computermanage.DAO.DAONhanVien;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    EditText ed_user,ed_password;
    DAOAdmin daoAdmin;
    DAONhanVien daoNhanVien;
    CheckBox chk_reLogin;
    private long Pressed;
    Toast mToast;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        addEvent();
    }

    private void addEvent() {
        preferences =getSharedPreferences("rememberLogin",MODE_PRIVATE);
        ed_user.setText(preferences.getString("user",""));
        ed_password.setText(preferences.getString("pass",""));
        chk_reLogin.setChecked(preferences.getBoolean("checked",false));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    private void rememberUser(String u ,String p,boolean checked) {
        SharedPreferences preferences = getSharedPreferences("rememberLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!checked){
            editor.clear();
        }else {
            editor.putString("user",u);
            editor.putString("pass",p);
            editor.putBoolean("checked",checked);
        }
        editor.commit();
    }
    private void checkLogin() {
        String user = ed_user.getText().toString();
        String password = ed_password.getText().toString();
        if (user.isEmpty()){
            ed_user.setError("Tài khoản không được bỏ trống");
            return;
        }else if (password.isEmpty()){
            ed_password.setError("Mật khẩu không được bỏ trống");
            return;
        }else if (daoAdmin.checkLogin(user,password)>0 || daoNhanVien.checkLoginNV(user,password)>0){
            rememberUser(user,password,chk_reLogin.isChecked());
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Tài khoản ,mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControl() {
        getSupportActionBar().hide();
        btn_login = findViewById(R.id.btn_login);
        ed_user = findViewById(R.id.ed_user);
        chk_reLogin = findViewById(R.id.chk_reLogin);
        ed_password = findViewById(R.id.ed_password);
        daoAdmin = new DAOAdmin(LoginActivity.this);
        daoNhanVien = new DAONhanVien(LoginActivity.this);
    }
    @Override
    public void onBackPressed() {
        if (Pressed + 2000 > System.currentTimeMillis()) {
            mToast.cancel();
            moveTaskToBack(true);
        } else {
            mToast = Toast.makeText(getApplicationContext(), "ấn 2 lần để thoát ứng dụng", Toast.LENGTH_SHORT);
            mToast.show();
        }
        Pressed = System.currentTimeMillis();
    }
}