package lbt.com.manager.Presenter;

import android.graphics.drawable.Drawable;

import lbt.com.manager.Models.Firebase.objNguoiDung;
import lbt.com.manager.Models.Firebase.objPhongMay;

public interface iTaiKhoan {
    void thongtintaikhoan(objNguoiDung user, Drawable hinhnen);
    void capnhatthongtin(boolean isSuccess);
    void laythongtinkhongthanhcong();
    void bankhongconquyenquanly();

}
