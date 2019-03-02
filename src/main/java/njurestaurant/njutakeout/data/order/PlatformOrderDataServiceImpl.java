package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.order.PlatformOrderDao;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.Optional;

@Service
public class PlatformOrderDataServiceImpl implements PlatformOrderDataService {
    private final PlatformOrderDao platformOrderDao;

    @Autowired
    public PlatformOrderDataServiceImpl(PlatformOrderDao platformOrderDao) {
        this.platformOrderDao = platformOrderDao;
    }

    @Override
    public PlatformOrder findById(int id) {
        Optional<PlatformOrder> platformOrderOption = platformOrderDao.findById(id);
        if (platformOrderOption.isPresent()) return platformOrderOption.get();
        else return null;
    }

    @Override
    public List<PlatformOrder> findByUid(int uid) {
        return platformOrderDao.findPlatformOrderByUid(uid);
    }

    @Override
    public PlatformOrder savePlatformOrder(PlatformOrder platformOrder) {
        return platformOrderDao.save(platformOrder);
    }

    @Override
    public void deleteById(int id) {
        platformOrderDao.deleteById(id);
    }

    @Override
    public List<PlatformOrder> findByState(OrderState orderState) {
        return platformOrderDao.findPlatformsByState(orderState);
    }

    @Override
    public Page<PlatformOrder> findAll(Pageable pageable, PlatformOrder platformOrder) {
        return condition(pageable, platformOrder);
    }

    @Override
    public List<PlatformOrder> findByUidAndState(int uid, OrderState orderState) {
        return platformOrderDao.findPlatformOrderByUidAndState(uid, orderState);
    }

    @Override
    public PlatformOrder findByNumber(String number) {
        return platformOrderDao.findPlatformOrderByNumber(number);
    }

    @Override
    public PlatformOrder findByImeiAndState(String imei, OrderState orderState) {
        return platformOrderDao.findPlatformOrdersByImeiAndState(imei, orderState);
    }

    @Override
    public void savePlatformOrders(List<PlatformOrder> platformOrders) {
        platformOrderDao.saveAll(platformOrders);
    }

    @Override
    public List<PlatformOrder> findPlatformByDate(Date startDate, Date endDate) {
        return platformOrderDao.findAll(dateBetween(startDate, endDate));
    }

    @Override
    public List<PlatformOrder> findByImeiAndPayTypeId(String imei, int payTypeId) {
        return platformOrderDao.findPlatformOrderByImeiAndPayTypeId(imei, payTypeId);
    }

    @Override
    public PlatformOrder findByImeiAndStateAndPayTypeIdAndMoney(String imei, OrderState orderState, int payTypeId, double money) {
        return platformOrderDao.findPlatformOrderByImeiAndStateAndPayTypeIdAndMoney(imei, orderState, payTypeId, money);
    }


    @Override
    public List<PlatformOrder> findByImeiAndStateAndPayTypeId(String imei, OrderState orderState, int payTypeId) {
        return platformOrderDao.findPlatformOrderByImeiAndStateAndPayTypeId(imei, orderState, payTypeId);
    }

    private Specification<PlatformOrder> dateBetween(Date startDate, Date endDate) {
        return new Specification<PlatformOrder>() {
            @Override
            public Predicate toPredicate(Root<PlatformOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("time"), startDate, endDate);
            }
        };
    }

    public Page<PlatformOrder> condition(Pageable pageable, PlatformOrder platformOrder) {
        return platformOrderDao.findAll(new Specification<PlatformOrder>() {
            @Override
            public Predicate toPredicate(Root<PlatformOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(platformOrder.getImei())) {
                    list.add(cb.equal(root.get("imei").as(String.class), platformOrder.getImei()));
                }
                if (platformOrder.getState() != null) {
                    list.add(cb.equal(root.get("state").as(OrderState.class), platformOrder.getState()));
                }
                if (platformOrder.getPayTypeId() != 0) {
                    list.add(cb.equal(root.get("payTypeId").as(Integer.class), platformOrder.getPayTypeId()));
                }
                if (!StringUtils.isEmpty(platformOrder.getRechargeId())) {
                    list.add(cb.equal(root.get("rechargeId").as(String.class), platformOrder.getRechargeId()));
                }
                if (!StringUtils.isEmpty(platformOrder.getNumber())) {
                    list.add(cb.equal(root.get("number").as(String.class), platformOrder.getNumber()));
                }
                if (!StringUtils.isEmpty(platformOrder.getMerchantName())) {
                    list.add(cb.equal(root.get("merchantName").as(String.class), platformOrder.getMerchantName()));
                }
                if (!StringUtils.isEmpty(platformOrder.getType())) {
                    list.add(cb.equal(root.get("type").as(String.class), platformOrder.getType()));
                }
                if (platformOrder.getStartDate() != null) {
                    list.add(cb.greaterThan(root.get("time").as(Date.class), platformOrder.getStartDate()));
                }
                if (platformOrder.getEndDate() != null) {
                    list.add(cb.lessThan(root.get("time").as(Date.class), platformOrder.getEndDate()));
                }
                query.where(cb.and(list.toArray(new Predicate[list.size()])));
                return query.getRestriction();
            }
        }, pageable);
    }

}
