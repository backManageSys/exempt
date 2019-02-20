package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.PersonalCard;

import java.util.List;

public interface PersonalCardDataService {
    /**
     * save the personal bank card
     *
     * @param personalCard the info of the bank card
     * @return
     */
    PersonalCard savePersonalCard(PersonalCard personalCard);

    PersonalCard findPersonalCardById(int id);

    PersonalCard findPersonalCardByAccountWithBank(String accountWithBank);

    void deleteById(int id);

    List<PersonalCard> findPersonalCarsByUid(int uid);

    List<PersonalCard> findAllCards();

    PersonalCard findPersonalCardByCardNumber(String cardNumber);
}
