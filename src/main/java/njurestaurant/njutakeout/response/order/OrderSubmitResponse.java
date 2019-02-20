package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.response.Response;

public class OrderSubmitResponse extends Response {
    private int orderId;
    private String timeStamp;
    private String nonceStr;
    private String pakcage;
    private String signType;
    private String paySign;

    public OrderSubmitResponse() {
    }

    public OrderSubmitResponse(int orderId, String timeStamp, String nonceStr, String pakcage, String signType, String paySign) {
        this.orderId = orderId;
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.pakcage = pakcage;
        this.signType = signType;
        this.paySign = paySign;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPakcage() {
        return pakcage;
    }

    public void setPakcage(String pakcage) {
        this.pakcage = pakcage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
