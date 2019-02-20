package njurestaurant.njutakeout.parameters.event;

import njurestaurant.njutakeout.publicdatas.event.EventType;

public class ItemSubtractionOnceEventParameters extends EventAddParameters {
    private int itemId;
    private double minusPrice;

    public ItemSubtractionOnceEventParameters() {
    }

    public ItemSubtractionOnceEventParameters(EventType eventType, String description, int itemId, double minusPrice) {
        super(eventType, description);
        this.itemId = itemId;
        this.minusPrice = minusPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getMinusPrice() {
        return minusPrice;
    }

    public void setMinusPrice(double minusPrice) {
        this.minusPrice = minusPrice;
    }
}
