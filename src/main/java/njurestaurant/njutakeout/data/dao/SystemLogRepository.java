package njurestaurant.njutakeout.data.dao;

import njurestaurant.njutakeout.entity.SystemLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:31
 * @Version: 1.0
 */
public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {

    @Query(value = "select * from systemlog as s where description like ?1",nativeQuery=true)
    Page<SystemLog> findBySearch(String condition, Pageable pageable);

}
