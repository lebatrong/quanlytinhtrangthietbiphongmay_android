package com.lbt.batro.tinhtrangthietbi.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objchitietthietbimaytinh;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objlichsu_maytinhs;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objlichsu_thietbikhacs;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objthietbikhacs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class lbaocaotinhtrang {
    public static final String TAG = "lbaocaotinhtrang";

    FirebaseDatabase mDatabase;
    ibaocaotinhtrang minterface;

    int count;


    public lbaocaotinhtrang(ibaocaotinhtrang minterface) {
        this.minterface = minterface;
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void laythongtinthietbi(final String mamay){
        try{
            String maphong = mamay.substring(0,4);

            DatabaseReference mref = mDatabase.getReference("thietbis").child(maphong).child("maytinhs");
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean comay = false;
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        if(i.getValue().equals(mamay))
                            comay =  true;
                    }
                    if(comay)
                        minterface.laythongtinmay(true);
                    else
                        minterface.laythongtinmay(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, databaseError.toException().toString());
                    minterface.maqrkhonghopke();
                }
            });

        }catch (Exception e){
            minterface.maqrkhonghopke();
            Log.e(TAG,e.toString());
        }

    }



    public void capnhatlichsusuachua(final String mamay, final objchitietthietbimaytinh tbUpdate){
        try{
            //KIEM TRA MA MAY
            final String maphong = mamay.substring(0,4);
            DatabaseReference mref = mDatabase.getReference("thietbis").child(maphong).child("maytinhs");
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean mamaydung = false;
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        if(mamay.matches(i.getValue().toString())) {
                           mamaydung = true;
                           //THEM VÀO LỊCH SỬ CHỈNH SỬA

                            //ĐẾM SỐ LẦN CHỈNH SỬA
                            final DatabaseReference reflichsu = mDatabase.getReference("lichsusuachuas").child(maphong).child("maytinhs").child(mamay);
                            reflichsu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //Kiểm tra có lịch sử chỉnh sửa hay chưa
                                    count = 0;
                                    if(dataSnapshot.getValue()!=null) {
                                        List<objlichsu_maytinhs> ls = new ArrayList<>();
                                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                                            ls.add(i.getValue(objlichsu_maytinhs.class));
                                        }
                                        if(ls.get(ls.size()-1).isDasuachua())
                                            count = ls.size();
                                        else
                                            count = ls.size() -1;
                                    }

                                    //SET VALUE NODE
                                    objlichsu_maytinhs mtbupdate = new objlichsu_maytinhs();
                                    mtbupdate.setChitietsuachua(tbUpdate);
                                    mtbupdate.setDasuachua(false);
                                    mtbupdate.setEmailnguoibaocao(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                    Calendar cal = Calendar.getInstance();
                                    mtbupdate.setNgaybaocao(cal.getTimeInMillis());

                                    reflichsu.child(String.valueOf(count)).setValue(mtbupdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            minterface.capnhatthanhcong(true);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //CAP NHAT KHONG THANH CONG
                                            minterface.maqrkhonghopke();
                                            Log.e(TAG+"108",e.toString());
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    //CAP NHAT KHONG THANH CONG
                                    minterface.maqrkhonghopke();
                                    Log.e(TAG+"128",databaseError.toString());
                                }
                            });

                        }
                    }

                    //KIỂM TRA MÃ MÁY SAI BÁO LỖI
                    if(!mamaydung)
                        minterface.maqrkhonghopke();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    minterface.maqrkhonghopke();
                }
            });

        }catch (Exception e){
            minterface.maqrkhonghopke();
            Log.e(TAG+"132",e.toString());
        }
    }

    //Kiểm tra nếu máy chưa hư thì cho báo cáo
    public void kiemtratinhtrangmay(final String mamay){
        try{
            final String maphong = mamay.substring(0,4);
            DatabaseReference mref = mDatabase.getReference("lichsusuachuas").child(maphong).child("maytinhs").child(mamay);
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null) {
                        List<objlichsu_maytinhs> ls = new ArrayList<>();
                        //Lấy item cuối cùng kiểm tra xem đã sửa chữa hay chưa
                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                            ls.add(i.getValue(objlichsu_maytinhs.class));
                        }
                        if(ls.get(ls.size()-1).isDasuachua())
                            minterface.maycontot(true, null,mamay);
                        else
                            minterface.maycontot(false, ls.get(ls.size()-1),mamay);
                    }else
                        //CHưa có item nào thì là tốt
                        minterface.maycontot(true,null,mamay);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, databaseError.toException().toString());
                    minterface.maqrkhonghopke();
                }
            });

        }catch (Exception e){
            minterface.maqrkhonghopke();
            Log.e(TAG,e.toString());
        }
    }

    public void capnhattinhtrangthietbikhac(objthietbikhacs default_values,
                                            final objthietbikhacs thietbihu,
                                            final objthietbikhacs thietbihumoi,
                                            final String maphong){

        //KIỂM TRA LOGIC
        if(thietbihu.getBan()+thietbihumoi.getBan() <= default_values.getBan() &&
                thietbihu.getDen()+thietbihumoi.getDen() <= default_values.getDen() &&
                thietbihu.getGhe()+thietbihumoi.getGhe() <= default_values.getGhe() &&
                thietbihu.getMaydieuhoa()+thietbihumoi.getMaydieuhoa() <= default_values.getMaydieuhoa() &&
                thietbihu.getModemwifi()+thietbihumoi.getModemwifi() <= default_values.getModemwifi() &&
                thietbihu.getSwitchmang()+thietbihumoi.getSwitchmang() <= default_values.getSwitchmang() &&
                thietbihu.getQuat()+thietbihumoi.getQuat() <= default_values.getQuat() &&
                thietbihu.getTivi()+thietbihumoi.getTivi() <= default_values.getTivi()){

            DatabaseReference mRef = mDatabase.getReference().child("lichsusuachuas").child(maphong).child("thietbikhacs");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null) {
                        GenericTypeIndicator<List<objlichsu_thietbikhacs>> gen = new GenericTypeIndicator<List<objlichsu_thietbikhacs>>() {};
                        List<objlichsu_thietbikhacs> mlistTBK = dataSnapshot.getValue(gen);

                        objlichsu_thietbikhacs thietbikhac = mlistTBK.get(mlistTBK.size() - 1);

                        //DA SUA CHỮA - THÊM MỚI
                        if (thietbikhac.isDasuachua()) {

                            objlichsu_thietbikhacs tbk = new objlichsu_thietbikhacs();
                            tbk.setDasuachua(false);
                            tbk.setEmailnguoibaocao(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            Calendar cal = Calendar.getInstance();
                            tbk.setNgaybaocao(cal.getTimeInMillis());
                            tbk.setChitietsuachua(thietbihumoi);

                            capnhat_thietbikhac(tbk, mlistTBK.size(), maphong);

                        } else {
                            //CHƯA SỬA - CẬP NHẬT LẠI
                            objthietbikhacs chitietold = thietbikhac.getChitietsuachua();

                            objthietbikhacs tbnew = new objthietbikhacs();
                            tbnew.setDen(chitietold.getDen() + thietbihumoi.getDen());
                            tbnew.setSwitchmang(chitietold.getSwitchmang() + thietbihumoi.getSwitchmang());
                            tbnew.setGhe(chitietold.getGhe() + thietbihumoi.getGhe());
                            tbnew.setMaydieuhoa(chitietold.getMaydieuhoa() + thietbihumoi.getMaydieuhoa());
                            tbnew.setModemwifi(chitietold.getModemwifi() + thietbihumoi.getModemwifi());
                            tbnew.setTivi(chitietold.getTivi() + thietbihumoi.getTivi());
                            tbnew.setQuat(chitietold.getQuat() + thietbihumoi.getQuat());
                            tbnew.setBan(chitietold.getBan() + thietbihumoi.getBan());

                            objlichsu_thietbikhacs update = new objlichsu_thietbikhacs();
                            update.setChitietsuachua(tbnew);
                            update.setDasuachua(thietbikhac.isDasuachua());
                            update.setEmailnguoibaocao(thietbikhac.getEmailnguoibaocao());
                            update.setNgaybaocao(thietbikhac.getNgaybaocao());

                            Gson gson = new Gson();
                            Log.e(TAG, gson.toJson(update));

                            capnhat_thietbikhac(update, mlistTBK.size() - 1, maphong);

                        }
                    }else //KHÔNG CÓ GIÁ TRỊ THÊM MỚI
                    {
                        objlichsu_thietbikhacs tbk = new objlichsu_thietbikhacs();
                        tbk.setDasuachua(false);
                        tbk.setEmailnguoibaocao(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        Calendar cal = Calendar.getInstance();
                        tbk.setNgaybaocao(cal.getTimeInMillis());
                        tbk.setChitietsuachua(thietbihumoi);

                        capnhat_thietbikhac(tbk, 0, maphong);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    minterface.capnhatthanhcong(false);
                    Log.e(TAG,databaseError.toString());
                }
            });

        }else{ //GIÁ TRỊ KHÔNG HỢP LỆ
            minterface.giatrikhonghople();
        }
    }


    private void capnhat_thietbikhac(objlichsu_thietbikhacs chitiet, int position, String maphong){
        DatabaseReference mRef = mDatabase.getReference()
                .child("lichsusuachuas")
                .child(maphong).child("thietbikhacs")
                .child(String.valueOf(position));

        mRef.setValue(chitiet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        minterface.baocaothietbikhacthanhcong(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                minterface.capnhatthanhcong(false);
            }
        });
    }
}
