package com.lbt.batro.tinhtrangthietbi.models.clsFireBase;

import java.io.Serializable;

public class objlichsu_maytinhs implements Serializable {

    objchitietthietbimaytinh chitietsuachua;
    boolean dasuachua;
    long ngaybaocao;
    String emailnguoibaocao;

    public objlichsu_maytinhs() {
    }

    public objlichsu_maytinhs(objchitietthietbimaytinh chitietsuachua, boolean dasuachua, long ngaybaocao, String emailnguoibaocao) {
        this.chitietsuachua = chitietsuachua;
        this.dasuachua = dasuachua;
        this.ngaybaocao = ngaybaocao;
        this.emailnguoibaocao = emailnguoibaocao;
    }


    public objchitietthietbimaytinh getChitietsuachua() {
        return chitietsuachua;
    }

    public void setChitietsuachua(objchitietthietbimaytinh chitietsuachua) {
        this.chitietsuachua = chitietsuachua;
    }

    public boolean isDasuachua() {
        return dasuachua;
    }

    public void setDasuachua(boolean dasuachua) {
        this.dasuachua = dasuachua;
    }

    public long getNgaybaocao() {
        return ngaybaocao;
    }

    public void setNgaybaocao(long ngaybaocao) {
        this.ngaybaocao = ngaybaocao;
    }

    public String getEmailnguoibaocao() {
        return emailnguoibaocao;
    }

    public void setEmailnguoibaocao(String emailnguoibaocao) {
        this.emailnguoibaocao = emailnguoibaocao;
    }
}


