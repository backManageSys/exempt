package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.PayType;
import njurestaurant.njutakeout.parameters.company.PayTypeAddParameters;

import java.util.List;

public interface PayTypeBlService {
    void addPayType(PayTypeAddParameters payTypeAddParameters);

    void updatePayType(int id, PayTypeAddParameters payTypeAddParameters);

    List<PayType> getAllPayType();

    List<PayType> getPayTypeByCodeCategory(String codeCategory);
}
