package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.company.PayType;

import java.util.List;

public interface PayTypeDataService {
    PayType savePayType(PayType payType);
    PayType findById(int id);
    List<PayType> findAllPayType();
    List<PayType> findByCodeCategory(String codeCategory);
}
