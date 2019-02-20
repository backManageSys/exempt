package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class SupplierFoodShelfOffResponse extends Response {
    private String info;

    public SupplierFoodShelfOffResponse() {
    }

    public SupplierFoodShelfOffResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
