package lbt.com.manager.Models.Firebase;

import java.io.Serializable;

public class objthietbikhacs implements Serializable {

    long ban,ghe,maydieuhoa,tivi,quat,switchmang,modemwifi,den;
    String khac;

    public objthietbikhacs() {
    }

    public objthietbikhacs(long ban, long ghe, long maydieuhoa, long tivi, long quat, long switchmang, long modemwifi, long den, String khac) {
        this.ban = ban;
        this.ghe = ghe;
        this.maydieuhoa = maydieuhoa;
        this.tivi = tivi;
        this.quat = quat;
        this.switchmang = switchmang;
        this.modemwifi = modemwifi;
        this.den = den;
        this.khac = khac;
    }

    public long getDen() {
        return den;
    }

    public void setDen(long den) {
        this.den = den;
    }

    public long getBan() {
        return ban;
    }

    public void setBan(long ban) {
        this.ban = ban;
    }

    public long getGhe() {
        return ghe;
    }

    public void setGhe(long ghe) {
        this.ghe = ghe;
    }

    public long getMaydieuhoa() {
        return maydieuhoa;
    }

    public void setMaydieuhoa(long maydieuhoa) {
        this.maydieuhoa = maydieuhoa;
    }

    public long getTivi() {
        return tivi;
    }

    public void setTivi(long tivi) {
        this.tivi = tivi;
    }

    public long getQuat() {
        return quat;
    }

    public void setQuat(long quat) {
        this.quat = quat;
    }

    public long getSwitchmang() {
        return switchmang;
    }

    public void setSwitchmang(long switchmang) {
        this.switchmang = switchmang;
    }

    public long getModemwifi() {
        return modemwifi;
    }

    public void setModemwifi(long modemwifi) {
        this.modemwifi = modemwifi;
    }

    public String getKhac() {
        return khac;
    }

    public void setKhac(String khac) {
        this.khac = khac;
    }
}
