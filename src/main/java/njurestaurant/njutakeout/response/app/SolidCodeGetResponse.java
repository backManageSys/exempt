package njurestaurant.njutakeout.response.app;

import njurestaurant.njutakeout.response.Response;

public class SolidCodeGetResponse extends Response {
    private String cmd;
    private String type;
    private String imei;
    private String userId;
    private double money;
    private String memo;

    public SolidCodeGetResponse() {
    }

    public SolidCodeGetResponse(String cmd, String type, String imei, String userId, double money, String memo) {
        this.cmd = cmd;
        this.type = type;
        this.imei = imei;
        this.userId = userId;
        this.money = money;
        this.memo = memo;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
