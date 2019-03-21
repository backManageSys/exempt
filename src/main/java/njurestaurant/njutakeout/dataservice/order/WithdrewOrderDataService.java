package njurestaurant.njutakeout.dataservice.order;

import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface WithdrewOrderDataService {
    WithdrewOrder saveWithdrewOrder(WithdrewOrder withdrewOrder);

    void deleteById(int id);

    WithdrewOrder findWithdrewOrderById(int id);

    List<WithdrewOrder> findAll();

    Page<WithdrewOrder> findByState(int id,WithdrewState withdrewState, Pageable pageable, WithdrewOrder withdrewOrder);

    Page<WithdrewOrder> findAll(String condition, Pageable pageable);

    List<WithdrewOrder> findByOperatorId(int id);

    List<WithdrewOrder> findByDateRange(Date d1, Date d2);


}
