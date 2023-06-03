package team.skadi.rental.bean;

public class Power {
    /** 电源id*/
    private String id;
    /**电源序列号*/
    private int serialnum;
    /**剩余电量*/
    private double left;
    /**租借状态*/
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(int serialnum) {
        this.serialnum = serialnum;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Power(String id, int serialnum, double left, int status) {
        this.id = id;
        this.serialnum = serialnum;
        this.left = left;
        this.status = status;
    }

    public Power() {
    }
}
