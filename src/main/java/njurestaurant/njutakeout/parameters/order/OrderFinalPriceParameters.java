package njurestaurant.njutakeout.parameters.order;

import java.util.List;

public class OrderFinalPriceParameters {
    private double total;
    private List<OrderSubmitFood> foods;

    public OrderFinalPriceParameters() {
    }

    public OrderFinalPriceParameters(double total, List<OrderSubmitFood> foods) {
        this.total = total;
        this.foods = foods;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<OrderSubmitFood> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderSubmitFood> foods) {
        this.foods = foods;
    }
}
