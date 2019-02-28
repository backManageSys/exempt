package njurestaurant.njutakeout.parameters.user;

public class PayRateAddParameters {
    private int payType_id;
    private double rate;
    private String status;

    public PayRateAddParameters(int payType_id, double rate, String status) {
        this.payType_id = payType_id;
        this.rate = rate;
        this.status = status;
    }

    public int getPayType_id() {
        return payType_id;
    }

    public void setPayType_id(int payType_id) {
        this.payType_id = payType_id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
