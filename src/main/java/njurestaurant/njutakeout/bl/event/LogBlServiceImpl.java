package njurestaurant.njutakeout.bl.event;

import njurestaurant.njutakeout.blservice.event.LogBlService;
import njurestaurant.njutakeout.dataservice.event.LogDataService;
import njurestaurant.njutakeout.entity.event.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogBlServiceImpl implements LogBlService {
    private LogDataService logDataService;

    @Autowired
    public LogBlServiceImpl(LogDataService logDataService) {
        this.logDataService = logDataService;
    }

    @Override
    public void addLog(Log log) {
        logDataService.saveLog(log);
    }

    @Override
    public List<Log> findLogByUId(int uid) {
        return logDataService.findByUid(uid);
    }

    @Override
    public List<Log> findLogByDateDESC() {
        return logDataService.findAllByTimeDESC();
    }
}
