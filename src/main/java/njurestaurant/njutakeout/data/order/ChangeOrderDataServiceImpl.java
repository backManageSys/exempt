package njurestaurant.njutakeout.data.order;


import njurestaurant.njutakeout.data.dao.order.CardChangeOrderDao;
import njurestaurant.njutakeout.data.dao.order.QRcodeChangeOrderDao;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<QRcodeChangeOrder> findAllQrCodeChangeOrder(Pageable pageable, QRcodeChangeOrder qRcodeChangeOrder) {
        return condition(pageable, qRcodeChangeOrder);
    }

    @Override
    public List<CardChangeOrder> findAllCardChangeOrder(String username, Pageable pageable, CardChangeOrder cardChangeOrder) {
        return condition1(username,pageable, cardChangeOrder);
    }


    @Override
    public List<QRcodeChangeOrder> findAllQrCodeChangeOrderByDate(Date startDate, Date endDate) {
        return QRcodeChangeOrderDao.findAll(dateBetweenOfQrCode(startDate, endDate));
    }

    @Override
    public List<QRcodeChangeOrder> findAllQrCodeChangeOrderByLessThanDateAndisNotQueried(Date endDate) {
        return QRcodeChangeOrderDao.findAll(dateLessThanOrEqualToQrCode(endDate)).stream().filter(p -> !p.getQuery()).collect(Collectors.toList());
    }

    @Override
    public List<CardChangeOrder> findAllCardChangeOrderByDate(Date startDate, Date endDate) {
        return CardChangeOrderDao.findAll(dateBetweenOfCard(startDate, endDate));
    }


    @Override
    public List<QRcodeChangeOrder> findQrCodeChangeOrderByOperateUsername(String username) {

        return QRcodeChangeOrderDao.findQRcodeChangeOrderByOperateUsername(username);
    }


    private Specification<QRcodeChangeOrder> dateLessThanOrEqualToQrCode(Date endDate) {
        return new Specification<QRcodeChangeOrder>() {
            @Override
            public Predicate toPredicate(Root<QRcodeChangeOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(root.get("operateTime"), endDate);
            }
        };
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
                return cb.between(root.get("finalOperateTime"), startDate, endDate);
            }
        };
    }

    public List<QRcodeChangeOrder> condition(Pageable pageable, QRcodeChangeOrder qRcodeChangeOrder) {
        return QRcodeChangeOrderDao.findAll(new Specification<QRcodeChangeOrder>() {
            @Override
            public Predicate toPredicate(Root<QRcodeChangeOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(qRcodeChangeOrder.getLoginId())) {
                    list.add(cb.equal(root.get("loginId").as(String.class), qRcodeChangeOrder.getLoginId()));
                }
                if (!StringUtils.isEmpty(qRcodeChangeOrder.getCardNumber())) {
                    list.add(cb.equal(root.get("cardNumber").as(String.class), qRcodeChangeOrder.getCardNumber()));
                }
                if (!StringUtils.isEmpty(qRcodeChangeOrder.getState())) {
                    list.add(cb.equal(root.get("state").as(String.class), qRcodeChangeOrder.getState()));
                }
                if (qRcodeChangeOrder.getStartDate() != null) {
                    list.add(cb.greaterThan(root.get("operateTime").as(Date.class), qRcodeChangeOrder.getStartDate()));
                }
                if (qRcodeChangeOrder.getEndDate() != null) {
                    list.add(cb.lessThan(root.get("operateTime").as(Date.class), qRcodeChangeOrder.getEndDate()));
                }
                query.where(cb.and(list.toArray(new Predicate[list.size()])));
                return query.getRestriction();
            }
        }, pageable);
    }

    public List<CardChangeOrder> condition1(String username, Pageable pageable, CardChangeOrder cardChangeOrder) {
        return CardChangeOrderDao.findAll(new Specification<CardChangeOrder>() {
            @Override
            public Predicate toPredicate(Root<CardChangeOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(cardChangeOrder.getCardNumber_out())) {
                    list.add(cb.equal(root.get("cardNumber_out").as(String.class), cardChangeOrder.getCardNumber_out()));
                }
                if (!StringUtils.isEmpty(cardChangeOrder.getCardNumber_in())) {
                    list.add(cb.equal(root.get("cardNumber_in").as(String.class), cardChangeOrder.getCardNumber_in()));
                }
                if (cardChangeOrder.getState() != null) {
                    list.add(cb.equal(root.get("state").as(WithdrewState.class), cardChangeOrder.getState()));
                }
                if (cardChangeOrder.getWithdrewStateSql()!= null) {
                    int i=0;
                    for(WithdrewState withdrewState: WithdrewState.values())
                        if (String.valueOf(withdrewState).equals(cardChangeOrder.getWithdrewStateSql())) {
                            i=1;
                            break;
                        }
                    if (i == 1) {
                        WithdrewState withdrewState = WithdrewState.valueOf(cardChangeOrder.getWithdrewStateSql());
                        list.add(cb.equal(root.get("state").as(WithdrewState.class), withdrewState));
                    }else {
                        //查一个不存在的值，目的是返回空查询
                        list.add(cb.equal(root.get("state").as(WithdrewState.class), 5));
                        query.where(cb.and(list.toArray(new Predicate[list.size()])));
                        return query.getRestriction();
                    }
                }
                if (cardChangeOrder.getStartDate() != null) {
                    list.add(cb.greaterThan(root.get("operateTime").as(Date.class), cardChangeOrder.getStartDate()));
                }
                if (cardChangeOrder.getEndDate() != null) {
                    list.add(cb.lessThan(root.get("operateTime").as(Date.class), cardChangeOrder.getEndDate()));
                }
                if (username != null) {
                    list.add(cb.equal(root.get("operateUsername").as(String.class), username));
                }
                query.where(cb.and(list.toArray(new Predicate[list.size()])));
                return query.getRestriction();
            }
        }, pageable);
    }
}
