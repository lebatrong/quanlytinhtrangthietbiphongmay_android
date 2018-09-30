package com.lbt.batro.tinhtrangthietbi.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objnguoidungs;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

public class ltaikhoan implements idangnhap{
    public static final String TAG = "LTaiKhoan";

    private FirebaseUser user;
    private Context context;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private ldangnhap mdangnhap;
    private itaikhoan mInter;


    public ltaikhoan(Context context, itaikhoan itaikhoan) {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;
        this.mInter = itaikhoan;
        this.mDatabase = FirebaseDatabase.getInstance();
    }


    public void getDataDefault(){
        try {
            Gson gson = new Gson();
            SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            objnguoidungs nguoidung = gson.fromJson(spf.getString("user",null),objnguoidungs.class);
            String anh = spf.getString("anhnen",null);
            Drawable drawable = null;
            if(anh!=null) {
                byte[] bytes = Base64.decode(anh, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                drawable = new BitmapDrawable(Resources.getSystem(), bm);
            }
            mInter.thongtintaikhoan(nguoidung,drawable);
        }catch (Exception e){
            Log.e(TAG+"a",e.toString());
        }
    }

    public objnguoidungs getDataUser(){
        try {
            Gson gson = new Gson();
            SharedPreferences spf = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            objnguoidungs nguoidung = gson.fromJson(spf.getString("user",null),objnguoidungs.class);
            return nguoidung;
        }catch (Exception e){
            Log.e(TAG+"a",e.toString());
            return null;
        }
    }

    public void laythongtintaikhoan(){
        mdangnhap = new ldangnhap(this,context);
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DatabaseReference mRef = mDatabase.getReference("nguoidungs").child(userID);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.e("MNguoiDung",dataSnapshot.getValue().toString());
                objnguoidungs mNguoiDung = dataSnapshot.getValue(objnguoidungs.class);
                mdangnhap.downloadAnhNen(mNguoiDung.getHinhnen(),mNguoiDung);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAIKHOAN",databaseError.toString());
            }
        });
    }

    public void capnhatthongtin(objnguoidungs mnguoidung){
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mr = mDatabase.getReference("nguoidungs").child(user.getUid());
        mr.setValue(mnguoidung).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("CAP NHAT THONG TIN", "CAP NHAT THONG TIN THANH CONG");
                mInter.capnhatthongtin(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mInter.capnhatthongtin(false);
            }
        });
    }


    public void uploadAvatar(ImageView imv, final String path, final objnguoidungs mnguoidung){
        final FirebaseStorage mStorage = FirebaseStorage.getInstance("gs://tinhtrangthietbi-787c9.appspot.com");
        Calendar cal = Calendar.getInstance();
        mStorageRef = mStorage.getReference("hinhnen").child("avatar-"+cal.getTimeInMillis()+"-"+this.user.getUid());

        imv.setDrawingCacheEnabled(true);
        imv.buildDrawingCache();
        Bitmap bm = ((BitmapDrawable) imv.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,20,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final String url = taskSnapshot.getMetadata().getReference().getPath().toString();

                //TẢI LÊN THÀNH CÔNG XÓA HÌNH CŨ
                StorageReference mSRef = mStorage.getReference().child(path);
                mSRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //CẬP NHẬT LẠI DATABASE
                        objnguoidungs nguoidungupdate = mnguoidung;
                        nguoidungupdate.setHinhnen(url);
                        Gson gson = new Gson();
                        //Log.e("Nguoidung",gson.toJson(nguoidungupdate));
                        capnhatthongtin(nguoidungupdate);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       mInter.capnhatthongtin(false);
                        Log.e("158",e.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mInter.capnhatthongtin(false);
                Log.e("158",e.toString());
            }
        });

    }


    //========================================
    @Override
    public void dangnhapthanhcong() {

    }

    @Override
    public void dangnhapthatbai() {

    }

    @Override
    public void saitendangnhap() {

    }

    @Override
    public void saimatkhau() {

    }

    @Override
    public void autoDangNhap(FirebaseUser user, boolean isSuccess) {

    }

    @Override
    public void luuthongtinnguoidungthanhcong(boolean iscoluu) {
        getDataDefault();
    }

    @Override
    public void dangxuat() {

    }



}
