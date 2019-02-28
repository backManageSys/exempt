package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.PayTypeBlService;
import njurestaurant.njutakeout.dataservice.account.PayRateListDataService;
import njurestaurant.njutakeout.dataservice.company.PayTypeDataService;
import njurestaurant.njutakeout.entity.account.PayRateList;
import njurestaurant.njutakeout.entity.company.PayType;
import njurestaurant.njutakeout.parameters.company.PayTypeAddParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayTypeBlServiceImpl implements PayTypeBlService {
    private final PayTypeDataService payTypeDataService;
    private final PayRateListDataService payRateListDataService;

    @Autowired
    public PayTypeBlServiceImpl(PayTypeDataService payTypeDataService, PayRateListDataService payRateListDataService) {
        this.payTypeDataService = payTypeDataService;
        this.payRateListDataService = payRateListDataService;
    }


    @Override
    public void addPayType(PayTypeAddParameters payTypeAddParameters) {
        PayType payType =payTypeDataService.savePayType(new PayType(payTypeAddParameters.getCodeCategory(), payTypeAddParameters.getCodeType(), payTypeAddParameters.getStatus()));
        int[] uids = payRateListDataService.findAllUid();
        for(int uid : uids){
            payRateListDataService.savePayRateList(new PayRateList(uid,payType.getId(),0,"停用"));
        }
    }


    @Override
    public void updatePayType(int id, PayTypeAddParameters payTypeAddParameters) {
        PayType payType = payTypeDataService.findById(id);
        payType.setCodeCategory(payTypeAddParameters.getCodeCategory());
        payType.setCodeType(payTypeAddParameters.getCodeType());
        payType.setStatus(payTypeAddParameters.getStatus());
        payTypeDataService.savePayType(payType);
    }

    @Override
    public List<PayType> getAllPayType() {
        return payTypeDataService.findAllPayType();
    }

    @Override
    public List<PayType> getPayTypeByCodeCategory(String codeCategory) {
        return payTypeDataService.findByCodeCategory(codeCategory);
    }


}
