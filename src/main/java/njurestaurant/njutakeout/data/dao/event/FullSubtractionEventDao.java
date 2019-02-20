package njurestaurant.njutakeout.data.dao.event;

import njurestaurant.njutakeout.entity.event.FullSubtractionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FullSubtractionEventDao extends JpaRepository<FullSubtractionEvent, String> {
}
