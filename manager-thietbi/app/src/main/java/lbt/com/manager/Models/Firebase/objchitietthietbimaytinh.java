package lbt.com.manager.Models.Firebase;

import java.io.Serializable;

public class objchitietthietbimaytinh implements Serializable {
    boolean banphim;
    boolean chuot;
    boolean cpu;
    boolean manhinh;
    boolean hedieuhanh;
    String phanmem;
    String khac;

    public objchitietthietbimaytinh(boolean banphim, boolean chuot, boolean cpu, boolean manhinh, boolean hedieuhanh, String phanmem, String khac) {
        this.banphim = banphim;
        this.chuot = chuot;
        this.cpu = cpu;
        this.manhinh = manhinh;
        this.hedieuhanh = hedieuhanh;
        this.phanmem = phanmem;
        this.khac = khac;
    }

    public objchitietthietbimaytinh() {
    }

    public boolean isHedieuhanh() {
        return hedieuhanh;
    }

    public void setHedieuhanh(boolean hedieuhanh) {
        this.hedieuhanh = hedieuhanh;
    }

    public String getPhanmem() {
        return phanmem;
    }

    public void setPhanmem(String phanmem) {
        this.phanmem = phanmem;
    }

    public boolean isBanphim() {
        return banphim;
    }

    public void setBanphim(boolean banphim) {
        this.banphim = banphim;
    }

    public boolean isChuot() {
        return chuot;
    }

    public void setChuot(boolean chuot) {
        this.chuot = chuot;
    }

    public boolean isCpu() {
        return cpu;
    }

    public void setCpu(boolean cpu) {
        this.cpu = cpu;
    }

    public boolean isManhinh() {
        return manhinh;
    }

    public void setManhinh(boolean manhinh) {
        this.manhinh = manhinh;
    }

    public String getKhac() {
        return khac;
    }

    public void setKhac(String khac) {
        this.khac = khac;
    }
}
