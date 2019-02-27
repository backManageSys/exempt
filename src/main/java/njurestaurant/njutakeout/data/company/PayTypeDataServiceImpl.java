package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.account.PayTypeDao;
import njurestaurant.njutakeout.data.dao.company.PayPlatformDao;
import njurestaurant.njutakeout.dataservice.company.PayTypeDataService;
import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.company.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayTypeDataServiceImpl implements PayTypeDataService {
    private final PayTypeDao payTypeDao;
    @Autowired
    public PayTypeDataServiceImpl(PayTypeDao payTypeDao) {
        this.payTypeDao = payTypeDao;
    }

    @Override
    public PayType savePayType(PayType payType) {
        return payTypeDao.save(payType);
    }

    @Override
    public PayType findById(int id) {
        return payTypeDao.findById(id);
    }

    @Override
    public List<PayType> findAllPayType() {
        return payTypeDao.findAll();
    }

    @Override
    public List<PayType> findByCodeCategory(String codeCategory) {
        return payTypeDao.findByCodeCategory(codeCategory);
    }

}
