package lbt.com.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import lbt.com.manager.Models.Firebase.objchitietthietbimaytinh;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Presenter.iCapNhatTinhTrang;
import lbt.com.manager.Presenter.lCapNhatTinhTrang;

public class TinhTrangThietBiActivity extends AppCompatActivity implements iCapNhatTinhTrang {

    lCapNhatTinhTrang mCapNhat;

    ImageView imvbanphim,imvchuot,imvcpu,imvmanhinh,imvos;
    EditText edtkhac,edtphanmem;
    TextView tvngaybaocao,tvtinhtrang,tvnguoibaocao;
    Toolbar toolbar;
    Button btnDaSua;

    ProgressDialog mProgress;

    String mamay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinh_trang_thiet_bi);
        initView();
        actionDaSua();
    }

    private void showPro(String content){
        mProgress.setCancelable(false);
        mProgress.setMessage(content);
        mProgress.show();
    }

    private void actionDaSua() {
        btnDaSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TinhTrangThietBiActivity.this);
                builder.setMessage(getText(R.string.dasuaxong)+" "+ mamay +" ?");
                builder.setNegativeButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCapNhat.capnhattinhtrang(mamay);
                        showPro(getText(R.string.dangtaidulieu).toString());
                    }
                });
                builder.setPositiveButton(getText(R.string.huy), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                Dialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void settup(objlichsu_maytinhs mls, String mamay) {
        objchitietthietbimaytinh mthietbi = mls.getChitietsuachua();
        if(mls.isDasuachua())
            btnDaSua.setVisibility(View.GONE);
        else
            btnDaSua.setVisibility(View.VISIBLE);

        if(mthietbi.isBanphim())
            imvbanphim.setImageResource(R.drawable.circlecheck);
        else
            imvbanphim.setImageResource(R.drawable.circlenocheck);

        if(mthietbi.isHedieuhanh())
            imvos.setImageResource(R.drawable.circlecheck);
        else
            imvos.setImageResource(R.drawable.circlenocheck);

        if(mthietbi.isChuot())
            imvchuot.setImageResource(R.drawable.circlecheck);
        else
            imvchuot.setImageResource(R.drawable.circlenocheck);

        if(mthietbi.isCpu())
            imvcpu.setImageResource(R.drawable.circlecheck);
        else
            imvcpu.setImageResource(R.drawable.circlenocheck);

        if(mthietbi.isManhinh())
            imvmanhinh.setImageResource(R.drawable.circlecheck);
        else
            imvmanhinh.setImageResource(R.drawable.circlenocheck);

        edtkhac.setText(mthietbi.getKhac());
        edtphanmem.setText(mthietbi.getPhanmem());

        toolbar.setTitle(mamay);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mls.getNgaybaocao());
        tvngaybaocao.setText(format.format(calendar.getTime()));
        tvnguoibaocao.setText(mls.getEmailnguoibaocao());

        if(mls.isDasuachua()){
            tvtinhtrang.setText(getText(R.string.dasua));
            tvtinhtrang.setTextColor(Color.GREEN);
        }else{
            tvtinhtrang.setText(getText(R.string.chuasua));
            tvtinhtrang.setTextColor(Color.RED);
        }
    }

    private void initView() {
        mCapNhat = new lCapNhatTinhTrang(this);
        mProgress = new ProgressDialog(this);
        //INIT VIEW
        imvbanphim = findViewById(R.id.imvcheckbanphimdialog);
        imvchuot = findViewById(R.id.imvcheckchuotdialog);
        imvcpu = findViewById(R.id.imvcheckcpudialog);
        imvmanhinh = findViewById(R.id.imvcheckmahinhdialog);
        imvos = findViewById(R.id.imvcheckosdialog);
        edtkhac = findViewById(R.id.edtkhacdialog);
        edtphanmem = findViewById(R.id.edtphanmemdialog);
        tvngaybaocao = findViewById(R.id.tvngaybaocaodialog);
        tvtinhtrang = findViewById(R.id.tvtinhtrangdialog);
        tvnguoibaocao = findViewById(R.id.tvnguoibaocaodialog);
        toolbar = findViewById(R.id.toolbartinhtrangthietbi);

        btnDaSua = findViewById(R.id.btndasua);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle!=null){
            mamay = bundle.getString("mamay");
            settup((objlichsu_maytinhs) bundle.getSerializable("mls"),mamay);
        }else{
            onBackPressed();
            finish();
        }



    }

    @Override
    public void laythongtinmay(boolean isSuccess) {

    }

    @Override
    public void capnhatthanhcong(boolean isthanhcong) {
        mProgress.dismiss();
        if(isthanhcong){
            Toast.makeText(this, R.string.capnhatthanhcong, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, R.string.capnhatkhongthanhcong, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void maycontot(boolean isTot, objlichsu_maytinhs mls, String mamay) {

    }

    @Override
    public void maqrkhonghopke() {

    }
}
