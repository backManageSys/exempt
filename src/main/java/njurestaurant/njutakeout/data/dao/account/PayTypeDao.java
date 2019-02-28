package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.company.PayType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PayTypeDao extends JpaRepository<PayType, Integer> {
    PayType findById(int id);
    List<PayType> findByCodeCategory(String codeCategory);
}
