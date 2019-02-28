package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.parameters.order.QRcodeChangeParameters;

import java.util.List;

public interface ChangeBlService {

    CardChangeOrder addCardChangeOrder(CardChangeParameters CardChangeParameters) throws WrongIdException, WrongInputException, PersonalCardDoesNotExistException, CompanyCardDoesNotExistException;

}
