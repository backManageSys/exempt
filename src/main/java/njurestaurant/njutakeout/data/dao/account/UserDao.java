package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserById(int id);
    List<User> findUsersByRole(int role);
}
