package njurestaurant.njutakeout.response.report;

public class TotalOrder {
    private String type;
    private int totalOrders;

    public TotalOrder(String type, int totalOrders) {
        this.type = type;
        this.totalOrders = totalOrders;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
