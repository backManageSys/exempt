package njurestaurant.njutakeout.data;
import njurestaurant.njutakeout.data.dao.SystemLogIn;
import njurestaurant.njutakeout.data.dao.SystemLogRepository;
import njurestaurant.njutakeout.dataservice.SystemLogService;
import njurestaurant.njutakeout.entity.SystemLog;
import njurestaurant.njutakeout.response.company.SystemLogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:45
 * @Version: 1.0
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    SystemLogRepository systemLogRepository ;
    @Override
    public  Page<SystemLogIn> findAll(Integer page, Integer size, String condition) {
        Pageable pageable = new PageRequest(page,size);
        // 获取分页数据
        Page<SystemLogIn> logPage = systemLogRepository.findBySearch(condition,pageable);

       return  logPage;
    }

    @Override
    public void save(SystemLog log) {
        systemLogRepository.save(log);
    }

    @Override
    public SystemLog findOne(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
