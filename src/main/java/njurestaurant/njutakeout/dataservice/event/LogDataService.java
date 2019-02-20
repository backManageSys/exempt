package njurestaurant.njutakeout.dataservice.event;

import njurestaurant.njutakeout.entity.event.Log;

import java.util.List;

public interface LogDataService {

    Log saveLog(Log log);

    void deleteLogById(int id);

    List<Log> findAllByTimeDESC();

    List<Log> findByUid(int uid);

}
