package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.data.dao.company.CompanyCardDao;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyCardDataServiceImpl implements CompanyCardDataService {
    private CompanyCardDao companyCardDao;
    private UserDao userDao;

    @Autowired
    public CompanyCardDataServiceImpl(CompanyCardDao companyCardDao,UserDao userDao) {
        this.companyCardDao = companyCardDao;
        this.userDao=userDao;
    }

    /**
     * save the new bank card of the company
     *
     * @param companyCard the information of bank card
     * @return
     */
    @Override
    public CompanyCard saveCompanyCard(CompanyCard companyCard) {
        return companyCardDao.save(companyCard);
    }

    /**
     * load all bank cards
     * @return
     */
    @Override
    public List<CompanyCard> findAllCompanyCards() {

        List<CompanyCard> list=companyCardDao.findAll();
        for (CompanyCard companyCard:list){
            companyCard.setOperateName(userDao.findUserById(companyCard.getOperateId()).getUsername());
        }

        return list;
    }

    /**
     * find the card whether exist
     *
     * @param cardNumber the card number
     * @return
     */
    @Override
    public boolean isExistentCard(String cardNumber) {
        return companyCardDao.findCompanyCardByCardNumber(cardNumber) != null;
    }

    @Override
    public void deleteCompanyCardById(int id) {
        companyCardDao.deleteById(id);
    }

    @Override
    public CompanyCard findCompanyById(int id) {
        Optional<CompanyCard> optionalCompanyCard = companyCardDao.findById(id);
        if(optionalCompanyCard.isPresent()) {
            return optionalCompanyCard.get();
        } else return null;
    }

    @Override
    public CompanyCard findCompanyCardByCardNumber(String cardNumber) {
        return companyCardDao.findCompanyCardByCardNumber(cardNumber);
    }

    @Override
    public List<CompanyCard> findCompanyCardByOperateId(int uid) {
        return companyCardDao.findByOperateId(uid);
    }
}
