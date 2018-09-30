package com.lbt.batro.tinhtrangthietbi.Presenter;

import com.google.firebase.auth.FirebaseUser;

public interface idangnhap {
    void dangnhapthanhcong();
    void dangnhapthatbai();
    void saitendangnhap();
    void saimatkhau();
    void autoDangNhap(FirebaseUser user, boolean isSuccess);
    void luuthongtinnguoidungthanhcong(boolean iscoluu);
    void dangxuat();
}
