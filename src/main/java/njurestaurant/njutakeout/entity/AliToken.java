package njurestaurant.njutakeout.entity;

public class AliToken {
    private Double money;
    private String orderId;
    private String auth_token;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public AliToken(Double money, String orderId, String auth_token) {
        this.money = money;
        this.orderId = orderId;
        this.auth_token = auth_token;
    }

    @Override
    public String toString() {
        return "AliToken{" +
                "money=" + money +
                ", orderId='" + orderId + '\'' +
                ", auth_token='" + auth_token + '\'' +
                '}';
    }
}
