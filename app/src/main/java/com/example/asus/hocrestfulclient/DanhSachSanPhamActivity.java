package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.model.SinhVien;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachSanPhamActivity extends AppCompatActivity {

    ListView lvSV;
    ArrayAdapter<SinhVien> sanPhamArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);
        SetControl();
    }

    private void SetControl() {
        lvSV = findViewById(R.id.lvSinhVien);
        sanPhamArrayAdapter = new ArrayAdapter<SinhVien>(DanhSachSanPhamActivity.this,android.R.layout.simple_list_item_1);
        lvSV.setAdapter(sanPhamArrayAdapter);
        DanhSachSinhVienTask task = new DanhSachSinhVienTask();
        task.execute();
    }

    class DanhSachSinhVienTask extends AsyncTask<Void,Void,ArrayList<SinhVien>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // hàm hứng dữ liệu trả về
        @Override
        protected void onPostExecute(ArrayList<SinhVien> sinhViens) {
            super.onPostExecute(sinhViens);
            sanPhamArrayAdapter.clear();
            sanPhamArrayAdapter.addAll(sinhViens);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<SinhVien> doInBackground(Void... voids) {
            ArrayList<SinhVien> dssv = new ArrayList<>();
            try{
                URL url = new URL("http://192.168.0.36/hocrestful/api/sinhvien");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                // yêu cầu trả về định dạng Json
                connection.setRequestProperty("Content-Type","application/json; charset-utf-8");

                //kết quả trả về là tập chuỗi có dạng Json
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);

                //tiến hành đọc
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null){
                    builder.append(line);
                }

                // convert chuỗi trong stringbuilder => 1 dối tượng JsonArray
                // vì kq trả về là dấu + nên sd JsonArray
                JSONArray jsonArray = new JSONArray(builder.toString());

                for(int i = 0 ; i<jsonArray.length();i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                    int masv = jsonObject.getInt("Ma");
                    String tensv = jsonObject.getString("Ten");
                    //int namsinh = jsonObject.getInt("NamSinh");
                   // int malop = jsonObject.getInt("MaLop");
                    SinhVien sv = new SinhVien();
                    sv.setMa(masv);
                    sv.setTen(tensv);
                    //sv.setNamSinh(namsinh);
                   // sv.setMaLop(malop);
                    dssv.add(sv);
                }
                br.close();
            }catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dssv;
        }
    }
}