package njurestaurant.njutakeout.data.dao;

import njurestaurant.njutakeout.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:31
 * @Version: 1.0
 */
public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {
}
