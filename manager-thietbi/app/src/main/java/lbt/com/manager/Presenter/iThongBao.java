package lbt.com.manager.Presenter;

import java.util.List;

import lbt.com.manager.Models.App.objThietBi;

public interface iThongBao {
    void loiduongtruyen();
    void pushthongbao(List<objThietBi> thietBi);
    void phongmayhoatdongtot();
}
