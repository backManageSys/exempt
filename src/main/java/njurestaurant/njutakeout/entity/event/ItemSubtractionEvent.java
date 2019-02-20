package njurestaurant.njutakeout.entity.event;

import njurestaurant.njutakeout.publicdatas.event.EventState;
import njurestaurant.njutakeout.publicdatas.event.EventType;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class ItemSubtractionEvent extends Event {
    @Column(name = "itemList")
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> itemList;
    @Column(name = "minusPrice")
    private double minusPrice;

    public ItemSubtractionEvent() {
    }

    public ItemSubtractionEvent(String content, EventState eventState, List<Integer> itemList, double minusPrice) {
        super(content, EventType.ItemSubtraction, eventState);
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

