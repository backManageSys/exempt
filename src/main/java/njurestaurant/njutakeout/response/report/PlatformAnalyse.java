package njurestaurant.njutakeout.response.report;

public class PlatformAnalyse {
    private String type;
    private double money;

    public PlatformAnalyse(String type, double money) {
        this.type = type;
        this.money = money;
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
}
