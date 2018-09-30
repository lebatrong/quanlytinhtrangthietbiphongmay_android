package lbt.com.manager.Presenter;

import java.util.ArrayList;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objthietbikhacs;

public interface iPhongMay {
    void loitaidulieu();
    void phongmaydangxaydung();
    void thongkethietbikhac(objthietbikhacs default_values , objthietbikhacs hu);
    void thongkemaytinh(boolean phongmayhu, objthongkemaytinh_app thongke);
    void danhsachmaytinh(ArrayList<objthietbimaytinh_app> list);
    void capnhatthanhcong(boolean isSuccess);

}
