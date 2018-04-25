package com.example.asus.hocrestfulclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDssp,btnDetailSP,btnDsspTheoDM,btnDsspTheoDonGia,btnListDM,btnListDetailDM,btnAddSP,btnAddDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetControl();

        btnDssp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DanhSachSanPhamActivity.class));
            }
        });
        btnDetailSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ChiTietSanPhamActivity.class));
            }
        });
        btnDsspTheoDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DanhSachSanPhamTheoDanhMucActivity.class));
            }
        });
        btnDsspTheoDonGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DanhSachSanPhamTheoDonGiaActivity.class));
            }
        });
        btnListDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DanhSachDanhMucActivity.class));
        }
        });
        btnListDetailDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ChiTietDanhMucActivity.class));
            }
        });
        btnAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ThemSanPhamActivity.class));
            }
        });
        btnAddDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ThemDanhMucActivity.class));
            }
        });
    }


    private void SetControl(){
        btnDssp = findViewById(R.id.buttonListsp);
        btnDetailSP = findViewById(R.id.buttonDetailSp);
        btnDsspTheoDM = findViewById(R.id.buttonListSPTheoDanhMuc);
        btnDsspTheoDonGia = findViewById(R.id.buttonListSPTheoDonGia);
        btnListDM = findViewById(R.id.btnlistDM);
        btnListDetailDM = findViewById(R.id.btnlistDetailDM);
        btnAddSP = findViewById(R.id.btnThemSP);
        btnAddDM = findViewById(R.id.btnThemDM);
    }
}
