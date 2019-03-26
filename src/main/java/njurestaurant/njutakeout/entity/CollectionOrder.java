package njurestaurant.njutakeout.entity;


public class CollectionOrder {
    private String cmd;
    private String type;
    private String ordertype;
    private String money;
    private String realMoney;
    private String orderId;
    private String imei;
    private String memo;
    private String time;

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

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(String realMoney) {
        this.realMoney = realMoney;
    }

    @Override
    public String toString() {
        return "CollectionOrder{" +
                "cmd='" + cmd + '\'' +
                ", type='" + type + '\'' +
                ", ordertype='" + ordertype + '\'' +
                ", money='" + money + '\'' +
                ", realMoney='" + realMoney + '\'' +
                ", orderId='" + orderId + '\'' +
                ", imei='" + imei + '\'' +
                ", memo='" + memo + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public CollectionOrder(String cmd, String type, String ordertype, String money, String realMoney, String orderId, String imei, String memo, String time) {
        this.cmd = cmd;
        this.type = type;
        this.ordertype = ordertype;
        this.money = money;
        this.realMoney = realMoney;
        this.orderId = orderId;
        this.imei = imei;
        this.memo = memo;
        this.time = time;
    }
}
