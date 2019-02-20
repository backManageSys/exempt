package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.company.CompanyCardAddResponse;
import njurestaurant.njutakeout.response.company.CompanyCardLoadResponse;
import org.springframework.stereotype.Service;

@Service
public interface CompanyCardBlService {
    /**
     * add a new bank card of company
     *
     * @param companyCard the information of the bank card
     * @return
     */
    CompanyCardAddResponse addCompanyCard(CompanyCard companyCard) throws IsExistentException;

    /**
     * load all company bank cards
     * @return
     */
    CompanyCardLoadResponse loadAllCompanyCards();

    void delCompanyCardById(int id);

    CompanyCard loadCompanyCardById(int id);



}
