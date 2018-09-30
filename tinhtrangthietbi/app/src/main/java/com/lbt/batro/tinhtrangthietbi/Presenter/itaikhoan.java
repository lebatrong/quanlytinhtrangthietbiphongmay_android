package com.lbt.batro.tinhtrangthietbi.Presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objnguoidungs;

public interface itaikhoan {
    void thongtintaikhoan(objnguoidungs user, Drawable hinhnen);
    void capnhatthongtin(boolean isSuccess);
    void taianhnenthanhcong(String path);
}
