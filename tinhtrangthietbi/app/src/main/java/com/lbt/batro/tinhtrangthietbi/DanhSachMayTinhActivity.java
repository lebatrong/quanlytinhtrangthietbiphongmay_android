package com.lbt.batro.tinhtrangthietbi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lbt.batro.tinhtrangthietbi.Presenter.ichitietphongmay;
import com.lbt.batro.tinhtrangthietbi.Presenter.lchitietphongmay;
import com.lbt.batro.tinhtrangthietbi.customApdapter.aRecyclerViewDanhSachMayTinh;
import com.lbt.batro.tinhtrangthietbi.models.clsApp.objthietbimaytinh_app;
import com.lbt.batro.tinhtrangthietbi.models.clsApp.objthongkemaytinh_app;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objphongmay;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objthietbikhacs;

import java.util.ArrayList;

public class DanhSachMayTinhActivity extends AppCompatActivity implements ichitietphongmay {

    aRecyclerViewDanhSachMayTinh adapter;
    ArrayList<objthietbimaytinh_app> mList;
    Toolbar toolbar;
    RecyclerView rclvDanhSachMay;
    objphongmay mObjPhong;
    lchitietphongmay mphongmay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_may_tinh);

        initView();
        mphongmay.getDanhSachMay(mObjPhong.getMaphong());
    }

    private void initView() {
        String title = "Chi tiết phong máy";
        rclvDanhSachMay = findViewById(R.id.rclvDanhSachMay);
        //SETUP TOOLBAR
        toolbar = findViewById(R.id.toolbardanhsachmay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        Bundle bundle = getIntent().getBundleExtra("dataphong");
        if(bundle!=null){
            mObjPhong = (objphongmay) bundle.getSerializable("phong");
            title = mObjPhong.getTenphong();
        }
        toolbar.setTitle(title);
        mphongmay = new lchitietphongmay(this);

    }

    private void clickChiTietMay() {
        adapter.setOnClickListener(new aRecyclerViewDanhSachMayTinh.OnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if( mList.get(pos).getLichsusuachua()!=null){
                    if(!mList.get(pos).getLichsusuachua().isDasuachua()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mamay", mList.get(pos).getMathietbi());
                        bundle.putSerializable("mls", mList.get(pos).getLichsusuachua());
                        Intent intent = new Intent(DanhSachMayTinhActivity.this, tinhtrangthietbi.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("qrcode", mList.get(pos).getMathietbi());
                        Intent intent = new Intent(DanhSachMayTinhActivity.this, baocaotinhtrangActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    }
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcode", mList.get(pos).getMathietbi());
                    Intent intent = new Intent(DanhSachMayTinhActivity.this, baocaotinhtrangActivity.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void loitaidulieu() {
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void phongmaydangxaydung() {
        Toast.makeText(this, getText(R.string.phongmaydangxaydung), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void thongkethietbikhac(objthietbikhacs default_values, objthietbikhacs hu) {

    }

    @Override
    public void thongkemaytinh(boolean phongmayhu, objthongkemaytinh_app thongke) {

    }

    @Override
    public void danhsachmaytinh(ArrayList<objthietbimaytinh_app> list) {
        mList = new ArrayList<>();
        this.mList = list;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclvDanhSachMay.setLayoutManager(layoutManager);
        adapter = new aRecyclerViewDanhSachMayTinh(list,this,R.layout.row_thietbi);
        rclvDanhSachMay.setAdapter(adapter);
        clickChiTietMay();
    }
}
