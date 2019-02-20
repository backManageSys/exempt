package njurestaurant.njutakeout.data.event;

import njurestaurant.njutakeout.data.dao.event.LogDao;
import njurestaurant.njutakeout.dataservice.event.LogDataService;
import njurestaurant.njutakeout.entity.event.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogDataServiceImpl implements LogDataService {
    private final LogDao logDao;

    @Autowired
    public LogDataServiceImpl(LogDao logDao) {
        this.logDao = logDao;
    }

    @Override
    public Log saveLog(Log log) {
        return logDao.save(log);
    }

    @Override
    public void deleteLogById(int id) {
        logDao.deleteById(id);
    }

    @Override
    public List<Log> findAllByTimeDESC() {
//        return logDao.findAllByOrOrderByTimeAsc();
        return null;
    }

    @Override
    public List<Log> findByUid(int uid) {
        return logDao.findLogByUid(uid);
    }
}
