package lbt.com.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Presenter.iLichSu;
import lbt.com.manager.Presenter.lLichSu;
import lbt.com.manager.customAdapter.aExplvLichSu;

public class LichSuActivity extends AppCompatActivity implements iLichSu {

    Toolbar toolbar;
    ExpandableListView explist;
    aExplvLichSu adapter;
    List<objLichSu> mList;
    lLichSu mLichSu;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        initView();
        mLichSu.getLichSuMayTinh();
        actionExpListView();
    }

    private void actionExpListView() {
        explist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String mamay = mList.get(groupPosition).getMamay();
                objlichsu_maytinhs mLs = mList.get(groupPosition).getLichsu().get(childPosition);

                Bundle bundle = new Bundle();
                bundle.putSerializable("mls",  mLs);
                bundle.putString("mamay",mamay);
                Intent intent = new Intent(LichSuActivity.this,TinhTrangThietBiActivity.class);
                intent.putExtra("data",bundle);
                startActivity(intent);

                return false;
            }
        });
    }


    private void initView() {
        mLichSu = new lLichSu(this,this);
        toolbar = findViewById(R.id.toolbarlichsu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        explist = findViewById(R.id.expLvLichSu);
        progress = findViewById(R.id.progressBarLichSu);
        explist.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void loidulieu() {
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lichsutrong() {
        Toast.makeText(this, getText(R.string.khongcolichsu), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getlistlichsu(List<objLichSu> list) {
        explist.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        mList = new ArrayList<>();
        mList = list;
        adapter = new aExplvLichSu(this,list);
        explist.setAdapter(adapter);
    }
}
