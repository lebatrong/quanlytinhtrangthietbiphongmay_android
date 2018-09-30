package lbt.com.manager.Models.App;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;

public class objthietbimaytinh_app {
    String mathietbi;
    objlichsu_maytinhs lichsusuachua;

    public objthietbimaytinh_app(String mathietbi, objlichsu_maytinhs lichsusuachua) {
        this.mathietbi = mathietbi;
        this.lichsusuachua = lichsusuachua;
    }

    public objthietbimaytinh_app() {
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
