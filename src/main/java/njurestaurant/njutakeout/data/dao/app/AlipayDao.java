package njurestaurant.njutakeout.data.dao.app;

import njurestaurant.njutakeout.entity.app.Alipay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlipayDao extends JpaRepository<Alipay, Integer> {
    Alipay findByLoginId(String loginId);
    Alipay findByUserId(String userId);
    Alipay findByCardNumber(String cardNumber);
    List<Alipay> findByImei(String imei);
}
