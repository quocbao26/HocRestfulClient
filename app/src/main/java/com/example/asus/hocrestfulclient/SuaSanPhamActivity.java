package com.example.asus.hocrestfulclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.model.SanPham;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SuaSanPhamActivity extends AppCompatActivity {

    EditText edtSuaMaSP,edtSuaTenSP,edtSuaDonGiaSP;
    Button btnSuaSP;
    TextView txtSuaSPThongBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        SetControl();
        addEvents();
    }

    private void addEvents() {
        btnSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySuaSP();
            }
        });
    }

    private void xuLySuaSP() {
        SanPham sp = new SanPham();
        sp.setMa(Integer.parseInt(edtSuaMaSP.getText().toString().trim()));
        sp.setTen(edtSuaTenSP.getText().toString().trim());
        sp.setDongia(Integer.parseInt(edtSuaDonGiaSP.getText().toString().trim()));

        SuaSanPhamTask task = new SuaSanPhamTask();
        task.execute();
    }

    private void SetControl() {
        edtSuaMaSP = findViewById(R.id.edtSuaMaSP);
        edtSuaTenSP = findViewById(R.id.edtSuaTenSP);
        edtSuaDonGiaSP = findViewById(R.id.edtSuaDonGiaSP);
        btnSuaSP = findViewById(R.id.btnSuaSP);
        txtSuaSPThongBao = findViewById(R.id.txtSuaSPThongBao);

        Intent intent = getIntent();
        SanPham sp = (SanPham) intent.getSerializableExtra("SANPHAM");
        edtSuaMaSP.setText(sp.getMa()+"");
        edtSuaMaSP.setEnabled(false);
        edtSuaTenSP.setText(sp.getTen());
        edtSuaDonGiaSP.setText(sp.getDongia()+"");
    }

    class SuaSanPhamTask extends AsyncTask<SanPham,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                txtSuaSPThongBao.setText("Sửa sản phẩm thành công");
            }else{
                txtSuaSPThongBao.setText("Sửa sản phẩm thất bại");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(SanPham... sanPhams) {
            try{
                SanPham sp = sanPhams[0];
                String params = "?masp="+sp.getMa()+"&tensp="+ URLEncoder.encode(sp.getTen())+"&dongia="+sp.getDongia();
                URL url = new URL("http://10.216.149.29/restfulspdm/api/sanpham/"+params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type","application/json;charset-utf-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null)
                {
                    builder.append(line);
                }
                boolean kq = builder.toString().contains("true");
                return kq;
            }catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return false;
        }
    }
}
