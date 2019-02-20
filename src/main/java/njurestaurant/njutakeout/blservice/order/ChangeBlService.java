package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.parameters.order.QRcodeChangeParameters;

import java.util.List;

public interface ChangeBlService {
//    GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException, IPRiskControlException, IDRiskControlException, TooLittleMoneyException;
//
//    void addDevice();
//
//    String findQrCodeByOrderId(String orderid) throws WrongIdException;
//
//    PlatformOrder findPlatformOrderByImeiAndState(String imei, OrderState orderState);

   // QRcodeChangeOrder addQRcodeChangeOrder(QRcodeChangeParameters QRcodeChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, AlipayNotExistException;

    CardChangeOrder addP2CCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException;

    CardChangeOrder addC2PCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException;


    //    List<QRcodeChangeOrder> getMyQRcodeChangeHistory(int id) throws WrongIdException;
////    List<CardChangeOrder> getMyP2CCardChangeHistory(int id) throws WrongIdException;
////    List<CardChangeOrder> getMyC2PCardChangeHistory(int id) throws WrongIdException;
//    List<QRcodeChangeOrder> getQRcodeChangeHistoryById(int id) throws WrongIdException;

    List<QRcodeChangeOrder> getQRcodeChangeHistory(int id) throws WrongIdException;

  //  List<QRcodeChangeOrder> updateQRcodeChangeHistory(int id)throws WrongIdException;


    List<CardChangeOrder> getCardChangeHistory();

//
//    List<CardChangeOrder> getC2PCardChangeHistory();
//    List<CardChangeOrder> getP2CCardChangeHistory();

    //    List<WithdrewOrder> getAllWaitingWithdrewOrder();
//
//    void grabWithdrewOrderById(int oid, int uid) throws WrongIdException, WrongInputException;
//
//    void dealWithdrewOrder(int id, WithdrewDealParameters withdrewDealParameters) throws WrongIdException, BlankInputException;
//
//    List<WithdrewOrder> getMyWithdrewOrder(int id) throws WrongIdException;
}
