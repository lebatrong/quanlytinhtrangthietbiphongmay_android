package lbt.com.manager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Models.Firebase.objthietbikhacs;
import lbt.com.manager.Presenter.iPhongMay;
import lbt.com.manager.Presenter.lPhongMay;
import lbt.com.manager.customAdapter.aRclvDanhSachMayTinh;

public class PhongMayActivity extends AppCompatActivity implements iPhongMay {

    Toolbar toolbar;
    objPhongMay mObjPhong;
    lPhongMay mphongmay;

    CardView crvmain;

    ProgressDialog mProgress;

    EditText edtKhac;
    TextView tvban, tvbanhu,tvbanbinhthuong;
    TextView tvghe,tvghehu,tvghebinhthuong;
    TextView tvtivi,tvtivihu,tvtivibinhthuong;
    TextView tvquat,tvquathu,tvquatbinhthuong;
    TextView tvden,tvdenhu,tvdenbinhthuong;
    TextView tvmodem,tvmodemhu,tvmodembinhthuong;
    TextView tvswitch,tvswitchhu,tvswitchbinhthuong;
    TextView tvdieuhoa,tvdieuhoahu,tvdieuhoabinhthuong;

    TextView tvtongmaytinh, tvbtmaytinh,tvhumaytinh;
    TextView tvtongchuot,tvbtchuot,tvhuchuot;
    TextView tvtongbanphim,tvbtbanphim,tvhubanphim;
    TextView tvtongmanhinh,tvbtmanhinh,tvhumanhinh;
    TextView tvtongcpu,tvbtcpu,tvhucpu;

    TextView tvTitleMayTinh, tvTitleTBKhac;

    objthietbikhacs mThietbi_default,mThietBi_Hu;

    LinearLayout lnlpro,lnlmaytinh,lnltbk;
    Button btndanhsachmaytinh,btndasuaxong;
    boolean isvisible_mt = true;
    boolean isvisible_tbk = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_may);
        initView();

        actionViGoneTitle();
        actionChiTietMayTinh();
        actiondasuxong();
    }

    private void actiondasuxong() {
        btndasuaxong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void showProgress(String mess){
        mProgress.setCancelable(false);
        mProgress.setMessage(mess);
        mProgress.show();
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.bandansuaxong));
        builder.setNegativeButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mphongmay.suaxongtatcathietbikhac(mObjPhong.getMaphong());
                showProgress(getText(R.string.dangtaidulieu).toString());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void actionViGoneTitle() {
        tvTitleTBKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isvisible_tbk = !isvisible_tbk;
                if(isvisible_tbk)
                    lnltbk.setVisibility(View.VISIBLE);
                else
                    lnltbk.setVisibility(View.GONE);
            }
        });

        tvTitleMayTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isvisible_mt = !isvisible_mt;
                if(isvisible_mt)
                    lnlmaytinh.setVisibility(View.VISIBLE);
                else
                    lnlmaytinh.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        mProgress = new ProgressDialog(this);
        mphongmay = new lPhongMay(this);

        String title = "Chi tiết phong máy";
        Gson gson = new Gson();
        SharedPreferences spf = getSharedPreferences("data", Context.MODE_PRIVATE);
        String strPhong = spf.getString("phongmay",null);
        if(strPhong==null)
            finish();
        mObjPhong = gson.fromJson(strPhong,objPhongMay.class);
        title = mObjPhong.getTenphong();

        mphongmay.getChiTietPhongMay(mObjPhong.getMaphong());


        //SETUP TOOLBAR
        toolbar = findViewById(R.id.toolbarchitietphongmay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        lnlpro  = findViewById(R.id.lnlproctp);
        lnlmaytinh  = findViewById(R.id.lnlmaytinhthongke_ctp);
        lnltbk  = findViewById(R.id.lnlthietbikhacthongke_ctp);
        crvmain = findViewById(R.id.cardviewMain_ctp);

        tvban = findViewById(R.id.tvtongban_ctp);
        tvbanhu = findViewById(R.id.tvhuban_ctp);
        tvbanbinhthuong = findViewById(R.id.tvbinhthuongban_ctp);

        tvghe = findViewById(R.id.tvtongghe_ctp);
        tvghehu = findViewById(R.id.tvhughe_ctp);
        tvghebinhthuong = findViewById(R.id.tvbinhthuongghe_ctp);

        tvden = findViewById(R.id.tvtongden_ctp);
        tvdenhu = findViewById(R.id.tvhuden_ctp);
        tvdenbinhthuong = findViewById(R.id.tvbinhthuongden_ctp);

        tvtivi = findViewById(R.id.tvtongtivi_ctp);
        tvtivihu = findViewById(R.id.tvhutivi_ctp);
        tvtivibinhthuong = findViewById(R.id.tvbinhthuongtivi_ctp);

        tvswitch = findViewById(R.id.tvtongswitchmang_ctp);
        tvswitchhu = findViewById(R.id.tvhuswitchmang_ctp);
        tvswitchbinhthuong = findViewById(R.id.tvbinhthuongswitchmang_ctp);

        tvmodem = findViewById(R.id.tvtongmodemwifi_ctp);
        tvmodemhu = findViewById(R.id.tvhumodemwifi_ctp);
        tvmodembinhthuong = findViewById(R.id.tvbinhthuongmodemwifi_ctp);

        tvdieuhoa = findViewById(R.id.tvtongdieuhoa_ctp);
        tvdieuhoahu = findViewById(R.id.tvhudieuhoa_ctp);
        tvdieuhoabinhthuong = findViewById(R.id.tvbinhthuongdieuhoa_ctp);

        tvquat = findViewById(R.id.tvtongquat_ctp);
        tvquathu = findViewById(R.id.tvhuquat_ctp);
        tvquatbinhthuong = findViewById(R.id.tvbinhthuongquat_ctp);

        edtKhac = findViewById(R.id.edtKhac_ctp);

        btndanhsachmaytinh = findViewById(R.id.btnchitietmaytinh_ctp);
        btndasuaxong = findViewById(R.id.btndasuaxongtatca);

        tvtongmaytinh = findViewById(R.id.tvtongmaytinh_ctp);
        tvhumaytinh = findViewById(R.id.tvhumaytinh_ctp);
        tvbtmaytinh = findViewById(R.id.tvbinhthuongmaytinh_ctp);

        tvtongmanhinh = findViewById(R.id.tvtongmanhinh_ctp);
        tvhumanhinh = findViewById(R.id.tvhumanhinh_ctp);
        tvbtmanhinh = findViewById(R.id.tvbinhthuongmanhinh_ctp);

        tvtongbanphim = findViewById(R.id.tvtongbanphim_ctp);
        tvhubanphim = findViewById(R.id.tvhubanphim_ctp);
        tvbtbanphim = findViewById(R.id.tvbinhthuongbanphim_ctp);

        tvtongcpu = findViewById(R.id.tvtongcpu_ctp);
        tvhucpu = findViewById(R.id.tvhucpu_ctp);
        tvbtcpu = findViewById(R.id.tvbinhthuongcpu_ctp);

        tvtongchuot = findViewById(R.id.tvtongchuot_ctp);
        tvhuchuot = findViewById(R.id.tvhuchuot_ctp);
        tvbtchuot = findViewById(R.id.tvbinhthuongchuot_ctp);


        tvTitleMayTinh = findViewById(R.id.tvtitlemaytinh_ctp);
        tvTitleTBKhac = findViewById(R.id.tvtitlethietbikhac_ctp);

        //SETUP PROGRESS BAR
        lnlpro.setVisibility(View.VISIBLE);
        crvmain.setVisibility(View.GONE);

    }


    @Override
    public void loitaidulieu() {
        mProgress.dismiss();
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void actionChiTietMayTinh() {
        btndanhsachmaytinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("phong",mObjPhong);
                Intent intent = new Intent(PhongMayActivity.this,DanhSachMayTinhActivity.class);
                intent.putExtra("dataphong",bundle);
                startActivity(intent);
            }
        });
    }



    @Override
    public void phongmaydangxaydung() {
        Toast.makeText(this, getText(R.string.phongmaydangxaydung), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void thongkethietbikhac(objthietbikhacs default_values, objthietbikhacs hu) {

        mThietbi_default = default_values;
        mThietBi_Hu = hu;

        tvban.setText(String.valueOf(default_values.getBan()));
        tvbanhu.setText(String.valueOf(hu.getBan()));
        tvbanbinhthuong.setText(String.valueOf(default_values.getBan() - hu.getBan()));

        tvghe.setText(String.valueOf(default_values.getGhe()));
        tvghehu.setText(String.valueOf(hu.getGhe()));
        tvghebinhthuong.setText(String.valueOf(default_values.getGhe() - hu.getGhe()));

        tvden.setText(String.valueOf(default_values.getDen()));
        tvdenhu.setText(String.valueOf(hu.getDen()));
        tvdenbinhthuong.setText(String.valueOf(default_values.getDen() - hu.getDen()));

        tvtivi.setText(String.valueOf(default_values.getTivi()));
        tvtivihu.setText(String.valueOf(hu.getTivi()));
        tvtivibinhthuong.setText(String.valueOf(default_values.getTivi() - hu.getTivi()));

        tvquat.setText(String.valueOf(default_values.getQuat()));
        tvquathu.setText(String.valueOf(hu.getQuat()));
        tvquatbinhthuong.setText(String.valueOf(default_values.getQuat() - hu.getQuat()));

        tvdieuhoa.setText(String.valueOf(default_values.getMaydieuhoa()));
        tvdieuhoahu.setText(String.valueOf(hu.getMaydieuhoa()));
        tvdieuhoabinhthuong.setText(String.valueOf(default_values.getMaydieuhoa() - hu.getMaydieuhoa()));

        tvmodem.setText(String.valueOf(default_values.getModemwifi()));
        tvmodemhu.setText(String.valueOf(hu.getModemwifi()));
        tvmodembinhthuong.setText(String.valueOf(default_values.getModemwifi() - hu.getModemwifi()));

        tvswitch.setText(String.valueOf(default_values.getSwitchmang()));
        tvswitchhu.setText(String.valueOf(hu.getSwitchmang()));
        tvswitchbinhthuong.setText(String.valueOf(default_values.getSwitchmang() - hu.getSwitchmang()));

        edtKhac.setText(hu.getKhac());

        //SETUP PROGRESS BAR
        lnlpro.setVisibility(View.GONE);
        crvmain.setVisibility(View.VISIBLE);

        if(!tvbanhu.getText().toString().matches("0")&&
                !tvghehu.getText().toString().matches("0")&&
                !tvtivihu.getText().toString().matches("0")&&
                !tvdenhu.getText().toString().matches("0")&&
                !tvdieuhoahu.getText().toString().matches("0")&&
                !tvquathu.getText().toString().matches("0")&&
                !tvmodemhu.getText().toString().matches("0")&&
                !tvswitchhu.getText().toString().matches("0"))
        {
                btndasuaxong.setVisibility(View.VISIBLE);

        }else{
            if(!edtKhac.getText().toString().matches(""))
                btndasuaxong.setVisibility(View.VISIBLE);
            else
                btndasuaxong.setVisibility(View.GONE);
        }
    }

    @Override
    public void thongkemaytinh(boolean phongmayhu, objthongkemaytinh_app thongke) {
        if(phongmayhu){
            tvtongmaytinh.setText(String.valueOf(thongke.getMaytinh()));
            tvtongbanphim.setText(String.valueOf(thongke.getBanphim()));
            tvtongcpu.setText(String.valueOf(thongke.getCpu()));
            tvtongmanhinh.setText(String.valueOf(thongke.getManhinh()));
            tvtongchuot.setText(String.valueOf(thongke.getChuot()));

            tvhumaytinh.setText(String.valueOf(thongke.getMaytinhhu()));
            tvhubanphim.setText(String.valueOf(thongke.getBanphimhu()));
            tvhuchuot.setText(String.valueOf(thongke.getChuothu()));
            tvhucpu.setText(String.valueOf(thongke.getCpuhu()));
            tvhumanhinh.setText(String.valueOf(thongke.getManhinhhu()));

            tvbtmaytinh.setText(String.valueOf(thongke.getMaytinhbt()));
            tvbtbanphim.setText(String.valueOf(thongke.getBanphimbt()));
            tvbtchuot.setText(String.valueOf(thongke.getChuotbt()));
            tvbtcpu.setText(String.valueOf(thongke.getCpubt()));
            tvbtmanhinh.setText(String.valueOf(thongke.getManhinhbt()));

        }else{

            tvtongmaytinh.setText(String.valueOf(thongke.getMaytinh()));
            tvtongbanphim.setText(String.valueOf(thongke.getBanphim()));
            tvtongcpu.setText(String.valueOf(thongke.getCpu()));
            tvtongmanhinh.setText(String.valueOf(thongke.getManhinh()));
            tvtongchuot.setText(String.valueOf(thongke.getChuot()));

            tvhumaytinh.setText("0");
            tvhubanphim.setText("0");
            tvhuchuot.setText("0");
            tvhucpu.setText("0");
            tvhumanhinh.setText("0");

            tvbtmaytinh.setText("0");
            tvbtbanphim.setText("0");
            tvbtchuot.setText("0");
            tvbtcpu.setText("0");
            tvbtmanhinh.setText("0");

        }
    }

    @Override
    public void danhsachmaytinh(ArrayList<objthietbimaytinh_app> list) {

    }

    @Override
    public void capnhatthanhcong(boolean isSuccess) {
        mProgress.dismiss();
        if(isSuccess)
            Toast.makeText(this, getText(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getText(R.string.capnhatkhongthanhcong), Toast.LENGTH_SHORT).show();
    }
}
