package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class SupplierFoodShelfResponse extends Response {
    private String info;

    public SupplierFoodShelfResponse() {
    }

    public SupplierFoodShelfResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
