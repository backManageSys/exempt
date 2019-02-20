package njurestaurant.njutakeout.dataservice.order;

import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;

import java.util.Date;
import java.util.List;

public interface WithdrewOrderDataService {
    WithdrewOrder saveWithdrewOrder(WithdrewOrder withdrewOrder);

    void deleteById(int id);

    WithdrewOrder findWithdrewOrderById(int id);

    List<WithdrewOrder> findAll();

    List<WithdrewOrder> findByState(WithdrewState withdrewState);

    List<WithdrewOrder> findByOperatorId(int id);

    List<WithdrewOrder> findByDateRange(Date d1, Date d2);


}
