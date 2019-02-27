package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.PayTypeDao;
import njurestaurant.njutakeout.data.dao.company.PayRateListDao;
import njurestaurant.njutakeout.dataservice.account.PayRateListDataService;
import njurestaurant.njutakeout.entity.account.PayRateList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayRateListDataServiceImpl implements PayRateListDataService {
    private final PayRateListDao payRateListDao;

    @Autowired
    public PayRateListDataServiceImpl(PayRateListDao payRateListDao) {
        this.payRateListDao = payRateListDao;
    }

    @Override
    public PayRateList findById(int id) {
        return payRateListDao.findById(id);
    }

    @Override
    public PayRateList savePayRateList(PayRateList payRateList) {
        return payRateListDao.save(payRateList);
    }

    @Override
    public PayRateList findByUidAndPayTypeId(int uid, int payTypeId) {
        return payRateListDao.findByUidAndPayTypeId(uid, payTypeId);
    }

    @Override
    public int[] findAllUid() {
        return payRateListDao.findAllUid();
    }

    @Override
    public List<PayRateList> findByUid(int uid) {
        return payRateListDao.findByUid(uid);
    }
}
