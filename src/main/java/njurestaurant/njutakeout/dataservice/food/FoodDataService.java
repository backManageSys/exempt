package njurestaurant.njutakeout.dataservice.food;

import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.publicdatas.food.FoodState;

import java.util.List;

public interface FoodDataService {
    /**
     * delete food by id
     *
     * @param id
     */
    void deleteFood(int id);

    /**
     * get list of all foods
     *
     * @return
     */
    List<Food> getAllFoods();

    /**
     * save food
     *
     * @param food
     */
    Food saveFood(Food food);

    /**
     * get food by id
     *
     * @param foodId
     * @return
     */
    Food getFoodById(int foodId) throws FoodIdDoesNotExistException;

    /**
     * get foods by supplier
     *
     * @param username
     * @return
     */
    List<Food> getFoodBySupplierUsername(String username);

    /**
     * change foods' state
     *
     * @param foodIds
     * @param foodState
     */
    void changeFoodState(List<Integer> foodIds, FoodState foodState) throws FoodIdDoesNotExistException;

}
