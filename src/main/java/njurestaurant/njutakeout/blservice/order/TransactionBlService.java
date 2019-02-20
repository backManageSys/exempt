package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewDealParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewParameters;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;

import java.util.List;

public interface TransactionBlService {
    GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException, IPRiskControlException, IDRiskControlException, TooLittleMoneyException, OrderNotPayedException;

    void addDevice();

    String findQrCodeByOrderId(String orderid) throws WrongIdException;
    
    PlatformOrder findPlatformOrderByImeiAndState(String imei, OrderState orderState);

    WithdrewOrder addWithdrewOrder(WithdrewParameters withdrewParameters) throws WrongIdException, BlankInputException, WrongInputException;

    List<WithdrewOrder> getAllWaitingWithdrewOrder();

    void grabWithdrewOrderById(int oid, int uid) throws WrongIdException, WrongInputException;

    void dealWithdrewOrder(int id, WithdrewDealParameters withdrewDealParameters) throws WrongIdException, BlankInputException;

    List<WithdrewOrder> getMyWithdrewOrder(int id) throws WrongIdException;

    List<WithdrewOrder> getWithdrewOrder();
}
