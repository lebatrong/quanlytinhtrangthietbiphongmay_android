package com.lbt.batro.tinhtrangthietbi.Presenter;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objnguoidungs;

public class ldangnhap {
    private static final String TAG = "ldangnhap";

    private idangnhap inter;
    private FirebaseAuth mAuth;
    private Context context;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private FirebaseUser user;

    public ldangnhap(idangnhap inter,Context context) {
        this.inter = inter;
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.mDatabase = FirebaseDatabase.getInstance();
    }

    public boolean kiemtratentaikhoan(String tentaikhoan, String matkhau){
        if(!TextUtils.isEmpty(tentaikhoan) && !TextUtils.isEmpty(matkhau)){
            return true;
        }else{
            if(TextUtils.isEmpty(tentaikhoan))
                inter.saitendangnhap();
            if(TextUtils.isEmpty(matkhau))
                inter.saimatkhau();

            return false;
        }
    }

    public void login(String email, String pwd){
        mAuth.signInWithEmailAndPassword(email,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                inter.dangnhapthanhcong();
                Log.e(TAG,"ĐĂNG NHẬP THÀNH CÔNG");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,e.toString());
                inter.dangnhapthatbai();
            }
        });
    }

    public void Auto_kiemtradangnhap(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
            inter.autoDangNhap(currentUser,true);
        else
            inter.autoDangNhap(null,false);
    }

    public void dangxuat(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.commit();
        inter.dangxuat();
    }

    public void laythongtintaikhoan(){
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String struser = spf.getString("user",null);
        if(TextUtils.isEmpty(struser)) {
            this.user = FirebaseAuth.getInstance().getCurrentUser();
            String userID = user.getUid();
            DatabaseReference mRef = mDatabase.getReference("nguoidungs").child(userID);
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    objnguoidungs mNguoiDung = dataSnapshot.getValue(objnguoidungs.class);
                    //Log.e("StrUser","Lấy thông tin thành công "+dataSnapshot.getValue().toString());
                    downloadAnhNen(mNguoiDung.getHinhnen(), mNguoiDung);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG,databaseError.toString());
                    inter.dangnhapthatbai();
                }
            });
        }else{
            inter.luuthongtinnguoidungthanhcong(false);
        }
    }



    public void downloadAnhNen(String uri, final objnguoidungs data){
        try {
            final FirebaseStorage mStorage = FirebaseStorage.getInstance("gs://tinhtrangthietbi-787c9.appspot.com");
            mStorageRef = mStorage.getReference();
            StorageReference mf = mStorageRef.child(uri);
            final long ONE_MEGABYTE = 1024 * 1024;
            mf.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Log.e("Dowload", "Download thành công");
                    saveData(data, Base64.encodeToString(bytes, Base64.DEFAULT));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.toString());
                    inter.dangnhapthatbai();
                }
            });
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }

    }

    public void saveData(objnguoidungs data, String anhnen){
        Gson gson = new Gson();
        String mData = gson.toJson(data);
        SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.putString("user", mData);
        editor.putString("anhnen",anhnen);
        editor.commit();
        inter.luuthongtinnguoidungthanhcong(true);

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
