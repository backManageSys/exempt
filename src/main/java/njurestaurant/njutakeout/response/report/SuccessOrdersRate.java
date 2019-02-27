package njurestaurant.njutakeout.response.report;

public class SuccessOrdersRate {
    private String type;
    private double successOrdersRate;

    public SuccessOrdersRate(String type, double successOrdersRate) {
        this.type = type;
        this.successOrdersRate = successOrdersRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSuccessOrdersRate() {
        return successOrdersRate;
    }

    public void setSuccessOrdersRate(double successOrdersRate) {
        this.successOrdersRate = successOrdersRate;
    }
}
