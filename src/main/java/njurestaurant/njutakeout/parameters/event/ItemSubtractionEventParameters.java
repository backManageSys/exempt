package njurestaurant.njutakeout.parameters.event;

import njurestaurant.njutakeout.publicdatas.event.EventType;

import java.util.List;

public class ItemSubtractionEventParameters extends EventAddParameters {
    private List<Integer> itemList;
    private double minusPrice;

    public ItemSubtractionEventParameters() {
    }

    public ItemSubtractionEventParameters(EventType eventType, String description, List<Integer> itemList, double minusPrice) {
        super(eventType, description);
        this.itemList = itemList;
        this.minusPrice = minusPrice;
    }

    public List<Integer> getItemList() {
        return itemList;
    }

    public void setItemList(List<Integer> itemList) {
        this.itemList = itemList;
    }

    public double getMinusPrice() {
        return minusPrice;
    }

    public void setMinusPrice(double minusPrice) {
        this.minusPrice = minusPrice;
    }
}
