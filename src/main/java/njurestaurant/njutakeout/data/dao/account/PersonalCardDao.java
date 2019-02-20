package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.PersonalCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalCardDao extends JpaRepository<PersonalCard, Integer> {
    List<PersonalCard> findPersonalCardByUserId(int id);

    PersonalCard findPersonalCardByCardNumber(String cardNumber);

    PersonalCard findByAccountWithBank(String accountWithBank);
}
