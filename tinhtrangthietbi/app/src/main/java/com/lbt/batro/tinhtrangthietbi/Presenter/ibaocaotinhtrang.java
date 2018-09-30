package com.lbt.batro.tinhtrangthietbi.Presenter;

import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objlichsu_maytinhs;

public interface ibaocaotinhtrang {
    void laythongtinmay(boolean isSuccess);
    void capnhatthanhcong(boolean isthanhcong);
    void giatrikhonghople();
    void baocaothietbikhacthanhcong(boolean isCapNhat);
    void maycontot(boolean isTot, objlichsu_maytinhs mls, String mamay);
    void maqrkhonghopke();
}
