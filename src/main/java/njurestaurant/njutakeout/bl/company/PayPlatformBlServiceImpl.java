package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.PayPlatformBlService;
import njurestaurant.njutakeout.dataservice.company.PayPlatformDataService;
import njurestaurant.njutakeout.dataservice.company.PayTypeDataService;
import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.company.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayPlatformBlServiceImpl implements PayPlatformBlService {
    private final PayPlatformDataService payPlatformDataService;
    private final PayTypeDataService payTypeDataService;
    @Autowired
    public PayPlatformBlServiceImpl(PayPlatformDataService payPlatformDataService, PayTypeDataService payTypeDataService) {
        this.payPlatformDataService = payPlatformDataService;
        this.payTypeDataService = payTypeDataService;
    }

    @Override
    public void addCodeCategory(String codeCategory) {
        payPlatformDataService.savePayPlatform(new PayPlatform(codeCategory));
    }

    @Override
    public void updateCodeCategory(int id, String codeCategory) {
        PayPlatform payPlatform = payPlatformDataService.findById(id);
        payPlatform.setCodeCategory(codeCategory);
        payPlatformDataService.savePayPlatform(payPlatform);
        for(PayType payType : payTypeDataService.findByCodeCategory(codeCategory)){
            payType.setCodeCategory(codeCategory);
            payTypeDataService.savePayType(payType);
        }
    }

    @Override
    public List<PayPlatform> getPayPlatform() {
        return payPlatformDataService.findAllPayPlatform();
    }
}
