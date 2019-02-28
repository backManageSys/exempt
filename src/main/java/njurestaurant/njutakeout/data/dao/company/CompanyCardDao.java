package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.CompanyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyCardDao extends JpaRepository<CompanyCard, Integer> {
    CompanyCard findCompanyCardByCardNumber(String cardNumber);
    List<CompanyCard> findByOperateId(int uid);
}
