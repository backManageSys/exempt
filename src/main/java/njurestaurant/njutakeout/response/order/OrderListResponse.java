package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;

import java.util.Date;

public class OrderListResponse {
    private int orderId;
    private String orderNumber;
    private double money;
    private double paymoney;
    private String rechargeId;
    private OrderState orderState;
    private Date time;
    private Date payTime;
    private int merchantId;
    private int supplierId;
    private int agentId;
    private String merchantName;
    private String codeCategory;
    private String codeType;

    public OrderListResponse(int orderId, String orderNumber, double money, double paymoney, String rechargeId, OrderState orderState, Date time, Date payTime, int merchantId, int supplierId, int agentId, String merchantName, String codeCategory, String codeType) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.money = money;
        this.paymoney = paymoney;
        this.rechargeId = rechargeId;
        this.orderState = orderState;
        this.time = time;
        this.payTime = payTime;
        this.merchantId = merchantId;
        this.supplierId = supplierId;
        this.agentId = agentId;
        this.merchantName = merchantName;
        this.codeCategory = codeCategory;
        this.codeType = codeType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(double paymoney) {
        this.paymoney = paymoney;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }


    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
