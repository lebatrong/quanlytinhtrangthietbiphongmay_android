package lbt.com.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Models.Firebase.objthietbikhacs;
import lbt.com.manager.Presenter.iPhongMay;
import lbt.com.manager.Presenter.lPhongMay;
import lbt.com.manager.customAdapter.aRclvDanhSachMayTinh;

public class DanhSachMayTinhActivity extends AppCompatActivity implements iPhongMay {

    aRclvDanhSachMayTinh adapter;
    ArrayList<objthietbimaytinh_app> mList;
    Toolbar toolbar;
    RecyclerView rclvDanhSachMay;
    objPhongMay mObjPhong;
    lPhongMay mphongmay;

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
            mObjPhong = (objPhongMay) bundle.getSerializable("phong");
            title = mObjPhong.getTenphong();
        }
        toolbar.setTitle(title);
        mphongmay = new lPhongMay(this);

    }

    private void clickChiTietMay() {
        adapter.setOnClickListener(new aRclvDanhSachMayTinh.OnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if( mList.get(pos).getLichsusuachua()!=null){
                    if(!mList.get(pos).getLichsusuachua().isDasuachua()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mamay", mList.get(pos).getMathietbi());
                        bundle.putSerializable("mls", mList.get(pos).getLichsusuachua());
                        Intent intent = new Intent(DanhSachMayTinhActivity.this, TinhTrangThietBiActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    }
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
        adapter = new aRclvDanhSachMayTinh(list,this);
        rclvDanhSachMay.setAdapter(adapter);
        clickChiTietMay();
    }

    @Override
    public void capnhatthanhcong(boolean isSuccess) {

    }

}
