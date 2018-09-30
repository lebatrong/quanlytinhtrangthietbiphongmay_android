package lbt.com.manager.Models.App;

import java.util.List;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;

public class objLichSu {

    List<objlichsu_maytinhs> lichsu;
    String mamay;

    public objLichSu(String mamay, List<objlichsu_maytinhs> lichsu ) {
        this.lichsu = lichsu;
        this.mamay = mamay;
    }

    public List<objlichsu_maytinhs> getLichsu() {
        return lichsu;
    }

    public void setLichsu(List<objlichsu_maytinhs> lichsu) {
        this.lichsu = lichsu;
    }

    public String getMamay() {
        return mamay;
    }

    public void setMamay(String mamay) {
        this.mamay = mamay;
    }

    public objLichSu() {
    }


}
