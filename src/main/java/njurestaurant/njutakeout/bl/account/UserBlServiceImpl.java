package njurestaurant.njutakeout.bl.account;

import net.sf.json.JSONObject;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.blservice.company.PostAndPermissionBlService;
import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.*;
import njurestaurant.njutakeout.security.jwt.JwtService;
import njurestaurant.njutakeout.security.jwt.JwtUser;
import njurestaurant.njutakeout.security.jwt.JwtUserDetailsService;
import njurestaurant.njutakeout.util.AESDecodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBlServiceImpl implements UserBlService {

    private final static long EXPIRATION = 604800;
    private final static String USER_DEFAULT_PASSWORD = "user";
//    private final static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJFUF/b5yuXRNV20Cvgzh1jmhQ5SPQ5C4DwfQfANnTv62HU5t0nR58/eJRc8bwfVke1lygpEy+SD/MTwPf0m3UiSyYBPyx5m+YRmI+4Qhla2lCzduLfmVvCJuMxdnWor66WCRtcdrlRWE4tpDcHTOMCrGFFM2p/c2Pgd6Ipc6S1LAgMBAAECgYAopHPvRfxQOSnLgsZukzqOyij70FPy4REEv1kZYTVPyN8wKWvw4RsLLv0Aeo4yiq+8zHKsXIEI70CJcwZi/bN0Ys9MMl7Yy+ALl0/VMfUcAfoOC6PC8LiFojevN8iC0BnqlOR5rnB9bqJOYkxNnkOOE3KdP8YDlEeQoeEA5TTXAQJBAOW3lfls/iffzBX0bt9Zu/u5AEfnrmBiVbMegxrn3O4HYoLGYiHAau1sWCOPjL7wajEP2Ch3s1g7Qw9KPJsxX5sCQQCh9MPDYo0oEefP+Fhkiw3ywQa8rWlVvxqFStFeiWCl+ACj4whxOkn3PTL70VghVwbuPBaNfVwdqHpCx2LKBLwRAkBBB/TsFJ/qt991w6nzjtq5y0i6Emt6G7x5JcUlw8f2lp6buP+k4G0k44wcHRJSJ4tYckzWP/TEoJo+ZNy9bn/LAkBi8e3T5dbFX0MXvOsL6iSIZcNe7DcJauqh+pa1Qgro+v4xIhhbTLg5s4r9a+WC4O9cBxvkL21itTaUq8nAEGpxAkEAkSTVfttBdtoJVYPklkpCrWxcXQ+F5fDUm4Hyqd6wcxTsx5qCvsHAUe9hInTvj/5Vqjq+QI65WSPeR+Shu/olIA==";
//    private final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRVBf2+crl0TVdtAr4M4dY5oUOUj0OQuA8H0HwDZ07+th1ObdJ0efP3iUXPG8H1ZHtZcoKRMvkg/zE8D39Jt1IksmAT8seZvmEZiPuEIZWtpQs3bi35lbwibjMXZ1qK+ulgkbXHa5UVhOLaQ3B0zjAqxhRTNqf3Nj4HeiKXOktSwIDAQAB";

    private final UserDataService userDataService;
    private final AgentDataService agentDataService;
    private final MerchantDataService merchantDataService;
    private final StaffDataService staffDataService;
    private final SupplierDataService supplierDataService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtService jwtService;
    private final PostAndPermissionBlService postAndPermissionBlService;
    private final DeviceDataService deviceDataService;

    @Value(value = "${wechat.url}")
    private String wechatUrl;

    @Value(value = "${wechat.id}")
    private String appId;

    @Value(value = "${wechat.secret}")
    private String appSecret;

    @Value(value = "${spring.encrypt.privateKey}")
    private String privateKey;

    @Value(value = "${spring.encrypt.publicKey}")
    private String publicKey;

    @Autowired
    public UserBlServiceImpl(UserDataService userDataService, AgentDataService agentDataService, MerchantDataService merchantDataService, StaffDataService staffDataService, SupplierDataService supplierDataService, JwtUserDetailsService jwtUserDetailsService, JwtService jwtService, PostAndPermissionBlService postAndPermissionBlService, DeviceDataService deviceDataService) {
        this.userDataService = userDataService;
        this.agentDataService = agentDataService;
        this.merchantDataService = merchantDataService;
        this.staffDataService = staffDataService;
        this.supplierDataService = supplierDataService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtService = jwtService;
        this.postAndPermissionBlService = postAndPermissionBlService;
        this.deviceDataService = deviceDataService;
    }

    /**
     * login
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the login info to response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    @Override
    public UserLoginResponse login(String username, String password) throws WrongUsernameOrPasswordException, CannotRegisterException, BlockUpException, WaitingException {
        if (username.length() == 0) {
            throw new CannotRegisterException();
        }
//        if (password.equals(USER_DEFAULT_PASSWORD)) {
//            if (!userDataService.isUserExistent(username)) {
//                userDataService.saveUser(new User(username, password, 1, 0));
////                userDataService.saveUser(new User("", username, password, Role.USER, new ArrayList<>(), new ArrayList<>()));
//            }
//            JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
//            String token = jwtService.generateToken(jwtUser, EXPIRATION);
//            return new UserLoginResponse(token);
//        } else {
        if (userDataService.confirmPassword(username, password)) {
            User user = userDataService.getUserByUsername(username);
//                if(StringUtils.isBlank(user.getOriginPassword())) {
//                    user.setOriginPassword(RSAUtils.encryptedDataOnJava(password, publicKey));
//                    userDataService.saveUser(user);
//                }
            JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
            String token = jwtService.generateToken(jwtUser, EXPIRATION);
            String post = null;
            switch (user.getRole()) {
                case 1:
                    Staff staff = staffDataService.findStaffById(user.getTableId());
                    if (staff.getStatus().equals("停用"))
                        throw new BlockUpException();
                    post = staffDataService.findStaffById(user.getTableId()).getPost();
                    break;
                case 2:
                    Agent agent = agentDataService.findAgentById(user.getTableId());
                    if (agent.getStatus().equals("停用"))
                        throw new BlockUpException();
                    post = "代理商";
                    break;
                case 3:
                    Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                    if (merchant.getStatus().equals("申请启用"))
                        throw new WaitingException();
                    if (merchant.getStatus().equals("停用"))
                        throw new BlockUpException();
                    post = "商户";
                    break;
                case 4:
                    Supplier supplier = supplierDataService.findSupplierById(user.getTableId());
                    if (supplier.getStatus().equals("停用"))
                        throw new BlockUpException();
                    post = "供码用户";
                    break;
            }

            return new UserLoginResponse(token, user.getRole(), user.getId(), postAndPermissionBlService.getPostAndPermissionsByPost(post) == null ? new ArrayList<>() : postAndPermissionBlService.getPostAndPermissionsByPost(post).getPermission());
        } else {
            throw new WrongUsernameOrPasswordException();
        }
        //       }
    }

    @Override
    public SuccessResponse appLogin(String username, String password, String imei) throws WrongUsernameOrPasswordException, CannotRegisterException, RoleIdentityNotConformException, WaitingforAuthorizeException{
        if (username.length() == 0) {
            throw new CannotRegisterException();
        }
        if (userDataService.confirmPassword(username, password)) {
            User user = userDataService.getUserByUsername(username);
            if (user.getRole() == 4) {
                JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
                String token = jwtService.generateToken(jwtUser, EXPIRATION);
                Supplier supplier = supplierDataService.findSupplierById(user.getTableId());

                List<Device> devices = deviceDataService.findDevicesByImei(imei);
                Device device1 = deviceDataService.findBySupplierIdAndImei(supplier.getId(),imei);
                if (devices == null || devices.size() == 0 || device1 == null) {
                    Device device = new Device(imei, supplier, "停用");
                    device.setOnline(0);
                    deviceDataService.saveDevice(device);
                    throw new WaitingforAuthorizeException();
                }
                if ( device1.getStatus().equals("停用"))
                    throw new WaitingforAuthorizeException();

//                if (device == null || device.getSupplier().getId() != supplier.getId()) {
//                    device = new Device(imei, supplier, "停用");
//                    device.setOnline(0);
//                    deviceDataService.saveDevice(device);
//                    throw new WaitingforAuthorizeException();
//                }

                List<Device> list = deviceDataService.findDevicesBySupplierId(supplier.getId());
                List<Device> list1 = deviceDataService.findAll();
                if (list != null && list.size() >= 1)
                    list1.removeAll(list);
                if (list1 != null && list1.size() >= 1)
                    for (Device a : list)
                        for (Device b : list1)
                            if (a.getImei().equals(b.getImei()))
                                deviceDataService.deleteById(b.getId()); //去除与当前登录的供码用户有相同设备号的记录，保证设备号的唯一性
                return new SuccessResponse("登陆成功");
            } else {
                throw new RoleIdentityNotConformException();
            }

        } else {
            throw new WrongUsernameOrPasswordException();
        }
    }

    @Override
    public SuccessResponse appDevice(int id, String imei ,String status) {
        Device device = deviceDataService.findBySupplierIdAndImei(id,imei);
        device.setStatus(status);
        deviceDataService.saveDevice(device);
        return  new SuccessResponse("修改成功");
    }

    /**
     * get user openid
     *
     * @param jsCode wechat js code
     * @return whether the operation is success or not
     */
    @Override
    public OpenIdAndSessionKeyResponse getOpenIdAndSessionKey(String jsCode) throws CannotGetOpenIdAndSessionKeyException {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> response = client.exchange(wechatUrl + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=authorization_code", HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return new OpenIdAndSessionKeyResponse((String) JSONObject.fromObject(response.getBody()).get("openid"), (String) JSONObject.fromObject(response.getBody()).get("session_key"));
        } else {
            throw new CannotGetOpenIdAndSessionKeyException();
        }
    }

    /**
     * register
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserLoginResponse register(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDataService.saveUser(new User(username, encoder.encode(password), 0, 0));
//        userDataService.saveUser(new User("", username, encoder.encode(password), Role.RESTAURANT, new ArrayList<>(), new ArrayList<>()));
        JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
        String token = jwtService.generateToken(jwtUser, EXPIRATION);
        return new UserLoginResponse(token);
    }

    /**
     * decrypt data
     *
     * @param encryptData
     * @param sessionKey
     * @param ivCode
     * @return
     */
    @Override
    public PhoneNumberGetResponse decryptData(String encryptData, String sessionKey, String ivCode) throws PhoneNumberGetWrongException {
        try {
            return new PhoneNumberGetResponse(AESDecodeUtils.decrypt(sessionKey, ivCode, encryptData));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PhoneNumberGetWrongException();
        }
    }

    /**
     * save avatar to a user
     *
     * @param username
     * @param avatarUrl
     */
    @Override
    public AvatarSaveResponse saveAvatar(String username, String avatarUrl) throws UsernameDoesNotFoundException {
        User user = userDataService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameDoesNotFoundException();
        } else {
            user.setAvatarUrl(avatarUrl);
            userDataService.saveUser(user);
            return new AvatarSaveResponse();
        }
    }


    @Override
    public void updateUser(User user) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDataService.saveUser(user);
    }

    /**
     * check the username whether existent
     *
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        return userDataService.isUserExistent(username);
    }

    /**
     * 根据用户id查找用户的信息
     *
     * @param id
     * @return
     */
    @Override
    public Response findUserInfoById(int id) {
        User user = userDataService.getUserById(id);
        if (user == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            if (user.getTableId() == 0) {
                return new WrongResponse(10130, "Wrong id.");
            }
//            if(StringUtils.isNotBlank(user.getOriginPassword())) {
//                user.setOriginPassword(RSAUtils.decryptDataOnJava(user.getOriginPassword(), privateKey));
//            }
            if (user.getRole() == 1) {
                Staff staff = staffDataService.findStaffById(user.getTableId());
                List<PersonalCard> cardList = staff.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
                userInfoResponse.setInfo(staff);
                userInfoResponse.setPost(staff.getPost());
                if (postAndPermissionBlService.getPostAndPermissionsByPost(staff.getPost()) == null)
                    userInfoResponse.setPermission(new ArrayList<>());
                else
                    userInfoResponse.setPermission(postAndPermissionBlService.getPostAndPermissionsByPost(staff.getPost()).getPermission());
            } else if (user.getRole() == 2) {
                Agent agent = agentDataService.findAgentById(user.getTableId());
                List<PersonalCard> cardList = agent.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
                double flow = AgentDailyFlow.flow.containsKey(agent.getId()) ? AgentDailyFlow.flow.get(agent.getId()) : 0;
                double commission = AgentDailyFlow.commission.containsKey(agent.getId()) ? AgentDailyFlow.commission.get(agent.getId()) : 0;
                AgentInfoResponse agentInfoResponse = new AgentInfoResponse(agent.getId(), agent.getUser().getId(), agent.getAgentName(), agent.getStatus(), agent.getAlipay(), agent.getWechat(), agent.getBalance(), agent.getUser(), flow, commission);
                userInfoResponse.setInfo(agent);
                userInfoResponse.setPost("代理商");
                if (postAndPermissionBlService.getPostAndPermissionsByPost("代理商") == null)
                    userInfoResponse.setPermission(new ArrayList<>());
                else
                    userInfoResponse.setPermission(postAndPermissionBlService.getPostAndPermissionsByPost("代理商").getPermission());
            } else if (user.getRole() == 3) {
                Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                List<PersonalCard> personalCardList = merchant.getUser().getCards();
                if (personalCardList.size() > 0)
                    personalCardList.stream().peek(p -> p.setUser(null)).collect(Collectors.toList());
                userInfoResponse.setInfo(merchant);
                userInfoResponse.setPost("商户");
                if (postAndPermissionBlService.getPostAndPermissionsByPost("商户") == null)
                    userInfoResponse.setPermission(new ArrayList<>());
                else
                    userInfoResponse.setPermission(postAndPermissionBlService.getPostAndPermissionsByPost("商户").getPermission());
            } else if (user.getRole() == 4) {
                Supplier supplier = supplierDataService.findSupplierById(user.getTableId());
                List<PersonalCard> personalCardList = supplier.getUser().getCards();
                List<Device> deviceList = supplier.getDevices();
                if (personalCardList.size() > 0)
                    personalCardList.stream().peek(p -> p.setUser(null)).collect(Collectors.toList());
                if (deviceList.size() > 0)
                    deviceList.stream().peek(p -> p.setSupplier(null)).collect(Collectors.toList());
                userInfoResponse.setInfo(supplier);
                userInfoResponse.setPost("供码用户");
                if (postAndPermissionBlService.getPostAndPermissionsByPost("供码用户") == null)
                    userInfoResponse.setPermission(new ArrayList<>());
                else
                    userInfoResponse.setPermission(postAndPermissionBlService.getPostAndPermissionsByPost("供码用户").getPermission());
            } else {
                return new WrongResponse(10150, "Wrong role.");
            }
            return userInfoResponse;
        }
    }

    @Override
    public Response deleteUserById(int id) {
        User user = userDataService.getUserById(id);
        if (user == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            if (user.getTableId() == 0) {
                return new WrongResponse(10130, "Wrong id.");
            }
            UserDeleteResponse userDeleteResponse = new UserDeleteResponse(user.getId(), user.getTableId());
            if (user.getRole() == 1) {
                userDeleteResponse.setTableId(user.getTableId());
                staffDataService.deleteStaffById(user.getTableId());
            } else if (user.getRole() == 2) {
                agentDataService.deleteAgentById(user.getTableId());
            } else if (user.getRole() == 3) {
                merchantDataService.deleteMerchantById(user.getTableId());
            } else if (user.getRole() == 4) {
                supplierDataService.deleteSupplierById(user.getTableId());
            } else {
                return new WrongResponse(10150, "Wrong role.");
            }
            return userDeleteResponse;
        }
    }
}
