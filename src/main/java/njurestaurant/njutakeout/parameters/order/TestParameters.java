package njurestaurant.njutakeout.parameters.order;

import njurestaurant.njutakeout.publicdatas.order.OrderState;

import java.util.Date;

public class TestParameters {
    private String imei;
    private OrderState orderState;
    private int payTypeId;
    private Date startDate;
    private Date endDate;

    public TestParameters(String imei, OrderState orderState, int payTypeId, Date startDate, Date endDate) {
        this.imei = imei;
        this.orderState = orderState;
        this.payTypeId = payTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
