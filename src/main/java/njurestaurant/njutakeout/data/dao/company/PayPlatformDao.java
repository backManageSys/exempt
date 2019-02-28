package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.account.PayRateList;
import njurestaurant.njutakeout.entity.company.PayPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayPlatformDao extends JpaRepository<PayPlatform, Integer> {
    PayPlatform findById(int id);

}
