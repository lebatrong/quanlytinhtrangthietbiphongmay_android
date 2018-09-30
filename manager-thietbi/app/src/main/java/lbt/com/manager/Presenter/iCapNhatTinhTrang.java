package lbt.com.manager.Presenter;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;

public interface iCapNhatTinhTrang {
    void laythongtinmay(boolean isSuccess);
    void capnhatthanhcong(boolean isthanhcong);
    void maycontot(boolean isTot, objlichsu_maytinhs mls, String mamay);
    void maqrkhonghopke();
}
