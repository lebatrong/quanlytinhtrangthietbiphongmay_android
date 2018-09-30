package com.lbt.batro.tinhtrangthietbi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.lbt.batro.tinhtrangthietbi.Presenter.idangnhap;
import com.lbt.batro.tinhtrangthietbi.Presenter.itaikhoan;
import com.lbt.batro.tinhtrangthietbi.Presenter.ldangnhap;
import com.lbt.batro.tinhtrangthietbi.Presenter.ltaikhoan;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objnguoidungs;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements idangnhap, itaikhoan {

    CardView crvCaiDat,crvQRcode,crvTaiKhoan,crvPhong;
    ldangnhap mdangnhap;
    ltaikhoan mTaiKhoan;
    CircleImageView anhnen;
    TextView tvHoTen;
    Button btnDangXuat;

    @Override
    protected void onResume() {
        super.onResume();
        mTaiKhoan.getDataDefault();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        actionQRCode();
        actionDangXuat();
        actionTaiKhoan();
        actionPhong();
        actionSetting();
    }

    private void actionPhong() {
        crvPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PhongMayActivity.class));
            }
        });
    }

    private void actionTaiKhoan() {
        crvTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,taikhoanActivity.class));
            }
        });
    }

    private void actionQRCode() {
        crvQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ScannerQRCode.class);
                startActivity(intent);
            }
        });
    }

    private void actionSetting() {
        crvCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actionDangXuat() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdangnhap.dangxuat();
            }
        });
    }

    private void initView() {
        mTaiKhoan = new ltaikhoan(this,this);
        mTaiKhoan.getDataDefault();
        mTaiKhoan.laythongtintaikhoan();
        crvCaiDat = findViewById(R.id.cardviewCaiDat);
        crvPhong = findViewById(R.id.cardviewPhong);
        crvQRcode = findViewById(R.id.cardviewQRCode);
        crvTaiKhoan = findViewById(R.id.cardviewTaiKhoan);
        mdangnhap = new ldangnhap(this, this);
        tvHoTen = findViewById(R.id.tvHoTenMain);
        anhnen = findViewById(R.id.imvhinhnenMain);
        btnDangXuat = findViewById(R.id.btnDangXuat);
    }

    @Override
    public void dangnhapthanhcong() {

    }

    @Override
    public void dangnhapthatbai() {

    }

    @Override
    public void saitendangnhap() {

    }

    @Override
    public void saimatkhau() {

    }

    @Override
    public void autoDangNhap(FirebaseUser user, boolean isSuccess) {

    }

    @Override
    public void luuthongtinnguoidungthanhcong(boolean iscoluu) {

    }

    @Override
    public void dangxuat() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }


    @Override
    public void thongtintaikhoan(objnguoidungs user, Drawable hinhnen) {
        if(hinhnen!=null)
            anhnen.setImageDrawable(hinhnen);
        if(user!=null)
            tvHoTen.setText(user.getHoten());
    }

    @Override
    public void capnhatthongtin(boolean isSuccess) {

    }



    @Override
    public void taianhnenthanhcong(String path) {

    }


}
