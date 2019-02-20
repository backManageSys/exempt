package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.CompanyCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCardDao extends JpaRepository<CompanyCard, Integer> {
    CompanyCard findCompanyCardByCardNumber(String cardNumber);
}
