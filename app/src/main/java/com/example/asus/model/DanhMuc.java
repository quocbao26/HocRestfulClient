package com.example.asus.model;

import java.io.Serializable;

/**
 * Created by Asus on 24/04/2018.
 */

public class DanhMuc implements Serializable {
    private int MaDanhMuc;
    private String TenDanhMuc;

    public int getMaDanhMuc() {
        return MaDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        MaDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        TenDanhMuc = tenDanhMuc;
    }

    @Override
    public String toString() {
        return MaDanhMuc+"\n"+TenDanhMuc;
    }
}
