package njurestaurant.njutakeout.dataservice.app;

import njurestaurant.njutakeout.entity.app.Alipay;

import java.util.List;

public interface AlipayDataService {

    Alipay findById(int id);

    Alipay saveAlipay(Alipay alipay);

    Alipay findByLoginId(String loginId);

    List<Alipay> findAll();

    Alipay findByUserId(String userid);


}
