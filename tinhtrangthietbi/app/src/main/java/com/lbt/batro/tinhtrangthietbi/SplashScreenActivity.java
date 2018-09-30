package com.lbt.batro.tinhtrangthietbi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.lbt.batro.tinhtrangthietbi.Presenter.idangnhap;
import com.lbt.batro.tinhtrangthietbi.Presenter.ldangnhap;

public class SplashScreenActivity extends AppCompatActivity implements idangnhap  {


    ldangnhap mdangnhap;
    @Override
    protected void onStart() {
        super.onStart();
        mdangnhap = new ldangnhap(this,this);
        mdangnhap.Auto_kiemtradangnhap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
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
        if(isSuccess && mdangnhap.kiemtrathongtinnguoidunglocal())
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                    finish();
                }
            },700);
        }else{
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public void luuthongtinnguoidungthanhcong(boolean iscoluu) {
        if(iscoluu){
            startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
            finish();
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                    finish();
                }
            },700);
        }
    }


    @Override
    public void dangxuat() {

    }


}
