package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.company.MerchantApprovalParameters;
import njurestaurant.njutakeout.parameters.user.MerchantUpdateParameters;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MerchantBlService {
    /**
     * add a new merchant
     * @param merchant the info of merchant
     * @return
     */
    MerchantAddResponse addMerchant(Merchant merchant) ;

    /**
     * update the merchant
     *
     * @param merchantUpdateParameters the new info of merchant
     * @return
     */
    MerchantAddResponse updateMerchant(int id, MerchantUpdateParameters merchantUpdateParameters) throws WrongIdException, UsernameIsExistentException;

    /**
     * select the record of merchant by id
     * @param id the merchant id
     * @return
     */
    Merchant selectMerchantById(int id);

    /**
     * 审批商户
     *
     * @param id
     * @return
     */
    Response ApprovalMerchant(int id, MerchantApprovalParameters merchantApprovalParameters);

    /**
     * delete the merchant by id
     *
     * @param id the id of merchant
     */
    void delMerchantById(int id);

    List<Merchant> findAllMerchants();

    List<Merchant> findMerchantsBySuperior(int id) throws WrongIdException;

    List<Merchant> findMerchantsByState(String status);

    Merchant findMerchantByMerchantName(String merchantName);
}
