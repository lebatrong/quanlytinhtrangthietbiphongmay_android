package com.lbt.batro.tinhtrangthietbi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lbt.batro.tinhtrangthietbi.Presenter.iphongmay;
import com.lbt.batro.tinhtrangthietbi.Presenter.lphongmay;
import com.lbt.batro.tinhtrangthietbi.customApdapter.aRecyclerViewDanhSachPhong;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objphongmay;

import java.util.ArrayList;

public class PhongMayActivity extends AppCompatActivity implements iphongmay {

    Toolbar toolbar;
    RecyclerView rclvdanhsachphong;
    aRecyclerViewDanhSachPhong adapter;
    lphongmay mPhongMay;
    ProgressDialog mProgress;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_may);
        initView();
        setupRecyclerview();
        showProgress("Đang tải dữ liệu");
    }

    private void showProgress(String content){
        mProgress.setMessage(content);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    private void setupRecyclerview() {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclvdanhsachphong.setLayoutManager(manager);
        mPhongMay.getdanhsachphongmay();
    }

    private void initView() {
        mProgress = new ProgressDialog(this);
        mPhongMay = new lphongmay(this);
        toolbar = findViewById(R.id.toolbardsphongmay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        rclvdanhsachphong = findViewById(R.id.rclvdanhsachphongmay);

    }

    @Override
    public void thongkemay(long tongmay, long mayhu, long maybinhthuong) {

    }

    @Override
    public void danhsachphongmay(final ArrayList<objphongmay> mlist) {
        mProgress.dismiss();
        adapter = new aRecyclerViewDanhSachPhong(mlist,this,R.layout.row_danhsachphongmay);
        rclvdanhsachphong.setAdapter(adapter);
        adapter.setOnItemClickListener(new aRecyclerViewDanhSachPhong.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("phong",mlist.get(pos));
                Intent intent = new Intent(PhongMayActivity.this,ChitietphongmayActivity.class);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });

    }
}
