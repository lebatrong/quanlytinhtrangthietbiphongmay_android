package lbt.com.manager.Presenter;

import com.google.firebase.auth.FirebaseUser;

import lbt.com.manager.Models.Firebase.objPhongMay;


public interface iDangNhap {
    void dangnhapthatbai();
    void dangnhapthanhcong();
    void saitendangnhap();
    void saimatkhau();
    void bankhongcoquyentruycap();
    void loitruyxuatdulieu();
    void autoDangNhap(FirebaseUser user, boolean isSuccess);
    void dangxuat();
}
