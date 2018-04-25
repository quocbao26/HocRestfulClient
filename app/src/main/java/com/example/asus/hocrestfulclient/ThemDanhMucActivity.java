package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.model.DanhMuc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ThemDanhMucActivity extends AppCompatActivity {

    EditText edtAddMaDM,edtADDTenDM;
    Button btnAddDM;
    TextView txtThongBaoAddDM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);
        Setcontrol();

        addEvents();
    }

    private void addEvents() {
        btnAddDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLuuDanhMuc();
            }
        });
    }

    private void xuLyLuuDanhMuc() {
        DanhMuc dm = new DanhMuc();
        dm.setMaDanhMuc(Integer.parseInt(edtAddMaDM.getText().toString().trim()));
        dm.setTenDanhMuc(edtADDTenDM.getText().toString().trim());

        LuuDanhMucTask task = new LuuDanhMucTask();
        task.execute(dm);
    }

    class LuuDanhMucTask extends AsyncTask<DanhMuc,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                txtThongBaoAddDM.setText("Lưu danh mục thành công");
            }else{
                txtThongBaoAddDM.setText("Lưu danh mục thất bại");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(DanhMuc... danhMucs) {
            try{
                DanhMuc dm = danhMucs[0];
                String params = "?madm="+dm.getMaDanhMuc()+
                        "&tendm="+ URLEncoder.encode(dm.getTenDanhMuc());
                URL url = new URL("http://10.216.149.29/restfulspdm/api/danhmuc/"+params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
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
                Log.e("loi",ex.toString());
            }
            return false;
        }
    }

    private void Setcontrol() {
        edtAddMaDM = findViewById(R.id.edtAddMaDM);
        edtADDTenDM = findViewById(R.id.edtAddTenDM);
        btnAddDM = findViewById(R.id.btnAddDM);
        txtThongBaoAddDM = findViewById(R.id.txtThongBaoAddDM);
    }
}
