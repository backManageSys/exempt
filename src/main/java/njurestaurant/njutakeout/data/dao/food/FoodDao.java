package njurestaurant.njutakeout.data.dao.food;

import njurestaurant.njutakeout.entity.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodDao extends JpaRepository<Food, Integer> {
    List<Food> findFoodsByUserUsername(String username);
}
