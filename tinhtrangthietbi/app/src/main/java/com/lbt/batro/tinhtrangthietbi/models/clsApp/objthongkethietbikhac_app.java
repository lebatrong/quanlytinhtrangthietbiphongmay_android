package com.lbt.batro.tinhtrangthietbi.models.clsApp;

public class objthongkethietbikhac_app {
    String title;
    long tong,hu,binhthuong;

    public objthongkethietbikhac_app(String title, long tong, long hu, long binhthuong) {
        this.title = title;
        this.tong = tong;
        this.hu = hu;
        this.binhthuong = binhthuong;
    }

    public objthongkethietbikhac_app() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTong() {
        return tong;
    }

    public void setTong(long tong) {
        this.tong = tong;
    }

    public long getHu() {
        return hu;
    }

    public void setHu(long hu) {
        this.hu = hu;
    }

    public long getBinhthuong() {
        return binhthuong;
    }

    public void setBinhthuong(long binhthuong) {
        this.binhthuong = binhthuong;
    }
}
