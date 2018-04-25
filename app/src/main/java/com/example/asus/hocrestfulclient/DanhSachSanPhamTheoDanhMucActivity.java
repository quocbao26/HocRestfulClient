package com.example.asus.hocrestfulclient;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachSanPhamTheoDanhMucActivity extends AppCompatActivity {

    EditText edtMaDM;
    Button btnXemSP;
    ListView lvSP;
    ArrayAdapter<SanPham> sanPhamArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham_theo_danh_muc);
        Setcontrol();
        addEvents();

    }

    private void addEvents() {
        btnXemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xemDanhSachSanPhamTheoDanhMuc();
            }
        });
        lvSP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sp = sanPhamArrayAdapter.getItem(i);
                xuLyXoaSanPham(sp);
                return false;
            }
        });
    }

    private void xuLyXoaSanPham(final SanPham sp) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DanhSachSanPhamTheoDanhMucActivity.this);
        alertDialog.setTitle("Xác nhận xóa");
        alertDialog.setMessage("Bạn có chắc chắn xóa "+sp.getTen());
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                XoaSanPhamTask task = new XoaSanPhamTask();
                task.execute(sp);
                dialogInterface.dismiss();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.create().show();
    }

    private void xemDanhSachSanPhamTheoDanhMuc() {
        DanhSachSanPhamTheoDanhMucTask task = new DanhSachSanPhamTheoDanhMucTask();
        task.execute(edtMaDM.getText().toString().trim());
    }

    private void Setcontrol() {
        edtMaDM = findViewById(R.id.edtMaDM);
        btnXemSP = findViewById(R.id.btnXemSP);
        lvSP = findViewById(R.id.lvSanPham);

        sanPhamArrayAdapter = new ArrayAdapter<SanPham>(DanhSachSanPhamTheoDanhMucActivity.this,android.R.layout.simple_list_item_1);
        lvSP.setAdapter(sanPhamArrayAdapter);
    }

    class DanhSachSanPhamTheoDanhMucTask extends AsyncTask<String,Void,ArrayList<SanPham>>{
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
                URL url = new URL("http://10.216.149.29/restfulspdm/api/sanpham/?madm="+strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset-UTF-8");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                JSONArray jsonArray = new JSONArray(builder.toString());
                for(int i = 0;i < jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int masp = jsonObject.getInt("Ma");
                    String tensp = jsonObject.getString("Ten");
                    int dongia = jsonObject.getInt("DonGia");
                    SanPham sp = new SanPham();
                    sp.setTen(tensp);
                    sp.setMa(masp);
                    sp.setDongia(dongia);
                    dssp.add(sp);
                }
                bufferedReader.close();
            }catch (Exception ex){
                Log.e("Loi",ex.toString());
            }
            return dssp;
        }
    }

    class XoaSanPhamTask extends AsyncTask<SanPham,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                xemDanhSachSanPhamTheoDanhMuc();
            }else{
                Toast.makeText(DanhSachSanPhamTheoDanhMucActivity.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(SanPham... sanPhams) {
            try{
                SanPham sp =sanPhams[0];
                String params = "?masp="+sp.getMa();
                URL url = new URL("http://10.216.149.29/restfulspdm/api/sanpham"+params);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
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
}
