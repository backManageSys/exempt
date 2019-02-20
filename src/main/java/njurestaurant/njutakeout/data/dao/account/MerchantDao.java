package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantDao extends JpaRepository<Merchant, Integer> {
    List<Merchant> findMerchantByStatus(String status);

    Merchant findByUserId(int id);

    List<Merchant> findMerchantsByApplyId(int id);

    Merchant findByName(String name);
}
