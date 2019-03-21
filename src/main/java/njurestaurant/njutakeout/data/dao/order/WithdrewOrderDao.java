package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WithdrewOrderDao extends JpaRepository<WithdrewOrder, Integer> {
    List<WithdrewOrder> findWithdrewOrdersByState(WithdrewState withdrewState);

    List<WithdrewOrder> findWithdrewOrdersByOperateId(int id);

    List<WithdrewOrder> findAll(Specification<WithdrewOrder> dateBetween);
    Page<WithdrewOrder> findAll(Specification<WithdrewOrder> dateBetween, Pageable pageable);


    //提现编号
    //提现类型（merchant 或者 agent）
    //转出卡卡号
    //转入卡卡号
    //申请时间
    //申请人id
    // 提现状态
    //操作人id
    @Query(value = "select *   from withdrew_order as s  where 1=1 and  ( " +
            "  s.number like concat('%',?1,'%')" +
            " or s.type like concat('%',?1,'%')" +
            " or s.card_in like concat('%',?1,'%')" +
            " or s.card_out  like concat('%',?1,'%')" +
            " or s.apply_time like concat('%',?1,'%')" +
            " or s.applicant_id like concat('%',?1,'%')" +
            " or s.state like concat('%',?2,'%')" +
            " or s.operate_id  like concat('%',?1,'%')" +
            ")",nativeQuery = true)
    Page<WithdrewOrder> findAll(String condition,String state,Pageable pageable);
}
