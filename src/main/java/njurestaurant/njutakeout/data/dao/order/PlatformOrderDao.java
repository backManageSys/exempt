package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlatformOrderDao extends PagingAndSortingRepository<PlatformOrder, Integer> {
    PlatformOrder findPlatformOrderByNumber(String number);

    List<PlatformOrder> findPlatformOrderByUid(int uid);

    List<PlatformOrder> findPlatformOrderByUidAndState(int uid, OrderState orderState);

    List<PlatformOrder> findPlatformsByState(OrderState orderState);

    PlatformOrder findPlatformOrdersByImeiAndState(String imei, OrderState state);


    @Query(value = "select *   from platform_order as s  where 1=1 and  ( " +
            "  s.ip like concat('%',?1,'%')" +
            " or s.recharge_id like concat('%',?1,'%')" +
            " or s.number like concat('%',?1,'%')" +
            " or s.pay_type_id  like concat('%',?1,'%')" +
            " or s.type like concat('%',?1,'%')" +
            " or s.time like concat('%',?1,'%')" +
            " or s.uid like concat('%',?2,'%')" +
            ")",nativeQuery = true)
    Page<PlatformOrder> findAll(String condition,String uid,Pageable pageable);

    List<PlatformOrder> findAll(Specification<PlatformOrder> dateBetween);

    List<PlatformOrder> findPlatformOrderByImeiAndPayTypeId(String imei, int payTypeId);

    PlatformOrder findPlatformOrderByImeiAndStateAndPayTypeIdAndMoney(String imei, OrderState state,int payTypeId,double money);

    List<PlatformOrder>  findPlatformOrderByImeiAndStateAndPayTypeId(String imei, OrderState state,int payTypeId);

    List<PlatformOrder>  findByImeiAndPayTypeIdAndStateAndType(String imei,int payTypeId,OrderState state,String type);
}
