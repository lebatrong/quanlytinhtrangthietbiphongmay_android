package lbt.com.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Presenter.iThongBao;
import lbt.com.manager.Presenter.lThongBao;
import lbt.com.manager.customAdapter.aRclvThongBao;

public class ThongBaoActivity extends AppCompatActivity implements iThongBao {
    public static final String TAG = "ThongBaoActivity";

    lThongBao mthongbao;
    Toolbar toolbar;
    RecyclerView rclvthongbao;
    aRclvThongBao adapter;
    List<objThietBi> mList;
    objPhongMay mPhongMay;
    LinearLayout lnlProgress,lnlRclvThongBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        initView();
        setupRecyclerview();

    }

    private void setupRecyclerview() {
        Gson gson = new Gson();
        SharedPreferences spf = getSharedPreferences("data",Context.MODE_PRIVATE);
        try {
            mPhongMay = gson.fromJson(spf.getString("phongmay", null), objPhongMay.class);
        }catch (Exception e){
            Log.e("Lỗi GSON",e.toString());
        }
        mthongbao.LangNgeThongBao(mPhongMay.getMaphong());

    }

    private void showtinhtrangthietbi(objlichsu_maytinhs mls, String mamay){
        Bundle bundle = new Bundle();
        bundle.putSerializable("mls",  mls);
        bundle.putString("mamay",mamay);
        Intent intent = new Intent(this,TinhTrangThietBiActivity.class);
        intent.putExtra("data",bundle);
        startActivity(intent);

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarThongBao);
        rclvthongbao = findViewById(R.id.rclvThongBao);
        mthongbao = new lThongBao(this,this);
        lnlProgress = findViewById(R.id.lnlProgess);
        lnlRclvThongBao = findViewById(R.id.lnlrclvThongBao);

        //CHƯA TẢI DỮ LIỆU XONG KHÔNG SHOW RA
        lnlProgress.setVisibility(View.VISIBLE);
        lnlRclvThongBao.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void loiduongtruyen() {

    }

    @Override
    public void pushthongbao(List<objThietBi> thietBi) {

//TẢI DỮ LIỆU XONG  SHOW RA
        lnlRclvThongBao.setVisibility(View.VISIBLE);
        lnlProgress.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(ThongBaoActivity.this,LinearLayoutManager.VERTICAL,false);
        rclvthongbao.hasFixedSize();
        rclvthongbao.setLayoutManager(manager);

        mList = new ArrayList<>();
        mList = thietBi;
        adapter = new aRclvThongBao(thietBi,this);
        rclvthongbao.setAdapter(adapter);

        adapter.setOnClickListener(new aRclvThongBao.OnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                showtinhtrangthietbi(mList.get(pos).getLichsusuachua(),mList.get(pos).getMathietbi());
            }
        });

    }


    @Override
    public void phongmayhoatdongtot() {
        Toast.makeText(this, getText(R.string.khongcothongbao), Toast.LENGTH_SHORT).show();
        finish();
    }


}
