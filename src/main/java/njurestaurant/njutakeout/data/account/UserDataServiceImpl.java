package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.util.RSAUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UserDataServiceImpl implements UserDataService {
    private final UserDao userDao;

    @Autowired
    public UserDataServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Value(value = "${spring.encrypt.privateKey}")
    private String privateKey;

    @Value(value = "${spring.encrypt.publicKey}")
    private String publicKey;


    /**
     * find whether the user exists
     *
     * @param username the username
     * @return whether the user exists
     */
    @Override
    public boolean isUserExistent(String username) {
        return userDao.findUserByUsername(username) != null;
    }

    /**
     * save the user
     *
     * @param user the user to be saved
     */
    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    @Override
    public boolean confirmPassword(String username, String password) {
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
            else {
//                if(StringUtils.isNotBlank(user.getOriginPassword())) {
//                    String psd = RSAUtils.decryptDataOnJava(user.getOriginPassword(), privateKey);
//                    if(psd.equals(password))    return true;
//                }
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Removes a user. No exception is thrown if username doesn't exist.
     *
     * @param id id
     */
    @Override
    public void deleteUser(int id) {
        userDao.deleteById(id);
    }

    /**
     * get user by username
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public User getUserById(int id) {
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public List<User> getUserByRole(int role) {
        return userDao.findUsersByRole(role);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }
}
