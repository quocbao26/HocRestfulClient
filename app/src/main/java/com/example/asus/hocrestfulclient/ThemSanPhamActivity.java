package com.example.asus.hocrestfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.asus.model.DanhMuc;
import com.example.asus.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ThemSanPhamActivity extends AppCompatActivity {

    TextView txtThongBao;
    EditText edtAddMaSP,edtAddTenSP,edtAddDonGia;
    Button btnLuu;
    Spinner spinnerDM;
    ArrayAdapter<DanhMuc> danhMucArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        SetControl();
        addEvents();

    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLuuSanPham();
            }
        });
    }

    private void xuLyLuuSanPham() {
        SanPham sp = new SanPham();
        sp.setMa(Integer.parseInt(edtAddMaSP.getText().toString().trim()));
        sp.setTen(edtAddTenSP.getText().toString().trim());
        sp.setDongia(Integer.parseInt(edtAddDonGia.getText().toString().trim()));

        DanhMuc dm = (DanhMuc) spinnerDM.getSelectedItem();
        sp.setMaDanhMuc(dm.getMaDanhMuc());

        LuuSanPhamTask task = new LuuSanPhamTask();
        task.execute(sp);
    }

    private void SetControl() {
        edtAddMaSP = findViewById(R.id.edtAddMaSP);
        edtAddTenSP = findViewById(R.id.edtAddTenSP);
        edtAddDonGia = findViewById(R.id.edtAddDonGiaSP);
        txtThongBao = findViewById(R.id.txtThongBao);
        btnLuu = findViewById(R.id.btnLuu);
        spinnerDM = findViewById(R.id.spinnerAddMaDMofSP);

        danhMucArrayAdapter = new ArrayAdapter<DanhMuc>(ThemSanPhamActivity.this,android.R.layout.simple_spinner_item);
        danhMucArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDM.setAdapter(danhMucArrayAdapter);

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
                URL url = new URL("http://10.216.149.29/restfulspdm/api/danhmuc");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json;charset-utf-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine()) != null)
                {
                    builder.append(line);
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(builder.toString());
                for(int i = 0; i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int madm = jsonObject.getInt("MaDanhMuc");
                    String tendm = jsonObject.getString("TenDanhMuc");
                    DanhMuc dm = new DanhMuc();
                    dm.setMaDanhMuc(madm);
                    dm.setTenDanhMuc(tendm);
                    dsdm.add(dm);
                }
            }catch (Exception ex)
            {
                Log.e("loi", ex.toString());
            }
            return dsdm;
        }
    }

    class LuuSanPhamTask extends AsyncTask<SanPham,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                txtThongBao.setText("Lưu sản phẩm thành công");
            }else{
                txtThongBao.setText("Lưu sản phẩm thất bại");
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
                // khi truyền chuỗi lên thông qua parameter thì bắt buộc phải mã hóa ENcode
                String params = "?masp="+sp.getMa()+
                                "&tensp="+ URLEncoder.encode(sp.getTen())+
                                "&dongia="+sp.getDongia()+
                                "&madm="+sp.getMaDanhMuc();
                URL url = new URL("http://10.216.149.29/restfulspdm/api/sanpham/"+params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json;charset-utf-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line= bufferedReader.readLine()) != null)
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
}
