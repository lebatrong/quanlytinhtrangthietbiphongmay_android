package com.lbt.batro.tinhtrangthietbi.Presenter;

import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objphongmay;

import java.util.ArrayList;

public interface iphongmay {
    void thongkemay(long tongmay, long mayhu, long maybinhthuong);
    void danhsachphongmay(ArrayList<objphongmay> mlist);
}
