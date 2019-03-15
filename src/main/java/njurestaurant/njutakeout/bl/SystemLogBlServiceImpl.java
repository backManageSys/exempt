package njurestaurant.njutakeout.bl;

import njurestaurant.njutakeout.blservice.SystemLogBlService;
import njurestaurant.njutakeout.data.dao.SystemLogLeftJoinModel;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.dataservice.SystemLogService;
import njurestaurant.njutakeout.entity.SystemLog;
import njurestaurant.njutakeout.response.company.SystemLogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemLogBlServiceImpl implements SystemLogBlService {


    private SystemLogService systemLogService;

    private UserDao userDao;

    @Autowired
    public SystemLogBlServiceImpl(SystemLogService systemLogService, UserDao userDao) {
        this.systemLogService = systemLogService;
        this.userDao = userDao;
    }

    /**
     * 分页获取系统日志信息
     * @param page 页码
     * @param size 每页多少
     * @return
     */
    @Override
    public SystemLogResponse findAll(Integer page, Integer size, String condition){
        condition = "%"+condition+"%";
        Page<SystemLogLeftJoinModel> all = systemLogService.findAll(page, size, condition);
        List<SystemLog> list = new ArrayList();
        for (SystemLogLeftJoinModel p:all) {
            list.add(new SystemLog(p.getId(),p.getRequestip(), p.getType(), p.getUserid(), p.getDescription(), p.getActiondate(), p.getExceptioncode(), p.getExceptiondetail(), p.getActionmethod(), p.getParams(), p.getUsername()));
        }
        return new SystemLogResponse(systemLogService.getCount(),list);
    }
}
