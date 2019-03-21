package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QRcodeChangeOrderDao extends PagingAndSortingRepository<QRcodeChangeOrder, Integer> {


    @Query(value = "select * from qrcode_change_order as s where 1=1 and (s.balance like concat('%',?1,'%') " +
                                                                        " or s.card_balance like concat('%',?1,'%')" +
                                                                        " or s.card_number like concat('%',?1,'%')" +
                                                                        " or s.login_id like concat('%',?1,'%')" +
                                                                        " or s.money like concat('%',?1,'%')" +
                                                                        " or s.operate_username like concat('%',?1,'%')" +
                                                                        " or s.real_money like concat('%',?1,'%')" +
            ")",nativeQuery = true)
    Page<QRcodeChangeOrder> findAll(String  condition,Pageable pageable);

    List<QRcodeChangeOrder> findAll(Specification<QRcodeChangeOrder> dateBetweenOfQrCode);

    List<QRcodeChangeOrder> findQRcodeChangeOrderByOperateUsername(String username);

    QRcodeChangeOrder findById(int id);

    QRcodeChangeOrder findByLoginId(String loginId);

}

