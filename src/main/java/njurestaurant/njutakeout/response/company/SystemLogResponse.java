package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.entity.SystemLog;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

public class SystemLogResponse {

    private Long count;

    private List<SystemLog> systemLogs;

    public SystemLogResponse(Long count, List<SystemLog> systemLogs) {
        this.count = count;
        this.systemLogs = systemLogs;
    }

    public SystemLogResponse() {
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<SystemLog> getSystemLogs() {
        return systemLogs;
    }

    public void setSystemLogs(List<SystemLog> systemLogs) {
        this.systemLogs = systemLogs;
    }
}
