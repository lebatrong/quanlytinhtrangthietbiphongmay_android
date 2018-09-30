package com.lbt.batro.tinhtrangthietbi.Presenter;

import com.lbt.batro.tinhtrangthietbi.models.clsApp.objthietbimaytinh_app;
import com.lbt.batro.tinhtrangthietbi.models.clsApp.objthongkemaytinh_app;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objthietbikhacs;

import java.util.ArrayList;

public interface ichitietphongmay {
    void loitaidulieu();
    void phongmaydangxaydung();
    void thongkethietbikhac(objthietbikhacs default_values , objthietbikhacs hu);
    void thongkemaytinh(boolean phongmayhu, objthongkemaytinh_app thongke);
    void danhsachmaytinh(ArrayList<objthietbimaytinh_app> list);
}
