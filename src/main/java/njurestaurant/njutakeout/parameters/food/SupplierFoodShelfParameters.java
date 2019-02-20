package njurestaurant.njutakeout.parameters.food;

import java.util.List;

public class SupplierFoodShelfParameters {
    private List<Integer> supplierFoodIds;

    public SupplierFoodShelfParameters() {
    }

    public SupplierFoodShelfParameters(List<Integer> supplierFoodIds) {
        this.supplierFoodIds = supplierFoodIds;
    }

    public List<Integer> getSupplierFoodIds() {
        return supplierFoodIds;
    }

    public void setSupplierFoodIds(List<Integer> supplierFoodIds) {
        this.supplierFoodIds = supplierFoodIds;
    }
}
