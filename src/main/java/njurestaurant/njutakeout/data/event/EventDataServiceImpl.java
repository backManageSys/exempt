package njurestaurant.njutakeout.data.event;

import njurestaurant.njutakeout.data.dao.event.EventDao;
import njurestaurant.njutakeout.dataservice.event.EventDataService;
import njurestaurant.njutakeout.entity.event.Event;
import njurestaurant.njutakeout.exception.EventDoesNotExistException;
import njurestaurant.njutakeout.publicdatas.event.EventState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventDataServiceImpl implements EventDataService {
    private final EventDao eventDao;

    @Autowired
    public EventDataServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * get list od all events
     *
     * @return
     */
    @Override
    public List<Event> getAllEvents() {
        return eventDao.findAll();
    }

    /**
     * add new event
     *
     * @param event
     */
    @Override
    public Event addEvent(Event event) {
        return eventDao.save(event);
    }

    /**
     * delete a event
     *
     * @param id
     */
    @Override
    public void deleteEvent(int id) throws EventDoesNotExistException {
        Optional<Event> eventOptional = eventDao.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setEventState(EventState.INACTIVE);
            eventDao.save(event);
        } else {
            throw new EventDoesNotExistException();
        }
    }
}
