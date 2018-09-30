package lbt.com.manager.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;

public class lCapNhatTinhTrang {

    public static final String TAG = "lCapNhatTinhTrang";

    FirebaseDatabase mDatabase;
    iCapNhatTinhTrang minterface;

    int count;


    public lCapNhatTinhTrang(iCapNhatTinhTrang minterface) {
        this.minterface = minterface;
        mDatabase = FirebaseDatabase.getInstance();
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

    public void capnhattinhtrang(String mamay){
        String maphong = mamay.substring(0,4);
        final DatabaseReference mRef = mDatabase.getReference().child("lichsusuachuas").child(maphong).child("maytinhs").child(mamay);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<objlichsu_maytinhs>> gen = new GenericTypeIndicator<List<objlichsu_maytinhs>>(){};
                List<objlichsu_maytinhs> mlist = dataSnapshot.getValue(gen);
                mRef.child(String.valueOf(mlist.size()-1)).child("dasuachua").setValue(true)
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        minterface.capnhatthanhcong(false);
                        Log.e(TAG,e.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        minterface.capnhatthanhcong(true);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                minterface.capnhatthanhcong(false);
                Log.e(TAG,databaseError.toString());
            }
        });
    }
}
