package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.parameters.order.OrderMsgParameters;
import njurestaurant.njutakeout.response.order.AlipayOrderAddResponse;

public interface AlipayOrderBlService {
    AlipayOrderAddResponse saveAlipayOrder(OrderMsgParameters orderMsgParameters);
}
