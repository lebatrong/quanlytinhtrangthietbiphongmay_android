package lbt.com.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.Firebase.objNguoiDung;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Presenter.iDangNhap;
import lbt.com.manager.Presenter.iTaiKhoan;
import lbt.com.manager.Presenter.iThongBao;
import lbt.com.manager.Presenter.lDangNhap;
import lbt.com.manager.Presenter.lTaiKhoan;
import lbt.com.manager.Presenter.lThongBao;

public class MainActivity extends AppCompatActivity implements iDangNhap, iTaiKhoan, iThongBao {

    CardView crvCaiDat,crvQRcode,crvTaiKhoan,crvPhong,crvThongBao,crvLichSu;
    lDangNhap mdangnhap;
    lTaiKhoan mTaiKhoan;
    lThongBao mThongBao;
    CircleImageView anhnen;
    TextView tvHoTen;
    Button btnDangXuat;

    ImageView imvNew;

    objPhongMay mPhongMay;

    int notifyID = 1;


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
        actionThongBao();
        actionLichSu();
    }

    private void actionLichSu() {
        crvLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LichSuActivity.class));
            }
        });
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
                startActivity(new Intent(MainActivity.this,TaiKhoanActivity.class));
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

    private void actionThongBao() {
        crvThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThongBaoActivity.class);
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
        mTaiKhoan = new lTaiKhoan(this,this);
        mdangnhap = new lDangNhap(this, this);
        mThongBao = new lThongBao(this,this);

        mTaiKhoan.getDataDefault();
        mTaiKhoan.laythongtintaikhoan();

        Gson gson = new Gson();
        SharedPreferences spf = getSharedPreferences("data",Context.MODE_PRIVATE);
        try {
            mPhongMay = gson.fromJson(spf.getString("phongmay", null), objPhongMay.class);
        }catch (Exception e){
            Log.e("Lỗi GSON",e.toString());
        }
        mThongBao.LangNgeThongBao(mPhongMay.getMaphong());

        crvCaiDat = findViewById(R.id.cardviewCaiDat);
        crvPhong = findViewById(R.id.cardviewPhong);
        crvQRcode = findViewById(R.id.cardviewQRCode);
        crvTaiKhoan = findViewById(R.id.cardviewTaiKhoan);
        crvThongBao = findViewById(R.id.cardviewThongBao);
        crvLichSu = findViewById(R.id.cardviewLichSu);

        imvNew = findViewById(R.id.imvnew);

        tvHoTen = findViewById(R.id.tvHoTenMain);
        anhnen = findViewById(R.id.imvhinhnenMain);
        btnDangXuat = findViewById(R.id.btnDangXuat);
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

    }

    @Override
    public void dangxuat() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }


    @Override
    public void thongtintaikhoan(objNguoiDung user, Drawable hinhnen) {
        if(hinhnen!=null)
            anhnen.setImageDrawable(hinhnen);
        if(user!=null)
            tvHoTen.setText(user.getHoten());
    }

    @Override
    public void capnhatthongtin(boolean isSuccess) {

    }

    @Override
    public void laythongtinkhongthanhcong() {
        Toast.makeText(this, R.string.mangkhongon, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bankhongconquyenquanly() {
        mdangnhap.dangxuat();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }


    @Override
    public void loiduongtruyen() {
        Toast.makeText(this, R.string.mangkhongon, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pushthongbao(List<objThietBi> thietBi) {
        // Sets an ID for the notification, so it can be updated.
        //int notifyID = 1;
        imvNew.setVisibility(View.VISIBLE);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, ThongBaoActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),intent,0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = getString(R.string.notifi);// The user-visible name of the channel.

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setDescription(getText(R.string.thongbaohu).toString());

            // Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(this)
                    .setContentTitle(getText(R.string.comayhu))
                    .setContentText(getText(R.string.thongbaohu))
                    .setSmallIcon(R.drawable.icnew)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .build();
            mNotificationManager.createNotificationChannel(mChannel);
            // Issue the notification.
            mNotificationManager.notify(notifyID , notification);

        }else{
            Notification notification = new Notification.Builder(this)
                    .setContentTitle(getText(R.string.comayhu))
                    .setContentText(getText(R.string.thongbaohu))
                    .setSmallIcon(R.drawable.icnew)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .build();
            mNotificationManager.notify(notifyID , notification);
        }
    }



    @Override
    public void phongmayhoatdongtot() {
        imvNew.setVisibility(View.GONE);
    }
}
