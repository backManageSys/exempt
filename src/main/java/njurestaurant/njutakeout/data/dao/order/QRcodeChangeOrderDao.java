package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QRcodeChangeOrderDao extends PagingAndSortingRepository<QRcodeChangeOrder, Integer> {

    List<QRcodeChangeOrder> findAll(Specification<QRcodeChangeOrder> dateBetweenOfQrCode, Pageable pageable);
    List<QRcodeChangeOrder> findAll(Specification<QRcodeChangeOrder> dateBetweenOfQrCode);

    List<QRcodeChangeOrder> findQRcodeChangeOrderByOperateUsername(String username);

    QRcodeChangeOrder findById(int id);

    QRcodeChangeOrder findByLoginId(String loginId);
}

