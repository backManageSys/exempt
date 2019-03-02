package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CardChangeOrderDao extends JpaRepository<CardChangeOrder, Integer> {

    List<CardChangeOrder> findAll(Specification<CardChangeOrder> dateBetweenOfCard, Pageable pageable);

    List<CardChangeOrder> findAll(Specification<CardChangeOrder> dateBetweenOfCard);

    List<CardChangeOrder> findByOperateUsername(String username);

    CardChangeOrder findById(int id);


}
