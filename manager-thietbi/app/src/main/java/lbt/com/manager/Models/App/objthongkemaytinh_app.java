package lbt.com.manager.Models.App;

public class objthongkemaytinh_app {
    long chuot,chuothu,chuotbt;
    long banphim,banphimhu,banphimbt;
    long manhinh,manhinhhu,manhinhbt;
    long cpu,cpuhu,cpubt;
    long maytinh, maytinhhu, maytinhbt;

    public objthongkemaytinh_app(long chuot, long chuothu, long chuotbt, long banphim, long banphimhu, long banphimbt, long manhinh, long manhinhhu, long manhinhbt, long cpu, long cpuhu, long cpubt, long maytinh, long maytinhhu, long maytinhbt) {
        this.chuot = chuot;
        this.chuothu = chuothu;
        this.chuotbt = chuotbt;
        this.banphim = banphim;
        this.banphimhu = banphimhu;
        this.banphimbt = banphimbt;
        this.manhinh = manhinh;
        this.manhinhhu = manhinhhu;
        this.manhinhbt = manhinhbt;
        this.cpu = cpu;
        this.cpuhu = cpuhu;
        this.cpubt = cpubt;
        this.maytinh = maytinh;
        this.maytinhhu = maytinhhu;
        this.maytinhbt = maytinhbt;
    }

    public objthongkemaytinh_app() {
    }

    public long getMaytinh() {
        return maytinh;
    }

    public void setMaytinh(long maytinh) {
        this.maytinh = maytinh;
    }

    public long getMaytinhhu() {
        return maytinhhu;
    }

    public void setMaytinhhu(long maytinhhu) {
        this.maytinhhu = maytinhhu;
    }

    public long getMaytinhbt() {
        return getMaytinh() - getMaytinhhu();
    }


    public long getChuot() {
        return chuot;
    }

    public void setChuot(long chuot) {
        this.chuot = chuot;
    }

    public long getChuothu() {
        return chuothu;
    }

    public void setChuothu(long chuothu) {
        this.chuothu = chuothu;
    }

    public long getChuotbt() {
        return getChuot() - getChuothu();
    }


    public long getBanphim() {
        return banphim;
    }

    public void setBanphim(long banphim) {
        this.banphim = banphim;
    }

    public long getBanphimhu() {
        return banphimhu;
    }

    public void setBanphimhu(long banphimhu) {
        this.banphimhu = banphimhu;
    }

    public long getBanphimbt() {
        return getBanphim() - getBanphimhu();
    }


    public long getManhinh() {
        return manhinh;
    }

    public void setManhinh(long manhinh) {
        this.manhinh = manhinh;
    }

    public long getManhinhhu() {
        return manhinhhu;
    }

    public void setManhinhhu(long manhinhhu) {
        this.manhinhhu = manhinhhu;
    }

    public long getManhinhbt() {
        return getManhinh() - getManhinhhu();
    }


    public long getCpu() {
        return cpu;
    }

    public void setCpu(long cpu) {
        this.cpu = cpu;
    }

    public long getCpuhu() {
        return cpuhu;
    }

    public void setCpuhu(long cpuhu) {
        this.cpuhu = cpuhu;
    }

    public long getCpubt() {
        return getCpu() - getCpuhu();
    }
}
