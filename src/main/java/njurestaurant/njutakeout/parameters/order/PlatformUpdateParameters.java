package njurestaurant.njutakeout.parameters.order;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PlatformUpdateParameters {
    private String state;
    private double money;
    private double realPay;
    private String orderId;
    private String payTime;

    private String memo;

    public PlatformUpdateParameters(String state, double money, double realPay, String orderId, String payTime, String memo) {
        this.state = state;
        this.money = money;
        this.realPay = realPay;
        this.orderId = orderId;
        this.payTime = payTime;
        this.memo = memo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getRealPay() {
        return realPay;
    }

    public void setRealPay(double realPay) {
        this.realPay = realPay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
