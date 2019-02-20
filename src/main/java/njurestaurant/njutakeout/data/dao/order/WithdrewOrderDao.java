package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrewOrderDao extends JpaRepository<WithdrewOrder, Integer> {
    List<WithdrewOrder> findWithdrewOrdersByState(WithdrewState withdrewState);

    List<WithdrewOrder> findWithdrewOrdersByOperateId(int id);

    List<WithdrewOrder> findAll(Specification<WithdrewOrder> dateBetween);
}
