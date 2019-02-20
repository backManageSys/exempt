package njurestaurant.njutakeout.util;

import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.response.food.FoodItem;
import njurestaurant.njutakeout.response.food.SupplierFoodDetailResponse;

public class Converter {

    public static FoodItem fromFoodToFoodItem(Food food) {
        return new FoodItem(food.getId(), food.getName(), food.getUrl(), food.getPrice(), food.isHasChoice(), food.getChoice());
    }

    public static SupplierFoodDetailResponse fromFoodToSupplierFoodDetailResponse(Food food) {
        return new SupplierFoodDetailResponse(food.getId(), food.getName(), food.getUrl(), food.getPrice(), food.isHasChoice(), food.getChoice(), food.getPort().getName());
    }
}
