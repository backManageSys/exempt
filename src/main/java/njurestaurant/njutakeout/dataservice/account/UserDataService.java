package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.User;

import java.util.List;

public interface UserDataService {
    /**
     * find whether the user exists
     *
     * @param username the username
     * @return whether the user exists
     */
    boolean isUserExistent(String username);

    /**
     * save the user
     *
     * @param user the user to be saved
     */
    void saveUser(User user);

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    boolean confirmPassword(String username, String password);

    /**
     * Removes a user.
     *
     * @param id id
     */
    void deleteUser(int id);


    /**
     * get user by username
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    User getUserById(int id);

    List<User> getUserByRole(int role);

    List<User> getAll();
}
