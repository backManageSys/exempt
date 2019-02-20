package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.response.Response;

public class OrderAcceptResponse extends Response {
    private String info;

    public OrderAcceptResponse() {
        this.info = "success";
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
