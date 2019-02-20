package njurestaurant.njutakeout.parameters.order;

public class WithdrewParameters {
    private int id;//user表下的id
    private String type;//角色
    private double money;//提现金额
    private int cardId;//提现卡号

    public WithdrewParameters(int id, String type, double money, int cardId) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.cardId = cardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
