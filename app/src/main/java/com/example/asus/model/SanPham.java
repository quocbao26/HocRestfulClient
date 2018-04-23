package com.example.asus.model;

/**
 * Created by Asus on 23/04/2018.
 */

public class SanPham {
    private int Ma;
    private String Ten;
    private int Dongia;
    private int MaDanhMuc;

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getDongia() {
        return Dongia;
    }

    public void setDongia(int dongia) {
        Dongia = dongia;
    }

    public int getMaDanhMuc() {
        return MaDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        MaDanhMuc = maDanhMuc;
    }

    @Override
    public String toString() {
        return Ma+"\n"+Ten+"\n"+Dongia;
    }
}
