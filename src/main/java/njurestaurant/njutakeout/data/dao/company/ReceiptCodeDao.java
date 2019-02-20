package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptCodeDao extends JpaRepository<ReceiptCode, Integer> {
    ReceiptCode findReceiptCodeByAccountNumber(String accountNumber);
}
