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

    List<PlatformOrder> findByImeiAndCodeType(String imei , CodeType codeType);

    List<PlatformOrder>  findByImeiAndStateAndCodeType(String imei, OrderState orderState,CodeType codeType);

    PlatformOrder findByImeiAndStateAndCodeTypeAndMoney(String imei, OrderState orderState,CodeType codeType,double money);
}
