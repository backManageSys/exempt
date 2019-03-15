package njurestaurant.njutakeout.blservice;

import njurestaurant.njutakeout.dataservice.SystemLogService;
import njurestaurant.njutakeout.entity.SystemLog;
import njurestaurant.njutakeout.response.company.SystemLogResponse;

import java.util.List;

public interface SystemLogBlService {
    /**
     * 分页获取系统日志信息
     * @param page 页码
     * @param size 每页多少
     * @return
     */
     SystemLogResponse  findAll(Integer page, Integer size, String condition);
}
