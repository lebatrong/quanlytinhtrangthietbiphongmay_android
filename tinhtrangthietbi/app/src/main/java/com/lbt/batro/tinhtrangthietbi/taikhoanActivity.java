package com.lbt.batro.tinhtrangthietbi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lbt.batro.tinhtrangthietbi.Presenter.itaikhoan;
import com.lbt.batro.tinhtrangthietbi.Presenter.ltaikhoan;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objnguoidungs;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class taikhoanActivity extends AppCompatActivity implements itaikhoan {

    Toolbar toolbar;
    TextInputLayout tilHoTen,tilTenDangNhap,tilQueQuan;
    TextView tvNgaySinh;
    RadioButton rdoNam,rdoNu;
    Button btnCapNhatThongTin, btnChinhSuaMatKhau;
    CircleImageView crlhinhnen;

    private int REQUEST_CODE_IMAGE = 1;
    private boolean isTaiHinh;
    ltaikhoan mTaiKhoan;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taikhoan);
        initView();
        actionCapnhat();
        actionChonHinhNen();
        actionchonngaysinh();
    }

    private void actionchonngaysinh() {
        tvNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(taikhoanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvNgaySinh.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, 1960, 01, 01);
                pickerDialog.show();
            }
        });
    }

    private void actionChonHinhNen() {
        crlhinhnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
    }

    private void actionCapnhat() {
        btnCapNhatThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress("Đang cập nhật thông tin...");
                objnguoidungs mNguoiDung = mTaiKhoan.getDataUser();
                try {
                    String ht = tilHoTen.getEditText().getText().toString();
                    String qq = tilQueQuan.getEditText().getText().toString();
                    String gt = "Nam";
                    if (rdoNu.isChecked())
                        gt = "Nữ";
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    calendar.setTime(format.parse(tvNgaySinh.getText().toString()));

                    long ngaysinh = calendar.getTimeInMillis();

                    mNguoiDung.setHoten(ht);
                    mNguoiDung.setGioitinh(gt);
                    mNguoiDung.setQuequan(qq);
                    mNguoiDung.setNgaysinh(ngaysinh);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!isTaiHinh) {
                    mTaiKhoan.capnhatthongtin(mNguoiDung);
                }else{
                    mTaiKhoan.uploadAvatar(crlhinhnen,mTaiKhoan.getDataUser().getHinhnen(),mNguoiDung);
                }
            }
        });
    }

    private void initView() {
        this.tilHoTen = findViewById(R.id.tilhoten);
        this.tilTenDangNhap = findViewById(R.id.tiltentaikhoan);
        this.tilQueQuan = findViewById(R.id.tilquequan);
        this.tvNgaySinh = findViewById(R.id.tvngaysinh);
        this.btnCapNhatThongTin = findViewById(R.id.btnCapNhatThongTin);
        this.btnChinhSuaMatKhau = findViewById(R.id.btnChinhSuaMatKhau);
        this.toolbar = findViewById(R.id.toolbarTaiKhoan);
        this.rdoNam = findViewById(R.id.rdonam);
        this.rdoNu = findViewById(R.id.rdonu);
        this.crlhinhnen = findViewById(R.id.crlhinhnen);

        mProgress = new ProgressDialog(this);

        mTaiKhoan = new ltaikhoan(this,this);

        mTaiKhoan.getDataDefault();
        mTaiKhoan.laythongtintaikhoan();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        isTaiHinh = false;
    }

    @Override
    public void thongtintaikhoan(objnguoidungs user, Drawable hinhnen) {
        crlhinhnen.setImageDrawable(hinhnen);
        tilQueQuan.getEditText().setText(user.getQuequan());
        tilHoTen.getEditText().setText(user.getHoten());
        tilTenDangNhap.getEditText().setText(user.getTendangnhap());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(user.getNgaysinh());
        tvNgaySinh.setText(dateFormat.format(calendar.getTime()));
        if(user.getGioitinh().equals("Nam"))
            rdoNam.setChecked(true);
        else
            rdoNu.setChecked(true);
    }

    private void showProgress(String content){
        mProgress.setMessage(content);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    public void capnhatthongtin(boolean isSuccess) {
        mProgress.dismiss();
        if(isSuccess)
            Toast.makeText(this, getText(R.string.capnhatthongtinthanhcong), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getText(R.string.capnhatthongtinthatbai), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void taianhnenthanhcong(String path) {
        try {
            String ht = tilHoTen.getEditText().getText().toString();
            String qq = tilQueQuan.getEditText().getText().toString();
            String gt = "Nam";
            if (rdoNu.isChecked())
                gt = "Nữ";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            calendar.setTime(format.parse(tvNgaySinh.getText().toString()));

            long ngaysinh = calendar.getTimeInMillis();

            objnguoidungs mNguoiDung = mTaiKhoan.getDataUser();
            mNguoiDung.setHoten(ht);
            mNguoiDung.setGioitinh(gt);
            mNguoiDung.setQuequan(qq);
            mNguoiDung.setNgaysinh(ngaysinh);
            mNguoiDung.setHinhnen(path);

            mTaiKhoan.capnhatthongtin(mNguoiDung);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE && data!=null){
            try {
                isTaiHinh = true;
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                crlhinhnen.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                isTaiHinh = false;
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
