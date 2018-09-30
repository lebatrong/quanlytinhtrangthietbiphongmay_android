package lbt.com.manager.Presenter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.List;

import lbt.com.manager.Models.Firebase.objNguoiDung;
import lbt.com.manager.Models.Firebase.objPhongMay;

public class lDangNhap {
    private static final String TAG = "ldangnhap";

    private iDangNhap mDangNhap;
    private FirebaseAuth mAuth;
    private Context context;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;

    public lDangNhap(iDangNhap inter,Context context) {
        this.mDangNhap = inter;
        this.mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.mDatabase = FirebaseDatabase.getInstance();
    }

    public boolean kiemtratentaikhoan(String tentaikhoan, String matkhau){
        if(!TextUtils.isEmpty(tentaikhoan) && !TextUtils.isEmpty(matkhau)){
            return true;
        }else{
            if(TextUtils.isEmpty(tentaikhoan))
                mDangNhap.saitendangnhap();
            if(TextUtils.isEmpty(matkhau))
                mDangNhap.saimatkhau();

            return false;
        }
    }

    public void Auto_kiemtradangnhap(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null && kiemtrathongtinnguoidunglocal())
            mDangNhap.autoDangNhap(currentUser,true);
        else
            mDangNhap.autoDangNhap(null,false);
    }

    public void dangxuat(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.commit();
        mDangNhap.dangxuat();
    }

//    public void laythongtintaikhoan(){
//        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
//        String struser = spf.getString("user",null);
//        if(TextUtils.isEmpty(struser)) {
//            this.user = FirebaseAuth.getInstance().getCurrentUser();
//            String userID = user.getUid();
//            DatabaseReference mRef = mDatabase.getReference("nguoidungs").child(userID);
//            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    objNguoiDung mNguoiDung = dataSnapshot.getValue(objNguoiDung.class);
//
//                    downloadAnhNen(mNguoiDung.getHinhnen(), mNguoiDung);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.e(TAG,databaseError.toString());
//                    mDangNhap.dangnhapthatbai();
//                }
//            });
//        }else{
//            mDangNhap.luuthongtinnguoidungthanhcong(false);
//        }
//    }

    public void login(String email, String pwd){
        mAuth.signInWithEmailAndPassword(email,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //KIỂM TRA QUYỀN
                kiemtraquyen(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,e.toString());
                mDangNhap.dangnhapthatbai();
            }
        });
    }

    private void kiemtraquyen(String uid){
        DatabaseReference mref = mDatabase.getReference().child("nguoidungs").child(uid);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    objNguoiDung mND = dataSnapshot.getValue(objNguoiDung.class);
                    if(mND.getQuyen() == 2){
                        mDangNhap.bankhongcoquyentruycap();
                        dangxuat();
                    }else
                        layphongquanly(mND.getHinhnen(),mND);

                }else
                    mDangNhap.dangnhapthatbai();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.toString());
                mDangNhap.dangnhapthatbai();
            }
        });
    }


    public void layphongquanly(final String uri, final objNguoiDung data){
        final DatabaseReference reference = mDatabase.getReference().child("phongmays");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<objPhongMay>> type = new GenericTypeIndicator<List<objPhongMay>>(){};
                List<objPhongMay> mList = dataSnapshot.getValue(type);
                for(objPhongMay i : mList){
                    if(i.getQuanly().matches(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        downloadAnhNen(uri,data,i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDangNhap.loitruyxuatdulieu();
                Log.e(TAG,databaseError.toString());
            }
        });
    }


    public void downloadAnhNen(String uri, final objNguoiDung data, final objPhongMay phongMay){
        try {
            final FirebaseStorage mStorage = FirebaseStorage.getInstance("gs://tinhtrangthietbi-787c9.appspot.com");
            mStorageRef = mStorage.getReference();
            StorageReference mf = mStorageRef.child(uri);
            final long ONE_MEGABYTE = 1024 * 1024;
            mf.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Log.e("Dowload", "Download thành công");
                    saveData(data, Base64.encodeToString(bytes, Base64.DEFAULT),phongMay);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.toString());
                    mDangNhap.dangnhapthatbai();
                }
            });
        }catch (Exception e){
            mDangNhap.dangnhapthatbai();
            Log.e(TAG,e.toString());
        }

    }

    public void saveData(objNguoiDung data, String anhnen, objPhongMay phongMay){
        Gson gson = new Gson();
        String mData = gson.toJson(data);
        String mPhongmay = gson.toJson(phongMay);
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.putString("user", mData);
        editor.putString("anhnen",anhnen);
        editor.putString("phongmay",mPhongmay);
        editor.commit();
        mDangNhap.dangnhapthanhcong();

    }

    public boolean kiemtrathongtinnguoidunglocal(){
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String a = spf.getString("user","");
        if(a.matches("")){
            return false;
        }else{
            return true;
        }
    }
}
