package njurestaurant.njutakeout.dataservice.app;


import njurestaurant.njutakeout.entity.order.AlipayOrder;

public interface AlipayOrderDataService{
    AlipayOrder saveAlipayOrder(AlipayOrder alipayOrder);

    void deleteAlipayOrderById(int id);

    AlipayOrder getAlipayOrderById(int id);

    AlipayOrder getAlipayOrderByOrderId(String orderId);
}
