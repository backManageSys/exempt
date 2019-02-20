package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class SupplierFoodUpdateResponse extends Response {
    private String info;

    public SupplierFoodUpdateResponse() {
    }

    public SupplierFoodUpdateResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
