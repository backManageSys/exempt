package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.ChangeBlService;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ChangeBlServiceImpl implements ChangeBlService {
    private final UserDataService userDataService;
    private final ChangeOrderDataService changeOrderDataService;
    private final AlipayDataService alipayDataService;
    private final PersonalCardDataService personalCardDataService;
    private final CompanyCardDataService companyCardDataService;

    @Autowired
    public ChangeBlServiceImpl(UserDataService userDataService, ChangeOrderDataService changeOrderDataService, AlipayDataService alipayDataService, PersonalCardDataService personalCardDataService, CompanyCardDataService companyCardDataService) {
        this.userDataService = userDataService;
        this.changeOrderDataService = changeOrderDataService;
        this.alipayDataService = alipayDataService;
        this.personalCardDataService = personalCardDataService;
        this.companyCardDataService = companyCardDataService;
    }

    //内部卡账变订单
    @Override
    public CardChangeOrder addCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException {
        User user = userDataService.getUserById(CardChangeParameters.getOperateId());
        String cardNumber_out = CardChangeParameters.getCardNumber_out();
        String cardNumber_in = CardChangeParameters.getCardNumber_in();
        if (user == null)
            throw new WrongIdException();
        if (user.getRole() == 4) {
            if (CardChangeParameters.getType().equals("入款")) {
                Alipay alipay = alipayDataService.findByLoginId(CardChangeParameters.getCardNumber_out());
                double cardBalance = alipay.getCardBalance();
                if (new BigDecimal(cardBalance).compareTo(new BigDecimal(CardChangeParameters.getMoney())) < 0)
                    throw new WrongInputException();
                alipay.setCardBalance(cardBalance - CardChangeParameters.getMoney());
                alipayDataService.saveAlipay(alipay);
                return changeOrderDataService.saveCardChangeOrder(new CardChangeOrder(
                        alipay.getCardNumber(), cardNumber_in,
                        CardChangeParameters.getMoney(), 0, alipay.getCardBalance(),
                        companyCardDataService.findCompanyCardByCardNumber(cardNumber_in).getBalance(), WithdrewState.WAITING, new Date(),
                        null, user.getUsername(), CardChangeParameters.getType(), null));
            } else if (CardChangeParameters.getType().equals("储备")) {
                CompanyCard card_out = companyCardDataService.findCompanyCardByCardNumber(cardNumber_out);
                CompanyCard card_in = companyCardDataService.findCompanyCardByCardNumber(cardNumber_in);
                if (card_out == null || card_in == null)
                    throw new CompanyCardDoesNotExistException();
                if (new BigDecimal(card_out.getBalance()).compareTo(new BigDecimal(CardChangeParameters.getMoney())) < 0)
                    throw new WrongInputException();
                card_out.setBalance(card_out.getBalance() - CardChangeParameters.getMoney());
                companyCardDataService.saveCompanyCard(card_out);
                return changeOrderDataService.saveCardChangeOrder(new CardChangeOrder(
                        cardNumber_out, cardNumber_in,
                        CardChangeParameters.getMoney(), 0, card_out.getBalance(), card_in.getBalance(), WithdrewState.WAITING, new Date(),
                        null, user.getUsername(), CardChangeParameters.getType(), null));
            } else
                return null;
        } else
            throw new WrongIdException();
    }


}
