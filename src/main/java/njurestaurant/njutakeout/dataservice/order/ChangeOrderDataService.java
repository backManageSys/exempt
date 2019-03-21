package njurestaurant.njutakeout.dataservice.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;

public interface ChangeOrderDataService {
    QRcodeChangeOrder saveQRcodeChangeOrder(QRcodeChangeOrder QRcodeChangeOrder);

    CardChangeOrder saveCardChangeOrder(CardChangeOrder CardChangeOrder);

    QRcodeChangeOrder findByLoginId(String loginId);
    List<QRcodeChangeOrder> findAllQrCodeChangeOrder(String  condition,Integer page,Integer size);

    List<CardChangeOrder> findAllCardChangeOrder(String username,String condition,Integer page,Integer size);

    List<QRcodeChangeOrder> findAllQrCodeChangeOrderByDate(Date startDate, Date endDate);

    List<QRcodeChangeOrder> findAllQrCodeChangeOrderByLessThanDateAndisNotQueried(Date endDate);

    List<CardChangeOrder> findAllCardChangeOrderByDate(Date startDate, Date endDate);



    List<QRcodeChangeOrder> findQrCodeChangeOrderByOperateUsername(String username);
}
