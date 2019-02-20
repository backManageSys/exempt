package njurestaurant.njutakeout.data.dao.order;

import njurestaurant.njutakeout.entity.order.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Integer> {
    int countAllByFoodIdsContains(int foodId);
}
