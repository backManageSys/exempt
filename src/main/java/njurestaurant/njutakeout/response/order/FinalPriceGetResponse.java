package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.response.Response;

public class FinalPriceGetResponse extends Response {
    private double finalPrice;

    public FinalPriceGetResponse() {
    }

    public FinalPriceGetResponse(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
