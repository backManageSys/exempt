package njurestaurant.njutakeout.data.dao.event;

import njurestaurant.njutakeout.entity.event.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogDao extends JpaRepository<Log, Integer> {
    List<Log> findLogByUid(int uid);

}
