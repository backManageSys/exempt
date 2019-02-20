package njurestaurant.njutakeout.parameters.event;

import njurestaurant.njutakeout.publicdatas.event.EventType;

public class FullSubtractionEventParameters extends EventAddParameters {
    private double fullPrice;
    private double minusPrice;

    public FullSubtractionEventParameters() {
    }

    public FullSubtractionEventParameters(EventType eventType, String description, double fullPrice, double minusPrice) {
        super(eventType, description);
        this.fullPrice = fullPrice;
        this.minusPrice = minusPrice;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public double getMinusPrice() {
        return minusPrice;
    }

    public void setMinusPrice(double minusPrice) {
        this.minusPrice = minusPrice;
    }
}
