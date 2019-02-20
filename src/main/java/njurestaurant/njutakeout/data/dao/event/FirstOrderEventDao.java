package njurestaurant.njutakeout.data.dao.event;

import njurestaurant.njutakeout.entity.event.FirstOrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstOrderEventDao extends JpaRepository<FirstOrderEvent, String> {
}
