package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.publicdatas.order.OrderState;


public class OneOrderResponse {
    private String time;
    private OrderState orderState;

    public OneOrderResponse(String time, OrderState orderState) {
        this.time = time;
        this.orderState = orderState;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
