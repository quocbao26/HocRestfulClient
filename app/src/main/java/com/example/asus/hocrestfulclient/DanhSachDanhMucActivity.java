package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.model.DanhMuc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachDanhMucActivity extends AppCompatActivity {

    ListView lvDsDM;
    ArrayAdapter<DanhMuc> danhMucArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_danh_muc);
        Setcontrol();
        addEvents();
    }

    private void addEvents() {
        xemDanhSachDanhMuc();
    }

    private void xemDanhSachDanhMuc() {
        DanhSachDanhMucTask task = new DanhSachDanhMucTask();
        task.execute();
    }

    class DanhSachDanhMucTask extends AsyncTask<Void,Void,ArrayList<DanhMuc>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<DanhMuc> danhMucs) {
            super.onPostExecute(danhMucs);
            danhMucArrayAdapter.clear();
            danhMucArrayAdapter.addAll(danhMucs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<DanhMuc> doInBackground(Void... voids) {
            ArrayList<DanhMuc> dsdm = new ArrayList<>();
            try{
                URL url = new URL("http://192.168.0.29/restfulspdm/api/danhmuc");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset-utf-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine()) != null)
                {
                    builder.append(line);
                }

                JSONArray jsonArray = new JSONArray(builder.toString());
                for(int i = 0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int madm = jsonObject.getInt("MaDanhMuc");
                    String tendm = jsonObject.getString("TenDanhMuc");
                    DanhMuc dm = new DanhMuc();
                    dm.setMaDanhMuc(madm);
                    dm.setTenDanhMuc(tendm);
                    dsdm.add(dm);
                }
                bufferedReader.close();
            }catch (Exception ex){
                Log.e("Loi",ex.toString());
            }
            return dsdm;
        }
    }

    private void Setcontrol() {
        lvDsDM = findViewById(R.id.lvDSDM);
        danhMucArrayAdapter = new ArrayAdapter<DanhMuc>(DanhSachDanhMucActivity.this,android.R.layout.simple_list_item_1);
        lvDsDM.setAdapter(danhMucArrayAdapter);
    }
}
