package com.lbt.batro.tinhtrangthietbi.Presenter;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objphongmay;

import java.util.ArrayList;

public class lphongmay {

    FirebaseDatabase mDatabase;
    iphongmay mThongKe;

    public lphongmay(iphongmay mThongKe) {
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mThongKe = mThongKe;
    }

    public void getdanhsachphongmay(){
        DatabaseReference mRef = mDatabase.getReference("phongmays");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<objphongmay> mlist = new ArrayList<>();
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    mlist.add(i.getValue(objphongmay.class));
                }
                mThongKe.danhsachphongmay(mlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}
