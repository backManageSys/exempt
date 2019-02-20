package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class FoodAddResponse extends Response {
    private int foodId;

    public FoodAddResponse() {
    }

    public FoodAddResponse(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
