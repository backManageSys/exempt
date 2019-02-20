package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.response.Response;

public class AgentInfoResponse extends Response {
    private int id;
    private int uid;
    private String name;
    private String status;      // 状态
    private double alipay;
    private double wechat;
    private double balance;
    private User userInfo;
    private double dailyFlow;       // 当日流量
    private double dailyCommission;     // 当日佣金

    public AgentInfoResponse(int id, int uid, String name, String status, double alipay, double wechat, double balance, User userInfo, double dailyFlow, double dailyCommission) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.status = status;
        this.alipay = alipay;
        this.wechat = wechat;
        this.balance = balance;
        this.userInfo = userInfo;
        this.dailyFlow = dailyFlow;
        this.dailyCommission = dailyCommission;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getAlipay() {
        return alipay;
    }

    public void setAlipay(double alipay) {
        this.alipay = alipay;
    }

    public double getWechat() {
        return wechat;
    }

    public void setWechat(double wechat) {
        this.wechat = wechat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public double getDailyFlow() {
        return dailyFlow;
    }

    public void setDailyFlow(double dailyFlow) {
        this.dailyFlow = dailyFlow;
    }

    public double getDailyCommission() {
        return dailyCommission;
    }

    public void setDailyCommission(double dailyCommission) {
        this.dailyCommission = dailyCommission;
    }
}
