package njurestaurant.njutakeout.data.food;

import njurestaurant.njutakeout.data.dao.food.FoodDao;
import njurestaurant.njutakeout.dataservice.food.FoodDataService;
import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.publicdatas.food.FoodState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodDataServiceImpl implements FoodDataService {
    private final FoodDao foodDao;

    @Autowired
    public FoodDataServiceImpl(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    /**
     * delete food by id
     *
     * @param id
     */
    @Override
    public void deleteFood(int id) {
        foodDao.deleteById(id);
    }

    /**
     * get list of all foods
     *
     * @return
     */
    @Override
    public List<Food> getAllFoods() {
        return foodDao.findAll();
    }

    /**
     * save food
     *
     * @param food
     */
    @Override
    public Food saveFood(Food food) {
        return foodDao.save(food);
    }

    /**
     * get food by id
     *
     * @param foodId
     * @return
     */
    @Override
    public Food getFoodById(int foodId) throws FoodIdDoesNotExistException {
        Optional<Food> optionalFood = foodDao.findById(foodId);
        if (optionalFood.isPresent()) {
            return optionalFood.get();
        } else {
            throw new FoodIdDoesNotExistException();
        }
    }

    /**
     * get foods by supplier
     *
     * @param username
     * @return
     */
    @Override
    public List<Food> getFoodBySupplierUsername(String username) {
        return foodDao.findFoodsByUserUsername(username);
    }

    /**
     * change foods' state
     *
     * @param foodIds
     * @param foodState
     */
    @Override
    public void changeFoodState(List<Integer> foodIds, FoodState foodState) throws FoodIdDoesNotExistException {
        for (int id : foodIds) {
            Optional<Food> optionalFood = foodDao.findById(id);
            if (optionalFood.isPresent()) {
                Food food = optionalFood.get();
                food.setFoodState(foodState);
                foodDao.save(food);
            } else {
                throw new FoodIdDoesNotExistException();
            }
        }
    }
}
