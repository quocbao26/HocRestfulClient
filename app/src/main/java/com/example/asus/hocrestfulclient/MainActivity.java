package com.example.asus.hocrestfulclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDssp,btnDetailSP;
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
    }


    private void SetControl(){
        btnDssp = findViewById(R.id.buttonListsp);
        btnDetailSP = findViewById(R.id.buttonDetailSp);
    }
}
