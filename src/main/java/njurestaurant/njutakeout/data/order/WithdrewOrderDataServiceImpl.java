package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.order.WithdrewOrderDao;
import njurestaurant.njutakeout.dataservice.order.WithdrewOrderDataService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrewOrderDataServiceImpl implements WithdrewOrderDataService {
    private final WithdrewOrderDao withdrewOrderDao;

    @Autowired
    public WithdrewOrderDataServiceImpl(WithdrewOrderDao withdrewOrderDao) {
        this.withdrewOrderDao = withdrewOrderDao;
    }

    @Override
    public WithdrewOrder saveWithdrewOrder(WithdrewOrder withdrewOrder) {
        return withdrewOrderDao.save(withdrewOrder);
    }

    @Override
    public void deleteById(int id) {
        withdrewOrderDao.deleteById(id);
    }

    @Override
    public WithdrewOrder findWithdrewOrderById(int id) {
        Optional<WithdrewOrder> withdrewOrderOptional = withdrewOrderDao.findById(id);
        return withdrewOrderOptional.isPresent()? withdrewOrderOptional.get() : null;
    }

    @Override
    public List<WithdrewOrder> findAll() {
        return withdrewOrderDao.findAll();
    }

    @Override
    public List<WithdrewOrder> findByState(WithdrewState withdrewState) {
        return withdrewOrderDao.findWithdrewOrdersByState(withdrewState);
    }

    @Override
    public List<WithdrewOrder> findByOperatorId(int id) {
        return withdrewOrderDao.findWithdrewOrdersByOperateId(id);
    }

    @Override
    public List<WithdrewOrder> findByDateRange(Date d1, Date d2) {
        return withdrewOrderDao.findAll(dateBetween(d1, d2));
    }

    private Specification<WithdrewOrder> dateBetween(Date startDate, Date endDate) {
        return new Specification<WithdrewOrder>() {
            @Override
            public Predicate toPredicate(Root<WithdrewOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("applyTime"), startDate, endDate);
            }
        };
    }
}
