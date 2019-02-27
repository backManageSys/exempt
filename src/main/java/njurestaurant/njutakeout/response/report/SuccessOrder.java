package njurestaurant.njutakeout.response.report;

public class SuccessOrder {
    private String type;
    private int successOrders;

    public SuccessOrder(String type, int successOrders) {
        this.type = type;
        this.successOrders = successOrders;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSuccessOrders() {
        return successOrders;
    }

    public void setSuccessOrders(int successOrders) {
        this.successOrders = successOrders;
    }
}
