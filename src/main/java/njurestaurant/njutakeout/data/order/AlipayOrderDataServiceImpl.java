package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.app.AlipayOrderDao;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlipayOrderDataServiceImpl implements AlipayOrderDataService {
    private final AlipayOrderDao alipayOrderDao;

    @Autowired
    public AlipayOrderDataServiceImpl(AlipayOrderDao alipayOrderDao) {
        this.alipayOrderDao = alipayOrderDao;
    }

    @Override
    public AlipayOrder saveAlipayOrder(AlipayOrder alipayOrder) {
        return alipayOrderDao.save(alipayOrder);
    }

    @Override
    public void deleteAlipayOrderById(int id) {
        alipayOrderDao.deleteById(id);
    }

    @Override
    public AlipayOrder getAlipayOrderById(int id) {
        Optional<AlipayOrder> alipayOrderOptional =  alipayOrderDao.findById(id);
        if(alipayOrderOptional.isPresent()) return alipayOrderOptional.get();
        else return null;
    }

    @Override
    public AlipayOrder getAlipayOrderByOrderId(String orderId) {
        return alipayOrderDao.findAlipayOrderByOrderId(orderId);
    }
}
