package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.company.PayType;

import java.util.List;

public interface PayPlatformDataService {

    PayPlatform savePayPlatform(PayPlatform payPlatform);
    PayPlatform findById(int id);
    List<PayPlatform> findAllPayPlatform();
}
