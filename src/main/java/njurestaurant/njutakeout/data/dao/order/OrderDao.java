package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.Order;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUserUsernameAndOrderStateIsNot(String username, OrderState orderState);

    List<Order> findOrdersByOrderStateIsNot(OrderState orderState);

    List<Order> findOrdersByUserUsername(String username);
}
