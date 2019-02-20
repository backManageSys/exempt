package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class FoodLoadByPortResponse extends Response {
    private List<FoodItem> foodList;
    public FoodLoadByPortResponse() {
    }
    public FoodLoadByPortResponse(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    public List<FoodItem> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodItem> foodList) {
        this.foodList = foodList;
    }
}
