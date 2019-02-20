package njurestaurant.njutakeout.entity.event;

import njurestaurant.njutakeout.publicdatas.event.EventState;
import njurestaurant.njutakeout.publicdatas.event.EventType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FullSubtractionEvent extends Event {
    @Column(name = "fullPrice")
    private double fullPrice;
    @Column(name = "minusPrice")
    private double minusPrice;

    public FullSubtractionEvent() {
    }

    public FullSubtractionEvent(String content, EventState eventState, double fullPrice, double minusPrice) {
        super(content, EventType.FullSubtraction, eventState);
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
