package njurestaurant.njutakeout.data.dao;

import njurestaurant.njutakeout.entity.SystemLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:31
 * @Version: 1.0
 */
public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {

    @Query(value = "select t1.*,t2.username as username from systemlog as t1 left join user as t2 on t1.userid = t2.id where t1.description like ?1",
            countQuery = "select count(t1.id) from systemlog as t1 left join user as t2 on t1.userid = t2.id where t1.description like ?1",nativeQuery=true)
    Page<SystemLogLeftJoinModel> findBySearch(String condition, Pageable pageable);

    @Query(value = "select count(t1.id) from systemlog as t1 left join user as t2 on t1.userid = t2.id where t1.description like ?1",nativeQuery=true)
    Long getCount();


}
