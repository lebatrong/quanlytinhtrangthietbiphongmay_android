package lbt.com.manager;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import lbt.com.manager.Presenter.iDangNhap;
import lbt.com.manager.Presenter.lDangNhap;

public class SplatScreenActivity extends AppCompatActivity implements iDangNhap {


    lDangNhap mdangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splat_screen);


        mdangnhap = new lDangNhap(this,this);
        mdangnhap.Auto_kiemtradangnhap();

    }

    @Override
    public void dangnhapthatbai() {

    }

    @Override
    public void dangnhapthanhcong() {

    }

    @Override
    public void saitendangnhap() {

    }

    @Override
    public void saimatkhau() {

    }

    @Override
    public void bankhongcoquyentruycap() {

    }

    @Override
    public void loitruyxuatdulieu() {

    }

    @Override
    public void autoDangNhap(FirebaseUser user, boolean isSuccess) {
        if(isSuccess)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplatScreenActivity.this,MainActivity.class));
                    finish();
                }
            },700);
        }else{
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public void dangxuat() {

    }
}
