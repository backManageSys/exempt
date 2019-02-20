package njurestaurant.njutakeout.bl.food;

import njurestaurant.njutakeout.blservice.order.AlipayOrderBlService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.parameters.order.OrderMsgParameters;
import njurestaurant.njutakeout.response.order.AlipayOrderAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayOrderBlServiceImpl implements AlipayOrderBlService {
    private final AlipayOrderDataService alipayOrderDataService;

    @Autowired
    public AlipayOrderBlServiceImpl(AlipayOrderDataService alipayOrderDataService) {
        this.alipayOrderDataService = alipayOrderDataService;
    }

    @Override
    public AlipayOrderAddResponse saveAlipayOrder(OrderMsgParameters orderMsgParameters) {
        AlipayOrder alipayOrder = alipayOrderDataService.saveAlipayOrder(new AlipayOrder(orderMsgParameters.getImei(), orderMsgParameters.getOrderId(), orderMsgParameters.getMoney(), orderMsgParameters.getMemo(), orderMsgParameters.getTime()));
        AlipayOrderAddResponse alipayOrderAddResponse = new AlipayOrderAddResponse(orderMsgParameters.getCmd(), "success", "", alipayOrder.getOrderId() ,alipayOrder.getImei());
        if(alipayOrder.getId() == 0) {
            alipayOrderAddResponse.setStatus("failed");
            alipayOrderAddResponse.setMsg("Save the order failed!");
        }
        return alipayOrderAddResponse;
    }
}
