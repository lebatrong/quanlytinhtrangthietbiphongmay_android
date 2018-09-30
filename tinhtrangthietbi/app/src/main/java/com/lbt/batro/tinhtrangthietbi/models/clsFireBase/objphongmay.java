package com.lbt.batro.tinhtrangthietbi.models.clsFireBase;

import java.io.Serializable;
import java.util.HashMap;

public class objphongmay implements Serializable {

    String khu;
    String lau;
    String phong;
    String maphong;
    String tenphong;
    String quanly;

    public objphongmay(String khu, String lau, String phong, String maphong, String tenphong, String quanly) {
        this.khu = khu;
        this.lau = lau;
        this.phong = phong;
        this.maphong = maphong;
        this.tenphong = tenphong;
        this.quanly = quanly;
    }


    public objphongmay() {

    }

    public String getQuanly() {
        return quanly;
    }

    public void setQuanly(String quanly) {
        this.quanly = quanly;
    }

    public String getKhu() {
        return khu;
    }

    public void setKhu(String khu) {
        this.khu = khu;
    }

    public String getLau() {
        return lau;
    }

    public void setLau(String lau) {
        this.lau = lau;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }
}
