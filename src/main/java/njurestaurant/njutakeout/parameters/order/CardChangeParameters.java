package njurestaurant.njutakeout.parameters.order;

public class CardChangeParameters {
    //转出卡卡号
    private String cardNumber_out;
    //转入卡卡号
    private String cardNumber_in;
    //转账金额
    private double money;
    //操作人userId
    private int operateId;
    //账变类型
    private String type;

    public CardChangeParameters(String cardNumber_out, String cardNumber_in, double money, int operateId, String type) {
        this.cardNumber_out = cardNumber_out;
        this.cardNumber_in = cardNumber_in;
        this.money = money;
        this.operateId = operateId;
        this.type = type;
    }

    public String getCardNumber_out() {
        return cardNumber_out;
    }

    public void setCardNumber_out(String cardNumber_out) {
        this.cardNumber_out = cardNumber_out;
    }

    public String getCardNumber_in() {
        return cardNumber_in;
    }

    public void setCardNumber_in(String cardNumber_in) {
        this.cardNumber_in = cardNumber_in;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
