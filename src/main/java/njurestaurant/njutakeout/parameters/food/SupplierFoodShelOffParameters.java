package njurestaurant.njutakeout.parameters.food;

import java.util.List;

public class SupplierFoodShelOffParameters {
    private List<Integer> supplierFoodIds;

    public SupplierFoodShelOffParameters() {
    }

    public SupplierFoodShelOffParameters(List<Integer> supplierFoodIds) {
        this.supplierFoodIds = supplierFoodIds;
    }

    public List<Integer> getSupplierFoodIds() {
        return supplierFoodIds;
    }

    public void setSupplierFoodIds(List<Integer> supplierFoodIds) {
        this.supplierFoodIds = supplierFoodIds;
    }
}
