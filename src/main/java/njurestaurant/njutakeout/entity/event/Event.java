package njurestaurant.njutakeout.entity.event;


import njurestaurant.njutakeout.publicdatas.event.EventState;
import njurestaurant.njutakeout.publicdatas.event.EventType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "eventType")
    private EventType eventType;

    @Column(name = "eventState")
    private EventState eventState;

    public Event() {
    }

    public Event(String content, EventType eventType, EventState eventState) {
        this.content = content;
        this.eventType = eventType;
        this.eventState = eventState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventState getEventState() {
        return eventState;
    }

    public void setEventState(EventState eventState) {
        this.eventState = eventState;
    }
}
