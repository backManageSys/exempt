package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.PayRateList;
import njurestaurant.njutakeout.entity.company.PayType;

import java.util.List;

public interface PayRateListDataService {
    PayRateList findById(int id);

    PayRateList savePayRateList(PayRateList payRateList);

    PayRateList findByUidAndPayTypeId(int uid, int payTypeId);

    int[] findAllUid();

    List<PayRateList> findByUid(int uid);

}
