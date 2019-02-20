package njurestaurant.njutakeout.data.dao.app;

import njurestaurant.njutakeout.entity.order.AlipayOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlipayOrderDao extends JpaRepository<AlipayOrder, Integer> {
    AlipayOrder findAlipayOrderByOrderId(String orderId);
}
