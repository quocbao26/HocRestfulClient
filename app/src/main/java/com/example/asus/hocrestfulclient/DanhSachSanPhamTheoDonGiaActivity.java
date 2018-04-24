package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.asus.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachSanPhamTheoDonGiaActivity extends AppCompatActivity {

    EditText edtMin,edtMax;
    Button btnXemDssp;
    ListView lvDSSPTheoDonGia;
    ArrayAdapter<SanPham> sanPhamArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham_theo_don_gia);
        SetControl();
        addEvents();
    }

    private void addEvents() {
        btnXemDssp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXemSanPhamTheoDonGia();
            }
        });
    }

    private void xuLyXemSanPhamTheoDonGia() {
        DanhSachSanPhamTheoDonGiaTask task = new DanhSachSanPhamTheoDonGiaTask();
        task.execute(edtMin.getText().toString().trim(),edtMax.getText().toString().trim());
    }

    class DanhSachSanPhamTheoDonGiaTask extends AsyncTask<String,Void,ArrayList<SanPham>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
        protected ArrayList<SanPham> doInBackground(String... strings) {
            ArrayList<SanPham> dssp = new ArrayList<>();
            try{
                URL url = new URL("http://192.168.0.36/restfulspdm/api/sanpham/?a="+strings[0]+"&b="+strings[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json;charset-UTF-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null)
                {
                    builder.append(line);
                }
                JSONArray jsonArray = new JSONArray(builder.toString());
                for(int i =0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SanPham sp = new SanPham();
                    int ma = jsonObject.getInt("Ma");
                    String ten = jsonObject.getString("Ten");
                    int dongia = jsonObject.getInt("DonGia");

                    sp.setMa(ma);
                    sp.setTen(ten);
                    sp.setDongia(dongia);
                    dssp.add(sp);
                }
            }catch (Exception ex){
                Log.e("loi",ex.toString());
            }
            return dssp;
        }
    }

    private void SetControl() {
        edtMax = findViewById(R.id.edtGiaMax);
        edtMin = findViewById(R.id.edtGiaMin);
        btnXemDssp = findViewById(R.id.btnXemDSSPDonGia);
        lvDSSPTheoDonGia = findViewById(R.id.lvDSSPTheoDonGia);

        sanPhamArrayAdapter = new ArrayAdapter<SanPham>(DanhSachSanPhamTheoDonGiaActivity.this,android.R.layout.simple_list_item_1);
        lvDSSPTheoDonGia.setAdapter(sanPhamArrayAdapter);
    }
}
