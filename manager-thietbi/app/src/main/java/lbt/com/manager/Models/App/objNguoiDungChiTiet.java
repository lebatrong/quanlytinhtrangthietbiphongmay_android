package lbt.com.manager.Models.App;

public class objNguoiDungChiTiet {
    private String gioitinh;
    private String hoten;
    private String quequan;
    private long ngaysinh;
    private String hinhnen;
    private String tendangnhap;
    private long quyen;
    private String maphongquanly;

    public objNguoiDungChiTiet(String gioitinh, String hoten, String quequan, long ngaysinh, String hinhnen, String tendangnhap, long quyen, String maphongquanly) {
        this.gioitinh = gioitinh;
        this.hoten = hoten;
        this.quequan = quequan;
        this.ngaysinh = ngaysinh;
        this.hinhnen = hinhnen;
        this.tendangnhap = tendangnhap;
        this.quyen = quyen;
        this.maphongquanly = maphongquanly;
    }

    public objNguoiDungChiTiet() {
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public long getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(long ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getHinhnen() {
        return hinhnen;
    }

    public void setHinhnen(String hinhnen) {
        this.hinhnen = hinhnen;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public long getQuyen() {
        return quyen;
    }

    public void setQuyen(long quyen) {
        this.quyen = quyen;
    }

    public String getMaphongquanly() {
        return maphongquanly;
    }

    public void setMaphongquanly(String maphongquanly) {
        this.maphongquanly = maphongquanly;
    }
}
