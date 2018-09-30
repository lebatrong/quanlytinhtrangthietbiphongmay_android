package lbt.com.manager.Models.App;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;

public class objThietBi {
    String mathietbi;
    objlichsu_maytinhs lichsusuachua;

    public objThietBi(String mathietbi, objlichsu_maytinhs lichsusuachua) {
        this.mathietbi = mathietbi;
        this.lichsusuachua = lichsusuachua;
    }

    public objThietBi() {
    }

    public String getMathietbi() {
        return mathietbi;
    }

    public void setMathietbi(String mathietbi) {
        this.mathietbi = mathietbi;
    }

    public objlichsu_maytinhs getLichsusuachua() {
        return lichsusuachua;
    }

    public void setLichsusuachua(objlichsu_maytinhs lichsusuachua) {
        this.lichsusuachua = lichsusuachua;
    }
}
