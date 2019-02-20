package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.response.Response;

public class OrderPrintResponse extends Response {
    private String info;

    public OrderPrintResponse() {
        this.info = "success";
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
