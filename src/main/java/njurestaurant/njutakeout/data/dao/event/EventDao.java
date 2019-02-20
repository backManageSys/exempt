package njurestaurant.njutakeout.data.dao.event;

import njurestaurant.njutakeout.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Integer> {
}
