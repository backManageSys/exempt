package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.PersonalCardDao;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalCardDataServiceImpl implements PersonalCardDataService {
    private final PersonalCardDao personalCardDao;

    @Autowired
    public PersonalCardDataServiceImpl(PersonalCardDao personalCardDao) {
        this.personalCardDao = personalCardDao;
    }

    /**
     * save the personal bank card
     * @param personalCard the info of the bank card
     * @return
     */
    @Override
    public PersonalCard savePersonalCard(PersonalCard personalCard) {
        return personalCardDao.saveAndFlush(personalCard);
    }

    @Override
    public PersonalCard findPersonalCardById(int id) {
        Optional<PersonalCard> optionalPersonalCard = personalCardDao.findById(id);
        if(optionalPersonalCard.isPresent()) return optionalPersonalCard.get();
        else return null;
    }

    @Override
    public PersonalCard findPersonalCardByAccountWithBank(String accountWithBank) {
        return personalCardDao.findByAccountWithBank(accountWithBank);
    }

    @Override
    public void deleteById(int id) {
        personalCardDao.deleteById(id);
    }

    @Override
    public List<PersonalCard> findAllCards() {
        return personalCardDao.findAll();
    }

    @Override
    public List<PersonalCard> findPersonalCarsByUid(int uid) {
        return personalCardDao.findPersonalCardByUserId(uid);
    }

    @Override
    public PersonalCard findPersonalCardByCardNumber(String cardNumber) {
        return personalCardDao.findPersonalCardByCardNumber(cardNumber);
    }
}
