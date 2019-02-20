package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.user.*;
import org.springframework.stereotype.Service;

@Service
public interface UserBlService {
    /**
     * login
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the login info to  response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    UserLoginResponse login(String username, String password) throws WrongUsernameOrPasswordException, CannotRegisterException,RoleIdentityNotConformException, BlockUpException, WaitingException;

    /**
     * get user openid
     *
     * @param jsCode wechat js code
     * @return whether the operation is success or not
     */
    OpenIdAndSessionKeyResponse getOpenIdAndSessionKey(String jsCode) throws CannotGetOpenIdAndSessionKeyException;

    /**
     * register
     *
     * @param username
     * @param password
     * @return
     */
    UserLoginResponse register(String username, String password);

    /**
     * decrypt data
     *
     * @param encryptData
     * @param sessionKey
     * @param ivCode
     * @return
     */
    PhoneNumberGetResponse decryptData(String encryptData, String sessionKey, String ivCode) throws PhoneNumberGetWrongException;

    /**
     * save avatar to a user
     *
     * @param avatarUrl
     */
    AvatarSaveResponse saveAvatar(String username, String avatarUrl) throws UsernameDoesNotFoundException;

    /**
     * update the user info
     * @param user
     */
    void updateUser(User user);

    /**
     * check the username whether existence
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    Response findUserInfoById(int id);

    Response deleteUserById(int id);

    SuccessResponse appLogin(String username, String password, String imei) throws WrongUsernameOrPasswordException, CannotRegisterException, RoleIdentityNotConformException, WaitingforAuthorizeException;

    SuccessResponse appDevice(int id, String imei ,String status);
}
