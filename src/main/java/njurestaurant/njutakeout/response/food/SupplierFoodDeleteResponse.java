package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class SupplierFoodDeleteResponse extends Response {
    private String info;

    public SupplierFoodDeleteResponse() {
    }

    public SupplierFoodDeleteResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
