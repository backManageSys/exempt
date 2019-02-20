package njurestaurant.njutakeout.blservice.event;

import njurestaurant.njutakeout.exception.EventDoesNotExistException;
import njurestaurant.njutakeout.parameters.event.EventAddParameters;
import njurestaurant.njutakeout.response.event.EventAddResponse;
import njurestaurant.njutakeout.response.event.EventDeleteResponse;
import njurestaurant.njutakeout.response.event.EventLoadResponse;
import njurestaurant.njutakeout.response.event.EventWithIdLoadResponse;

public interface EventBlService {
    /**
     * load all events
     *
     * @return
     */
    EventLoadResponse loadEvents();

    /**
     * load all events with their ids
     *
     * @return
     */
    EventWithIdLoadResponse loadEventsWithId();

    /**
     * delete the event by id
     *
     * @param eventId
     * @return
     */
    EventDeleteResponse deleteEvent(int eventId) throws EventDoesNotExistException;

    /**
     * add event
     *
     * @param eventAddParameters
     * @return
     */
    EventAddResponse addEvent(EventAddParameters eventAddParameters);
}
