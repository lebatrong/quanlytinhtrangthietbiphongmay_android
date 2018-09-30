package lbt.com.manager.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Models.Firebase.objPhongMay;

public class lLichSu {

    public static final String TAG = "lLichSu";

    FirebaseDatabase mDatabase;
    iLichSu mLichSu;
    Context context;

    public lLichSu(iLichSu mLichSu, Context context) {
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mLichSu = mLichSu;
        this.context = context;
    }

    public void getLichSuMayTinh(){
        final objPhongMay mPhongMay = getMyRoom();
        if(mPhongMay!=null){

            DatabaseReference mRef = mDatabase.getReference().child("lichsusuachuas").child(mPhongMay.getMaphong()).child("maytinhs");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null){

                        GenericTypeIndicator<List<objlichsu_maytinhs>> gen = new GenericTypeIndicator<List<objlichsu_maytinhs>>(){};
                        List<objLichSu> mList = new ArrayList<>();

                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                            List<objlichsu_maytinhs> list = i.getValue(gen);
                            mList.add(new objLichSu(i.getKey(),list));
                        }
                        mLichSu.getlistlichsu(mList);



                    }else
                        mLichSu.lichsutrong();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mLichSu.loidulieu();
                    Log.e(TAG,databaseError.toString());
                }
            });

        }else
            mLichSu.loidulieu();
    }

    public objPhongMay getMyRoom(){
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(spf.getString("phongmay",null),objPhongMay.class);
    }
}
