package njurestaurant.njutakeout.response.event;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class EventFoodLoadResponse extends Response {
    private List<EventFoodItem> eventFoodItemList;

    public EventFoodLoadResponse() {
    }

    public EventFoodLoadResponse(List<EventFoodItem> eventFoodItemList) {
        this.eventFoodItemList = eventFoodItemList;
    }

    public List<EventFoodItem> getEventFoodItemList() {
        return eventFoodItemList;
    }

    public void setEventFoodItemList(List<EventFoodItem> eventFoodItemList) {
        this.eventFoodItemList = eventFoodItemList;
    }
}
