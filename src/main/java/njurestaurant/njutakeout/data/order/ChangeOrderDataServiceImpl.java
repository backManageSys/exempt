package njurestaurant.njutakeout.data.order;


import njurestaurant.njutakeout.data.dao.order.CardChangeOrderDao;
import njurestaurant.njutakeout.data.dao.order.QRcodeChangeOrderDao;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class ChangeOrderDataServiceImpl implements ChangeOrderDataService {
    private final QRcodeChangeOrderDao QRcodeChangeOrderDao;
    private final CardChangeOrderDao CardChangeOrderDao;

    @Autowired
    public ChangeOrderDataServiceImpl(QRcodeChangeOrderDao QRcodeChangeOrderDao, CardChangeOrderDao CardChangeOrderDao) {
        this.QRcodeChangeOrderDao = QRcodeChangeOrderDao;
        this.CardChangeOrderDao = CardChangeOrderDao;
    }

    @Override
    public QRcodeChangeOrder saveQRcodeChangeOrder(QRcodeChangeOrder QRcodeChangeOrder) {
        return QRcodeChangeOrderDao.save(QRcodeChangeOrder);
    }

    @Override
    public CardChangeOrder saveCardChangeOrder(CardChangeOrder CardChangeOrder) {
        return CardChangeOrderDao.save(CardChangeOrder);
    }

    @Override
    public QRcodeChangeOrder findByLoginId(String loginId) {
        return QRcodeChangeOrderDao.findByLoginId(loginId);
    }

    @Override
    public List<QRcodeChangeOrder> findAllQrCodeChangeOrder() {
        return QRcodeChangeOrderDao.findAll();
    }

    @Override
    public List<QRcodeChangeOrder> findAllQrCodeChangeOrderByDate(Date startDate, Date endDate) {
        return QRcodeChangeOrderDao.findAll(dateBetweenOfQrCode(startDate, endDate));
    }

    @Override
    public List<CardChangeOrder> findAllCardChangeOrderByDate(Date startDate, Date endDate) {
        return CardChangeOrderDao.findAll();
    }

    @Override
    public List<CardChangeOrder> findAllCardChangeOrder() {
        return CardChangeOrderDao.findAll();
    }

    @Override
    public List<QRcodeChangeOrder> findQrCodeChangeOrderByOperateUsername(String username) {

        return QRcodeChangeOrderDao.findQRcodeChangeOrderByOperateUsername(username);
    }

    private Specification<QRcodeChangeOrder> dateBetweenOfQrCode(Date startDate, Date endDate) {
        return new Specification<QRcodeChangeOrder>() {
            @Override
            public Predicate toPredicate(Root<QRcodeChangeOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("operateTime"), startDate, endDate);
            }
        };
    }

    private Specification<CardChangeOrder> dateBetweenOfCard(Date startDate, Date endDate) {
        return new Specification<CardChangeOrder>() {
            @Override
            public Predicate toPredicate(Root<CardChangeOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("operateTime"), startDate, endDate);
            }
        };
    }
}
