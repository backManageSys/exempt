package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.ChangeBlService;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.parameters.order.QRcodeChangeParameters;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

//    //内部码账变订单
//    @Override
//    public QRcodeChangeOrder addQRcodeChangeOrder(QRcodeChangeParameters QRcodeChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, AlipayNotExistException {
//        User user = userDataService.getUserById(QRcodeChangeParameters.getOperateId());
//
//        if (user == null) throw new WrongIdException();
//        if (user.getRole() == 1 || user.getRole() == 4) {
//            String loginId = QRcodeChangeParameters.getLoginId();
//            Alipay alipay = alipayDataService.findByLoginId(loginId);
//            if (alipay == null)
//                throw new AlipayNotExistException();
//            double pre_balance = alipay.getWealth();
//            if (alipay.getWealth() - QRcodeChangeParameters.getMoney() < 0)
//                throw new WrongInputException();
//            alipay.setWealth(alipay.getWealth() - QRcodeChangeParameters.getMoney()); //先把钱给它扣掉，如果后面审批不成功，再给他加回来。
//            alipayDataService.saveAlipay(alipay);
//            String cardNumber = QRcodeChangeParameters.getCardNumber();
//            PersonalCard personalCard = personalCardDataService.findPersonalCardByCardNumber(cardNumber);
//            if (personalCard == null)
//                throw new PersonalCardDoesNotExistException();
//            return changeOrderDataService.saveQRcodeChangeOrder(new QRcodeChangeOrder(
//                    loginId, QRcodeChangeParameters.getMoney(), 0, pre_balance, QRcodeChangeParameters.getCardNumber(),
//                    personalCard.getCardBalance(), WithdrewState.WAITING, new Date(), user.getUsername()));
//            //到卡金额会在银行发短信后监控到更新，先写成0
//            //安卓会发支付宝余额，在websocket
//        } else
//            throw new WrongIdException();
//    }

    //内部卡账变订单(个人到公司)
    @Override
    public CardChangeOrder addP2CCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException {
        User user = userDataService.getUserById(CardChangeParameters.getOperateId());
        if (user == null)
            throw new WrongIdException();
        if (user.getRole() == 4) {
//            String cardNumber_out = CardChangeParameters.getCardNumber_out();
            String cardNumber_in = CardChangeParameters.getCardNumber_in();
            //PersonalCard card_out = personalCardDataService.findPersonalCardByCardNumber(cardNumber_out);
            CompanyCard card_in = companyCardDataService.findCompanyCardByCardNumber(cardNumber_in);
            // PersonalCard card_in = personalCardDataService.findPersonalCardByCardNumber(cardNumber_in);
//            if (card_out == null)
//                throw new PersonalCardDoesNotExistException();
            if (card_in == null)
                throw new CompanyCardDoesNotExistException();

//            double card_out_balance = card_out.getCardBalance();
//            if (card_out_balance - CardChangeParameters.getMoney() < 0)
//                throw new WrongInputException();
//            card_out.setCardBalance(card_out_balance - CardChangeParameters.getMoney());
//            personalCardDataService.savePersonalCard(card_out);

            return changeOrderDataService.saveCardChangeOrder(new CardChangeOrder(
                    CardChangeParameters.getCardNumber_out(), CardChangeParameters.getCardNumber_in(),
                    CardChangeParameters.getMoney(), 0.0, card_in.getBalance(), WithdrewState.WAITING, new Date(),
                    user.getUsername()));
        } else
            throw new WrongIdException();
    }

    //内部卡账变订单（公司到个人）
    @Override
    public CardChangeOrder addC2PCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException {
        User user = userDataService.getUserById(CardChangeParameters.getOperateId());
        if (user == null)
            throw new WrongIdException();
        if (user.getRole() == 1) {
            String cardNumber_out = CardChangeParameters.getCardNumber_out();
            String cardNumber_in = CardChangeParameters.getCardNumber_in();
            CompanyCard card_out = companyCardDataService.findCompanyCardByCardNumber(cardNumber_out);
            PersonalCard card_in = personalCardDataService.findPersonalCardByCardNumber(cardNumber_in);
            // PersonalCard card_in = personalCardDataService.findPersonalCardByCardNumber(cardNumber_in);
            if (card_out == null)
                throw new CompanyCardDoesNotExistException();
            if (card_in == null)
                throw new PersonalCardDoesNotExistException();
            double card_out_balance = card_out.getBalance();
            if (card_out_balance - CardChangeParameters.getMoney() < 0)
                throw new WrongInputException();
            card_out.setBalance(card_out_balance - CardChangeParameters.getMoney());
            companyCardDataService.saveCompanyCard(card_out);

            return changeOrderDataService.saveCardChangeOrder(new CardChangeOrder(
                    CardChangeParameters.getCardNumber_out(), CardChangeParameters.getCardNumber_in(),
                    CardChangeParameters.getMoney(), 0.0, card_in.getCardBalance(), WithdrewState.WAITING, new Date(),
                    user.getUsername()));
        } else
            throw new WrongIdException();
    }



    @Override
    public List<QRcodeChangeOrder> getQRcodeChangeHistory(int id) throws WrongIdException{
        User user = userDataService.getUserById(id);
        if (user == null)
            throw new WrongIdException();
        if (user.getRole() == 1) {
            return changeOrderDataService.findAllQrCodeChangeOrder();
        } else if (user.getRole() == 4) {
            return changeOrderDataService.findQrCodeChangeOrderByOperateUsername(user.getUsername());
        }
        else
            throw new WrongIdException();
    }

    @Override
    public List<CardChangeOrder> getCardChangeHistory() {
        return changeOrderDataService.findAllCardChangeOrder();
    }


}
