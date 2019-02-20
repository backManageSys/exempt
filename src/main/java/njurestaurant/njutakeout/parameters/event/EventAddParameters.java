package njurestaurant.njutakeout.parameters.event;

import njurestaurant.njutakeout.publicdatas.event.EventType;

public class EventAddParameters {
    private EventType eventType;
    private String description;

    public EventAddParameters() {
    }

    public EventAddParameters(EventType eventType, String description) {
        this.eventType = eventType;
        this.description = description;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
