package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.company.PayType;
import njurestaurant.njutakeout.parameters.company.PayTypeAddParameters;

import java.util.List;

public interface PayPlatformBlService {
    void addCodeCategory(String codeCategory);

    void updateCodeCategory(int id, String codeCategory);

    List<PayPlatform> getPayPlatform();
}
