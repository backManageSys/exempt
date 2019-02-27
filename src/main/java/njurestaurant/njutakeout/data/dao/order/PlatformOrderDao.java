package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatformOrderDao extends JpaRepository<PlatformOrder, Integer> {
    PlatformOrder findPlatformOrderByNumber(String number);

    List<PlatformOrder> findPlatformOrderByUid(int uid);

    List<PlatformOrder> findPlatformOrderByUidAndState(int uid, OrderState orderState);

    List<PlatformOrder> findPlatformsByState(OrderState orderState);

    PlatformOrder findPlatformOrdersByImeiAndState(String imei, OrderState state);

    List<PlatformOrder> findAll(Specification<PlatformOrder> dateBetween);

    List<PlatformOrder> findPlatformOrderByImeiAndPayTypeId(String imei, int payTypeId);

    PlatformOrder findPlatformOrderByImeiAndStateAndPayTypeIdAndMoney(String imei, OrderState state,int payTypeId,double money);

    List<PlatformOrder>  findPlatformOrderByImeiAndStateAndPayTypeId(String imei, OrderState state,int payTypeId);
}
