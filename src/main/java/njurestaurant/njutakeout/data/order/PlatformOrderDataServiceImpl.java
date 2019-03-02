package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.order.PlatformOrderDao;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.print.Pageable;
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
    public List<PlatformOrder> findAll() {
        return platformOrderDao.findAll();
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
        return platformOrderDao.findPlatformOrderByImeiAndPayTypeId(imei,payTypeId);
    }

    @Override
    public PlatformOrder findByImeiAndStateAndPayTypeIdAndMoney(String imei, OrderState orderState, int payTypeId , double money) {
        return platformOrderDao.findPlatformOrderByImeiAndStateAndPayTypeIdAndMoney(imei,orderState,payTypeId,money);
    }

    @Override
    public List<PlatformOrder> findByConditions(String imei, OrderState orderState, int payTypeId, Date startDate, Date endDate) {
        return condition(imei,orderState,payTypeId,startDate,endDate);
    }

    @Override
    public List<PlatformOrder>  findByImeiAndStateAndPayTypeId(String imei, OrderState orderState, int payTypeId) {
        return platformOrderDao.findPlatformOrderByImeiAndStateAndPayTypeId(imei,orderState,payTypeId);
    }
    private Specification<PlatformOrder> dateBetween(Date startDate, Date endDate) {
        return new Specification<PlatformOrder>() {
            @Override
            public Predicate toPredicate(Root<PlatformOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("time"), startDate, endDate);
            }
        };
    }
    public List<PlatformOrder> condition(String imei, OrderState orderState,int payTypeId,Date startDate,Date endDate) {
        return platformOrderDao.findAll(new Specification<PlatformOrder>() {
            @Override
            public Predicate toPredicate(Root<PlatformOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //条件1 用户
                if(!StringUtils.isEmpty(imei)){
                    list.add(cb.equal(root.get("imei").as(String.class),imei));
                }
                //条件2 类型
                if(orderState!=null){
                    list.add(cb.equal(root.get("state").as(OrderState.class),orderState));
                }
                if(payTypeId != 0){
                    list.add(cb.equal(root.get("payTypeId").as(Integer.class),payTypeId));
                }
                if(startDate != null){
                    list.add(cb.greaterThan(root.get("time").as(Date.class),startDate));
                }
                if(endDate != null){
                    list.add(cb.lessThan(root.get("time").as(Date.class),endDate));
                }
                query.where(cb.and(list.toArray(new Predicate[list.size()])));
                return query.getRestriction();
            }
        });
    }

}
