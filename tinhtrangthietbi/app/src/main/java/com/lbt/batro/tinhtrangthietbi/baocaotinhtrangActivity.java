package com.lbt.batro.tinhtrangthietbi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.lbt.batro.tinhtrangthietbi.Presenter.ibaocaotinhtrang;
import com.lbt.batro.tinhtrangthietbi.Presenter.lbaocaotinhtrang;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objchitietthietbimaytinh;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objlichsu_maytinhs;

public class baocaotinhtrangActivity extends AppCompatActivity implements ibaocaotinhtrang {

    private Toolbar toolbar;
    private CheckBox cbbanphim,cbchuot,cbmanhinh,cbcpu,cbbhedieuhanh;
    private EditText edtKhac,edtphanmem;
    private Button btnGui;
    private lbaocaotinhtrang mBaoCao;
    private ProgressDialog mPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaotinhtrang);
        initView();
        actionClick();
        actionGuiBaoCao();
    }

    private void actionGuiBaoCao() {
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mamay = toolbar.getTitle().toString();
                objchitietthietbimaytinh mtb = new objchitietthietbimaytinh();
                mtb.setBanphim(cbbanphim.isChecked());
                mtb.setChuot(cbchuot.isChecked());
                mtb.setCpu(cbcpu.isChecked());
                mtb.setManhinh(cbmanhinh.isChecked());
                mtb.setHedieuhanh(cbbhedieuhanh.isChecked());
                String khac = edtKhac.getText().toString();
                String phanmem = edtphanmem.getText().toString();
                if(TextUtils.isEmpty(khac))
                    mtb.setKhac("");
                else
                    mtb.setKhac(khac);

                if(TextUtils.isEmpty(phanmem))
                    mtb.setPhanmem("");
                else
                    mtb.setPhanmem(phanmem);

                showPro("Gửi báo cáo", "Đang gửi báo cáo vui lòng đợi...");
                mBaoCao.capnhatlichsusuachua(mamay,mtb);
            }
        });
    }

    private void actionClick() {
        cbcpu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cbcpu.setText(R.string.danghoatdong);
                    cbcpu.setChecked(true);
                }else{
                    cbcpu.setText(R.string.khonghoatdong);
                    cbcpu.setChecked(false);
                }
            }
        });

        cbbhedieuhanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cbbhedieuhanh.setText(R.string.danghoatdong);
                    cbbhedieuhanh.setChecked(true);
                }else{
                    cbbhedieuhanh.setText(R.string.khonghoatdong);
                    cbbhedieuhanh.setChecked(false);
                }
            }
        });

        cbmanhinh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cbmanhinh.setText(R.string.danghoatdong);
                    cbmanhinh.setChecked(true);
                }else{
                    cbmanhinh.setText(R.string.khonghoatdong);
                    cbmanhinh.setChecked(false);
                }
            }
        });

        cbchuot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cbchuot.setText(R.string.danghoatdong);
                    cbchuot.setChecked(true);
                }else{
                    cbchuot.setText(R.string.khonghoatdong);
                    cbchuot.setChecked(false);
                }
            }
        });

        cbbanphim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cbbanphim.setText(R.string.danghoatdong);
                    cbbanphim.setChecked(true);
                }else{
                    cbbanphim.setText(R.string.khonghoatdong);
                    cbbanphim.setChecked(false);
                }
            }
        });
    }

    private void initView() {
        mBaoCao = new lbaocaotinhtrang(this);
        mPro = new ProgressDialog(this);
        toolbar = findViewById(R.id.toolbarTinhTrangTB);
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
        objchitietthietbimaytinh mthietbi = new objchitietthietbimaytinh();
        String pc = getString(R.string.tinhtrangthietbi);
        if(bundle!=null)
        {
            pc = bundle.getString("qrcode") ;
        }
        toolbar.setTitle(pc);



        cbbanphim = findViewById(R.id.cbbanphim);
        cbchuot = findViewById(R.id.cbchuot);
        cbmanhinh = findViewById(R.id.cbmanhinh);
        cbcpu = findViewById(R.id.cbcpu);
        edtKhac = findViewById(R.id.edtkhac);
        btnGui = findViewById(R.id.btngiuibaocao);
        edtphanmem = findViewById(R.id.edtphanmem);
        cbbhedieuhanh = findViewById(R.id.cbbos);


    }

    private void showPro(String title,String content){
        mPro.setCancelable(false);
        mPro.setTitle(title);
        mPro.setMessage(content);
        mPro.show();
    }


    @Override
    public void laythongtinmay(boolean isSuccess) {

    }

    @Override
    public void capnhatthanhcong(boolean isthanhcong) {
        mPro.dismiss();
        Toast.makeText(this, getText(R.string.guibaocaothanhcong), Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }

    @Override
    public void giatrikhonghople() {

    }

    @Override
    public void baocaothietbikhacthanhcong(boolean isCapNhat) {

    }

    @Override
    public void maycontot(boolean isTot, objlichsu_maytinhs mls, String mamay) {

    }


    @Override
    public void maqrkhonghopke() {
        Toast.makeText(this, getText(R.string.guibaocaokhongthanhcong), Toast.LENGTH_SHORT).show();
    }
}
