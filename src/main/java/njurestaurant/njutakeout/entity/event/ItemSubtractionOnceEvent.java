package njurestaurant.njutakeout.entity.event;

import njurestaurant.njutakeout.publicdatas.event.EventState;
import njurestaurant.njutakeout.publicdatas.event.EventType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ItemSubtractionOnceEvent extends Event {
    @Column(name = "itemId")
    private int itemId;
    @Column(name = "minusPrice")
    private double minusPrice;

    public ItemSubtractionOnceEvent() {
    }

    public ItemSubtractionOnceEvent(String content, EventState eventState, int itemId, double minusPrice) {
        super(content, EventType.ItemSubtractionOnce, eventState);
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

