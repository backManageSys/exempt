package njurestaurant.njutakeout.blservice.event;

import njurestaurant.njutakeout.entity.event.Log;

import java.util.List;

public interface LogBlService {

    void addLog(Log log);

    List<Log> findLogByUId(int uid);

    List<Log> findLogByDateDESC();
}
