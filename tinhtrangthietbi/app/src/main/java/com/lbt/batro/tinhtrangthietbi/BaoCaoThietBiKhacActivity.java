package com.lbt.batro.tinhtrangthietbi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lbt.batro.tinhtrangthietbi.Presenter.ibaocaotinhtrang;
import com.lbt.batro.tinhtrangthietbi.Presenter.lbaocaotinhtrang;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objlichsu_maytinhs;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objthietbikhacs;

public class BaoCaoThietBiKhacActivity extends AppCompatActivity implements ibaocaotinhtrang {

    Toolbar toolbar;
    Button btnGuiBaoCao;

    objthietbikhacs mThietbi_default,mThietBi_Hu;

    String maphong;

    lbaocaotinhtrang mbaocao;

    EditText edtban,edtghe,edtquat,edtden,edttivi,edtswitch,edtmodem,edtmaydieuhoa,edtkhac;
    TextView tvban, tvhuban,
            tvghe, tvhughe,
            tvden,tvhuden,
            tvtivi,tvhutivi,
            tvquat,tvhuquat,
            tvswitch,tvhuswitch,
            tvmaydieuhoa,tvhumaydieuhoa,
            tvmodem,tvhumodem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_thiet_bi_khac);

        initView();
        setupdefault();

        actionbaocao();
    }

    private void actionbaocao() {
        btnGuiBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    objthietbikhacs capnhat = new objthietbikhacs();
                    capnhat.setKhac(edtkhac.getText().toString());

                    String strquat = edtquat.getText().toString();
                    String strban = edtban.getText().toString();
                    String strghe = edtghe.getText().toString();
                    String strtivi = edttivi.getText().toString();
                    String strmodem = edtmodem.getText().toString();
                    String strswitch = edtswitch.getText().toString();
                    String strden = edtden.getText().toString();
                    String strdieuhoa = edtmaydieuhoa.getText().toString();

                    long quat,den,ban,tivi,maydieuhoa,switchmang,ghe,modem;

                    if(strquat.isEmpty())
                        quat = 0;
                    else
                        quat = Long.parseLong(strquat);

                    if(strban.isEmpty())
                        ban = 0;
                    else
                        ban = Long.parseLong(strban);

                    if(strghe.isEmpty())
                        ghe = 0;
                    else
                        ghe = Long.parseLong(strghe);

                    if(strtivi.isEmpty())
                        tivi = 0;
                    else
                        tivi = Long.parseLong(strtivi);

                    if(strmodem.isEmpty())
                        modem = 0;
                    else
                        modem = Long.parseLong(strmodem);

                    if(strswitch.isEmpty())
                        switchmang = 0;
                    else
                        switchmang = Long.parseLong(strswitch);

                    if(strden.isEmpty())
                        den = 0;
                    else
                        den = Long.parseLong(strden);

                    if(strdieuhoa.isEmpty())
                        maydieuhoa = 0;
                    else
                        maydieuhoa = Long.parseLong(strdieuhoa);


                    capnhat.setQuat(quat);
                    capnhat.setSwitchmang(switchmang);
                    capnhat.setDen(den);
                    capnhat.setGhe(ghe);
                    capnhat.setMaydieuhoa(maydieuhoa);
                    capnhat.setModemwifi(modem);
                    capnhat.setTivi(tivi);
                    capnhat.setBan(ban);



                    mbaocao.capnhattinhtrangthietbikhac(mThietbi_default,mThietBi_Hu,capnhat,maphong);

                }catch (Exception e){
                    Log.e("LỐI", e.toString());
                    Toast.makeText(BaoCaoThietBiKhacActivity.this, getText(R.string.giatrikhonghople), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void setupdefault() {
        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle!=null){

            mThietbi_default = (objthietbikhacs) bundle.getSerializable("defautl");
            mThietBi_Hu = (objthietbikhacs) bundle.getSerializable("hu");
            maphong = bundle.getString("maphong");

        }

        tvban.setText(String.valueOf(mThietbi_default.getBan()));
        tvghe.setText(String.valueOf(mThietbi_default.getGhe()));
        tvquat.setText(String.valueOf(mThietbi_default.getQuat()));
        tvden.setText(String.valueOf(mThietbi_default.getDen()));
        tvmaydieuhoa.setText(String.valueOf(mThietbi_default.getMaydieuhoa()));
        tvmodem.setText(String.valueOf(mThietbi_default.getModemwifi()));
        tvswitch.setText(String.valueOf(mThietbi_default.getSwitchmang()));
        tvtivi.setText(String.valueOf(mThietbi_default.getTivi()));


        tvhuban.setText(String.valueOf(mThietBi_Hu.getBan()));
        tvhughe.setText(String.valueOf(mThietBi_Hu.getGhe()));
        tvhuquat.setText(String.valueOf(mThietBi_Hu.getQuat()));
        tvhuden.setText(String.valueOf(mThietBi_Hu.getDen()));
        tvhumaydieuhoa.setText(String.valueOf(mThietBi_Hu.getMaydieuhoa()));
        tvhumodem.setText(String.valueOf(mThietBi_Hu.getModemwifi()));
        tvhuswitch.setText(String.valueOf(mThietBi_Hu.getSwitchmang()));
        tvhutivi.setText(String.valueOf(mThietBi_Hu.getTivi()));

    }

    private void initView() {

        mbaocao = new lbaocaotinhtrang(this);

        tvban = findViewById(R.id.tvtongban_bc);
        tvhuban = findViewById(R.id.tvhuban_bc);

        tvghe = findViewById(R.id.tvtongghe_bc);
        tvhughe = findViewById(R.id.tvhughe_bc);

        tvden = findViewById(R.id.tvtongden_bc);
        tvhuden = findViewById(R.id.tvhuden_bc);

        tvquat = findViewById(R.id.tvtongquat_bc);
        tvhuquat = findViewById(R.id.tvhuquat_bc);

        tvtivi = findViewById(R.id.tvtongtivi_bc);
        tvhutivi = findViewById(R.id.tvhutivi_bc);

        tvmodem = findViewById(R.id.tvtongmodem_bc);
        tvhumodem = findViewById(R.id.tvhumodem_bc);

        tvswitch = findViewById(R.id.tvtongswitch_bc);
        tvhuswitch = findViewById(R.id.tvhuswitch_bc);

        tvmaydieuhoa = findViewById(R.id.tvtongdieuhoa_bc);
        tvhumaydieuhoa = findViewById(R.id.tvhudieuhoa_bc);

        edtban = findViewById(R.id.edtban_bc);
        edtghe = findViewById(R.id.edtghe_bc);
        edttivi = findViewById(R.id.edttivi_bc);
        edtden = findViewById(R.id.edtden_bc);
        edtswitch = findViewById(R.id.edtswitch_bc);
        edtmodem = findViewById(R.id.edtmodem_bc);
        edtquat = findViewById(R.id.edtquat_bc);
        edtkhac = findViewById(R.id.edtkhac_bc);
        edtmaydieuhoa = findViewById(R.id.edtdieuhoa_bc);

        btnGuiBaoCao = findViewById(R.id.btnGuiBaoCao_cb);

        toolbar = findViewById(R.id.toolbarbaocaotbk);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void laythongtinmay(boolean isSuccess) {

    }

    @Override
    public void capnhatthanhcong(boolean isthanhcong) {

    }

    @Override
    public void giatrikhonghople() {
        Toast.makeText(this, getText(R.string.giatrikhonghople), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void baocaothietbikhacthanhcong(boolean isCapNhat) {
        if(isCapNhat)
            Toast.makeText(this, getText(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getText(R.string.baocaothanhcong), Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void maycontot(boolean isTot, objlichsu_maytinhs mls, String mamay) {

    }

    @Override
    public void maqrkhonghopke() {

    }
}
