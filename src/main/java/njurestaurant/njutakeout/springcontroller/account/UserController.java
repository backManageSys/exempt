package njurestaurant.njutakeout.springcontroller.account;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.account.*;
import njurestaurant.njutakeout.blservice.event.LogBlService;
import njurestaurant.njutakeout.data.dao.account.AgentDao;
import njurestaurant.njutakeout.data.dao.account.StaffDao;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.dataservice.account.StaffDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.company.StaffAddParameters;
import njurestaurant.njutakeout.parameters.user.*;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    private final UserBlService userBlService;
    private final StaffBlService staffBlService;
    private final AgentBlService agentBlService;
    private final MerchantBlService merchantBlService;
    private final SupplierBlService supplierBlService;
    private final LogBlService logBlService;
    private final UserDataService userDataService;
    private final AgentDataService agentDataService;
    private final StaffDataService staffDataService;
    private final AgentDao agentDao;
    private final StaffDao staffDao;
    private final UserDao userDao;


    @Autowired
    public UserController(UserBlService userBlService, StaffBlService staffBlService, AgentBlService agentBlService, MerchantBlService merchantBlService, SupplierBlService supplierBlService, LogBlService logBlService, UserDataService userDataService, AgentDataService agentDataService, StaffDataService staffDataService, AgentDao agentDao, StaffDao staffDao, UserDao userDao) {
        this.userBlService = userBlService;
        this.staffBlService = staffBlService;
        this.agentBlService = agentBlService;
        this.merchantBlService = merchantBlService;
        this.supplierBlService = supplierBlService;
        this.logBlService = logBlService;
        this.userDataService = userDataService;
        this.agentDataService = agentDataService;
        this.staffDataService = staffDataService;
        this.agentDao = agentDao;
        this.staffDao = staffDao;
        this.userDao = userDao;
    }

//<<<<<<< HEAD
//=======
//    @Value(value = "${spring.encrypt.publicKey}")
//    private String publicKey;
//>>>>>>> 38cf3b12fe269d63feb7dae83d236134b1729aa1

    @ApiOperation(value = "用户登录", notes = "验证用户登录并返回token")
    @RequestMapping(value = "account/login", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> login(@RequestBody UserLoginParameters userLoginParameters) throws RoleIdentityNotConformException {
        try {
            UserLoginResponse userLoginResponse = userBlService.login(userLoginParameters.getUsername(), userLoginParameters.getPassword());
            return new ResponseEntity<>(new JSONResponse(200, userLoginResponse), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(401, e.getResponse()), HttpStatus.UNAUTHORIZED);
        } catch (CannotRegisterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(503, e.getResponse()), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (BlockUpException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(1010, e.getResponse()), HttpStatus.OK);
        } catch (WaitingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(1011, e.getResponse()), HttpStatus.OK);
        }
    }

//    @ApiOperation(value = "用户注册", notes = "管理员注册用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
//    })
//    @RequestMapping(value = "account/register", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> register(
//            @RequestParam("username") String username, @RequestParam("password") String password) {
//        UserLoginResponse userLoginResponse = userBlService.register(username, password);
//        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "获得微信openid", notes = "获得微信openid")
//    @RequestMapping(method = RequestMethod.POST, path = "/getOpenId", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Response> getOpenId(@RequestParam("jsCode") String jsCode) {
//        try {
//            return new ResponseEntity<>(userBlService.getOpenIdAndSessionKey(jsCode), HttpStatus.OK);
//        } catch (CannotGetOpenIdAndSessionKeyException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
//
//    @ApiOperation(value = "获得手机号码", notes = "获得手机号码")
//    @RequestMapping(method = RequestMethod.POST, path = "/getPhoneNumber", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Response> getPhoneNumber(@RequestBody PhoneNumberGetParameters phoneNumberGetParameters) {
//        try {
//            return new ResponseEntity<>(userBlService.decryptData(phoneNumberGetParameters.getEncryptData(), phoneNumberGetParameters.getSessionKey(), phoneNumberGetParameters.getIvCode()), HttpStatus.OK);
//        } catch (PhoneNumberGetWrongException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
//
//    @ApiOperation(value = "保存用户头像", notes = "提交订单工程中保存用户头像")
//    @RequestMapping(method = RequestMethod.POST, path = "/saveAvatar", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Response> saveAvatar(@RequestBody AvatarSaveParameters avatarSaveParameters) {
//        try {
//            return new ResponseEntity<>(userBlService.saveAvatar(UserInfoUtil.getUsername(), avatarSaveParameters.getAvatarUrl()), HttpStatus.OK);
//        } catch (UsernameDoesNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
    @SystemControllerLog(descrption = "管理员新增管理员",actionType = "1")
    @ApiOperation(value = "新增管理员", notes = "管理员新增管理员")
    @RequestMapping(value = "admin/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addStaff(@RequestBody StaffAddParameters staffAddParameters) throws UsernameIsExistentException {
        if (!userBlService.checkUsername(staffAddParameters.getUsername())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(staffAddParameters.getUsername(), encoder.encode(staffAddParameters.getPassword()), 1, new ArrayList<>());
            Staff staff = new Staff(staffAddParameters.getUsername(), staffAddParameters.getTeam(), new Date(), staffAddParameters.getCode(), staffAddParameters.getOperator(), staffAddParameters.getStatus(), staffAddParameters.getPost(), user);
            Staff result = staffBlService.addStaff(staff);
            user.setTableId(result.getId());
            userBlService.updateUser(user);
            AdminAddResponse adminAddResponse = new AdminAddResponse(staff.getId(), 1);
            return new ResponseEntity<>(new JSONResponse(200, adminAddResponse), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "新增代理商",actionType = "1")
    @ApiOperation(value = "新增代理商", notes = "管理员新增代理")
    @RequestMapping(value = "agent/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addAgent(@RequestBody AgentAddParameters agentAddParameters) {
        if (userBlService.checkUsername(agentAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(agentAddParameters.getUsername(), encoder.encode(agentAddParameters.getPassword()), 2, new ArrayList<>());
            Agent agent = new Agent(agentAddParameters.getUsername(), agentAddParameters.getStatus(), agentAddParameters.getAlipay(), agentAddParameters.getWechat(), 0, 0, user);
            //    User user = new User(agentAddParameters.getUsername(), encoder.encode(agentAddParameters.getPassword()), RSAUtils.encryptedDataOnJava(agentAddParameters.getPassword(), publicKey), 2, new ArrayList<>());
            //    Agent agent = new Agent(agentAddParameters.getUsername(), agentAddParameters.getStatus(), agentAddParameters.getAlipay(), agentAddParameters.getWechat(),0, 0,user);
            AgentAddResponse agentAddResponse = agentBlService.addAgent(agent);
            user.setTableId(agentAddResponse.getAgentId());
            userBlService.updateUser(user);
            return new ResponseEntity<>(new JSONResponse(200, agentAddResponse), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "新增商家",actionType = "1")
    @ApiOperation(value = "新增商家", notes = "代理/管理员新增商家")
    @RequestMapping(value = "merchant/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addMerchant(@RequestBody MerchantAddParameters merchantAddParameters) {

        if (userBlService.checkUsername(merchantAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        } else if (StringUtils.isBlank(merchantAddParameters.getUsername()) || StringUtils.isBlank(merchantAddParameters.getPassword())) {
            return new ResponseEntity<>(new JSONResponse(10120, new BlankInputException().getResponse()), HttpStatus.OK);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(merchantAddParameters.getUsername(), encoder.encode(merchantAddParameters.getPassword()), 3, new ArrayList<>());
            Merchant merchant = new Merchant(merchantAddParameters.getAlipay_TPASS(),merchantAddParameters.getAlipay_TSOLID(),merchantAddParameters.getAlipay_RPASSOFF(),
                    merchantAddParameters.getAlipay_RPASSQR(),merchantAddParameters.getAlipay_RSOLID(), merchantAddParameters.getWechat(), 0, merchantAddParameters.getStatus(),
                    new Date(), merchantAddParameters.getUsername(), merchantAddParameters.getApplyId(), user, merchantAddParameters.getLevel());
//            if (userDataService.getUserById(merchantAddParameters.getApplyId()).getRole() == 2)
//                merchant.setStatus("申请启用");//代理商新增商户需要等待管理员审批，所以账号暂时不可用
            MerchantAddResponse merchantAddResponse = merchantBlService.addMerchant(merchant);
            user.setTableId(merchant.getId());
            userBlService.updateUser(user);
            return new ResponseEntity<>(new JSONResponse(200, merchantAddResponse), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "新增供码用户",actionType = "1")
    @ApiOperation(value = "新增供码用户", notes = "管理员新增供码用户")
    @RequestMapping(value = "supplier/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addSupplier(@RequestBody SupplierAddParameters supplierAddParameters) {
        if (userBlService.checkUsername(supplierAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException().getResponse()), HttpStatus.OK);
        } else if (StringUtils.isBlank(supplierAddParameters.getUsername())) {
            return new ResponseEntity<>(new JSONResponse(10110, new BlankInputException().getResponse()), HttpStatus.OK);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(supplierAddParameters.getUsername(), encoder.encode(supplierAddParameters.getPassword()), 4, new ArrayList<>());
            Supplier supplier = new Supplier(user, supplierAddParameters.getId(), new Date(), supplierAddParameters.getStatus(), new ArrayList<>(), supplierAddParameters.getLevel(), CodeType.TSOLID);

            try {
                UserAddResponse userAddResponse = supplierBlService.addSupplier(supplier);
                user.setTableId(userAddResponse.getTableId());
                userBlService.updateUser(user);
                return new ResponseEntity<>(new JSONResponse(200, userAddResponse), HttpStatus.OK);
            } catch (UsernameIsExistentException e) {
                return new ResponseEntity<>(new JSONResponse(10100, e.getResponse()), HttpStatus.OK);
            }
        }
    }
    @SystemControllerLog(descrption = "更改管理员信息",actionType = "3")
    @ApiOperation(value = "更改管理员信息", notes = "更改管理员信息")
    @RequestMapping(value = "staff/update/{uid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> updateStaff(@PathVariable("uid") int id, @RequestBody AdminUpdateParameters adminUpdateParameters) {
        if (StringUtils.isBlank(adminUpdateParameters.getPassword()) || StringUtils.isBlank(adminUpdateParameters.getName())   || StringUtils.isBlank(adminUpdateParameters.getStatus())) {
            return new ResponseEntity<>(new JSONResponse(10120, new WrongResponse(10120, "输入不能为空.")), HttpStatus.OK);
        }
        User user = userDao.findUserById(id);
        if (!user.getUsername().equals(adminUpdateParameters.getName())) {
            if (userDao.findUserByUsername(adminUpdateParameters.getName()) != null)
                return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException()), HttpStatus.OK);
            else {
                Staff staff = staffDao.findByUserId(id);
                staff.setTeam(adminUpdateParameters.getTeam());
                staff.setPost(adminUpdateParameters.getPost());
                staff.setStatus(adminUpdateParameters.getStatus());
                staff.setStaffName(adminUpdateParameters.getName());
                user.setUsername(adminUpdateParameters.getName());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(adminUpdateParameters.getPassword()));
                staff.setUser(user);
                userDataService.saveUser(user);
                staffDataService.saveStaff(staff);
                return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新成功")), HttpStatus.OK);
            }
        } else {
            Staff staff = staffDao.findByUserId(id);
            staff.setTeam(adminUpdateParameters.getTeam());
            staff.setPost(adminUpdateParameters.getPost());
            staff.setStatus(adminUpdateParameters.getStatus());
            staff.setStaffName(adminUpdateParameters.getName());
            user.setUsername(adminUpdateParameters.getName());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(adminUpdateParameters.getPassword()));
            staff.setUser(user);
            userDataService.saveUser(user);
            staffDataService.saveStaff(staff);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新成功")), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "更改代理商信息",actionType = "3")
    @ApiOperation(value = "更改代理商信息", notes = "更改代理商信息")
    @RequestMapping(value = "agent/update/{uid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> updateAgent(@PathVariable("uid") int id, @RequestBody AgentUpdateParameters agentUpdateParameters) throws UsernameIsExistentException {
        if (StringUtils.isBlank(agentUpdateParameters.getPassword()) || StringUtils.isBlank(agentUpdateParameters.getName()) || StringUtils.isBlank(String.valueOf(agentUpdateParameters.getAlipay())) || StringUtils.isBlank(String.valueOf(agentUpdateParameters.getWechat())) || StringUtils.isBlank(agentUpdateParameters.getStatus())) {
            return new ResponseEntity<>(new JSONResponse(10120, new WrongResponse(10120, "输入不能为空.")), HttpStatus.OK);
        }
        User user = userDao.findUserById(id);
        if (!user.getUsername().equals(agentUpdateParameters.getName())) {
            if (userDao.findUserByUsername(agentUpdateParameters.getName()) != null)
                return new ResponseEntity<>(new JSONResponse(10100, new UsernameIsExistentException()), HttpStatus.OK);
            else {
                Agent agent = agentDao.findByUserId(id);
                agent.setAlipay(agentUpdateParameters.getAlipay());
                agent.setWechat(agentUpdateParameters.getWechat());
                agent.setStatus(agentUpdateParameters.getStatus());
                agent.setAgentName(agentUpdateParameters.getName());
                user.setUsername(agentUpdateParameters.getName());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(agentUpdateParameters.getPassword()));
                agent.setUser(user);
                userDataService.saveUser(user);
                agentDataService.saveAgent(agent);
                return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新成功")), HttpStatus.OK);
            }
        } else {
            Agent agent = agentDao.findByUserId(id);
            agent.setAlipay(agentUpdateParameters.getAlipay());
            agent.setWechat(agentUpdateParameters.getWechat());
            agent.setStatus(agentUpdateParameters.getStatus());
            agent.setAgentName(agentUpdateParameters.getName());
            user.setUsername(agentUpdateParameters.getName());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(agentUpdateParameters.getPassword()));
            agent.setUser(user);
            userDataService.saveUser(user);
            agentDataService.saveAgent(agent);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新成功")), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "更改商户信息",actionType = "3")
    @ApiOperation(value = "更改商户信息", notes = "更改商户信息")
    @RequestMapping(value = "merchant/update/{uid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> updateMerchant(@PathVariable("uid") int id, @RequestBody MerchantUpdateParameters merchantUpdateParameters) {
        if (StringUtils.isBlank(merchantUpdateParameters.getPassword())) {
            return new ResponseEntity<>(new JSONResponse(10120, new WrongResponse(10120, "密码不能为空.")), HttpStatus.OK);
        }
        try {
            merchantBlService.updateMerchant(id, merchantUpdateParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新成功")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        } catch (UsernameIsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10100, e.getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "更改供码用户信息",actionType = "3")
    @ApiOperation(value = "更改供码用户信息", notes = "更改供码用户信息")
    @RequestMapping(value = "supplier/update/{uid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> updateSupplier(@PathVariable("uid") int id, @RequestBody SupplierUpdateParameters supplierUpdateParameters) {
        try {
            supplierBlService.updateSupplier(id, supplierUpdateParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新成功")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10170, new WrongResponse(10170, "输入错误")), HttpStatus.OK);
        } catch (UsernameIsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10100, e.getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "删除用户",actionType = "2")
    @ApiOperation(value = "删除用户", notes = "删除某个用户")
    @RequestMapping(value = "account/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(new JSONResponse(200, userBlService.deleteUserById(id)), HttpStatus.OK);
    }
//
//    @ApiOperation(value = "删除代理商", notes = "管理员删除代理商")
//    @RequestMapping(value = "agent/delete/{aid}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteAgent(@PathVariable("aid")int aid) {
//        agentBlService.delAgentById(aid);
//        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "删除商家", notes = "代理商/管理员删除商家")
//    @RequestMapping(value = "merchant/delete/{mid}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteMerchant(@PathVariable("mid")int mid) {
//        merchantBlService.delMerchantById(mid);
//        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "删除管理员", notes = "主管理员删除管理员")
//    @RequestMapping(value = "admin/delete/{aid}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteAdmin(@PathVariable("aid")int aid) {
//        staffBlService.delStaffById(aid);
//        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "删除供码用户", notes = "管理员删除供码用户")
//    @RequestMapping(value = "supplier/delete/{sid}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteSupplier(@PathVariable("sid")int sid) {
//        supplierBlService.delSupplierById(sid);
//        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete successfully")), HttpStatus.OK);
//    }

    @ApiOperation(value = "用户信息", notes = "查看用户详细信息")
    @RequestMapping(value = "user/info/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserInfoResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showUserInfo(@PathVariable("id") int id) {
        return new ResponseEntity<>(new JSONResponse(200, userBlService.findUserInfoById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "管理员列表", notes = "查看全部管理员")
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Staff.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showAdmins() {
        return new ResponseEntity<>(new JSONResponse(200, staffBlService.findAllStaffs()), HttpStatus.OK);
    }

    @ApiOperation(value = "代理商列表", notes = "查看全部代理商")
    @RequestMapping(value = "/agents", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AgentInfoResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showAgents() {
        return new ResponseEntity<>(new JSONResponse(200, agentBlService.findAllAgents()), HttpStatus.OK);
    }

    @ApiOperation(value = "供码用户列表", notes = "查看供码用户")
    @RequestMapping(value = "/suppliers", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Supplier.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showSuppliers() {
        return new ResponseEntity<>(new JSONResponse(200, supplierBlService.findAllSuppliers()), HttpStatus.OK);
    }

    @ApiOperation(value = "全部商户", notes = "管理员查看平台全部商户")
    @RequestMapping(value = "/merchants", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Merchant.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showMerchants() {
        return new ResponseEntity<>(new JSONResponse(200, merchantBlService.findAllMerchants()), HttpStatus.OK);
    }

    @ApiOperation(value = "代理商的商户", notes = "代理商查看已签约商户")
    @RequestMapping(value = "/myMerchants/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Merchant.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showMyMerchants(@PathVariable("id") int id) {
        try {
            List<Merchant> merchantList = merchantBlService.findMerchantsBySuperior(id);
            return new ResponseEntity<>(new JSONResponse(200, merchantList), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(200, e.getResponse()), HttpStatus.OK);
        }
    }
    @ApiOperation(value = "查看某个商户", notes = "查看某个商户")
    @RequestMapping(value = "merchant/{merchantName}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getMerchantRate(@PathVariable("merchantName")String merchantName) {
        return new ResponseEntity<>(new JSONResponse(200, merchantBlService.findMerchantByMerchantName(merchantName)), HttpStatus.OK);
    }
}
