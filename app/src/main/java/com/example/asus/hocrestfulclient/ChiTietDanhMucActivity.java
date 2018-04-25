package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.model.DanhMuc;
import com.example.asus.model.SanPham;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChiTietDanhMucActivity extends AppCompatActivity {

    EditText edtInPutDetailDM,edtMaDM,edtTenDM;
    Button btnXemDetailDM,btnBackDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_danh_muc);
        SetControl();
        addEvents();
    }

    private void addEvents() {
        btnBackDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnXemDetailDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xemChiTietDanhMuc();
            }
        });
    }

    private void xemChiTietDanhMuc() {
        ChiTietDanhMucTask task = new ChiTietDanhMucTask();
        task.execute(edtInPutDetailDM.getText().toString().trim());
    }

    class ChiTietDanhMucTask extends AsyncTask<String,Void,DanhMuc>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(DanhMuc danhMuc) {
            super.onPostExecute(danhMuc);
            if(danhMuc != null){
                edtMaDM.setText(danhMuc.getMaDanhMuc()+"");
                edtTenDM.setText(danhMuc.getTenDanhMuc()+"");

            }
            else{
                Toast.makeText(ChiTietDanhMucActivity.this, "Đọc bị lỗi", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected DanhMuc doInBackground(String... strings) {
            try{
                URL url = new URL("http://10.216.149.29/restfulspdm/api/danhmuc/"+strings[0]);
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
                int madm = jsonObject.getInt("MaDanhMuc");
                String tendm = jsonObject.getString("TenDanhMuc");

                DanhMuc dm = new DanhMuc();
                dm.setMaDanhMuc(madm);
                dm.setTenDanhMuc(tendm);

                return dm;
            }catch (Exception ex){
                Log.e("LOI" , ex.toString());
            }
            return null;
        }
    }

    private void SetControl() {
        edtInPutDetailDM = findViewById(R.id.edtInPutMaDM);
        edtMaDM = findViewById(R.id.edtMaDM);
        edtTenDM = findViewById(R.id.edtTenDM);
        btnXemDetailDM = findViewById(R.id.btnXemChiTietDM);
        btnBackDM = findViewById(R.id.btnBackDM);
    }
}
