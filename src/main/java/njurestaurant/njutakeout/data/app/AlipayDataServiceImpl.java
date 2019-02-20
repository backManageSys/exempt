package njurestaurant.njutakeout.data.app;

import njurestaurant.njutakeout.data.dao.app.AlipayDao;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.entity.app.Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlipayDataServiceImpl implements AlipayDataService {
    private final AlipayDao alipayDao;

    @Autowired
    public AlipayDataServiceImpl(AlipayDao alipayDao) {
        this.alipayDao = alipayDao;
    }

    @Override
    public Alipay findById(int id) {
        Optional<Alipay> alipayOptional = alipayDao.findById(id);
        if (alipayOptional.isPresent()) return alipayOptional.get();
        else return null;
    }

    @Override
    public Alipay saveAlipay(Alipay alipay) {
        return alipayDao.save(alipay);
    }

    @Override
    public Alipay findByLoginId(String loginId) {
        Alipay alipay = alipayDao.findByLoginId(loginId);
        if (alipay != null)
            return alipay;
        else
            return null;
    }

    @Override
    public List<Alipay> findAll() {
        return alipayDao.findAll();
    }

    @Override
    public Alipay findByUserId(String userId) {
        return alipayDao.findByUserId(userId);
    }
}
