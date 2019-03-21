package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CardChangeOrderDao extends JpaRepository<CardChangeOrder, Integer> {


    /**
     *  TODO
     * 管理员查询卡账变订单 Timor 18点45分
     * @param condition
     * @param pageable
     * @return
     */
    @Query(value = "select * from card_change_order as s where 1=1 and  (s.card_balance like concat('%',?1,'%') " +
            " or s.card_number_in like concat('%',?1,'%')" +
            " or s.card_number_out like concat('%',?1,'%')" +
            " or s.money_in like concat('%',?1,'%')" +
            " or s.money_out like concat('%',?1,'%')" +
            " or s.operate_time like concat('%',?1,'%')" +
            " or s.operate_username like concat('%',?1,'%')" +
            " or s.state like concat('%',?1,'%')" +
            " or s.type like concat('%',?1,'%')" +
            ")",nativeQuery = true)
    Page<CardChangeOrder> findAll(String condition,Pageable pageable);


    /**
     *   TODO
     * 登陆用户查询卡账变订单 Timor  18点46分
     * @param operateUsername
     * @param condition
     * @param pageable
     * @return
     */
    @Query(value = "select * from card_change_order as s where 1=1 and operate_username=?1 and  (s.card_balance like concat('%',?1,'%') " +
            " or s.card_number_in like concat('%',?1,'%')" +
            " or s.card_number_out like concat('%',?1,'%')" +
            " or s.money_in like concat('%',?1,'%')" +
            " or s.money_out like concat('%',?1,'%')" +
            " or s.operate_time like concat('%',?1,'%')" +
            " or s.operate_username like concat('%',?1,'%')" +
            " or s.state like concat('%',?1,'%')" +
            " or s.type like concat('%',?1,'%')" +
            ")",nativeQuery = true)
    Page<CardChangeOrder> findAllByOperateUsername(String operateUsername,String condition,Pageable pageable);

    List<CardChangeOrder> findAll(Specification<CardChangeOrder> dateBetweenOfCard);

    List<CardChangeOrder> findByOperateUsername(String username);

    CardChangeOrder findById(int id);


}
