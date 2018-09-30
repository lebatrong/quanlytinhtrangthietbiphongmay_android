package lbt.com.manager.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;

public class lThongBao {
    public static final String TAG = "lThongBao";


    FirebaseDatabase mDatabase;
    iThongBao mInterface;
    Context context;

    int countThongBao = 0;


    public lThongBao(iThongBao mInterface,Context context) {
        this.mInterface = mInterface;
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
    }

//    public void LangNgeThongBao(final String maphong){
//        mListThietBi = new ArrayList<>();
//        //LẮNG NGHE SỰ KIỆN CỦA PHÒNG MÁY DO USER QUẢN LÝ
//        DatabaseReference mTB = mDatabase.getReference().child("lichsusuachuas").child(maphong);
//        mTB.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                isMayHu = false;
//                if(dataSnapshot.getValue()!=null) {
//                    count = 0;
//                    //DUYỆT QUA TẤT CẢ CÁC MÁY KIỂM TRA MÁY NÀO HƯ
//                    for (DataSnapshot i : dataSnapshot.getChildren()) {
//                        DatabaseReference mref =  mDatabase.getReference().child("lichsusuachuas").child(maphong).child(i.getKey());
//                        mref.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                if(dataSnapshot.getValue()!=null) {
//                                    GenericTypeIndicator<List<objlichsu_maytinhs>> mType = new GenericTypeIndicator<List<objlichsu_maytinhs>>() {};
//                                    List<objlichsu_maytinhs> mList = dataSnapshot.getValue(mType);
//                                    //NẾU MÁY CHƯA SỬA THÌ THÔNG BÁO
//                                    if (!mList.get(mList.size() - 1).isDasuachua()) {
//                                        objThietBi mthietbi = new objThietBi();
//                                        mthietbi.setLichsusuachua(mList.get(mList.size() - 1));
//                                        mthietbi.setMathietbi(dataSnapshot.getKey());
//
//                                        mListThietBi.add(mthietbi);
//
//                                        mInterface.thietbihu(mthietbi);
//                                        mInterface.pushthongbao(mthietbi);
//                                        isMayHu = true;
//                                    }
//                                }else{
//                                    mInterface.phongmayhoatdongtot();
//                                }
//                                if(count == dataSnapshot.getChildrenCount() && !isMayHu){
//                                    mInterface.phongmayhoatdongtot();
//                                }
//                                count++;
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Log.e(TAG, databaseError.toString());
//                                mInterface.loiduongtruyen();
//                            }
//                        });
//                    }
//                }else{
//                    mInterface.phongmayhoatdongtot();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAG,databaseError.toString());
//                mInterface.loiduongtruyen();
//            }
//        });
//    }


    public void LangNgeThongBao(final String maphong){

        //LẮNG NGHE SỰ KIỆN CỦA PHÒNG MÁY DO USER QUẢN LÝ
        DatabaseReference mTB = mDatabase.getReference().child("lichsusuachuas").child(maphong);
        mTB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {

                    //PHÂN TÁCH DATA
                    //===========================================
                    // NHÁNH MAYTINHS
                    GenericTypeIndicator<List<objlichsu_maytinhs>> gen = new GenericTypeIndicator<List<objlichsu_maytinhs>>(){};
                    List<objLichSu> mList = new ArrayList<>();
                    for (DataSnapshot i : dataSnapshot.child("maytinhs").getChildren()) {
                        List<objlichsu_maytinhs> list = i.getValue(gen);
                        mList.add(new objLichSu(i.getKey(),list));
                    }


                    List<objThietBi> mTB = new ArrayList<>();
                    //GÁN LIST THIẾT BỊ HƯ
                    for (objLichSu j: mList) {
                        if(!j.getLichsu().get(j.getLichsu().size()-1).isDasuachua()){
                            objThietBi obj = new objThietBi();
                            obj.setMathietbi(j.getMamay());
                            obj.setLichsusuachua(j.getLichsu().get(j.getLichsu().size()-1));
                            mTB.add(obj);
                        }
                    }


                    if(mTB.size()!=0)
                        mInterface.pushthongbao(mTB);
                    else
                        mInterface.phongmayhoatdongtot();




                }else{
                    mInterface.phongmayhoatdongtot();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.toString());
                mInterface.loiduongtruyen();
            }
        });
    }


}
