package com.lbt.batro.tinhtrangthietbi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objchitietthietbimaytinh;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objlichsu_maytinhs;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class tinhtrangthietbi extends AppCompatActivity {

    ImageView imvbanphim,imvchuot,imvcpu,imvmanhinh,imvos;
    EditText edtkhac,edtphanmem;
    TextView tvngaybaocao,tvtinhtrang,tvnguoibaocao;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinhtrangthietbi);

        initView();

    }

    private void settup(objlichsu_maytinhs mls, String mamay) {
        objchitietthietbimaytinh mthietbi = mls.getChitietsuachua();
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
            settup((objlichsu_maytinhs) bundle.getSerializable("mls"),bundle.getString("mamay"));
        }else{
            onBackPressed();
            finish();
        }

    }


}
