package njurestaurant.njutakeout.dataservice.event;

import njurestaurant.njutakeout.entity.event.Event;
import njurestaurant.njutakeout.exception.EventDoesNotExistException;

import java.util.List;

public interface EventDataService {
    /**
     * get list od all events
     *
     * @return
     */
    List<Event> getAllEvents();

    /**
     * add new event
     *
     * @param event
     */
    Event addEvent(Event event);

    /**
     * delete a event
     *
     * @param id
     */
    void deleteEvent(int id) throws EventDoesNotExistException;
}
