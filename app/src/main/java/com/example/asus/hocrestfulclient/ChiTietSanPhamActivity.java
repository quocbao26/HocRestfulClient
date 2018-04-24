package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.model.SanPham;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    EditText edtInputMaSP,edtMaSP,edtTenSP,edtDonGia;
    Button btnBack,btnChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        SetControl();
        addEvents();

    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXemChiTiet();
            }
        });

    }

    private void xuLyXemChiTiet() {
        ChiTietSanPhamTask task = new ChiTietSanPhamTask();
        task.execute(edtInputMaSP.getText().toString().trim());
    }

    private void SetControl() {
        edtInputMaSP = findViewById(R.id.edtInPutMaSP);
        edtMaSP = findViewById(R.id.edtMaSP);
        edtTenSP = findViewById(R.id.edtTenSP);
        edtDonGia = findViewById(R.id.edtGiaSP);
        btnBack = findViewById(R.id.btnBack);
        btnChiTiet = findViewById(R.id.btnXemChiTiet);
    }

    class ChiTietSanPhamTask extends AsyncTask<String,Void,SanPham>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(SanPham sanPham) {
            super.onPostExecute(sanPham);
            if(sanPham != null){
                edtMaSP.setText(sanPham.getMa()+"");
                edtTenSP.setText(sanPham.getTen()+"");
                edtDonGia.setText(sanPham.getDongia()+"");
            }
            else{
                Toast.makeText(ChiTietSanPhamActivity.this, "Đọc bị lỗi", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected SanPham doInBackground(String... strings) {
            try{
                URL url = new URL("http://192.168.0.36/restfulspdm/api/sanpham/"+strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json;charset-UTF-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while((line = br.readLine()) != null)
                {
                    builder.append(line);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                int masp = jsonObject.getInt("Ma");
                String tensp = jsonObject.getString("Ten");
                int dongia = jsonObject.getInt("DonGia");
                SanPham sp = new SanPham();
                sp.setMa(masp);
                sp.setTen(tensp);
                sp.setDongia(dongia);
                return sp;
            }catch (Exception ex){
                Log.e("LOI" , ex.toString());
            }
            return null;
        }
    }
}
