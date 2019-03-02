package njurestaurant.njutakeout.dataservice.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;

import java.util.Date;
import java.util.List;

public interface PlatformOrderDataService {
    PlatformOrder findById(int id);

    List<PlatformOrder> findByUid(int uid);

    PlatformOrder savePlatformOrder(PlatformOrder platformOrder);

    void deleteById(int id);

    List<PlatformOrder> findByState(OrderState orderState);

    List<PlatformOrder> findAll();

    List<PlatformOrder> findByUidAndState(int uid, OrderState orderState);

    PlatformOrder findByNumber(String number);

    PlatformOrder findByImeiAndState(String imei, OrderState orderState);

    void savePlatformOrders(List<PlatformOrder> platformOrders);

    List<PlatformOrder> findPlatformByDate(Date startDate, Date endDate);

    List<PlatformOrder> findByImeiAndPayTypeId(String imei , int payTypeId);

    List<PlatformOrder>  findByImeiAndStateAndPayTypeId(String imei, OrderState orderState,int payTypeId);

    PlatformOrder findByImeiAndStateAndPayTypeIdAndMoney(String imei, OrderState orderState,int payTypeId,double money);

    List<PlatformOrder> findByConditions(String imei, OrderState orderState,int payTypeId,Date startDate,Date endDate);
}
