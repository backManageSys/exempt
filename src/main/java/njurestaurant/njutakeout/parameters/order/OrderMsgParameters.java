package njurestaurant.njutakeout.parameters.order;

import java.util.Date;

public class OrderMsgParameters {
    private String cmd;
    private String type;
    private String imei;
    private String orderId;
    private double money;
    private String memo;
    private Date time;

    public OrderMsgParameters(String cmd, String type, String imei, String orderId, double money, String memo, Date time) {
        this.cmd = cmd;
        this.type = type;
        this.imei = imei;
        this.orderId = orderId;
        this.money = money;
        this.memo = memo;
        this.time = time;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
