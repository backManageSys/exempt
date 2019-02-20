package njurestaurant.njutakeout.response.event;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class EventLoadResponse extends Response {
    private List<String> eventList;

    public EventLoadResponse() {
    }

    public EventLoadResponse(List<String> eventList) {
        this.eventList = eventList;
    }

    public List<String> getEventList() {
        return eventList;
    }

    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
    }
}
