package com.example.computermanage.Model;

public class Admin {
    String user;
    String password;

    public static final String TABLE_ADMIN = "admin";
    public static final String COL_USER = "user";
    public static final String COL_PASSWORD = "password";

    public Admin(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public Admin() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
