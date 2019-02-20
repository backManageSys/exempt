package njurestaurant.njutakeout.parameters.order;

public class QRcodeChangeParameters {
    //支付宝账号
    private String loginId;
    //提现金额
    private double money;
    //卡号
    private String cardNumber;
    //操作人userId
    private int operateId;

    public QRcodeChangeParameters() {

    }

    public QRcodeChangeParameters(String loginId, double money, String cardNumber,int operateId) {
        this.loginId = loginId;
        this.money = money;
        this.cardNumber = cardNumber;
        this.operateId = operateId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }
}
