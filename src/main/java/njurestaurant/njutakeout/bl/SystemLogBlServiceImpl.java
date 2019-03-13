package njurestaurant.njutakeout.bl;

import njurestaurant.njutakeout.blservice.SystemLogBlService;
import njurestaurant.njutakeout.dataservice.SystemLogService;
import njurestaurant.njutakeout.entity.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogBlServiceImpl implements SystemLogBlService {

    private SystemLogService systemLogService;

    @Autowired
    public SystemLogBlServiceImpl(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * 分页获取系统日志信息
     * @param page 页码
     * @param size 每页多少
     * @return
     */
    @Override
    public List<SystemLog> findAll(Integer page, Integer size,String condition){
        condition = "%"+condition+"%";
        return systemLogService.findAll(page, size,condition);
    }
}
