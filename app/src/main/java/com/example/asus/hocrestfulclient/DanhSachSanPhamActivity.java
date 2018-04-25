package com.example.asus.hocrestfulclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachSanPhamActivity extends AppCompatActivity {

    ListView lvSV;
    ArrayAdapter<SanPham> sanPhamArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);
        SetControl();
        addEvents();
    }

    private void addEvents() {
        lvSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sp = sanPhamArrayAdapter.getItem(i);
                moManHinhSuaSanPham(sp);
            }
        });
    }

    private void moManHinhSuaSanPham(SanPham sp) {
        Intent intent = new Intent(DanhSachSanPhamActivity.this,SuaSanPhamActivity.class);
        intent.putExtra("SANPHAM",sp);
        startActivity(intent);
    }

    private void SetControl() {
        lvSV = findViewById(R.id.lvSinhVien);
        sanPhamArrayAdapter = new ArrayAdapter<SanPham>(DanhSachSanPhamActivity.this,android.R.layout.simple_list_item_1);
        lvSV.setAdapter(sanPhamArrayAdapter);
        DanhSachSanPhamTask task = new DanhSachSanPhamTask();
        task.execute();
    }

    class DanhSachSanPhamTask extends AsyncTask<Void,Void,ArrayList<SanPham>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // hàm hứng dữ liệu trả về
        @Override
        protected void onPostExecute(ArrayList<SanPham> sanPhams) {
            super.onPostExecute(sanPhams);
            sanPhamArrayAdapter.clear();
            sanPhamArrayAdapter.addAll(sanPhams);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<SanPham> doInBackground(Void... voids) {
            ArrayList<SanPham> dssp = new ArrayList<>();
            try{
                URL url = new URL("http://10.216.149.29/restfulspdm/api/sanpham");
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
                    int ma = jsonObject.getInt("Ma");
                    String ten = jsonObject.getString("Ten");
                    int dongia = jsonObject.getInt("DonGia");
                    SanPham sp = new SanPham();
                    sp.setMa(ma);
                    sp.setTen(ten);
                    sp.setDongia(dongia);

                    dssp.add(sp);
                }
                br.close();
            }catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dssp;
        }
    }
}
