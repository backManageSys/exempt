package njurestaurant.njutakeout.data.dao.port;

import njurestaurant.njutakeout.entity.port.Port;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortDao extends JpaRepository<Port, Integer> {
    Port findPortByName(String name);
}
