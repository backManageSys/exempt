package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;

import java.util.List;

public interface MerchantDataService {
    /**
     * save the merchant
     * @param merchant the info of the merchant
     * @return
     */
    Merchant saveMerchant(Merchant merchant);

    /**
     * find the merchant by id
     *
     * @param id merchant id
     * @return
     */
    Merchant findMerchantById(int id);

    /**
     * delete the merchant by id
     * @param id the id
     */
    void deleteMerchantById(int id);

    /**
     * load all merchants
     * @return
     */
    List<Merchant> getAllMerchants();

    List<Merchant> getMerchantsByState(String status);

    List<Merchant> getMerchantsByApplyId(int id);

}
