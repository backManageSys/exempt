package njurestaurant.njutakeout.response.event;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class EventWithIdLoadResponse extends Response {
    private List<EventItem> eventItems;

    public EventWithIdLoadResponse() {
    }

    public EventWithIdLoadResponse(List<EventItem> eventItems) {
        this.eventItems = eventItems;
    }

    public List<EventItem> getEventItems() {
        return eventItems;
    }

    public void setEventItems(List<EventItem> eventItems) {
        this.eventItems = eventItems;
    }
}
