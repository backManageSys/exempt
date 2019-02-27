package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.account.PayTypeDao;
import njurestaurant.njutakeout.data.dao.company.PayPlatformDao;
import njurestaurant.njutakeout.dataservice.company.PayPlatformDataService;
import njurestaurant.njutakeout.dataservice.company.PayTypeDataService;
import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.company.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayPlatformDataServiceImpl implements PayPlatformDataService {
    private final PayPlatformDao payPlatformDao;
    @Autowired
    public PayPlatformDataServiceImpl(PayPlatformDao payPlatformDao) {
        this.payPlatformDao = payPlatformDao;
    }


    @Override
    public PayPlatform savePayPlatform(PayPlatform payPlatform) {
        return payPlatformDao.save(payPlatform);
    }

    @Override
    public PayPlatform findById(int id) {
        return payPlatformDao.findById(id);
    }

    @Override
    public List<PayPlatform> findAllPayPlatform() {
        return payPlatformDao.findAll();
    }
}
