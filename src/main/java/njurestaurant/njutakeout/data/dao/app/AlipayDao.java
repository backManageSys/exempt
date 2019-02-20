package njurestaurant.njutakeout.data.dao.app;

import njurestaurant.njutakeout.entity.app.Alipay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlipayDao extends JpaRepository<Alipay, Integer> {
    Alipay findByLoginId(String loginId);
    Alipay findByUserId(String userId);
}
