package njurestaurant.njutakeout.entity.event;

import njurestaurant.njutakeout.publicdatas.event.EventState;
import njurestaurant.njutakeout.publicdatas.event.EventType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FirstOrderEvent extends Event {
    @Column(name = "minusPrice")
    private double minusPrice;

    public FirstOrderEvent() {
    }

    public FirstOrderEvent(String content, EventState eventState, double minusPrice) {
        super(content, EventType.FirstOrder, eventState);
        this.minusPrice = minusPrice;
    }

    public double getMinusPrice() {
        return minusPrice;
    }

    public void setMinusPrice(double minusPrice) {
        this.minusPrice = minusPrice;
    }
}
