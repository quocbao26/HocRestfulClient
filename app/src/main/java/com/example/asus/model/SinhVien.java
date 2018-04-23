package com.example.asus.model;

import java.io.Serializable;

/**
 * Created by Asus on 22/04/2018.
 */

public class SinhVien implements Serializable{
    private Integer Ma;
    private String Ten;

    //private Integer MaLop;

    public Integer getMa() {
        return Ma;
    }

    public void setMa(Integer ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }


//    public Integer getMaLop() {
//        return MaLop;
//    }
//
//    public void setMaLop(Integer maLop) {
//        MaLop = maLop;
//    }

    @Override
    public String toString() {
        return Ma+"\n"+Ten;
    }
}
