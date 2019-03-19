package njurestaurant.njutakeout.dataservice;

import njurestaurant.njutakeout.data.dao.SystemLogLeftJoinModel;
import njurestaurant.njutakeout.entity.SystemLog;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:43
 * @Version: 1.0
 */
public interface SystemLogService {

    public List<SystemLog> findAll(Integer page, Integer size, String condition);

    public void save(SystemLog log);

    public SystemLog findOne(long id);

    public void delete(long id);


    Long getCount();
}
